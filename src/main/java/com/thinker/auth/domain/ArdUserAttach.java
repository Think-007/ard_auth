package com.thinker.auth.domain;

import java.io.Serializable;
import java.util.Date;

public class ArdUserAttach implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 用户id
	private String userId;
	// 用户电话(第三方id)
	private String telNum;
	// 缩略图地址
	private String thumbURL;
	// 头像地址
	private String headpicURL;

	// 创建时间
	private Date createTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "ArdUserAttach [userId=" + userId + ", telNum=" + telNum
				+ ", thumbURL=" + thumbURL + ", headpicURL=" + headpicURL
				+ ", createTime=" + createTime + "]";
	}

}
