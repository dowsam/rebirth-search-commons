/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons MatchNoDocsQuery.java 2012-7-6 10:23:47 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene.search;

import java.io.IOException;
import java.util.Set;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.ComplexExplanation;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.Weight;


/**
 * The Class MatchNoDocsQuery.
 *
 * @author l.xue.nong
 */
@SuppressWarnings("deprecation")
public final class MatchNoDocsQuery extends Query {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8787328597468328504L;

	
	/** The instance. */
	public static MatchNoDocsQuery INSTANCE = new MatchNoDocsQuery();

	
	/** The Constant HASH_CODE. */
	private static final int HASH_CODE = 12345;

	
	/**
	 * The Class MatchNoDocsWeight.
	 *
	 * @author l.xue.nong
	 */
	private class MatchNoDocsWeight extends Weight {

		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = -4447802930617968456L;

		
		/**
		 * Instantiates a new match no docs weight.
		 *
		 * @param searcher the searcher
		 */
		public MatchNoDocsWeight(final Searcher searcher) {
			searcher.getSimilarity();
		}

		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "weight(" + MatchNoDocsQuery.this + ")";
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.Weight#getQuery()
		 */
		@Override
		public Query getQuery() {
			return MatchNoDocsQuery.this;
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.Weight#getValue()
		 */
		@Override
		public float getValue() {
			return 0;
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.Weight#sumOfSquaredWeights()
		 */
		@Override
		public float sumOfSquaredWeights() {
			return 0;
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.Weight#normalize(float)
		 */
		@Override
		public void normalize(final float queryNorm) {
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.Weight#scorer(org.apache.lucene.index.IndexReader, boolean, boolean)
		 */
		@Override
		public Scorer scorer(final IndexReader reader, final boolean scoreDocsInOrder, final boolean topScorer)
				throws IOException {
			return null;
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.Weight#explain(org.apache.lucene.index.IndexReader, int)
		 */
		@Override
		public Explanation explain(final IndexReader reader, final int doc) {
			return new ComplexExplanation(false, 0, "MatchNoDocs matches nothing");
		}
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Query#createWeight(org.apache.lucene.search.Searcher)
	 */
	@Override
	public Weight createWeight(final Searcher searcher) {
		return new MatchNoDocsWeight(searcher);
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Query#extractTerms(java.util.Set)
	 */
	@Override
	public void extractTerms(final Set<Term> terms) {
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Query#toString(java.lang.String)
	 */
	@Override
	public String toString(final String field) {
		return "MatchNoDocsQuery";
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Query#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o) {
		return o instanceof MatchAllDocsQuery;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Query#hashCode()
	 */
	@Override
	public int hashCode() {
		return HASH_CODE;
	}
}