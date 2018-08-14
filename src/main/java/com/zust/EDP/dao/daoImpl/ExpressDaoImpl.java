package com.zust.EDP.dao.daoImpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zust.EDP.dao.ExpressDao;
import com.zust.EDP.entity.Texpress;

@Repository
public class ExpressDaoImpl implements ExpressDao {
	@Autowired
	private SessionFactory sessionFactory;

	public Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public void saveExpress(Texpress express) {
		// TODO Auto-generated method stub
		getCurrentSession().save(express);
	}

	@SuppressWarnings("unchecked")
	public List<Texpress> findexpress_by_companyandcode(String company, String code) {
		// TODO Auto-generated method stub
		String sql = "from Texpress as express where express.company='" + company + "' and express.code='" + code + "'";
		return getCurrentSession().createQuery(sql).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Texpress findexpress_by_id(Integer expressId) {
		// TODO Auto-generated method stub
		String sql = "from Texpress as express where express.expressId='" + expressId;
		List<Texpress> list = getCurrentSession().createQuery(sql).list();
		return list.get(0);
	}

}
