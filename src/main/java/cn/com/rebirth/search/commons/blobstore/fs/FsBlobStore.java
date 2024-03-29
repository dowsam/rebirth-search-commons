/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons FsBlobStore.java 2012-7-17 10:46:00 l.xue.nong$$
 */

package cn.com.rebirth.search.commons.blobstore.fs;

import java.io.File;
import java.util.concurrent.Executor;

import cn.com.rebirth.commons.component.AbstractComponent;
import cn.com.rebirth.commons.io.FileSystemUtils;
import cn.com.rebirth.commons.settings.Settings;
import cn.com.rebirth.commons.unit.ByteSizeUnit;
import cn.com.rebirth.commons.unit.ByteSizeValue;
import cn.com.rebirth.search.commons.blobstore.BlobPath;
import cn.com.rebirth.search.commons.blobstore.BlobStore;
import cn.com.rebirth.search.commons.blobstore.BlobStoreException;
import cn.com.rebirth.search.commons.blobstore.ImmutableBlobContainer;

/**
 * The Class FsBlobStore.
 *
 * @author l.xue.nong
 */
public class FsBlobStore extends AbstractComponent implements BlobStore {

	/** The executor. */
	private final Executor executor;

	/** The path. */
	private final File path;

	/** The buffer size in bytes. */
	private final int bufferSizeInBytes;

	/**
	 * Instantiates a new fs blob store.
	 *
	 * @param settings the settings
	 * @param executor the executor
	 * @param path the path
	 */
	public FsBlobStore(Settings settings, Executor executor, File path) {
		super(settings);
		this.path = path;
		if (!path.exists()) {
			boolean b = FileSystemUtils.mkdirs(path);
			if (!b) {
				throw new BlobStoreException("Failed to create directory at [" + path + "]");
			}
		}
		if (!path.isDirectory()) {
			throw new BlobStoreException("Path is not a directory at [" + path + "]");
		}
		this.bufferSizeInBytes = (int) settings.getAsBytesSize("buffer_size", new ByteSizeValue(100, ByteSizeUnit.KB))
				.bytes();
		this.executor = executor;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return path.toString();
	}

	/**
	 * Path.
	 *
	 * @return the file
	 */
	public File path() {
		return path;
	}

	/**
	 * Buffer size in bytes.
	 *
	 * @return the int
	 */
	public int bufferSizeInBytes() {
		return this.bufferSizeInBytes;
	}

	/**
	 * Executor.
	 *
	 * @return the executor
	 */
	public Executor executor() {
		return executor;
	}

	/* (non-Javadoc)
	 * @see cn.com.rebirth.search.commons.blobstore.BlobStore#immutableBlobContainer(cn.com.rebirth.search.commons.blobstore.BlobPath)
	 */
	@Override
	public ImmutableBlobContainer immutableBlobContainer(BlobPath path) {
		return new FsImmutableBlobContainer(this, path, buildAndCreate(path));
	}

	/* (non-Javadoc)
	 * @see cn.com.rebirth.search.commons.blobstore.BlobStore#delete(cn.com.rebirth.search.commons.blobstore.BlobPath)
	 */
	@Override
	public void delete(BlobPath path) {
		FileSystemUtils.deleteRecursively(buildPath(path));
	}

	/* (non-Javadoc)
	 * @see cn.com.rebirth.search.commons.blobstore.BlobStore#close()
	 */
	@Override
	public void close() {

	}

	/**
	 * Builds the and create.
	 *
	 * @param path the path
	 * @return the file
	 */
	private synchronized File buildAndCreate(BlobPath path) {
		File f = buildPath(path);
		FileSystemUtils.mkdirs(f);
		return f;
	}

	/**
	 * Builds the path.
	 *
	 * @param path the path
	 * @return the file
	 */
	private File buildPath(BlobPath path) {
		String[] paths = path.toArray();
		if (paths.length == 0) {
			return path();
		}
		File blobPath = new File(this.path, paths[0]);
		if (paths.length > 1) {
			for (int i = 1; i < paths.length; i++) {
				blobPath = new File(blobPath, paths[i]);
			}
		}
		return blobPath;
	}
}
