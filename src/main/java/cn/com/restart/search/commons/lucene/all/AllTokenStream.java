/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons AllTokenStream.java 2012-3-29 15:15:18 l.xue.nong$$
 */


package cn.com.restart.search.commons.lucene.all;

import static org.apache.lucene.analysis.payloads.PayloadHelper.encodeFloat;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.PayloadAttribute;
import org.apache.lucene.index.Payload;


/**
 * The Class AllTokenStream.
 *
 * @author l.xue.nong
 */
public final class AllTokenStream extends TokenFilter {

	
	/**
	 * All token stream.
	 *
	 * @param allFieldName the all field name
	 * @param allEntries the all entries
	 * @param analyzer the analyzer
	 * @return the token stream
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static TokenStream allTokenStream(String allFieldName, AllEntries allEntries, Analyzer analyzer)
			throws IOException {
		return new AllTokenStream(analyzer.reusableTokenStream(allFieldName, allEntries), allEntries);
	}

	
	/** The all entries. */
	private final AllEntries allEntries;

	
	/** The payload attribute. */
	private final PayloadAttribute payloadAttribute;

	
	/**
	 * Instantiates a new all token stream.
	 *
	 * @param input the input
	 * @param allEntries the all entries
	 */
	AllTokenStream(TokenStream input, AllEntries allEntries) {
		super(input);
		this.allEntries = allEntries;
		payloadAttribute = addAttribute(PayloadAttribute.class);
	}

	
	/**
	 * All entries.
	 *
	 * @return the all entries
	 */
	public AllEntries allEntries() {
		return allEntries;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.analysis.TokenStream#incrementToken()
	 */
	@Override
	public final boolean incrementToken() throws IOException {
		if (!input.incrementToken()) {
			return false;
		}
		if (allEntries.current() != null) {
			float boost = allEntries.current().boost();
			if (boost != 1.0f) {
				payloadAttribute.setPayload(new Payload(encodeFloat(boost)));
			} else {
				payloadAttribute.setPayload(null);
			}
		}
		return true;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.util.AttributeSource#toString()
	 */
	@Override
	public String toString() {
		return allEntries.toString();
	}
}
