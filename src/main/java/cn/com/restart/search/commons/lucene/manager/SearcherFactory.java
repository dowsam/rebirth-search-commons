/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons SearcherFactory.java 2012-3-29 15:15:08 l.xue.nong$$
 */

package cn.com.restart.search.commons.lucene.manager;



import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;

import java.io.IOException;


/**
 * A factory for creating Searcher objects.
 */
public class SearcherFactory {

	
	/**
	 * New searcher.
	 *
	 * @param reader the reader
	 * @return the index searcher
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public IndexSearcher newSearcher(IndexReader reader) throws IOException {
		return new IndexSearcher(reader);
	}
}
