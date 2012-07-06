/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons BlobStore.java 2012-7-6 10:23:43 l.xue.nong$$
 */
package cn.com.rebirth.search.commons.blobstore;


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
