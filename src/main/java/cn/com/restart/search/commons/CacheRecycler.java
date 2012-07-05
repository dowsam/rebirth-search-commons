/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons CacheRecycler.java 2012-3-29 15:15:14 l.xue.nong$$
 */


package cn.com.restart.search.commons;

import gnu.trove.map.hash.TByteIntHashMap;
import gnu.trove.map.hash.TDoubleIntHashMap;
import gnu.trove.map.hash.TFloatIntHashMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TLongIntHashMap;
import gnu.trove.map.hash.TLongLongHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import gnu.trove.map.hash.TShortIntHashMap;

import java.lang.ref.SoftReference;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import cn.com.restart.commons.compress.lzf.BufferRecycler;
import cn.com.restart.commons.trove.ExtTDoubleObjectHashMap;
import cn.com.restart.commons.trove.ExtTHashMap;
import cn.com.restart.commons.trove.ExtTLongObjectHashMap;


/**
 * The Class CacheRecycler.
 *
 * @author l.xue.nong
 */
@SuppressWarnings("all")
public class CacheRecycler {

	
	/**
	 * Clear.
	 */
	public static void clear() {
		BufferRecycler.clean();
		hashMap.clear();
		doubleObjectHashMap.clear();
		longObjectHashMap.clear();
		longLongHashMap.clear();
		intIntHashMap.clear();
		floatIntHashMap.clear();
		doubleIntHashMap.clear();
		shortIntHashMap.clear();
		longIntHashMap.clear();
		objectIntHashMap.clear();
		objectArray.clear();
		intArray.clear();
	}

	
	/**
	 * The Class SoftWrapper.
	 *
	 * @param <T> the generic type
	 * @author l.xue.nong
	 */
	static class SoftWrapper<T> {

		
		/** The ref. */
		private SoftReference<T> ref;

		
		/**
		 * Instantiates a new soft wrapper.
		 */
		public SoftWrapper() {
		}

		
		/**
		 * Sets the.
		 *
		 * @param ref the ref
		 */
		public void set(T ref) {
			this.ref = new SoftReference<T>(ref);
		}

		
		/**
		 * Gets the.
		 *
		 * @return the t
		 */
		public T get() {
			return ref == null ? null : ref.get();
		}

		
		/**
		 * Clear.
		 */
		public void clear() {
			ref = null;
		}
	}

	

	
	/** The hash map. */
	private static SoftWrapper<Queue<ExtTHashMap>> hashMap = new SoftWrapper<Queue<ExtTHashMap>>();

	
	/**
	 * Pop hash map.
	 *
	 * @param <K> the key type
	 * @param <V> the value type
	 * @return the ext t hash map
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> ExtTHashMap<K, V> popHashMap() {
		Queue<ExtTHashMap> ref = hashMap.get();
		if (ref == null) {
			return new ExtTHashMap<K, V>();
		}
		ExtTHashMap map = ref.poll();
		if (map == null) {
			return new ExtTHashMap<K, V>();
		}
		return map;
	}

	
	/**
	 * Push hash map.
	 *
	 * @param map the map
	 */
	public static void pushHashMap(ExtTHashMap<?, ?> map) {
		Queue<ExtTHashMap> ref = hashMap.get();
		if (ref == null) {
			ref = new LinkedBlockingQueue<ExtTHashMap>();
			hashMap.set(ref);
		}
		map.clear();
		ref.add(map);
	}

	

	
	/** The double object hash map. */
	private static SoftWrapper<Queue<ExtTDoubleObjectHashMap>> doubleObjectHashMap = new SoftWrapper<Queue<ExtTDoubleObjectHashMap>>();

	
	/**
	 * Pop double object map.
	 *
	 * @param <T> the generic type
	 * @return the ext t double object hash map
	 */
	public static <T> ExtTDoubleObjectHashMap<T> popDoubleObjectMap() {
		Queue<ExtTDoubleObjectHashMap> ref = doubleObjectHashMap.get();
		if (ref == null) {
			return new ExtTDoubleObjectHashMap();
		}
		ExtTDoubleObjectHashMap map = ref.poll();
		if (map == null) {
			return new ExtTDoubleObjectHashMap();
		}
		return map;
	}

	
	/**
	 * Push double object map.
	 *
	 * @param map the map
	 */
	public static void pushDoubleObjectMap(ExtTDoubleObjectHashMap map) {
		Queue<ExtTDoubleObjectHashMap> ref = doubleObjectHashMap.get();
		if (ref == null) {
			ref = new LinkedBlockingQueue<ExtTDoubleObjectHashMap>();
			doubleObjectHashMap.set(ref);
		}
		map.clear();
		ref.add(map);
	}

	

	
	/** The long object hash map. */
	private static SoftWrapper<Queue<ExtTLongObjectHashMap>> longObjectHashMap = new SoftWrapper<Queue<ExtTLongObjectHashMap>>();

	
	/**
	 * Pop long object map.
	 *
	 * @param <T> the generic type
	 * @return the ext t long object hash map
	 */
	public static <T> ExtTLongObjectHashMap<T> popLongObjectMap() {
		Queue<ExtTLongObjectHashMap> ref = longObjectHashMap.get();
		if (ref == null) {
			return new ExtTLongObjectHashMap();
		}
		ExtTLongObjectHashMap map = ref.poll();
		if (map == null) {
			return new ExtTLongObjectHashMap();
		}
		return map;
	}

	
	/**
	 * Push long object map.
	 *
	 * @param map the map
	 */
	public static void pushLongObjectMap(ExtTLongObjectHashMap map) {
		Queue<ExtTLongObjectHashMap> ref = longObjectHashMap.get();
		if (ref == null) {
			ref = new LinkedBlockingQueue<ExtTLongObjectHashMap>();
			longObjectHashMap.set(ref);
		}
		map.clear();
		ref.add(map);
	}

	

	
	/** The long long hash map. */
	private static SoftWrapper<Queue<TLongLongHashMap>> longLongHashMap = new SoftWrapper<Queue<TLongLongHashMap>>();

	
	/**
	 * Pop long long map.
	 *
	 * @return the t long long hash map
	 */
	public static TLongLongHashMap popLongLongMap() {
		Queue<TLongLongHashMap> ref = longLongHashMap.get();
		if (ref == null) {
			return new TLongLongHashMap();
		}
		TLongLongHashMap map = ref.poll();
		if (map == null) {
			return new TLongLongHashMap();
		}
		return map;
	}

	
	/**
	 * Push long long map.
	 *
	 * @param map the map
	 */
	public static void pushLongLongMap(TLongLongHashMap map) {
		Queue<TLongLongHashMap> ref = longLongHashMap.get();
		if (ref == null) {
			ref = new LinkedBlockingQueue<TLongLongHashMap>();
			longLongHashMap.set(ref);
		}
		map.clear();
		ref.add(map);
	}

	

	
	/** The int int hash map. */
	private static SoftWrapper<Queue<TIntIntHashMap>> intIntHashMap = new SoftWrapper<Queue<TIntIntHashMap>>();

	
	/**
	 * Pop int int map.
	 *
	 * @return the t int int hash map
	 */
	public static TIntIntHashMap popIntIntMap() {
		Queue<TIntIntHashMap> ref = intIntHashMap.get();
		if (ref == null) {
			return new TIntIntHashMap();
		}
		TIntIntHashMap map = ref.poll();
		if (map == null) {
			return new TIntIntHashMap();
		}
		return map;
	}

	
	/**
	 * Push int int map.
	 *
	 * @param map the map
	 */
	public static void pushIntIntMap(TIntIntHashMap map) {
		Queue<TIntIntHashMap> ref = intIntHashMap.get();
		if (ref == null) {
			ref = new LinkedBlockingQueue<TIntIntHashMap>();
			intIntHashMap.set(ref);
		}
		map.clear();
		ref.add(map);
	}

	

	
	/** The float int hash map. */
	private static SoftWrapper<Queue<TFloatIntHashMap>> floatIntHashMap = new SoftWrapper<Queue<TFloatIntHashMap>>();

	
	/**
	 * Pop float int map.
	 *
	 * @return the t float int hash map
	 */
	public static TFloatIntHashMap popFloatIntMap() {
		Queue<TFloatIntHashMap> ref = floatIntHashMap.get();
		if (ref == null) {
			return new TFloatIntHashMap();
		}
		TFloatIntHashMap map = ref.poll();
		if (map == null) {
			return new TFloatIntHashMap();
		}
		return map;
	}

	
	/**
	 * Push float int map.
	 *
	 * @param map the map
	 */
	public static void pushFloatIntMap(TFloatIntHashMap map) {
		Queue<TFloatIntHashMap> ref = floatIntHashMap.get();
		if (ref == null) {
			ref = new LinkedBlockingQueue<TFloatIntHashMap>();
			floatIntHashMap.set(ref);
		}
		map.clear();
		ref.add(map);
	}

	

	
	/** The double int hash map. */
	private static SoftWrapper<Queue<TDoubleIntHashMap>> doubleIntHashMap = new SoftWrapper<Queue<TDoubleIntHashMap>>();

	
	/**
	 * Pop double int map.
	 *
	 * @return the t double int hash map
	 */
	public static TDoubleIntHashMap popDoubleIntMap() {
		Queue<TDoubleIntHashMap> ref = doubleIntHashMap.get();
		if (ref == null) {
			return new TDoubleIntHashMap();
		}
		TDoubleIntHashMap map = ref.poll();
		if (map == null) {
			return new TDoubleIntHashMap();
		}
		return map;
	}

	
	/**
	 * Push double int map.
	 *
	 * @param map the map
	 */
	public static void pushDoubleIntMap(TDoubleIntHashMap map) {
		Queue<TDoubleIntHashMap> ref = doubleIntHashMap.get();
		if (ref == null) {
			ref = new LinkedBlockingQueue<TDoubleIntHashMap>();
			doubleIntHashMap.set(ref);
		}
		map.clear();
		ref.add(map);
	}

	

	
	/** The byte int hash map. */
	private static SoftWrapper<Queue<TByteIntHashMap>> byteIntHashMap = new SoftWrapper<Queue<TByteIntHashMap>>();

	
	/**
	 * Pop byte int map.
	 *
	 * @return the t byte int hash map
	 */
	public static TByteIntHashMap popByteIntMap() {
		Queue<TByteIntHashMap> ref = byteIntHashMap.get();
		if (ref == null) {
			return new TByteIntHashMap();
		}
		TByteIntHashMap map = ref.poll();
		if (map == null) {
			return new TByteIntHashMap();
		}
		return map;
	}

	
	/**
	 * Push byte int map.
	 *
	 * @param map the map
	 */
	public static void pushByteIntMap(TByteIntHashMap map) {
		Queue<TByteIntHashMap> ref = byteIntHashMap.get();
		if (ref == null) {
			ref = new LinkedBlockingQueue<TByteIntHashMap>();
			byteIntHashMap.set(ref);
		}
		map.clear();
		ref.add(map);
	}

	

	
	/** The short int hash map. */
	private static SoftWrapper<Queue<TShortIntHashMap>> shortIntHashMap = new SoftWrapper<Queue<TShortIntHashMap>>();

	
	/**
	 * Pop short int map.
	 *
	 * @return the t short int hash map
	 */
	public static TShortIntHashMap popShortIntMap() {
		Queue<TShortIntHashMap> ref = shortIntHashMap.get();
		if (ref == null) {
			return new TShortIntHashMap();
		}
		TShortIntHashMap map = ref.poll();
		if (map == null) {
			return new TShortIntHashMap();
		}
		return map;
	}

	
	/**
	 * Push short int map.
	 *
	 * @param map the map
	 */
	public static void pushShortIntMap(TShortIntHashMap map) {
		Queue<TShortIntHashMap> ref = shortIntHashMap.get();
		if (ref == null) {
			ref = new LinkedBlockingQueue<TShortIntHashMap>();
			shortIntHashMap.set(ref);
		}
		map.clear();
		ref.add(map);
	}

	

	
	/** The long int hash map. */
	private static SoftWrapper<Queue<TLongIntHashMap>> longIntHashMap = new SoftWrapper<Queue<TLongIntHashMap>>();

	
	/**
	 * Pop long int map.
	 *
	 * @return the t long int hash map
	 */
	public static TLongIntHashMap popLongIntMap() {
		Queue<TLongIntHashMap> ref = longIntHashMap.get();
		if (ref == null) {
			return new TLongIntHashMap();
		}
		TLongIntHashMap map = ref.poll();
		if (map == null) {
			return new TLongIntHashMap();
		}
		return map;
	}

	
	/**
	 * Push long int map.
	 *
	 * @param map the map
	 */
	public static void pushLongIntMap(TLongIntHashMap map) {
		Queue<TLongIntHashMap> ref = longIntHashMap.get();
		if (ref == null) {
			ref = new LinkedBlockingQueue<TLongIntHashMap>();
			longIntHashMap.set(ref);
		}
		map.clear();
		ref.add(map);
	}

	

	
	/** The object int hash map. */
	private static SoftWrapper<Queue<TObjectIntHashMap>> objectIntHashMap = new SoftWrapper<Queue<TObjectIntHashMap>>();

	
	/**
	 * Pop object int map.
	 *
	 * @param <T> the generic type
	 * @return the t object int hash map
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> TObjectIntHashMap<T> popObjectIntMap() {
		Queue<TObjectIntHashMap> ref = objectIntHashMap.get();
		if (ref == null) {
			return new TObjectIntHashMap();
		}
		TObjectIntHashMap map = ref.poll();
		if (map == null) {
			return new TObjectIntHashMap();
		}
		return map;
	}

	
	/**
	 * Push object int map.
	 *
	 * @param <T> the generic type
	 * @param map the map
	 */
	public static <T> void pushObjectIntMap(TObjectIntHashMap<T> map) {
		Queue<TObjectIntHashMap> ref = objectIntHashMap.get();
		if (ref == null) {
			ref = new LinkedBlockingQueue<TObjectIntHashMap>();
			objectIntHashMap.set(ref);
		}
		map.clear();
		ref.add(map);
	}

	

	
	/** The object array. */
	private static SoftWrapper<Queue<Object[]>> objectArray = new SoftWrapper<Queue<Object[]>>();

	
	/**
	 * Pop object array.
	 *
	 * @param size the size
	 * @return the object[]
	 */
	public static Object[] popObjectArray(int size) {
		size = size < 100 ? 100 : size;
		Queue<Object[]> ref = objectArray.get();
		if (ref == null) {
			return new Object[size];
		}
		Object[] objects = ref.poll();
		if (objects == null) {
			return new Object[size];
		}
		if (objects.length < size) {
			return new Object[size];
		}
		return objects;
	}

	
	/**
	 * Push object array.
	 *
	 * @param objects the objects
	 */
	public static void pushObjectArray(Object[] objects) {
		Queue<Object[]> ref = objectArray.get();
		if (ref == null) {
			ref = new LinkedBlockingQueue<Object[]>();
			objectArray.set(ref);
		}
		Arrays.fill(objects, null);
		ref.add(objects);
	}

	
	/** The int array. */
	private static SoftWrapper<Queue<int[]>> intArray = new SoftWrapper<Queue<int[]>>();

	
	/**
	 * Pop int array.
	 *
	 * @param size the size
	 * @return the int[]
	 */
	public static int[] popIntArray(int size) {
		return popIntArray(size, 0);
	}

	
	/**
	 * Pop int array.
	 *
	 * @param size the size
	 * @param sentinal the sentinal
	 * @return the int[]
	 */
	public static int[] popIntArray(int size, int sentinal) {
		size = size < 100 ? 100 : size;
		Queue<int[]> ref = intArray.get();
		if (ref == null) {
			int[] ints = new int[size];
			if (sentinal != 0) {
				Arrays.fill(ints, sentinal);
			}
			return ints;
		}
		int[] ints = ref.poll();
		if (ints == null) {
			ints = new int[size];
			if (sentinal != 0) {
				Arrays.fill(ints, sentinal);
			}
			return ints;
		}
		if (ints.length < size) {
			ints = new int[size];
			if (sentinal != 0) {
				Arrays.fill(ints, sentinal);
			}
			return ints;
		}
		return ints;
	}

	
	/**
	 * Push int array.
	 *
	 * @param ints the ints
	 */
	public static void pushIntArray(int[] ints) {
		pushIntArray(ints, 0);
	}

	
	/**
	 * Push int array.
	 *
	 * @param ints the ints
	 * @param sentinal the sentinal
	 */
	public static void pushIntArray(int[] ints, int sentinal) {
		Queue<int[]> ref = intArray.get();
		if (ref == null) {
			ref = new LinkedBlockingQueue<int[]>();
			intArray.set(ref);
		}
		Arrays.fill(ints, sentinal);
		ref.add(ints);
	}
}