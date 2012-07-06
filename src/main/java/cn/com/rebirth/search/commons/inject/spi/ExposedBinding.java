/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ExposedBinding.java 2012-3-29 15:15:10 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject.spi;

import cn.com.rebirth.search.commons.inject.Binder;
import cn.com.rebirth.search.commons.inject.Binding;


/**
 * The Interface ExposedBinding.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
public interface ExposedBinding<T> extends Binding<T>, HasDependencies {

	
	/**
	 * Gets the private elements.
	 *
	 * @return the private elements
	 */
	PrivateElements getPrivateElements();

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.Element#applyTo(cn.com.summall.search.commons.inject.Binder)
	 */
	void applyTo(Binder binder);
}