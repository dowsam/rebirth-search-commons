/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons AllField.java 2012-7-6 10:23:44 l.xue.nong$$
 */
package cn.com.rebirth.search.commons.lucene.all;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.AbstractField;
import org.apache.lucene.document.Field;

import cn.com.rebirth.commons.exception.RebirthException;


/**
 * The Class AllField.
 *
 * @author l.xue.nong
 */
public class AllField extends AbstractField {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2134719222421664026L;

	
	/** The all entries. */
	private final AllEntries allEntries;

	
	/** The analyzer. */
	private final Analyzer analyzer;

	
	/**
	 * Instantiates a new all field.
	 *
	 * @param name the name
	 * @param store the store
	 * @param termVector the term vector
	 * @param allEntries the all entries
	 * @param analyzer the analyzer
	 */
	public AllField(String name, Field.Store store, Field.TermVector termVector, AllEntries allEntries,
			Analyzer analyzer) {
		super(name, store, Field.Index.ANALYZED, termVector);

		this.allEntries = allEntries;
		this.analyzer = analyzer;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.document.Fieldable#stringValue()
	 */
	@Override
	public String stringValue() {
		if (isStored()) {
			return allEntries.buildText();
		}
		return null;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.document.Fieldable#readerValue()
	 */
	@Override
	public Reader readerValue() {
		return null;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.document.Fieldable#tokenStreamValue()
	 */
	@Override
	public TokenStream tokenStreamValue() {
		try {
			allEntries.reset(); 
			return AllTokenStream.allTokenStream(name, allEntries, analyzer);
		} catch (IOException e) {
			throw new RebirthException("Failed to create token stream");
		}
	}
}
