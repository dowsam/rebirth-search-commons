/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons SimpleBoundaryScanner2.java 2012-7-6 10:23:40 l.xue.nong$$
 */

package cn.com.rebirth.search.commons.lucene.search.vectorhighlight;

import gnu.trove.set.hash.TCharHashSet;

import org.apache.lucene.search.vectorhighlight.BoundaryScanner;


/**
 * The Class SimpleBoundaryScanner2.
 *
 * @author l.xue.nong
 */
public class SimpleBoundaryScanner2 implements BoundaryScanner {

	
	/** The Constant DEFAULT_MAX_SCAN. */
	public static final int DEFAULT_MAX_SCAN = 20;

	
	/** The Constant DEFAULT_BOUNDARY_CHARS. */
	public static final char[] DEFAULT_BOUNDARY_CHARS = { '.', ',', '!', '?', ' ', '\t', '\n' };

	
	/** The Constant DEFAULT. */
	public static final SimpleBoundaryScanner2 DEFAULT = new SimpleBoundaryScanner2();

	
	/** The max scan. */
	public int maxScan;

	
	/** The boundary chars. */
	public TCharHashSet boundaryChars;

	
	/**
	 * Instantiates a new simple boundary scanner2.
	 */
	public SimpleBoundaryScanner2() {
		this(DEFAULT_MAX_SCAN, DEFAULT_BOUNDARY_CHARS);
	}

	
	/**
	 * Instantiates a new simple boundary scanner2.
	 *
	 * @param maxScan the max scan
	 * @param boundaryChars the boundary chars
	 */
	public SimpleBoundaryScanner2(int maxScan, char[] boundaryChars) {
		this.maxScan = maxScan;
		this.boundaryChars = new TCharHashSet(boundaryChars);
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.vectorhighlight.BoundaryScanner#findStartOffset(java.lang.StringBuilder, int)
	 */
	public int findStartOffset(StringBuilder buffer, int start) {
		
		if (start > buffer.length() || start < 1)
			return start;
		int offset, count = maxScan;
		for (offset = start; offset > 0 && count > 0; count--) {
			
			if (boundaryChars.contains(buffer.charAt(offset - 1)))
				return offset;
			offset--;
		}
		
		if (offset == 0) {
			return 0;
		}
		
		return start;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.vectorhighlight.BoundaryScanner#findEndOffset(java.lang.StringBuilder, int)
	 */
	public int findEndOffset(StringBuilder buffer, int start) {
		
		if (start > buffer.length() || start < 0)
			return start;
		int offset, count = maxScan;
		
		for (offset = start; offset < buffer.length() && count > 0; count--) {
			
			if (boundaryChars.contains(buffer.charAt(offset)))
				return offset;
			offset++;
		}
		
		return start;
	}
}
