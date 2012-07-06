/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons FilteredCollector.java 2012-7-6 10:23:54 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene.search;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Scorer;

import cn.com.rebirth.search.commons.lucene.docset.DocSet;
import cn.com.rebirth.search.commons.lucene.docset.DocSets;


/**
 * The Class FilteredCollector.
 *
 * @author l.xue.nong
 */
public class FilteredCollector extends Collector {

	
	/** The collector. */
	private final Collector collector;

	
	/** The filter. */
	private final Filter filter;

	
	/** The doc set. */
	private DocSet docSet;

	
	/**
	 * Instantiates a new filtered collector.
	 *
	 * @param collector the collector
	 * @param filter the filter
	 */
	public FilteredCollector(Collector collector, Filter filter) {
		this.collector = collector;
		this.filter = filter;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Collector#setScorer(org.apache.lucene.search.Scorer)
	 */
	@Override
	public void setScorer(Scorer scorer) throws IOException {
		collector.setScorer(scorer);
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Collector#collect(int)
	 */
	@Override
	public void collect(int doc) throws IOException {
		if (docSet.get(doc)) {
			collector.collect(doc);
		}
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Collector#setNextReader(org.apache.lucene.index.IndexReader, int)
	 */
	@Override
	public void setNextReader(IndexReader reader, int docBase) throws IOException {
		collector.setNextReader(reader, docBase);
		docSet = DocSets.convert(reader, filter.getDocIdSet(reader));
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Collector#acceptsDocsOutOfOrder()
	 */
	@Override
	public boolean acceptsDocsOutOfOrder() {
		return collector.acceptsDocsOutOfOrder();
	}
}