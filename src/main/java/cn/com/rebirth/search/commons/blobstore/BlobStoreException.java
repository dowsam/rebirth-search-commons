/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons BlobStoreException.java 2012-3-29 15:15:18 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.blobstore;

import cn.com.rebirth.commons.exception.RestartException;


/**
 * The Class BlobStoreException.
 *
 * @author l.xue.nong
 */
public class BlobStoreException extends RestartException {

	
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
