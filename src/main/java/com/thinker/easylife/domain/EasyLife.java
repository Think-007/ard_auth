package com.thinker.easylife.domain;

import java.io.Serializable;

/**
 * 便民功能的地址信息
 * 
 * @author lipengfeia
 *
 */
public class EasyLife implements Serializable, Comparable<EasyLife>, Cloneable {

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

	// 图标地址
	private String picUrl;

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

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	@Override
	public String toString() {
		return "EasyLife [name=" + name + ", type=" + type + ", appUrl="
				+ appUrl + ", picUrl=" + picUrl + "]";
	}

	@Override
	public int compareTo(EasyLife o) {

		return this.name.compareTo(o.getName());
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		EasyLife temp = new EasyLife();
		temp.setAppUrl(this.appUrl);
		temp.setName(this.name);
		temp.setPicUrl(this.picUrl);
		temp.setType(this.type);
		return temp;
	}

}
