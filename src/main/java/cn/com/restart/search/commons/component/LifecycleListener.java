/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons LifecycleListener.java 2012-3-29 15:15:11 l.xue.nong$$
 */
package cn.com.restart.search.commons.component;


/**
 * The listener interface for receiving lifecycle events.
 * The class that is interested in processing a lifecycle
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addLifecycleListener<code> method. When
 * the lifecycle event occurs, that object's appropriate
 * method is invoked.
 *
 * @see LifecycleEvent
 */
public interface LifecycleListener {

	
	/**
	 * Before start.
	 */
	void beforeStart();

	
	/**
	 * After start.
	 */
	void afterStart();

	
	/**
	 * Before stop.
	 */
	void beforeStop();

	
	/**
	 * After stop.
	 */
	void afterStop();

	
	/**
	 * Before close.
	 */
	void beforeClose();

	
	/**
	 * After close.
	 */
	void afterClose();
}
