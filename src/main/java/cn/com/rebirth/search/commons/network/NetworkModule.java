/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons NetworkModule.java 2012-7-6 10:23:48 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.network;

import cn.com.rebirth.search.commons.inject.AbstractModule;


/**
 * The Class NetworkModule.
 *
 * @author l.xue.nong
 */
public class NetworkModule extends AbstractModule {

	
	/* (non-Javadoc)
	 * @see cn.com.rebirth.search.commons.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		bind(NetworkService.class).asEagerSingleton();
	}
}
