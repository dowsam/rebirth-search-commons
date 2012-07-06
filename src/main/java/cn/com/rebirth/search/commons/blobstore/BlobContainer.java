/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons BlobContainer.java 2012-3-29 15:15:09 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.blobstore;

import java.io.IOException;

import com.google.common.collect.ImmutableMap;


/**
 * The Interface BlobContainer.
 *
 * @author l.xue.nong
 */
public interface BlobContainer {

	
	/**
	 * The Interface BlobNameFilter.
	 *
	 * @author l.xue.nong
	 */
	interface BlobNameFilter {

		
		/**
		 * Accept.
		 *
		 * @param blobName the blob name
		 * @return true, if successful
		 */
		boolean accept(String blobName);
	}

	
	/**
	 * The listener interface for receiving readBlob events.
	 * The class that is interested in processing a readBlob
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addReadBlobListener<code> method. When
	 * the readBlob event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see ReadBlobEvent
	 */
	interface ReadBlobListener {

		
		/**
		 * On partial.
		 *
		 * @param data the data
		 * @param offset the offset
		 * @param size the size
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		void onPartial(byte[] data, int offset, int size) throws IOException;

		
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
	 * Path.
	 *
	 * @return the blob path
	 */
	BlobPath path();

	
	/**
	 * Blob exists.
	 *
	 * @param blobName the blob name
	 * @return true, if successful
	 */
	boolean blobExists(String blobName);

	
	/**
	 * Read blob.
	 *
	 * @param blobName the blob name
	 * @param listener the listener
	 */
	void readBlob(String blobName, ReadBlobListener listener);

	
	/**
	 * Read blob fully.
	 *
	 * @param blobName the blob name
	 * @return the byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	byte[] readBlobFully(String blobName) throws IOException;

	
	/**
	 * Delete blob.
	 *
	 * @param blobName the blob name
	 * @return true, if successful
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	boolean deleteBlob(String blobName) throws IOException;

	
	/**
	 * Delete blobs by prefix.
	 *
	 * @param blobNamePrefix the blob name prefix
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void deleteBlobsByPrefix(String blobNamePrefix) throws IOException;

	
	/**
	 * Delete blobs by filter.
	 *
	 * @param filter the filter
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void deleteBlobsByFilter(BlobNameFilter filter) throws IOException;

	
	/**
	 * List blobs.
	 *
	 * @return the immutable map
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	ImmutableMap<String, BlobMetaData> listBlobs() throws IOException;

	
	/**
	 * List blobs by prefix.
	 *
	 * @param blobNamePrefix the blob name prefix
	 * @return the immutable map
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	ImmutableMap<String, BlobMetaData> listBlobsByPrefix(String blobNamePrefix) throws IOException;
}
