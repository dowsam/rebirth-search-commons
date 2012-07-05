/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons MoreLikeThisQuery.java 2012-3-29 15:15:15 l.xue.nong$$
 */


package cn.com.restart.search.commons.lucene.search;

import java.io.IOException;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.DefaultSimilarity;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Similarity;
import org.apache.lucene.search.similar.MoreLikeThis;

import cn.com.restart.search.commons.io.FastStringReader;


/**
 * The Class MoreLikeThisQuery.
 *
 * @author l.xue.nong
 */
public class MoreLikeThisQuery extends Query {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1982006752290212276L;

	
	/** The Constant DEFAULT_PERCENT_TERMS_TO_MATCH. */
	public static final float DEFAULT_PERCENT_TERMS_TO_MATCH = 0.3f;

	
	/** The similarity. */
	private Similarity similarity;

	
	/** The like text. */
	private String likeText;

	
	/** The more like fields. */
	private String[] moreLikeFields;

	
	/** The analyzer. */
	private Analyzer analyzer;

	
	/** The percent terms to match. */
	private float percentTermsToMatch = DEFAULT_PERCENT_TERMS_TO_MATCH;

	
	/** The min term frequency. */
	private int minTermFrequency = MoreLikeThis.DEFAULT_MIN_TERM_FREQ;

	
	/** The max query terms. */
	private int maxQueryTerms = MoreLikeThis.DEFAULT_MAX_QUERY_TERMS;

	
	/** The stop words. */
	private Set<?> stopWords = MoreLikeThis.DEFAULT_STOP_WORDS;

	
	/** The min doc freq. */
	private int minDocFreq = MoreLikeThis.DEFAULT_MIN_DOC_FREQ;

	
	/** The max doc freq. */
	private int maxDocFreq = MoreLikeThis.DEFAULT_MAX_DOC_FREQ;

	
	/** The min word len. */
	private int minWordLen = MoreLikeThis.DEFAULT_MIN_WORD_LENGTH;

	
	/** The max word len. */
	private int maxWordLen = MoreLikeThis.DEFAULT_MAX_WORD_LENGTH;

	
	/** The boost terms. */
	private boolean boostTerms = MoreLikeThis.DEFAULT_BOOST;

	
	/** The boost terms factor. */
	private float boostTermsFactor = 1;

	
	/**
	 * Instantiates a new more like this query.
	 */
	public MoreLikeThisQuery() {

	}

	
	/**
	 * Instantiates a new more like this query.
	 *
	 * @param likeText the like text
	 * @param moreLikeFields the more like fields
	 * @param analyzer the analyzer
	 */
	public MoreLikeThisQuery(String likeText, String[] moreLikeFields, Analyzer analyzer) {
		this.likeText = likeText;
		this.moreLikeFields = moreLikeFields;
		this.analyzer = analyzer;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Query#rewrite(org.apache.lucene.index.IndexReader)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public Query rewrite(IndexReader reader) throws IOException {
		MoreLikeThis mlt = new MoreLikeThis(reader, similarity == null ? new DefaultSimilarity() : similarity);

		mlt.setFieldNames(moreLikeFields);
		mlt.setAnalyzer(analyzer);
		mlt.setMinTermFreq(minTermFrequency);
		mlt.setMinDocFreq(minDocFreq);
		mlt.setMaxDocFreq(maxDocFreq);
		mlt.setMaxQueryTerms(maxQueryTerms);
		mlt.setMinWordLen(minWordLen);
		mlt.setMaxWordLen(maxWordLen);
		mlt.setStopWords(stopWords);
		mlt.setBoost(boostTerms);
		mlt.setBoostFactor(boostTermsFactor);
		BooleanQuery bq = (BooleanQuery) mlt.like(new FastStringReader(likeText));
		BooleanClause[] clauses = bq.getClauses();

		bq.setMinimumNumberShouldMatch((int) (clauses.length * percentTermsToMatch));

		bq.setBoost(getBoost());
		return bq;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Query#toString(java.lang.String)
	 */
	@Override
	public String toString(String field) {
		return "like:" + likeText;
	}

	
	/**
	 * Gets the like text.
	 *
	 * @return the like text
	 */
	public String getLikeText() {
		return likeText;
	}

	
	/**
	 * Sets the like text.
	 *
	 * @param likeText the new like text
	 */
	public void setLikeText(String likeText) {
		this.likeText = likeText;
	}

	
	/**
	 * Gets the more like fields.
	 *
	 * @return the more like fields
	 */
	public String[] getMoreLikeFields() {
		return moreLikeFields;
	}

	
	/**
	 * Sets the more like fields.
	 *
	 * @param moreLikeFields the new more like fields
	 */
	public void setMoreLikeFields(String[] moreLikeFields) {
		this.moreLikeFields = moreLikeFields;
	}

	
	/**
	 * Gets the similarity.
	 *
	 * @return the similarity
	 */
	public Similarity getSimilarity() {
		return similarity;
	}

	
	/**
	 * Sets the similarity.
	 *
	 * @param similarity the new similarity
	 */
	public void setSimilarity(Similarity similarity) {
		this.similarity = similarity;
	}

	
	/**
	 * Gets the analyzer.
	 *
	 * @return the analyzer
	 */
	public Analyzer getAnalyzer() {
		return analyzer;
	}

	
	/**
	 * Sets the analyzer.
	 *
	 * @param analyzer the new analyzer
	 */
	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	
	/**
	 * Gets the percent terms to match.
	 *
	 * @return the percent terms to match
	 */
	public float getPercentTermsToMatch() {
		return percentTermsToMatch;
	}

	
	/**
	 * Sets the percent terms to match.
	 *
	 * @param percentTermsToMatch the new percent terms to match
	 */
	public void setPercentTermsToMatch(float percentTermsToMatch) {
		this.percentTermsToMatch = percentTermsToMatch;
	}

	
	/**
	 * Gets the min term frequency.
	 *
	 * @return the min term frequency
	 */
	public int getMinTermFrequency() {
		return minTermFrequency;
	}

	
	/**
	 * Sets the min term frequency.
	 *
	 * @param minTermFrequency the new min term frequency
	 */
	public void setMinTermFrequency(int minTermFrequency) {
		this.minTermFrequency = minTermFrequency;
	}

	
	/**
	 * Gets the max query terms.
	 *
	 * @return the max query terms
	 */
	public int getMaxQueryTerms() {
		return maxQueryTerms;
	}

	
	/**
	 * Sets the max query terms.
	 *
	 * @param maxQueryTerms the new max query terms
	 */
	public void setMaxQueryTerms(int maxQueryTerms) {
		this.maxQueryTerms = maxQueryTerms;
	}

	
	/**
	 * Gets the stop words.
	 *
	 * @return the stop words
	 */
	public Set<?> getStopWords() {
		return stopWords;
	}

	
	/**
	 * Sets the stop words.
	 *
	 * @param stopWords the new stop words
	 */
	public void setStopWords(Set<?> stopWords) {
		this.stopWords = stopWords;
	}

	
	/**
	 * Gets the min doc freq.
	 *
	 * @return the min doc freq
	 */
	public int getMinDocFreq() {
		return minDocFreq;
	}

	
	/**
	 * Sets the min doc freq.
	 *
	 * @param minDocFreq the new min doc freq
	 */
	public void setMinDocFreq(int minDocFreq) {
		this.minDocFreq = minDocFreq;
	}

	
	/**
	 * Gets the max doc freq.
	 *
	 * @return the max doc freq
	 */
	public int getMaxDocFreq() {
		return maxDocFreq;
	}

	
	/**
	 * Sets the max doc freq.
	 *
	 * @param maxDocFreq the new max doc freq
	 */
	public void setMaxDocFreq(int maxDocFreq) {
		this.maxDocFreq = maxDocFreq;
	}

	
	/**
	 * Gets the min word len.
	 *
	 * @return the min word len
	 */
	public int getMinWordLen() {
		return minWordLen;
	}

	
	/**
	 * Sets the min word len.
	 *
	 * @param minWordLen the new min word len
	 */
	public void setMinWordLen(int minWordLen) {
		this.minWordLen = minWordLen;
	}

	
	/**
	 * Gets the max word len.
	 *
	 * @return the max word len
	 */
	public int getMaxWordLen() {
		return maxWordLen;
	}

	
	/**
	 * Sets the max word len.
	 *
	 * @param maxWordLen the new max word len
	 */
	public void setMaxWordLen(int maxWordLen) {
		this.maxWordLen = maxWordLen;
	}

	
	/**
	 * Checks if is boost terms.
	 *
	 * @return true, if is boost terms
	 */
	public boolean isBoostTerms() {
		return boostTerms;
	}

	
	/**
	 * Sets the boost terms.
	 *
	 * @param boostTerms the new boost terms
	 */
	public void setBoostTerms(boolean boostTerms) {
		this.boostTerms = boostTerms;
	}

	
	/**
	 * Gets the boost terms factor.
	 *
	 * @return the boost terms factor
	 */
	public float getBoostTermsFactor() {
		return boostTermsFactor;
	}

	
	/**
	 * Sets the boost terms factor.
	 *
	 * @param boostTermsFactor the new boost terms factor
	 */
	public void setBoostTermsFactor(float boostTermsFactor) {
		this.boostTermsFactor = boostTermsFactor;
	}
}
