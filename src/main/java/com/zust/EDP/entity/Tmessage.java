package com.zust.EDP.entity;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "message")
public class Tmessage {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer messageId;
	@Column
	private Integer msgType;
	@Column
	private String fromNum;
	@Column
	private Date orderDate;
	@Column
	private Integer state;
	// 与Tuser关联
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "passivePer")
	private Tuser passivePer;

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

	public Tuser getPassivePer() {
		return passivePer;
	}

	public void setPassivePer(Tuser passivePer) {
		this.passivePer = passivePer;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
}
