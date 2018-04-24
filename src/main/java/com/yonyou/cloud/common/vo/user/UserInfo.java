package com.yonyou.cloud.common.vo.user;

import java.io.Serializable;
import java.util.Map;

/**
 * 用户信息
 * 
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
	private String dealerCode;
	private String dealerName;
	
	
	private Map<String, String> attrMap;


	public Map<String, String> getAttrMap() {
		return attrMap;
	}

	public void setAttrMap(Map<String, String> attrMap) {
		this.attrMap = attrMap;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

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

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name
				+ ", description=" + description + ", telPhone=" + telPhone + ", dealerCode=" + dealerCode
				+ ", dealerName=" + dealerName + ", attrMap=" + attrMap + "]";
	}
	

}
