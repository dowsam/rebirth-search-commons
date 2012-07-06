/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons PlainBlobMetaData.java 2012-7-6 10:23:52 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.blobstore.support;

import cn.com.rebirth.search.commons.blobstore.BlobMetaData;


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

	
	@Override
	public String name() {
		return this.name;
	}

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
