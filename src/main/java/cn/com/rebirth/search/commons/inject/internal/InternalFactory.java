/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons InternalFactory.java 2012-3-29 15:15:11 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject.internal;

import cn.com.rebirth.search.commons.inject.spi.Dependency;


/**
 * A factory for creating Internal objects.
 *
 * @param <T> the generic type
 */
public interface InternalFactory<T> {

	
	/**
	 * Gets the.
	 *
	 * @param errors the errors
	 * @param context the context
	 * @param dependency the dependency
	 * @return the t
	 * @throws ErrorsException the errors exception
	 */
	T get(Errors errors, InternalContext context, Dependency<?> dependency) throws ErrorsException;
}
