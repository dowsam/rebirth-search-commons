/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons GetDocSet.java 2012-3-29 15:15:07 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene.docset;

import java.io.IOException;

import org.apache.lucene.search.DocIdSetIterator;


/**
 * The Class GetDocSet.
 *
 * @author l.xue.nong
 */
public abstract class GetDocSet extends DocSet {

	
	/** The max doc. */
	private final int maxDoc;

	
	/**
	 * Instantiates a new gets the doc set.
	 *
	 * @param maxDoc the max doc
	 */
	protected GetDocSet(int maxDoc) {
		this.maxDoc = maxDoc;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.lucene.docset.DocSet#sizeInBytes()
	 */
	@Override
	public long sizeInBytes() {
		return 0;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.util.Bits#length()
	 */
	@Override
	public int length() {
		return maxDoc;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.DocIdSet#iterator()
	 */
	@Override
	public DocIdSetIterator iterator() throws IOException {
		return new DocIdSetIterator() {
			private int doc = -1;

			@Override
			public int docID() {
				return doc;
			}

			@Override
			public int nextDoc() throws IOException {
				do {
					doc++;
					if (doc >= maxDoc) {
						return doc = NO_MORE_DOCS;
					}
				} while (!get(doc));
				return doc;
			}

			@Override
			public int advance(int target) throws IOException {
				if (target >= maxDoc) {
					return doc = NO_MORE_DOCS;
				}
				doc = target;
				while (!get(doc)) {
					doc++;
					if (doc >= maxDoc) {
						return doc = NO_MORE_DOCS;
					}
				}
				return doc;
			}
		};
	}
}
