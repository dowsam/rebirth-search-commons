/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons TypeListenerBinding.java 2012-3-29 15:15:15 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.spi;

import cn.com.restart.search.commons.inject.Binder;
import cn.com.restart.search.commons.inject.TypeLiteral;
import cn.com.restart.search.commons.inject.matcher.Matcher;


/**
 * The Class TypeListenerBinding.
 *
 * @author l.xue.nong
 */
public final class TypeListenerBinding implements Element {

	
	/** The source. */
	private final Object source;

	
	/** The type matcher. */
	private final Matcher<? super TypeLiteral<?>> typeMatcher;

	
	/** The listener. */
	private final TypeListener listener;

	
	/**
	 * Instantiates a new type listener binding.
	 *
	 * @param source the source
	 * @param listener the listener
	 * @param typeMatcher the type matcher
	 */
	TypeListenerBinding(Object source, TypeListener listener, Matcher<? super TypeLiteral<?>> typeMatcher) {
		this.source = source;
		this.listener = listener;
		this.typeMatcher = typeMatcher;
	}

	
	/**
	 * Gets the listener.
	 *
	 * @return the listener
	 */
	public TypeListener getListener() {
		return listener;
	}

	
	/**
	 * Gets the type matcher.
	 *
	 * @return the type matcher
	 */
	public Matcher<? super TypeLiteral<?>> getTypeMatcher() {
		return typeMatcher;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.Element#getSource()
	 */
	public Object getSource() {
		return source;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.Element#acceptVisitor(cn.com.summall.search.commons.inject.spi.ElementVisitor)
	 */
	public <T> T acceptVisitor(ElementVisitor<T> visitor) {
		return visitor.visit(this);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.Element#applyTo(cn.com.summall.search.commons.inject.Binder)
	 */
	public void applyTo(Binder binder) {
		binder.withSource(getSource()).bindListener(typeMatcher, listener);
	}
}
