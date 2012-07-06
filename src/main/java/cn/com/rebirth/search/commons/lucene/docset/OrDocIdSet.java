/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons OrDocIdSet.java 2012-3-29 15:15:12 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene.docset;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.DocIdSetIterator;


/**
 * The Class OrDocIdSet.
 *
 * @author l.xue.nong
 */
public class OrDocIdSet extends DocIdSet {

	
	/** The sets. */
	private final List<DocIdSet> sets;

	
	/**
	 * Instantiates a new or doc id set.
	 *
	 * @param sets the sets
	 */
	public OrDocIdSet(List<DocIdSet> sets) {
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
		return new OrDocIdSetIterator();
	}

	
	/**
	 * The Class OrDocIdSetIterator.
	 *
	 * @author l.xue.nong
	 */
	public class OrDocIdSetIterator extends DocIdSetIterator {

		
		/**
		 * The Class Item.
		 *
		 * @author l.xue.nong
		 */
		private final class Item {

			
			/** The iter. */
			public final DocIdSetIterator iter;

			
			/** The doc. */
			public int doc;

			
			/**
			 * Instantiates a new item.
			 *
			 * @param iter the iter
			 */
			public Item(DocIdSetIterator iter) {
				this.iter = iter;
				this.doc = -1;
			}
		}

		
		/** The _cur doc. */
		private int _curDoc;

		
		/** The _heap. */
		private final Item[] _heap;

		
		/** The _size. */
		private int _size;

		
		/**
		 * Instantiates a new or doc id set iterator.
		 *
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		OrDocIdSetIterator() throws IOException {
			_curDoc = -1;
			_heap = new Item[sets.size()];
			_size = 0;
			for (DocIdSet set : sets) {
				DocIdSetIterator iterator = set.iterator();
				if (iterator != null) {
					_heap[_size++] = new Item(iterator);
				}
			}
			if (_size == 0)
				_curDoc = DocIdSetIterator.NO_MORE_DOCS;
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.DocIdSetIterator#docID()
		 */
		@Override
		public final int docID() {
			return _curDoc;
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.DocIdSetIterator#nextDoc()
		 */
		@Override
		public final int nextDoc() throws IOException {
			if (_curDoc == DocIdSetIterator.NO_MORE_DOCS)
				return DocIdSetIterator.NO_MORE_DOCS;

			Item top = _heap[0];
			while (true) {
				DocIdSetIterator topIter = top.iter;
				int docid;
				if ((docid = topIter.nextDoc()) != DocIdSetIterator.NO_MORE_DOCS) {
					top.doc = docid;
					heapAdjust();
				} else {
					heapRemoveRoot();
					if (_size == 0)
						return (_curDoc = DocIdSetIterator.NO_MORE_DOCS);
				}
				top = _heap[0];
				int topDoc = top.doc;
				if (topDoc > _curDoc) {
					return (_curDoc = topDoc);
				}
			}
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.DocIdSetIterator#advance(int)
		 */
		@Override
		public final int advance(int target) throws IOException {
			if (_curDoc == DocIdSetIterator.NO_MORE_DOCS)
				return DocIdSetIterator.NO_MORE_DOCS;

			if (target <= _curDoc)
				target = _curDoc + 1;

			Item top = _heap[0];
			while (true) {
				DocIdSetIterator topIter = top.iter;
				int docid;
				if ((docid = topIter.advance(target)) != DocIdSetIterator.NO_MORE_DOCS) {
					top.doc = docid;
					heapAdjust();
				} else {
					heapRemoveRoot();
					if (_size == 0)
						return (_curDoc = DocIdSetIterator.NO_MORE_DOCS);
				}
				top = _heap[0];
				int topDoc = top.doc;
				if (topDoc >= target) {
					return (_curDoc = topDoc);
				}
			}
		}

		
		
		

		
		/**
		 * Heap adjust.
		 */
		private final void heapAdjust() {
			final Item[] heap = _heap;
			final Item top = heap[0];
			final int doc = top.doc;
			final int size = _size;
			int i = 0;

			while (true) {
				int lchild = (i << 1) + 1;
				if (lchild >= size)
					break;

				Item left = heap[lchild];
				int ldoc = left.doc;

				int rchild = lchild + 1;
				if (rchild < size) {
					Item right = heap[rchild];
					int rdoc = right.doc;

					if (rdoc <= ldoc) {
						if (doc <= rdoc)
							break;

						heap[i] = right;
						i = rchild;
						continue;
					}
				}

				if (doc <= ldoc)
					break;

				heap[i] = left;
				i = lchild;
			}
			heap[i] = top;
		}

		

		
		/**
		 * Heap remove root.
		 */
		private void heapRemoveRoot() {
			_size--;
			if (_size > 0) {
				Item tmp = _heap[0];
				_heap[0] = _heap[_size];
				_heap[_size] = tmp; 
				heapAdjust();
			}
		}

	}
}
