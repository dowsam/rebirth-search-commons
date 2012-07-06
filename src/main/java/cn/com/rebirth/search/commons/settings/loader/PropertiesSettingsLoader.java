/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons PropertiesSettingsLoader.java 2012-3-29 15:15:14 l.xue.nong$$
 */
package cn.com.rebirth.search.commons.settings.loader;

import static com.google.common.collect.Maps.newHashMap;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import cn.com.rebirth.search.commons.io.FastByteArrayInputStream;
import cn.com.rebirth.search.commons.io.FastStringReader;

import com.google.common.io.Closeables;


/**
 * The Class PropertiesSettingsLoader.
 *
 * @author l.xue.nong
 */
public class PropertiesSettingsLoader implements SettingsLoader {

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.settings.loader.SettingsLoader#load(java.lang.String)
	 */
	@Override
	public Map<String, String> load(String source) throws IOException {
		Properties props = new Properties();
		FastStringReader reader = new FastStringReader(source);
		try {
			props.load(reader);
			Map<String, String> result = newHashMap();
			for (Map.Entry<Object, Object> entry : props.entrySet()) {
				result.put((String) entry.getKey(), (String) entry.getValue());
			}
			return result;
		} finally {
			Closeables.closeQuietly(reader);
		}
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.settings.loader.SettingsLoader#load(byte[])
	 */
	@Override
	public Map<String, String> load(byte[] source) throws IOException {
		Properties props = new Properties();
		FastByteArrayInputStream stream = new FastByteArrayInputStream(source);
		try {
			props.load(stream);
			Map<String, String> result = newHashMap();
			for (Map.Entry<Object, Object> entry : props.entrySet()) {
				result.put((String) entry.getKey(), (String) entry.getValue());
			}
			return result;
		} finally {
			Closeables.closeQuietly(stream);
		}
	}
}
