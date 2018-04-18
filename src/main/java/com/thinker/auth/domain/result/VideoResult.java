package com.thinker.auth.domain.result;

import java.util.List;

import com.thinker.auth.domain.ArdType;
import com.thinker.video.domain.ArdVideo;

public class VideoResult {

	private List<ArdVideo> videoList;

	private List<ArdType> typeList;

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

	@Override
	public String toString() {
		return "VideoResult [videoList=" + videoList + ", typeList=" + typeList
				+ "]";
	}

}
