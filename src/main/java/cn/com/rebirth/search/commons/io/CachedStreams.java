/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons CachedStreams.java 2012-3-29 15:15:20 l.xue.nong$$
 */
package cn.com.rebirth.search.commons.io;

import cn.com.rebirth.commons.io.stream.CachedStreamInput;
import cn.com.rebirth.search.commons.io.stream.CachedStreamOutput;


/**
 * The Class CachedStreams.
 *
 * @author l.xue.nong
 */
public class CachedStreams {

	
	/**
	 * Clear.
	 */
	public static void clear() {
		CachedStreamInput.clear();
		CachedStreamOutput.clear();
	}
}