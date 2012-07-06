/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Singleton.java 2012-3-29 15:15:11 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * The Interface Singleton.
 *
 * @author l.xue.nong
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RUNTIME)
@ScopeAnnotation
public @interface Singleton {
}