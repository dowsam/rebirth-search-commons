/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons DocSets.java 2012-3-29 15:15:08 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene.docset;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.util.FixedBitSet;
import org.apache.lucene.util.OpenBitSet;


/**
 * The Class DocSets.
 *
 * @author l.xue.nong
 */
public class DocSets {

	
	/**
	 * Creates the fixed bit set.
	 *
	 * @param disi the disi
	 * @param numBits the num bits
	 * @return the fixed bit set
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static FixedBitSet createFixedBitSet(DocIdSetIterator disi, int numBits) throws IOException {
		FixedBitSet set = new FixedBitSet(numBits);
		int doc;
		while ((doc = disi.nextDoc()) != DocIdSetIterator.NO_MORE_DOCS) {
			set.set(doc);
		}
		return set;
	}

	
	/**
	 * Or.
	 *
	 * @param into the into
	 * @param other the other
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void or(FixedBitSet into, DocIdSet other) throws IOException {
		if (other == null) {
			return;
		}
		if (other instanceof FixedBitSet) {
			into.or((FixedBitSet) other);
		} else if (other instanceof FixedBitDocSet) {
			into.or(((FixedBitDocSet) other).set());
		} else {
			DocIdSetIterator disi = other.iterator();
			if (disi != null) {
				into.or(disi);
			}
		}
	}

	
	/**
	 * And.
	 *
	 * @param into the into
	 * @param other the other
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void and(FixedBitSet into, DocIdSet other) throws IOException {
		if (other instanceof FixedBitDocSet) {
			other = ((FixedBitDocSet) other).set();
		}
		if (other instanceof FixedBitSet) {
			into.and((FixedBitSet) other);
		} else {
			if (other == null) {
				into.clear(0, into.length());
			} else {
				
				DocIdSetIterator disi = other.iterator();
				if (disi == null) {
					into.clear(0, into.length());
				} else {
					into.and(disi);
				}
			}
		}
	}

	
	/**
	 * And not.
	 *
	 * @param into the into
	 * @param other the other
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void andNot(FixedBitSet into, DocIdSet other) throws IOException {
		if (other == null) {
			return;
		}
		if (other instanceof FixedBitDocSet) {
			other = ((FixedBitDocSet) other).set();
		}
		if (other instanceof FixedBitSet) {
			into.andNot((FixedBitSet) other);
		} else {
			
			DocIdSetIterator disi = other.iterator();
			if (disi != null) {
				into.andNot(disi);
			}
		}
	}

	
	/**
	 * Convert.
	 *
	 * @param reader the reader
	 * @param docIdSet the doc id set
	 * @return the doc set
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static DocSet convert(IndexReader reader, DocIdSet docIdSet) throws IOException {
		if (docIdSet == null) {
			return DocSet.EMPTY_DOC_SET;
		} else if (docIdSet instanceof DocSet) {
			return (DocSet) docIdSet;
		} else if (docIdSet instanceof FixedBitSet) {
			return new FixedBitDocSet((FixedBitSet) docIdSet);
		} else if (docIdSet instanceof OpenBitSet) {
			return new OpenBitDocSet((OpenBitSet) docIdSet);
		} else {
			final DocIdSetIterator it = docIdSet.iterator();
			
			
			
			return (it == null) ? DocSet.EMPTY_DOC_SET : new FixedBitDocSet(createFixedBitSet(it, reader.maxDoc()));
		}
	}

	
	/**
	 * Cacheable.
	 *
	 * @param reader the reader
	 * @param docIdSet the doc id set
	 * @return the doc set
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static DocSet cacheable(IndexReader reader, DocIdSet docIdSet) throws IOException {
		if (docIdSet == null) {
			return DocSet.EMPTY_DOC_SET;
		} else if (docIdSet.isCacheable() && (docIdSet instanceof DocSet)) {
			return (DocSet) docIdSet;
		} else if (docIdSet instanceof FixedBitSet) {
			return new FixedBitDocSet((FixedBitSet) docIdSet);
		} else if (docIdSet instanceof OpenBitSet) {
			return new OpenBitDocSet((OpenBitSet) docIdSet);
		} else {
			final DocIdSetIterator it = docIdSet.iterator();
			
			
			
			return (it == null) ? DocSet.EMPTY_DOC_SET : new FixedBitDocSet(createFixedBitSet(it, reader.maxDoc()));
		}
	}

	
	/**
	 * Instantiates a new doc sets.
	 */
	private DocSets() {

	}

}
