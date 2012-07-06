/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons SingleFieldSelector.java 2012-3-29 15:15:17 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene.document;

import org.apache.lucene.document.FieldSelectorResult;


/**
 * The Class SingleFieldSelector.
 *
 * @author l.xue.nong
 */
public class SingleFieldSelector implements ResetFieldSelector {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -909137294214534420L;
	
	/** The name. */
	private String name;

	
	/**
	 * Instantiates a new single field selector.
	 */
	public SingleFieldSelector() {
	}

	
	/**
	 * Instantiates a new single field selector.
	 *
	 * @param name the name
	 */
	public SingleFieldSelector(String name) {
		this.name = name;
	}

	
	/**
	 * Name.
	 *
	 * @param name the name
	 */
	public void name(String name) {
		this.name = name;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.document.FieldSelector#accept(java.lang.String)
	 */
	@Override
	public FieldSelectorResult accept(String fieldName) {
		if (name.equals(fieldName)) {
			return FieldSelectorResult.LOAD;
		}
		return FieldSelectorResult.NO_LOAD;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.lucene.document.ResetFieldSelector#reset()
	 */
	@Override
	public void reset() {
	}
}
