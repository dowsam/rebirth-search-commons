/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons LoggerInfoStream.java 2012-3-29 15:15:10 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene;

import java.io.OutputStream;
import java.io.PrintStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class LoggerInfoStream.
 *
 * @author l.xue.nong
 */
public class LoggerInfoStream extends PrintStream {

	
	/** The Constant SUFFIX. */
	public static final String SUFFIX = ".lucene";

	
	/**
	 * Gets the info stream.
	 *
	 * @param logger the logger
	 * @return the info stream
	 */
	public static LoggerInfoStream getInfoStream(Logger logger) {
		return new LoggerInfoStream(LoggerFactory.getLogger(LoggerInfoStream.class.getName() + SUFFIX));
	}

	
	/**
	 * Gets the info stream.
	 *
	 * @param name the name
	 * @return the info stream
	 */
	public static LoggerInfoStream getInfoStream(String name) {
		return new LoggerInfoStream(LoggerFactory.getLogger(name + SUFFIX));
	}

	
	/** The logger. */
	private final Logger logger;

	
	/**
	 * Instantiates a new logger info stream.
	 *
	 * @param logger the logger
	 */
	public LoggerInfoStream(Logger logger) {
		super((OutputStream) null);
		this.logger = logger;
	}

	
	/* (non-Javadoc)
	 * @see java.io.PrintStream#println(java.lang.String)
	 */
	@Override
	public void println(String x) {
		logger.trace(x);
	}
}
