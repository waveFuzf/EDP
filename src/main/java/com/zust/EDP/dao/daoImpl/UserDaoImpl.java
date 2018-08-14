package com.zust.EDP.dao.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.zust.EDP.dao.UserDao;
import com.zust.EDP.entity.Tuser;

@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
	private SessionFactory sessionFactory;

	public Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public void saveUser(Tuser user) {
		// TODO Auto-generated method stub
		getCurrentSession().save(user);
	}

	@SuppressWarnings("unchecked")
	public List<Tuser> findUserTel(Tuser user) {
		// TODO Auto-generated method stub
		String sql = "from Tuser as user where user.tel='" + user.getTel() + "'";
		return getCurrentSession().createQuery(sql).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Tuser findUser_by_Id(Integer userId) {
		// TODO Auto-generated method stub
		String sql = "from Tuser as user where user.userId=" + userId;
		List<Tuser> list = getCurrentSession().createQuery(sql).list();
		return list.get(0);
	}

	@Override
	public int count(String type, Tuser user) {
		// TODO Auto-generated method stub
		int count;
		if (type.equals("p")) {
			String sql = "select count(m.messageId)from Tmessage m left join m.passivePer u where u.userId='"
					+ user.getUserId() + "' group by u.userId ";
			Query query = getCurrentSession().createQuery(sql);
			count = ((Number) query.uniqueResult()).intValue();
		} else {
			String sql = "select count(r.requestId) from Trequest r left join r.user_requester_id u where u.userId='"
					+ user.getUserId() + "'group by u.userId ";
			Query query = getCurrentSession().createQuery(sql);
			count = ((Number) query.uniqueResult()).intValue();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tuser> findtop3_p() {
		// TODO Auto-generated method stub
		String sql = "select u from Tmessage m left join m.passivePer u where m.fromNum like 'PH%' group by u.userId order by count(m.messageId) desc";
		Query query = getCurrentSession().createQuery(sql);
		query.setMaxResults(3);
		return query.list();
	}

	// 更新用户积分
	@Override
	public void updateuser(Integer userId, Tuser user) {
		// TODO Auto-generated method stub
		String sql = "from Tuser as user where user.userId=" + userId;
		Tuser u = (Tuser) getCurrentSession().createQuery(sql).list().get(0);
		u.setIntegral(user.getIntegral());
	}
	//更新用户信誉等级
	@Override
	public void updateuserLevel(Integer userId, Tuser user) {
		// TODO Auto-generated method stub
		String sql = "from Tuser as user where user.userId=" + userId;
		Tuser u = (Tuser) getCurrentSession().createQuery(sql).list().get(0);
		u.setCreditLevel(user.getCreditLevel());
	}

	// 更新用户信息
	@Override
	public void update(Tuser user) {
		// TODO Auto-generated method stub
		String sql = "from Tuser as user where user.userId=" + user.getUserId();
		Tuser u = (Tuser) getCurrentSession().createQuery(sql).list().get(0);
		u.setAddress(user.getAddress());
		u.setName(user.getName());
		u.setSex(user.getSex());
	}
	// 更换头像

	@Override
	public void updateAvatar(Tuser user) {
		// TODO Auto-generated method stub
		String sql = "from Tuser as user where user.userId=" + user.getUserId();
		Tuser u = (Tuser) getCurrentSession().createQuery(sql).list().get(0);
		u.setImage(user.getImage());
	}

}
