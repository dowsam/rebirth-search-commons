/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons LocalTransportAddress.java 2012-7-6 10:23:41 l.xue.nong$$
 */
package cn.com.rebirth.search.commons.transport;

import java.io.IOException;

import cn.com.rebirth.commons.io.stream.StreamInput;
import cn.com.rebirth.commons.io.stream.StreamOutput;


/**
 * The Class LocalTransportAddress.
 *
 * @author l.xue.nong
 */
public class LocalTransportAddress implements TransportAddress {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4960057841868234609L;
	
	/** The id. */
	private String id;

	
	/**
	 * Instantiates a new local transport address.
	 */
	LocalTransportAddress() {
	}

	
	/**
	 * Instantiates a new local transport address.
	 *
	 * @param id the id
	 */
	public LocalTransportAddress(String id) {
		this.id = id;
	}

	
	/**
	 * Id.
	 *
	 * @return the string
	 */
	public String id() {
		return this.id;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.rebirth.search.commons.transport.TransportAddress#uniqueAddressTypeId()
	 */
	@Override
	public short uniqueAddressTypeId() {
		return 2;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.rebirth.search.commons.transport.TransportAddress#match(java.lang.String)
	 */
	@Override
	public boolean match(String otherAddress) {
		return id.equals(otherAddress);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.rebirth.search.commons.transport.TransportAddress#sameHost(cn.com.rebirth.search.commons.transport.TransportAddress)
	 */
	@Override
	public boolean sameHost(TransportAddress other) {
		return true;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.rebirth.search.commons.io.stream.Streamable#readFrom(cn.com.rebirth.search.commons.io.stream.StreamInput)
	 */
	@Override
	public void readFrom(StreamInput in) throws IOException {
		id = in.readUTF();
	}

	
	/* (non-Javadoc)
	 * @see cn.com.rebirth.search.commons.io.stream.Streamable#writeTo(cn.com.rebirth.search.commons.io.stream.StreamOutput)
	 */
	@Override
	public void writeTo(StreamOutput out) throws IOException {
		out.writeUTF(id);
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		LocalTransportAddress that = (LocalTransportAddress) o;

		if (id != null ? !id.equals(that.id) : that.id != null)
			return false;

		return true;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "local[" + id + "]";
	}
}
