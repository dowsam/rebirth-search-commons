/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons AndDocIdSet.java 2012-3-29 15:15:08 l.xue.nong$$
 */


package cn.com.restart.search.commons.lucene.docset;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.DocIdSetIterator;


/**
 * The Class AndDocIdSet.
 *
 * @author l.xue.nong
 */
public class AndDocIdSet extends DocIdSet {

	
	/** The sets. */
	private final List<DocIdSet> sets;

	
	/**
	 * Instantiates a new and doc id set.
	 *
	 * @param sets the sets
	 */
	public AndDocIdSet(List<DocIdSet> sets) {
		this.sets = sets;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.DocIdSet#isCacheable()
	 */
	@Override
	public boolean isCacheable() {
		
		
		return false;
		
		
		
		
		
		
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.DocIdSet#iterator()
	 */
	@Override
	public DocIdSetIterator iterator() throws IOException {
		return new AndDocIdSetIterator();
	}

	
	/**
	 * The Class AndDocIdSetIterator.
	 *
	 * @author l.xue.nong
	 */
	class AndDocIdSetIterator extends DocIdSetIterator {

		
		/** The last return. */
		int lastReturn = -1;

		
		/** The iterators. */
		private DocIdSetIterator[] iterators = null;

		
		/**
		 * Instantiates a new and doc id set iterator.
		 *
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		AndDocIdSetIterator() throws IOException {
			iterators = new DocIdSetIterator[sets.size()];
			int j = 0;
			for (DocIdSet set : sets) {
				if (set == null) {
					lastReturn = DocIdSetIterator.NO_MORE_DOCS; 
					break;
				} else {
					DocIdSetIterator dcit = set.iterator();
					if (dcit == null) {
						lastReturn = DocIdSetIterator.NO_MORE_DOCS; 
						break;
					}
					iterators[j++] = dcit;
				}
			}
			if (lastReturn != DocIdSetIterator.NO_MORE_DOCS) {
				lastReturn = (iterators.length > 0 ? -1 : DocIdSetIterator.NO_MORE_DOCS);
			}
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.DocIdSetIterator#docID()
		 */
		@Override
		public final int docID() {
			return lastReturn;
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.DocIdSetIterator#nextDoc()
		 */
		@Override
		public final int nextDoc() throws IOException {

			if (lastReturn == DocIdSetIterator.NO_MORE_DOCS)
				return DocIdSetIterator.NO_MORE_DOCS;

			DocIdSetIterator dcit = iterators[0];
			int target = dcit.nextDoc();
			int size = iterators.length;
			int skip = 0;
			int i = 1;
			while (i < size) {
				if (i != skip) {
					dcit = iterators[i];
					int docid = dcit.advance(target);
					if (docid > target) {
						target = docid;
						if (i != 0) {
							skip = i;
							i = 0;
							continue;
						} else
							skip = 0;
					}
				}
				i++;
			}
			return (lastReturn = target);
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.DocIdSetIterator#advance(int)
		 */
		@Override
		public final int advance(int target) throws IOException {

			if (lastReturn == DocIdSetIterator.NO_MORE_DOCS)
				return DocIdSetIterator.NO_MORE_DOCS;

			DocIdSetIterator dcit = iterators[0];
			target = dcit.advance(target);
			int size = iterators.length;
			int skip = 0;
			int i = 1;
			while (i < size) {
				if (i != skip) {
					dcit = iterators[i];
					int docid = dcit.advance(target);
					if (docid > target) {
						target = docid;
						if (i != 0) {
							skip = i;
							i = 0;
							continue;
						} else {
							skip = 0;
						}
					}
				}
				i++;
			}
			return (lastReturn = target);
		}
	}
}
