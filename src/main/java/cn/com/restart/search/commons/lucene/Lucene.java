/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Lucene.java 2012-3-29 15:15:13 l.xue.nong$$
 */


package cn.com.restart.search.commons.lucene;

import java.io.IOException;
import java.lang.reflect.Field;

import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.SegmentInfo;
import org.apache.lucene.index.SegmentReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.FieldDoc;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.restart.commons.Nullable;
import cn.com.restart.commons.io.stream.StreamInput;
import cn.com.restart.commons.io.stream.StreamOutput;
import cn.com.restart.search.index.analysis.AnalyzerScope;
import cn.com.restart.search.index.analysis.NamedAnalyzer;
import cn.com.restart.search.index.field.data.ExtendedFieldComparatorSource;


/**
 * The Class Lucene.
 *
 * @author l.xue.nong
 */
public class Lucene {

	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(Lucene.class);

	
	/** The Constant VERSION. */
	public static final Version VERSION = Version.LUCENE_35;

	
	/** The Constant ANALYZER_VERSION. */
	public static final Version ANALYZER_VERSION = VERSION;

	
	/** The Constant QUERYPARSER_VERSION. */
	public static final Version QUERYPARSER_VERSION = VERSION;

	
	/** The Constant STANDARD_ANALYZER. */
	public static final NamedAnalyzer STANDARD_ANALYZER = new NamedAnalyzer("_standard", AnalyzerScope.GLOBAL,
			new StandardAnalyzer(ANALYZER_VERSION));

	
	/** The Constant KEYWORD_ANALYZER. */
	public static final NamedAnalyzer KEYWORD_ANALYZER = new NamedAnalyzer("_keyword", AnalyzerScope.GLOBAL,
			new KeywordAnalyzer());

	
	/** The Constant NO_DOC. */
	public static final int NO_DOC = -1;

	
	/** The EMPT y_ scor e_ docs. */
	public static ScoreDoc[] EMPTY_SCORE_DOCS = new ScoreDoc[0];

	
	/**
	 * Parses the version.
	 *
	 * @param version the version
	 * @param defaultVersion the default version
	 * @return the version
	 */
	public static Version parseVersion(@Nullable String version, Version defaultVersion) {
		if (version == null) {
			return defaultVersion;
		}
		if ("3.5".equals(version)) {
			return Version.LUCENE_35;
		}
		if ("3.4".equals(version)) {
			return Version.LUCENE_34;
		}
		if ("3.3".equals(version)) {
			return Version.LUCENE_33;
		}
		if ("3.2".equals(version)) {
			return Version.LUCENE_32;
		}
		if ("3.1".equals(version)) {
			return Version.LUCENE_31;
		}
		if ("3.0".equals(version)) {
			return Version.LUCENE_30;
		}
		logger.warn("no version match {}, default to {}", version, defaultVersion);
		return defaultVersion;
	}

	
	/**
	 * Count.
	 *
	 * @param searcher the searcher
	 * @param query the query
	 * @param minScore the min score
	 * @return the long
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static long count(IndexSearcher searcher, Query query, float minScore) throws IOException {
		return count(searcher, query, null, minScore);
	}

	
	/**
	 * Count.
	 *
	 * @param searcher the searcher
	 * @param query the query
	 * @param filter the filter
	 * @param minScore the min score
	 * @return the long
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static long count(IndexSearcher searcher, Query query, Filter filter, float minScore) throws IOException {
		CountCollector countCollector = new CountCollector(minScore);
		searcher.search(query, filter, countCollector);
		return countCollector.count();
	}

	
	/**
	 * Doc id.
	 *
	 * @param reader the reader
	 * @param term the term
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static int docId(IndexReader reader, Term term) throws IOException {
		TermDocs termDocs = reader.termDocs(term);
		try {
			if (termDocs.next()) {
				return termDocs.doc();
			}
			return NO_DOC;
		} finally {
			termDocs.close();
		}
	}

	
	/**
	 * Safe close.
	 *
	 * @param writer the writer
	 * @return true, if successful
	 */
	public static boolean safeClose(IndexWriter writer) {
		if (writer == null) {
			return true;
		}
		try {
			writer.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	
	/**
	 * Read top docs.
	 *
	 * @param in the in
	 * @return the top docs
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("rawtypes")
	public static TopDocs readTopDocs(StreamInput in) throws IOException {
		if (!in.readBoolean()) {
			
			return null;
		}
		if (in.readBoolean()) {
			int totalHits = in.readVInt();
			float maxScore = in.readFloat();

			SortField[] fields = new SortField[in.readVInt()];
			for (int i = 0; i < fields.length; i++) {
				String field = null;
				if (in.readBoolean()) {
					field = in.readUTF();
				}
				fields[i] = new SortField(field, in.readVInt(), in.readBoolean());
			}

			FieldDoc[] fieldDocs = new FieldDoc[in.readVInt()];
			for (int i = 0; i < fieldDocs.length; i++) {
				Comparable[] cFields = new Comparable[in.readVInt()];
				for (int j = 0; j < cFields.length; j++) {
					byte type = in.readByte();
					if (type == 0) {
						cFields[j] = null;
					} else if (type == 1) {
						cFields[j] = in.readUTF();
					} else if (type == 2) {
						cFields[j] = in.readInt();
					} else if (type == 3) {
						cFields[j] = in.readLong();
					} else if (type == 4) {
						cFields[j] = in.readFloat();
					} else if (type == 5) {
						cFields[j] = in.readDouble();
					} else if (type == 6) {
						cFields[j] = in.readByte();
					} else if (type == 7) {
						cFields[j] = in.readShort();
					} else if (type == 8) {
						cFields[j] = in.readBoolean();
					} else {
						throw new IOException("Can't match type [" + type + "]");
					}
				}
				fieldDocs[i] = new FieldDoc(in.readVInt(), in.readFloat(), cFields);
			}
			return new TopFieldDocs(totalHits, fieldDocs, fields, maxScore);
		} else {
			int totalHits = in.readVInt();
			float maxScore = in.readFloat();

			ScoreDoc[] scoreDocs = new ScoreDoc[in.readVInt()];
			for (int i = 0; i < scoreDocs.length; i++) {
				scoreDocs[i] = new ScoreDoc(in.readVInt(), in.readFloat());
			}
			return new TopDocs(totalHits, scoreDocs, maxScore);
		}
	}

	
	/**
	 * Write top docs.
	 *
	 * @param out the out
	 * @param topDocs the top docs
	 * @param from the from
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void writeTopDocs(StreamOutput out, TopDocs topDocs, int from) throws IOException {
		if (topDocs.scoreDocs.length - from < 0) {
			out.writeBoolean(false);
			return;
		}
		out.writeBoolean(true);
		if (topDocs instanceof TopFieldDocs) {
			out.writeBoolean(true);
			TopFieldDocs topFieldDocs = (TopFieldDocs) topDocs;

			out.writeVInt(topDocs.totalHits);
			out.writeFloat(topDocs.getMaxScore());

			out.writeVInt(topFieldDocs.fields.length);
			for (SortField sortField : topFieldDocs.fields) {
				if (sortField.getField() == null) {
					out.writeBoolean(false);
				} else {
					out.writeBoolean(true);
					out.writeUTF(sortField.getField());
				}
				if (sortField.getComparatorSource() != null) {
					out.writeVInt(((ExtendedFieldComparatorSource) sortField.getComparatorSource()).reducedType());
				} else {
					out.writeVInt(sortField.getType());
				}
				out.writeBoolean(sortField.getReverse());
			}

			out.writeVInt(topDocs.scoreDocs.length - from);
			int index = 0;
			for (ScoreDoc doc : topFieldDocs.scoreDocs) {
				if (index++ < from) {
					continue;
				}
				FieldDoc fieldDoc = (FieldDoc) doc;
				out.writeVInt(fieldDoc.fields.length);
				for (Object field : fieldDoc.fields) {
					if (field == null) {
						out.writeByte((byte) 0);
					} else {
						Class<?> type = field.getClass();
						if (type == String.class) {
							out.writeByte((byte) 1);
							out.writeUTF((String) field);
						} else if (type == Integer.class) {
							out.writeByte((byte) 2);
							out.writeInt((Integer) field);
						} else if (type == Long.class) {
							out.writeByte((byte) 3);
							out.writeLong((Long) field);
						} else if (type == Float.class) {
							out.writeByte((byte) 4);
							out.writeFloat((Float) field);
						} else if (type == Double.class) {
							out.writeByte((byte) 5);
							out.writeDouble((Double) field);
						} else if (type == Byte.class) {
							out.writeByte((byte) 6);
							out.writeByte((Byte) field);
						} else if (type == Short.class) {
							out.writeByte((byte) 7);
							out.writeShort((Short) field);
						} else if (type == Boolean.class) {
							out.writeByte((byte) 8);
							out.writeBoolean((Boolean) field);
						} else {
							throw new IOException("Can't handle sort field value of type [" + type + "]");
						}
					}
				}

				out.writeVInt(doc.doc);
				out.writeFloat(doc.score);
			}
		} else {
			out.writeBoolean(false);
			out.writeVInt(topDocs.totalHits);
			out.writeFloat(topDocs.getMaxScore());

			out.writeVInt(topDocs.scoreDocs.length - from);
			int index = 0;
			for (ScoreDoc doc : topDocs.scoreDocs) {
				if (index++ < from) {
					continue;
				}
				out.writeVInt(doc.doc);
				out.writeFloat(doc.score);
			}
		}
	}

	
	/**
	 * Read explanation.
	 *
	 * @param in the in
	 * @return the explanation
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Explanation readExplanation(StreamInput in) throws IOException {
		float value = in.readFloat();
		String description = in.readUTF();
		Explanation explanation = new Explanation(value, description);
		if (in.readBoolean()) {
			int size = in.readVInt();
			for (int i = 0; i < size; i++) {
				explanation.addDetail(readExplanation(in));
			}
		}
		return explanation;
	}

	
	/**
	 * Write explanation.
	 *
	 * @param out the out
	 * @param explanation the explanation
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void writeExplanation(StreamOutput out, Explanation explanation) throws IOException {
		out.writeFloat(explanation.getValue());
		out.writeUTF(explanation.getDescription());
		Explanation[] subExplanations = explanation.getDetails();
		if (subExplanations == null) {
			out.writeBoolean(false);
		} else {
			out.writeBoolean(true);
			out.writeVInt(subExplanations.length);
			for (Explanation subExp : subExplanations) {
				writeExplanation(out, subExp);
			}
		}
	}

	
	/** The Constant segmentReaderSegmentInfoField. */
	private static final Field segmentReaderSegmentInfoField;

	static {
		Field segmentReaderSegmentInfoFieldX = null;
		try {
			segmentReaderSegmentInfoFieldX = SegmentReader.class.getDeclaredField("si");
			segmentReaderSegmentInfoFieldX.setAccessible(true);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		segmentReaderSegmentInfoField = segmentReaderSegmentInfoFieldX;
	}

	
	/**
	 * Gets the segment info.
	 *
	 * @param reader the reader
	 * @return the segment info
	 */
	public static SegmentInfo getSegmentInfo(SegmentReader reader) {
		try {
			return (SegmentInfo) segmentReaderSegmentInfoField.get(reader);
		} catch (IllegalAccessException e) {
			return null;
		}
	}

	
	/**
	 * The Class CountCollector.
	 *
	 * @author l.xue.nong
	 */
	public static class CountCollector extends Collector {

		
		/** The min score. */
		private final float minScore;

		
		/** The scorer. */
		private Scorer scorer;

		
		/** The count. */
		private long count;

		
		/**
		 * Instantiates a new count collector.
		 *
		 * @param minScore the min score
		 */
		public CountCollector(float minScore) {
			this.minScore = minScore;
		}

		
		/**
		 * Count.
		 *
		 * @return the long
		 */
		public long count() {
			return this.count;
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.Collector#setScorer(org.apache.lucene.search.Scorer)
		 */
		@Override
		public void setScorer(Scorer scorer) throws IOException {
			this.scorer = scorer;
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.Collector#collect(int)
		 */
		@Override
		public void collect(int doc) throws IOException {
			if (scorer.score() > minScore) {
				count++;
			}
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.Collector#setNextReader(org.apache.lucene.index.IndexReader, int)
		 */
		@Override
		public void setNextReader(IndexReader reader, int docBase) throws IOException {
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.Collector#acceptsDocsOutOfOrder()
		 */
		@Override
		public boolean acceptsDocsOutOfOrder() {
			return true;
		}

	}

	
	/**
	 * The Class ExistsCollector.
	 *
	 * @author l.xue.nong
	 */
	public static class ExistsCollector extends Collector {

		
		/** The exists. */
		private boolean exists;

		
		/**
		 * Reset.
		 */
		public void reset() {
			exists = false;
		}

		
		/**
		 * Exists.
		 *
		 * @return true, if successful
		 */
		public boolean exists() {
			return exists;
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.Collector#setScorer(org.apache.lucene.search.Scorer)
		 */
		@Override
		public void setScorer(Scorer scorer) throws IOException {
			this.exists = false;
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.Collector#collect(int)
		 */
		@Override
		public void collect(int doc) throws IOException {
			exists = true;
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.Collector#setNextReader(org.apache.lucene.index.IndexReader, int)
		 */
		@Override
		public void setNextReader(IndexReader reader, int docBase) throws IOException {
		}

		
		/* (non-Javadoc)
		 * @see org.apache.lucene.search.Collector#acceptsDocsOutOfOrder()
		 */
		@Override
		public boolean acceptsDocsOutOfOrder() {
			return true;
		}
	}

	
	/**
	 * Instantiates a new lucene.
	 */
	private Lucene() {

	}
}
