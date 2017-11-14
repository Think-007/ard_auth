/*      						
 * Copyright 2010 Beijing Xinwei, Inc. All rights reserved.
 * 
 * History:
 * ------------------------------------------------------------------------------
 * Date    	|  Who  		|  What  
 * 2017年4月6日	| lipengfeia 	| 	create the file                       
 */

package com.thinker.util;

import org.slf4j.Logger;

/**
 * 
 * 类简要描述
 * 
 * <p>
 * 类详细描述
 * </p>
 * 
 * @author lipengfeia
 * 
 */

public final class ArdLog {

	private static final String HEAD_INFO = "ARD--------> :";

	private static final String MID_INFO = "   obj===:";

	private ArdLog() {

	}

	public static void info(Logger logger, String methodName, String mainID,
			Object obj) {

		logger.info(HEAD_INFO + methodName + " " + mainID + MID_INFO + obj);

	}

	public static void debug(Logger logger, String methodName, String mainID,
			Object obj) {

		logger.debug(HEAD_INFO + methodName + " " + mainID + MID_INFO + obj);

	}

	public static void error(Logger logger, String methodName, String mainID,
			Object obj, Throwable t) {

		logger.error(HEAD_INFO + methodName + " " + mainID + MID_INFO + obj, t);

	}

}
