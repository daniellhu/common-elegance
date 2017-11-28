package com.yonyou.cloud.common.vo.user;

import java.io.Serializable;
import java.util.Date;

/**
 * @author BENJAMIN
 *
 */
public class UserInfo implements Serializable {
	
	private static final long serialVersionUID = 4085293276820622295L;
	
	public String id;
	public String username;
	public String password;
	public String name;
	private String description;
	private String telPhone;

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	private Date updTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

}
