/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ConstantBindingBuilder.java 2012-3-29 15:15:13 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.binder;


/**
 * The Interface ConstantBindingBuilder.
 *
 * @author l.xue.nong
 */
public interface ConstantBindingBuilder {

    
    /**
     * To.
     *
     * @param value the value
     */
    void to(String value);

    
    /**
     * To.
     *
     * @param value the value
     */
    void to(int value);

    
    /**
     * To.
     *
     * @param value the value
     */
    void to(long value);

    
    /**
     * To.
     *
     * @param value the value
     */
    void to(boolean value);

    
    /**
     * To.
     *
     * @param value the value
     */
    void to(double value);

    
    /**
     * To.
     *
     * @param value the value
     */
    void to(float value);

    
    /**
     * To.
     *
     * @param value the value
     */
    void to(short value);

    
    /**
     * To.
     *
     * @param value the value
     */
    void to(char value);

    
    /**
     * To.
     *
     * @param value the value
     */
    void to(Class<?> value);

    
    /**
     * To.
     *
     * @param <E> the element type
     * @param value the value
     */
    <E extends Enum<E>> void to(E value);
}
