/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ProviderBinding.java 2012-3-29 15:15:17 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject.spi;

import cn.com.rebirth.search.commons.inject.Binding;
import cn.com.rebirth.search.commons.inject.Key;
import cn.com.rebirth.search.commons.inject.Provider;


/**
 * The Interface ProviderBinding.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
public interface ProviderBinding<T extends Provider<?>> extends Binding<T> {

	
	/**
	 * Gets the provided key.
	 *
	 * @return the provided key
	 */
	Key<?> getProvidedKey();
}