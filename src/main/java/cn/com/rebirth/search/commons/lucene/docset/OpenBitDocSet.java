/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons OpenBitDocSet.java 2012-3-29 15:15:21 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene.docset;

import java.io.IOException;

import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.util.OpenBitSet;
import org.apache.lucene.util.OpenBitSetDISI;

import cn.com.rebirth.search.commons.RamUsage;


/**
 * The Class OpenBitDocSet.
 *
 * @author l.xue.nong
 */
public class OpenBitDocSet extends DocSet {

	
	/** The set. */
	private final OpenBitSet set;

	
	/**
	 * Instantiates a new open bit doc set.
	 *
	 * @param set the set
	 */
	public OpenBitDocSet(OpenBitSet set) {
		this.set = set;
	}

	
	/**
	 * Instantiates a new open bit doc set.
	 *
	 * @param numBits the num bits
	 */
	public OpenBitDocSet(int numBits) {
		this.set = new OpenBitSetDISI(numBits);
	}

	
	/**
	 * Instantiates a new open bit doc set.
	 *
	 * @param disi the disi
	 * @param numBits the num bits
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public OpenBitDocSet(DocIdSetIterator disi, int numBits) throws IOException {
		this.set = new OpenBitSetDISI(disi, numBits);
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.util.Bits#length()
	 */
	@Override
	public int length() {
		return set.length();
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.DocIdSet#isCacheable()
	 */
	@Override
	public boolean isCacheable() {
		return true;
	}

	
	/**
	 * Sets the.
	 *
	 * @return the open bit set
	 */
	public OpenBitSet set() {
		return set;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.lucene.docset.DocSet#get(int)
	 */
	@Override
	public boolean get(int doc) {
		return set.fastGet(doc);
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
