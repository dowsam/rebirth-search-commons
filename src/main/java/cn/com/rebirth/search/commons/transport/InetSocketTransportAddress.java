/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons InetSocketTransportAddress.java 2012-3-29 15:15:10 l.xue.nong$$
 */
package cn.com.rebirth.search.commons.transport;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import cn.com.rebirth.commons.io.stream.StreamInput;
import cn.com.rebirth.commons.io.stream.StreamOutput;
import cn.com.rebirth.commons.regex.Regex;


/**
 * The Class InetSocketTransportAddress.
 *
 * @author l.xue.nong
 */
public class InetSocketTransportAddress implements TransportAddress {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7605454130729764662L;
	
	
	/** The address. */
	private InetSocketAddress address;

	
	/**
	 * Instantiates a new inet socket transport address.
	 */
	InetSocketTransportAddress() {

	}

	
	/**
	 * Instantiates a new inet socket transport address.
	 *
	 * @param hostname the hostname
	 * @param port the port
	 */
	public InetSocketTransportAddress(String hostname, int port) {
		this(new InetSocketAddress(hostname, port));
	}

	
	/**
	 * Instantiates a new inet socket transport address.
	 *
	 * @param address the address
	 * @param port the port
	 */
	public InetSocketTransportAddress(InetAddress address, int port) {
		this(new InetSocketAddress(address, port));
	}

	
	/**
	 * Instantiates a new inet socket transport address.
	 *
	 * @param address the address
	 */
	public InetSocketTransportAddress(InetSocketAddress address) {
		this.address = address;
	}

	
	/**
	 * Read inet socket transport address.
	 *
	 * @param in the in
	 * @return the inet socket transport address
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static InetSocketTransportAddress readInetSocketTransportAddress(StreamInput in) throws IOException {
		InetSocketTransportAddress address = new InetSocketTransportAddress();
		address.readFrom(in);
		return address;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.transport.TransportAddress#uniqueAddressTypeId()
	 */
	@Override
	public short uniqueAddressTypeId() {
		return 1;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.transport.TransportAddress#match(java.lang.String)
	 */
	@Override
	public boolean match(String otherAddress) {
		if (address.getHostName() != null && Regex.simpleMatch(otherAddress, address.getHostName())) {
			return true;
		}
		if (address.getAddress() != null) {
			if (address.getAddress().getHostName() != null
					&& Regex.simpleMatch(otherAddress, address.getAddress().getHostName())) {
				return true;
			}
			if (address.getAddress().getHostAddress() != null
					&& Regex.simpleMatch(otherAddress, address.getAddress().getHostAddress())) {
				return true;
			}
		}
		return false;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.transport.TransportAddress#sameHost(cn.com.summall.search.commons.transport.TransportAddress)
	 */
	@Override
	public boolean sameHost(TransportAddress other) {
		if (!(other instanceof InetSocketTransportAddress)) {
			return false;
		}
		InetSocketTransportAddress otherAddr = (InetSocketTransportAddress) other;
		if (address.isUnresolved() || otherAddr.address().isUnresolved()) {
			String hostName = address.getHostName();
			String otherHostName = otherAddr.address().getHostName();
			return !(hostName == null || otherHostName == null) && hostName.equals(otherHostName);
		}
		return otherAddr.address().getAddress().equals(address.getAddress());
	}

	
	/**
	 * Address.
	 *
	 * @return the inet socket address
	 */
	public InetSocketAddress address() {
		return this.address;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.io.stream.Streamable#readFrom(cn.com.summall.search.commons.io.stream.StreamInput)
	 */
	@Override
	public void readFrom(StreamInput in) throws IOException {
		if (in.readByte() == 0) {
			int len = in.readByte();
			byte[] a = new byte[len]; 
			in.readFully(a);
			InetAddress inetAddress;
			if (len == 16) {
				int scope_id = in.readInt();
				inetAddress = Inet6Address.getByAddress(null, a, scope_id);
			} else {
				inetAddress = InetAddress.getByAddress(a);
			}
			int port = in.readInt();
			this.address = new InetSocketAddress(inetAddress, port);
		} else {
			this.address = new InetSocketAddress(in.readUTF(), in.readInt());
		}
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.io.stream.Streamable#writeTo(cn.com.summall.search.commons.io.stream.StreamOutput)
	 */
	@Override
	public void writeTo(StreamOutput out) throws IOException {
		if (address.getAddress() != null) {
			out.writeByte((byte) 0);
			byte[] bytes = address().getAddress().getAddress(); 
			out.writeByte((byte) bytes.length); 
			out.write(bytes, 0, bytes.length);
			if (address().getAddress() instanceof Inet6Address)
				out.writeInt(((Inet6Address) address.getAddress()).getScopeId());
		} else {
			out.writeByte((byte) 1);
			out.writeUTF(address.getHostName());
		}
		out.writeInt(address.getPort());
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
		InetSocketTransportAddress address1 = (InetSocketTransportAddress) o;
		return address.equals(address1.address);
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return address != null ? address.hashCode() : 0;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "inet[" + address + "]";
	}
}
