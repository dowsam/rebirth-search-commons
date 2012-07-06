/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons DeletionAwareConstantScoreQuery.java 2012-7-6 10:23:41 l.xue.nong$$
 */

package cn.com.rebirth.search.commons.lucene.search;

import org.apache.lucene.search.ConstantScoreQuery;
import org.apache.lucene.search.Filter;


/**
 * The Class DeletionAwareConstantScoreQuery.
 *
 * @author l.xue.nong
 */
public class DeletionAwareConstantScoreQuery extends ConstantScoreQuery {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3520783943329436711L;

	
	/** The actual filter. */
	private final Filter actualFilter;

	
	/**
	 * Instantiates a new deletion aware constant score query.
	 *
	 * @param filter the filter
	 */
	public DeletionAwareConstantScoreQuery(Filter filter) {
		super(new NotDeletedFilter(filter));
		this.actualFilter = filter;
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.apache.lucene.search.ConstantScoreQuery#getFilter()
	 */
	@Override
	public Filter getFilter() {
		return this.actualFilter;
	}
}
