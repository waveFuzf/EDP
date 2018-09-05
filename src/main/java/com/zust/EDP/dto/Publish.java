package com.zust.EDP.dto;

import java.math.BigDecimal;

public class Publish {
	private Integer publishId;
	private String publishDate;
	private double requirement;
	private Integer state;
	private String takeDate;// 截止日期
	private Integer integral;// 积分
	private String fromNum;
	private BigDecimal distance;
	private String orderdate;

	// 与Tuser关联
	private String publisherusername;
	private Integer publisheruserId;
	// private Double creditLevel;
	private String publisheruserimage;

	// 与Texpress关联
	// private Integer expressId;
	private String type;// 类型
	private Integer size;// 大小
	private String address;
	private String company;//
	private String code;//
	private String tip;
	private Double longitude;//经度
	private Double latitude;//维度

	public String getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Integer getPublishId() {
		return publishId;
	}

	public void setPublishId(Integer publishId) {
		this.publishId = publishId;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public double getRequirement() {
		return requirement;
	}

	public void setRequirement(double requirement) {
		this.requirement = requirement;
	}

	public String getTakeDate() {
		return takeDate;
	}

	public void setTakeDate(String takeDate) {
		this.takeDate = takeDate;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
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

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getFromNum() {
		return fromNum;
	}

	public void setFromNum(String fromNum) {
		this.fromNum = fromNum;
	}

	public String getPublisherusername() {
		return publisherusername;
	}

	public void setPublisherusername(String publisherusername) {
		this.publisherusername = publisherusername;
	}

	public Integer getPublisheruserId() {
		return publisheruserId;
	}

	public void setPublisheruserId(Integer publisheruserId) {
		this.publisheruserId = publisheruserId;
	}

	public BigDecimal getDistance() {
		return distance;
	}

	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}

	public String getPublisheruserimage() {
		return publisheruserimage;
	}

	public void setPublisheruserimage(String publisheruserimage) {
		this.publisheruserimage = publisheruserimage;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}
