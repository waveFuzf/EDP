package com.zust.EDP.dao;

import java.util.List;

import com.zust.EDP.entity.Tidcard;
import com.zust.EDP.entity.Tuser;

public interface UserDao {
	public void saveUser(Tuser user);

	public List<Tuser> findUserTel(Tuser user);

	public Tuser findUser_by_Id(Integer userId);

	public int count(String type, Tuser user);

	public List<Tuser> findtop3_p();

	public void updateuser(Integer userId, Tuser user);

	public void update(Tuser user);

	public void updateAvatar(Tuser user);

	public void updateuserLevel(Integer userId, Tuser user);

    public List<Tuser> findUserByNum(Integer cardnum);

	public void updateCardnum(Integer userId, Tidcard id);

	public void updateState(int i, int i1);
}
