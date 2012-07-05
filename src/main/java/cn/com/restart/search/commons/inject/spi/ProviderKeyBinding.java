/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ProviderKeyBinding.java 2012-3-29 15:15:11 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.spi;

import cn.com.restart.search.commons.inject.Binding;
import cn.com.restart.search.commons.inject.Key;
import cn.com.restart.search.commons.inject.Provider;


/**
 * The Interface ProviderKeyBinding.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
public interface ProviderKeyBinding<T> extends Binding<T> {

	
	/**
	 * Gets the provider key.
	 *
	 * @return the provider key
	 */
	Key<? extends Provider<? extends T>> getProviderKey();

}