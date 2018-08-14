package com.zust.EDP.dto;

import java.sql.Date;

public class Record {
	private Integer recordId;
	private String record;
	private Date recordDate;
	private Integer recordState;

	// 与Tuser关联
	private Integer senderuserid;
	private String senderusername;

	private Integer sendeduserid;
	private String sendedusername;

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

	public Integer getSenderuserid() {
		return senderuserid;
	}

	public void setSenderuserid(Integer senderuserid) {
		this.senderuserid = senderuserid;
	}

	public String getSenderusername() {
		return senderusername;
	}

	public void setSenderusername(String senderusername) {
		this.senderusername = senderusername;
	}

	public Integer getSendeduserid() {
		return sendeduserid;
	}

	public void setSendeduserid(Integer sendeduserid) {
		this.sendeduserid = sendeduserid;
	}

	public String getSendedusername() {
		return sendedusername;
	}

	public void setSendedusername(String sendedusername) {
		this.sendedusername = sendedusername;
	}

}
