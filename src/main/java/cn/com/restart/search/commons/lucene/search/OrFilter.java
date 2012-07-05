/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons OrFilter.java 2012-3-29 15:15:19 l.xue.nong$$
 */


package cn.com.restart.search.commons.lucene.search;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.Filter;

import cn.com.restart.search.commons.lucene.docset.DocSet;
import cn.com.restart.search.commons.lucene.docset.OrDocIdSet;
import cn.com.restart.search.commons.lucene.docset.OrDocSet;

import com.google.common.collect.Lists;


/**
 * The Class OrFilter.
 *
 * @author l.xue.nong
 */
public class OrFilter extends Filter {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1811541759820206573L;

	
	/** The filters. */
	private final List<? extends Filter> filters;

	
	/**
	 * Instantiates a new or filter.
	 *
	 * @param filters the filters
	 */
	public OrFilter(List<? extends Filter> filters) {
		this.filters = filters;
	}

	
	/**
	 * Filters.
	 *
	 * @return the list<? extends filter>
	 */
	public List<? extends Filter> filters() {
		return filters;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Filter#getDocIdSet(org.apache.lucene.index.IndexReader)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public DocIdSet getDocIdSet(IndexReader reader) throws IOException {
		if (filters.size() == 1) {
			return filters.get(0).getDocIdSet(reader);
		}
		List sets = Lists.newArrayListWithExpectedSize(filters.size());
		boolean allAreDocSet = true;
		for (Filter filter : filters) {
			DocIdSet set = filter.getDocIdSet(reader);
			if (set == null) { 
				continue;
			}
			if (!(set instanceof DocSet)) {
				allAreDocSet = false;
			}
			sets.add(set);
		}
		if (sets.size() == 0) {
			return DocSet.EMPTY_DOC_SET;
		}
		if (sets.size() == 1) {
			return (DocIdSet) sets.get(0);
		}
		if (allAreDocSet) {
			return new OrDocSet(sets);
		}
		return new OrDocIdSet(sets);
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (null == filters ? 0 : filters.hashCode());
		return hash;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;

		OrFilter other = (OrFilter) obj;
		return equalFilters(filters, other.filters);
	}

	
	/**
	 * Equal filters.
	 *
	 * @param filters1 the filters1
	 * @param filters2 the filters2
	 * @return true, if successful
	 */
	private boolean equalFilters(List<? extends Filter> filters1, List<? extends Filter> filters2) {
		return (filters1 == filters2) || ((filters1 != null) && filters1.equals(filters2));
	}
}