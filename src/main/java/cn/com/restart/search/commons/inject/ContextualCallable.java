/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ContextualCallable.java 2012-3-29 15:15:10 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject;

import cn.com.restart.search.commons.inject.internal.ErrorsException;
import cn.com.restart.search.commons.inject.internal.InternalContext;


/**
 * The Interface ContextualCallable.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
interface ContextualCallable<T> {

	
	/**
	 * Call.
	 *
	 * @param context the context
	 * @return the t
	 * @throws ErrorsException the errors exception
	 */
	T call(InternalContext context) throws ErrorsException;
}
