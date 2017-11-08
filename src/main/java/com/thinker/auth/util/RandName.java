package com.thinker.auth.util;

public class RandName {

	public static final String randUserName() {

		StringBuilder sb = new StringBuilder();

		String headStr = "名气";
		String midStr = "或";
		String tailStr = "巨人";
		sb.append(headStr).append(midStr).append(tailStr);

		return sb.toString();

	}
}
