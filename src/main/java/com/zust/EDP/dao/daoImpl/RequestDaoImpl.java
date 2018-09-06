package com.zust.EDP.dao.daoImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zust.EDP.dao.RequestDao;
import com.zust.EDP.entity.Texpress;
import com.zust.EDP.entity.Trequest;

@Repository
public class RequestDaoImpl implements RequestDao {
	@Autowired
	private SessionFactory sessionFactory;

	public Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public void saveRequest(Trequest request) {
		// TODO Auto-generated method stub
		getCurrentSession().save(request);

	}

	@SuppressWarnings("unchecked")
	public List<Trequest> findRequest(int page, String address, int limit, Integer userId) {
		// TODO Auto-generated method stub
		if(address.equals(" "))
			address="";
		System.out.println("requestaddress="+address);
		String sql = "select r from Trequest r left join r.user_requester_id u where r.address like '%" + address
				+ "%' and u.creditLevel<='" + limit + "' and u.id!='"+userId+"'";
		Query query = getCurrentSession().createQuery(sql);
		query.setFirstResult((page - 1) * 4);
		query.setMaxResults(4);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Trequest> findRequest_by_id(List<Integer> requestId) {
		// TODO Auto-generated method stub
		Set<Trequest> set = new HashSet<Trequest>();
		for (int i = 0; i < requestId.size(); i++) {
			String sql = "from Trequest as request where request.requestId='" + requestId.get(i) + "'";
			List<Trequest> list = getCurrentSession().createQuery(sql).list();
			set.add(list.get(0));
		}
		return set;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Texpress> findmytakerequest(Integer userId) {
		// TODO Auto-generated method stub
		String sql = "select e from Trequest r left join r.user_requester_id u,Textra ee left join ee.express e,Tmessage m where m.msgType=2 and r.fromNum=m.fromNum and ee.fromNum=m.fromNum and u.userId="+userId;
		return getCurrentSession().createQuery(sql).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Texpress> findmytakenrequest(Integer userId) {
		// TODO Auto-generated method stub
		String sql = "select e from Textra ee left join ee.express e,Tmessage m left join m.passivePer u where m.fromNum like 'ES%' and ee.fromNum=m.fromNum and u.userId="
				+ userId;
		return getCurrentSession().createQuery(sql).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Trequest> findByRequest(int personId) {
		// TODO Auto-generated method stub
		String hql="from Trequest e where e.user_requester_id.userId="+personId;
		List<Trequest> list=getCurrentSession().createQuery(hql).list();
		return list;
	}

}
