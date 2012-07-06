/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons BloomFilterFactory.java 2012-3-29 15:15:17 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.bloom;


import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.rebirth.commons.unit.ByteSizeValue;
import cn.com.rebirth.commons.unit.SizeValue;
import cn.com.rebirth.commons.unit.TimeValue;
import cn.com.rebirth.search.commons.UUID;


/**
 * A factory for creating BloomFilter objects.
 */
public class BloomFilterFactory {

    
    /** The logger. */
    private static Logger logger = LoggerFactory.getLogger(BloomFilterFactory.class.getName());

    
    /** The Constant EXCESS. */
    private static final int EXCESS = 20;

    
    /**
     * Gets the filter.
     *
     * @param numElements the num elements
     * @param targetBucketsPerElem the target buckets per elem
     * @return the filter
     */
    public static BloomFilter getFilter(long numElements, int targetBucketsPerElem) {
        int maxBucketsPerElement = Math.max(1, BloomCalculations.maxBucketsPerElement(numElements));
        int bucketsPerElement = Math.min(targetBucketsPerElem, maxBucketsPerElement);
        if (bucketsPerElement < targetBucketsPerElem) {
            logger.warn(String.format("Cannot provide an optimal BloomFilter for %d elements (%d/%d buckets per element).",
                    numElements, bucketsPerElement, targetBucketsPerElem));
        }
        BloomCalculations.BloomSpecification spec = BloomCalculations.computeBloomSpec(bucketsPerElement);
        return new ObsBloomFilter(spec.K, bucketsFor(numElements, spec.bucketsPerElement));
    }

    
    /**
     * Gets the filter.
     *
     * @param numElements the num elements
     * @param maxFalsePosProbability the max false pos probability
     * @return the filter
     */
    public static BloomFilter getFilter(long numElements, double maxFalsePosProbability) {
        assert maxFalsePosProbability <= 1.0 : "Invalid probability";
        int bucketsPerElement = BloomCalculations.maxBucketsPerElement(numElements);
        BloomCalculations.BloomSpecification spec = BloomCalculations.computeBloomSpec(bucketsPerElement, maxFalsePosProbability);
        return new ObsBloomFilter(spec.K, bucketsFor(numElements, spec.bucketsPerElement));
    }

    
    /**
     * Buckets for.
     *
     * @param numElements the num elements
     * @param bucketsPer the buckets per
     * @return the long
     */
    private static long bucketsFor(long numElements, int bucketsPer) {
        return numElements * bucketsPer + EXCESS;
    }

    
    /**
     * The main method.
     *
     * @param args the arguments
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        long elements = SizeValue.parseSizeValue("100m").singles();
        BloomFilter filter = BloomFilterFactory.getFilter(elements, 15);
        System.out.println("Filter size: " + new ByteSizeValue(filter.sizeInBytes()));
        for (long i = 0; i < elements; i++) {
            byte[] utf8s = UUID.randomBase64UUID().getBytes("UTF8");
            filter.add(utf8s, 0, utf8s.length);
        }
        long falsePositives = 0;
        for (long i = 0; i < elements; i++) {
            byte[] utf8s = UUID.randomBase64UUID().getBytes("UTF8");
            if (filter.isPresent(utf8s, 0, utf8s.length)) {
                falsePositives++;
            }
        }
        System.out.println("false positives: " + falsePositives);

        byte[] utf8s = UUID.randomBase64UUID().getBytes("UTF8");
        long time = System.currentTimeMillis();
        for (long i = 0; i < elements; i++) {
            if (filter.isPresent(utf8s, 0, utf8s.length)) {
            }
        }
        long timeSize = System.currentTimeMillis() - time;
        System.out.println("Indexed in " + new TimeValue(timeSize) + ", TPS: " + (elements / timeSize) + " per millisecond");
    }
}