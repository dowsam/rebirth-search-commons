/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons ResetFieldSelector.java 2012-7-6 10:23:45 l.xue.nong$$
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
