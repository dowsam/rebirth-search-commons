/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons DummyTransportAddress.java 2012-3-29 15:15:16 l.xue.nong$$
 */
package cn.com.restart.search.commons.transport;

import java.io.IOException;

import cn.com.restart.commons.io.stream.StreamInput;
import cn.com.restart.commons.io.stream.StreamOutput;


/**
 * The Class DummyTransportAddress.
 *
 * @author l.xue.nong
 */
public class DummyTransportAddress implements TransportAddress {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7799059385236236258L;
	
	
	/** The Constant INSTANCE. */
	public static final DummyTransportAddress INSTANCE = new DummyTransportAddress();

	
	/**
	 * Instantiates a new dummy transport address.
	 */
	DummyTransportAddress() {
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.transport.TransportAddress#uniqueAddressTypeId()
	 */
	@Override
	public short uniqueAddressTypeId() {
		return 0;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.transport.TransportAddress#match(java.lang.String)
	 */
	@Override
	public boolean match(String otherAddress) {
		return false;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.transport.TransportAddress#sameHost(cn.com.summall.search.commons.transport.TransportAddress)
	 */
	@Override
	public boolean sameHost(TransportAddress otherAddress) {
		return false;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.io.stream.Streamable#readFrom(cn.com.summall.search.commons.io.stream.StreamInput)
	 */
	@Override
	public void readFrom(StreamInput in) throws IOException {
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.io.stream.Streamable#writeTo(cn.com.summall.search.commons.io.stream.StreamOutput)
	 */
	@Override
	public void writeTo(StreamOutput out) throws IOException {
	}
}
