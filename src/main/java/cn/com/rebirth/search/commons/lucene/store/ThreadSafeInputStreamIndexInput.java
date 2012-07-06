/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons ThreadSafeInputStreamIndexInput.java 2012-7-6 10:23:42 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene.store;

import java.io.IOException;

import org.apache.lucene.store.IndexInput;


/**
 * The Class ThreadSafeInputStreamIndexInput.
 *
 * @author l.xue.nong
 */
public class ThreadSafeInputStreamIndexInput extends InputStreamIndexInput {

	
	/**
	 * Instantiates a new thread safe input stream index input.
	 *
	 * @param indexInput the index input
	 * @param limit the limit
	 */
	public ThreadSafeInputStreamIndexInput(IndexInput indexInput, long limit) {
		super(indexInput, limit);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.rebirth.search.commons.lucene.store.InputStreamIndexInput#read(byte[], int, int)
	 */
	@Override
	public synchronized int read(byte[] b, int off, int len) throws IOException {
		return super.read(b, off, len);
	}
}
