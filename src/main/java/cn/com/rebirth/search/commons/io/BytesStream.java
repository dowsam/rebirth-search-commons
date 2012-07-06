/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons BytesStream.java 2012-3-29 15:15:13 l.xue.nong$$
 */
package cn.com.rebirth.search.commons.io;


/**
 * The Interface BytesStream.
 *
 * @author l.xue.nong
 */
public interface BytesStream {

    
    /**
     * Underlying bytes.
     *
     * @return the byte[]
     */
    byte[] underlyingBytes();

    
    /**
     * Size.
     *
     * @return the int
     */
    int size();

    
    /**
     * Copied byte array.
     *
     * @return the byte[]
     */
    byte[] copiedByteArray();
}