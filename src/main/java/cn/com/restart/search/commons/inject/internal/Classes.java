/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Classes.java 2012-3-29 15:15:16 l.xue.nong$$
 */

package cn.com.restart.search.commons.inject.internal;

import java.lang.reflect.Modifier;


/**
 * The Class Classes.
 *
 * @author l.xue.nong
 */
public class Classes {

    
    /**
     * Checks if is inner class.
     *
     * @param clazz the clazz
     * @return true, if is inner class
     */
    public static boolean isInnerClass(Class<?> clazz) {
        return !Modifier.isStatic(clazz.getModifiers())
                && clazz.getEnclosingClass() != null;
    }

    
    /**
     * Checks if is concrete.
     *
     * @param clazz the clazz
     * @return true, if is concrete
     */
    public static boolean isConcrete(Class<?> clazz) {
        int modifiers = clazz.getModifiers();
        return !clazz.isInterface() && !Modifier.isAbstract(modifiers);
    }
}
