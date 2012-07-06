/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons MeanMetric.java 2012-7-6 10:23:51 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.metrics;

import jsr166e.LongAdder;


/**
 * The Class MeanMetric.
 *
 * @author l.xue.nong
 */
public class MeanMetric implements Metric {

	
	/** The counter. */
	private final LongAdder counter = new LongAdder();

	
	/** The sum. */
	private final LongAdder sum = new LongAdder();

	
	/**
	 * Inc.
	 *
	 * @param n the n
	 */
	public void inc(long n) {
		counter.increment();
		sum.add(n);
	}

	
	/**
	 * Dec.
	 *
	 * @param n the n
	 */
	public void dec(long n) {
		counter.decrement();
		sum.add(-n);
	}

	
	/**
	 * Count.
	 *
	 * @return the long
	 */
	public long count() {
		return counter.sum();
	}

	
	/**
	 * Sum.
	 *
	 * @return the long
	 */
	public long sum() {
		return sum.sum();
	}

	
	/**
	 * Mean.
	 *
	 * @return the double
	 */
	public double mean() {
		long count = count();
		if (count > 0) {
			return sum.sum() / (double) count;
		}
		return 0.0;
	}

	
	/**
	 * Clear.
	 */
	public void clear() {
		counter.reset();
		sum.reset();
	}
}
