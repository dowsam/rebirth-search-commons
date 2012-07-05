/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons AsynchronousComputationException.java 2012-3-29 15:15:13 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.internal;


/**
 * The Class AsynchronousComputationException.
 *
 * @author l.xue.nong
 */
public class AsynchronousComputationException extends ComputationException {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4511195185616179160L;

	
	/**
	 * Instantiates a new asynchronous computation exception.
	 *
	 * @param cause the cause
	 */
	public AsynchronousComputationException(Throwable cause) {
		super(cause);
	}
}
