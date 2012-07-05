/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ErrorHandler.java 2012-3-29 15:15:20 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.internal;

import cn.com.restart.search.commons.inject.spi.Message;


/**
 * The Interface ErrorHandler.
 *
 * @author l.xue.nong
 */
public interface ErrorHandler {

	
	/**
	 * Handle.
	 *
	 * @param source the source
	 * @param errors the errors
	 */
	void handle(Object source, Errors errors);

	
	/**
	 * Handle.
	 *
	 * @param message the message
	 */
	void handle(Message message);
}