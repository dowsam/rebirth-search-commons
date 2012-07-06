/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ProviderInstanceBinding.java 2012-3-29 15:15:08 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject.spi;

import java.util.Set;

import cn.com.rebirth.search.commons.inject.Binding;
import cn.com.rebirth.search.commons.inject.Provider;


/**
 * The Interface ProviderInstanceBinding.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
public interface ProviderInstanceBinding<T> extends Binding<T>, HasDependencies {

	
	/**
	 * Gets the provider instance.
	 *
	 * @return the provider instance
	 */
	Provider<? extends T> getProviderInstance();

	
	/**
	 * Gets the injection points.
	 *
	 * @return the injection points
	 */
	Set<InjectionPoint> getInjectionPoints();

}