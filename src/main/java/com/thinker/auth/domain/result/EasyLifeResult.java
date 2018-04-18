package com.thinker.auth.domain.result;

import java.util.List;

import com.thinker.auth.domain.ArdType;
import com.thinker.easylife.domain.EasyLife;

public class EasyLifeResult {

	// 便民应用列表
	private List<EasyLife> easyLifeList;
	// 应用类型列表
	private List<ArdType> typeList;

	// 最近使用列表
	private List<EasyLife> recentEasyLifeList;

	public List<EasyLife> getEasyLifeList() {
		return easyLifeList;
	}

	public void setEasyLifeList(List<EasyLife> easyLifeList) {
		this.easyLifeList = easyLifeList;
	}

	public List<ArdType> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<ArdType> typeList) {
		this.typeList = typeList;
	}

	public List<EasyLife> getRecentEasyLifeList() {
		return recentEasyLifeList;
	}

	public void setRecentEasyLifeList(List<EasyLife> recentEasyLifeList) {
		this.recentEasyLifeList = recentEasyLifeList;
	}

	@Override
	public String toString() {
		return "EasyLifeResult [easyLifeList=" + easyLifeList + ", typeList="
				+ typeList + ", recentEasyLifeList=" + recentEasyLifeList + "]";
	}

}
