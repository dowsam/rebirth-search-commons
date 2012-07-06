/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons FixedBitDocSet.java 2012-3-29 15:15:09 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene.docset;

import java.io.IOException;

import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.util.FixedBitSet;

import cn.com.rebirth.search.commons.RamUsage;


/**
 * The Class FixedBitDocSet.
 *
 * @author l.xue.nong
 */
public class FixedBitDocSet extends DocSet {

	
	/** The set. */
	private final FixedBitSet set;

	
	/**
	 * Instantiates a new fixed bit doc set.
	 *
	 * @param set the set
	 */
	public FixedBitDocSet(FixedBitSet set) {
		this.set = set;
	}

	
	/**
	 * Instantiates a new fixed bit doc set.
	 *
	 * @param numBits the num bits
	 */
	public FixedBitDocSet(int numBits) {
		this.set = new FixedBitSet(numBits);
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.DocIdSet#isCacheable()
	 */
	@Override
	public boolean isCacheable() {
		return true;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.util.Bits#length()
	 */
	@Override
	public int length() {
		return set.length();
	}

	
	/**
	 * Sets the.
	 *
	 * @return the fixed bit set
	 */
	public FixedBitSet set() {
		return set;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.lucene.docset.DocSet#get(int)
	 */
	@Override
	public boolean get(int doc) {
		return set.get(doc);
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.DocIdSet#iterator()
	 */
	@Override
	public DocIdSetIterator iterator() throws IOException {
		return set.iterator();
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.lucene.docset.DocSet#sizeInBytes()
	 */
	@Override
	public long sizeInBytes() {
		return set.getBits().length * RamUsage.NUM_BYTES_LONG + RamUsage.NUM_BYTES_ARRAY_HEADER
				+ RamUsage.NUM_BYTES_INT ;
	}
}
