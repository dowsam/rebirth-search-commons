/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Assisted.java 2012-3-29 15:15:20 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.assistedinject;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import cn.com.restart.search.commons.inject.BindingAnnotation;


/**
 * The Interface Assisted.
 *
 * @author l.xue.nong
 */
@BindingAnnotation
@Target({ FIELD, PARAMETER, METHOD })
@Retention(RUNTIME)
public @interface Assisted {

	
	/**
	 * Value.
	 *
	 * @return the string
	 */
	String value() default "";
}