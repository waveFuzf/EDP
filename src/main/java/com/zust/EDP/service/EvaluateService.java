package com.zust.EDP.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.zust.EDP.dto.Eveluate;
import com.zust.EDP.entity.Tevaluate;

public interface EvaluateService {

	public Tevaluate Evaluate_Tevaluate(Eveluate evaluate, HttpSession session);

	public String evaluate(Eveluate evaluate, HttpSession session);
	public List<Map<String, Object>>  findByEvaluateId(int personId);

}
