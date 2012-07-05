/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ExposureBuilder.java 2012-3-29 15:15:15 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.internal;

import java.lang.annotation.Annotation;

import cn.com.restart.search.commons.inject.Binder;
import cn.com.restart.search.commons.inject.Key;
import cn.com.restart.search.commons.inject.binder.AnnotatedElementBuilder;


/**
 * The Class ExposureBuilder.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
public class ExposureBuilder<T> implements AnnotatedElementBuilder {
    
    
    /** The binder. */
    private final Binder binder;
    
    
    /** The source. */
    private final Object source;
    
    
    /** The key. */
    private Key<T> key;

    
    /**
     * Instantiates a new exposure builder.
     *
     * @param binder the binder
     * @param source the source
     * @param key the key
     */
    public ExposureBuilder(Binder binder, Object source, Key<T> key) {
        this.binder = binder;
        this.source = source;
        this.key = key;
    }

    
    /**
     * Check not annotated.
     */
    protected void checkNotAnnotated() {
        if (key.getAnnotationType() != null) {
            binder.addError(AbstractBindingBuilder.ANNOTATION_ALREADY_SPECIFIED);
        }
    }

    
    /* (non-Javadoc)
     * @see cn.com.summall.search.commons.inject.binder.AnnotatedElementBuilder#annotatedWith(java.lang.Class)
     */
    public void annotatedWith(Class<? extends Annotation> annotationType) {
        com.google.common.base.Preconditions.checkNotNull(annotationType, "annotationType");
        checkNotAnnotated();
        key = Key.get(key.getTypeLiteral(), annotationType);
    }

    
    /* (non-Javadoc)
     * @see cn.com.summall.search.commons.inject.binder.AnnotatedElementBuilder#annotatedWith(java.lang.annotation.Annotation)
     */
    public void annotatedWith(Annotation annotation) {
        com.google.common.base.Preconditions.checkNotNull(annotation, "annotation");
        checkNotAnnotated();
        key = Key.get(key.getTypeLiteral(), annotation);
    }

    
    /**
     * Gets the key.
     *
     * @return the key
     */
    public Key<?> getKey() {
        return key;
    }

    
    /**
     * Gets the source.
     *
     * @return the source
     */
    public Object getSource() {
        return source;
    }

    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AnnotatedElementBuilder";
    }
}