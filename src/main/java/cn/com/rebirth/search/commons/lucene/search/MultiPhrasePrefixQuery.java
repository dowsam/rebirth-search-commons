/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons MultiPhrasePrefixQuery.java 2012-7-6 10:23:45 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.search.MultiPhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.ToStringUtils;


/**
 * The Class MultiPhrasePrefixQuery.
 *
 * @author l.xue.nong
 */
public class MultiPhrasePrefixQuery extends Query {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7127050561775908854L;

	
	/** The field. */
	private String field;

	
	/** The term arrays. */
	private ArrayList<Term[]> termArrays = new ArrayList<Term[]>();

	
	/** The positions. */
	private ArrayList<Integer> positions = new ArrayList<Integer>();

	
	/** The max expansions. */
	private int maxExpansions = Integer.MAX_VALUE;

	
	/** The slop. */
	private int slop = 0;

	
	/**
	 * Sets the slop.
	 *
	 * @param s the new slop
	 */
	public void setSlop(int s) {
		slop = s;
	}

	
	/**
	 * Sets the max expansions.
	 *
	 * @param maxExpansions the new max expansions
	 */
	public void setMaxExpansions(int maxExpansions) {
		this.maxExpansions = maxExpansions;
	}

	
	/**
	 * Gets the slop.
	 *
	 * @return the slop
	 */
	public int getSlop() {
		return slop;
	}

	
	/**
	 * Adds the.
	 *
	 * @param term the term
	 */
	public void add(Term term) {
		add(new Term[] { term });
	}

	
	/**
	 * Adds the.
	 *
	 * @param terms the terms
	 */
	public void add(Term[] terms) {
		int position = 0;
		if (positions.size() > 0)
			position = positions.get(positions.size() - 1).intValue() + 1;

		add(terms, position);
	}

	
	/**
	 * Adds the.
	 *
	 * @param terms the terms
	 * @param position the position
	 */
	public void add(Term[] terms, int position) {
		if (termArrays.size() == 0)
			field = terms[0].field();

		for (int i = 0; i < terms.length; i++) {
			if (terms[i].field() != field) {
				throw new IllegalArgumentException("All phrase terms must be in the same field (" + field + "): "
						+ terms[i]);
			}
		}

		termArrays.add(terms);
		positions.add(Integer.valueOf(position));
	}

	
	/**
	 * Gets the term arrays.
	 *
	 * @return the term arrays
	 */
	public List<Term[]> getTermArrays() {
		return Collections.unmodifiableList(termArrays);
	}

	
	/**
	 * Gets the positions.
	 *
	 * @return the positions
	 */
	public int[] getPositions() {
		int[] result = new int[positions.size()];
		for (int i = 0; i < positions.size(); i++)
			result[i] = positions.get(i).intValue();
		return result;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Query#rewrite(org.apache.lucene.index.IndexReader)
	 */
	@Override
	public Query rewrite(IndexReader reader) throws IOException {
		if (termArrays.isEmpty()) {
			return MatchNoDocsQuery.INSTANCE;
		}
		MultiPhraseQuery query = new MultiPhraseQuery();
		query.setSlop(slop);
		int sizeMinus1 = termArrays.size() - 1;
		for (int i = 0; i < sizeMinus1; i++) {
			query.add(termArrays.get(i), positions.get(i));
		}
		Term[] suffixTerms = termArrays.get(sizeMinus1);
		int position = positions.get(sizeMinus1);
		List<Term> terms = new ArrayList<Term>();
		for (Term term : suffixTerms) {
			getPrefixTerms(terms, term, reader);
			if (terms.size() > maxExpansions) {
				break;
			}
		}
		if (terms.isEmpty()) {
			return MatchNoDocsQuery.INSTANCE;
		}
		query.add(terms.toArray(new Term[terms.size()]), position);
		return query.rewrite(reader);
	}

	
	/**
	 * Gets the prefix terms.
	 *
	 * @param terms the terms
	 * @param prefix the prefix
	 * @param reader the reader
	 * @return the prefix terms
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void getPrefixTerms(List<Term> terms, final Term prefix, final IndexReader reader) throws IOException {
		TermEnum enumerator = reader.terms(prefix);
		try {
			do {
				Term term = enumerator.term();
				if (term != null && term.text().startsWith(prefix.text()) && term.field().equals(field)) {
					terms.add(term);
				} else {
					break;
				}
				if (terms.size() >= maxExpansions) {
					break;
				}
			} while (enumerator.next());
		} finally {
			enumerator.close();
		}
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Query#toString(java.lang.String)
	 */
	@Override
	public final String toString(String f) {
		StringBuilder buffer = new StringBuilder();
		if (field == null || !field.equals(f)) {
			buffer.append(field);
			buffer.append(":");
		}

		buffer.append("\"");
		Iterator<Term[]> i = termArrays.iterator();
		while (i.hasNext()) {
			Term[] terms = i.next();
			if (terms.length > 1) {
				buffer.append("(");
				for (int j = 0; j < terms.length; j++) {
					buffer.append(terms[j].text());
					if (j < terms.length - 1)
						buffer.append(" ");
				}
				buffer.append(")");
			} else {
				buffer.append(terms[0].text());
			}
			if (i.hasNext())
				buffer.append(" ");
		}
		buffer.append("\"");

		if (slop != 0) {
			buffer.append("~");
			buffer.append(slop);
		}

		buffer.append(ToStringUtils.boost(getBoost()));

		return buffer.toString();
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Query#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof MultiPhrasePrefixQuery))
			return false;
		MultiPhrasePrefixQuery other = (MultiPhrasePrefixQuery) o;
		return this.getBoost() == other.getBoost() && this.slop == other.slop
				&& termArraysEquals(this.termArrays, other.termArrays) && this.positions.equals(other.positions);
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Query#hashCode()
	 */
	@Override
	public int hashCode() {
		return Float.floatToIntBits(getBoost()) ^ slop ^ termArraysHashCode() ^ positions.hashCode() ^ 0x4AC65113;
	}

	
	
	/**
	 * Term arrays hash code.
	 *
	 * @return the int
	 */
	private int termArraysHashCode() {
		int hashCode = 1;
		for (final Term[] termArray : termArrays) {
			hashCode = 31 * hashCode + (termArray == null ? 0 : Arrays.hashCode(termArray));
		}
		return hashCode;
	}

	
	
	/**
	 * Term arrays equals.
	 *
	 * @param termArrays1 the term arrays1
	 * @param termArrays2 the term arrays2
	 * @return true, if successful
	 */
	private boolean termArraysEquals(List<Term[]> termArrays1, List<Term[]> termArrays2) {
		if (termArrays1.size() != termArrays2.size()) {
			return false;
		}
		ListIterator<Term[]> iterator1 = termArrays1.listIterator();
		ListIterator<Term[]> iterator2 = termArrays2.listIterator();
		while (iterator1.hasNext()) {
			Term[] termArray1 = iterator1.next();
			Term[] termArray2 = iterator2.next();
			if (!(termArray1 == null ? termArray2 == null : Arrays.equals(termArray1, termArray2))) {
				return false;
			}
		}
		return true;
	}
}