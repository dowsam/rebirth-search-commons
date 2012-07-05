/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons MembersInjector.java 2012-3-29 15:15:10 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject;


/**
 * The Interface MembersInjector.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
public interface MembersInjector<T> {

    
    /**
     * Inject members.
     *
     * @param instance the instance
     */
    void injectMembers(T instance);
}
