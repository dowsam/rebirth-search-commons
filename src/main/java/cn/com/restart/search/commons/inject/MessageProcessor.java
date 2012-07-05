/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons MessageProcessor.java 2012-3-29 15:15:13 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject;

import cn.com.restart.search.commons.inject.internal.Errors;
import cn.com.restart.search.commons.inject.spi.Message;


/**
 * The Class MessageProcessor.
 *
 * @author l.xue.nong
 */
class MessageProcessor extends AbstractProcessor {

	

	
	/**
	 * Instantiates a new message processor.
	 *
	 * @param errors the errors
	 */
	MessageProcessor(Errors errors) {
		super(errors);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.AbstractProcessor#visit(cn.com.summall.search.commons.inject.spi.Message)
	 */
	@Override
	public Boolean visit(Message message) {
		
		
		
		
		
		
		

		errors.addMessage(message);
		return true;
	}

	
	/**
	 * Gets the root message.
	 *
	 * @param t the t
	 * @return the root message
	 */
	public static String getRootMessage(Throwable t) {
		Throwable cause = t.getCause();
		return cause == null ? t.toString() : getRootMessage(cause);
	}
}
