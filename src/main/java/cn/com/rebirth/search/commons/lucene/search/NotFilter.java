/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons NotFilter.java 2012-3-29 15:15:09 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene.search;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.Filter;

import cn.com.rebirth.search.commons.lucene.docset.AllDocSet;
import cn.com.rebirth.search.commons.lucene.docset.DocSet;
import cn.com.rebirth.search.commons.lucene.docset.NotDocIdSet;
import cn.com.rebirth.search.commons.lucene.docset.NotDocSet;


/**
 * The Class NotFilter.
 *
 * @author l.xue.nong
 */
public class NotFilter extends Filter {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2010936251508390364L;

	
	/** The filter. */
	private final Filter filter;

	
	/**
	 * Instantiates a new not filter.
	 *
	 * @param filter the filter
	 */
	public NotFilter(Filter filter) {
		this.filter = filter;
	}

	
	/**
	 * Filter.
	 *
	 * @return the filter
	 */
	public Filter filter() {
		return filter;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Filter#getDocIdSet(org.apache.lucene.index.IndexReader)
	 */
	@Override
	public DocIdSet getDocIdSet(IndexReader reader) throws IOException {
		DocIdSet set = filter.getDocIdSet(reader);
		if (set == null) {
			return new AllDocSet(reader.maxDoc());
		}
		if (set instanceof DocSet) {
			return new NotDocSet((DocSet) set, reader.maxDoc());
		}
		return new NotDocIdSet(set, reader.maxDoc());
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		NotFilter notFilter = (NotFilter) o;
		return !(filter != null ? !filter.equals(notFilter.filter) : notFilter.filter != null);
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return filter != null ? filter.hashCode() : 0;
	}
}
