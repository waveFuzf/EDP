package com.zust.EDP.webServer;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import com.zust.EDP.dao.PublishDao;
import com.zust.EDP.dao.WebSocketDao;
import com.zust.EDP.dto.Message;
import com.zust.EDP.service.MessageService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.zust.EDP.entity.Tmessage;
import com.zust.EDP.entity.Tuser;
import com.zust.EDP.service.WebSocketService;
import com.zust.EDP.util.Tools;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@ServerEndpoint(value = "/websocket", configurator = GetHttpSessionConfigurator.class)
public class WebSocket {
	public static Map<Integer, Session> webSocketMap = new HashMap<Integer, Session>();

	private static Map<String, Tuser> users = new HashMap<String, Tuser>();
	@Autowired
	private Tools tool;

	@Autowired
	private MessageService messageService;
	@Autowired
	private PublishDao publishService;

	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		users.put(session.getId(), (Tuser) httpSession.getAttribute("userMessage"));
		webSocketMap.put(((Tuser) httpSession.getAttribute("userMessage")).getUserId(), session);
		System.out.println("有新连接加入！当前在线人数为" + webSocketMap.size());
	}

	@OnClose
	public void onClose(Session session) {
		webSocketMap.remove(users.get(session.getId()).getUserId());
		System.out.println("有一连接关闭！当前在线人数为" + webSocketMap.size());
	}

	@Autowired
	private WebSocketService webSocketService;
	@Autowired
	private WebSocketDao webSocketDao;

	/*
	 * type：1，第一种发布的确定消息； type：2，第一种发布的反馈消息； type：3，第一种发布的反馈消息时的快递信息；
	 * type：4，第二种发布的确定消息; type：5，返回消息记录和对方是否在线； 无type为转发消息
	 */
	@OnMessage
	public void onMessage(String message, Session session) throws JSONException, IOException {
		System.out.println("来自客户端的消息:" + message);
		String string = null;
		JSONObject jsonObject = tool.changeStringToJson(message);
		if (jsonObject.has("type")) {
			System.out.println("包含type");
			if (jsonObject.get("type").toString().equals("1")) {
				// 判断接单人的信誉等级
				System.out.println("type==1");
				if (webSocketService.userCreditLevelCompareWithPRequirement(users.get(session.getId()).getUserId(),
						jsonObject.getString(("publishId")))) {
					Tmessage tmessage = new Tmessage();
					tmessage.setFromNum((String) jsonObject.get("publishId"));
					tmessage.setMsgType(0);
					tmessage.setPassivePer(users.get(session.getId()));
					tmessage.setOrderDate(tool.getNowTime());
					tmessage.setState(0);
					Integer userId=publishService.findPublish_by_num("PH20180829171845001").get(0).getUser_publisher_id().getUserId();
					tmessage.setUserId(userId);
					string = webSocketService.sendToPublisher(tmessage);
					sendToUser(string, session);
					System.out.println("当前用户信誉高于限制要求");
				} else {
					System.out.println("用户id：" + users.get(session.getId()).getUserId());
					string = webSocketService.splitTypeSix(users.get(session.getId()).getUserId());
					sendToUser(string, session);
					System.out.println("当前用户信誉等级低");
				}

			} else if (jsonObject.get("type").toString().equals("2")) {
				System.out.println("type==2");
				if (jsonObject.get("choose").toString().equals("true")) {
					string = webSocketService.sendToPassivePerByTrue(jsonObject.getInt("messageId"),jsonObject.getInt("supportId"));
//					messageService.changeState(jsonObject.getInt("messageId"),2);
					System.out.println("接单人确认接单");
					sendToUser(string, session);
					webSocketDao.changeMsgType(0, jsonObject.getInt("messageId"));
				} else {
					string = webSocketService.sendToPassivePerByFalse(jsonObject.getInt("messageId"),jsonObject.getInt("supportId"));
//					messageService.changeState(jsonObject.getInt("messageId"),1);
					System.out.println("接单人拒绝接单");
					sendToUser(string, session);
					webSocketDao.changeMsgType(0, jsonObject.getInt("messageId"));
				}
			} else if (jsonObject.get("type").toString().equals("3")) {
				System.out.println("type==3");
				string = webSocketService.backToExpressMessage(users.get(session.getId()).getUserId());
				sendToUser(string, session);
				System.out.println("接单人收到反馈信息以确认是哪个单");
			} else if (jsonObject.get("type").toString().equals("4")) {
				System.out.println("type==4");
				string = webSocketService.sendToExpressSecond(jsonObject.getString("publishId"),
						jsonObject.getString("publisherId"), jsonObject.getInt("integral"),jsonObject.getInt("supportId"));
				sendToUser(string, session);
				System.out.println("第二种发布，接单人查看快递信息");
			} else if (jsonObject.get("type").toString().equals("5")) {
				System.out.println("type==5");
				string = webSocketService.findTrecord(jsonObject.getInt("fromId"), jsonObject.getInt("toId"));
				if (webSocketMap.containsKey(jsonObject.getInt("toId")))
					string = string + "\"online\":\"true\"}";
				else
					string = string + "\"online\":\"false\"}";
				System.out.println(string);
				sendToUser(string, session);
				System.out.println("返回历史消息和对方是否在线");
			}
		} else {
			// string = "{\"id\":\"" + jsonObject.get("id") + "\",\"body\":\"" +
			// jsonObject.get("body") + "\",\"time\":\""
			// + tool.formatTime(tool.getNowTime()) + "\"}";
			/*
			 * 发送消息 id：接受人id body：消息内容 time：发送时间 senderId：发送人id;
			 */
			System.out.println("不包含type");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", jsonObject.get("id"));
			map.put("body", jsonObject.get("body"));
			map.put("time", tool.formatTime(tool.getNowTime()));
			map.put("senderId", users.get(session.getId()).getUserId());
			map.put("image", users.get(session.getId()).getImage());
			map.put("name", users.get(session.getId()).getName());
			string = tool.splitJson(map);
			sendToUser(string, session);
			System.out.println("发送人：" + users.get(session.getId()).getUserId());
			System.out.println(string);
		}
	}

	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}

	public void sendToUser(String str, Session session) throws JSONException, IOException {
		JSONObject json = tool.changeStringToJson(str);
		if (webSocketMap.containsKey(json.getInt("id"))) {
			if (json.has("body")) {
				Tuser user1 = new Tuser();
				Tuser user2 = new Tuser();
				user1.setUserId(users.get(session.getId()).getUserId());
				user2.setUserId(json.getInt("id"));
				webSocketService.saveRecord(user1, user2, json.getString("body"), 1, tool.getNowTime());
			}
			webSocketMap.get(json.getInt("id")).getBasicRemote().sendText(str);
			System.out.println("消息发送成功！");

		} else {
			if (json.has("body")) {
				Tuser user1 = new Tuser();
				Tuser user2 = new Tuser();
				user1.setUserId(users.get(session.getId()).getUserId());
				user2.setUserId(json.getInt("id"));
				webSocketService.saveRecord(user1, user2, json.getString("body"), 0, tool.getNowTime());
				System.out.println("对方不在线！");
			}
		}
	}
}