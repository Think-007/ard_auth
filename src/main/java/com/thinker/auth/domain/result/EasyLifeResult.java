package com.thinker.auth.domain.result;

import java.util.List;

import com.thinker.auth.domain.ArdType;
import com.thinker.easylife.domain.EasyLife;

public class EasyLifeResult {

	private List<EasyLife> easyLifeList;

	private List<ArdType> typeList;

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

	@Override
	public String toString() {
		return "EasyLifeResult [easyLifeList=" + easyLifeList + ", typeList="
				+ typeList + "]";
	}

}
