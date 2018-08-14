package com.zust.EDP.dto;

public class Message {
	private Integer messageId;
	private Integer msgType;
	private String fromNum;

	// 与Tuser关联
	private Integer passivePeruserid;// 接收人id
	private String passivePerusername;// 接收人姓名
	private Integer pubuserid;// 发布人id
	private String pubusername;// 发布人姓名
	private String pubuserimage;// 发布人头像
	private String efromNum;
	private Integer supportId;

	// 与Texpress关联
	private Integer expressId;
	private String type;// 类型
	private Integer size;// 大小
	private String address;
	private String company;//
	private String code;//
	private String tip;
	// private Double longitude;//经度
	// private Double latitude;//维度

	public Integer getExpressId() {
		return expressId;
	}

	public void setExpressId(Integer expressId) {
		this.expressId = expressId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public String getFromNum() {
		return fromNum;
	}

	public void setFromNum(String fromNum) {
		this.fromNum = fromNum;
	}

	public Integer getPassivePeruserid() {
		return passivePeruserid;
	}

	public void setPassivePeruserid(Integer passivePeruserid) {
		this.passivePeruserid = passivePeruserid;
	}

	public String getPassivePerusername() {
		return passivePerusername;
	}

	public void setPassivePerusername(String passivePerusername) {
		this.passivePerusername = passivePerusername;
	}

	public Integer getPubuserid() {
		return pubuserid;
	}

	public void setPubuserid(Integer pubuserid) {
		this.pubuserid = pubuserid;
	}

	public String getPubusername() {
		return pubusername;
	}

	public void setPubusername(String pubusername) {
		this.pubusername = pubusername;
	}

	public String getPubuserimage() {
		return pubuserimage;
	}

	public void setPubuserimage(String pubuserimage) {
		this.pubuserimage = pubuserimage;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getEfromNum() {
		return efromNum;
	}

	public void setEfromNum(String efromNum) {
		this.efromNum = efromNum;
	}

	public Integer getSupportId() {
		return supportId;
	}

	public void setSupportId(Integer supportId) {
		this.supportId = supportId;
	}
	

}
