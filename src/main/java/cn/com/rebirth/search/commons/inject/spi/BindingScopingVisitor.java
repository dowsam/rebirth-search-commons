/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons BindingScopingVisitor.java 2012-3-29 15:15:10 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject.spi;

import java.lang.annotation.Annotation;

import cn.com.rebirth.search.commons.inject.Scope;


/**
 * The Interface BindingScopingVisitor.
 *
 * @param <V> the value type
 * @author l.xue.nong
 */
public interface BindingScopingVisitor<V> {

	
	/**
	 * Visit eager singleton.
	 *
	 * @return the v
	 */
	V visitEagerSingleton();

	
	/**
	 * Visit scope.
	 *
	 * @param scope the scope
	 * @return the v
	 */
	V visitScope(Scope scope);

	
	/**
	 * Visit scope annotation.
	 *
	 * @param scopeAnnotation the scope annotation
	 * @return the v
	 */
	V visitScopeAnnotation(Class<? extends Annotation> scopeAnnotation);

	
	/**
	 * Visit no scoping.
	 *
	 * @return the v
	 */
	V visitNoScoping();
}
