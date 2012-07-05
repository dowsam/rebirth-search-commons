/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ScopedBindingBuilder.java 2012-3-29 15:15:10 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.binder;

import java.lang.annotation.Annotation;

import cn.com.restart.search.commons.inject.Scope;


/**
 * The Interface ScopedBindingBuilder.
 *
 * @author l.xue.nong
 */
public interface ScopedBindingBuilder {

	
	/**
	 * In.
	 *
	 * @param scopeAnnotation the scope annotation
	 */
	void in(Class<? extends Annotation> scopeAnnotation);

	
	/**
	 * In.
	 *
	 * @param scope the scope
	 */
	void in(Scope scope);

	
	/**
	 * As eager singleton.
	 */
	void asEagerSingleton();
}
