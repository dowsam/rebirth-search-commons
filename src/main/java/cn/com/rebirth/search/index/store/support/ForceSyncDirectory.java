/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons ForceSyncDirectory.java 2012-7-6 10:23:50 l.xue.nong$$
 */
package cn.com.rebirth.search.index.store.support;

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
