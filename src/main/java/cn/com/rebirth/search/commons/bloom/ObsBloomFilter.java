/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ObsBloomFilter.java 2012-3-29 15:15:15 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.bloom;

import java.nio.ByteBuffer;

import org.apache.lucene.util.OpenBitSet;

import cn.com.rebirth.search.commons.RamUsage;


/**
 * The Class ObsBloomFilter.
 *
 * @author l.xue.nong
 */
public class ObsBloomFilter implements BloomFilter {

	
	/** The hash count. */
	private final int hashCount;

	
	/** The bitset. */
	private final OpenBitSet bitset;
	
	
	/** The size. */
	private final long size;

	
	/**
	 * Instantiates a new obs bloom filter.
	 *
	 * @param hashCount the hash count
	 * @param size the size
	 */
	ObsBloomFilter(int hashCount, long size) {
		this.hashCount = hashCount;
		this.bitset = new OpenBitSet(size);
		this.size = size;
	}

	
	/**
	 * Empty buckets.
	 *
	 * @return the long
	 */
	long emptyBuckets() {
		long n = 0;
		for (long i = 0; i < buckets(); i++) {
			if (!bitset.get(i)) {
				n++;
			}
		}
		return n;
	}

	
	/**
	 * Buckets.
	 *
	 * @return the long
	 */
	private long buckets() {
		return size;
	}

	
	/**
	 * Gets the hash buckets.
	 *
	 * @param key the key
	 * @return the hash buckets
	 */
	private long[] getHashBuckets(ByteBuffer key) {
		return getHashBuckets(key, hashCount, buckets());
	}

	
	/**
	 * Gets the hash buckets.
	 *
	 * @param key the key
	 * @param offset the offset
	 * @param length the length
	 * @return the hash buckets
	 */
	private long[] getHashBuckets(byte[] key, int offset, int length) {
		return getHashBuckets(key, offset, length, hashCount, buckets());
	}

	
	
	
	
	
	
	/**
	 * Gets the hash buckets.
	 *
	 * @param b the b
	 * @param hashCount the hash count
	 * @param max the max
	 * @return the hash buckets
	 */
	static long[] getHashBuckets(ByteBuffer b, int hashCount, long max) {
		long[] result = new long[hashCount];
		long hash1 = MurmurHash.hash64(b, b.position(), b.remaining(), 0L);
		long hash2 = MurmurHash.hash64(b, b.position(), b.remaining(), hash1);
		for (int i = 0; i < hashCount; ++i) {
			result[i] = Math.abs((hash1 + (long) i * hash2) % max);
		}
		return result;
	}

	
	
	
	
	
	
	/**
	 * Gets the hash buckets.
	 *
	 * @param b the b
	 * @param offset the offset
	 * @param length the length
	 * @param hashCount the hash count
	 * @param max the max
	 * @return the hash buckets
	 */
	static long[] getHashBuckets(byte[] b, int offset, int length, int hashCount, long max) {
		long[] result = new long[hashCount];
		long hash1 = MurmurHash.hash64(b, offset, length, 0L);
		long hash2 = MurmurHash.hash64(b, offset, length, hash1);
		for (int i = 0; i < hashCount; ++i) {
			result[i] = Math.abs((hash1 + (long) i * hash2) % max);
		}
		return result;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.bloom.BloomFilter#add(byte[], int, int)
	 */
	@Override
	public void add(byte[] key, int offset, int length) {
		for (long bucketIndex : getHashBuckets(key, offset, length)) {
			bitset.fastSet(bucketIndex);
		}
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.bloom.BloomFilter#add(java.nio.ByteBuffer)
	 */
	public void add(ByteBuffer key) {
		for (long bucketIndex : getHashBuckets(key)) {
			bitset.fastSet(bucketIndex);
		}
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.bloom.BloomFilter#isPresent(byte[], int, int)
	 */
	@Override
	public boolean isPresent(byte[] key, int offset, int length) {
		for (long bucketIndex : getHashBuckets(key, offset, length)) {
			if (!bitset.fastGet(bucketIndex)) {
				return false;
			}
		}
		return true;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.bloom.BloomFilter#isPresent(java.nio.ByteBuffer)
	 */
	public boolean isPresent(ByteBuffer key) {
		for (long bucketIndex : getHashBuckets(key)) {
			if (!bitset.fastGet(bucketIndex)) {
				return false;
			}
		}
		return true;
	}

	
	/**
	 * Clear.
	 */
	public void clear() {
		bitset.clear(0, bitset.size());
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.bloom.BloomFilter#sizeInBytes()
	 */
	@Override
	public long sizeInBytes() {
		return bitset.getBits().length * RamUsage.NUM_BYTES_LONG + RamUsage.NUM_BYTES_ARRAY_HEADER
				+ RamUsage.NUM_BYTES_INT ;
	}
}
