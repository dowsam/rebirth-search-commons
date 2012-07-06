/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons SpawnModules.java 2012-3-29 15:15:11 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject;


/**
 * The Interface SpawnModules.
 *
 * @author l.xue.nong
 */
public interface SpawnModules {

    
    /**
     * Spawn modules.
     *
     * @return the iterable<? extends module>
     */
    Iterable<? extends Module> spawnModules();
}
