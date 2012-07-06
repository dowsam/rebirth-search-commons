/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons BlobStoreException.java 2012-7-6 10:23:50 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.blobstore;

import cn.com.rebirth.commons.exception.RebirthException;


/**
 * The Class BlobStoreException.
 *
 * @author l.xue.nong
 */
public class BlobStoreException extends RebirthException {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8167174056744237317L;

	
	/**
	 * Instantiates a new blob store exception.
	 *
	 * @param msg the msg
	 */
	public BlobStoreException(String msg) {
		super(msg);
	}

	
	/**
	 * Instantiates a new blob store exception.
	 *
	 * @param msg the msg
	 * @param cause the cause
	 */
	public BlobStoreException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
