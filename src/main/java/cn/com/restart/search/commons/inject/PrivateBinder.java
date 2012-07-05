/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons PrivateBinder.java 2012-3-29 15:15:14 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject;

import cn.com.restart.search.commons.inject.binder.AnnotatedElementBuilder;


/**
 * The Interface PrivateBinder.
 *
 * @author l.xue.nong
 */
public interface PrivateBinder extends Binder {

	
	/**
	 * Expose.
	 *
	 * @param key the key
	 */
	void expose(Key<?> key);

	
	/**
	 * Expose.
	 *
	 * @param type the type
	 * @return the annotated element builder
	 */
	AnnotatedElementBuilder expose(Class<?> type);

	
	/**
	 * Expose.
	 *
	 * @param type the type
	 * @return the annotated element builder
	 */
	AnnotatedElementBuilder expose(TypeLiteral<?> type);

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.Binder#withSource(java.lang.Object)
	 */
	PrivateBinder withSource(Object source);

	
	
	/**
	 * Skip sources.
	 *
	 * @param classesToSkip the classes to skip
	 * @return the private binder
	 */
	PrivateBinder skipSources(Class<?>... classesToSkip);
}
