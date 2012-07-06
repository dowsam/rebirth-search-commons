/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Scope.java 2012-3-29 15:15:17 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject;


/**
 * The Interface Scope.
 *
 * @author l.xue.nong
 */
public interface Scope {

    
    /**
     * Scope.
     *
     * @param <T> the generic type
     * @param key the key
     * @param unscoped the unscoped
     * @return the provider
     */
    public <T> Provider<T> scope(Key<T> key, Provider<T> unscoped);

    
    /**
     * To string.
     *
     * @return the string
     */
    String toString();
}
