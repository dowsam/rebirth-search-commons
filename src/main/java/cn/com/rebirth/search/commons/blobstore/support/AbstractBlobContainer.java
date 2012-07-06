/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons AbstractBlobContainer.java 2012-3-29 15:15:17 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.blobstore.support;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import cn.com.rebirth.search.commons.blobstore.BlobContainer;
import cn.com.rebirth.search.commons.blobstore.BlobMetaData;
import cn.com.rebirth.search.commons.blobstore.BlobPath;

import com.google.common.collect.ImmutableMap;


/**
 * The Class AbstractBlobContainer.
 *
 * @author l.xue.nong
 */
public abstract class AbstractBlobContainer implements BlobContainer {

	
	/** The path. */
	private final BlobPath path;

	
	/**
	 * Instantiates a new abstract blob container.
	 *
	 * @param path the path
	 */
	protected AbstractBlobContainer(BlobPath path) {
		this.path = path;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.blobstore.BlobContainer#path()
	 */
	@Override
	public BlobPath path() {
		return this.path;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.blobstore.BlobContainer#readBlobFully(java.lang.String)
	 */
	@Override
	public byte[] readBlobFully(String blobName) throws IOException {
		final CountDownLatch latch = new CountDownLatch(1);
		final AtomicReference<Throwable> failure = new AtomicReference<Throwable>();
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();

		readBlob(blobName, new ReadBlobListener() {
			@Override
			public void onPartial(byte[] data, int offset, int size) {
				bos.write(data, offset, size);
			}

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
			throw new InterruptedIOException("Interrupted while waiting to read [" + blobName + "]");
		}

		if (failure.get() != null) {
			if (failure.get() instanceof IOException) {
				throw (IOException) failure.get();
			} else {
				throw new IOException("Failed to get [" + blobName + "]", failure.get());
			}
		}
		return bos.toByteArray();
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.blobstore.BlobContainer#listBlobsByPrefix(java.lang.String)
	 */
	@Override
	public ImmutableMap<String, BlobMetaData> listBlobsByPrefix(String blobNamePrefix) throws IOException {
		ImmutableMap<String, BlobMetaData> allBlobs = listBlobs();
		ImmutableMap.Builder<String, BlobMetaData> blobs = ImmutableMap.builder();
		for (BlobMetaData blob : allBlobs.values()) {
			if (blob.name().startsWith(blobNamePrefix)) {
				blobs.put(blob.name(), blob);
			}
		}
		return blobs.build();
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.blobstore.BlobContainer#deleteBlobsByPrefix(java.lang.String)
	 */
	@Override
	public void deleteBlobsByPrefix(final String blobNamePrefix) throws IOException {
		deleteBlobsByFilter(new BlobNameFilter() {
			@Override
			public boolean accept(String blobName) {
				return blobName.startsWith(blobNamePrefix);
			}
		});
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.blobstore.BlobContainer#deleteBlobsByFilter(cn.com.summall.search.commons.blobstore.BlobContainer.BlobNameFilter)
	 */
	@Override
	public void deleteBlobsByFilter(BlobNameFilter filter) throws IOException {
		ImmutableMap<String, BlobMetaData> blobs = listBlobs();
		for (BlobMetaData blob : blobs.values()) {
			if (filter.accept(blob.name())) {
				deleteBlob(blob.name());
			}
		}
	}
}
