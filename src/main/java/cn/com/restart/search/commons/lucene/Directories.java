/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Directories.java 2012-3-29 15:15:09 l.xue.nong$$
 */


package cn.com.restart.search.commons.lucene;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.lucene.store.Directory;


/**
 * The Class Directories.
 *
 * @author l.xue.nong
 */
public class Directories {

	
	/**
	 * Estimate size.
	 *
	 * @param directory the directory
	 * @return the long
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static long estimateSize(Directory directory) throws IOException {
		long estimatedSize = 0;
		String[] files = directory.listAll();
		for (String file : files) {
			try {
				estimatedSize += directory.fileLength(file);
			} catch (FileNotFoundException e) {
				
			}
		}
		return estimatedSize;
	}

	
	/**
	 * Instantiates a new directories.
	 */
	private Directories() {

	}
}
