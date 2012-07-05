/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons BooleanStreamable.java 2012-3-29 15:15:17 l.xue.nong$$
 */
package cn.com.restart.search.commons.io;

import java.io.IOException;

import cn.com.restart.commons.io.stream.StreamInput;
import cn.com.restart.commons.io.stream.StreamOutput;
import cn.com.restart.commons.io.stream.Streamable;


/**
 * The Class BooleanStreamable.
 *
 * @author l.xue.nong
 */
public class BooleanStreamable implements Streamable {

	
	/** The value. */
	private boolean value;

	
	/**
	 * Instantiates a new boolean streamable.
	 */
	public BooleanStreamable() {
	}

	
	/**
	 * Instantiates a new boolean streamable.
	 *
	 * @param value the value
	 */
	public BooleanStreamable(boolean value) {
		this.value = value;
	}

	
	/**
	 * Sets the.
	 *
	 * @param newValue the new value
	 */
	public void set(boolean newValue) {
		value = newValue;
	}

	
	/**
	 * Gets the.
	 *
	 * @return true, if successful
	 */
	public boolean get() {
		return this.value;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.io.stream.Streamable#readFrom(cn.com.summall.search.commons.io.stream.StreamInput)
	 */
	@Override
	public void readFrom(StreamInput in) throws IOException {
		value = in.readBoolean();
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.io.stream.Streamable#writeTo(cn.com.summall.search.commons.io.stream.StreamOutput)
	 */
	@Override
	public void writeTo(StreamOutput out) throws IOException {
		out.writeBoolean(value);
	}
}
