/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons MembersInjectorLookup.java 2012-3-29 15:15:07 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.spi;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import cn.com.restart.search.commons.inject.Binder;
import cn.com.restart.search.commons.inject.MembersInjector;
import cn.com.restart.search.commons.inject.TypeLiteral;


/**
 * The Class MembersInjectorLookup.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
public final class MembersInjectorLookup<T> implements Element {

	
	/** The source. */
	private final Object source;

	
	/** The type. */
	private final TypeLiteral<T> type;

	
	/** The delegate. */
	private MembersInjector<T> delegate;

	
	/**
	 * Instantiates a new members injector lookup.
	 *
	 * @param source the source
	 * @param type the type
	 */
	public MembersInjectorLookup(Object source, TypeLiteral<T> type) {
		this.source = checkNotNull(source, "source");
		this.type = checkNotNull(type, "type");
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.Element#getSource()
	 */
	public Object getSource() {
		return source;
	}

	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public TypeLiteral<T> getType() {
		return type;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.Element#acceptVisitor(cn.com.summall.search.commons.inject.spi.ElementVisitor)
	 */
	@SuppressWarnings("hiding")
	public <T> T acceptVisitor(ElementVisitor<T> visitor) {
		return visitor.visit(this);
	}

	
	/**
	 * Initialize delegate.
	 *
	 * @param delegate the delegate
	 */
	public void initializeDelegate(MembersInjector<T> delegate) {
		checkState(this.delegate == null, "delegate already initialized");
		this.delegate = checkNotNull(delegate, "delegate");
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.Element#applyTo(cn.com.summall.search.commons.inject.Binder)
	 */
	public void applyTo(Binder binder) {
		initializeDelegate(binder.withSource(getSource()).getMembersInjector(type));
	}

	
	/**
	 * Gets the delegate.
	 *
	 * @return the delegate
	 */
	public MembersInjector<T> getDelegate() {
		return delegate;
	}

	
	/**
	 * Gets the members injector.
	 *
	 * @return the members injector
	 */
	public MembersInjector<T> getMembersInjector() {
		return new MembersInjector<T>() {
			public void injectMembers(T instance) {
				checkState(delegate != null, "This MembersInjector cannot be used until the Injector has been created.");
				delegate.injectMembers(instance);
			}

			@Override
			public String toString() {
				return "MembersInjector<" + type + ">";
			}
		};
	}
}