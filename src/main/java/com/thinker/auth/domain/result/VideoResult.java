package com.thinker.auth.domain.result;

import java.util.List;

import com.thinker.auth.domain.ArdType;
import com.thinker.video.domain.ArdAdvertiseMent;
import com.thinker.video.domain.ArdVideo;

public class VideoResult {

	// 视听列表
	private List<ArdVideo> videoList;

	// 视听类型列表
	private List<ArdType> typeList;

	// 广告列表
	private List<ArdAdvertiseMent> advList;

	public List<ArdVideo> getVideoList() {
		return videoList;
	}

	public void setVideoList(List<ArdVideo> videoList) {
		this.videoList = videoList;
	}

	public List<ArdType> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<ArdType> typeList) {
		this.typeList = typeList;
	}

	public List<ArdAdvertiseMent> getAdvList() {
		return advList;
	}

	public void setAdvList(List<ArdAdvertiseMent> advList) {
		this.advList = advList;
	}

	@Override
	public String toString() {
		return "VideoResult [videoList=" + videoList + ", typeList=" + typeList
				+ ", advList=" + advList + "]";
	}

}
