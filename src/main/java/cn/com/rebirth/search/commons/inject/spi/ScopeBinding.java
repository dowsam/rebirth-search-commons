/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ScopeBinding.java 2012-3-29 15:15:08 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject.spi;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.annotation.Annotation;

import cn.com.rebirth.search.commons.inject.Binder;
import cn.com.rebirth.search.commons.inject.Scope;


/**
 * The Class ScopeBinding.
 *
 * @author l.xue.nong
 */
public final class ScopeBinding implements Element {

	
	/** The source. */
	private final Object source;

	
	/** The annotation type. */
	private final Class<? extends Annotation> annotationType;

	
	/** The scope. */
	private final Scope scope;

	
	/**
	 * Instantiates a new scope binding.
	 *
	 * @param source the source
	 * @param annotationType the annotation type
	 * @param scope the scope
	 */
	ScopeBinding(Object source, Class<? extends Annotation> annotationType, Scope scope) {
		this.source = checkNotNull(source, "source");
		this.annotationType = checkNotNull(annotationType, "annotationType");
		this.scope = checkNotNull(scope, "scope");
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.Element#getSource()
	 */
	public Object getSource() {
		return source;
	}

	
	/**
	 * Gets the annotation type.
	 *
	 * @return the annotation type
	 */
	public Class<? extends Annotation> getAnnotationType() {
		return annotationType;
	}

	
	/**
	 * Gets the scope.
	 *
	 * @return the scope
	 */
	public Scope getScope() {
		return scope;
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
		binder.withSource(getSource()).bindScope(annotationType, scope);
	}
}
