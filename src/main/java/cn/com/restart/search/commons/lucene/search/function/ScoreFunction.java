/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ScoreFunction.java 2012-3-29 15:15:21 l.xue.nong$$
 */


package cn.com.restart.search.commons.lucene.search.function;

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
