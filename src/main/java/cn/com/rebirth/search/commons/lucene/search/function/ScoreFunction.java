/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons ScoreFunction.java 2012-7-6 10:23:52 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene.search.function;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Explanation;


/**
 * The Interface ScoreFunction.
 *
 * @author l.xue.nong
 */
public interface ScoreFunction {

	
	/**
	 * Sets the next reader.
	 *
	 * @param reader the new next reader
	 */
	void setNextReader(IndexReader reader);

	
	/**
	 * Score.
	 *
	 * @param docId the doc id
	 * @param subQueryScore the sub query score
	 * @return the float
	 */
	float score(int docId, float subQueryScore);

	
	/**
	 * Factor.
	 *
	 * @param docId the doc id
	 * @return the float
	 */
	float factor(int docId);

	
	/**
	 * Explain score.
	 *
	 * @param docId the doc id
	 * @param subQueryExpl the sub query expl
	 * @return the explanation
	 */
	Explanation explainScore(int docId, Explanation subQueryExpl);

	
	/**
	 * Explain factor.
	 *
	 * @param docId the doc id
	 * @return the explanation
	 */
	Explanation explainFactor(int docId);
}
