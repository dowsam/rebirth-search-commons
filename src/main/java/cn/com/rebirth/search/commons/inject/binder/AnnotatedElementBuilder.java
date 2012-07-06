/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons AnnotatedElementBuilder.java 2012-3-29 15:15:11 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject.binder;

import java.lang.annotation.Annotation;


/**
 * The Interface AnnotatedElementBuilder.
 *
 * @author l.xue.nong
 */
public interface AnnotatedElementBuilder {

    
    /**
     * Annotated with.
     *
     * @param annotationType the annotation type
     */
    void annotatedWith(Class<? extends Annotation> annotationType);

    
    /**
     * Annotated with.
     *
     * @param annotation the annotation
     */
    void annotatedWith(Annotation annotation);
}
