/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons CacheBuilderHelper.java 2012-3-29 15:15:21 l.xue.nong$$
 */

package cn.com.rebirth.search.commons.cache;

import com.google.common.cache.CacheBuilder;

import java.lang.reflect.Method;


/**
 * The Class CacheBuilderHelper.
 *
 * @author l.xue.nong
 */
public class CacheBuilderHelper {

	
	/** The Constant cacheBuilderDisableStatsMethod. */
	private static final Method cacheBuilderDisableStatsMethod;

	static {
		Method cacheBuilderDisableStatsMethodX = null;
		try {
			cacheBuilderDisableStatsMethodX = CacheBuilder.class.getDeclaredMethod("disableStats");
			cacheBuilderDisableStatsMethodX.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		cacheBuilderDisableStatsMethod = cacheBuilderDisableStatsMethodX;
	}

	
	/**
	 * Disable stats.
	 *
	 * @param cacheBuilder the cache builder
	 */
	@SuppressWarnings("rawtypes")
	public static void disableStats(CacheBuilder cacheBuilder) {
		if (cacheBuilderDisableStatsMethod != null) {
			try {
				cacheBuilderDisableStatsMethod.invoke(cacheBuilder);
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		}
	}
}
