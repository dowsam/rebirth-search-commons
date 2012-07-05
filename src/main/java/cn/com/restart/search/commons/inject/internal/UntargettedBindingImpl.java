/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons UntargettedBindingImpl.java 2012-3-29 15:15:13 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.internal;

import cn.com.restart.search.commons.inject.Binder;
import cn.com.restart.search.commons.inject.Injector;
import cn.com.restart.search.commons.inject.Key;
import cn.com.restart.search.commons.inject.spi.BindingTargetVisitor;
import cn.com.restart.search.commons.inject.spi.Dependency;
import cn.com.restart.search.commons.inject.spi.UntargettedBinding;


/**
 * The Class UntargettedBindingImpl.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
public class UntargettedBindingImpl<T> extends BindingImpl<T> implements UntargettedBinding<T> {

	
	/**
	 * Instantiates a new untargetted binding impl.
	 *
	 * @param injector the injector
	 * @param key the key
	 * @param source the source
	 */
	public UntargettedBindingImpl(Injector injector, Key<T> key, Object source) {
		super(injector, key, source, new InternalFactory<T>() {
			public T get(Errors errors, InternalContext context, Dependency<?> dependency) {
				throw new AssertionError();
			}
		}, Scoping.UNSCOPED);
	}

	
	/**
	 * Instantiates a new untargetted binding impl.
	 *
	 * @param source the source
	 * @param key the key
	 * @param scoping the scoping
	 */
	public UntargettedBindingImpl(Object source, Key<T> key, Scoping scoping) {
		super(source, key, scoping);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.Binding#acceptTargetVisitor(cn.com.summall.search.commons.inject.spi.BindingTargetVisitor)
	 */
	public <V> V acceptTargetVisitor(BindingTargetVisitor<? super T, V> visitor) {
		return visitor.visit(this);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.internal.BindingImpl#withScoping(cn.com.summall.search.commons.inject.internal.Scoping)
	 */
	public BindingImpl<T> withScoping(Scoping scoping) {
		return new UntargettedBindingImpl<T>(getSource(), getKey(), scoping);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.internal.BindingImpl#withKey(cn.com.summall.search.commons.inject.Key)
	 */
	public BindingImpl<T> withKey(Key<T> key) {
		return new UntargettedBindingImpl<T>(getSource(), key, getScoping());
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.Element#applyTo(cn.com.summall.search.commons.inject.Binder)
	 */
	public void applyTo(Binder binder) {
		getScoping().applyTo(binder.withSource(getSource()).bind(getKey()));
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.internal.BindingImpl#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(UntargettedBinding.class).add("key", getKey()).add("source", getSource()).toString();
	}
}
