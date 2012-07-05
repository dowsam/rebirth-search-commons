/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons MatcherAndConverter.java 2012-3-29 15:15:08 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.internal;

import static com.google.common.base.Preconditions.checkNotNull;
import cn.com.restart.search.commons.inject.TypeLiteral;
import cn.com.restart.search.commons.inject.matcher.Matcher;
import cn.com.restart.search.commons.inject.spi.TypeConverter;


/**
 * The Class MatcherAndConverter.
 *
 * @author l.xue.nong
 */
public final class MatcherAndConverter {

    
    /** The type matcher. */
    private final Matcher<? super TypeLiteral<?>> typeMatcher;
    
    
    /** The type converter. */
    private final TypeConverter typeConverter;
    
    
    /** The source. */
    private final Object source;

    
    /**
     * Instantiates a new matcher and converter.
     *
     * @param typeMatcher the type matcher
     * @param typeConverter the type converter
     * @param source the source
     */
    public MatcherAndConverter(Matcher<? super TypeLiteral<?>> typeMatcher,
                               TypeConverter typeConverter, Object source) {
        this.typeMatcher = checkNotNull(typeMatcher, "type matcher");
        this.typeConverter = checkNotNull(typeConverter, "converter");
        this.source = source;
    }

    
    /**
     * Gets the type converter.
     *
     * @return the type converter
     */
    public TypeConverter getTypeConverter() {
        return typeConverter;
    }

    
    /**
     * Gets the type matcher.
     *
     * @return the type matcher
     */
    public Matcher<? super TypeLiteral<?>> getTypeMatcher() {
        return typeMatcher;
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
        return typeConverter + " which matches " + typeMatcher
                + " (bound at " + source + ")";
    }
}
