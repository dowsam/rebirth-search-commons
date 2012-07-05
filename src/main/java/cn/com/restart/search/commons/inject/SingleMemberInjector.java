/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons SingleMemberInjector.java 2012-3-29 15:15:12 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject;

import cn.com.restart.search.commons.inject.internal.Errors;
import cn.com.restart.search.commons.inject.internal.InternalContext;
import cn.com.restart.search.commons.inject.spi.InjectionPoint;


/**
 * The Interface SingleMemberInjector.
 *
 * @author l.xue.nong
 */
interface SingleMemberInjector {

	
	/**
	 * Inject.
	 *
	 * @param errors the errors
	 * @param context the context
	 * @param o the o
	 */
	void inject(Errors errors, InternalContext context, Object o);

	
	/**
	 * Gets the injection point.
	 *
	 * @return the injection point
	 */
	InjectionPoint getInjectionPoint();
}
