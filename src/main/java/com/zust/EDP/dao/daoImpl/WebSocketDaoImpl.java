package com.zust.EDP.dao.daoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zust.EDP.dao.WebSocketDao;
import com.zust.EDP.entity.Texpress;
import com.zust.EDP.entity.Tmessage;
import com.zust.EDP.entity.Tpublish;
import com.zust.EDP.entity.Trecord;
import com.zust.EDP.entity.Trequest;
import com.zust.EDP.entity.Tuser;

@Repository
public class WebSocketDaoImpl implements WebSocketDao {
	@Autowired
	private SessionFactory sessionFactory;

	public Session getCurrentSession() {
		return this.sessionFactory.openSession();
	}

	@Override
	public void save(Tmessage message) {
		// TODO Auto-generated method stub
		getCurrentSession().save(message);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> findPublisherName(Tmessage message) {
		// TODO Auto-generated method stub
		String sql = "from Tuser u left join fetch u.publisher_id p where p.fromNum='" + message.getFromNum() + "'";
		List<Tuser> user = getCurrentSession().createQuery(sql).list();
		// if (user.isEmpty()) {
		// sql = "from Tuser u left join fetch u.requester_id r where
		// r.fromNum='" + message.getFromNum() + "'";
		// user = getCurrentSession().createQuery(sql).list();
		// }
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", message.getPassivePer().getName());
		map.put("passiveId", message.getPassivePer().getUserId());
		map.put("fromNum", message.getFromNum());
		map.put("messageId", message.getMessageId());
		map.put("id", user.get(0).getUserId());
		map.put("image", message.getPassivePer().getImage());
		return map;
	}

	@Override
	public Tmessage findMessage(int id) {
		// TODO Auto-generated method stub
		Tmessage tmessage = (Tmessage) getCurrentSession().get(Tmessage.class, id);
		return tmessage;
	}

	@Override
	public Tuser findUser(int id) {
		// TODO Auto-generated method stub
		Tuser tuser = (Tuser) getCurrentSession().get(Tuser.class, id);
		return tuser;
	}

	@Override
	public void changeState(Tmessage msg) {
		// TODO Auto-generated method stub
		String sql = "update Tpublish p set p.state=3 where p.fromNum=\'" + msg.getFromNum() + "\'";
		getCurrentSession().createQuery(sql).executeUpdate();
	}

	@Override
	public void changeMsgType(int msgType, int id) {
		// TODO Auto-generated method stub
		String sql = "update Tmessage m set m.msgType=" + msgType + " where m.messageId=" + id;
		getCurrentSession().createQuery(sql).executeUpdate();
	}

	@Override
	public void saveRecord(Trecord trecord) {
		// TODO Auto-generated method stub
		getCurrentSession().save(trecord);
	}

	@Override
	public Tpublish findByPfromNum(String fromNum) {
		// TODO Auto-generated method stub
		String sql = "from Tpublish p where p.fromNum='" + fromNum + "'";
		return (Tpublish) getCurrentSession().createQuery(sql).list().get(0);
	}

	@Override
	public Trequest findByRfromNum(String fromNum) {
		// TODO Auto-generated method stub
		String sql = "from Trequest r where r.fromNum='" + fromNum + "'";
		return (Trequest) getCurrentSession().createQuery(sql).list().get(0);
	}

	@Override
	public Texpress findExpress(Tpublish tpublish) {
		// TODO Auto-generated method stub
		String sql = "select e from Texpress e left join e.publishId p where p.publishId='" + tpublish.getPublishId()
				+ "'";
		return (Texpress) getCurrentSession().createQuery(sql).list().get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Trecord> findRecord(int fromId, int toId) {
		// TODO Auto-generated method stub
		String sql = "from Trecord r where r.user_sender_id=:fromId and r.user_sended_id=:toId or r.user_sender_id=:toId and r.user_sended_id=:fromId order by r.recordDate";
		return getCurrentSession().createQuery(sql).setInteger("fromId", fromId).setInteger("toId", toId)
				.setFirstResult(0).setMaxResults(10).list();
	}

	@Override
	public void update(int integral, String fromNum) {
		// TODO Auto-generated method stub
		String hql = "update Tpublish p set p.integral='" + integral + "' where p.fromNum='" + fromNum + "'";
		getCurrentSession().createQuery(hql).executeUpdate();
	}

	@Override
	public void updateIntegral(int newIntegral, int id) {
		// TODO Auto-generated method stub
		String hql = "update Tuser u set u.integral='" + newIntegral + "' where u.id='" + id + "'";
		getCurrentSession().createQuery(hql).executeUpdate();
	}

	@Override
	public Tpublish find(int supportId) {
		// TODO Auto-generated method stub
		return (Tpublish) getCurrentSession().get(Tpublish.class, supportId);
	}

}
