/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons TypeConverterBinding.java 2012-3-29 15:15:09 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.spi;

import static com.google.common.base.Preconditions.checkNotNull;
import cn.com.restart.search.commons.inject.Binder;
import cn.com.restart.search.commons.inject.TypeLiteral;
import cn.com.restart.search.commons.inject.matcher.Matcher;


/**
 * The Class TypeConverterBinding.
 *
 * @author l.xue.nong
 */
public final class TypeConverterBinding implements Element {

	
	/** The source. */
	private final Object source;

	
	/** The type matcher. */
	private final Matcher<? super TypeLiteral<?>> typeMatcher;

	
	/** The type converter. */
	private final TypeConverter typeConverter;

	
	/**
	 * Instantiates a new type converter binding.
	 *
	 * @param source the source
	 * @param typeMatcher the type matcher
	 * @param typeConverter the type converter
	 */
	TypeConverterBinding(Object source, Matcher<? super TypeLiteral<?>> typeMatcher, TypeConverter typeConverter) {
		this.source = checkNotNull(source, "source");
		this.typeMatcher = checkNotNull(typeMatcher, "typeMatcher");
		this.typeConverter = checkNotNull(typeConverter, "typeConverter");
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.Element#getSource()
	 */
	public Object getSource() {
		return source;
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
	 * Gets the type converter.
	 *
	 * @return the type converter
	 */
	public TypeConverter getTypeConverter() {
		return typeConverter;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.Element#acceptVisitor(cn.com.summall.search.commons.inject.spi.ElementVisitor)
	 */
	public <T> T acceptVisitor(ElementVisitor<T> visitor) {
		return visitor.visit(this);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.Element#applyTo(cn.com.summall.search.commons.inject.Binder)
	 */
	public void applyTo(Binder binder) {
		binder.withSource(getSource()).convertToTypes(typeMatcher, typeConverter);
	}
}
