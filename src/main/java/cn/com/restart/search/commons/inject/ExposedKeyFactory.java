/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ExposedKeyFactory.java 2012-3-29 15:15:07 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject;

import cn.com.restart.search.commons.inject.internal.BindingImpl;
import cn.com.restart.search.commons.inject.internal.Errors;
import cn.com.restart.search.commons.inject.internal.ErrorsException;
import cn.com.restart.search.commons.inject.internal.InternalContext;
import cn.com.restart.search.commons.inject.internal.InternalFactory;
import cn.com.restart.search.commons.inject.spi.Dependency;
import cn.com.restart.search.commons.inject.spi.PrivateElements;


/**
 * A factory for creating ExposedKey objects.
 *
 * @param <T> the generic type
 */
class ExposedKeyFactory<T> implements InternalFactory<T>, BindingProcessor.CreationListener {

	
	/** The key. */
	private final Key<T> key;

	
	/** The private elements. */
	private final PrivateElements privateElements;

	
	/** The delegate. */
	private BindingImpl<T> delegate;

	
	/**
	 * Instantiates a new exposed key factory.
	 *
	 * @param key the key
	 * @param privateElements the private elements
	 */
	public ExposedKeyFactory(Key<T> key, PrivateElements privateElements) {
		this.key = key;
		this.privateElements = privateElements;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.BindingProcessor.CreationListener#notify(cn.com.summall.search.commons.inject.internal.Errors)
	 */
	public void notify(Errors errors) {
		InjectorImpl privateInjector = (InjectorImpl) privateElements.getInjector();
		BindingImpl<T> explicitBinding = privateInjector.state.getExplicitBinding(key);

		
		
		
		if (explicitBinding.getInternalFactory() == this) {
			errors.withSource(explicitBinding.getSource()).exposedButNotBound(key);
			return;
		}

		this.delegate = explicitBinding;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.internal.InternalFactory#get(cn.com.summall.search.commons.inject.internal.Errors, cn.com.summall.search.commons.inject.internal.InternalContext, cn.com.summall.search.commons.inject.spi.Dependency)
	 */
	public T get(Errors errors, InternalContext context, Dependency<?> dependency) throws ErrorsException {
		return delegate.getInternalFactory().get(errors, context, dependency);
	}
}
