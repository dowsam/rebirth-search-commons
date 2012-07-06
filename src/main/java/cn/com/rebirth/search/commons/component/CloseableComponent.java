/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons CloseableComponent.java 2012-3-29 15:15:14 l.xue.nong$$
 */
package cn.com.rebirth.search.commons.component;

import cn.com.rebirth.commons.exception.RestartException;



/**
 * The Interface CloseableComponent.
 *
 * @author l.xue.nong
 */
public interface CloseableComponent {

	
	/**
	 * Close.
	 *
	 * @throws SumMallSearchException the sum mall search exception
	 */
	void close() throws RestartException;
}
