/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons UUID.java 2012-3-29 15:15:10 l.xue.nong$$
 */

package cn.com.rebirth.search.commons;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import cn.com.rebirth.commons.exception.RestartIllegalStateException;



/**
 * The Class UUID.
 *
 * @author l.xue.nong
 */
public class UUID implements Comparable<UUID> {

	
	
	/** The most sig bits. */
	private final long mostSigBits;

	
	
	/** The least sig bits. */
	private final long leastSigBits;

	
	
	/** The number generator. */
	private static volatile SecureRandom numberGenerator = null;

	

	

	
	/**
	 * Instantiates a new uUID.
	 *
	 * @param data the data
	 */
	private UUID(byte[] data) {
		long msb = 0;
		long lsb = 0;
		assert data.length == 16;
		for (int i = 0; i < 8; i++)
			msb = (msb << 8) | (data[i] & 0xff);
		for (int i = 8; i < 16; i++)
			lsb = (lsb << 8) | (data[i] & 0xff);
		this.mostSigBits = msb;
		this.leastSigBits = lsb;
	}

	
	/**
	 * Instantiates a new uUID.
	 *
	 * @param mostSigBits the most sig bits
	 * @param leastSigBits the least sig bits
	 */
	public UUID(long mostSigBits, long leastSigBits) {
		this.mostSigBits = mostSigBits;
		this.leastSigBits = leastSigBits;
	}

	
	/**
	 * Random uuid.
	 *
	 * @return the uUID
	 */
	public static UUID randomUUID() {
		SecureRandom ng = numberGenerator;
		if (ng == null) {
			numberGenerator = ng = new SecureRandom();
		}

		byte[] randomBytes = new byte[16];
		ng.nextBytes(randomBytes);
		randomBytes[6] &= 0x0f; 
		randomBytes[6] |= 0x40; 
		randomBytes[8] &= 0x3f; 
		randomBytes[8] |= 0x80; 
		return new UUID(randomBytes);
	}

	
	/**
	 * Random base64 uuid.
	 *
	 * @return the string
	 */
	public static String randomBase64UUID() {
		SecureRandom ng = numberGenerator;
		if (ng == null) {
			numberGenerator = ng = new SecureRandom();
		}

		byte[] randomBytes = new byte[16];
		ng.nextBytes(randomBytes);
		randomBytes[6] &= 0x0f; 
		randomBytes[6] |= 0x40; 
		randomBytes[8] &= 0x3f; 
		randomBytes[8] |= 0x80; 

		try {
			byte[] encoded = Base64.encodeBytesToBytes(randomBytes, 0, randomBytes.length, Base64.URL_SAFE);
			
			assert encoded[encoded.length - 1] == '=';
			assert encoded[encoded.length - 2] == '=';
			
			return new String(encoded, 0, encoded.length - 2, Base64.PREFERRED_ENCODING);
		} catch (IOException e) {
			throw new RestartIllegalStateException("should not be thrown");
		}
	}

	
	/**
	 * Name uuid from bytes.
	 *
	 * @param name the name
	 * @return the uUID
	 */
	public static UUID nameUUIDFromBytes(byte[] name) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException nsae) {
			throw new InternalError("MD5 not supported");
		}
		byte[] md5Bytes = md.digest(name);
		md5Bytes[6] &= 0x0f; 
		md5Bytes[6] |= 0x30; 
		md5Bytes[8] &= 0x3f; 
		md5Bytes[8] |= 0x80; 
		return new UUID(md5Bytes);
	}

	
	/**
	 * From string.
	 *
	 * @param name the name
	 * @return the uUID
	 */
	public static UUID fromString(String name) {
		String[] components = name.split("-");
		if (components.length != 5)
			throw new IllegalArgumentException("Invalid UUID string: " + name);
		for (int i = 0; i < 5; i++)
			components[i] = "0x" + components[i];

		long mostSigBits = Long.decode(components[0]).longValue();
		mostSigBits <<= 16;
		mostSigBits |= Long.decode(components[1]).longValue();
		mostSigBits <<= 16;
		mostSigBits |= Long.decode(components[2]).longValue();

		long leastSigBits = Long.decode(components[3]).longValue();
		leastSigBits <<= 48;
		leastSigBits |= Long.decode(components[4]).longValue();

		return new UUID(mostSigBits, leastSigBits);
	}

	

	
	/**
	 * Gets the least significant bits.
	 *
	 * @return the least significant bits
	 */
	public long getLeastSignificantBits() {
		return leastSigBits;
	}

	
	/**
	 * Gets the most significant bits.
	 *
	 * @return the most significant bits
	 */
	public long getMostSignificantBits() {
		return mostSigBits;
	}

	

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return (digits(mostSigBits >> 32, 8) + "-" + digits(mostSigBits >> 16, 4) + "-" + digits(mostSigBits, 4) + "-"
				+ digits(leastSigBits >> 48, 4) + "-" + digits(leastSigBits, 12));
	}

	
	/**
	 * Digits.
	 *
	 * @param val the val
	 * @param digits the digits
	 * @return the string
	 */
	private static String digits(long val, int digits) {
		long hi = 1L << (digits * 4);
		return Long.toHexString(hi | (val & (hi - 1))).substring(1);
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return (int) ((mostSigBits >> 32) ^ mostSigBits ^ (leastSigBits >> 32) ^ leastSigBits);
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof UUID))
			return false;
		UUID id = (UUID) obj;
		return (mostSigBits == id.mostSigBits && leastSigBits == id.leastSigBits);
	}

	

	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(UUID val) {
		
		
		return (this.mostSigBits < val.mostSigBits ? -1 : (this.mostSigBits > val.mostSigBits ? 1
				: (this.leastSigBits < val.leastSigBits ? -1 : (this.leastSigBits > val.leastSigBits ? 1 : 0))));
	}
}
