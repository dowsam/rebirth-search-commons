/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons BlobStores.java 2012-7-6 10:23:53 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.blobstore.support;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import cn.com.rebirth.search.commons.blobstore.ImmutableBlobContainer;


/**
 * The Class BlobStores.
 *
 * @author l.xue.nong
 */
public class BlobStores {

	
	/**
	 * Sync write blob.
	 *
	 * @param blobContainer the blob container
	 * @param blobName the blob name
	 * @param is the is
	 * @param sizeInBytes the size in bytes
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void syncWriteBlob(ImmutableBlobContainer blobContainer, String blobName, InputStream is,
			long sizeInBytes) throws IOException {
		final CountDownLatch latch = new CountDownLatch(1);
		final AtomicReference<Throwable> failure = new AtomicReference<Throwable>();
		blobContainer.writeBlob(blobName, is, sizeInBytes, new ImmutableBlobContainer.WriterListener() {
			@Override
			public void onCompleted() {
				latch.countDown();
			}

			@Override
			public void onFailure(Throwable t) {
				failure.set(t);
				latch.countDown();
			}
		});
		try {
			latch.await();
		} catch (InterruptedException e) {
			throw new InterruptedIOException("Interrupted while waiting to write [" + blobName + "]");
		}

		if (failure.get() != null) {
			if (failure.get() instanceof IOException) {
				throw (IOException) failure.get();
			} else {
				throw new IOException("Failed to get [" + blobName + "]", failure.get());
			}
		}
	}
}
