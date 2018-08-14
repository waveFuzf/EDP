package com.zust.EDP.dto;

import java.util.ArrayList;
import java.util.List;

public class Mytake {
	private List<Publish> publish = new ArrayList<Publish>();
	private List<Request> request = new ArrayList<Request>();

	public List<Publish> getPublish() {
		return publish;
	}

	public void setPublish(List<Publish> publish) {
		this.publish = publish;
	}

	public List<Request> getRequest() {
		return request;
	}

	public void setRequest(List<Request> request) {
		this.request = request;
	}

}
