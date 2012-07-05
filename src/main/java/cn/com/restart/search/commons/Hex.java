/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Hex.java 2012-3-29 15:15:15 l.xue.nong$$
 */


package cn.com.restart.search.commons;

import cn.com.restart.commons.exception.RestartIllegalStateException;



/**
 * The Class Hex.
 *
 * @author l.xue.nong
 */
public class Hex {

	
	/** The Constant DIGITS_LOWER. */
	private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	
	/** The Constant DIGITS_UPPER. */
	private static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F' };

	
	/**
	 * Encode hex.
	 *
	 * @param data the data
	 * @return the char[]
	 */
	public static char[] encodeHex(byte[] data) {
		return encodeHex(data, true);
	}

	
	/**
	 * Encode hex.
	 *
	 * @param data the data
	 * @param toLowerCase the to lower case
	 * @return the char[]
	 */
	public static char[] encodeHex(byte[] data, boolean toLowerCase) {
		return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
	}

	
	/**
	 * Encode hex.
	 *
	 * @param data the data
	 * @param toDigits the to digits
	 * @return the char[]
	 */
	protected static char[] encodeHex(byte[] data, char[] toDigits) {
		int l = data.length;
		char[] out = new char[l << 1];
		
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
			out[j++] = toDigits[0x0F & data[i]];
		}
		return out;
	}

	
	/**
	 * Encode hex string.
	 *
	 * @param data the data
	 * @return the string
	 */
	public static String encodeHexString(byte[] data) {
		return new String(encodeHex(data));
	}

	
	/**
	 * Decode hex.
	 *
	 * @param data the data
	 * @return the byte[]
	 * @throws SumMallSearchIllegalStateException the sum mall search illegal state exception
	 */
	public static byte[] decodeHex(String data) throws RestartIllegalStateException {
		return decodeHex(data.toCharArray());
	}

	
	/**
	 * Decode hex.
	 *
	 * @param data the data
	 * @return the byte[]
	 * @throws SumMallSearchIllegalStateException the sum mall search illegal state exception
	 */
	public static byte[] decodeHex(char[] data) throws RestartIllegalStateException {

		int len = data.length;

		if ((len & 0x01) != 0) {
			throw new RestartIllegalStateException("Odd number of characters.");
		}

		byte[] out = new byte[len >> 1];

		
		for (int i = 0, j = 0; j < len; i++) {
			int f = toDigit(data[j], j) << 4;
			j++;
			f = f | toDigit(data[j], j);
			j++;
			out[i] = (byte) (f & 0xFF);
		}

		return out;
	}

	
	/**
	 * To digit.
	 *
	 * @param ch the ch
	 * @param index the index
	 * @return the int
	 * @throws SumMallSearchIllegalStateException the sum mall search illegal state exception
	 */
	protected static int toDigit(char ch, int index) throws RestartIllegalStateException {
		int digit = Character.digit(ch, 16);
		if (digit == -1) {
			throw new RestartIllegalStateException("Illegal hexadecimal character " + ch + " at index " + index);
		}
		return digit;
	}
}
