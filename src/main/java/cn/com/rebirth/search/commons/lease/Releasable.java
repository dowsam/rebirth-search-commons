/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons Releasable.java 2012-7-6 10:23:49 l.xue.nong$$
 */

package cn.com.rebirth.search.commons.lease;

import cn.com.rebirth.commons.exception.RebirthException;

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
	 * @throws rebirthException the rebirth exception
	 */
	boolean release() throws RebirthException;
}
