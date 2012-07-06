/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons BoundTransportAddress.java 2012-3-29 15:15:08 l.xue.nong$$
 */
package cn.com.rebirth.search.commons.transport;

import java.io.IOException;

import cn.com.rebirth.commons.io.stream.StreamInput;
import cn.com.rebirth.commons.io.stream.StreamOutput;
import cn.com.rebirth.commons.io.stream.Streamable;


/**
 * The Class BoundTransportAddress.
 *
 * @author l.xue.nong
 */
public class BoundTransportAddress implements Streamable {

	
	/** The bound address. */
	private TransportAddress boundAddress;

	
	/** The publish address. */
	private TransportAddress publishAddress;

	
	/**
	 * Instantiates a new bound transport address.
	 */
	BoundTransportAddress() {
	}

	
	/**
	 * Instantiates a new bound transport address.
	 *
	 * @param boundAddress the bound address
	 * @param publishAddress the publish address
	 */
	public BoundTransportAddress(TransportAddress boundAddress, TransportAddress publishAddress) {
		this.boundAddress = boundAddress;
		this.publishAddress = publishAddress;
	}

	
	/**
	 * Bound address.
	 *
	 * @return the transport address
	 */
	public TransportAddress boundAddress() {
		return boundAddress;
	}

	
	/**
	 * Publish address.
	 *
	 * @return the transport address
	 */
	public TransportAddress publishAddress() {
		return publishAddress;
	}

	
	/**
	 * Read bound transport address.
	 *
	 * @param in the in
	 * @return the bound transport address
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static BoundTransportAddress readBoundTransportAddress(StreamInput in) throws IOException {
		BoundTransportAddress addr = new BoundTransportAddress();
		addr.readFrom(in);
		return addr;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.io.stream.Streamable#readFrom(cn.com.summall.search.commons.io.stream.StreamInput)
	 */
	@Override
	public void readFrom(StreamInput in) throws IOException {
		boundAddress = TransportAddressSerializers.addressFromStream(in);
		publishAddress = TransportAddressSerializers.addressFromStream(in);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.io.stream.Streamable#writeTo(cn.com.summall.search.commons.io.stream.StreamOutput)
	 */
	@Override
	public void writeTo(StreamOutput out) throws IOException {
		TransportAddressSerializers.addressToStream(out, boundAddress);
		TransportAddressSerializers.addressToStream(out, publishAddress);
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "bound_address {" + boundAddress + "}, publish_address {" + publishAddress + "}";
	}
}
