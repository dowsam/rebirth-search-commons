/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons IndexCommitDelegate.java 2012-3-29 15:15:12 l.xue.nong$$
 */


package cn.com.restart.search.commons.lucene;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.apache.lucene.index.IndexCommit;
import org.apache.lucene.store.Directory;


/**
 * The Class IndexCommitDelegate.
 *
 * @author l.xue.nong
 */
public abstract class IndexCommitDelegate extends IndexCommit {

	
	/** The delegate. */
	protected final IndexCommit delegate;

	
	/**
	 * Instantiates a new index commit delegate.
	 *
	 * @param delegate the delegate
	 */
	public IndexCommitDelegate(IndexCommit delegate) {
		this.delegate = delegate;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.index.IndexCommit#getSegmentsFileName()
	 */
	@Override
	public String getSegmentsFileName() {
		return delegate.getSegmentsFileName();
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.index.IndexCommit#getFileNames()
	 */
	@Override
	public Collection<String> getFileNames() throws IOException {
		return delegate.getFileNames();
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.index.IndexCommit#getDirectory()
	 */
	@Override
	public Directory getDirectory() {
		return delegate.getDirectory();
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.index.IndexCommit#delete()
	 */
	@Override
	public void delete() {
		delegate.delete();
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.index.IndexCommit#isDeleted()
	 */
	@Override
	public boolean isDeleted() {
		return delegate.isDeleted();
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.index.IndexCommit#getSegmentCount()
	 */
	@Override
	public int getSegmentCount() {
		return delegate.getSegmentCount();
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.index.IndexCommit#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		return delegate.equals(other);
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.index.IndexCommit#hashCode()
	 */
	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.index.IndexCommit#getVersion()
	 */
	@Override
	public long getVersion() {
		return delegate.getVersion();
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.index.IndexCommit#getGeneration()
	 */
	@Override
	public long getGeneration() {
		return delegate.getGeneration();
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.index.IndexCommit#getTimestamp()
	 */
	@Override
	public long getTimestamp() throws IOException {
		return delegate.getTimestamp();
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.index.IndexCommit#getUserData()
	 */
	@Override
	public Map<String, String> getUserData() throws IOException {
		return delegate.getUserData();
	}
}
