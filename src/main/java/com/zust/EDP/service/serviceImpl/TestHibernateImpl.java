package com.zust.EDP.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zust.EDP.dao.hibernateService;
import com.zust.EDP.entity.Tuser;
import com.zust.EDP.service.TestHibernate;

@Service
public class TestHibernateImpl implements TestHibernate {
	@Autowired
	private hibernateService hibernateService;

	public void save(Tuser user) {
		// TODO Auto-generated method stub
		hibernateService.save(user);
	}

}
