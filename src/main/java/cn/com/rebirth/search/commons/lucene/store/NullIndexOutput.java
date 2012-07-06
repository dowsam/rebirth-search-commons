/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons NullIndexOutput.java 2012-3-29 15:15:10 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene.store;

import java.io.IOException;

import org.apache.lucene.store.IndexOutput;


/**
 * The Class NullIndexOutput.
 *
 * @author l.xue.nong
 */
public class NullIndexOutput extends IndexOutput {

	
	/** The length. */
	private long length = 0;

	
	/** The position. */
	private long position = 0;

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.store.DataOutput#writeByte(byte)
	 */
	@Override
	public void writeByte(byte b) throws IOException {
		position++;
		if (position > length) {
			length = position;
		}
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.store.DataOutput#writeBytes(byte[], int, int)
	 */
	@Override
	public void writeBytes(byte[] b, int offset, int length) throws IOException {
		position += length;
		if (position > this.length) {
			this.length = position;
		}
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.store.IndexOutput#flush()
	 */
	@Override
	public void flush() throws IOException {
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.store.IndexOutput#close()
	 */
	@Override
	public void close() throws IOException {
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.store.IndexOutput#getFilePointer()
	 */
	@Override
	public long getFilePointer() {
		return position;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.store.IndexOutput#seek(long)
	 */
	@Override
	public void seek(long pos) throws IOException {
		position = pos;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.store.IndexOutput#length()
	 */
	@Override
	public long length() throws IOException {
		return length;
	}
}
