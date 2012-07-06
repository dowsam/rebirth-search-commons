/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons NotDocIdSet.java 2012-7-6 10:23:46 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene.docset;

import java.io.IOException;

import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.DocIdSetIterator;


/**
 * The Class NotDocIdSet.
 *
 * @author l.xue.nong
 */
public class NotDocIdSet extends DocIdSet {

	
	/** The set. */
	private final DocIdSet set;

	
	/** The max. */
	private final int max;

	
	/**
	 * Instantiates a new not doc id set.
	 *
	 * @param set the set
	 * @param max the max
	 */
	public NotDocIdSet(DocIdSet set, int max) {
		this.max = max;
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
	 * @see org.apache.lucene.search.DocIdSet#iterator()
	 */
	@Override
	public DocIdSetIterator iterator() throws IOException {
		DocIdSetIterator it = set.iterator();
		if (it == null) {
			return new AllDocSet.AllDocIdSetIterator(max);
		}
		return new NotDocIdSetIterator(max, it);
	}

	
	/**
	 * The Class NotDocIdSetIterator.
	 *
	 * @author l.xue.nong
	 */
	public static class NotDocIdSetIterator extends DocIdSetIterator {

		
		/** The max. */
		private final int max;

		
		/** The it1. */
		private DocIdSetIterator it1;

		
		/** The last return. */
		int lastReturn = -1;

		
		/** The inner docid. */
		private int innerDocid = -1;

		
		/**
		 * Instantiates a new not doc id set iterator.
		 *
		 * @param max the max
		 * @param it the it
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		NotDocIdSetIterator(int max, DocIdSetIterator it) throws IOException {
			this.max = max;
			this.it1 = it;
			if ((innerDocid = it1.nextDoc()) == DocIdSetIterator.NO_MORE_DOCS)
				it1 = null;
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.DocIdSetIterator#docID()
		 */
		@Override
		public int docID() {
			return lastReturn;
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.DocIdSetIterator#nextDoc()
		 */
		@Override
		public int nextDoc() throws IOException {
			return advance(0);
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.DocIdSetIterator#advance(int)
		 */
		@Override
		public int advance(int target) throws IOException {

			if (lastReturn == DocIdSetIterator.NO_MORE_DOCS) {
				return DocIdSetIterator.NO_MORE_DOCS;
			}

			if (target <= lastReturn)
				target = lastReturn + 1;

			if (it1 != null && innerDocid < target) {
				if ((innerDocid = it1.advance(target)) == DocIdSetIterator.NO_MORE_DOCS) {
					it1 = null;
				}
			}

			while (it1 != null && innerDocid == target) {
				target++;
				if (target >= max) {
					return (lastReturn = DocIdSetIterator.NO_MORE_DOCS);
				}
				if ((innerDocid = it1.advance(target)) == DocIdSetIterator.NO_MORE_DOCS) {
					it1 = null;
				}
			}

			
			if (target >= max) {
				return (lastReturn = DocIdSetIterator.NO_MORE_DOCS);
			}

			return (lastReturn = target);
		}
	}
}
