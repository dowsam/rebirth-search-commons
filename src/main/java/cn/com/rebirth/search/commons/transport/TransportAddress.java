/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons TransportAddress.java 2012-7-6 10:23:54 l.xue.nong$$
 */
package cn.com.rebirth.search.commons.transport;

import java.io.Serializable;

import cn.com.rebirth.commons.io.stream.Streamable;


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
