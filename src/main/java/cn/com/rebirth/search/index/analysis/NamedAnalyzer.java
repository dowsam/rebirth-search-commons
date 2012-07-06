/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons NamedAnalyzer.java 2012-3-29 15:15:03 l.xue.nong$$
 */

package cn.com.rebirth.search.index.analysis;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Fieldable;

/**
 * The Class NamedAnalyzer.
 *
 * @author l.xue.nong
 */
public class NamedAnalyzer extends Analyzer {

	/** The name. */
	private final String name;

	/** The scope. */
	private final AnalyzerScope scope;

	/** The analyzer. */
	private final Analyzer analyzer;

	/**
	 * Instantiates a new named analyzer.
	 *
	 * @param name the name
	 * @param analyzer the analyzer
	 */
	public NamedAnalyzer(String name, Analyzer analyzer) {
		this(name, AnalyzerScope.INDEX, analyzer);
	}

	/**
	 * Instantiates a new named analyzer.
	 *
	 * @param name the name
	 * @param scope the scope
	 * @param analyzer the analyzer
	 */
	public NamedAnalyzer(String name, AnalyzerScope scope, Analyzer analyzer) {
		this.name = name;
		this.scope = scope;
		this.analyzer = analyzer;
	}

	/**
	 * Name.
	 *
	 * @return the string
	 */
	public String name() {
		return this.name;
	}

	/**
	 * Scope.
	 *
	 * @return the analyzer scope
	 */
	public AnalyzerScope scope() {
		return this.scope;
	}

	/**
	 * Analyzer.
	 *
	 * @return the analyzer
	 */
	public Analyzer analyzer() {
		return this.analyzer;
	}

	/* (non-Javadoc)
	 * @see org.apache.lucene.analysis.Analyzer#tokenStream(java.lang.String, java.io.Reader)
	 */
	@Override
	public final TokenStream tokenStream(String fieldName, Reader reader) {
		return analyzer.tokenStream(fieldName, reader);
	}

	/* (non-Javadoc)
	 * @see org.apache.lucene.analysis.Analyzer#reusableTokenStream(java.lang.String, java.io.Reader)
	 */
	@Override
	public final TokenStream reusableTokenStream(String fieldName, Reader reader) throws IOException {
		return analyzer.reusableTokenStream(fieldName, reader);
	}

	/* (non-Javadoc)
	 * @see org.apache.lucene.analysis.Analyzer#getPositionIncrementGap(java.lang.String)
	 */
	@Override
	public int getPositionIncrementGap(String fieldName) {
		return analyzer.getPositionIncrementGap(fieldName);
	}

	/* (non-Javadoc)
	 * @see org.apache.lucene.analysis.Analyzer#getOffsetGap(org.apache.lucene.document.Fieldable)
	 */
	@Override
	public int getOffsetGap(Fieldable field) {
		return analyzer.getOffsetGap(field);
	}

	/* (non-Javadoc)
	 * @see org.apache.lucene.analysis.Analyzer#close()
	 */
	@Override
	public void close() {
		analyzer.close();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "analyzer name[" + name + "], analyzer [" + analyzer + "]";
	}
}
