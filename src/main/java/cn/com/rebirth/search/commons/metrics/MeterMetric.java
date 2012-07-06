/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons MeterMetric.java 2012-3-29 15:15:17 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.metrics;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import jsr166e.LongAdder;


/**
 * The Class MeterMetric.
 *
 * @author l.xue.nong
 */
public class MeterMetric implements Metric {

	
	/** The Constant INTERVAL. */
	private static final long INTERVAL = 5; 

	
	/** The m1 rate. */
	private final EWMA m1Rate = EWMA.oneMinuteEWMA();

	
	/** The m5 rate. */
	private final EWMA m5Rate = EWMA.fiveMinuteEWMA();

	
	/** The m15 rate. */
	private final EWMA m15Rate = EWMA.fifteenMinuteEWMA();

	
	/** The count. */
	private final LongAdder count = new LongAdder();

	
	/** The start time. */
	private final long startTime = System.nanoTime();

	
	/** The rate unit. */
	private final TimeUnit rateUnit;

	
	/** The future. */
	private final ScheduledFuture<?> future;

	
	/**
	 * Instantiates a new meter metric.
	 *
	 * @param tickThread the tick thread
	 * @param rateUnit the rate unit
	 */
	public MeterMetric(ScheduledExecutorService tickThread, TimeUnit rateUnit) {
		this.rateUnit = rateUnit;
		this.future = tickThread.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				tick();
			}
		}, INTERVAL, INTERVAL, TimeUnit.SECONDS);
	}

	
	/**
	 * Rate unit.
	 *
	 * @return the time unit
	 */
	public TimeUnit rateUnit() {
		return rateUnit;
	}

	
	/**
	 * Tick.
	 */
	void tick() {
		m1Rate.tick();
		m5Rate.tick();
		m15Rate.tick();
	}

	
	/**
	 * Mark.
	 */
	public void mark() {
		mark(1);
	}

	
	/**
	 * Mark.
	 *
	 * @param n the n
	 */
	public void mark(long n) {
		count.add(n);
		m1Rate.update(n);
		m5Rate.update(n);
		m15Rate.update(n);
	}

	
	/**
	 * Count.
	 *
	 * @return the long
	 */
	public long count() {
		return count.sum();
	}

	
	/**
	 * Fifteen minute rate.
	 *
	 * @return the double
	 */
	public double fifteenMinuteRate() {
		return m15Rate.rate(rateUnit);
	}

	
	/**
	 * Five minute rate.
	 *
	 * @return the double
	 */
	public double fiveMinuteRate() {
		return m5Rate.rate(rateUnit);
	}

	
	/**
	 * Mean rate.
	 *
	 * @return the double
	 */
	public double meanRate() {
		long count = count();
		if (count == 0) {
			return 0.0;
		} else {
			final long elapsed = (System.nanoTime() - startTime);
			return convertNsRate(count / (double) elapsed);
		}
	}

	
	/**
	 * One minute rate.
	 *
	 * @return the double
	 */
	public double oneMinuteRate() {
		return m1Rate.rate(rateUnit);
	}

	
	/**
	 * Convert ns rate.
	 *
	 * @param ratePerNs the rate per ns
	 * @return the double
	 */
	private double convertNsRate(double ratePerNs) {
		return ratePerNs * (double) rateUnit.toNanos(1);
	}

	
	/**
	 * Stop.
	 */
	public void stop() {
		future.cancel(false);
	}
}
