/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Nullable.java 2012-3-29 15:15:13 l.xue.nong$$
 */

package cn.com.rebirth.search.commons.inject.internal;

import java.lang.annotation.*;


/**
 * The Interface Nullable.
 *
 * @author l.xue.nong
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface Nullable {
}
