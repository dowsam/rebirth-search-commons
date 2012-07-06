/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Releasable.java 2012-3-29 15:15:11 l.xue.nong$$
 */

package cn.com.rebirth.search.commons.lease;

import cn.com.rebirth.commons.exception.RestartException;

/**
 * The Interface Releasable.
 *
 * @author l.xue.nong
 */
public interface Releasable {

	/**
	 * Release.
	 *
	 * @return true, if successful
	 * @throws SumMallSearchException the sum mall search exception
	 */
	boolean release() throws RestartException;
}
