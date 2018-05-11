package com.thinker.video.domain;

import java.io.Serializable;

public class ArdAdvertiseMent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 图片地址
	private String picUrl;
	// 广告页面地址
	private String advUrl;

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getAdvUrl() {
		return advUrl;
	}

	public void setAdvUrl(String advUrl) {
		this.advUrl = advUrl;
	}

	@Override
	public String toString() {
		return "ArdAdvertiseMent [picUrl=" + picUrl + ", advUrl=" + advUrl
				+ "]";
	}

}
