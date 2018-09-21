package com.zust.EDP.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class Tuser {
	@Id
	@GeneratedValue
	private Integer userId;
	@Column
	private String name;
	@Column
	private String tel;
	@Column
	private String password;
	@Column
	private String address;
	@Column
	private String image ="../avatar/defaultavatar.jpg";
	@Column(name = "credit_level")
	private Double creditLevel=2.0;
	@Column
	private Integer state=0;
	@Column
	private Integer sex=2;
	@Column
	private Integer integral=5;
	@Column
	private Integer times;
	// 与Trequest关联
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user_requester_id")
	private Set<Trequest> requester_id = new HashSet<Trequest>();

	// 与Trecord关联
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user_sender_id")
	private Set<Trecord> sender_id = new HashSet<Trecord>();
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user_sended_id")
	private Set<Trecord> sended_id = new HashSet<Trecord>();

	// 与Tevaluate关联
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user_evaluater_id")
	private Set<Tevaluate> evaluater_id = new HashSet<Tevaluate>();
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user_evaluated_id")
	private Set<Tevaluate> evaluated_id = new HashSet<Tevaluate>();

	// 与Tpublish关联
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user_publisher_id")
	private Set<Tpublish> publisher_id = new HashSet<Tpublish>();

	// 与Tmessage关联
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "passivePer")
	private Set<Tmessage> message_id = new HashSet<Tmessage>();

	// 与Ticard关联
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tidcard")
	private Tidcard tidcard;

	public Tidcard getTidcard() {
		return tidcard;
	}

	public void setTidcard(Tidcard tidcard) {
		this.tidcard = tidcard;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Set<Trequest> getRequester_id() {
		return requester_id;
	}

	public void setRequester_id(Set<Trequest> requester_id) {
		this.requester_id = requester_id;
	}

	public Set<Trecord> getSender_id() {
		return sender_id;
	}

	public void setSender_id(Set<Trecord> sender_id) {
		this.sender_id = sender_id;
	}

	public Set<Tevaluate> getEvaluater_id() {
		return evaluater_id;
	}

	public void setEvaluater_id(Set<Tevaluate> evaluater_id) {
		this.evaluater_id = evaluater_id;
	}

	public Set<Tevaluate> getEvaluated_id() {
		return evaluated_id;
	}

	public void setEvaluated_id(Set<Tevaluate> evaluated_id) {
		this.evaluated_id = evaluated_id;
	}

	public Set<Tpublish> getPublisher_id() {
		return publisher_id;
	}

	public void setPublisher_id(Set<Tpublish> publisher_id) {
		this.publisher_id = publisher_id;
	}

	public Set<Tmessage> getMessage_id() {
		return message_id;
	}

	public void setMessage_id(Set<Tmessage> message_id) {
		this.message_id = message_id;
	}

	public Set<Trecord> getSended_id() {
		return sended_id;
	}

	public void setSended_id(Set<Trecord> sended_id) {
		this.sended_id = sended_id;
	}

}
