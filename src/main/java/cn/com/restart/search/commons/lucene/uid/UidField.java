/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons UidField.java 2012-3-29 15:15:12 l.xue.nong$$
 */


package cn.com.restart.search.commons.lucene.uid;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PayloadAttribute;
import org.apache.lucene.document.AbstractField;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.FieldInfo;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Payload;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermPositions;

import cn.com.restart.search.commons.Numbers;
import cn.com.restart.search.commons.lucene.Lucene;


/**
 * The Class UidField.
 *
 * @author l.xue.nong
 */
public class UidField extends AbstractField {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1062823897307183775L;

	
	/**
	 * The Class DocIdAndVersion.
	 *
	 * @author l.xue.nong
	 */
	public static class DocIdAndVersion {

		
		/** The doc id. */
		public final int docId;

		
		/** The version. */
		public final long version;

		
		/** The reader. */
		public final IndexReader reader;

		
		/**
		 * Instantiates a new doc id and version.
		 *
		 * @param docId the doc id
		 * @param version the version
		 * @param reader the reader
		 */
		public DocIdAndVersion(int docId, long version, IndexReader reader) {
			this.docId = docId;
			this.version = version;
			this.reader = reader;
		}
	}

	
	
	
	/**
	 * Load doc id and version.
	 *
	 * @param reader the reader
	 * @param term the term
	 * @return the doc id and version
	 */
	public static DocIdAndVersion loadDocIdAndVersion(IndexReader reader, Term term) {
		int docId = Lucene.NO_DOC;
		TermPositions uid = null;
		try {
			uid = reader.termPositions(term);
			if (!uid.next()) {
				return null; 
			}
			
			
			do {
				docId = uid.doc();
				uid.nextPosition();
				if (!uid.isPayloadAvailable()) {
					continue;
				}
				if (uid.getPayloadLength() < 8) {
					continue;
				}
				byte[] payload = uid.getPayload(new byte[8], 0);
				return new DocIdAndVersion(docId, Numbers.bytesToLong(payload), reader);
			} while (uid.next());
			return new DocIdAndVersion(docId, -2, reader);
		} catch (Exception e) {
			return new DocIdAndVersion(docId, -2, reader);
		} finally {
			if (uid != null) {
				try {
					uid.close();
				} catch (IOException e) {
					
				}
			}
		}
	}

	
	/**
	 * Load version.
	 *
	 * @param reader the reader
	 * @param term the term
	 * @return the long
	 */
	public static long loadVersion(IndexReader reader, Term term) {
		TermPositions uid = null;
		try {
			uid = reader.termPositions(term);
			if (!uid.next()) {
				return -1;
			}
			
			
			do {
				uid.nextPosition();
				if (!uid.isPayloadAvailable()) {
					continue;
				}
				if (uid.getPayloadLength() < 8) {
					continue;
				}
				byte[] payload = uid.getPayload(new byte[8], 0);
				return Numbers.bytesToLong(payload);
			} while (uid.next());
			return -2;
		} catch (Exception e) {
			return -2;
		} finally {
			if (uid != null) {
				try {
					uid.close();
				} catch (IOException e) {
					
				}
			}
		}
	}

	
	/** The uid. */
	private String uid;

	
	/** The version. */
	private long version;

	
	/** The token stream. */
	private final UidPayloadTokenStream tokenStream;

	
	/**
	 * Instantiates a new uid field.
	 *
	 * @param name the name
	 * @param uid the uid
	 * @param version the version
	 */
	public UidField(String name, String uid, long version) {
		super(name, Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.NO);
		this.uid = uid;
		this.version = version;
		this.indexOptions = FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS;
		this.tokenStream = new UidPayloadTokenStream(this);
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.document.AbstractField#setIndexOptions(org.apache.lucene.index.FieldInfo.IndexOptions)
	 */
	@Override
	public void setIndexOptions(FieldInfo.IndexOptions indexOptions) {
		
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.document.AbstractField#setOmitTermFreqAndPositions(boolean)
	 */
	@Override
	public void setOmitTermFreqAndPositions(boolean omitTermFreqAndPositions) {
		
	}

	
	/**
	 * Uid.
	 *
	 * @return the string
	 */
	public String uid() {
		return this.uid;
	}

	
	/**
	 * Sets the uid.
	 *
	 * @param uid the new uid
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.document.Fieldable#stringValue()
	 */
	@Override
	public String stringValue() {
		return uid;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.document.Fieldable#readerValue()
	 */
	@Override
	public Reader readerValue() {
		return null;
	}

	
	/**
	 * Version.
	 *
	 * @return the long
	 */
	public long version() {
		return this.version;
	}

	
	/**
	 * Version.
	 *
	 * @param version the version
	 */
	public void version(long version) {
		this.version = version;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.document.Fieldable#tokenStreamValue()
	 */
	@Override
	public TokenStream tokenStreamValue() {
		return tokenStream;
	}

	
	/**
	 * The Class UidPayloadTokenStream.
	 *
	 * @author l.xue.nong
	 */
	public static final class UidPayloadTokenStream extends TokenStream {

		
		/** The payload attribute. */
		private final PayloadAttribute payloadAttribute = addAttribute(PayloadAttribute.class);

		
		/** The term att. */
		private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

		
		/** The field. */
		private final UidField field;

		
		/** The added. */
		private boolean added = false;

		
		/**
		 * Instantiates a new uid payload token stream.
		 *
		 * @param field the field
		 */
		public UidPayloadTokenStream(UidField field) {
			this.field = field;
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.analysis.TokenStream#reset()
		 */
		@Override
		public void reset() throws IOException {
			added = false;
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.analysis.TokenStream#incrementToken()
		 */
		@Override
		public final boolean incrementToken() throws IOException {
			if (added) {
				return false;
			}
			termAtt.setLength(0);
			termAtt.append(field.uid);
			payloadAttribute.setPayload(new Payload(Numbers.longToBytes(field.version())));
			added = true;
			return true;
		}
	}
}
