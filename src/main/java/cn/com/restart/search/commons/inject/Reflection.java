/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Reflection.java 2012-3-29 15:15:17 l.xue.nong$$
 */



package cn.com.restart.search.commons.inject;
import java.lang.reflect.Constructor;


/**
 * The Class Reflection.
 *
 * @author l.xue.nong
 */
class Reflection {

    
    /**
     * The Class InvalidConstructor.
     *
     * @author l.xue.nong
     */
    static class InvalidConstructor {
        
        
        /**
         * Instantiates a new invalid constructor.
         */
        InvalidConstructor() {
            throw new AssertionError();
        }
    }

    
    /**
     * Invalid constructor.
     *
     * @param <T> the generic type
     * @return the constructor
     */
    @SuppressWarnings("unchecked")
    static <T> Constructor<T> invalidConstructor() {
        try {
            return (Constructor<T>) InvalidConstructor.class.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }
}
