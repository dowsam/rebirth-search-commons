/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ImmutableBlobContainer.java 2012-3-29 15:15:14 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.blobstore;

import java.io.IOException;
import java.io.InputStream;


/**
 * The Interface ImmutableBlobContainer.
 *
 * @author l.xue.nong
 */
public interface ImmutableBlobContainer extends BlobContainer {

    /**
     * The listener interface for receiving writer events.
     * The class that is interested in processing a writer
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>addWriterListener<code> method. When
     * the writer event occurs, that object's appropriate
     * method is invoked.
     *
     * @see WriterEvent
     */
    interface WriterListener {
        
        /**
         * On completed.
         */
        void onCompleted();

        /**
         * On failure.
         *
         * @param t the t
         */
        void onFailure(Throwable t);
    }

    /**
     * Write blob.
     *
     * @param blobName the blob name
     * @param is the is
     * @param sizeInBytes the size in bytes
     * @param listener the listener
     */
    void writeBlob(String blobName, InputStream is, long sizeInBytes, WriterListener listener);

    /**
     * Write blob.
     *
     * @param blobName the blob name
     * @param is the is
     * @param sizeInBytes the size in bytes
     * @throws IOException Signals that an I/O exception has occurred.
     */
    void writeBlob(String blobName, InputStream is, long sizeInBytes) throws IOException;
}
