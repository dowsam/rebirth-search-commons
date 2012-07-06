/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons AllDocSet.java 2012-7-6 10:23:51 l.xue.nong$$
 */
package cn.com.rebirth.search.commons.lucene.docset;

import java.io.IOException;

import org.apache.lucene.search.DocIdSetIterator;

import cn.com.rebirth.search.commons.RamUsage;


/**
 * The Class AllDocSet.
 *
 * @author l.xue.nong
 */
public class AllDocSet extends DocSet {

	
	/** The max doc. */
	private final int maxDoc;

	
	/**
	 * Instantiates a new all doc set.
	 *
	 * @param maxDoc the max doc
	 */
	public AllDocSet(int maxDoc) {
		this.maxDoc = maxDoc;
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
		return maxDoc;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.rebirth.search.commons.lucene.docset.DocSet#get(int)
	 */
	@Override
	public boolean get(int doc) {
		return doc < maxDoc;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.rebirth.search.commons.lucene.docset.DocSet#sizeInBytes()
	 */
	@Override
	public long sizeInBytes() {
		return RamUsage.NUM_BYTES_INT;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.DocIdSet#iterator()
	 */
	@Override
	public DocIdSetIterator iterator() throws IOException {
		return new AllDocIdSetIterator(maxDoc);
	}

	
	/**
	 * The Class AllDocIdSetIterator.
	 *
	 * @author l.xue.nong
	 */
	public static final class AllDocIdSetIterator extends DocIdSetIterator {

		
		/** The max doc. */
		private final int maxDoc;

		
		/** The doc. */
		private int doc = -1;

		
		/**
		 * Instantiates a new all doc id set iterator.
		 *
		 * @param maxDoc the max doc
		 */
		public AllDocIdSetIterator(int maxDoc) {
			this.maxDoc = maxDoc;
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.DocIdSetIterator#docID()
		 */
		@Override
		public int docID() {
			return doc;
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.DocIdSetIterator#nextDoc()
		 */
		@Override
		public int nextDoc() throws IOException {
			if (++doc < maxDoc) {
				return doc;
			}
			return doc = NO_MORE_DOCS;
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.DocIdSetIterator#advance(int)
		 */
		@Override
		public int advance(int target) throws IOException {
			doc = target;
			if (doc < maxDoc) {
				return doc;
			}
			return doc = NO_MORE_DOCS;
		}
	}
}
