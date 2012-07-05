/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons AllField.java 2012-3-29 15:15:20 l.xue.nong$$
 */
package cn.com.restart.search.commons.lucene.all;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.AbstractField;
import org.apache.lucene.document.Field;

import cn.com.restart.commons.exception.RestartException;


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
			throw new RestartException("Failed to create token stream");
		}
	}
}
