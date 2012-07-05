/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons XBooleanFilter.java 2012-3-29 15:15:18 l.xue.nong$$
 */


package cn.com.restart.search.commons.lucene.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.FilterClause;
import org.apache.lucene.util.FixedBitSet;

import cn.com.restart.search.commons.lucene.docset.DocSet;
import cn.com.restart.search.commons.lucene.docset.DocSets;


/**
 * The Class XBooleanFilter.
 *
 * @author l.xue.nong
 */
public class XBooleanFilter extends Filter {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5519401347430041533L;

	
	/** The should filters. */
	ArrayList<Filter> shouldFilters = null;

	
	/** The not filters. */
	ArrayList<Filter> notFilters = null;

	
	/** The must filters. */
	ArrayList<Filter> mustFilters = null;

	
	/**
	 * Gets the dISI.
	 *
	 * @param filters the filters
	 * @param index the index
	 * @param reader the reader
	 * @return the dISI
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private DocIdSet getDISI(ArrayList<Filter> filters, int index, IndexReader reader) throws IOException {
		DocIdSet docIdSet = filters.get(index).getDocIdSet(reader);
		if (docIdSet == DocIdSet.EMPTY_DOCIDSET || docIdSet == DocSet.EMPTY_DOC_SET) {
			return null;
		}
		return docIdSet;
	}

	
	/**
	 * Gets the should filters.
	 *
	 * @return the should filters
	 */
	public List<Filter> getShouldFilters() {
		return this.shouldFilters;
	}

	
	/**
	 * Gets the must filters.
	 *
	 * @return the must filters
	 */
	public List<Filter> getMustFilters() {
		return this.mustFilters;
	}

	
	/**
	 * Gets the not filters.
	 *
	 * @return the not filters
	 */
	public List<Filter> getNotFilters() {
		return this.notFilters;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Filter#getDocIdSet(org.apache.lucene.index.IndexReader)
	 */
	@Override
	public DocIdSet getDocIdSet(IndexReader reader) throws IOException {
		FixedBitSet res = null;

		if (mustFilters == null && notFilters == null && shouldFilters != null && shouldFilters.size() == 1) {
			return shouldFilters.get(0).getDocIdSet(reader);
		}

		if (shouldFilters == null && notFilters == null && mustFilters != null && mustFilters.size() == 1) {
			return mustFilters.get(0).getDocIdSet(reader);
		}

		if (shouldFilters != null) {
			for (int i = 0; i < shouldFilters.size(); i++) {
				final DocIdSet disi = getDISI(shouldFilters, i, reader);
				if (disi == null)
					continue;
				if (res == null) {
					res = new FixedBitSet(reader.maxDoc());
				}
				DocSets.or(res, disi);
			}

			
			if (res == null && !shouldFilters.isEmpty()) {
				return null;
			}
		}

		if (notFilters != null) {
			for (int i = 0; i < notFilters.size(); i++) {
				if (res == null) {
					res = new FixedBitSet(reader.maxDoc());
					res.set(0, reader.maxDoc()); 
				}
				final DocIdSet disi = getDISI(notFilters, i, reader);
				if (disi != null) {
					DocSets.andNot(res, disi);
				}
			}
		}

		if (mustFilters != null) {
			for (int i = 0; i < mustFilters.size(); i++) {
				final DocIdSet disi = getDISI(mustFilters, i, reader);
				if (disi == null) {
					return null;
				}
				if (res == null) {
					res = new FixedBitSet(reader.maxDoc());
					DocSets.or(res, disi);
				} else {
					DocSets.and(res, disi);
				}
			}
		}

		return res;
	}

	

	/**
	 * Adds the.
	 *
	 * @param filterClause the filter clause
	 */
	public void add(FilterClause filterClause) {
		if (filterClause.getOccur().equals(BooleanClause.Occur.MUST)) {
			if (mustFilters == null) {
				mustFilters = new ArrayList<Filter>();
			}
			mustFilters.add(filterClause.getFilter());
		}
		if (filterClause.getOccur().equals(BooleanClause.Occur.SHOULD)) {
			if (shouldFilters == null) {
				shouldFilters = new ArrayList<Filter>();
			}
			shouldFilters.add(filterClause.getFilter());
		}
		if (filterClause.getOccur().equals(BooleanClause.Occur.MUST_NOT)) {
			if (notFilters == null) {
				notFilters = new ArrayList<Filter>();
			}
			notFilters.add(filterClause.getFilter());
		}
	}

	
	/**
	 * Adds the must.
	 *
	 * @param filter the filter
	 */
	public void addMust(Filter filter) {
		if (mustFilters == null) {
			mustFilters = new ArrayList<Filter>();
		}
		mustFilters.add(filter);
	}

	
	/**
	 * Adds the should.
	 *
	 * @param filter the filter
	 */
	public void addShould(Filter filter) {
		if (shouldFilters == null) {
			shouldFilters = new ArrayList<Filter>();
		}
		shouldFilters.add(filter);
	}

	
	/**
	 * Adds the not.
	 *
	 * @param filter the filter
	 */
	public void addNot(Filter filter) {
		if (notFilters == null) {
			notFilters = new ArrayList<Filter>();
		}
		notFilters.add(filter);
	}

	
	/**
	 * Equal filters.
	 *
	 * @param filters1 the filters1
	 * @param filters2 the filters2
	 * @return true, if successful
	 */
	private boolean equalFilters(ArrayList<Filter> filters1, ArrayList<Filter> filters2) {
		return (filters1 == filters2) || ((filters1 != null) && filters1.equals(filters2));
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

		XBooleanFilter other = (XBooleanFilter) obj;
		return equalFilters(notFilters, other.notFilters) && equalFilters(mustFilters, other.mustFilters)
				&& equalFilters(shouldFilters, other.shouldFilters);
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (null == mustFilters ? 0 : mustFilters.hashCode());
		hash = 31 * hash + (null == notFilters ? 0 : notFilters.hashCode());
		hash = 31 * hash + (null == shouldFilters ? 0 : shouldFilters.hashCode());
		return hash;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("BooleanFilter(");
		appendFilters(shouldFilters, "", buffer);
		appendFilters(mustFilters, "+", buffer);
		appendFilters(notFilters, "-", buffer);
		buffer.append(")");
		return buffer.toString();
	}

	
	/**
	 * Append filters.
	 *
	 * @param filters the filters
	 * @param occurString the occur string
	 * @param buffer the buffer
	 */
	private void appendFilters(ArrayList<Filter> filters, String occurString, StringBuilder buffer) {
		if (filters != null) {
			for (int i = 0; i < filters.size(); i++) {
				buffer.append(' ');
				buffer.append(occurString);
				buffer.append(filters.get(i).toString());
			}
		}
	}
}
