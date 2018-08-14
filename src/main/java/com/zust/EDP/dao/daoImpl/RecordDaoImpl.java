package com.zust.EDP.dao.daoImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zust.EDP.dao.RecordDao;
import com.zust.EDP.entity.Trecord;

@Repository
public class RecordDaoImpl implements RecordDao {
	@Autowired
	private SessionFactory sessionFactory;

	public Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Trecord> findRecord_by_recordid(List<Integer> recordId) {
		// TODO Auto-generated method stub
		Set<Trecord> set = new HashSet<Trecord>();
		for (int i = 0; i < recordId.size(); i++) {
			String sql = "from Trecord as record where record.recordId='" + recordId.get(i) + "'";
			List<Trecord> list = getCurrentSession().createQuery(sql).list();
			set.add(list.get(0));
		}
		return set;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Trecord> find_by_userId(Integer userId) {
		// TODO Auto-generated method stub
		System.out.println("bbbbbbbbb");
		String sql = "select r from Trecord r left join r.user_sended_id u where r.recordState=0 and u.userId = '"
				+ userId + "'group by r.user_sender_id";
		System.out.println("Trecord的size" + getCurrentSession().createQuery(sql).list().size());
		return getCurrentSession().createQuery(sql).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Trecord> findrecord(Integer userId1, Integer userId2) {
		// TODO Auto-generated method stub
		System.out.println("cccccccc");
		String sql = "select r from Trecord r left join r.user_sended_id u where r.recordState=0 and r.user_sender_id='"
				+ userId2 + "' and u.userId = " + userId1 + " order by r.recordDate";
		return getCurrentSession().createQuery(sql).list();
	}

	// 将未读信息变为已读信息
	@Override
	public void chang_record_state(List<Trecord> list) {
		// TODO Auto-generated method stub
		for (int i = 0; i < list.size(); i++) {
			String sql = "select r from Trecord r where r.recordId=" + list.get(i).getRecordId();
			Trecord record = (Trecord) getCurrentSession().createQuery(sql).list().get(0);
			record.setRecordState(1);
		}
	}

}
