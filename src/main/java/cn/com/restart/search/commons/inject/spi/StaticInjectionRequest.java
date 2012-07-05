/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons StaticInjectionRequest.java 2012-3-29 15:15:11 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.spi;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Set;

import cn.com.restart.search.commons.inject.Binder;
import cn.com.restart.search.commons.inject.ConfigurationException;


/**
 * The Class StaticInjectionRequest.
 *
 * @author l.xue.nong
 */
public final class StaticInjectionRequest implements Element {

	
	/** The source. */
	private final Object source;

	
	/** The type. */
	private final Class<?> type;

	
	/**
	 * Instantiates a new static injection request.
	 *
	 * @param source the source
	 * @param type the type
	 */
	StaticInjectionRequest(Object source, Class<?> type) {
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
	public Class<?> getType() {
		return type;
	}

	
	/**
	 * Gets the injection points.
	 *
	 * @return the injection points
	 * @throws ConfigurationException the configuration exception
	 */
	public Set<InjectionPoint> getInjectionPoints() throws ConfigurationException {
		return InjectionPoint.forStaticMethodsAndFields(type);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.Element#applyTo(cn.com.summall.search.commons.inject.Binder)
	 */
	public void applyTo(Binder binder) {
		binder.withSource(getSource()).requestStaticInjection(type);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.Element#acceptVisitor(cn.com.summall.search.commons.inject.spi.ElementVisitor)
	 */
	public <T> T acceptVisitor(ElementVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
