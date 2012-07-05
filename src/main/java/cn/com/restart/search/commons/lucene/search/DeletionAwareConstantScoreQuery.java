/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons DeletionAwareConstantScoreQuery.java 2012-3-29 15:15:12 l.xue.nong$$
 */

package cn.com.restart.search.commons.lucene.search;

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
