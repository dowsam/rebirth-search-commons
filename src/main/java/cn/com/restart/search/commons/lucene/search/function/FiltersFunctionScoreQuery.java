/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons FiltersFunctionScoreQuery.java 2012-3-29 15:15:12 l.xue.nong$$
 */


package cn.com.restart.search.commons.lucene.search.function;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.ComplexExplanation;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.Similarity;
import org.apache.lucene.search.Weight;
import org.apache.lucene.util.ToStringUtils;

import cn.com.restart.search.commons.lucene.docset.DocSet;
import cn.com.restart.search.commons.lucene.docset.DocSets;


/**
 * The Class FiltersFunctionScoreQuery.
 *
 * @author l.xue.nong
 */
@SuppressWarnings("deprecation")
public class FiltersFunctionScoreQuery extends Query {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3524397270289523561L;

	
	/**
	 * The Class FilterFunction.
	 *
	 * @author l.xue.nong
	 */
	public static class FilterFunction {

		
		/** The filter. */
		public final Filter filter;

		
		/** The function. */
		public final ScoreFunction function;

		
		/**
		 * Instantiates a new filter function.
		 *
		 * @param filter the filter
		 * @param function the function
		 */
		public FilterFunction(Filter filter, ScoreFunction function) {
			this.filter = filter;
			this.function = function;
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

			FilterFunction that = (FilterFunction) o;

			if (filter != null ? !filter.equals(that.filter) : that.filter != null)
				return false;
			if (function != null ? !function.equals(that.function) : that.function != null)
				return false;

			return true;
		}

		
		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			int result = filter != null ? filter.hashCode() : 0;
			result = 31 * result + (function != null ? function.hashCode() : 0);
			return result;
		}
	}

	
	/**
	 * The Enum ScoreMode.
	 *
	 * @author l.xue.nong
	 */
	public static enum ScoreMode {

		
		/** The First. */
		First,

		
		/** The Avg. */
		Avg,

		
		/** The Max. */
		Max,

		
		/** The Total. */
		Total,

		
		/** The Min. */
		Min,

		
		/** The Multiply. */
		Multiply
	}

	
	/** The sub query. */
	Query subQuery;

	
	/** The filter functions. */
	final FilterFunction[] filterFunctions;

	
	/** The score mode. */
	final ScoreMode scoreMode;

	
	/** The doc sets. */
	DocSet[] docSets;

	
	/**
	 * Instantiates a new filters function score query.
	 *
	 * @param subQuery the sub query
	 * @param scoreMode the score mode
	 * @param filterFunctions the filter functions
	 */
	public FiltersFunctionScoreQuery(Query subQuery, ScoreMode scoreMode, FilterFunction[] filterFunctions) {
		this.subQuery = subQuery;
		this.scoreMode = scoreMode;
		this.filterFunctions = filterFunctions;
		this.docSets = new DocSet[filterFunctions.length];
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
	 * Gets the filter functions.
	 *
	 * @return the filter functions
	 */
	public FilterFunction[] getFilterFunctions() {
		return filterFunctions;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Query#rewrite(org.apache.lucene.index.IndexReader)
	 */
	@Override
	public Query rewrite(IndexReader reader) throws IOException {
		Query newQ = subQuery.rewrite(reader);
		if (newQ == subQuery)
			return this;
		FiltersFunctionScoreQuery bq = (FiltersFunctionScoreQuery) this.clone();
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
		private static final long serialVersionUID = -5736718529711835652L;

		
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
			return FiltersFunctionScoreQuery.this;
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
			for (int i = 0; i < filterFunctions.length; i++) {
				FilterFunction filterFunction = filterFunctions[i];
				filterFunction.function.setNextReader(reader);
				docSets[i] = DocSets.convert(reader, filterFunction.filter.getDocIdSet(reader));
			}
			return new CustomBoostFactorScorer(getSimilarity(searcher), this, subQueryScorer, scoreMode,
					filterFunctions, docSets);
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

			if (scoreMode == ScoreMode.First) {
				for (FilterFunction filterFunction : filterFunctions) {
					DocSet docSet = DocSets.convert(reader, filterFunction.filter.getDocIdSet(reader));
					if (docSet.get(doc)) {
						filterFunction.function.setNextReader(reader);
						Explanation functionExplanation = filterFunction.function.explainFactor(doc);
						float sc = getValue() * subQueryExpl.getValue() * functionExplanation.getValue();
						Explanation res = new ComplexExplanation(true, sc, "custom score, product of:");
						res.addDetail(new Explanation(1.0f, "match filter: " + filterFunction.filter.toString()));
						res.addDetail(functionExplanation);
						res.addDetail(new Explanation(getValue(), "queryBoost"));
						return res;
					}
				}
			} else {
				int count = 0;
				float total = 0;
				float multiply = 1;
				float max = Float.NEGATIVE_INFINITY;
				float min = Float.POSITIVE_INFINITY;
				ArrayList<Explanation> filtersExplanations = new ArrayList<Explanation>();
				for (FilterFunction filterFunction : filterFunctions) {
					DocSet docSet = DocSets.convert(reader, filterFunction.filter.getDocIdSet(reader));
					if (docSet.get(doc)) {
						filterFunction.function.setNextReader(reader);
						Explanation functionExplanation = filterFunction.function.explainFactor(doc);
						float factor = functionExplanation.getValue();
						count++;
						total += factor;
						multiply *= factor;
						max = Math.max(factor, max);
						min = Math.min(factor, min);
						Explanation res = new ComplexExplanation(true, factor, "custom score, product of:");
						res.addDetail(new Explanation(1.0f, "match filter: " + filterFunction.filter.toString()));
						res.addDetail(functionExplanation);
						res.addDetail(new Explanation(getValue(), "queryBoost"));
						filtersExplanations.add(res);
					}
				}
				if (count > 0) {
					float factor = 0;
					switch (scoreMode) {
					case Avg:
						factor = total / count;
						break;
					case Max:
						factor = max;
						break;
					case Min:
						factor = min;
						break;
					case Total:
						factor = total;
						break;
					case Multiply:
						factor = multiply;
						break;
					}

					float sc = factor * subQueryExpl.getValue() * getValue();
					Explanation res = new ComplexExplanation(true, sc, "custom score, score mode ["
							+ scoreMode.toString().toLowerCase() + "]");
					res.addDetail(subQueryExpl);
					for (Explanation explanation : filtersExplanations) {
						res.addDetail(explanation);
					}
					return res;
				}
			}

			float sc = getValue() * subQueryExpl.getValue();
			Explanation res = new ComplexExplanation(true, sc, "custom score, no filter match, product of:");
			res.addDetail(subQueryExpl);
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

		
		/** The filter functions. */
		private final FilterFunction[] filterFunctions;

		
		/** The score mode. */
		private final ScoreMode scoreMode;

		
		/** The doc sets. */
		private final DocSet[] docSets;

		
		/**
		 * Instantiates a new custom boost factor scorer.
		 *
		 * @param similarity the similarity
		 * @param w the w
		 * @param scorer the scorer
		 * @param scoreMode the score mode
		 * @param filterFunctions the filter functions
		 * @param docSets the doc sets
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		private CustomBoostFactorScorer(Similarity similarity, CustomBoostFactorWeight w, Scorer scorer,
				ScoreMode scoreMode, FilterFunction[] filterFunctions, DocSet[] docSets) throws IOException {
			super(similarity);
			this.subQueryWeight = w.getValue();
			this.scorer = scorer;
			this.scoreMode = scoreMode;
			this.filterFunctions = filterFunctions;
			this.docSets = docSets;
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
			int docId = scorer.docID();
			float factor = 1.0f;
			if (scoreMode == ScoreMode.First) {
				for (int i = 0; i < filterFunctions.length; i++) {
					if (docSets[i].get(docId)) {
						factor = filterFunctions[i].function.factor(docId);
						break;
					}
				}
			} else if (scoreMode == ScoreMode.Max) {
				float maxFactor = Float.NEGATIVE_INFINITY;
				for (int i = 0; i < filterFunctions.length; i++) {
					if (docSets[i].get(docId)) {
						maxFactor = Math.max(filterFunctions[i].function.factor(docId), maxFactor);
					}
				}
				if (maxFactor != Float.NEGATIVE_INFINITY) {
					factor = maxFactor;
				}
			} else if (scoreMode == ScoreMode.Min) {
				float minFactor = Float.POSITIVE_INFINITY;
				for (int i = 0; i < filterFunctions.length; i++) {
					if (docSets[i].get(docId)) {
						minFactor = Math.min(filterFunctions[i].function.factor(docId), minFactor);
					}
				}
				if (minFactor != Float.POSITIVE_INFINITY) {
					factor = minFactor;
				}
			} else if (scoreMode == ScoreMode.Multiply) {
				for (int i = 0; i < filterFunctions.length; i++) {
					if (docSets[i].get(docId)) {
						factor *= filterFunctions[i].function.factor(docId);
					}
				}
			} else { 
				float totalFactor = 0.0f;
				int count = 0;
				for (int i = 0; i < filterFunctions.length; i++) {
					if (docSets[i].get(docId)) {
						totalFactor += filterFunctions[i].function.factor(docId);
						count++;
					}
				}
				if (count != 0) {
					factor = totalFactor;
					if (scoreMode == ScoreMode.Avg) {
						factor /= count;
					}
				}
			}
			float score = scorer.score();
			return subQueryWeight * score * factor;
		}
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Query#toString(java.lang.String)
	 */
	public String toString(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("custom score (").append(subQuery.toString(field)).append(", functions: [");
		for (FilterFunction filterFunction : filterFunctions) {
			sb.append("{filter(").append(filterFunction.filter).append("), function [").append(filterFunction.function)
					.append("]}");
		}
		sb.append("])");
		sb.append(ToStringUtils.boost(getBoost()));
		return sb.toString();
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Query#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (getClass() != o.getClass())
			return false;
		FiltersFunctionScoreQuery other = (FiltersFunctionScoreQuery) o;
		if (this.getBoost() != other.getBoost())
			return false;
		if (!this.subQuery.equals(other.subQuery)) {
			return false;
		}
		return Arrays.equals(this.filterFunctions, other.filterFunctions);
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Query#hashCode()
	 */
	public int hashCode() {
		return subQuery.hashCode() + 31 * Arrays.hashCode(filterFunctions) ^ Float.floatToIntBits(getBoost());
	}
}
