/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons TransportAddress.java 2012-3-29 15:15:09 l.xue.nong$$
 */
package cn.com.restart.search.commons.transport;

import java.io.Serializable;

import cn.com.restart.commons.io.stream.Streamable;


/**
 * The Interface TransportAddress.
 *
 * @author l.xue.nong
 */
public interface TransportAddress extends Streamable, Serializable {

	
	/**
	 * Unique address type id.
	 *
	 * @return the short
	 */
	short uniqueAddressTypeId();

	
	/**
	 * Match.
	 *
	 * @param otherAddress the other address
	 * @return true, if successful
	 */
	boolean match(String otherAddress);

	
	/**
	 * Same host.
	 *
	 * @param other the other
	 * @return true, if successful
	 */
	boolean sameHost(TransportAddress other);
}
