/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons InstanceBinding.java 2012-3-29 15:15:19 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.spi;

import java.util.Set;

import cn.com.restart.search.commons.inject.Binding;


/**
 * The Interface InstanceBinding.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
public interface InstanceBinding<T> extends Binding<T>, HasDependencies {

	
	/**
	 * Gets the single instance of InstanceBinding.
	 *
	 * @return single instance of InstanceBinding
	 */
	T getInstance();

	
	/**
	 * Gets the injection points.
	 *
	 * @return the injection points
	 */
	Set<InjectionPoint> getInjectionPoints();

}
