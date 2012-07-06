/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons CounterMetric.java 2012-7-6 10:23:45 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.metrics;

import jsr166e.LongAdder;


/**
 * The Class CounterMetric.
 *
 * @author l.xue.nong
 */
public class CounterMetric implements Metric {

	
	/** The counter. */
	private final LongAdder counter = new LongAdder();

	
	/**
	 * Inc.
	 */
	public void inc() {
		counter.increment();
	}

	
	/**
	 * Inc.
	 *
	 * @param n the n
	 */
	public void inc(long n) {
		counter.add(n);
	}

	
	/**
	 * Dec.
	 */
	public void dec() {
		counter.decrement();
	}

	
	/**
	 * Dec.
	 *
	 * @param n the n
	 */
	public void dec(long n) {
		counter.add(-n);
	}

	
	/**
	 * Count.
	 *
	 * @return the long
	 */
	public long count() {
		return counter.sum();
	}
}
