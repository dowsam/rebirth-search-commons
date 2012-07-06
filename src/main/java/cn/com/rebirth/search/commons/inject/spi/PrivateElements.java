/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons PrivateElements.java 2012-3-29 15:15:17 l.xue.nong$$
 */



package cn.com.rebirth.search.commons.inject.spi;
import java.util.List;
import java.util.Set;

import cn.com.rebirth.search.commons.inject.Injector;
import cn.com.rebirth.search.commons.inject.Key;


/**
 * The Interface PrivateElements.
 *
 * @author l.xue.nong
 */
public interface PrivateElements extends Element {

    
    /**
     * Gets the elements.
     *
     * @return the elements
     */
    List<Element> getElements();

    
    /**
     * Gets the injector.
     *
     * @return the injector
     */
    Injector getInjector();

    
    /**
     * Gets the exposed keys.
     *
     * @return the exposed keys
     */
    Set<Key<?>> getExposedKeys();

    
    /**
     * Gets the exposed source.
     *
     * @param key the key
     * @return the exposed source
     */
    Object getExposedSource(Key<?> key);
}
