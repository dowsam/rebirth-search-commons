/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons RamUsage.java 2012-3-29 15:15:07 l.xue.nong$$
 */
package cn.com.restart.search.commons;

/**
 * The Class RamUsage.
 *
 * @author l.xue.nong
 */
public class RamUsage {

	/** The Constant OS_ARCH. */
	private static final String OS_ARCH = System.getProperty("os.arch");

	/** The Constant JRE_IS_64BIT. */
	private static final boolean JRE_IS_64BIT;

	static {
		String x = System.getProperty("sun.arch.data.model");
		if (x != null) {
			JRE_IS_64BIT = x.indexOf("64") != -1;
		} else {
			if (OS_ARCH != null && OS_ARCH.indexOf("64") != -1) {
				JRE_IS_64BIT = true;
			} else {
				JRE_IS_64BIT = false;
			}
		}
	}

	/** The Constant NUM_BYTES_SHORT. */
	public final static int NUM_BYTES_SHORT = 2;

	/** The Constant NUM_BYTES_INT. */
	public final static int NUM_BYTES_INT = 4;

	/** The Constant NUM_BYTES_LONG. */
	public final static int NUM_BYTES_LONG = 8;

	/** The Constant NUM_BYTES_FLOAT. */
	public final static int NUM_BYTES_FLOAT = 4;

	/** The Constant NUM_BYTES_DOUBLE. */
	public final static int NUM_BYTES_DOUBLE = 8;

	/** The Constant NUM_BYTES_CHAR. */
	public final static int NUM_BYTES_CHAR = 2;

	/** The Constant NUM_BYTES_OBJECT_HEADER. */
	public final static int NUM_BYTES_OBJECT_HEADER = 8;

	/** The Constant NUM_BYTES_OBJECT_REF. */
	public final static int NUM_BYTES_OBJECT_REF = JRE_IS_64BIT ? 8 : 4;

	/** The Constant NUM_BYTES_ARRAY_HEADER. */
	public final static int NUM_BYTES_ARRAY_HEADER = NUM_BYTES_OBJECT_HEADER + NUM_BYTES_INT + NUM_BYTES_OBJECT_REF;

}
