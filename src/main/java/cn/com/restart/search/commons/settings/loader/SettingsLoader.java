/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons SettingsLoader.java 2012-3-29 15:15:11 l.xue.nong$$
 */
package cn.com.restart.search.commons.settings.loader;

import java.io.IOException;
import java.util.Map;


/**
 * The Interface SettingsLoader.
 *
 * @author l.xue.nong
 */
public interface SettingsLoader {

	
	/**
	 * Load.
	 *
	 * @param source the source
	 * @return the map
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	Map<String, String> load(String source) throws IOException;

	
	/**
	 * Load.
	 *
	 * @param source the source
	 * @return the map
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	Map<String, String> load(byte[] source) throws IOException;
}
