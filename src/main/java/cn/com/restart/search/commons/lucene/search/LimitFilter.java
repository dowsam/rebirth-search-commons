/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons LimitFilter.java 2012-3-29 15:15:08 l.xue.nong$$
 */


package cn.com.restart.search.commons.lucene.search;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.DocIdSet;

import cn.com.restart.search.commons.lucene.docset.GetDocSet;


/**
 * The Class LimitFilter.
 *
 * @author l.xue.nong
 */
public class LimitFilter extends NoCacheFilter {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8988225214559888131L;

	
	/** The limit. */
	private final int limit;

	
	/** The counter. */
	private int counter;

	
	/**
	 * Instantiates a new limit filter.
	 *
	 * @param limit the limit
	 */
	public LimitFilter(int limit) {
		this.limit = limit;
	}

	
	/**
	 * Gets the limit.
	 *
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Filter#getDocIdSet(org.apache.lucene.index.IndexReader)
	 */
	@Override
	public DocIdSet getDocIdSet(IndexReader reader) throws IOException {
		if (counter > limit) {
			return null;
		}
		return new LimitDocSet(reader.maxDoc(), limit);
	}

	
	/**
	 * The Class LimitDocSet.
	 *
	 * @author l.xue.nong
	 */
	public class LimitDocSet extends GetDocSet {

		
		/** The limit. */
		private final int limit;

		
		/**
		 * Instantiates a new limit doc set.
		 *
		 * @param maxDoc the max doc
		 * @param limit the limit
		 */
		public LimitDocSet(int maxDoc, int limit) {
			super(maxDoc);
			this.limit = limit;
		}

		
		/* (non-Javadoc)
		 * @see cn.com.summall.search.commons.lucene.docset.DocSet#get(int)
		 */
		@Override
		public boolean get(int doc) {
			if (++counter > limit) {
				return false;
			}
			return true;
		}
	}
}