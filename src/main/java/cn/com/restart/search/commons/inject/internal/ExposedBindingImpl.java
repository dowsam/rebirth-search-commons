/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ExposedBindingImpl.java 2012-3-29 15:15:08 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.internal;

import java.util.Set;

import cn.com.restart.search.commons.inject.Binder;
import cn.com.restart.search.commons.inject.Injector;
import cn.com.restart.search.commons.inject.Key;
import cn.com.restart.search.commons.inject.spi.BindingTargetVisitor;
import cn.com.restart.search.commons.inject.spi.Dependency;
import cn.com.restart.search.commons.inject.spi.ExposedBinding;
import cn.com.restart.search.commons.inject.spi.PrivateElements;

import com.google.common.collect.ImmutableSet;


/**
 * The Class ExposedBindingImpl.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
public class ExposedBindingImpl<T> extends BindingImpl<T> implements ExposedBinding<T> {

	
	/** The private elements. */
	private final PrivateElements privateElements;

	
	/**
	 * Instantiates a new exposed binding impl.
	 *
	 * @param injector the injector
	 * @param source the source
	 * @param key the key
	 * @param factory the factory
	 * @param privateElements the private elements
	 */
	public ExposedBindingImpl(Injector injector, Object source, Key<T> key, InternalFactory<T> factory,
			PrivateElements privateElements) {
		super(injector, key, source, factory, Scoping.UNSCOPED);
		this.privateElements = privateElements;
	}

	
	/**
	 * Instantiates a new exposed binding impl.
	 *
	 * @param source the source
	 * @param key the key
	 * @param scoping the scoping
	 * @param privateElements the private elements
	 */
	public ExposedBindingImpl(Object source, Key<T> key, Scoping scoping, PrivateElements privateElements) {
		super(source, key, scoping);
		this.privateElements = privateElements;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.Binding#acceptTargetVisitor(cn.com.summall.search.commons.inject.spi.BindingTargetVisitor)
	 */
	public <V> V acceptTargetVisitor(BindingTargetVisitor<? super T, V> visitor) {
		return visitor.visit(this);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.HasDependencies#getDependencies()
	 */
	public Set<Dependency<?>> getDependencies() {
		return ImmutableSet.<Dependency<?>> of(Dependency.get(Key.get(Injector.class)));
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.ExposedBinding#getPrivateElements()
	 */
	public PrivateElements getPrivateElements() {
		return privateElements;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.internal.BindingImpl#withScoping(cn.com.summall.search.commons.inject.internal.Scoping)
	 */
	public BindingImpl<T> withScoping(Scoping scoping) {
		return new ExposedBindingImpl<T>(getSource(), getKey(), scoping, privateElements);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.internal.BindingImpl#withKey(cn.com.summall.search.commons.inject.Key)
	 */
	public ExposedBindingImpl<T> withKey(Key<T> key) {
		return new ExposedBindingImpl<T>(getSource(), key, getScoping(), privateElements);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.internal.BindingImpl#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(ExposedBinding.class).add("key", getKey()).add("source", getSource())
				.add("privateElements", privateElements).toString();
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.Element#applyTo(cn.com.summall.search.commons.inject.Binder)
	 */
	public void applyTo(Binder binder) {
		throw new UnsupportedOperationException("This element represents a synthetic binding.");
	}
}
