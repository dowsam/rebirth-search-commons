/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Initializable.java 2012-3-29 15:15:09 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject;

import cn.com.restart.search.commons.inject.internal.Errors;
import cn.com.restart.search.commons.inject.internal.ErrorsException;


/**
 * The Interface Initializable.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
interface Initializable<T> {

	
	/**
	 * Gets the.
	 *
	 * @param errors the errors
	 * @return the t
	 * @throws ErrorsException the errors exception
	 */
	T get(Errors errors) throws ErrorsException;
}
