/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ConstructionProxyFactory.java 2012-3-29 15:15:07 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject;


/**
 * A factory for creating ConstructionProxy objects.
 *
 * @param <T> the generic type
 */
interface ConstructionProxyFactory<T> {

    
    /**
     * Creates the.
     *
     * @return the construction proxy
     */
    ConstructionProxy<T> create();
}
