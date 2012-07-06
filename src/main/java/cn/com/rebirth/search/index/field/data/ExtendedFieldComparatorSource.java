/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons ExtendedFieldComparatorSource.java 2012-7-6 10:23:42 l.xue.nong$$
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
