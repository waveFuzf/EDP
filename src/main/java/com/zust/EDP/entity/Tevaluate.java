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
@Table(name = "evaluate")
public class Tevaluate {
	@Id
	@GeneratedValue
	private Integer evaluateId;
	@Column
	private String evaluate;
	@Column(name = "evaluate_date")
	private Date evaluateDate;
	@Column(name = "star_level")
	private Double starLevel;
	// 与Tuser关联
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "evaluater_id")
	private Tuser user_evaluater_id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "evaluated_id")
	private Tuser user_evaluated_id;

	public Integer getEvaluateId() {
		return evaluateId;
	}

	public void setEvaluateId(Integer evaluateId) {
		this.evaluateId = evaluateId;
	}

	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}

	public Date getEvaluateDate() {
		return evaluateDate;
	}

	public void setEvaluateDate(Date evaluateDate) {
		this.evaluateDate = evaluateDate;
	}

	public Double getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(Double starLevel) {
		this.starLevel = starLevel;
	}

	public Tuser getUser_evaluater_id() {
		return user_evaluater_id;
	}

	public void setUser_evaluater_id(Tuser user_evaluater_id) {
		this.user_evaluater_id = user_evaluater_id;
	}

	public Tuser getUser_evaluated_id() {
		return user_evaluated_id;
	}

	public void setUser_evaluated_id(Tuser user_evaluated_id) {
		this.user_evaluated_id = user_evaluated_id;
	}
}
