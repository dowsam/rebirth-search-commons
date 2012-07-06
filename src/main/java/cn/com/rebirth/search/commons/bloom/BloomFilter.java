/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons BloomFilter.java 2012-3-29 15:15:17 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.bloom;

import java.nio.ByteBuffer;


/**
 * The Interface BloomFilter.
 *
 * @author l.xue.nong
 */
public interface BloomFilter {

    
    /** The Constant NONE. */
    public static final BloomFilter NONE = new BloomFilter() {
        @Override
        public void add(byte[] key, int offset, int length) {
        }

        @Override
        public void add(ByteBuffer key) {
        }

        @Override
        public boolean isPresent(byte[] key, int offset, int length) {
            return true;
        }

        @Override
        public boolean isPresent(ByteBuffer key) {
            return true;
        }

        @Override
        public long sizeInBytes() {
            return 0;
        }
    };

    
    /** The Constant EMPTY. */
    public static final BloomFilter EMPTY = new BloomFilter() {
        @Override
        public void add(byte[] key, int offset, int length) {
        }

        @Override
        public void add(ByteBuffer key) {
        }

        @Override
        public boolean isPresent(byte[] key, int offset, int length) {
            return false;
        }

        @Override
        public boolean isPresent(ByteBuffer key) {
            return false;
        }

        @Override
        public long sizeInBytes() {
            return 0;
        }
    };

    
    /**
     * Adds the.
     *
     * @param key the key
     * @param offset the offset
     * @param length the length
     */
    void add(byte[] key, int offset, int length);

    
    /**
     * Adds the.
     *
     * @param key the key
     */
    void add(ByteBuffer key);

    
    /**
     * Checks if is present.
     *
     * @param key the key
     * @param offset the offset
     * @param length the length
     * @return true, if is present
     */
    boolean isPresent(byte[] key, int offset, int length);

    
    /**
     * Checks if is present.
     *
     * @param key the key
     * @return true, if is present
     */
    boolean isPresent(ByteBuffer key);

    
    /**
     * Size in bytes.
     *
     * @return the long
     */
    long sizeInBytes();
}