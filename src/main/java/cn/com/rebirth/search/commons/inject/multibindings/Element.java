/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Element.java 2012-3-29 15:15:10 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject.multibindings;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

import cn.com.rebirth.search.commons.inject.BindingAnnotation;


/**
 * The Interface Element.
 *
 * @author l.xue.nong
 */
@Retention(RUNTIME)
@BindingAnnotation
@interface Element {

	
	/**
	 * Sets the name.
	 *
	 * @return the string
	 */
	String setName();

	
	/**
	 * Unique id.
	 *
	 * @return the int
	 */
	int uniqueId();
}
