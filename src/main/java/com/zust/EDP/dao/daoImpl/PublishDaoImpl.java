package com.zust.EDP.dao.daoImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zust.EDP.dao.PublishDao;
import com.zust.EDP.entity.Texpress;
import com.zust.EDP.entity.Tpublish;

@Repository
public class PublishDaoImpl implements PublishDao {
	@Autowired
	private SessionFactory sessionFactory;

	public Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public void savePublish(Tpublish publish) {
		// TODO Auto-generated method stub
		getCurrentSession().save(publish);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Texpress> findPublish(int page, String address, int limit, Integer userId) {
		// TODO Auto-generated method stub
		String sql = "select e from Texpress e left join e.publishId p where p.state=2 and e.address like '%" + address
				+ "%' and p.requirement<=" + limit;
		if(address.equals(" "))
			address="";
		System.out.println("pubishaddress="+address);
		if(userId!=null) {
			sql = "select e from Texpress e left join e.publishId p where p.state=2 and e.address like '%" + address
					+ "%' and p.requirement<=" + limit+" and p.user_publisher_id!='"+userId+"'";
		}
		Query query = getCurrentSession().createQuery(sql);
		query.setFirstResult((page - 1) * 4);
		query.setMaxResults(4);
		System.out.println("页数2：" + page);
		System.out.println("-----------------------------------------");
		List<Texpress> list = query.list();
		for (Texpress texpress : list) {
			System.out.println("publishId:" + texpress.getPublishId().getPublishId());
		}
		System.out.println("-----------------------------------------");
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Tpublish findPublish_by_Id(Integer publishId) {
		// TODO Auto-generated method stub
		String sql = "from Tpublish as publish where publish.publishId = " + publishId;
		List<Tpublish> list = getCurrentSession().createQuery(sql).list();
		return list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Tpublish> findPublish_by_Id(List<Integer> publishId) {
		// TODO Auto-generated method stub
		Set<Tpublish> set = new HashSet<Tpublish>();
		for (int i = 0; i < publishId.size(); i++) {
			String sql = "from Tpublish as publish where publish.publishId='" + publishId.get(i) + "'";
			List<Tpublish> list = getCurrentSession().createQuery(sql).list();
			set.add(list.get(0));
		}
		return set;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Texpress> find_by_id(Integer userId) {
		// TODO Auto-generated method stub
		String sql = "select e from Texpress e left join e.publishId p left join p.user_publisher_id u where p.state=1 and u.userId="
				+ userId;
		return getCurrentSession().createQuery(sql).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Texpress> findmytakepublih(Integer userId) {
		// TODO Auto-generated method stub
		String sql = "select e from Texpress e left join e.publishId p,Tmessage m left join m.passivePer u where m.msgType=1 and p.fromNum = m.fromNum and u.userId="
				+ userId;
		return getCurrentSession().createQuery(sql).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Texpress> findmyputpublish(Integer userId) {
		// TODO Auto-generated method stub
		String sql = "select e from Texpress e left join e.publishId p left join p.user_publisher_id u where p.state=2 and u.userId="
				+ userId; 
		return getCurrentSession().createQuery(sql).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Texpress> findmytakenpublish(Integer userId) {
		// TODO Auto-generated method stub
		String sql = "select e from Texpress e left join e.publishId p left join p.user_publisher_id u where p.state=3 and u.userId="
				+ userId;
		System.out.println("userId="+userId);
		return getCurrentSession().createQuery(sql).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public void updatestate(Integer PublishId,int state) {
		// TODO Auto-generated method stub
		String sql = "select p from Tpublish p where p.publishId="+PublishId;
		List<Tpublish> list  = getCurrentSession().createQuery(sql).list();
		Tpublish pTpublish = list.get(0);
		pTpublish.setState(state);
		
	}


}
