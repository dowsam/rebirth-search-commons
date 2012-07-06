/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons LinkedKeyBinding.java 2012-3-29 15:15:13 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject.spi;

import cn.com.rebirth.search.commons.inject.Binding;
import cn.com.rebirth.search.commons.inject.Key;


/**
 * The Interface LinkedKeyBinding.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
public interface LinkedKeyBinding<T> extends Binding<T> {

	
	/**
	 * Gets the linked key.
	 *
	 * @return the linked key
	 */
	Key<? extends T> getLinkedKey();

}