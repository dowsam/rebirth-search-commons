/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Inject.java 2012-3-29 15:15:09 l.xue.nong$$
 */

package cn.com.rebirth.search.commons.inject;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * The Interface Inject.
 *
 * @author l.xue.nong
 */
@Target({ METHOD, CONSTRUCTOR, FIELD })
@Retention(RUNTIME)
@Documented
public @interface Inject {
	
	
	/**
	 * Optional.
	 *
	 * @return true, if successful
	 */
	boolean optional() default false;
}
