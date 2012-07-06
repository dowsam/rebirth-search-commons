/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ResetFieldSelector.java 2012-3-29 15:15:11 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene.document;

import org.apache.lucene.document.FieldSelector;


/**
 * The Interface ResetFieldSelector.
 *
 * @author l.xue.nong
 */
public interface ResetFieldSelector extends FieldSelector {

	
	/**
	 * Reset.
	 */
	void reset();
}
