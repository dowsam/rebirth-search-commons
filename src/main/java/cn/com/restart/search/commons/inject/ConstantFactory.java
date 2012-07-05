/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ConstantFactory.java 2012-3-29 15:15:11 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject;

import cn.com.restart.search.commons.inject.internal.Errors;
import cn.com.restart.search.commons.inject.internal.ErrorsException;
import cn.com.restart.search.commons.inject.internal.InternalContext;
import cn.com.restart.search.commons.inject.internal.InternalFactory;
import cn.com.restart.search.commons.inject.internal.ToStringBuilder;
import cn.com.restart.search.commons.inject.spi.Dependency;


/**
 * A factory for creating Constant objects.
 *
 * @param <T> the generic type
 */
class ConstantFactory<T> implements InternalFactory<T> {

	
	/** The initializable. */
	private final Initializable<T> initializable;

	
	/**
	 * Instantiates a new constant factory.
	 *
	 * @param initializable the initializable
	 */
	public ConstantFactory(Initializable<T> initializable) {
		this.initializable = initializable;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.internal.InternalFactory#get(cn.com.summall.search.commons.inject.internal.Errors, cn.com.summall.search.commons.inject.internal.InternalContext, cn.com.summall.search.commons.inject.spi.Dependency)
	 */
	@SuppressWarnings("rawtypes")
	public T get(Errors errors, InternalContext context, Dependency dependency) throws ErrorsException {
		return initializable.get(errors);
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(ConstantFactory.class).add("value", initializable).toString();
	}
}
