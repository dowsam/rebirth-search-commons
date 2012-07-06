/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons BytesWrap.java 2012-7-6 10:23:53 l.xue.nong$$
 */
package cn.com.rebirth.search.commons;

import java.util.Arrays;

import cn.com.rebirth.commons.Unicode;


/**
 * The Class BytesWrap.
 *
 * @author l.xue.nong
 */
public class BytesWrap {

	
	/** The bytes. */
	private final byte[] bytes;

	
	
	/** The hash code. */
	private final int hashCode;

	
	/**
	 * Instantiates a new bytes wrap.
	 *
	 * @param bytes the bytes
	 */
	public BytesWrap(byte[] bytes) {
		this.bytes = bytes;
		this.hashCode = Arrays.hashCode(bytes);
	}

	
	/**
	 * Instantiates a new bytes wrap.
	 *
	 * @param str the str
	 */
	public BytesWrap(String str) {
		this(Unicode.fromStringAsBytes(str));
	}

	
	/**
	 * Bytes.
	 *
	 * @return the byte[]
	 */
	public byte[] bytes() {
		return this.bytes;
	}

	
	/**
	 * Utf8 to string.
	 *
	 * @return the string
	 */
	public String utf8ToString() {
		return Unicode.fromBytes(bytes);
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		BytesWrap bytesWrap = (BytesWrap) o;
		return Arrays.equals(bytes, bytesWrap.bytes);
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return hashCode;
	}
}
