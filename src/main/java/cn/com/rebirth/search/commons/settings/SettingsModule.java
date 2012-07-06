/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons SettingsModule.java 2012-3-29 15:15:12 l.xue.nong$$
 */
package cn.com.rebirth.search.commons.settings;

import cn.com.rebirth.commons.settings.Settings;
import cn.com.rebirth.search.commons.inject.AbstractModule;


/**
 * The Class SettingsModule.
 *
 * @author l.xue.nong
 */
public class SettingsModule extends AbstractModule {

    
    /** The settings. */
    private final Settings settings;

    
    /**
     * Instantiates a new settings module.
     *
     * @param settings the settings
     */
    public SettingsModule(Settings settings) {
        this.settings = settings;
    }

    
    /* (non-Javadoc)
     * @see cn.com.summall.search.commons.inject.AbstractModule#configure()
     */
    @Override
    protected void configure() {
        bind(Settings.class).toInstance(settings);
        bind(SettingsFilter.class).asEagerSingleton();
    }
}