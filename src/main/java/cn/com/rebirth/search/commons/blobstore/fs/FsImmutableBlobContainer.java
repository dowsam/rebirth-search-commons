/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons FsImmutableBlobContainer.java 2012-7-6 10:23:53 l.xue.nong$$
 */

package cn.com.rebirth.search.commons.blobstore.fs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import cn.com.rebirth.commons.exception.RebirthIllegalStateException;
import cn.com.rebirth.commons.io.FileSystemUtils;
import cn.com.rebirth.search.commons.blobstore.BlobPath;
import cn.com.rebirth.search.commons.blobstore.ImmutableBlobContainer;
import cn.com.rebirth.search.commons.blobstore.support.BlobStores;

/**
 * The Class FsImmutableBlobContainer.
 *
 * @author l.xue.nong
 */
public class FsImmutableBlobContainer extends AbstractFsBlobContainer implements ImmutableBlobContainer {

	/**
	 * Instantiates a new fs immutable blob container.
	 *
	 * @param blobStore the blob store
	 * @param blobPath the blob path
	 * @param path the path
	 */
	public FsImmutableBlobContainer(FsBlobStore blobStore, BlobPath blobPath, File path) {
		super(blobStore, blobPath, path);
	}

	@Override
	public void writeBlob(final String blobName, final InputStream is, final long sizeInBytes,
			final WriterListener listener) {
		blobStore.executor().execute(new Runnable() {
			@Override
			public void run() {
				File file = new File(path, blobName);
				RandomAccessFile raf;
				try {
					raf = new RandomAccessFile(file, "rw");

					raf.setLength(0);
				} catch (Exception e) {
					listener.onFailure(e);
					return;
				}
				try {
					try {
						long bytesWritten = 0;
						byte[] buffer = new byte[blobStore.bufferSizeInBytes()];
						int bytesRead;
						while ((bytesRead = is.read(buffer)) != -1) {
							raf.write(buffer, 0, bytesRead);
							bytesWritten += bytesRead;
						}
						if (bytesWritten != sizeInBytes) {
							listener.onFailure(new RebirthIllegalStateException("[" + blobName + "]: wrote ["
									+ bytesWritten + "], expected to write [" + sizeInBytes + "]"));
							return;
						}
					} finally {
						try {
							is.close();
						} catch (IOException ex) {

						}
						try {
							raf.close();
						} catch (IOException ex) {

						}
					}
					FileSystemUtils.syncFile(file);
					listener.onCompleted();
				} catch (Exception e) {

					try {
						if (file.exists()) {
							file.delete();
						}
					} catch (Exception e1) {

					}
					listener.onFailure(e);
				}
			}
		});
	}

	@Override
	public void writeBlob(String blobName, InputStream is, long sizeInBytes) throws IOException {
		BlobStores.syncWriteBlob(this, blobName, is, sizeInBytes);
	}
}
