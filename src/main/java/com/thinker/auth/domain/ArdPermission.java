package com.thinker.auth.domain;

import java.io.Serializable;

/**
 * 权限模型
 * @author lipengfeia
 *
 */
public class ArdPermission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 资源id
	private int sourceId;

	// 资源路径
	private String sourceURL;

	// 路径描述
	private String sourceDesc;

	public int getSourceId() {
		return sourceId;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceURL() {
		return sourceURL;
	}

	public void setSourceURL(String sourceURL) {
		this.sourceURL = sourceURL;
	}

	public String getSourceDesc() {
		return sourceDesc;
	}

	public void setSourceDesc(String sourceDesc) {
		this.sourceDesc = sourceDesc;
	}

	@Override
	public String toString() {
		return "ArdPermission [sourceId=" + sourceId + ", sourceURL="
				+ sourceURL + ", sourceDesc=" + sourceDesc + "]";
	}

}
