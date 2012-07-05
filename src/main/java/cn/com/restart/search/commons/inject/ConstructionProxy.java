/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ConstructionProxy.java 2012-3-29 15:15:07 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import cn.com.restart.search.commons.inject.spi.InjectionPoint;


/**
 * The Interface ConstructionProxy.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
interface ConstructionProxy<T> {

	
	/**
	 * New instance.
	 *
	 * @param arguments the arguments
	 * @return the t
	 * @throws InvocationTargetException the invocation target exception
	 */
	T newInstance(Object... arguments) throws InvocationTargetException;

	
	/**
	 * Gets the injection point.
	 *
	 * @return the injection point
	 */
	InjectionPoint getInjectionPoint();

	
	/**
	 * Gets the constructor.
	 *
	 * @return the constructor
	 */
	Constructor<T> getConstructor();
}
