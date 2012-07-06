/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons DocIdSetCollector.java 2012-7-6 10:23:49 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene.docset;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.util.OpenBitSetDISI;


/**
 * The Class DocIdSetCollector.
 *
 * @author l.xue.nong
 */
public class DocIdSetCollector extends Collector {

	
	/** The collector. */
	private final Collector collector;

	
	/** The doc id set. */
	private final OpenBitSetDISI docIdSet;

	
	/** The base. */
	private int base;

	
	/**
	 * Instantiates a new doc id set collector.
	 *
	 * @param collector the collector
	 * @param reader the reader
	 */
	public DocIdSetCollector(Collector collector, IndexReader reader) {
		this.collector = collector;
		this.docIdSet = new OpenBitSetDISI(reader.maxDoc());
	}

	
	/**
	 * Doc id set.
	 *
	 * @return the open bit set disi
	 */
	public OpenBitSetDISI docIdSet() {
		return docIdSet;
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
		collector.collect(doc);
		docIdSet.fastSet(base + doc);
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Collector#setNextReader(org.apache.lucene.index.IndexReader, int)
	 */
	@Override
	public void setNextReader(IndexReader reader, int docBase) throws IOException {
		base = docBase;
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
