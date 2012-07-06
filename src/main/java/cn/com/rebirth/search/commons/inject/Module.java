/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Module.java 2012-3-29 15:15:18 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject;


/**
 * The Interface Module.
 *
 * @author l.xue.nong
 */
public interface Module {

	
	/**
	 * Configure.
	 *
	 * @param binder the binder
	 */
	void configure(Binder binder);
}
