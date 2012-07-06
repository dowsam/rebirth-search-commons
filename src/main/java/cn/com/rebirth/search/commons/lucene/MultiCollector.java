/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons MultiCollector.java 2012-7-6 10:23:41 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.ScoreCachingWrappingScorer;
import org.apache.lucene.search.Scorer;


/**
 * The Class MultiCollector.
 *
 * @author l.xue.nong
 */
public class MultiCollector extends Collector {

	
	/** The collector. */
	private final Collector collector;

	
	/** The collectors. */
	private final Collector[] collectors;

	
	/**
	 * Instantiates a new multi collector.
	 *
	 * @param collector the collector
	 * @param collectors the collectors
	 */
	public MultiCollector(Collector collector, Collector[] collectors) {
		this.collector = collector;
		this.collectors = collectors;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Collector#setScorer(org.apache.lucene.search.Scorer)
	 */
	@Override
	public void setScorer(Scorer scorer) throws IOException {
		
		if (!(scorer instanceof ScoreCachingWrappingScorer)) {
			scorer = new ScoreCachingWrappingScorer(scorer);
		}
		collector.setScorer(scorer);
		for (Collector collector : collectors) {
			collector.setScorer(scorer);
		}
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Collector#collect(int)
	 */
	@Override
	public void collect(int doc) throws IOException {
		collector.collect(doc);
		for (Collector collector : collectors) {
			collector.collect(doc);
		}
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Collector#setNextReader(org.apache.lucene.index.IndexReader, int)
	 */
	@Override
	public void setNextReader(IndexReader reader, int docBase) throws IOException {
		collector.setNextReader(reader, docBase);
		for (Collector collector : collectors) {
			collector.setNextReader(reader, docBase);
		}
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Collector#acceptsDocsOutOfOrder()
	 */
	@Override
	public boolean acceptsDocsOutOfOrder() {
		if (!collector.acceptsDocsOutOfOrder()) {
			return false;
		}
		for (Collector collector : collectors) {
			if (!collector.acceptsDocsOutOfOrder()) {
				return false;
			}
		}
		return true;
	}
}
