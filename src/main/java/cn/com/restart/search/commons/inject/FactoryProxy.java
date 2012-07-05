/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons FactoryProxy.java 2012-3-29 15:15:13 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject;

import cn.com.restart.search.commons.inject.internal.Errors;
import cn.com.restart.search.commons.inject.internal.ErrorsException;
import cn.com.restart.search.commons.inject.internal.InternalContext;
import cn.com.restart.search.commons.inject.internal.InternalFactory;
import cn.com.restart.search.commons.inject.internal.ToStringBuilder;
import cn.com.restart.search.commons.inject.spi.Dependency;


/**
 * The Class FactoryProxy.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
class FactoryProxy<T> implements InternalFactory<T>, BindingProcessor.CreationListener {

	
	/** The injector. */
	private final InjectorImpl injector;

	
	/** The key. */
	private final Key<T> key;

	
	/** The target key. */
	private final Key<? extends T> targetKey;

	
	/** The source. */
	private final Object source;

	
	/** The target factory. */
	private InternalFactory<? extends T> targetFactory;

	
	/**
	 * Instantiates a new factory proxy.
	 *
	 * @param injector the injector
	 * @param key the key
	 * @param targetKey the target key
	 * @param source the source
	 */
	FactoryProxy(InjectorImpl injector, Key<T> key, Key<? extends T> targetKey, Object source) {
		this.injector = injector;
		this.key = key;
		this.targetKey = targetKey;
		this.source = source;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.BindingProcessor.CreationListener#notify(cn.com.summall.search.commons.inject.internal.Errors)
	 */
	public void notify(final Errors errors) {
		try {
			targetFactory = injector.getInternalFactory(targetKey, errors.withSource(source));
		} catch (ErrorsException e) {
			errors.merge(e.getErrors());
		}
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.internal.InternalFactory#get(cn.com.summall.search.commons.inject.internal.Errors, cn.com.summall.search.commons.inject.internal.InternalContext, cn.com.summall.search.commons.inject.spi.Dependency)
	 */
	public T get(Errors errors, InternalContext context, Dependency<?> dependency) throws ErrorsException {
		return targetFactory.get(errors.withSource(targetKey), context, dependency);
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(FactoryProxy.class).add("key", key).add("provider", targetFactory).toString();
	}
}
