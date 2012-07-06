/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons JsonSettingsLoader.java 2012-3-29 15:15:20 l.xue.nong$$
 */
package cn.com.rebirth.search.commons.settings.loader;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.com.rebirth.search.commons.xcontent.XContentFactory;
import cn.com.rebirth.search.commons.xcontent.XContentParser;
import cn.com.rebirth.search.commons.xcontent.XContentType;


/**
 * The Class JsonSettingsLoader.
 *
 * @author l.xue.nong
 */
public class JsonSettingsLoader implements SettingsLoader {

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.settings.loader.SettingsLoader#load(java.lang.String)
	 */
	@Override
	public Map<String, String> load(String source) throws IOException {
		XContentParser parser = XContentFactory.xContent(XContentType.JSON).createParser(source);
		try {
			return load(parser);
		} finally {
			parser.close();
		}
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.settings.loader.SettingsLoader#load(byte[])
	 */
	@Override
	public Map<String, String> load(byte[] source) throws IOException {
		XContentParser parser = XContentFactory.xContent(XContentType.JSON).createParser(source);
		try {
			return load(parser);
		} finally {
			parser.close();
		}
	}

	
	/**
	 * Load.
	 *
	 * @param jp the jp
	 * @return the map
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Map<String, String> load(XContentParser jp) throws IOException {
		StringBuilder sb = new StringBuilder();
		Map<String, String> settings = newHashMap();
		List<String> path = newArrayList();
		jp.nextToken();
		serializeObject(settings, sb, path, jp, null);
		return settings;
	}

	
	/**
	 * Serialize object.
	 *
	 * @param settings the settings
	 * @param sb the sb
	 * @param path the path
	 * @param parser the parser
	 * @param objFieldName the obj field name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void serializeObject(Map<String, String> settings, StringBuilder sb, List<String> path,
			XContentParser parser, String objFieldName) throws IOException {
		if (objFieldName != null) {
			path.add(objFieldName);
		}

		String currentFieldName = null;
		XContentParser.Token token;
		while ((token = parser.nextToken()) != XContentParser.Token.END_OBJECT) {
			if (token == XContentParser.Token.START_OBJECT) {
				serializeObject(settings, sb, path, parser, currentFieldName);
			} else if (token == XContentParser.Token.START_ARRAY) {
				serializeArray(settings, sb, path, parser, currentFieldName);
			} else if (token == XContentParser.Token.FIELD_NAME) {
				currentFieldName = parser.currentName();
			} else if (token == XContentParser.Token.VALUE_NULL) {
				
			} else {
				serializeValue(settings, sb, path, parser, currentFieldName);

			}
		}

		if (objFieldName != null) {
			path.remove(path.size() - 1);
		}
	}

	
	/**
	 * Serialize array.
	 *
	 * @param settings the settings
	 * @param sb the sb
	 * @param path the path
	 * @param parser the parser
	 * @param fieldName the field name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void serializeArray(Map<String, String> settings, StringBuilder sb, List<String> path,
			XContentParser parser, String fieldName) throws IOException {
		XContentParser.Token token;
		int counter = 0;
		while ((token = parser.nextToken()) != XContentParser.Token.END_ARRAY) {
			if (token == XContentParser.Token.START_OBJECT) {
				serializeObject(settings, sb, path, parser, fieldName + '.' + (counter++));
			} else if (token == XContentParser.Token.START_ARRAY) {
				serializeArray(settings, sb, path, parser, fieldName + '.' + (counter++));
			} else if (token == XContentParser.Token.FIELD_NAME) {
				fieldName = parser.currentName();
			} else if (token == XContentParser.Token.VALUE_NULL) {
				
			} else {
				serializeValue(settings, sb, path, parser, fieldName + '.' + (counter++));
			}
		}
	}

	
	/**
	 * Serialize value.
	 *
	 * @param settings the settings
	 * @param sb the sb
	 * @param path the path
	 * @param parser the parser
	 * @param fieldName the field name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void serializeValue(Map<String, String> settings, StringBuilder sb, List<String> path,
			XContentParser parser, String fieldName) throws IOException {
		sb.setLength(0);
		for (String pathEle : path) {
			sb.append(pathEle).append('.');
		}
		sb.append(fieldName);
		settings.put(sb.toString(), parser.text());
	}

}
