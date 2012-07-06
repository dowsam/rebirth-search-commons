/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons LifecycleComponent.java 2012-3-29 15:15:08 l.xue.nong$$
 */
package cn.com.rebirth.search.commons.component;

import cn.com.rebirth.commons.exception.RestartException;



/**
 * The Interface LifecycleComponent.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
public interface LifecycleComponent<T> extends CloseableComponent {

	
	/**
	 * Lifecycle state.
	 *
	 * @return the lifecycle. state
	 */
	Lifecycle.State lifecycleState();

	
	/**
	 * Adds the lifecycle listener.
	 *
	 * @param listener the listener
	 */
	void addLifecycleListener(LifecycleListener listener);

	
	/**
	 * Removes the lifecycle listener.
	 *
	 * @param listener the listener
	 */
	void removeLifecycleListener(LifecycleListener listener);

	
	/**
	 * Start.
	 *
	 * @return the t
	 * @throws SumMallSearchException the sum mall search exception
	 */
	T start() throws RestartException;

	
	/**
	 * Stop.
	 *
	 * @return the t
	 * @throws SumMallSearchException the sum mall search exception
	 */
	T stop() throws RestartException;
}
