package com.zust.EDP.service;

import javax.servlet.http.HttpSession;

import com.zust.EDP.dto.Dpublish;
import com.zust.EDP.dto.Publish;
import com.zust.EDP.entity.Texpress;

public interface ExpressService {
	public Texpress Express_Texpress(Publish publish);

	public Texpress Dpublish_Texpress(Dpublish publish, HttpSession session);

}
