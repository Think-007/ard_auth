package com.thinker.video.domain;

import java.io.Serializable;

/**
 * ard广播音频实体类
 * 
 * @author lipengfeia
 *
 */
public class ArdVideo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 电台名称
	private String name;

	// 电台类型
	private String type;
	// 电台所属地
	private String address;
	// 电台地址
	private String videoUrl;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	@Override
	public String toString() {
		return "ArdVideo [name=" + name + ", type=" + type + ", address="
				+ address + ", videoUrl=" + videoUrl + "]";
	}

}
