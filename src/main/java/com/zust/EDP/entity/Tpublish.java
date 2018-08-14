package com.zust.EDP.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "publish")
public class Tpublish {
	@Id
	@GeneratedValue
	private Integer publishId;
	@Column(name = "publish_date")
	private Date publishDate;
	@Column
	private double requirement;
	@Column
	private Integer state;
	@Column(name = "take_date")
	private Date takeDate;
	@Column
	private Integer integral;
	private String fromNum;

	// 与Tuser关联
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "publisher_id")
	private Tuser user_publisher_id;

	// 与Texpress关联
	// @OneToOne(cascade = CascadeType.ALL)
	// @JoinColumn(name = "publish_id")
	// private Texpress publish_id;

	public Integer getPublishId() {
		return publishId;
	}

	public void setPublishId(Integer publishId) {
		this.publishId = publishId;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public double getRequirement() {
		return requirement;
	}

	public void setRequirement(double requirement) {
		this.requirement = requirement;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getTakeDate() {
		return takeDate;
	}

	public void setTakeDate(Date takeDate) {
		this.takeDate = takeDate;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Tuser getUser_publisher_id() {
		return user_publisher_id;
	}

	public void setUser_publisher_id(Tuser user_publisher_id) {
		this.user_publisher_id = user_publisher_id;
	}

	// public Texpress getPublish_id() {
	// return publish_id;
	// }
	//
	// public void setPublish_id(Texpress publish_id) {
	// this.publish_id = publish_id;
	// }

	public String getFromNum() {
		return fromNum;
	}

	public void setFromNum(String fromNum) {
		this.fromNum = fromNum;
	}

}
