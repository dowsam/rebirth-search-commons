/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ForceSyncDirectory.java 2012-3-29 15:15:03 l.xue.nong$$
 */
package cn.com.restart.search.index.store.support;

import java.io.IOException;

/**
 * The Interface ForceSyncDirectory.
 *
 * @author l.xue.nong
 */
public interface ForceSyncDirectory {

	/**
	 * Force sync.
	 *
	 * @param name the name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void forceSync(String name) throws IOException;
}
