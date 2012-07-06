/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons EWMA.java 2012-3-29 15:15:14 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.metrics;

import java.util.concurrent.TimeUnit;

import jsr166e.LongAdder;


/**
 * The Class EWMA.
 *
 * @author l.xue.nong
 */
public class EWMA {

	
	/** The Constant M1_ALPHA. */
	private static final double M1_ALPHA = 1 - Math.exp(-5 / 60.0);

	
	/** The Constant M5_ALPHA. */
	private static final double M5_ALPHA = 1 - Math.exp(-5 / 60.0 / 5);

	
	/** The Constant M15_ALPHA. */
	private static final double M15_ALPHA = 1 - Math.exp(-5 / 60.0 / 15);

	
	/** The initialized. */
	private volatile boolean initialized = false;

	
	/** The rate. */
	private volatile double rate = 0.0;

	
	/** The uncounted. */
	private final LongAdder uncounted = new LongAdder();

	
	/** The interval. */
	private final double alpha, interval;

	
	/**
	 * One minute ewma.
	 *
	 * @return the eWMA
	 */
	public static EWMA oneMinuteEWMA() {
		return new EWMA(M1_ALPHA, 5, TimeUnit.SECONDS);
	}

	
	/**
	 * Five minute ewma.
	 *
	 * @return the eWMA
	 */
	public static EWMA fiveMinuteEWMA() {
		return new EWMA(M5_ALPHA, 5, TimeUnit.SECONDS);
	}

	
	/**
	 * Fifteen minute ewma.
	 *
	 * @return the eWMA
	 */
	public static EWMA fifteenMinuteEWMA() {
		return new EWMA(M15_ALPHA, 5, TimeUnit.SECONDS);
	}

	
	/**
	 * Instantiates a new eWMA.
	 *
	 * @param alpha the alpha
	 * @param interval the interval
	 * @param intervalUnit the interval unit
	 */
	public EWMA(double alpha, long interval, TimeUnit intervalUnit) {
		this.interval = intervalUnit.toNanos(interval);
		this.alpha = alpha;
	}

	
	/**
	 * Update.
	 *
	 * @param n the n
	 */
	public void update(long n) {
		uncounted.add(n);
	}

	
	/**
	 * Tick.
	 */
	public void tick() {
		final long count = uncounted.sumThenReset();
		double instantRate = count / interval;
		if (initialized) {
			rate += (alpha * (instantRate - rate));
		} else {
			rate = instantRate;
			initialized = true;
		}
	}

	
	/**
	 * Rate.
	 *
	 * @param rateUnit the rate unit
	 * @return the double
	 */
	public double rate(TimeUnit rateUnit) {
		return rate * (double) rateUnit.toNanos(1);
	}
}
