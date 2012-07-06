/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons PathTrie.java 2012-3-29 15:15:17 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.path;

import java.util.Map;

import cn.com.rebirth.commons.Strings;
import cn.com.rebirth.commons.collect.MapBuilder;

import com.google.common.collect.ImmutableMap;


/**
 * The Class PathTrie.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
public class PathTrie<T> {

	
	/**
	 * The Interface Decoder.
	 *
	 * @author l.xue.nong
	 */
	public static interface Decoder {

		
		/**
		 * Decode.
		 *
		 * @param value the value
		 * @return the string
		 */
		String decode(String value);
	}

	
	/** The Constant NO_DECODER. */
	public static final Decoder NO_DECODER = new Decoder() {
		@Override
		public String decode(String value) {
			return value;
		}
	};

	
	/** The decoder. */
	private final Decoder decoder;

	
	/** The root. */
	private final TrieNode<T> root;

	
	/** The separator. */
	private final char separator;

	
	/** The root value. */
	private T rootValue;

	
	/**
	 * Instantiates a new path trie.
	 */
	public PathTrie() {
		this('/', "*", NO_DECODER);
	}

	
	/**
	 * Instantiates a new path trie.
	 *
	 * @param decoder the decoder
	 */
	public PathTrie(Decoder decoder) {
		this('/', "*", decoder);
	}

	
	/**
	 * Instantiates a new path trie.
	 *
	 * @param separator the separator
	 * @param wildcard the wildcard
	 * @param decoder the decoder
	 */
	public PathTrie(char separator, String wildcard, Decoder decoder) {
		this.decoder = decoder;
		this.separator = separator;
		root = new TrieNode<T>(new String(new char[] { separator }), null, null, wildcard);
	}

	
	/**
	 * The Class TrieNode.
	 *
	 * @param <T> the generic type
	 * @author l.xue.nong
	 */
	@SuppressWarnings("hiding")
	public class TrieNode<T> {

		
		/** The key. */
		private transient String key;

		
		/** The value. */
		private transient T value;

		
		/** The is wildcard. */
		private boolean isWildcard;

		
		/** The wildcard. */
		private final String wildcard;

		
		/** The named wildcard. */
		private transient String namedWildcard;

		
		/** The children. */
		private ImmutableMap<String, TrieNode<T>> children;

		
		/**
		 * Instantiates a new trie node.
		 *
		 * @param key the key
		 * @param value the value
		 * @param parent the parent
		 * @param wildcard the wildcard
		 */
		@SuppressWarnings("rawtypes")
		public TrieNode(String key, T value, TrieNode parent, String wildcard) {
			this.key = key;
			this.wildcard = wildcard;
			this.isWildcard = (key.equals(wildcard));
			this.value = value;
			this.children = ImmutableMap.of();
			if (isNamedWildcard(key)) {
				namedWildcard = key.substring(key.indexOf('{') + 1, key.indexOf('}'));
			} else {
				namedWildcard = null;
			}
		}

		
		/**
		 * Update key with named wildcard.
		 *
		 * @param key the key
		 */
		public void updateKeyWithNamedWildcard(String key) {
			this.key = key;
			namedWildcard = key.substring(key.indexOf('{') + 1, key.indexOf('}'));
		}

		
		/**
		 * Checks if is wildcard.
		 *
		 * @return true, if is wildcard
		 */
		public boolean isWildcard() {
			return isWildcard;
		}

		
		/**
		 * Adds the child.
		 *
		 * @param child the child
		 */
		public synchronized void addChild(TrieNode<T> child) {
			children = MapBuilder.newMapBuilder(children).put(child.key, child).immutableMap();
		}

		
		/**
		 * Gets the child.
		 *
		 * @param key the key
		 * @return the child
		 */
		@SuppressWarnings("rawtypes")
		public TrieNode getChild(String key) {
			return children.get(key);
		}

		
		/**
		 * Insert.
		 *
		 * @param path the path
		 * @param index the index
		 * @param value the value
		 */
		public synchronized void insert(String[] path, int index, T value) {
			if (index >= path.length)
				return;

			String token = path[index];
			String key = token;
			if (isNamedWildcard(token)) {
				key = wildcard;
			}
			TrieNode<T> node = children.get(key);
			if (node == null) {
				if (index == (path.length - 1)) {
					node = new TrieNode<T>(token, value, this, wildcard);
				} else {
					node = new TrieNode<T>(token, null, this, wildcard);
				}
				children = MapBuilder.newMapBuilder(children).put(key, node).immutableMap();
			} else {
				if (isNamedWildcard(token)) {
					node.updateKeyWithNamedWildcard(token);
				}

				
				
				if (index == (path.length - 1)) {
					assert (node.value == null || node.value == value);
					if (node.value == null) {
						node.value = value;
					}
				}
			}

			node.insert(path, index + 1, value);
		}

		
		/**
		 * Checks if is named wildcard.
		 *
		 * @param key the key
		 * @return true, if is named wildcard
		 */
		private boolean isNamedWildcard(String key) {
			return key.indexOf('{') != -1 && key.indexOf('}') != -1;
		}

		
		/**
		 * Named wildcard.
		 *
		 * @return the string
		 */
		private String namedWildcard() {
			return namedWildcard;
		}

		
		/**
		 * Checks if is named wildcard.
		 *
		 * @return true, if is named wildcard
		 */
		private boolean isNamedWildcard() {
			return namedWildcard != null;
		}

		
		/**
		 * Retrieve.
		 *
		 * @param path the path
		 * @param index the index
		 * @param params the params
		 * @return the t
		 */
		public T retrieve(String[] path, int index, Map<String, String> params) {
			if (index >= path.length)
				return null;

			String token = path[index];
			TrieNode<T> node = children.get(token);
			boolean usedWildcard = false;
			if (node == null) {
				node = children.get(wildcard);
				if (node == null) {
					return null;
				} else {
					usedWildcard = true;
					if (params != null && node.isNamedWildcard()) {
						put(params, node.namedWildcard(), token);
					}
				}
			}

			if (index == (path.length - 1)) {
				return node.value;
			}

			T res = node.retrieve(path, index + 1, params);
			if (res == null && !usedWildcard) {
				node = children.get(wildcard);
				if (node != null) {
					if (params != null && node.isNamedWildcard()) {
						put(params, node.namedWildcard(), token);
					}
					res = node.retrieve(path, index + 1, params);
				}
			}

			return res;
		}

		
		/**
		 * Put.
		 *
		 * @param params the params
		 * @param key the key
		 * @param value the value
		 */
		private void put(Map<String, String> params, String key, String value) {
			params.put(key, decoder.decode(value));
		}
	}

	
	/**
	 * Insert.
	 *
	 * @param path the path
	 * @param value the value
	 */
	public void insert(String path, T value) {
		String[] strings = Strings.splitStringToArray(path, separator);
		if (strings.length == 0) {
			rootValue = value;
			return;
		}
		int index = 0;
		
		if (strings.length > 0 && strings[0].isEmpty()) {
			index = 1;
		}
		root.insert(strings, index, value);
	}

	
	/**
	 * Retrieve.
	 *
	 * @param path the path
	 * @return the t
	 */
	public T retrieve(String path) {
		return retrieve(path, null);
	}

	
	/**
	 * Retrieve.
	 *
	 * @param path the path
	 * @param params the params
	 * @return the t
	 */
	public T retrieve(String path, Map<String, String> params) {
		if (path.length() == 0) {
			return rootValue;
		}
		String[] strings = Strings.splitStringToArray(path, separator);
		if (strings.length == 0) {
			return rootValue;
		}
		int index = 0;
		
		if (strings.length > 0 && strings[0].isEmpty()) {
			index = 1;
		}
		return root.retrieve(strings, index, params);
	}
}
