/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons MinimumScoreCollector.java 2012-3-29 15:15:10 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.ScoreCachingWrappingScorer;
import org.apache.lucene.search.Scorer;


/**
 * The Class MinimumScoreCollector.
 *
 * @author l.xue.nong
 */
public class MinimumScoreCollector extends Collector {

	
	/** The collector. */
	private final Collector collector;

	
	/** The minimum score. */
	private final float minimumScore;

	
	/** The scorer. */
	private Scorer scorer;

	
	/**
	 * Instantiates a new minimum score collector.
	 *
	 * @param collector the collector
	 * @param minimumScore the minimum score
	 */
	public MinimumScoreCollector(Collector collector, float minimumScore) {
		this.collector = collector;
		this.minimumScore = minimumScore;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Collector#setScorer(org.apache.lucene.search.Scorer)
	 */
	@Override
	public void setScorer(Scorer scorer) throws IOException {
		if (!(scorer instanceof ScoreCachingWrappingScorer)) {
			scorer = new ScoreCachingWrappingScorer(scorer);
		}
		this.scorer = scorer;
		collector.setScorer(scorer);
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Collector#collect(int)
	 */
	@Override
	public void collect(int doc) throws IOException {
		if (scorer.score() > minimumScore) {
			collector.collect(doc);
		}
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Collector#setNextReader(org.apache.lucene.index.IndexReader, int)
	 */
	@Override
	public void setNextReader(IndexReader reader, int docBase) throws IOException {
		collector.setNextReader(reader, docBase);
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Collector#acceptsDocsOutOfOrder()
	 */
	@Override
	public boolean acceptsDocsOutOfOrder() {
		return collector.acceptsDocsOutOfOrder();
	}
}