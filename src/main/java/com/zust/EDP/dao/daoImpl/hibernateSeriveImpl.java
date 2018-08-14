package com.zust.EDP.dao.daoImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zust.EDP.dao.hibernateService;
import com.zust.EDP.entity.Tuser;

@Repository
@Transactional
public class hibernateSeriveImpl implements hibernateService {
	@Autowired
	private SessionFactory sessionFactory;

	public Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public void save(Tuser user) {
		// TODO Auto-generated method stub
		getCurrentSession().save(user);
	}

}
