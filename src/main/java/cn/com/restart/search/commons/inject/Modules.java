/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Modules.java 2012-3-29 15:15:21 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject;

import java.lang.reflect.Constructor;

import cn.com.restart.commons.Nullable;
import cn.com.restart.commons.exception.RestartException;
import cn.com.restart.commons.settings.Settings;


/**
 * The Class Modules.
 *
 * @author l.xue.nong
 */
public class Modules {

	
	/**
	 * Creates the module.
	 *
	 * @param moduleClass the module class
	 * @param settings the settings
	 * @return the module
	 * @throws ClassNotFoundException the class not found exception
	 */
	@SuppressWarnings("unchecked")
	public static Module createModule(String moduleClass, Settings settings) throws ClassNotFoundException {
		return createModule((Class<? extends Module>) settings.getClassLoader().loadClass(moduleClass), settings);
	}

	
	/**
	 * Creates the module.
	 *
	 * @param moduleClass the module class
	 * @param settings the settings
	 * @return the module
	 */
	public static Module createModule(Class<? extends Module> moduleClass, @Nullable Settings settings) {
		Constructor<? extends Module> constructor;
		try {
			constructor = moduleClass.getConstructor(Settings.class);
			try {
				return constructor.newInstance(settings);
			} catch (Exception e) {
				throw new RestartException("Failed to create module [" + moduleClass + "]", e);
			}
		} catch (NoSuchMethodException e) {
			try {
				constructor = moduleClass.getConstructor();
				try {
					return constructor.newInstance();
				} catch (Exception e1) {
					throw new RestartException("Failed to create module [" + moduleClass + "]", e);
				}
			} catch (NoSuchMethodException e1) {
				throw new RestartException("No constructor for [" + moduleClass + "]");
			}
		}
	}

	
	/**
	 * Process modules.
	 *
	 * @param modules the modules
	 */
	public static void processModules(Iterable<Module> modules) {
		for (Module module : modules) {
			if (module instanceof PreProcessModule) {
				for (Module module1 : modules) {
					((PreProcessModule) module).processModule(module1);
				}
			}
		}
	}
}
