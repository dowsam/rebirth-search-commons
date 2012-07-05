/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons BlobPath.java 2012-3-29 15:15:09 l.xue.nong$$
 */


package cn.com.restart.search.commons.blobstore;

import com.google.common.collect.ImmutableList;

import java.util.Iterator;


/**
 * The Class BlobPath.
 *
 * @author l.xue.nong
 */
public class BlobPath implements Iterable<String> {

    /** The paths. */
    private final ImmutableList<String> paths;

    /**
     * Instantiates a new blob path.
     */
    public BlobPath() {
        this.paths = ImmutableList.of();
    }

    /**
     * Clean path.
     *
     * @return the blob path
     */
    public static BlobPath cleanPath() {
        return new BlobPath();
    }

    /**
     * Instantiates a new blob path.
     *
     * @param paths the paths
     */
    private BlobPath(ImmutableList<String> paths) {
        this.paths = paths;
    }

    /* (non-Javadoc)
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<String> iterator() {
        return paths.iterator();
    }

    /**
     * To array.
     *
     * @return the string[]
     */
    public String[] toArray() {
        return paths.toArray(new String[paths.size()]);
    }

    /**
     * Adds the.
     *
     * @param path the path
     * @return the blob path
     */
    public BlobPath add(String path) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        return new BlobPath(builder.addAll(paths).add(path).build());
    }

    /**
     * Builds the as string.
     *
     * @param separator the separator
     * @return the string
     */
    public String buildAsString(String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paths.size(); i++) {
            sb.append(paths.get(i));
            if (i < (paths.size() - 1)) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String path : paths) {
            sb.append('[').append(path).append(']');
        }
        return sb.toString();
    }
}
