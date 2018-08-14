package com.zust.EDP.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zust.EDP.dto.Home;
import com.zust.EDP.dto.Mytake;
import com.zust.EDP.dto.User;
import com.zust.EDP.entity.Tuser;
import com.zust.EDP.service.EvaluateService;
import com.zust.EDP.service.PublishService;
import com.zust.EDP.service.UserService;
import com.zust.EDP.util.ImgUp;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private PublishService publishService;
	@Autowired
	private EvaluateService evaluateService;

	// 点击注册
	@RequestMapping(value = "/code", method = RequestMethod.GET)
	public void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		userService.register(request, response);
	}

	// 提交注册信息

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> doRegister(Tuser user, HttpSession session, String code) {
		Map<String, String> map = new HashMap<String, String>();
		String isRegister = userService.doRegister(user, session, code);
		map.put("isRegister", isRegister);
		return map;
	}

	// 点击登录
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping("/home")
	public String webSocket() {
		return "main";
	}

	// 提交登录信息
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> doLogin(Tuser user, HttpSession session) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		// String isLogin = userService.doLogin(user, session);
		// map.put("isLogin", isLogin);
		// map.put("userMessage", value);
		map = userService.doLogin(user, session);
		System.out.println(session.getId());
		// System.out.println(user.getUserId());
		return map;
	}

	// 加载页面
	@RequestMapping(value = "/loading", method = RequestMethod.POST)
	public @ResponseBody Home loading(HttpServletResponse response, String longitude, String latitude,
			HttpSession session) {
		System.out.println("经度：" + longitude);
		System.out.println("纬度：" + latitude);
		double a = Double.parseDouble(longitude);
		double b = Double.parseDouble(latitude);
		session.getAttribute("userMessage");
		int c = ((Tuser) session.getAttribute("userMessage")).getUserId();
		System.out.println(c);
		publishService.savelalo(a, b);
		return userService.loading(c);
	}

	// 更换头像
	// 返回isUpdate：true|error|uploadError|loginBeforupload
	// 返回image
	@RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> uploadAvatar(HttpSession session, String datax) {
		Tuser my = (Tuser) session.getAttribute("userMessage");
		String path = "F:\\maven_stu\\EDP\\src\\main\\webapp";
		String msg = "";
		Map<String, String> map = new HashMap<String, String>();
		ImgUp imgUp = new ImgUp();
		try {
			msg = imgUp.UpImg(String.valueOf(my.getUserId()), datax, path);
			map.put("isUpload", msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("isUpload", "error");
		}
		if (my.getImage() == "" || my.getImage() == null) {
			my.setImage("../avatar_" + my.getUserId() + ".jpg");
			userService.updateAvatar(my, session);
		}
		map.put("image", "../avatar_" + my.getUserId() + ".jpg");
		return map;
	}

	// 修改个人信息
	// 返回isUpdate：true|false
	@RequestMapping(value = "/updateUserMessage", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateUserMessage(String name, Integer sex, String address,
			HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		Tuser my = (Tuser) session.getAttribute("userMessage");
		my.setAddress(address);
		my.setName(name);
		my.setSex(sex);
		map = userService.updateUserMessage(my, session);
		map.put("name", name);
		map.put("sex", sex);
		map.put("address", address);
		return map;
	}

	// 我的发布
	@RequestMapping(value = "/myput", method = RequestMethod.POST)
	public @ResponseBody Mytake myput(HttpSession session) {
		Tuser u = (Tuser) session.getAttribute("userMessage");
		System.out.println("session的用户id"+u.getUserId());
		return userService.myput(u.getUserId());
	}

	// 我的接单
	@RequestMapping(value = "/mytake", method = RequestMethod.POST)
	public @ResponseBody Mytake mytake(HttpSession session) {
		Tuser u = (Tuser) session.getAttribute("userMessage");
		return userService.mytake(u.getUserId());
	}

	// 我的被接
	@RequestMapping(value = "/mytaken", method = RequestMethod.POST)
	public @ResponseBody Mytake mytaken(HttpSession session) {
		Tuser u = (Tuser) session.getAttribute("userMessage");
		return userService.mytaken(u.getUserId());
	}
	@RequestMapping("/personpage")
	public String personpage() {
		return "mypage";
	}
	
	// 进入个人信息页
	@RequestMapping("/mypage")
	public @ResponseBody Map<String, Object> mypage(HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		Tuser tuser = (Tuser) session.getAttribute("userMessage");
		map.put("image", tuser.getImage());
		map.put("name", tuser.getName());
		map.put("sex", tuser.getSex());
		map.put("address", tuser.getAddress());
		return map;
	}
	
	//删除我的发布
		@RequestMapping(value = "/deletemyput", method = RequestMethod.POST)
		public @ResponseBody Map<String, String> deletemyput(HttpServletResponse response,String publishId){
			Map<String, String> map = new HashMap<String,String>();
			Integer a = Integer.parseInt(publishId);
			String message = publishService.deletepublish(a);
			map.put("message", message);
			map.put("message", message);
			return map;	
		}
		
		
	//查看他人的资料
		@RequestMapping(value="/seeOtherSpace",method=RequestMethod.POST)
		public @ResponseBody Map<String, Object> seeOtherSpace(int personId){
			Map<String, Object> map=new HashMap<String,Object>();
			User user=	userService.findUser(personId);
			map.put("userMessage", user);
			List<Map<String, Object>> list=evaluateService.findByEvaluateId(personId);
			map.put("evaluates", list);
			return map;
		}
		
		@RequestMapping(value="/otherspace",method=RequestMethod.GET)
		public String otherSpace(){
			return "other";
		}

}
