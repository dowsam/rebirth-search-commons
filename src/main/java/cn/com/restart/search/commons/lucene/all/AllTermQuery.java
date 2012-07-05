/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons AllTermQuery.java 2012-3-29 15:15:17 l.xue.nong$$
 */


package cn.com.restart.search.commons.lucene.all;

import java.io.IOException;

import org.apache.lucene.analysis.payloads.PayloadHelper;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermPositions;
import org.apache.lucene.search.ComplexExplanation;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.Similarity;
import org.apache.lucene.search.Weight;
import org.apache.lucene.search.spans.SpanScorer;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.apache.lucene.search.spans.SpanWeight;
import org.apache.lucene.search.spans.TermSpans;


/**
 * The Class AllTermQuery.
 *
 * @author l.xue.nong
 */
@SuppressWarnings("deprecation")
public class AllTermQuery extends SpanTermQuery {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6208422091631684489L;
	
	/** The include span score. */
	private boolean includeSpanScore;

	
	/**
	 * Instantiates a new all term query.
	 *
	 * @param term the term
	 */
	public AllTermQuery(Term term) {
		this(term, true);
	}

	
	/**
	 * Instantiates a new all term query.
	 *
	 * @param term the term
	 * @param includeSpanScore the include span score
	 */
	public AllTermQuery(Term term, boolean includeSpanScore) {
		super(term);
		this.includeSpanScore = includeSpanScore;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.spans.SpanQuery#createWeight(org.apache.lucene.search.Searcher)
	 */
	@Override
	public Weight createWeight(Searcher searcher) throws IOException {
		return new AllTermWeight(this, searcher);
	}

	
	/**
	 * The Class AllTermWeight.
	 *
	 * @author l.xue.nong
	 */
	protected class AllTermWeight extends SpanWeight {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 5639834957476605388L;

		
		/**
		 * Instantiates a new all term weight.
		 *
		 * @param query the query
		 * @param searcher the searcher
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		public AllTermWeight(AllTermQuery query, Searcher searcher) throws IOException {
			super(query, searcher);
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.spans.SpanWeight#scorer(org.apache.lucene.index.IndexReader, boolean, boolean)
		 */
		@Override
		public Scorer scorer(IndexReader reader, boolean scoreDocsInOrder, boolean topScorer) throws IOException {
			return new AllTermSpanScorer((TermSpans) query.getSpans(reader), this, similarity, reader.norms(query
					.getField()));
		}

		
		/**
		 * The Class AllTermSpanScorer.
		 *
		 * @author l.xue.nong
		 */
		protected class AllTermSpanScorer extends SpanScorer {
			
			/** The payload. */
			protected byte[] payload = new byte[4];

			
			/** The positions. */
			protected TermPositions positions;

			
			/** The payload score. */
			protected float payloadScore;

			
			/** The payloads seen. */
			protected int payloadsSeen;

			
			/**
			 * Instantiates a new all term span scorer.
			 *
			 * @param spans the spans
			 * @param weight the weight
			 * @param similarity the similarity
			 * @param norms the norms
			 * @throws IOException Signals that an I/O exception has occurred.
			 */
			public AllTermSpanScorer(TermSpans spans, Weight weight, Similarity similarity, byte[] norms)
					throws IOException {
				super(spans, weight, similarity, norms);
				positions = spans.getPositions();
			}

			
			/* (non-Javadoc)
			 * @see org.apache.lucene.search.spans.SpanScorer#setFreqCurrentDoc()
			 */
			@Override
			protected boolean setFreqCurrentDoc() throws IOException {
				if (!more) {
					return false;
				}
				doc = spans.doc();
				freq = 0.0f;
				payloadScore = 0;
				payloadsSeen = 0;
				Similarity similarity1 = getSimilarity();
				while (more && doc == spans.doc()) {
					int matchLength = spans.end() - spans.start();

					freq += similarity1.sloppyFreq(matchLength);
					processPayload(similarity1);

					more = spans.next();
					
				}
				return more || (freq != 0);
			}

			
			/**
			 * Process payload.
			 *
			 * @param similarity the similarity
			 * @throws IOException Signals that an I/O exception has occurred.
			 */
			protected void processPayload(Similarity similarity) throws IOException {
				if (positions.isPayloadAvailable()) {
					payload = positions.getPayload(payload, 0);
					payloadScore += PayloadHelper.decodeFloat(payload);
					payloadsSeen++;

				} else {
					
				}
			}

			
			/* (non-Javadoc)
			 * @see org.apache.lucene.search.spans.SpanScorer#score()
			 */
			@Override
			public float score() throws IOException {
				return includeSpanScore ? getSpanScore() * getPayloadScore() : getPayloadScore();
			}

			
			/**
			 * Gets the span score.
			 *
			 * @return the span score
			 * @throws IOException Signals that an I/O exception has occurred.
			 */
			protected float getSpanScore() throws IOException {
				return super.score();
			}

			
			/**
			 * Gets the payload score.
			 *
			 * @return the payload score
			 */
			protected float getPayloadScore() {
				return payloadsSeen > 0 ? (payloadScore / payloadsSeen) : 1;
			}

			
			/* (non-Javadoc)
			 * @see org.apache.lucene.search.spans.SpanScorer#explain(int)
			 */
			@Override
			protected Explanation explain(final int doc) throws IOException {
				ComplexExplanation result = new ComplexExplanation();
				Explanation nonPayloadExpl = super.explain(doc);
				result.addDetail(nonPayloadExpl);
				
				
				Explanation payloadBoost = new Explanation();
				result.addDetail(payloadBoost);

				float payloadScore = getPayloadScore();
				payloadBoost.setValue(payloadScore);
				
				
				payloadBoost.setDescription("allPayload(...)");
				result.setValue(nonPayloadExpl.getValue() * payloadScore);
				result.setDescription("btq, product of:");
				result.setMatch(nonPayloadExpl.getValue() == 0 ? Boolean.FALSE : Boolean.TRUE); 
				return result;
			}

		}
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.spans.SpanTermQuery#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (includeSpanScore ? 1231 : 1237);
		return result;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.spans.SpanTermQuery#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AllTermQuery other = (AllTermQuery) obj;
		if (includeSpanScore != other.includeSpanScore)
			return false;
		return true;
	}

}
