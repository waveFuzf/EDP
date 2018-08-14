package com.zust.EDP.dto;

import java.util.ArrayList;
import java.util.List;

public class User {
	private Integer UserId;
	private String name;
	private String tel;
	private String password;
	private String address;
	private String image;
	private Double creditLevel;
	private Integer state;
	private Integer sex;
	private Integer integral;
	private Integer times;
	private int count;

	// 与Trequest关联
	private List<Integer> requesterid = new ArrayList<Integer>();

	// 与Trecord关联
	private List<Integer> recordid = new ArrayList<Integer>();
	private List<Integer> sendeduserid = new ArrayList<Integer>();
	private List<String> sendedusername = new ArrayList<String>();

	// 与Tevaluate关联
	private List<Integer> evaluatedid = new ArrayList<Integer>();
	private List<Integer> evaluateduserid = new ArrayList<Integer>();
	private List<String> evaluatedusername = new ArrayList<String>();
	// 与Tpublish关联
	private List<Integer> publishId = new ArrayList<Integer>();

	// 与Tmessage关联
	private List<Integer> messageId = new ArrayList<Integer>();

	public Integer getUserId() {
		return UserId;
	}

	public void setUserId(Integer userId) {
		UserId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Double getCreditLevel() {
		return creditLevel;
	}

	public void setCreditLevel(Double creditLevel) {
		this.creditLevel = creditLevel;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public List<Integer> getRequesterid() {
		return requesterid;
	}

	public void setRequesterid(List<Integer> requesterid) {
		this.requesterid = requesterid;
	}

	public List<Integer> getSendeduserid() {
		return sendeduserid;
	}

	public void setSendeduserid(List<Integer> sendeduserid) {
		this.sendeduserid = sendeduserid;
	}

	public List<Integer> getEvaluateduserid() {
		return evaluateduserid;
	}

	public void setEvaluateduserid(List<Integer> evaluateduserid) {
		this.evaluateduserid = evaluateduserid;
	}

	public List<Integer> getPublishId() {
		return publishId;
	}

	public void setPublishId(List<Integer> publishId) {
		this.publishId = publishId;
	}

	public List<Integer> getMessageId() {
		return messageId;
	}

	public void setMessageId(List<Integer> messageId) {
		this.messageId = messageId;
	}

	public List<Integer> getRecordid() {
		return recordid;
	}

	public void setRecordid(List<Integer> recordid) {
		this.recordid = recordid;
	}

	public List<String> getSendedusername() {
		return sendedusername;
	}

	public void setSendedusername(List<String> sendedusername) {
		this.sendedusername = sendedusername;
	}

	public List<String> getEvaluatedusername() {
		return evaluatedusername;
	}

	public void setEvaluatedusername(List<String> evaluatedusername) {
		this.evaluatedusername = evaluatedusername;
	}

	public List<Integer> getEvaluatedid() {
		return evaluatedid;
	}

	public void setEvaluatedid(List<Integer> evaluatedid) {
		this.evaluatedid = evaluatedid;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
