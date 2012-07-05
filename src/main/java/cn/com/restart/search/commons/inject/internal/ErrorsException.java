/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ErrorsException.java 2012-3-29 15:15:20 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.internal;


/**
 * The Class ErrorsException.
 *
 * @author l.xue.nong
 */
public class ErrorsException extends Exception {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3181514182676999879L;
	
	
	/** The errors. */
	private final Errors errors;

	
	/**
	 * Instantiates a new errors exception.
	 *
	 * @param errors the errors
	 */
	public ErrorsException(Errors errors) {
		this.errors = errors;
	}

	
	/**
	 * Gets the errors.
	 *
	 * @return the errors
	 */
	public Errors getErrors() {
		return errors;
	}
}
