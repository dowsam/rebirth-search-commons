/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons DummyTransportAddress.java 2012-7-6 10:23:44 l.xue.nong$$
 */
package cn.com.rebirth.search.commons.transport;

import java.io.IOException;

import cn.com.rebirth.commons.io.stream.StreamInput;
import cn.com.rebirth.commons.io.stream.StreamOutput;


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
	 * @see cn.com.rebirth.search.commons.transport.TransportAddress#uniqueAddressTypeId()
	 */
	@Override
	public short uniqueAddressTypeId() {
		return 0;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.rebirth.search.commons.transport.TransportAddress#match(java.lang.String)
	 */
	@Override
	public boolean match(String otherAddress) {
		return false;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.rebirth.search.commons.transport.TransportAddress#sameHost(cn.com.rebirth.search.commons.transport.TransportAddress)
	 */
	@Override
	public boolean sameHost(TransportAddress otherAddress) {
		return false;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.rebirth.search.commons.io.stream.Streamable#readFrom(cn.com.rebirth.search.commons.io.stream.StreamInput)
	 */
	@Override
	public void readFrom(StreamInput in) throws IOException {
	}

	
	/* (non-Javadoc)
	 * @see cn.com.rebirth.search.commons.io.stream.Streamable#writeTo(cn.com.rebirth.search.commons.io.stream.StreamOutput)
	 */
	@Override
	public void writeTo(StreamOutput out) throws IOException {
	}
}
