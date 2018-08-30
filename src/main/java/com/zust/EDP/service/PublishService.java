package com.zust.EDP.service;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.zust.EDP.dto.Dpublish;
import com.zust.EDP.dto.Publish;
import com.zust.EDP.entity.Texpress;
import com.zust.EDP.entity.Tpublish;

public interface PublishService {
	public String doRegisterdoissueAppointment(Dpublish publish, HttpSession session);

	public String temporary(Dpublish publish, HttpSession session);

	public List<Publish> selectAddress(String select, int limit, Integer userId);

	public Publish Tpublish_Publish(Texpress express);

	public Tpublish Publish_Tpublish(Publish publish, HttpSession session);

	public List<Publish> putdown(int page, String address, int limit, Integer userId);

	public void savelalo(double a, double b);

	public BigDecimal distance(Texpress express, Double longitude, Double latitude);

	public List<Publish> findpublishunsave(HttpSession session);

	public String deletepublish(Integer publishId);

}
