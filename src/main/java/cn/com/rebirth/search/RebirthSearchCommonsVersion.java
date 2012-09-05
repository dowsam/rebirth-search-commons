/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons rebirthSearchCommonsVersion.java 2012-7-6 10:23:45 l.xue.nong$$
 */
package cn.com.rebirth.search;

import cn.com.rebirth.commons.AbstractVersion;
import cn.com.rebirth.commons.Version;

/**
 * The Class rebirthSearchCommonsVersion.
 *
 * @author l.xue.nong
 */
public class RebirthSearchCommonsVersion extends AbstractVersion implements Version {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2459348649169511884L;

	/* (non-Javadoc)
	 * @see cn.com.rebirth.commons.Version#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "rebirth-search-commons";
	}

}
