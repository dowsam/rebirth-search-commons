/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons NotDeletedFilter.java 2012-3-29 15:15:13 l.xue.nong$$
 */

package cn.com.rebirth.search.commons.lucene.search;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.FilteredDocIdSetIterator;


/**
 * The Class NotDeletedFilter.
 *
 * @author l.xue.nong
 */
public class NotDeletedFilter extends Filter {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3119041092094254582L;

	
	/** The filter. */
	private final Filter filter;

	
	/**
	 * Instantiates a new not deleted filter.
	 *
	 * @param filter the filter
	 */
	public NotDeletedFilter(Filter filter) {
		this.filter = filter;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Filter#getDocIdSet(org.apache.lucene.index.IndexReader)
	 */
	@Override
	public DocIdSet getDocIdSet(IndexReader reader) throws IOException {
		DocIdSet docIdSet = filter.getDocIdSet(reader);
		if (docIdSet == null) {
			return null;
		}
		if (!reader.hasDeletions()) {
			return docIdSet;
		}
		return new NotDeletedDocIdSet(docIdSet, reader);
	}

	
	/**
	 * Filter.
	 *
	 * @return the filter
	 */
	public Filter filter() {
		return this.filter;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NotDeleted(" + filter + ")";
	}

	
	/**
	 * The Class NotDeletedDocIdSet.
	 *
	 * @author l.xue.nong
	 */
	static class NotDeletedDocIdSet extends DocIdSet {

		
		/** The inner set. */
		private final DocIdSet innerSet;

		
		/** The reader. */
		private final IndexReader reader;

		
		/**
		 * Instantiates a new not deleted doc id set.
		 *
		 * @param innerSet the inner set
		 * @param reader the reader
		 */
		NotDeletedDocIdSet(DocIdSet innerSet, IndexReader reader) {
			this.innerSet = innerSet;
			this.reader = reader;
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.DocIdSet#iterator()
		 */
		@Override
		public DocIdSetIterator iterator() throws IOException {
			DocIdSetIterator iterator = innerSet.iterator();
			if (iterator == null) {
				return null;
			}
			return new NotDeletedDocIdSetIterator(iterator, reader);
		}
	}

	
	/**
	 * The Class NotDeletedDocIdSetIterator.
	 *
	 * @author l.xue.nong
	 */
	static class NotDeletedDocIdSetIterator extends FilteredDocIdSetIterator {

		
		/** The reader. */
		private final IndexReader reader;

		
		/**
		 * Instantiates a new not deleted doc id set iterator.
		 *
		 * @param innerIter the inner iter
		 * @param reader the reader
		 */
		NotDeletedDocIdSetIterator(DocIdSetIterator innerIter, IndexReader reader) {
			super(innerIter);
			this.reader = reader;
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.FilteredDocIdSetIterator#match(int)
		 */
		@Override
		protected boolean match(int doc) throws IOException {
			return !reader.isDeleted(doc);
		}
	}
}
