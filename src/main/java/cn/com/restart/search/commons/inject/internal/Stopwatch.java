/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Stopwatch.java 2012-3-29 15:15:19 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.internal;

import java.util.logging.Logger;


/**
 * The Class Stopwatch.
 *
 * @author l.xue.nong
 */
public class Stopwatch {
    
    
    /** The Constant logger. */
    private static final Logger logger = Logger.getLogger(Stopwatch.class.getName());

    
    /** The start. */
    private long start = System.currentTimeMillis();

    
    /**
     * Reset.
     *
     * @return the long
     */
    public long reset() {
        long now = System.currentTimeMillis();
        try {
            return now - start;
        } finally {
            start = now;
        }
    }

    
    /**
     * Reset and log.
     *
     * @param label the label
     */
    public void resetAndLog(String label) {
        logger.fine(label + ": " + reset() + "ms");
    }
}
