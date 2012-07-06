/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons NamedImpl.java 2012-3-29 15:15:09 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject.name;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * The Class NamedImpl.
 *
 * @author l.xue.nong
 */
@SuppressWarnings("all")
class NamedImpl implements Named, Serializable {

	
	/** The value. */
	private final String value;

	
	/**
	 * Instantiates a new named impl.
	 *
	 * @param value the value
	 */
	public NamedImpl(String value) {
		this.value = checkNotNull(value, "name");
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.name.Named#value()
	 */
	public String value() {
		return this.value;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		
		return (127 * "value".hashCode()) ^ value.hashCode();
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (!(o instanceof Named)) {
			return false;
		}

		Named other = (Named) o;
		return value.equals(other.value());
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "@" + Named.class.getName() + "(value=" + value + ")";
	}

	
	/* (non-Javadoc)
	 * @see java.lang.annotation.Annotation#annotationType()
	 */
	public Class<? extends Annotation> annotationType() {
		return Named.class;
	}

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 0;
}
