/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons AnnotatedBindingBuilder.java 2012-3-29 15:15:09 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject.binder;

import java.lang.annotation.Annotation;


/**
 * The Interface AnnotatedBindingBuilder.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
public interface AnnotatedBindingBuilder<T> extends LinkedBindingBuilder<T> {

    
    /**
     * Annotated with.
     *
     * @param annotationType the annotation type
     * @return the linked binding builder
     */
    LinkedBindingBuilder<T> annotatedWith(
            Class<? extends Annotation> annotationType);

    
    /**
     * Annotated with.
     *
     * @param annotation the annotation
     * @return the linked binding builder
     */
    LinkedBindingBuilder<T> annotatedWith(Annotation annotation);
}
