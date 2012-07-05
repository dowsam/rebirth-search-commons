/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons BlobStore.java 2012-3-29 15:15:10 l.xue.nong$$
 */
package cn.com.restart.search.commons.blobstore;


/**
 * The Interface BlobStore.
 *
 * @author l.xue.nong
 */
public interface BlobStore {

    /**
     * Immutable blob container.
     *
     * @param path the path
     * @return the immutable blob container
     */
    ImmutableBlobContainer immutableBlobContainer(BlobPath path);

    /**
     * Delete.
     *
     * @param path the path
     */
    void delete(BlobPath path);

    /**
     * Close.
     */
    void close();
}
