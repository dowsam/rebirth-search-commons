/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons AnnotatedConstantBindingBuilder.java 2012-3-29 15:15:20 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.binder;

import java.lang.annotation.Annotation;


/**
 * The Interface AnnotatedConstantBindingBuilder.
 *
 * @author l.xue.nong
 */
public interface AnnotatedConstantBindingBuilder {

	
	/**
	 * Annotated with.
	 *
	 * @param annotationType the annotation type
	 * @return the constant binding builder
	 */
	ConstantBindingBuilder annotatedWith(Class<? extends Annotation> annotationType);

	
	/**
	 * Annotated with.
	 *
	 * @param annotation the annotation
	 * @return the constant binding builder
	 */
	ConstantBindingBuilder annotatedWith(Annotation annotation);
}
