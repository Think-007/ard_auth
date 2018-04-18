package com.thinker.auth.domain;

import java.io.Serializable;

public class ArdType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 类型id
	private String typeId;
	// 类型名称
	private String typeName;
	// 该类型图片地址
	private String picUrl;

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public String toString() {
		return "ArdType [typeId=" + typeId + ", typeName=" + typeName
				+ ", picUrl=" + picUrl + "]";
	}

}
