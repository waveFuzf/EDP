package com.zust.EDP.entity;

import javax.persistence.*;

@Entity
@Table(name = "extra")
public class Textra {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer extraId;
	@Column
	private String fromNum;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expressId")
	private Texpress express;

	public Integer getExtraId() {
		return extraId;
	}

	public void setExtraId(Integer extraId) {
		this.extraId = extraId;
	}

	public String getFromNum() {
		return fromNum;
	}

	public void setFromNum(String fromNum) {
		this.fromNum = fromNum;
	}

	public Texpress getExpress() {
		return express;
	}

	public void setExpress(Texpress express) {
		this.express = express;
	}

}
