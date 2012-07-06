/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons SettingsLoaderFactory.java 2012-7-6 10:23:47 l.xue.nong$$
 */
package cn.com.rebirth.search.commons.settings.loader;


/**
 * A factory for creating SettingsLoader objects.
 */
public final class SettingsLoaderFactory {

	
	/**
	 * Instantiates a new settings loader factory.
	 */
	private SettingsLoaderFactory() {

	}

	
	/**
	 * Loader from resource.
	 *
	 * @param resourceName the resource name
	 * @return the settings loader
	 */
	public static SettingsLoader loaderFromResource(String resourceName) {
		if (resourceName.endsWith(".json")) {
			return new JsonSettingsLoader();
		} else if (resourceName.endsWith(".yml")) {
			return new YamlSettingsLoader();
		} else if (resourceName.endsWith(".properties")) {
			return new PropertiesSettingsLoader();
		} else {
			
			return new JsonSettingsLoader();
		}
	}

	
	/**
	 * Loader from source.
	 *
	 * @param source the source
	 * @return the settings loader
	 */
	public static SettingsLoader loaderFromSource(String source) {
		if (source.indexOf('{') != -1 && source.indexOf('}') != -1) {
			return new JsonSettingsLoader();
		}
		if (source.indexOf(':') != -1) {
			return new YamlSettingsLoader();
		}
		return new PropertiesSettingsLoader();
	}
}
