package com.thinker.auth.dao;

import com.thinker.auth.domain.ArdUserAttach;

public interface ArdUserAttachMapper {

	/**
	 * 添加用户附加信息
	 * 
	 * @param userid
	 * @return
	 */
	public int insertUserAttach(ArdUserAttach ardUserAttach);

}
