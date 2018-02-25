package com.thinker.easylife.domain;

import java.io.Serializable;

/**
 * 便民功能的地址信息
 * 
 * @author lipengfeia
 *
 */
public class EasyLife implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 服务名称
	private String name;

	// 服务所属类型
	private String type;

	// 服务地址
	private String appUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	@Override
	public String toString() {
		return "EasyLife [name=" + name + ", type=" + type + ", appUrl="
				+ appUrl + "]";
	}

}
