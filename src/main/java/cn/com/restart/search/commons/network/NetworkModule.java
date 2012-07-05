/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons NetworkModule.java 2012-3-29 15:15:20 l.xue.nong$$
 */


package cn.com.restart.search.commons.network;

import cn.com.restart.search.commons.inject.AbstractModule;


/**
 * The Class NetworkModule.
 *
 * @author l.xue.nong
 */
public class NetworkModule extends AbstractModule {

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		bind(NetworkService.class).asEagerSingleton();
	}
}
