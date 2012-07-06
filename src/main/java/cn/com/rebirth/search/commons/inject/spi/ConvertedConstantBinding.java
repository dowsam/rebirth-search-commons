/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ConvertedConstantBinding.java 2012-3-29 15:15:07 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject.spi;

import java.util.Set;

import cn.com.rebirth.search.commons.inject.Binding;
import cn.com.rebirth.search.commons.inject.Key;


/**
 * The Interface ConvertedConstantBinding.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
public interface ConvertedConstantBinding<T> extends Binding<T>, HasDependencies {

	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	T getValue();

	
	/**
	 * Gets the source key.
	 *
	 * @return the source key
	 */
	Key<String> getSourceKey();

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.HasDependencies#getDependencies()
	 */
	Set<Dependency<?>> getDependencies();
}