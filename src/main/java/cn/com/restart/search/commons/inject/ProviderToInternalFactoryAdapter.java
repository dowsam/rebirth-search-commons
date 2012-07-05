/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ProviderToInternalFactoryAdapter.java 2012-3-29 15:15:12 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject;

import cn.com.restart.search.commons.inject.internal.Errors;
import cn.com.restart.search.commons.inject.internal.ErrorsException;
import cn.com.restart.search.commons.inject.internal.InternalContext;
import cn.com.restart.search.commons.inject.internal.InternalFactory;
import cn.com.restart.search.commons.inject.spi.Dependency;


/**
 * The Class ProviderToInternalFactoryAdapter.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
class ProviderToInternalFactoryAdapter<T> implements Provider<T> {

	
	/** The injector. */
	private final InjectorImpl injector;

	
	/** The internal factory. */
	private final InternalFactory<? extends T> internalFactory;

	
	/**
	 * Instantiates a new provider to internal factory adapter.
	 *
	 * @param injector the injector
	 * @param internalFactory the internal factory
	 */
	public ProviderToInternalFactoryAdapter(InjectorImpl injector, InternalFactory<? extends T> internalFactory) {
		this.injector = injector;
		this.internalFactory = internalFactory;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.Provider#get()
	 */
	public T get() {
		final Errors errors = new Errors();
		try {
			T t = injector.callInContext(new ContextualCallable<T>() {
				@SuppressWarnings("rawtypes")
				public T call(InternalContext context) throws ErrorsException {
					Dependency dependency = context.getDependency();
					return internalFactory.get(errors, context, dependency);
				}
			});
			errors.throwIfNewErrors(0);
			return t;
		} catch (ErrorsException e) {
			throw new ProvisionException(errors.merge(e.getErrors()).getMessages());
		}
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return internalFactory.toString();
	}
}
