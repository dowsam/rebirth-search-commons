/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons SearcherManager.java 2012-3-29 15:15:16 l.xue.nong$$
 */

package cn.com.restart.search.commons.lucene.manager;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;

import java.io.IOException;


/**
 * The Class SearcherManager.
 *
 * @author l.xue.nong
 */
public final class SearcherManager extends ReferenceManager<IndexSearcher> {

	
	/** The searcher factory. */
	private final SearcherFactory searcherFactory;

	
	/**
	 * Instantiates a new searcher manager.
	 *
	 * @param writer the writer
	 * @param applyAllDeletes the apply all deletes
	 * @param searcherFactory the searcher factory
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public SearcherManager(IndexWriter writer, boolean applyAllDeletes, SearcherFactory searcherFactory)
			throws IOException {
		if (searcherFactory == null) {
			searcherFactory = new SearcherFactory();
		}
		this.searcherFactory = searcherFactory;
		current = getSearcher(searcherFactory, IndexReader.open(writer, applyAllDeletes));
	}

	
	/**
	 * Instantiates a new searcher manager.
	 *
	 * @param dir the dir
	 * @param searcherFactory the searcher factory
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public SearcherManager(Directory dir, SearcherFactory searcherFactory) throws IOException {
		if (searcherFactory == null) {
			searcherFactory = new SearcherFactory();
		}
		this.searcherFactory = searcherFactory;
		current = getSearcher(searcherFactory, IndexReader.open(dir));
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.lucene.manager.ReferenceManager#decRef(java.lang.Object)
	 */
	@Override
	protected void decRef(IndexSearcher reference) throws IOException {
		reference.getIndexReader().decRef();
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.lucene.manager.ReferenceManager#refreshIfNeeded(java.lang.Object)
	 */
	@Override
	protected IndexSearcher refreshIfNeeded(IndexSearcher referenceToRefresh) throws IOException {
		final IndexReader newReader = IndexReader.openIfChanged(referenceToRefresh.getIndexReader());
		if (newReader == null) {
			return null;
		} else {
			return getSearcher(searcherFactory, newReader);
		}
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.lucene.manager.ReferenceManager#tryIncRef(java.lang.Object)
	 */
	@Override
	protected boolean tryIncRef(IndexSearcher reference) {
		return reference.getIndexReader().tryIncRef();
	}

	
	/**
	 * Maybe reopen.
	 *
	 * @return true, if successful
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Deprecated
	public boolean maybeReopen() throws IOException {
		return maybeRefresh();
	}

	
	/**
	 * Checks if is searcher current.
	 *
	 * @return true, if is searcher current
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public boolean isSearcherCurrent() throws IOException {
		final IndexSearcher searcher = acquire();
		try {
			return searcher.getIndexReader().isCurrent();
		} finally {
			release(searcher);
		}
	}

	
	
	/**
	 * Gets the searcher.
	 *
	 * @param searcherFactory the searcher factory
	 * @param reader the reader
	 * @return the searcher
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	static IndexSearcher getSearcher(SearcherFactory searcherFactory, IndexReader reader) throws IOException {
		boolean success = false;
		final IndexSearcher searcher;
		try {
			searcher = searcherFactory.newSearcher(reader);
			if (searcher.getIndexReader() != reader) {
				throw new IllegalStateException("SearcherFactory must wrap exactly the provided reader (got "
						+ searcher.getIndexReader() + " but expected " + reader + ")");
			}
			success = true;
		} finally {
			if (!success) {
				reader.decRef();
			}
		}
		return searcher;
	}
}
