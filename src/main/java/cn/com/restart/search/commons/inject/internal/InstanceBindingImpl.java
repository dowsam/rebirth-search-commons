/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons InstanceBindingImpl.java 2012-3-29 15:15:10 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.internal;

import java.util.Set;

import cn.com.restart.search.commons.inject.Binder;
import cn.com.restart.search.commons.inject.Injector;
import cn.com.restart.search.commons.inject.Key;
import cn.com.restart.search.commons.inject.Provider;
import cn.com.restart.search.commons.inject.spi.BindingTargetVisitor;
import cn.com.restart.search.commons.inject.spi.Dependency;
import cn.com.restart.search.commons.inject.spi.HasDependencies;
import cn.com.restart.search.commons.inject.spi.InjectionPoint;
import cn.com.restart.search.commons.inject.spi.InstanceBinding;
import cn.com.restart.search.commons.inject.util.Providers;

import com.google.common.collect.ImmutableSet;


/**
 * The Class InstanceBindingImpl.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
public class InstanceBindingImpl<T> extends BindingImpl<T> implements InstanceBinding<T> {

	
	/** The instance. */
	final T instance;

	
	/** The provider. */
	final Provider<T> provider;

	
	/** The injection points. */
	final ImmutableSet<InjectionPoint> injectionPoints;

	
	/**
	 * Instantiates a new instance binding impl.
	 *
	 * @param injector the injector
	 * @param key the key
	 * @param source the source
	 * @param internalFactory the internal factory
	 * @param injectionPoints the injection points
	 * @param instance the instance
	 */
	public InstanceBindingImpl(Injector injector, Key<T> key, Object source,
			InternalFactory<? extends T> internalFactory, Set<InjectionPoint> injectionPoints, T instance) {
		super(injector, key, source, internalFactory, Scoping.UNSCOPED);
		this.injectionPoints = ImmutableSet.copyOf(injectionPoints);
		this.instance = instance;
		this.provider = Providers.of(instance);
	}

	
	/**
	 * Instantiates a new instance binding impl.
	 *
	 * @param source the source
	 * @param key the key
	 * @param scoping the scoping
	 * @param injectionPoints the injection points
	 * @param instance the instance
	 */
	public InstanceBindingImpl(Object source, Key<T> key, Scoping scoping, Set<InjectionPoint> injectionPoints,
			T instance) {
		super(source, key, scoping);
		this.injectionPoints = ImmutableSet.copyOf(injectionPoints);
		this.instance = instance;
		this.provider = Providers.of(instance);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.internal.BindingImpl#getProvider()
	 */
	@Override
	public Provider<T> getProvider() {
		return this.provider;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.Binding#acceptTargetVisitor(cn.com.summall.search.commons.inject.spi.BindingTargetVisitor)
	 */
	public <V> V acceptTargetVisitor(BindingTargetVisitor<? super T, V> visitor) {
		return visitor.visit(this);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.InstanceBinding#getInstance()
	 */
	public T getInstance() {
		return instance;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.InstanceBinding#getInjectionPoints()
	 */
	public Set<InjectionPoint> getInjectionPoints() {
		return injectionPoints;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.HasDependencies#getDependencies()
	 */
	public Set<Dependency<?>> getDependencies() {
		return instance instanceof HasDependencies ? ImmutableSet
				.copyOf(((HasDependencies) instance).getDependencies()) : Dependency
				.forInjectionPoints(injectionPoints);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.internal.BindingImpl#withScoping(cn.com.summall.search.commons.inject.internal.Scoping)
	 */
	public BindingImpl<T> withScoping(Scoping scoping) {
		return new InstanceBindingImpl<T>(getSource(), getKey(), scoping, injectionPoints, instance);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.internal.BindingImpl#withKey(cn.com.summall.search.commons.inject.Key)
	 */
	public BindingImpl<T> withKey(Key<T> key) {
		return new InstanceBindingImpl<T>(getSource(), key, getScoping(), injectionPoints, instance);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.Element#applyTo(cn.com.summall.search.commons.inject.Binder)
	 */
	public void applyTo(Binder binder) {
		
		binder.withSource(getSource()).bind(getKey()).toInstance(instance);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.internal.BindingImpl#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(InstanceBinding.class).add("key", getKey()).add("source", getSource())
				.add("instance", instance).toString();
	}
}
