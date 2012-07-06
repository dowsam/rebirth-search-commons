/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons SearcherFactory.java 2012-7-6 10:23:45 l.xue.nong$$
 */

package cn.com.rebirth.search.commons.lucene.manager;



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
