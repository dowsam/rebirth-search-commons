/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ProvidedBy.java 2012-3-29 15:15:08 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * The Interface ProvidedBy.
 *
 * @author l.xue.nong
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface ProvidedBy {

    
    /**
     * Value.
     *
     * @return the class<? extends provider<?>>
     */
    Class<? extends Provider<?>> value();
}
