/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons TermFilter.java 2012-3-29 15:15:18 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene.search;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.Filter;
import org.apache.lucene.util.FixedBitSet;


/**
 * The Class TermFilter.
 *
 * @author l.xue.nong
 */
public class TermFilter extends Filter {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 115981038304703881L;

	
	/** The term. */
	private final Term term;

	
	/**
	 * Instantiates a new term filter.
	 *
	 * @param term the term
	 */
	public TermFilter(Term term) {
		this.term = term;
	}

	
	/**
	 * Gets the term.
	 *
	 * @return the term
	 */
	public Term getTerm() {
		return term;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Filter#getDocIdSet(org.apache.lucene.index.IndexReader)
	 */
	@Override
	public DocIdSet getDocIdSet(IndexReader reader) throws IOException {
		FixedBitSet result = null;
		TermDocs td = reader.termDocs();
		try {
			td.seek(term);
			if (td.next()) {
				result = new FixedBitSet(reader.maxDoc());
				result.set(td.doc());
				while (td.next()) {
					result.set(td.doc());
				}
			}
		} finally {
			td.close();
		}
		return result;
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

		TermFilter that = (TermFilter) o;

		if (term != null ? !term.equals(that.term) : that.term != null)
			return false;

		return true;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return term != null ? term.hashCode() : 0;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return term.field() + ":" + term.text();
	}
}
