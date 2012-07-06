/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons NullOutputException.java 2012-3-29 15:15:09 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject.internal;


/**
 * The Class NullOutputException.
 *
 * @author l.xue.nong
 */
class NullOutputException extends NullPointerException {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2032226431980380996L;

	
	/**
	 * Instantiates a new null output exception.
	 *
	 * @param s the s
	 */
	public NullOutputException(String s) {
		super(s);
	}
}
