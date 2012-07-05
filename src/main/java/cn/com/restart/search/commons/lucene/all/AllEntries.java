/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons AllEntries.java 2012-3-29 15:15:12 l.xue.nong$$
 */


package cn.com.restart.search.commons.lucene.all;

import static com.google.common.collect.Sets.newHashSet;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.com.restart.commons.exception.RestartIllegalStateException;
import cn.com.restart.search.commons.io.FastCharArrayWriter;
import cn.com.restart.search.commons.io.FastStringReader;

import com.google.common.collect.Lists;


/**
 * The Class AllEntries.
 *
 * @author l.xue.nong
 */
public class AllEntries extends Reader {

	
	/**
	 * The Class Entry.
	 *
	 * @author l.xue.nong
	 */
	public static class Entry {

		
		/** The name. */
		private final String name;

		
		/** The reader. */
		private final FastStringReader reader;

		
		/** The boost. */
		private final float boost;

		
		/**
		 * Instantiates a new entry.
		 *
		 * @param name the name
		 * @param reader the reader
		 * @param boost the boost
		 */
		public Entry(String name, FastStringReader reader, float boost) {
			this.name = name;
			this.reader = reader;
			this.boost = boost;
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
		 * Boost.
		 *
		 * @return the float
		 */
		public float boost() {
			return this.boost;
		}

		
		/**
		 * Reader.
		 *
		 * @return the fast string reader
		 */
		public FastStringReader reader() {
			return this.reader;
		}
	}

	
	/** The entries. */
	private final List<Entry> entries = Lists.newArrayList();

	
	/** The current. */
	private Entry current;

	
	/** The it. */
	private Iterator<Entry> it;

	
	/** The its separator time. */
	private boolean itsSeparatorTime = false;

	
	/** The custom boost. */
	private boolean customBoost = false;

	
	/**
	 * Adds the text.
	 *
	 * @param name the name
	 * @param text the text
	 * @param boost the boost
	 */
	public void addText(String name, String text, float boost) {
		if (boost != 1.0f) {
			customBoost = true;
		}
		Entry entry = new Entry(name, new FastStringReader(text), boost);
		entries.add(entry);
	}

	
	/**
	 * Clear.
	 */
	public void clear() {
		this.entries.clear();
		this.current = null;
		this.it = null;
		itsSeparatorTime = false;
	}

	
	/* (non-Javadoc)
	 * @see java.io.Reader#reset()
	 */
	public void reset() {
		try {
			for (Entry entry : entries) {
				entry.reader().reset();
			}
		} catch (IOException e) {
			throw new RestartIllegalStateException("should not happen");
		}
		it = entries.iterator();
		if (it.hasNext()) {
			current = it.next();
			itsSeparatorTime = true;
		}
	}

	
	/**
	 * Builds the text.
	 *
	 * @return the string
	 */
	public String buildText() {
		reset();
		FastCharArrayWriter writer = new FastCharArrayWriter();
		for (Entry entry : entries) {
			writer.append(entry.reader());
			writer.append(' ');
		}
		reset();
		return writer.toString();
	}

	
	/**
	 * Entries.
	 *
	 * @return the list
	 */
	public List<Entry> entries() {
		return this.entries;
	}

	
	/**
	 * Fields.
	 *
	 * @return the sets the
	 */
	public Set<String> fields() {
		Set<String> fields = newHashSet();
		for (Entry entry : entries) {
			fields.add(entry.name());
		}
		return fields;
	}

	
	/**
	 * Current.
	 *
	 * @return the entry
	 */
	public Entry current() {
		return this.current;
	}

	
	/* (non-Javadoc)
	 * @see java.io.Reader#read(char[], int, int)
	 */
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		if (current == null) {
			return -1;
		}
		if (customBoost) {
			int result = current.reader().read(cbuf, off, len);
			if (result == -1) {
				if (itsSeparatorTime) {
					itsSeparatorTime = false;
					cbuf[off] = ' ';
					return 1;
				}
				itsSeparatorTime = true;
				
				if (it.hasNext()) {
					current = it.next();
				} else {
					current = null;
				}
				return read(cbuf, off, len);
			}
			return result;
		} else {
			int read = 0;
			while (len > 0) {
				int result = current.reader().read(cbuf, off, len);
				if (result == -1) {
					if (it.hasNext()) {
						current = it.next();
					} else {
						current = null;
						if (read == 0) {
							return -1;
						}
						return read;
					}
					cbuf[off++] = ' ';
					read++;
					len--;
				} else {
					read += result;
					off += result;
					len -= result;
				}
			}
			return read;
		}
	}

	
	/* (non-Javadoc)
	 * @see java.io.Reader#close()
	 */
	@Override
	public void close() {
		if (current != null) {
			current.reader().close();
			current = null;
		}
	}

	
	/* (non-Javadoc)
	 * @see java.io.Reader#ready()
	 */
	@Override
	public boolean ready() throws IOException {
		return (current != null) && current.reader().ready();
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Entry entry : entries) {
			sb.append(entry.name()).append(',');
		}
		return sb.toString();
	}
}
