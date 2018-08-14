package com.zust.EDP.dao.daoImpl;

import java.util.HashSet;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zust.EDP.dao.MessageDao;
import com.zust.EDP.entity.Texpress;
import com.zust.EDP.entity.Tmessage;
import com.zust.EDP.entity.Tuser;

@Repository
public class MessageDaoImpl implements MessageDao {
	@Autowired
	private SessionFactory sessionFactory;

	public Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public void saveMessage(Tmessage message) {
		// TODO Auto-generated method stub
		getCurrentSession().save(message);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tmessage> findmessage_by_id(Tmessage message) {
		// TODO Auto-generated method stub
		String sql = "select message from Tuser as user where user.messageId='" + message.getMessageId() + "'";
		return getCurrentSession().createQuery(sql).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Tmessage> findmessage_by_id(List<Integer> messageId) {
		// TODO Auto-generated method stub
		Set<Tmessage> set = new HashSet<Tmessage>();
		for (int i = 0; i < messageId.size(); i++) {
			String sql = "select message from Tmessage as message where message.messageId='" + messageId.get(i) + "'";
			List<Tmessage> list = getCurrentSession().createQuery(sql).list();
			set.add(list.get(0));
		}
		return set;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tmessage> findmessage(Integer userId, String a) {
		// TODO Auto-generated method stub
		String sql;
		if (a.equals("PH")) {
			sql = "select m from Tmessage m left join m.passivePer u where m.state=0 and m.fromNum like 'PH%' and u.userId = '"
					+ userId + "' and m.msgType>=" + 2;
			System.out.println("ssss11");
		} else {
			sql = "select m from Tmessage m left join m.passivePer u where m.state=0 and m.fromNum like 'ES%' and u.userId = '"
					+ userId + "' and m.msgType>=" + 2;
			System.out.println("ssss22");
		}
		Query query = getCurrentSession().createQuery(sql);
		List<Tmessage> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Tuser findpubuser(Integer messageId) {
		// TODO Auto-generated method stub
		Tuser user = new Tuser();
		Tmessage message = find_by_id(messageId);
		String a = message.getFromNum().substring(0, 2);
		System.out.println(a);
		if (a.equals("PH")) {
			String sql = "select u from Tmessage m,Tpublish p left join p.user_publisher_id u where m.fromNum=p.fromNum and m.messageId="
					+ messageId;
			List<Tuser> list = getCurrentSession().createQuery(sql).list();
			if(list.size()!=0)
				user = list.get(0);
		} else if (a.equals("ES")) {
			String sql = "select u from Tmessage m,Trequest r left join r.user_requester_id u where m.fromNum=r.fromNum and m.messageId="
					+ messageId;
			List<Tuser> list = getCurrentSession().createQuery(sql).list();
			if(list.size()!=0)
				user = list.get(0);
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Tmessage find_by_id(Integer messageId) {
		// TODO Auto-generated method stub
		String sql = "select m from Tmessage m  where m.messageId = '" + messageId + "'";
		List<Tmessage> list = getCurrentSession().createQuery(sql).list();
		return list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tmessage> findmessage2(Integer userId) {
		// TODO Auto-generated method stub
		String hql = "select m from Tmessage m,Tpublish p left join p.user_publisher_id u where m.state=0 and m.fromNum=p.fromNum and u.userId = '"
				+ userId + "' and m.msgType=" + 1;
//		String hql2 = "select m from Tmessage m,Trequest r left join r.user_requester_id u where m.fromNum=r.fromNum and u.userId = '"
//				+ userId + "' and m.msgType=" + 1;
		List<Tmessage> list = getCurrentSession().createQuery(hql).list();
//		list.addAll(getCurrentSession().createQuery(hql2).list());
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Texpress findexpress(String fromNum) {
		// TODO Auto-generated method stub
		String sql = "select e from Tmessage m,Texpress e left join e.publishId p where m.fromNum = p.fromNum";
		List<Texpress> list = getCurrentSession().createQuery(sql).list();
		return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findmessageES(Integer userId) {
		// TODO Auto-generated method stub
		String sql = "select new map(m as m,e as e) from Textra ee left join ee.express e, Tmessage m left join m.passivePer u where ee.fromNum=m.fromNum and m.state=0 and m.fromNum like 'ES%' and u.userId = '"+userId+"' and m.msgType>="+2;
	    List<Map<String, Object>> list = getCurrentSession().createQuery(sql).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findmessageES2(Integer userId) {
		// TODO Auto-generated method stub
		String sql = "select new map(m as m,e as e) from Textra ee left join ee.express e, Tmessage m,Trequest r left join r.user_requester_id u where ee.fromNum=m.fromNum and m.state=0 and m.fromNum=r.fromNum and u.userId = '"+userId+"' and m.msgType="+1;
	    List<Map<String, Object>> list = getCurrentSession().createQuery(sql).list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updatestate(Integer messageId) {
		// TODO Auto-generated method stub
		String sql = "select m from Tmessage m where m.messageId = "+messageId;
		List<Tmessage> list = getCurrentSession().createQuery(sql).list();
		Tmessage message = list.get(0);
		message.setState(1);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Tmessage finddate(Integer expressId, int i) {
		// TODO Auto-generated method stub
		String sql;
		if(i==1) {
		 sql = "select m from Texpress e left join e.publishId p,Tmessage m where m.msgType=2 and m.fromNum = p.fromNum and e.expressId="+expressId;
		}
		else {
			sql = "select m from Textra ee left join ee.express e,Tmessage m where m.msgType=2 and m.fromNum = ee.fromNum and e.expressId="+expressId;
		}
		List<Tmessage> list = getCurrentSession().createQuery(sql).list();
		return list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tmessage> countPh(int personId) {
		// TODO Auto-generated method stub
		String hql="from Tmessage m where m.msgType=2 and m.passivePer.userId="+personId;
		List<Tmessage> list=getCurrentSession().createQuery(hql).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tmessage> findByFromNum(String fromNum) {
		// TODO Auto-generated method stub
		String hql="from Tmessage m where m.msgType=2 and m.fromNum='"+fromNum+"'";
		List<Tmessage> list=getCurrentSession().createQuery(hql).list();
		return list;
	}



}
