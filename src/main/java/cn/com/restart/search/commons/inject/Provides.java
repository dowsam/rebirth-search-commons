/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Provides.java 2012-3-29 15:15:17 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * The Interface Provides.
 *
 * @author l.xue.nong
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface Provides {
}
