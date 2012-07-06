/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons TypeConverter.java 2012-3-29 15:15:10 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject.spi;

import cn.com.rebirth.search.commons.inject.TypeLiteral;


/**
 * The Interface TypeConverter.
 *
 * @author l.xue.nong
 */
public interface TypeConverter {

	
	/**
	 * Convert.
	 *
	 * @param value the value
	 * @param toType the to type
	 * @return the object
	 */
	Object convert(String value, TypeLiteral<?> toType);
}
