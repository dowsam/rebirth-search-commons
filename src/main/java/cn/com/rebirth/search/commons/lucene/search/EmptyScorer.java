/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons EmptyScorer.java 2012-7-6 10:23:42 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene.search;

import java.io.IOException;

import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Similarity;


/**
 * The Class EmptyScorer.
 *
 * @author l.xue.nong
 */
public class EmptyScorer extends Scorer {

	
	/**
	 * Instantiates a new empty scorer.
	 *
	 * @param similarity the similarity
	 */
	@SuppressWarnings("deprecation")
	public EmptyScorer(Similarity similarity) {
		super(similarity);
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Scorer#score()
	 */
	@Override
	public float score() throws IOException {
		return 0;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.DocIdSetIterator#docID()
	 */
	@Override
	public int docID() {
		return NO_MORE_DOCS;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.DocIdSetIterator#nextDoc()
	 */
	@Override
	public int nextDoc() throws IOException {
		return NO_MORE_DOCS;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.DocIdSetIterator#advance(int)
	 */
	@Override
	public int advance(int target) throws IOException {
		return NO_MORE_DOCS;
	}
}
