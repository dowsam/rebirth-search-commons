/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Strings.java 2012-3-29 15:15:09 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.internal;


/**
 * The Class Strings.
 *
 * @author l.xue.nong
 */
public class Strings {
    
    
    /**
     * Instantiates a new strings.
     */
    private Strings() {
    }

    
    /**
     * Capitalize.
     *
     * @param s the s
     * @return the string
     */
    public static String capitalize(String s) {
        if (s.length() == 0) {
            return s;
        }
        char first = s.charAt(0);
        char capitalized = Character.toUpperCase(first);
        return (first == capitalized)
                ? s
                : capitalized + s.substring(1);
    }
}
