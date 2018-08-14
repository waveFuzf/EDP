package com.zust.EDP.dto;

public class Request {
	// private Integer requestId;
	private String freeDate;
	private String address;
	private String introduce;
	private String requestDate;
	private String fromNum;
	// private String fromNum;

	private String requesterusername;// session中找
	private Integer requesteruserId;
	private String requesteruserImage;
	private Double creditLevel;

	public String getFreeDate() {
		return freeDate;
	}

	public void setFreeDate(String freeDate) {
		this.freeDate = freeDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getRequesterusername() {
		return requesterusername;
	}

	public void setRequesterusername(String requesterusername) {
		this.requesterusername = requesterusername;
	}

	public Integer getRequesteruserId() {
		return requesteruserId;
	}

	public void setRequesteruserId(Integer requesteruserId) {
		this.requesteruserId = requesteruserId;
	}

	public Double getCreditLevel() {
		return creditLevel;
	}

	public void setCreditLevel(Double creditLevel) {
		this.creditLevel = creditLevel;
	}

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public String getFromNum() {
		return fromNum;
	}

	public void setFromNum(String fromNum) {
		this.fromNum = fromNum;
	}

	public String getRequesteruserImage() {
		return requesteruserImage;
	}

	public void setRequesteruserImage(String requesteruserImage) {
		this.requesteruserImage = requesteruserImage;
	}

}
