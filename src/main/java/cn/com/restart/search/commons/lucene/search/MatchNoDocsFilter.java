/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons MatchNoDocsFilter.java 2012-3-29 15:15:14 l.xue.nong$$
 */


package cn.com.restart.search.commons.lucene.search;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.Filter;


/**
 * The Class MatchNoDocsFilter.
 *
 * @author l.xue.nong
 */
public class MatchNoDocsFilter extends Filter {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4220139158249147346L;

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Filter#getDocIdSet(org.apache.lucene.index.IndexReader)
	 */
	@Override
	public DocIdSet getDocIdSet(IndexReader reader) throws IOException {
		return null;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getClass().hashCode();
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null) {
			return false;
		}

		if (obj.getClass() == this.getClass()) {
			return true;
		}

		return false;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MatchNoDocsFilter";
	}
}
