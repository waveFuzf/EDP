package com.zust.EDP.entity;

import javax.persistence.*;

@Entity
@Table(name = "express")
public class Texpress {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer expressId;
	@Column
	private String type;
	@Column
	private Integer size;
	@Column
	private String address;
	@Column
	private String company;
	@Column
	private String code;
	@Column
	private String tip;
	@Column
	private Double longitude;
	@Column
	private Double latitude;
	// 与Tpublish关联
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "publishId")
	private Tpublish publishId;

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

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
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

	public Tpublish getPublishId() {
		return publishId;
	}

	public void setPublishId(Tpublish publishId) {
		this.publishId = publishId;
	}
}
