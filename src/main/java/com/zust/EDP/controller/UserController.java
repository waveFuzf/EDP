package com.zust.EDP.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zust.EDP.entity.Tidcard;
import com.zust.EDP.service.TidcardService;
import com.zust.EDP.util.ApiCheckUtil;
import com.zust.EDP.util.CheckNumUtil;
import com.zust.EDP.util.RedisUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
	@Autowired
	private TidcardService tidcardService;
	@Autowired
	private CheckNumUtil checkNumUtil;
	@Autowired
	private ApiCheckUtil apiCheckUtil;

	@Autowired
	private RedisUtil redisUtil;

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
	public String webSocket(HttpServletRequest httpServletRequest) {

		Tuser tuser = (Tuser) httpServletRequest.getSession().getAttribute("userMessage");
		if (redisUtil.checkRedis(tuser.getUserId(),httpServletRequest.getSession().getId())&&tuser != null) {
			return "main";
		} else {
			return "login";
		}
	}

	// 提交登录信息
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> doLogin(Tuser user, HttpSession session) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map = userService.doLogin(user, session);
		return map;
	}

	// 加载页面
	@RequestMapping(value = "/loading", method = RequestMethod.POST)
	public @ResponseBody Home loading(HttpServletResponse response, String longitude, String latitude,
			HttpSession session) {
		double longitudes = Double.parseDouble(longitude);
		double latitudes = Double.parseDouble(latitude);
		int userid = ((Tuser) session.getAttribute("userMessage")).getUserId();
		publishService.savelalo(longitudes, latitudes);
		return userService.loading(userid);
	}

	// 更换头像
	// 返回isUpdate：true|error|uploadError|loginBeforupload
	// 返回image
	@RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> uploadAvatar(HttpSession session, String datax) {
		Tuser my = (Tuser) session.getAttribute("userMessage");
		Map<String, String> map = new HashMap<String, String>();
		if (!redisUtil.checkRedis(my.getUserId(),session.getId())){
			map.put("isUpload", "error");
			return map;
		}
		String path = "C:\\Users\\YFZX-FZF-1777\\Desktop\\Learn\\EDP\\src\\main\\webapp\\avatar";
		String msg = "";

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
		if (redisUtil.checkRedis(my.getUserId(),session.getId())){
			my.setAddress(address);
			my.setName(name);
			if(sex!=null){
				my.setSex(sex);}
			map = userService.updateUserMessage(my, session);
			map.put("name", name);
			map.put("sex", sex);
			map.put("address", address);
			return map;
		}else {
			return map;
		}

	}

	// 验证身份证号码
	// 返回：信息
	@ResponseBody
	@RequestMapping(value = "/checknum", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public String CheckCardNum(String realname, String idcardnum,HttpServletRequest httpServletRequest) {
		Tuser tuser = (Tuser) httpServletRequest.getSession().getAttribute("userMessage");
		if (!redisUtil.checkRedis(tuser.getUserId(),httpServletRequest.getSession().getId())){
			return new String("无效操作，请重新登录！");
		}
		if(!checkNumUtil.CheckNumName(idcardnum,realname)){
			return new String("格式不正确！");
		}
//		JSONArray reponse=apiCheckUtil.CheckNum(realname,cardnum);
		JSONArray reponse=JSONArray.fromObject("[{'error_code':0,'reason':'认证通过','result':{'realName':'傅郑锋','cardNo':'330108199702010910','details':{'addrCode':'330108','birth':'1997-02-01','sex':1,'length':18,'checkBit':'0','addr':'浙江省杭州市滨江区','province':'浙江省','city':'杭州市','area':'滨江区'}},'ordersign':'20180921115439073021021097'}]");
		switch (reponse.getJSONObject(0).optInt("error_code")){
        //0：认证通过   80008：参数不完整    90033：无此身份证号码    90099：认证不通过
			case 0:
				List<Tidcard> tidcard=tidcardService.selectByNum(idcardnum);
				if (tidcard.size()==0){
					tidcardService.saveTicard(new Tidcard(idcardnum,realname));
				}else {
					List<Tuser> users=userService.findUserByNum(tidcard.get(0).getId());
					if (users.size()==3){
						return new String ("该身份证绑定账号过多！");
					}else{
						userService.updateUser(tuser.getUserId(),tidcard.get(0));
					}
				}
				return new String("认证通过，已绑定该账号！");
			case 80008:
				return new String("参数不完整！");
			case 90033:
				return new String("无此身份证号码！");
			case 90099:
				return new String("认证不通过!");
			default:
				return new String("接口错误，我也不知道该怎么办！");
		}

	}

	// 我的发布
	@RequestMapping(value = "/myput", method = RequestMethod.POST)
	public @ResponseBody Mytake myput(HttpSession session) {
		Tuser u = (Tuser) session.getAttribute("userMessage");
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
	@RequestMapping(value = "/personpage",method = RequestMethod.GET)
	public String personpage() {
		return "mypage";
	}
	
	// 进入个人信息页
	@RequestMapping("/mypage")
	public @ResponseBody Map<String, Object> mypage(HttpServletRequest httpServletRequest) {
		Tuser tuser = (Tuser) httpServletRequest.getSession().getAttribute("userMessage");
		Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", tuser.getImage());
			map.put("name", tuser.getName());
			map.put("sex", tuser.getSex());
			map.put("address", tuser.getAddress());
			map.put("realname",tuser.getTidcard().getRealname());
			map.put("idcardnum",tuser.getTidcard().getIdcardnum().replaceAll("(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)","********"));
			map.put("msg","Success!");
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
