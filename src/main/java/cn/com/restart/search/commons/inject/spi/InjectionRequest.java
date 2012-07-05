/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons InjectionRequest.java 2012-3-29 15:15:11 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.spi;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Set;

import cn.com.restart.search.commons.inject.Binder;
import cn.com.restart.search.commons.inject.ConfigurationException;
import cn.com.restart.search.commons.inject.TypeLiteral;



/**
 * The Class InjectionRequest.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
public final class InjectionRequest<T> implements Element {

	
	/** The source. */
	private final Object source;

	
	/** The type. */
	private final TypeLiteral<T> type;

	
	/** The instance. */
	private final T instance;

	
	/**
	 * Instantiates a new injection request.
	 *
	 * @param source the source
	 * @param type the type
	 * @param instance the instance
	 */
	public InjectionRequest(Object source, TypeLiteral<T> type, T instance) {
		this.source = checkNotNull(source, "source");
		this.type = checkNotNull(type, "type");
		this.instance = checkNotNull(instance, "instance");
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.Element#getSource()
	 */
	public Object getSource() {
		return source;
	}

	
	/**
	 * Gets the single instance of InjectionRequest.
	 *
	 * @return single instance of InjectionRequest
	 */
	public T getInstance() {
		return instance;
	}

	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public TypeLiteral<T> getType() {
		return type;
	}

	
	/**
	 * Gets the injection points.
	 *
	 * @return the injection points
	 * @throws ConfigurationException the configuration exception
	 */
	public Set<InjectionPoint> getInjectionPoints() throws ConfigurationException {
		return InjectionPoint.forInstanceMethodsAndFields(instance.getClass());
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.Element#acceptVisitor(cn.com.summall.search.commons.inject.spi.ElementVisitor)
	 */
	public <R> R acceptVisitor(ElementVisitor<R> visitor) {
		return visitor.visit(this);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.Element#applyTo(cn.com.summall.search.commons.inject.Binder)
	 */
	public void applyTo(Binder binder) {
		binder.withSource(getSource()).requestInjection(type, instance);
	}

}
