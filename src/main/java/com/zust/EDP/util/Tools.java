package com.zust.EDP.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class Tools {
	public JSONObject changeStringToJson(String string) {
		JSONObject json = new JSONObject(string);
		return json;
	}

	public Date getNowTime() {
		return new Date();
	}

	public String formatTime(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return simpleDateFormat.format(date);
	}

	public Date getDate(String takeDate) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		String a = takeDate.replace('T', ' ');
		System.out.println("时间："+a );
		try {
			// Fri Feb 24 00:00:00 CST 2012
			date = simpleDateFormat.parse(a);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 2012-02-24
		return date;
	}

	public String splitJson(Map<String, Object> map) {
		int size = 0;
		String string = "{";
		for (Entry<String, Object> vo : map.entrySet()) {
			if (size == map.size() - 1)
				string = string + "\"" + vo.getKey() + "\":\"" + vo.getValue() + "\"";
			else
				string = string + "\"" + vo.getKey() + "\":\"" + vo.getValue() + "\",";
			size++;
		}
		string = string + "}";
		return string;
	}

	public String makeFromNumByRandom(String type, int num) {
		String string = type;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		if (String.valueOf(num).length() == 1)
			string = string + simpleDateFormat.format(getNowTime()) + "00" + num;
		else if (String.valueOf(num).length() == 2)
			string = string + simpleDateFormat.format(getNowTime()) + "0" + num;
		else
			string = string + simpleDateFormat.format(getNowTime()) + num;
		return string;
	}
}
