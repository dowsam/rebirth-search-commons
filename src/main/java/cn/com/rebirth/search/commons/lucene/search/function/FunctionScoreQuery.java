/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons FunctionScoreQuery.java 2012-7-6 10:23:42 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene.search.function;

import java.io.IOException;
import java.util.Set;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.ComplexExplanation;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.Similarity;
import org.apache.lucene.search.Weight;
import org.apache.lucene.util.ToStringUtils;


/**
 * The Class FunctionScoreQuery.
 *
 * @author l.xue.nong
 */
@SuppressWarnings("deprecation")
public class FunctionScoreQuery extends Query {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2160379289543815231L;

	
	/** The sub query. */
	Query subQuery;

	
	/** The function. */
	final ScoreFunction function;

	
	/**
	 * Instantiates a new function score query.
	 *
	 * @param subQuery the sub query
	 * @param function the function
	 */
	public FunctionScoreQuery(Query subQuery, ScoreFunction function) {
		this.subQuery = subQuery;
		this.function = function;
	}

	
	/**
	 * Gets the sub query.
	 *
	 * @return the sub query
	 */
	public Query getSubQuery() {
		return subQuery;
	}

	
	/**
	 * Gets the function.
	 *
	 * @return the function
	 */
	public ScoreFunction getFunction() {
		return function;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Query#rewrite(org.apache.lucene.index.IndexReader)
	 */
	@Override
	public Query rewrite(IndexReader reader) throws IOException {
		Query newQ = subQuery.rewrite(reader);
		if (newQ == subQuery)
			return this;
		FunctionScoreQuery bq = (FunctionScoreQuery) this.clone();
		bq.subQuery = newQ;
		return bq;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Query#extractTerms(java.util.Set)
	 */
	@Override
	public void extractTerms(Set<Term> terms) {
		subQuery.extractTerms(terms);
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Query#createWeight(org.apache.lucene.search.Searcher)
	 */
	@Override
	public Weight createWeight(Searcher searcher) throws IOException {
		return new CustomBoostFactorWeight(searcher);
	}

	
	/**
	 * The Class CustomBoostFactorWeight.
	 *
	 * @author l.xue.nong
	 */
	class CustomBoostFactorWeight extends Weight {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 6878135242756691169L;

		
		/** The searcher. */
		Searcher searcher;

		
		/** The sub query weight. */
		Weight subQueryWeight;

		
		/**
		 * Instantiates a new custom boost factor weight.
		 *
		 * @param searcher the searcher
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		public CustomBoostFactorWeight(Searcher searcher) throws IOException {
			this.searcher = searcher;
			this.subQueryWeight = subQuery.weight(searcher);
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.Weight#getQuery()
		 */
		public Query getQuery() {
			return FunctionScoreQuery.this;
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.Weight#getValue()
		 */
		public float getValue() {
			return getBoost();
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.Weight#sumOfSquaredWeights()
		 */
		@Override
		public float sumOfSquaredWeights() throws IOException {
			float sum = subQueryWeight.sumOfSquaredWeights();
			sum *= getBoost() * getBoost();
			return sum;
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.Weight#normalize(float)
		 */
		@Override
		public void normalize(float norm) {
			norm *= getBoost();
			subQueryWeight.normalize(norm);
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.Weight#scorer(org.apache.lucene.index.IndexReader, boolean, boolean)
		 */
		@Override
		public Scorer scorer(IndexReader reader, boolean scoreDocsInOrder, boolean topScorer) throws IOException {
			Scorer subQueryScorer = subQueryWeight.scorer(reader, scoreDocsInOrder, false);
			if (subQueryScorer == null) {
				return null;
			}
			function.setNextReader(reader);
			return new CustomBoostFactorScorer(getSimilarity(searcher), this, subQueryScorer, function);
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.Weight#explain(org.apache.lucene.index.IndexReader, int)
		 */
		@Override
		public Explanation explain(IndexReader reader, int doc) throws IOException {
			Explanation subQueryExpl = subQueryWeight.explain(reader, doc);
			if (!subQueryExpl.isMatch()) {
				return subQueryExpl;
			}

			function.setNextReader(reader);
			Explanation functionExplanation = function.explainScore(doc, subQueryExpl);
			float sc = getValue() * functionExplanation.getValue();
			Explanation res = new ComplexExplanation(true, sc, "custom score, product of:");
			res.addDetail(functionExplanation);
			res.addDetail(new Explanation(getValue(), "queryBoost"));
			return res;
		}
	}

	
	/**
	 * The Class CustomBoostFactorScorer.
	 *
	 * @author l.xue.nong
	 */
	static class CustomBoostFactorScorer extends Scorer {

		
		/** The sub query weight. */
		private final float subQueryWeight;

		
		/** The scorer. */
		private final Scorer scorer;

		
		/** The function. */
		private final ScoreFunction function;

		
		/**
		 * Instantiates a new custom boost factor scorer.
		 *
		 * @param similarity the similarity
		 * @param w the w
		 * @param scorer the scorer
		 * @param function the function
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		private CustomBoostFactorScorer(Similarity similarity, CustomBoostFactorWeight w, Scorer scorer,
				ScoreFunction function) throws IOException {
			super(similarity);
			this.subQueryWeight = w.getValue();
			this.scorer = scorer;
			this.function = function;
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.DocIdSetIterator#docID()
		 */
		@Override
		public int docID() {
			return scorer.docID();
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.DocIdSetIterator#advance(int)
		 */
		@Override
		public int advance(int target) throws IOException {
			return scorer.advance(target);
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.DocIdSetIterator#nextDoc()
		 */
		@Override
		public int nextDoc() throws IOException {
			return scorer.nextDoc();
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.Scorer#score()
		 */
		@Override
		public float score() throws IOException {
			return subQueryWeight * function.score(scorer.docID(), scorer.score());
		}
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Query#toString(java.lang.String)
	 */
	public String toString(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("custom score (").append(subQuery.toString(field)).append(",function=").append(function).append(')');
		sb.append(ToStringUtils.boost(getBoost()));
		return sb.toString();
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Query#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (getClass() != o.getClass())
			return false;
		FunctionScoreQuery other = (FunctionScoreQuery) o;
		return this.getBoost() == other.getBoost() && this.subQuery.equals(other.subQuery)
				&& this.function.equals(other.function);
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Query#hashCode()
	 */
	public int hashCode() {
		return subQuery.hashCode() + 31 * function.hashCode() ^ Float.floatToIntBits(getBoost());
	}
}
