/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons FieldBuilder.java 2012-3-29 15:15:08 l.xue.nong$$
 */


package cn.com.restart.search.commons.lucene;

import org.apache.lucene.document.Field;
import org.apache.lucene.index.FieldInfo.IndexOptions;


/**
 * The Class FieldBuilder.
 *
 * @author l.xue.nong
 */
public class FieldBuilder {

	
	/** The field. */
	private final Field field;

	
	/**
	 * Instantiates a new field builder.
	 *
	 * @param name the name
	 * @param value the value
	 * @param store the store
	 * @param index the index
	 */
	FieldBuilder(String name, String value, Field.Store store, Field.Index index) {
		field = new Field(name, value, store, index);
	}

	
	/**
	 * Instantiates a new field builder.
	 *
	 * @param name the name
	 * @param value the value
	 * @param store the store
	 * @param index the index
	 * @param termVector the term vector
	 */
	FieldBuilder(String name, String value, Field.Store store, Field.Index index, Field.TermVector termVector) {
		field = new Field(name, value, store, index, termVector);
	}

	
	/**
	 * Instantiates a new field builder.
	 *
	 * @param name the name
	 * @param value the value
	 * @param store the store
	 */
	@SuppressWarnings("deprecation")
	FieldBuilder(String name, byte[] value, Field.Store store) {
		field = new Field(name, value, store);
	}

	
	/**
	 * Instantiates a new field builder.
	 *
	 * @param name the name
	 * @param value the value
	 * @param offset the offset
	 * @param length the length
	 * @param store the store
	 */
	@SuppressWarnings("deprecation")
	FieldBuilder(String name, byte[] value, int offset, int length, Field.Store store) {
		field = new Field(name, value, offset, length, store);
	}

	
	/**
	 * Boost.
	 *
	 * @param boost the boost
	 * @return the field builder
	 */
	public FieldBuilder boost(float boost) {
		field.setBoost(boost);
		return this;
	}

	
	/**
	 * Omit norms.
	 *
	 * @param omitNorms the omit norms
	 * @return the field builder
	 */
	public FieldBuilder omitNorms(boolean omitNorms) {
		field.setOmitNorms(omitNorms);
		return this;
	}

	
	/**
	 * Omit term freq and positions.
	 *
	 * @param omitTermFreqAndPositions the omit term freq and positions
	 * @return the field builder
	 */
	public FieldBuilder omitTermFreqAndPositions(boolean omitTermFreqAndPositions) {
		if (omitTermFreqAndPositions) {
			field.setIndexOptions(IndexOptions.DOCS_ONLY);
		} else {
			field.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
		}
		return this;
	}

	
	/**
	 * Builds the.
	 *
	 * @return the field
	 */
	public Field build() {
		return field;
	}
}
