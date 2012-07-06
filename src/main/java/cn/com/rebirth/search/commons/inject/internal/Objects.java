/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Objects.java 2012-3-29 15:15:12 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject.internal;

import java.util.Arrays;


/**
 * The Class Objects.
 *
 * @author l.xue.nong
 */
public final class Objects {
    
    
    /**
     * Instantiates a new objects.
     */
    private Objects() {
    }

    
    /**
     * Equal.
     *
     * @param a the a
     * @param b the b
     * @return true, if successful
     */
    public static boolean equal(@Nullable Object a, @Nullable Object b) {
        return a == b || (a != null && a.equals(b));
    }

    
    /**
     * Hash code.
     *
     * @param objects the objects
     * @return the int
     */
    public static int hashCode(Object... objects) {
        return Arrays.hashCode(objects);
    }
}
