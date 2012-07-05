/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons HasDependencies.java 2012-3-29 15:15:09 l.xue.nong$$
 */



package cn.com.restart.search.commons.inject.spi;
import java.util.Set;


/**
 * The Interface HasDependencies.
 *
 * @author l.xue.nong
 */
public interface HasDependencies {

    
    /**
     * Gets the dependencies.
     *
     * @return the dependencies
     */
    Set<Dependency<?>> getDependencies();
}
