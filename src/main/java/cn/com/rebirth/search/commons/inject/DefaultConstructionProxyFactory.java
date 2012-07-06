/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons DefaultConstructionProxyFactory.java 2012-3-29 15:15:10 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import cn.com.rebirth.search.commons.inject.spi.InjectionPoint;


/**
 * A factory for creating DefaultConstructionProxy objects.
 *
 * @param <T> the generic type
 */
class DefaultConstructionProxyFactory<T> implements ConstructionProxyFactory<T> {

	
	/** The injection point. */
	private final InjectionPoint injectionPoint;

	
	/**
	 * Instantiates a new default construction proxy factory.
	 *
	 * @param injectionPoint the injection point
	 */
	DefaultConstructionProxyFactory(InjectionPoint injectionPoint) {
		this.injectionPoint = injectionPoint;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.ConstructionProxyFactory#create()
	 */
	public ConstructionProxy<T> create() {
		@SuppressWarnings("unchecked")
		
		final Constructor<T> constructor = (Constructor<T>) injectionPoint.getMember();

		
		if (Modifier.isPublic(constructor.getModifiers())) {
		} else {
			constructor.setAccessible(true);
		}

		return new ConstructionProxy<T>() {
			public T newInstance(Object... arguments) throws InvocationTargetException {
				try {
					return constructor.newInstance(arguments);
				} catch (InstantiationException e) {
					throw new AssertionError(e); 
				} catch (IllegalAccessException e) {
					throw new AssertionError(e); 
				}
			}

			public InjectionPoint getInjectionPoint() {
				return injectionPoint;
			}

			public Constructor<T> getConstructor() {
				return constructor;
			}
		};
	}
}
