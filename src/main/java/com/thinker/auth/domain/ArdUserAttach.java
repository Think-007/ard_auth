package com.thinker.auth.domain;

import java.io.Serializable;

public class ArdUserAttach implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 用户id
	private double userId;
	// 用户电话
	private String telNum;
	// 缩略图地址
	private String thumbURL;
	// 头像地址
	private String headpicURL;

	public double getUserId() {
		return userId;
	}

	public void setUserId(double userId) {
		this.userId = userId;
	}

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public String getThumbURL() {
		return thumbURL;
	}

	public void setThumbURL(String thumbURL) {
		this.thumbURL = thumbURL;
	}

	public String getHeadpicURL() {
		return headpicURL;
	}

	public void setHeadpicURL(String headpicURL) {
		this.headpicURL = headpicURL;
	}

	@Override
	public String toString() {
		return "ArdUserAttach [userId=" + userId + ", telNum=" + telNum
				+ ", thumbURL=" + thumbURL + ", headpicURL=" + headpicURL + "]";
	}

}
