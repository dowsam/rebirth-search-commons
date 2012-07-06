/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons SettingsFilter.java 2012-3-29 15:15:16 l.xue.nong$$
 */
package cn.com.rebirth.search.commons.settings;

import java.util.concurrent.CopyOnWriteArrayList;

import cn.com.rebirth.commons.settings.Settings;
import cn.com.rebirth.search.commons.component.AbstractComponent;
import cn.com.rebirth.search.commons.inject.Inject;


/**
 * The Class SettingsFilter.
 *
 * @author l.xue.nong
 */
public class SettingsFilter extends AbstractComponent {

    
    /**
     * The Interface Filter.
     *
     * @author l.xue.nong
     */
    public static interface Filter {

        
        /**
         * Filter.
         *
         * @param settings the settings
         */
        void filter(ImmutableSettings.Builder settings);
    }

    
    /** The filters. */
    private final CopyOnWriteArrayList<Filter> filters = new CopyOnWriteArrayList<Filter>();

    
    /**
     * Instantiates a new settings filter.
     *
     * @param settings the settings
     */
    @Inject
    public SettingsFilter(Settings settings) {
        super(settings);
    }

    
    /**
     * Adds the filter.
     *
     * @param filter the filter
     */
    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    
    /**
     * Removes the filter.
     *
     * @param filter the filter
     */
    public void removeFilter(Filter filter) {
        filters.remove(filter);
    }

    
    /**
     * Filter settings.
     *
     * @param settings the settings
     * @return the settings
     */
    public Settings filterSettings(Settings settings) {
        ImmutableSettings.Builder builder = ImmutableSettings.settingsBuilder().put(settings);
        for (Filter filter : filters) {
            filter.filter(builder);
        }
        return builder.build();
    }
}
