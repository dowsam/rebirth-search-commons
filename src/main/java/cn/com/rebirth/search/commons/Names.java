/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Names.java 2012-3-29 15:15:19 l.xue.nong$$
 */


package cn.com.rebirth.search.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;


/**
 * The Class Names.
 *
 * @author l.xue.nong
 */
public abstract class Names {

	
	/**
	 * Random node name.
	 *
	 * @param nodeNames the node names
	 * @return the string
	 */
	public static String randomNodeName(URL nodeNames) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(nodeNames.openStream()));
			int numberOfNames = 0;
			while (reader.readLine() != null) {
				numberOfNames++;
			}
			reader.close();
			reader = new BufferedReader(new InputStreamReader(nodeNames.openStream()));
			int number = ((new Random().nextInt(numberOfNames)) % numberOfNames);
			for (int i = 0; i < number; i++) {
				reader.readLine();
			}
			return reader.readLine();
		} catch (IOException e) {
			return null;
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				
			}
		}
	}

	
	/**
	 * Random node name.
	 *
	 * @param nodeNames the node names
	 * @return the string
	 */
	public static String randomNodeName(InputStream nodeNames) {
		if (nodeNames == null) {
			return null;
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(nodeNames));
			int numberOfNames = Integer.parseInt(reader.readLine());
			int number = ((new Random().nextInt(numberOfNames)) % numberOfNames) - 2; 
			for (int i = 0; i < number; i++) {
				reader.readLine();
			}
			return reader.readLine();
		} catch (Exception e) {
			return null;
		} finally {
			try {
				nodeNames.close();
			} catch (IOException e) {
				
			}
		}
	}

	
	/**
	 * Instantiates a new names.
	 */
	private Names() {

	}
}
