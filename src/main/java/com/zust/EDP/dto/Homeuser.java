package com.zust.EDP.dto;

public class Homeuser {
	private Integer UserId;
	private String name;
	private String tel;
	private String image;
	private Double creditLevel;
	private int count;

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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
