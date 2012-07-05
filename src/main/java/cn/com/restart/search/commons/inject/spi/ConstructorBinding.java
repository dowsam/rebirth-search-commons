/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ConstructorBinding.java 2012-3-29 15:15:13 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.spi;

import java.util.Set;

import cn.com.restart.search.commons.inject.Binding;


/**
 * The Interface ConstructorBinding.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
public interface ConstructorBinding<T> extends Binding<T>, HasDependencies {

	
	/**
	 * Gets the constructor.
	 *
	 * @return the constructor
	 */
	InjectionPoint getConstructor();

	
	/**
	 * Gets the injectable members.
	 *
	 * @return the injectable members
	 */
	Set<InjectionPoint> getInjectableMembers();
}