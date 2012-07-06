/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ImplementedBy.java 2012-3-29 15:15:17 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * The Interface ImplementedBy.
 *
 * @author l.xue.nong
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface ImplementedBy {

    
    /**
     * Value.
     *
     * @return the class
     */
    Class<?> value();
}
