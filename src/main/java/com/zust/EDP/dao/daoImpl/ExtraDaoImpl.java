package com.zust.EDP.dao.daoImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zust.EDP.dao.ExtraDao;
import com.zust.EDP.entity.Textra;

@Repository
public class ExtraDaoImpl implements ExtraDao {
	@Autowired
	private SessionFactory sessionFactory;

	public Session getCurrentSession() {
		return this.sessionFactory.openSession();
	}

	@Override
	public void save(Textra textra) {
		// TODO Auto-generated method stub
		getCurrentSession().save(textra);
	}

}
