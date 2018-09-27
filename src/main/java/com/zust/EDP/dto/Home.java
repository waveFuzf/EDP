package com.zust.EDP.dto;

import com.zust.EDP.entity.Tmessage;

import java.util.ArrayList;
import java.util.List;

public class Home {
	private List<Homeuser> user = new ArrayList<Homeuser>();
	private List<Tmessage> message = new ArrayList<Tmessage>();
	private List<Publish> publish = new ArrayList<Publish>();
	private List<Request> request = new ArrayList<Request>();
	private List<Unread> Unread = new ArrayList<Unread>();

	public List<Homeuser> getUser() {
		return user;
	}

	public void setUser(List<Homeuser> user) {
		this.user = user;
	}

	public List<Tmessage> getMessage() {
		return message;
	}

	public void setMessage(List<Tmessage> message) {
		this.message = message;
	}

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

	public List<Unread> getUnread() {
		return Unread;
	}

	public void setUnread(List<Unread> unread) {
		Unread = unread;
	}

}
