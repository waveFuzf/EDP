package com.zust.EDP.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zust.EDP.dao.EvaluateDao;
import com.zust.EDP.dao.PublishDao;
import com.zust.EDP.dao.UserDao;
import com.zust.EDP.dto.Eveluate;
import com.zust.EDP.entity.Tevaluate;
import com.zust.EDP.entity.Tuser;
import com.zust.EDP.service.EvaluateService;
import com.zust.EDP.util.Tools;

@Service
@Transactional
public class EvaluateServiceImpl implements EvaluateService{
	@Autowired
	private Tools tools; 
	@Autowired
	private UserDao userDao;
	@Autowired
	private EvaluateDao evaluateDao;
	@Autowired
	private PublishDao publishDao;

	@Override
	public Tevaluate Evaluate_Tevaluate(Eveluate evaluate, HttpSession session) {
		// TODO Auto-generated method stub
		Tuser u = new Tuser();
		u = (Tuser) session.getAttribute("userMessage");
		Tevaluate evaluate2 = new Tevaluate();
		evaluate2.setEvaluate(evaluate.getEvaluate());
		evaluate2.setEvaluateDate(tools.getNowTime());
		evaluate2.setStarLevel(evaluate.getStarLevel());
		//System.out.println("评价人id="+evaluate.getEvaluatedid());
		evaluate2.setUser_evaluated_id(userDao.findUser_by_Id(evaluate.getEvaluatedid()));
		evaluate2.setUser_evaluater_id(userDao.findUser_by_Id(u.getUserId()));
		return evaluate2;
	}

	@Override
	public String evaluate(Eveluate evaluate,HttpSession session) {
		// TODO Auto-generated method stub
		Tevaluate tevaluate = new Tevaluate();
		tevaluate = Evaluate_Tevaluate(evaluate,session);
		evaluateDao.saveTevaluate(tevaluate);
		
		double oldLevel=tevaluate.getUser_evaluated_id().getCreditLevel();
		
		double newLevel=evaluate.getStarLevel();
		
		double level=oldLevel*0.8+newLevel*0.2;
		
		if(level>5.0)
			level=5.0;
		int oldIntegral=tevaluate.getUser_evaluated_id().getIntegral();
		
		int newIntegral = publishDao.findPublish_by_Id(evaluate.getPublishId()).getIntegral();
		
		int integral=oldIntegral+newIntegral;
		
		Tuser tuser=new Tuser();
		tuser.setCreditLevel(level);
		tuser.setIntegral(integral);
		//修改积分
		userDao.updateuser(tevaluate.getUser_evaluated_id().getUserId(), tuser);
		//修改信誉等级
		userDao.updateuserLevel(tevaluate.getUser_evaluated_id().getUserId(), tuser);
		publishDao.updatestate(evaluate.getPublishId(), 4);
		return "true";
	}

	@Override
	public List<Map<String, Object>> findByEvaluateId(int personId) {
		// TODO Auto-generated method stub
		List<Tevaluate> tevaluates=evaluateDao.findByEvaluateId(personId);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		for (Tevaluate tevaluate : tevaluates) {
			Map<String, Object> mapp=new HashMap<String,Object>();
			mapp.put("evaluatedImage", tevaluate.getUser_evaluater_id().getImage());
			mapp.put("evaluatedName", tevaluate.getUser_evaluater_id().getName());
			mapp.put("evaluateDate", tools.formatTime(tevaluate.getEvaluateDate()));
			mapp.put("evaluateLevel", tevaluate.getStarLevel());
			mapp.put("evaluate", tevaluate.getEvaluate());
			list.add(mapp);
		}
		return list;
	}

}
