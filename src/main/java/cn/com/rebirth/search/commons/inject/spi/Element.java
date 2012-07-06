/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Element.java 2012-3-29 15:15:17 l.xue.nong$$
 */

package cn.com.rebirth.search.commons.inject.spi;


/**
 * The Interface Element.
 *
 * @author l.xue.nong
 */
public interface Element {

    
    /**
     * Gets the source.
     *
     * @return the source
     */
    Object getSource();

    
    /**
     * Accept visitor.
     *
     * @param <T> the generic type
     * @param visitor the visitor
     * @return the t
     */
    <T> T acceptVisitor(ElementVisitor<T> visitor);

    
    /**
     * Apply to.
     *
     * @param binder the binder
     */
    void applyTo(cn.com.rebirth.search.commons.inject.Binder binder);

}
