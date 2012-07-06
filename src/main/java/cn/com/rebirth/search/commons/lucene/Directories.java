/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons Directories.java 2012-7-6 10:23:51 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene;

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
