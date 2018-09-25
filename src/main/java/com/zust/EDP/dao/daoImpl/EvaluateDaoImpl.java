package com.zust.EDP.dao.daoImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import com.zust.EDP.dao.EvaluateDao;
import com.zust.EDP.entity.Tevaluate;
@Repository
public class EvaluateDaoImpl implements EvaluateDao {
	@Autowired
	private SessionFactory sessionFactory;

	public Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Tevaluate> findevaluate_by_id(List<Integer> evaluateId) {
		// TODO Auto-generated method stub
		Set<Tevaluate> set = new HashSet<Tevaluate>();
		for (int i = 0; i < evaluateId.size(); i++) {
			String sql = "from Tevaluate as evaluate where evaluate.evaluateId='" + evaluateId.get(i) + "'";
			List<Tevaluate> list = getCurrentSession().createQuery(sql).list();
			set.add(list.get(0));
		}
		return set;
	}
	
	@Override
	public void saveTevaluate(Tevaluate evaluate) {
		// TODO Auto-generated method stub
		getCurrentSession().save(evaluate);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Tevaluate> findByEvaluateId(int personId) {
		// TODO Auto-generated method stub
		String hql="from Tevaluate e where e.user_evaluated_id.userId="+personId;
		List<Tevaluate> tevaluates=getCurrentSession().createQuery(hql).list();
		return tevaluates;
	}

}
