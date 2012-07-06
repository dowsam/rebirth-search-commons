/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons YamlSettingsLoader.java 2012-3-29 15:15:20 l.xue.nong$$
 */
package cn.com.rebirth.search.commons.settings.loader;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import cn.com.rebirth.search.commons.io.FastByteArrayInputStream;


/**
 * The Class YamlSettingsLoader.
 *
 * @author l.xue.nong
 */
public class YamlSettingsLoader implements SettingsLoader {

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.settings.loader.SettingsLoader#load(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> load(String source) throws IOException {
		
		source = source.replace("\t", "  ");
		Yaml yaml = new Yaml();
		Map<Object, Object> yamlMap = (Map<Object, Object>) yaml.load(source);
		StringBuilder sb = new StringBuilder();
		Map<String, String> settings = newHashMap();
		if (yamlMap == null) {
			return settings;
		}
		List<String> path = newArrayList();
		serializeMap(settings, sb, path, yamlMap);
		return settings;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.settings.loader.SettingsLoader#load(byte[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> load(byte[] source) throws IOException {
		Yaml yaml = new Yaml();
		Map<Object, Object> yamlMap = (Map<Object, Object>) yaml.load(new FastByteArrayInputStream(source));
		StringBuilder sb = new StringBuilder();
		Map<String, String> settings = newHashMap();
		if (yamlMap == null) {
			return settings;
		}
		List<String> path = newArrayList();
		serializeMap(settings, sb, path, yamlMap);
		return settings;
	}

	
	/**
	 * Serialize map.
	 *
	 * @param settings the settings
	 * @param sb the sb
	 * @param path the path
	 * @param yamlMap the yaml map
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void serializeMap(Map<String, String> settings, StringBuilder sb, List<String> path,
			Map<Object, Object> yamlMap) {
		for (Map.Entry<Object, Object> entry : yamlMap.entrySet()) {
			if (entry.getValue() instanceof Map) {
				path.add((String) entry.getKey());
				serializeMap(settings, sb, path, (Map<Object, Object>) entry.getValue());
				path.remove(path.size() - 1);
			} else if (entry.getValue() instanceof List) {
				path.add((String) entry.getKey());
				serializeList(settings, sb, path, (List) entry.getValue());
				path.remove(path.size() - 1);
			} else {
				serializeValue(settings, sb, path, (String) entry.getKey(), entry.getValue());
			}
		}
	}

	
	/**
	 * Serialize list.
	 *
	 * @param settings the settings
	 * @param sb the sb
	 * @param path the path
	 * @param yamlList the yaml list
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void serializeList(Map<String, String> settings, StringBuilder sb, List<String> path, List yamlList) {
		int counter = 0;
		for (Object listEle : yamlList) {
			if (listEle instanceof Map) {
				path.add(Integer.toString(counter));
				serializeMap(settings, sb, path, (Map<Object, Object>) listEle);
				path.remove(path.size() - 1);
			} else if (listEle instanceof List) {
				path.add(Integer.toString(counter));
				serializeList(settings, sb, path, (List) listEle);
				path.remove(path.size() - 1);
			} else {
				serializeValue(settings, sb, path, Integer.toString(counter), listEle);
			}
			counter++;
		}
	}

	
	/**
	 * Serialize value.
	 *
	 * @param settings the settings
	 * @param sb the sb
	 * @param path the path
	 * @param name the name
	 * @param value the value
	 */
	private void serializeValue(Map<String, String> settings, StringBuilder sb, List<String> path, String name,
			Object value) {
		if (value == null) {
			return;
		}
		sb.setLength(0);
		for (String pathEle : path) {
			sb.append(pathEle).append('.');
		}
		sb.append(name);
		settings.put(sb.toString(), value.toString());
	}
}
