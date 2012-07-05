/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Provider.java 2012-3-29 15:15:16 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject;


/**
 * The Interface Provider.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
public interface Provider<T> {

	
	/**
	 * Gets the.
	 *
	 * @return the t
	 */
	T get();
}
