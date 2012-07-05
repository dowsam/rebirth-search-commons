/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Nullability.java 2012-3-29 15:15:18 l.xue.nong$$
 */

package cn.com.restart.search.commons.inject.internal;

import java.lang.annotation.Annotation;


/**
 * The Class Nullability.
 *
 * @author l.xue.nong
 */
public class Nullability {
    
    
    /**
     * Instantiates a new nullability.
     */
    private Nullability() {
    }

    
    /**
     * Allows null.
     *
     * @param annotations the annotations
     * @return true, if successful
     */
    public static boolean allowsNull(Annotation[] annotations) {
        for (Annotation a : annotations) {
            if ("Nullable".equals(a.annotationType().getSimpleName())) {
                return true;
            }
        }
        return false;
    }
}
