/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:restart-search-commons RestartSearchCommonsVersion.java 2012-7-5 12:01:12 l.xue.nong$$
 */
package cn.com.restart.search;

import cn.com.restart.commons.Version;

/**
 * The Class RestartSearchCommonsVersion.
 *
 * @author l.xue.nong
 */
public class RestartSearchCommonsVersion implements Version {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2459348649169511884L;

	/* (non-Javadoc)
	 * @see cn.com.restart.commons.Version#getModuleVersion()
	 */
	@Override
	public String getModuleVersion() {
		return "0.0.1.RC1-SNAPSHOT";
	}

	/* (non-Javadoc)
	 * @see cn.com.restart.commons.Version#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "restart-search-commons";
	}

}
