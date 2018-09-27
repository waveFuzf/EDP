package com.zust.EDP.service.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zust.EDP.dao.WebSocketDao;
import com.zust.EDP.entity.Texpress;
import com.zust.EDP.entity.Tmessage;
import com.zust.EDP.entity.Tpublish;
import com.zust.EDP.entity.Trecord;
import com.zust.EDP.entity.Trequest;
import com.zust.EDP.entity.Tuser;
import com.zust.EDP.service.ExtraService;
import com.zust.EDP.service.WebSocketService;
import com.zust.EDP.util.Tools;

@Service
@Transactional
public class WebSocketServiceImpl implements WebSocketService {
	@Autowired
	private WebSocketDao webSocketDao;
	@Autowired
	private Tools tools;
	@Autowired
	private ExtraService extraService;

	/*
	 * 发送给发布人确定消息 type：1 id：发布人id name：接单人姓名 fromNum：单号 messageId：消息id
	 * passiveId：接单人Id image：接单人头像
	 */
	@Override
	public String sendToPublisher(Tmessage message) {
		// TODO Auto-generated method stub
		webSocketDao.save(message);
		Map<String, Object> map = webSocketDao.findPublisherName(message);
		// String body = "{\"type\":\"1\",\"id\":\"" + map.get("id") +
		// "\",\"name\":\"" + map.get("name")
		// + "\",\"fromNum\":\"" + map.get("fromNum") + "\",\"messageId\":\"" +
		// map.get("messageId")
		// + "\",\"passiveId\":\"" + map.get("passiveId") + "\",\"image\":\"" +
		// map.get("image") + "\"}";
		Map<String, Object> mapp = new HashMap<String, Object>();
		mapp.put("type", 1);
		mapp.put("id", map.get("id"));
		mapp.put("name", map.get("name"));
		mapp.put("fromNum", map.get("fromNum"));
		mapp.put("messageId", map.get("messageId"));
		mapp.put("passiveId", map.get("passiveId"));
		mapp.put("image", map.get("image"));
		System.out.println(tools.splitJson(mapp));
		return tools.splitJson(mapp);
	}

	/*
	 * 发送给接单人反馈确定消息 type：2 passiveName：发布人姓名 messageId：消息Id id：接单人Id image：发布人头像
	 * choose:确定 passiveId:发布人Id
	 */
	@Override
	public String sendToPassivePerByTrue(int id,int supportId) {
		// TODO Auto-generated method stub
		Tmessage tmessage = webSocketDao.findMessage(id);
		webSocketDao.changeMsgType(1, id);
		if (tmessage.getFromNum().matches("^PH.*")) {
			Tpublish tpublish = webSocketDao.findByPfromNum(tmessage.getFromNum());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", 2);
			map.put("passiveName", tpublish.getUser_publisher_id().getName());
			map.put("messageId", tmessage.getMessageId());
			map.put("id", tmessage.getPassivePer().getUserId());
			map.put("image", tpublish.getUser_publisher_id().getImage());
			map.put("choose", "true");
			map.put("passiveId", tpublish.getUser_publisher_id().getUserId());
			Texpress texpress = webSocketDao.findExpress(tpublish);
			map.put("company", texpress.getCompany());
			webSocketDao.changeState(tmessage);
			System.out.println(tools.splitJson(map));
			return tools.splitJson(map);
		} else {
			Trequest trequest = webSocketDao.findByRfromNum(tmessage.getFromNum());	
			Tpublish tpublish=webSocketDao.find(supportId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", 2);
			map.put("passiveName", trequest.getUser_requester_id().getName());
			map.put("messageId", tmessage.getMessageId());
			map.put("id", tmessage.getPassivePer().getUserId());
			map.put("image", trequest.getUser_requester_id().getImage());
			map.put("choose", "true");
			map.put("passiveId", trequest.getUser_requester_id().getUserId());
			map.put("fromNum", tpublish.getFromNum());
			System.out.println(tools.splitJson(map));
			return tools.splitJson(map);
		}
	}

	/*
	 * 发送给接单人反馈确定消息 type：2 passiveName：发布人姓名 messageId：消息Id id：接单人Id image：发布人头像
	 * choose:拒绝 passiveId:发布人Id
	 */
	@Override
	public String sendToPassivePerByFalse(int id,int supportId) {
		// TODO Auto-generated method stub
		Tmessage tmessage = webSocketDao.findMessage(id);
		webSocketDao.changeMsgType(1, id);
		if (tmessage.getFromNum().matches("^PH.*")) {
			Tpublish tpublish = webSocketDao.findByPfromNum(tmessage.getFromNum());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", 2);
			map.put("passiveName", tpublish.getUser_publisher_id().getName());
			map.put("messageId", tmessage.getMessageId());
			map.put("id", tmessage.getPassivePer().getUserId());
			map.put("image", tpublish.getUser_publisher_id().getImage());
			map.put("choose", "false");
			map.put("passiveId", tpublish.getUser_publisher_id().getUserId());
			Texpress texpress = webSocketDao.findExpress(tpublish);
			map.put("company", texpress.getCompany());
			System.out.println(tools.splitJson(map));
			return tools.splitJson(map);
		} else {
			Trequest trequest = webSocketDao.findByRfromNum(tmessage.getFromNum());
			Tpublish tpublish=webSocketDao.find(supportId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", 2);
			map.put("passiveName", trequest.getUser_requester_id().getName());
			map.put("messageId", tmessage.getMessageId());
			map.put("id", tmessage.getPassivePer().getUserId());
			map.put("image", trequest.getUser_requester_id().getImage());
			map.put("choose", "false");
			map.put("passiveId", trequest.getUser_requester_id().getUserId());
			map.put("fromNum", tpublish.getFromNum());
			System.out.println(tools.splitJson(map));
			return tools.splitJson(map);
		}
	}

	/*
	 * 保存消息记录 user1:发送人id user2：接受人id message：消息 state：状态 date：发送时间
	 */
	@Override
	public void saveRecord(Tuser user1, Tuser user2, String message, int state, Date date) {
		// TODO Auto-generated method stub
		Trecord trecord = new Trecord();
		trecord.setUser_sender_id(user1);
		trecord.setUser_sended_id(user2);
		trecord.setRecord(message);
		trecord.setRecordDate(date);
		trecord.setRecordState(state);
		webSocketDao.saveRecord(trecord);
	}

	/*
	 * fromNumOne:要拿快递的发布的单号 fromNumTwo：拿快递发布的单号 type: id:接受消息的人的id
	 * name：要拿快递的人的姓名 expressType：快递类型 expressSize：快递大小 expresAddress：取件地址
	 * expressCompany：快递公司 tip：备注 image:要拿快递的人的头像,messageId:消息Id;
	 */
	@Override
	public String sendToExpressSecond(String fromNumOne, String fromNumTwo, int integral,int supportId) {
		// TODO Auto-generated method stub
		Tpublish tpublish = webSocketDao.findByPfromNum(fromNumOne);
		Trequest trequest = webSocketDao.findByRfromNum(fromNumTwo);
		Texpress texpress = webSocketDao.findExpress(tpublish);
		// 判断积分够不够
		if (tpublish.getUser_publisher_id().getIntegral() < integral) {
			System.out.println("用户积分不够");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", 7);
			map.put("id", tpublish.getUser_publisher_id().getUserId());
			return tools.splitJson(map);
		} else {
			// 保存积分奖励
			System.out.println("用户积分够");
			// tpublish.setIntegral(integral);
			webSocketDao.update(integral, fromNumOne);
			webSocketDao.updateIntegral(tpublish.getUser_publisher_id().getIntegral() - integral,
					tpublish.getUser_publisher_id().getUserId());
			Tmessage msg = new Tmessage();
			msg.setMsgType(1);
			msg.setFromNum(fromNumTwo);
			msg.setPassivePer(tpublish.getUser_publisher_id());
			msg.setOrderDate(tools.getNowTime());
			msg.setState(0);
			webSocketDao.save(msg);

			// 加一个插入语句，未写
			extraService.save(fromNumTwo, texpress.getExpressId());

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", 4);
			map.put("id", trequest.getUser_requester_id().getUserId());
			map.put("name", tpublish.getUser_publisher_id().getName());
			map.put("expressType", texpress.getType());
			map.put("expressSize", texpress.getSize());
			map.put("expressAddress", texpress.getAddress());
			map.put("expressCompany", texpress.getCompany());
			map.put("tip", texpress.getTip());
			map.put("image", tpublish.getUser_publisher_id().getImage());
			map.put("fromNumTwo", fromNumTwo);
			map.put("messageId", msg.getMessageId());
			map.put("supportId", supportId);
			// System.out.println("type:3,messageId=" + msg.getMessageId());
			System.out.println(tools.splitJson(map));
			return tools.splitJson(map);
		}

		// 保存积分奖励
		// tpublish.setIntegral(integral);
		// webSocketDao.update(integral, fromNumOne);
		// Tmessage msg = new Tmessage();
		// msg.setMsgType(1);
		// msg.setFromNum(fromNumTwo);
		// msg.setPassivePer(tpublish.getUser_publisher_id());
		// webSocketDao.save(msg);
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("type", 4);
		// map.put("id", trequest.getUser_requester_id().getUserId());
		// map.put("name", tpublish.getUser_publisher_id().getName());
		// map.put("expressType", texpress.getType());
		// map.put("expressSize", texpress.getSize());
		// map.put("expressAddress", texpress.getAddress());
		// map.put("expressCompany", texpress.getCompany());
		// map.put("tip", texpress.getTip());
		// map.put("image", tpublish.getUser_publisher_id().getImage());
		// map.put("fromNumTwo", fromNumTwo);
		// map.put("messageId", msg.getMessageId());
		// //System.out.println("type:3,messageId=" + msg.getMessageId());
		// System.out.println(tools.splitJson(map));
		// return tools.splitJson(map);
	}

	/*
	 * records:消息记录 fromId:发送人id toId:接受人id record:消息 id:聊天发起人id
	 */
	@Override
	public String findTrecord(int fromId, int toId) {
		// TODO Auto-generated method stub
		List<Trecord> lists = new ArrayList<Trecord>();
		lists = webSocketDao.findRecord(fromId, toId);
		Map<String, Object> map = new HashMap<String, Object>();
		String str = "{\"records\":[";
		if(lists.size()!=0){
		for (int i = 0; i < lists.size(); i++) {
			if (i < lists.size() - 1) {
				map.put("fromId", lists.get(i).getUser_sender_id().getUserId());
				map.put("toId", lists.get(i).getUser_sended_id().getUserId());
				map.put("record", lists.get(i).getRecord());
				str = str + tools.splitJson(map) + ",";
			} else {
				map.put("fromId", lists.get(i).getUser_sender_id().getUserId());
				map.put("toId", lists.get(i).getUser_sended_id().getUserId());
				map.put("record", lists.get(i).getRecord());
				str = str + tools.splitJson(map) + "],";
			}
		}}else
			str=str+"],";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String time="";
		if(lists.size()!=0)
			time=simpleDateFormat.format(lists.get(0).getRecordDate());
		str = str + "\"time\":\"" + time + "\",\"id\":\"" + fromId
				+ "\",\"type\":\"" + 5 + "\",";
		return str;
	}

	/*
	 * 查找接单人的信誉等级和该单号的限制要求，相比对 id:接单人id fromNum：表单
	 */
	@Override
	public boolean userCreditLevelCompareWithPRequirement(int id, String fromNum) {
		// TODO Auto-generated method stub
		Tuser user = webSocketDao.findUser(id);
		Tpublish publish = webSocketDao.findByPfromNum(fromNum);
		if (user.getCreditLevel() >= publish.getRequirement())
			return true;
		else
			return false;
	}

	@Override
	public String splitTypeSix(int id) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", 6);
		map.put("id", id);
		map.put("message", "您的信誉等等级略低,快去提升信誉等级！");
		return tools.splitJson(map);
	}

	/*
	 * 反馈信息时的快递信息 id：服务器发送给谁的id expressType：快递类型 expressType：快递类型
	 * expressSize：快递大小 expresAddress：取件地址 expressCompany：快递公司 tip：备注 code：取货码
	 * passiveName：发布人姓名 passiveTel：发布人电话 address：发布人地址
	 */
	@Override
	public String backToExpressMessage(int id) {
		// TODO Auto-generated method stub
		Tmessage message = webSocketDao.findMessage(id);
		Tpublish publish = webSocketDao.findByPfromNum(message.getFromNum());
		Texpress express = webSocketDao.findExpress(publish);
		Map<String, Object> map = new HashMap<String, Object>();
		if (message.getMsgType() == 2) {
			map.put("type", 3);
			map.put("id", message.getPassivePer().getUserId());
			map.put("expressType", express.getType());
			map.put("expressSize", express.getSize());
			map.put("expressAddress", express.getAddress());
			map.put("expressCompany", express.getCompany());
			map.put("tip", express.getTip());
			map.put("code", express.getCode());
			map.put("passiveName", publish.getUser_publisher_id().getName());
			map.put("passiveTel", publish.getUser_publisher_id().getTel());
			map.put("address", publish.getUser_publisher_id().getAddress());
		} else if (message.getMsgType() == 3) {
			map.put("type", 3);
			map.put("id", message.getPassivePer().getUserId());
			map.put("expressType", express.getType());
			map.put("expressSize", express.getSize());
			map.put("expressAddress", express.getAddress());
			map.put("expressCompany", express.getCompany());
			map.put("tip", express.getTip());
		}
		System.out.println(tools.splitJson(map));
		return tools.splitJson(map);
	}

	@Override
	public Integer findPublish_by_num(String fromNum) {
		Tpublish tpublish=webSocketDao.findByPfromNum(fromNum);
		Integer integer=tpublish.getUser_publisher_id().getUserId();
		return integer;

	}
}
