/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ProviderWithDependencies.java 2012-3-29 15:15:20 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.spi;

import cn.com.restart.search.commons.inject.Provider;


/**
 * The Interface ProviderWithDependencies.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
public interface ProviderWithDependencies<T> extends Provider<T>, HasDependencies {
}
