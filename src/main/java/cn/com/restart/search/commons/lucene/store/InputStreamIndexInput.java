/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons InputStreamIndexInput.java 2012-3-29 15:15:20 l.xue.nong$$
 */


package cn.com.restart.search.commons.lucene.store;

import java.io.IOException;
import java.io.InputStream;

import org.apache.lucene.store.IndexInput;


/**
 * The Class InputStreamIndexInput.
 *
 * @author l.xue.nong
 */
public class InputStreamIndexInput extends InputStream {

	
	/** The index input. */
	private final IndexInput indexInput;

	
	/** The limit. */
	private final long limit;

	
	/** The actual size to read. */
	private final long actualSizeToRead;

	
	/** The counter. */
	private long counter = 0;

	
	/** The mark pointer. */
	private long markPointer;

	
	/** The mark counter. */
	private long markCounter;

	
	/**
	 * Instantiates a new input stream index input.
	 *
	 * @param indexInput the index input
	 * @param limit the limit
	 */
	public InputStreamIndexInput(IndexInput indexInput, long limit) {
		this.indexInput = indexInput;
		this.limit = limit;
		if ((indexInput.length() - indexInput.getFilePointer()) > limit) {
			actualSizeToRead = limit;
		} else {
			actualSizeToRead = indexInput.length() - indexInput.getFilePointer();
		}
	}

	
	/**
	 * Actual size to read.
	 *
	 * @return the long
	 */
	public long actualSizeToRead() {
		return actualSizeToRead;
	}

	
	/* (non-Javadoc)
	 * @see java.io.InputStream#read(byte[], int, int)
	 */
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		if (b == null) {
			throw new NullPointerException();
		} else if (off < 0 || len < 0 || len > b.length - off) {
			throw new IndexOutOfBoundsException();
		}
		if (indexInput.getFilePointer() >= indexInput.length()) {
			return -1;
		}
		if (indexInput.getFilePointer() + len > indexInput.length()) {
			len = (int) (indexInput.length() - indexInput.getFilePointer());
		}
		if (counter + len > limit) {
			len = (int) (limit - counter);
		}
		if (len <= 0) {
			return -1;
		}
		indexInput.readBytes(b, off, len, false);
		counter += len;
		return len;
	}

	
	/* (non-Javadoc)
	 * @see java.io.InputStream#read()
	 */
	@Override
	public int read() throws IOException {
		if (counter++ >= limit) {
			return -1;
		}
		return (indexInput.getFilePointer() < indexInput.length()) ? (indexInput.readByte() & 0xff) : -1;
	}

	
	/* (non-Javadoc)
	 * @see java.io.InputStream#markSupported()
	 */
	@Override
	public boolean markSupported() {
		return true;
	}

	
	/* (non-Javadoc)
	 * @see java.io.InputStream#mark(int)
	 */
	@Override
	public synchronized void mark(int readlimit) {
		markPointer = indexInput.getFilePointer();
		markCounter = counter;
	}

	
	/* (non-Javadoc)
	 * @see java.io.InputStream#reset()
	 */
	@Override
	public synchronized void reset() throws IOException {
		indexInput.seek(markPointer);
		counter = markCounter;
	}
}
