/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons RateLimiter.java 2012-3-29 15:15:11 l.xue.nong$$
 */


package cn.com.rebirth.search.commons;

import cn.com.rebirth.commons.exception.RestartInterruptedException;


/**
 * The Class RateLimiter.
 *
 * @author l.xue.nong
 */
public class RateLimiter {

	
	/** The ns per byte. */
	private volatile double nsPerByte;

	
	/** The last ns. */
	private volatile long lastNS;

	
	/**
	 * Instantiates a new rate limiter.
	 *
	 * @param mbPerSec the mb per sec
	 */
	public RateLimiter(double mbPerSec) {
		setMaxRate(mbPerSec);
	}

	
	/**
	 * Sets the max rate.
	 *
	 * @param mbPerSec the new max rate
	 */
	public void setMaxRate(double mbPerSec) {
		nsPerByte = 1000000000. / (1024 * 1024 * mbPerSec);
	}

	
	/**
	 * Pause.
	 *
	 * @param bytes the bytes
	 */
	public void pause(long bytes) {
		final long targetNS = lastNS = lastNS + ((long) (bytes * nsPerByte));
		long curNS = System.nanoTime();
		if (lastNS < curNS) {
			lastNS = curNS;
		}
		while (true) {
			final long pauseNS = targetNS - curNS;
			if (pauseNS > 0) {
				try {
					Thread.sleep((int) (pauseNS / 1000000), (int) (pauseNS % 1000000));
				} catch (InterruptedException ie) {
					throw new RestartInterruptedException("interrupted while rate limiting", ie);
				}
				curNS = System.nanoTime();
				continue;
			}
			break;
		}
	}
}
