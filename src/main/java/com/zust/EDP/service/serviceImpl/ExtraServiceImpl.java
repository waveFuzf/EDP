package com.zust.EDP.service.serviceImpl;

import javax.transaction.Transactional;

import com.zust.EDP.dao.ExtraDao;
import com.zust.EDP.entity.Texpress;
import com.zust.EDP.entity.Textra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zust.EDP.service.ExtraService;

@Service
@Transactional
public class ExtraServiceImpl implements ExtraService {
	@Autowired
	private ExtraDao extraDao;

	@Override
	public void save(String fromNum, int id) {
		// TODO Auto-generated method stub
		Texpress texpress = new Texpress();
		Textra textra = new Textra();
		texpress.setExpressId(id);
		textra.setFromNum(fromNum);
		textra.setExpress(texpress);
		extraDao.save(textra);
	}

}
