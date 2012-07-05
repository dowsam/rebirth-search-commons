/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons AssistedInject.java 2012-3-29 15:15:17 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.assistedinject;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * The Interface AssistedInject.
 *
 * @author l.xue.nong
 */
@Target({ CONSTRUCTOR })
@Retention(RUNTIME)
public @interface AssistedInject {
}
