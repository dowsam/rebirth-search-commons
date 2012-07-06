/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons NotDocSet.java 2012-7-6 10:23:41 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene.docset;


/**
 * The Class NotDocSet.
 *
 * @author l.xue.nong
 */
public class NotDocSet extends GetDocSet {

	
	/** The set. */
	private final DocSet set;

	
	/**
	 * Instantiates a new not doc set.
	 *
	 * @param set the set
	 * @param max the max
	 */
	public NotDocSet(DocSet set, int max) {
		super(max);
		this.set = set;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.DocIdSet#isCacheable()
	 */
	@Override
	public boolean isCacheable() {
		
		
		return false;
		
	}

	
	/* (non-Javadoc)
	 * @see cn.com.rebirth.search.commons.lucene.docset.DocSet#get(int)
	 */
	@Override
	public boolean get(int doc) {
		return !set.get(doc);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.rebirth.search.commons.lucene.docset.GetDocSet#sizeInBytes()
	 */
	@Override
	public long sizeInBytes() {
		return set.sizeInBytes();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
