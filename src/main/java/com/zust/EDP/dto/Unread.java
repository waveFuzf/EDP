package com.zust.EDP.dto;

import java.util.ArrayList;
import java.util.List;

public class Unread {
	private String time;
	private Integer fromuserId;
	private String fromusername;
	private String fromuserimage;
	private List<String> record = new ArrayList<String>();

	public Integer getFromuserId() {
		return fromuserId;
	}

	public void setFromuserId(Integer fromuserId) {
		this.fromuserId = fromuserId;
	}

	public String getFromusername() {
		return fromusername;
	}

	public void setFromusername(String fromusername) {
		this.fromusername = fromusername;
	}

	public String getFromuserimage() {
		return fromuserimage;
	}

	public void setFromuserimage(String fromuserimage) {
		this.fromuserimage = fromuserimage;
	}

	public List<String> getRecord() {
		return record;
	}

	public void setRecord(List<String> record) {
		this.record = record;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
