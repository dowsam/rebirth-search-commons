/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons BindingAnnotation.java 2012-3-29 15:15:09 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * The Interface BindingAnnotation.
 *
 * @author l.xue.nong
 */
@Target(ANNOTATION_TYPE)
@Retention(RUNTIME)
public @interface BindingAnnotation {
}
