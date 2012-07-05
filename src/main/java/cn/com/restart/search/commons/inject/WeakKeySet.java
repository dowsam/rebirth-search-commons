/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons WeakKeySet.java 2012-3-29 15:15:13 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject;
import com.google.common.collect.Sets;

import java.util.Set;


/**
 * The Class WeakKeySet.
 *
 * @author l.xue.nong
 */
final class WeakKeySet {

    
    /** The backing set. */
    private Set<String> backingSet = Sets.newHashSet();

    
    /**
     * Adds the.
     *
     * @param key the key
     * @return true, if successful
     */
    public boolean add(Key<?> key) {
        return backingSet.add(key.toString());
    }

    
    /**
     * Contains.
     *
     * @param o the o
     * @return true, if successful
     */
    public boolean contains(Object o) {
        return o instanceof Key && backingSet.contains(o.toString());
    }
}
