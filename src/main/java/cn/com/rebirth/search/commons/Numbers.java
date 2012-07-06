/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons Numbers.java 2012-7-6 10:23:51 l.xue.nong$$
 */


package cn.com.rebirth.search.commons;


/**
 * The Class Numbers.
 *
 * @author l.xue.nong
 */
public final class Numbers {

	
	/**
	 * Instantiates a new numbers.
	 */
	private Numbers() {

	}

	
	/**
	 * Bytes to short.
	 *
	 * @param arr the arr
	 * @return the short
	 */
	public static short bytesToShort(byte[] arr) {
		return (short) (((arr[0] & 0xff) << 8) | (arr[1] & 0xff));
	}

	
	/**
	 * Bytes to int.
	 *
	 * @param arr the arr
	 * @return the int
	 */
	public static int bytesToInt(byte[] arr) {
		return (arr[0] << 24) | ((arr[1] & 0xff) << 16) | ((arr[2] & 0xff) << 8) | (arr[3] & 0xff);
	}

	
	/**
	 * Bytes to long.
	 *
	 * @param arr the arr
	 * @return the long
	 */
	public static long bytesToLong(byte[] arr) {
		int high = (arr[0] << 24) | ((arr[1] & 0xff) << 16) | ((arr[2] & 0xff) << 8) | (arr[3] & 0xff);
		int low = (arr[4] << 24) | ((arr[5] & 0xff) << 16) | ((arr[6] & 0xff) << 8) | (arr[7] & 0xff);
		return (((long) high) << 32) | (low & 0x0ffffffffL);
	}

	
	/**
	 * Bytes to float.
	 *
	 * @param arr the arr
	 * @return the float
	 */
	public static float bytesToFloat(byte[] arr) {
		return Float.intBitsToFloat(bytesToInt(arr));
	}

	
	/**
	 * Bytes to double.
	 *
	 * @param arr the arr
	 * @return the double
	 */
	public static double bytesToDouble(byte[] arr) {
		return Double.longBitsToDouble(bytesToLong(arr));
	}

	
	/**
	 * Int to bytes.
	 *
	 * @param val the val
	 * @return the byte[]
	 */
	public static byte[] intToBytes(int val) {
		byte[] arr = new byte[4];
		arr[0] = (byte) (val >>> 24);
		arr[1] = (byte) (val >>> 16);
		arr[2] = (byte) (val >>> 8);
		arr[3] = (byte) (val);
		return arr;
	}

	
	/**
	 * Short to bytes.
	 *
	 * @param val the val
	 * @return the byte[]
	 */
	public static byte[] shortToBytes(int val) {
		byte[] arr = new byte[2];
		arr[0] = (byte) (val >>> 8);
		arr[1] = (byte) (val);
		return arr;
	}

	
	/**
	 * Long to bytes.
	 *
	 * @param val the val
	 * @return the byte[]
	 */
	public static byte[] longToBytes(long val) {
		byte[] arr = new byte[8];
		arr[0] = (byte) (val >>> 56);
		arr[1] = (byte) (val >>> 48);
		arr[2] = (byte) (val >>> 40);
		arr[3] = (byte) (val >>> 32);
		arr[4] = (byte) (val >>> 24);
		arr[5] = (byte) (val >>> 16);
		arr[6] = (byte) (val >>> 8);
		arr[7] = (byte) (val);
		return arr;
	}

	
	/**
	 * Float to bytes.
	 *
	 * @param val the val
	 * @return the byte[]
	 */
	public static byte[] floatToBytes(float val) {
		return intToBytes(Float.floatToRawIntBits(val));
	}

	
	/**
	 * Double to bytes.
	 *
	 * @param val the val
	 * @return the byte[]
	 */
	public static byte[] doubleToBytes(double val) {
		return longToBytes(Double.doubleToRawLongBits(val));
	}

}
