package com.zust.EDP.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zust.EDP.dto.Home;
import com.zust.EDP.dto.Homeuser;
import com.zust.EDP.dto.Mytake;
import com.zust.EDP.dto.User;
import com.zust.EDP.entity.Tuser;

public interface UserService {
	public String doRegister(Tuser user, HttpSession session, String imageIcon);

	public Map<String, Object> doLogin(Tuser user, HttpSession session);

	public void getImageIcon(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;

	public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

	public User Tuser_User(Tuser user);

	public Tuser User_Tuser(User user);

	public List<Homeuser> homeuser();

	public Home loading(Integer userId);

	public Map<String, Object> updateUserMessage(Tuser tuser, HttpSession session);

	public void updateAvatar(Tuser tuser, HttpSession session);

	public Mytake myput(Integer userId);

	public Mytake mytake(Integer userId);

	public Mytake mytaken(Integer userId);

	public User findUser(int personId);

}
