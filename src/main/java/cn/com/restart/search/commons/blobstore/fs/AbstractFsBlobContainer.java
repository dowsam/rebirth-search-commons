/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons AbstractFsBlobContainer.java 2012-3-29 15:15:15 l.xue.nong$$
 */


package cn.com.restart.search.commons.blobstore.fs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import cn.com.restart.commons.collect.MapBuilder;
import cn.com.restart.search.commons.blobstore.BlobMetaData;
import cn.com.restart.search.commons.blobstore.BlobPath;
import cn.com.restart.search.commons.blobstore.support.AbstractBlobContainer;
import cn.com.restart.search.commons.blobstore.support.PlainBlobMetaData;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.Closeables;


/**
 * The Class AbstractFsBlobContainer.
 *
 * @author l.xue.nong
 */
public abstract class AbstractFsBlobContainer extends AbstractBlobContainer {

	
	/** The blob store. */
	protected final FsBlobStore blobStore;

	
	/** The path. */
	protected final File path;

	
	/**
	 * Instantiates a new abstract fs blob container.
	 *
	 * @param blobStore the blob store
	 * @param blobPath the blob path
	 * @param path the path
	 */
	public AbstractFsBlobContainer(FsBlobStore blobStore, BlobPath blobPath, File path) {
		super(blobPath);
		this.blobStore = blobStore;
		this.path = path;
	}

	
	/**
	 * File path.
	 *
	 * @return the file
	 */
	public File filePath() {
		return this.path;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.blobstore.BlobContainer#listBlobs()
	 */
	public ImmutableMap<String, BlobMetaData> listBlobs() throws IOException {
		File[] files = path.listFiles();
		if (files == null || files.length == 0) {
			return ImmutableMap.of();
		}
		
		MapBuilder<String, BlobMetaData> builder = MapBuilder.newMapBuilder();
		for (File file : files) {
			if (file.isFile()) {
				builder.put(file.getName(), new PlainBlobMetaData(file.getName(), file.length()));
			}
		}
		return builder.immutableMap();
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.blobstore.BlobContainer#deleteBlob(java.lang.String)
	 */
	public boolean deleteBlob(String blobName) throws IOException {
		return new File(path, blobName).delete();
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.blobstore.BlobContainer#blobExists(java.lang.String)
	 */
	@Override
	public boolean blobExists(String blobName) {
		return new File(path, blobName).exists();
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.blobstore.BlobContainer#readBlob(java.lang.String, cn.com.summall.search.commons.blobstore.BlobContainer.ReadBlobListener)
	 */
	@Override
	public void readBlob(final String blobName, final ReadBlobListener listener) {
		blobStore.executor().execute(new Runnable() {
			@Override
			public void run() {
				byte[] buffer = new byte[blobStore.bufferSizeInBytes()];
				FileInputStream is = null;
				try {
					is = new FileInputStream(new File(path, blobName));
				} catch (FileNotFoundException e) {
					Closeables.closeQuietly(is);
					listener.onFailure(e);
					return;
				}
				try {
					int bytesRead;
					while ((bytesRead = is.read(buffer)) != -1) {
						listener.onPartial(buffer, 0, bytesRead);
					}
					listener.onCompleted();
				} catch (Exception e) {
					Closeables.closeQuietly(is);
					listener.onFailure(e);
				}
			}
		});
	}
}
