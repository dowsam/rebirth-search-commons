/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Providers.java 2012-3-29 15:15:18 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject.util;
import cn.com.rebirth.search.commons.inject.Provider;


/**
 * The Class Providers.
 *
 * @author l.xue.nong
 */
public final class Providers {

    
    /**
     * Instantiates a new providers.
     */
    private Providers() {
    }

    
    /**
     * Of.
     *
     * @param <T> the generic type
     * @param instance the instance
     * @return the provider
     */
    public static <T> Provider<T> of(final T instance) {
        return new Provider<T>() {
            public T get() {
                return instance;
            }

            @Override
            public String toString() {
                return "of(" + instance + ")";
            }
        };
    }
}
