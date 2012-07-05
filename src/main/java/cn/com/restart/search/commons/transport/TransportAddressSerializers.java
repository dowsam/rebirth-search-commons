/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons TransportAddressSerializers.java 2012-3-29 15:15:13 l.xue.nong$$
 */
package cn.com.restart.search.commons.transport;

import java.io.IOException;
import java.lang.reflect.Constructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.restart.commons.collect.MapBuilder;
import cn.com.restart.commons.exception.RestartIllegalStateException;
import cn.com.restart.commons.io.stream.StreamInput;
import cn.com.restart.commons.io.stream.StreamOutput;

import com.google.common.collect.ImmutableMap;


/**
 * The Class TransportAddressSerializers.
 *
 * @author l.xue.nong
 */
public abstract class TransportAddressSerializers {

	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(TransportAddressSerializers.class);

	
	/** The address constructors. */
	private static ImmutableMap<Short, Constructor<? extends TransportAddress>> addressConstructors = ImmutableMap.of();

	static {
		try {
			addAddressType(DummyTransportAddress.INSTANCE);
			addAddressType(new InetSocketTransportAddress());
			addAddressType(new LocalTransportAddress());
		} catch (Exception e) {
			logger.warn("Failed to add InetSocketTransportAddress", e);
		}
	}

	
	/**
	 * Adds the address type.
	 *
	 * @param address the address
	 * @throws Exception the exception
	 */
	public static synchronized void addAddressType(TransportAddress address) throws Exception {
		if (addressConstructors.containsKey(address.uniqueAddressTypeId())) {
			throw new RestartIllegalStateException("Address [" + address.uniqueAddressTypeId()
					+ "] already bound");
		}
		Constructor<? extends TransportAddress> constructor = address.getClass().getDeclaredConstructor();
		constructor.setAccessible(true);
		addressConstructors = MapBuilder.newMapBuilder(addressConstructors)
				.put(address.uniqueAddressTypeId(), constructor).immutableMap();
	}

	
	/**
	 * Address from stream.
	 *
	 * @param input the input
	 * @return the transport address
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static TransportAddress addressFromStream(StreamInput input) throws IOException {
		short addressUniqueId = input.readShort();
		Constructor<? extends TransportAddress> constructor = addressConstructors.get(addressUniqueId);
		if (constructor == null) {
			throw new IOException("No transport address mapped to [" + addressUniqueId + "]");
		}
		TransportAddress address;
		try {
			address = constructor.newInstance();
		} catch (Exception e) {
			throw new IOException("Failed to create class with constructor [" + constructor + "]", e);
		}
		address.readFrom(input);
		return address;
	}

	
	/**
	 * Address to stream.
	 *
	 * @param out the out
	 * @param address the address
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void addressToStream(StreamOutput out, TransportAddress address) throws IOException {
		out.writeShort(address.uniqueAddressTypeId());
		address.writeTo(out);
	}
}
