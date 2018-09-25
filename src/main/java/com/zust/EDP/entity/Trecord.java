package com.zust.EDP.entity;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "record")
public class Trecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer recordId;
	@Column
	private String record;
	@Column(name = "record_date")
	private Date recordDate;
	@Column(name = "record_state")
	private Integer recordState;

	// 与Tuser关联
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id")
	private Tuser user_sender_id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sended_id")
	private Tuser user_sended_id;

	public Tuser getUser_sended_id() {
		return user_sended_id;
	}

	public void setUser_sended_id(Tuser user_sended_id) {
		this.user_sended_id = user_sended_id;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public Integer getRecordState() {
		return recordState;
	}

	public void setRecordState(Integer recordState) {
		this.recordState = recordState;
	}

	public Tuser getUser_sender_id() {
		return user_sender_id;
	}

	public void setUser_sender_id(Tuser user_sender_id) {
		this.user_sender_id = user_sender_id;
	}

}
