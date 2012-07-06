/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Lookups.java 2012-3-29 15:15:14 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject;


/**
 * The Interface Lookups.
 *
 * @author l.xue.nong
 */
interface Lookups {

    
    /**
     * Gets the provider.
     *
     * @param <T> the generic type
     * @param key the key
     * @return the provider
     */
    <T> Provider<T> getProvider(Key<T> key);

    
    /**
     * Gets the members injector.
     *
     * @param <T> the generic type
     * @param type the type
     * @return the members injector
     */
    <T> MembersInjector<T> getMembersInjector(TypeLiteral<T> type);
}
