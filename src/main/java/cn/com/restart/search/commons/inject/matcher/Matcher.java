/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Matcher.java 2012-3-29 15:15:20 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.matcher;


/**
 * The Interface Matcher.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
public interface Matcher<T> {

    
    /**
     * Matches.
     *
     * @param t the t
     * @return true, if successful
     */
    boolean matches(T t);

    
    /**
     * And.
     *
     * @param other the other
     * @return the matcher
     */
    Matcher<T> and(Matcher<? super T> other);

    
    /**
     * Or.
     *
     * @param other the other
     * @return the matcher
     */
    Matcher<T> or(Matcher<? super T> other);
}
