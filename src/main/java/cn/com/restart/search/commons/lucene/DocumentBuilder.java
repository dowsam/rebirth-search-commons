/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons DocumentBuilder.java 2012-3-29 15:15:17 l.xue.nong$$
 */


package cn.com.restart.search.commons.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Fieldable;

import cn.com.restart.search.commons.lucene.uid.UidField;


/**
 * The Class DocumentBuilder.
 *
 * @author l.xue.nong
 */
public class DocumentBuilder {

	
	/** The Constant EMPTY. */
	public static final Document EMPTY = new Document();

	
	/**
	 * Doc.
	 *
	 * @return the document builder
	 */
	public static DocumentBuilder doc() {
		return new DocumentBuilder();
	}

	
	/**
	 * Uid field.
	 *
	 * @param value the value
	 * @return the fieldable
	 */
	public static Fieldable uidField(String value) {
		return uidField(value, 0);
	}

	
	/**
	 * Uid field.
	 *
	 * @param value the value
	 * @param version the version
	 * @return the fieldable
	 */
	public static Fieldable uidField(String value, long version) {
		return new UidField("_uid", value, version);
	}

	
	/**
	 * Field.
	 *
	 * @param name the name
	 * @param value the value
	 * @return the field builder
	 */
	public static FieldBuilder field(String name, String value) {
		return field(name, value, Field.Store.YES, Field.Index.ANALYZED);
	}

	
	/**
	 * Field.
	 *
	 * @param name the name
	 * @param value the value
	 * @param store the store
	 * @param index the index
	 * @return the field builder
	 */
	public static FieldBuilder field(String name, String value, Field.Store store, Field.Index index) {
		return new FieldBuilder(name, value, store, index);
	}

	
	/**
	 * Field.
	 *
	 * @param name the name
	 * @param value the value
	 * @param store the store
	 * @param index the index
	 * @param termVector the term vector
	 * @return the field builder
	 */
	public static FieldBuilder field(String name, String value, Field.Store store, Field.Index index,
			Field.TermVector termVector) {
		return new FieldBuilder(name, value, store, index, termVector);
	}

	
	/**
	 * Field.
	 *
	 * @param name the name
	 * @param value the value
	 * @param store the store
	 * @return the field builder
	 */
	public static FieldBuilder field(String name, byte[] value, Field.Store store) {
		return new FieldBuilder(name, value, store);
	}

	
	/**
	 * Field.
	 *
	 * @param name the name
	 * @param value the value
	 * @param offset the offset
	 * @param length the length
	 * @param store the store
	 * @return the field builder
	 */
	public static FieldBuilder field(String name, byte[] value, int offset, int length, Field.Store store) {
		return new FieldBuilder(name, value, offset, length, store);
	}

	
	/** The document. */
	private final Document document;

	
	/**
	 * Instantiates a new document builder.
	 */
	private DocumentBuilder() {
		this.document = new Document();
	}

	
	/**
	 * Boost.
	 *
	 * @param boost the boost
	 * @return the document builder
	 */
	public DocumentBuilder boost(float boost) {
		document.setBoost(boost);
		return this;
	}

	
	/**
	 * Adds the.
	 *
	 * @param field the field
	 * @return the document builder
	 */
	public DocumentBuilder add(Fieldable field) {
		document.add(field);
		return this;
	}

	
	/**
	 * Adds the.
	 *
	 * @param fieldBuilder the field builder
	 * @return the document builder
	 */
	public DocumentBuilder add(FieldBuilder fieldBuilder) {
		document.add(fieldBuilder.build());
		return this;
	}

	
	/**
	 * Builds the.
	 *
	 * @return the document
	 */
	public Document build() {
		return document;
	}
}
