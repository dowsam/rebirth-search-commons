/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons DocSet.java 2012-3-29 15:15:12 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene.docset;

import java.io.IOException;

import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.util.Bits;


/**
 * The Class DocSet.
 *
 * @author l.xue.nong
 */
public abstract class DocSet extends DocIdSet implements Bits {

	
	/** The Constant EMPTY_DOC_SET. */
	public static final DocSet EMPTY_DOC_SET = new DocSet() {
		@Override
		public boolean get(int doc) {
			return false;
		}

		@Override
		public DocIdSetIterator iterator() throws IOException {
			return DocIdSet.EMPTY_DOCIDSET.iterator();
		}

		@Override
		public boolean isCacheable() {
			return true;
		}

		@Override
		public long sizeInBytes() {
			return 0;
		}

		@Override
		public int length() {
			return 0;
		}
	};

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.util.Bits#get(int)
	 */
	public abstract boolean get(int doc);

	
	/**
	 * Size in bytes.
	 *
	 * @return the long
	 */
	public abstract long sizeInBytes();
}
