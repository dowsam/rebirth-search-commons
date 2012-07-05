/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons BoostScoreFunction.java 2012-3-29 15:15:15 l.xue.nong$$
 */


package cn.com.restart.search.commons.lucene.search.function;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Explanation;


/**
 * The Class BoostScoreFunction.
 *
 * @author l.xue.nong
 */
public class BoostScoreFunction implements ScoreFunction {

	
	/** The boost. */
	private final float boost;

	
	/**
	 * Instantiates a new boost score function.
	 *
	 * @param boost the boost
	 */
	public BoostScoreFunction(float boost) {
		this.boost = boost;
	}

	
	/**
	 * Gets the boost.
	 *
	 * @return the boost
	 */
	public float getBoost() {
		return boost;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.lucene.search.function.ScoreFunction#setNextReader(org.apache.lucene.index.IndexReader)
	 */
	@Override
	public void setNextReader(IndexReader reader) {
		
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.lucene.search.function.ScoreFunction#score(int, float)
	 */
	@Override
	public float score(int docId, float subQueryScore) {
		return subQueryScore * boost;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.lucene.search.function.ScoreFunction#factor(int)
	 */
	@Override
	public float factor(int docId) {
		return boost;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.lucene.search.function.ScoreFunction#explainScore(int, org.apache.lucene.search.Explanation)
	 */
	@Override
	public Explanation explainScore(int docId, Explanation subQueryExpl) {
		Explanation exp = new Explanation(boost * subQueryExpl.getValue(), "static boost function: product of:");
		exp.addDetail(subQueryExpl);
		exp.addDetail(new Explanation(boost, "boostFactor"));
		return exp;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.lucene.search.function.ScoreFunction#explainFactor(int)
	 */
	@Override
	public Explanation explainFactor(int docId) {
		return new Explanation(boost, "boostFactor");
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		BoostScoreFunction that = (BoostScoreFunction) o;

		if (Float.compare(that.boost, boost) != 0)
			return false;

		return true;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (boost != +0.0f ? Float.floatToIntBits(boost) : 0);
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "boost[" + boost + "]";
	}
}
