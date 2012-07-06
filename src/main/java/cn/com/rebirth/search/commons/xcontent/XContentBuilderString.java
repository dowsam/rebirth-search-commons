/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons XContentBuilderString.java 2012-3-29 15:15:13 l.xue.nong$$
 */
package cn.com.rebirth.search.commons.xcontent;

import cn.com.rebirth.commons.Strings;


/**
 * The Class XContentBuilderString.
 *
 * @author l.xue.nong
 */
public class XContentBuilderString {

    
    /** The underscore. */
    private final XContentString underscore;

    
    /** The camel case. */
    private final XContentString camelCase;

    
    /**
     * Instantiates a new x content builder string.
     *
     * @param value the value
     */
    public XContentBuilderString(String value) {
        underscore = new XContentString(Strings.toUnderscoreCase(value));
        camelCase = new XContentString(Strings.toCamelCase(value));
    }

    
    /**
     * Underscore.
     *
     * @return the x content string
     */
    public XContentString underscore() {
        return underscore;
    }

    
    /**
     * Camel case.
     *
     * @return the x content string
     */
    public XContentString camelCase() {
        return camelCase;
    }
}
