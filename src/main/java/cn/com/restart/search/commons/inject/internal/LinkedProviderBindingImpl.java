/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons LinkedProviderBindingImpl.java 2012-3-29 15:15:10 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.internal;

import cn.com.restart.search.commons.inject.Binder;
import cn.com.restart.search.commons.inject.Injector;
import cn.com.restart.search.commons.inject.Key;
import cn.com.restart.search.commons.inject.Provider;
import cn.com.restart.search.commons.inject.spi.BindingTargetVisitor;
import cn.com.restart.search.commons.inject.spi.ProviderKeyBinding;


/**
 * The Class LinkedProviderBindingImpl.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
public final class LinkedProviderBindingImpl<T> extends BindingImpl<T> implements ProviderKeyBinding<T> {

	
	/** The provider key. */
	final Key<? extends Provider<? extends T>> providerKey;

	
	/**
	 * Instantiates a new linked provider binding impl.
	 *
	 * @param injector the injector
	 * @param key the key
	 * @param source the source
	 * @param internalFactory the internal factory
	 * @param scoping the scoping
	 * @param providerKey the provider key
	 */
	public LinkedProviderBindingImpl(Injector injector, Key<T> key, Object source,
			InternalFactory<? extends T> internalFactory, Scoping scoping,
			Key<? extends Provider<? extends T>> providerKey) {
		super(injector, key, source, internalFactory, scoping);
		this.providerKey = providerKey;
	}

	
	/**
	 * Instantiates a new linked provider binding impl.
	 *
	 * @param source the source
	 * @param key the key
	 * @param scoping the scoping
	 * @param providerKey the provider key
	 */
	LinkedProviderBindingImpl(Object source, Key<T> key, Scoping scoping,
			Key<? extends Provider<? extends T>> providerKey) {
		super(source, key, scoping);
		this.providerKey = providerKey;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.Binding#acceptTargetVisitor(cn.com.summall.search.commons.inject.spi.BindingTargetVisitor)
	 */
	public <V> V acceptTargetVisitor(BindingTargetVisitor<? super T, V> visitor) {
		return visitor.visit(this);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.ProviderKeyBinding#getProviderKey()
	 */
	public Key<? extends Provider<? extends T>> getProviderKey() {
		return providerKey;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.internal.BindingImpl#withScoping(cn.com.summall.search.commons.inject.internal.Scoping)
	 */
	public BindingImpl<T> withScoping(Scoping scoping) {
		return new LinkedProviderBindingImpl<T>(getSource(), getKey(), scoping, providerKey);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.internal.BindingImpl#withKey(cn.com.summall.search.commons.inject.Key)
	 */
	public BindingImpl<T> withKey(Key<T> key) {
		return new LinkedProviderBindingImpl<T>(getSource(), key, getScoping(), providerKey);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.Element#applyTo(cn.com.summall.search.commons.inject.Binder)
	 */
	public void applyTo(Binder binder) {
		getScoping().applyTo(binder.withSource(getSource()).bind(getKey()).toProvider(getProviderKey()));
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.internal.BindingImpl#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(ProviderKeyBinding.class).add("key", getKey()).add("source", getSource())
				.add("scope", getScoping()).add("provider", providerKey).toString();
	}
}
