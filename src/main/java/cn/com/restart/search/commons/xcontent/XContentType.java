/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons XContentType.java 2012-3-29 15:15:10 l.xue.nong$$
 */
package cn.com.restart.search.commons.xcontent;

/**
 * The Enum XContentType.
 *
 * @author l.xue.nong
 */
public enum XContentType {

    
    /** The JSON. */
    JSON(0) {
        @Override
        public String restContentType() {
            return "application/json; charset=UTF-8";
        }

        @Override
        public String shortName() {
            return "json";
        }
    },
    
    /** The SMILE. */
    SMILE(1) {
        @Override
        public String restContentType() {
            return "application/smile";
        }

        @Override
        public String shortName() {
            return "smile";
        }
    };

    
    /**
     * From rest content type.
     *
     * @param contentType the content type
     * @return the x content type
     */
    public static XContentType fromRestContentType(String contentType) {
        if (contentType == null) {
            return null;
        }
        if ("application/json".equals(contentType) || "json".equalsIgnoreCase(contentType)) {
            return JSON;
        }

        if ("application/smile".equals(contentType) || "smile".equalsIgnoreCase(contentType)) {
            return SMILE;
        }

        return null;
    }

    
    /** The index. */
    private int index;

    
    /**
     * Instantiates a new x content type.
     *
     * @param index the index
     */
    XContentType(int index) {
        this.index = index;
    }

    
    /**
     * Index.
     *
     * @return the int
     */
    public int index() {
        return index;
    }

    
    /**
     * Rest content type.
     *
     * @return the string
     */
    public abstract String restContentType();

    
    /**
     * Short name.
     *
     * @return the string
     */
    public abstract String shortName();
}
