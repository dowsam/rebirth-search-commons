/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Initializables.java 2012-3-29 15:15:10 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject;

import cn.com.rebirth.search.commons.inject.internal.Errors;
import cn.com.rebirth.search.commons.inject.internal.ErrorsException;


/**
 * The Class Initializables.
 *
 * @author l.xue.nong
 */
class Initializables {

	
	/**
	 * Of.
	 *
	 * @param <T> the generic type
	 * @param instance the instance
	 * @return the initializable
	 */
	static <T> Initializable<T> of(final T instance) {
		return new Initializable<T>() {
			public T get(Errors errors) throws ErrorsException {
				return instance;
			}

			@Override
			public String toString() {
				return String.valueOf(instance);
			}
		};
	}
}
