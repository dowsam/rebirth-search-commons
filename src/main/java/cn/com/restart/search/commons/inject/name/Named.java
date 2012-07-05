/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Named.java 2012-3-29 15:15:13 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.name;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import cn.com.restart.search.commons.inject.BindingAnnotation;


/**
 * The Interface Named.
 *
 * @author l.xue.nong
 */
@Retention(RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@BindingAnnotation
public @interface Named {
	
	
	/**
	 * Value.
	 *
	 * @return the string
	 */
	String value();
}
