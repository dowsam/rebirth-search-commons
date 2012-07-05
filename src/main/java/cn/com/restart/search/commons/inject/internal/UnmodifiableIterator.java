/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons UnmodifiableIterator.java 2012-3-29 15:15:12 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.internal;

import java.util.Iterator;


/**
 * The Class UnmodifiableIterator.
 *
 * @param <E> the element type
 * @author l.xue.nong
 */
public abstract class UnmodifiableIterator<E> implements Iterator<E> {

    
    /* (non-Javadoc)
     * @see java.util.Iterator#remove()
     */
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
