package com.zust.EDP.dto;

import java.util.ArrayList;
import java.util.List;

public class Home {
	private List<Homeuser> user = new ArrayList<Homeuser>();
	private List<Message> message = new ArrayList<Message>();
	private List<Publish> publish = new ArrayList<Publish>();
	private List<Request> request = new ArrayList<Request>();
	private List<Unread> Unread = new ArrayList<Unread>();

	public List<Homeuser> getUser() {
		return user;
	}

	public void setUser(List<Homeuser> user) {
		this.user = user;
	}

	public List<Message> getMessage() {
		return message;
	}

	public void setMessage(List<Message> message) {
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
