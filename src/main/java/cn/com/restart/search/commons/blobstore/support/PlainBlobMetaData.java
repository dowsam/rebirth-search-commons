/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons PlainBlobMetaData.java 2012-3-29 15:15:17 l.xue.nong$$
 */


package cn.com.restart.search.commons.blobstore.support;

import cn.com.restart.search.commons.blobstore.BlobMetaData;


/**
 * The Class PlainBlobMetaData.
 *
 * @author l.xue.nong
 */
public class PlainBlobMetaData implements BlobMetaData {

	
	/** The name. */
	private final String name;

	
	/** The length. */
	private final long length;

	
	/**
	 * Instantiates a new plain blob meta data.
	 *
	 * @param name the name
	 * @param length the length
	 */
	public PlainBlobMetaData(String name, long length) {
		this.name = name;
		this.length = length;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.blobstore.BlobMetaData#name()
	 */
	@Override
	public String name() {
		return this.name;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.blobstore.BlobMetaData#length()
	 */
	@Override
	public long length() {
		return this.length;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "name [" + name + "], length [" + length + "]";
	}
}
