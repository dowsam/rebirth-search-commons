/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ExtendedFieldComparatorSource.java 2012-3-29 15:15:03 l.xue.nong$$
 */

package cn.com.rebirth.search.index.field.data;

import org.apache.lucene.search.FieldComparatorSource;


/**
 * The Class ExtendedFieldComparatorSource.
 *
 * @author l.xue.nong
 */
public abstract class ExtendedFieldComparatorSource extends FieldComparatorSource {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8497566045123027556L;

	
	/**
	 * Reduced type.
	 *
	 * @return the int
	 */
	public abstract int reducedType();
}
