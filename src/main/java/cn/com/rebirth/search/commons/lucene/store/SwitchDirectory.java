/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons SwitchDirectory.java 2012-3-29 15:15:12 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.lucene.store;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.store.IndexOutput;

import cn.com.rebirth.search.index.store.support.ForceSyncDirectory;

import com.google.common.collect.ImmutableSet;


/**
 * The Class SwitchDirectory.
 *
 * @author l.xue.nong
 */
public class SwitchDirectory extends Directory implements ForceSyncDirectory {

	
	/** The secondary dir. */
	private final Directory secondaryDir;

	
	/** The primary dir. */
	private final Directory primaryDir;

	
	/** The primary extensions. */
	private final ImmutableSet<String> primaryExtensions;

	
	/** The do close. */
	private boolean doClose;

	
	/**
	 * Instantiates a new switch directory.
	 *
	 * @param primaryExtensions the primary extensions
	 * @param primaryDir the primary dir
	 * @param secondaryDir the secondary dir
	 * @param doClose the do close
	 */
	public SwitchDirectory(Set<String> primaryExtensions, Directory primaryDir, Directory secondaryDir, boolean doClose) {
		this.primaryExtensions = ImmutableSet.copyOf(primaryExtensions);
		this.primaryDir = primaryDir;
		this.secondaryDir = secondaryDir;
		this.doClose = doClose;
		this.lockFactory = primaryDir.getLockFactory();
	}

	
	/**
	 * Primary extensions.
	 *
	 * @return the immutable set
	 */
	public ImmutableSet<String> primaryExtensions() {
		return primaryExtensions;
	}

	
	/**
	 * Primary dir.
	 *
	 * @return the directory
	 */
	public Directory primaryDir() {
		return primaryDir;
	}

	
	/**
	 * Secondary dir.
	 *
	 * @return the directory
	 */
	public Directory secondaryDir() {
		return secondaryDir;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.store.Directory#close()
	 */
	@Override
	public void close() throws IOException {
		if (doClose) {
			try {
				secondaryDir.close();
			} finally {
				primaryDir.close();
			}
			doClose = false;
		}
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.store.Directory#listAll()
	 */
	@Override
	public String[] listAll() throws IOException {
		Set<String> files = new HashSet<String>();
		for (String f : primaryDir.listAll()) {
			files.add(f);
		}
		for (String f : secondaryDir.listAll()) {
			files.add(f);
		}
		return files.toArray(new String[files.size()]);
	}

	
	/**
	 * Gets the extension.
	 *
	 * @param name the name
	 * @return the extension
	 */
	public static String getExtension(String name) {
		int i = name.lastIndexOf('.');
		if (i == -1) {
			return "";
		}
		return name.substring(i + 1, name.length());
	}

	
	/**
	 * Gets the directory.
	 *
	 * @param name the name
	 * @return the directory
	 */
	private Directory getDirectory(String name) {
		String ext = getExtension(name);
		if (primaryExtensions.contains(ext)) {
			return primaryDir;
		} else {
			return secondaryDir;
		}
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.store.Directory#fileExists(java.lang.String)
	 */
	@Override
	public boolean fileExists(String name) throws IOException {
		return getDirectory(name).fileExists(name);
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.store.Directory#fileModified(java.lang.String)
	 */
	@Override
	public long fileModified(String name) throws IOException {
		return getDirectory(name).fileModified(name);
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.store.Directory#touchFile(java.lang.String)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void touchFile(String name) throws IOException {
		getDirectory(name).touchFile(name);
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.store.Directory#deleteFile(java.lang.String)
	 */
	@Override
	public void deleteFile(String name) throws IOException {
		getDirectory(name).deleteFile(name);
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.store.Directory#fileLength(java.lang.String)
	 */
	@Override
	public long fileLength(String name) throws IOException {
		return getDirectory(name).fileLength(name);
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.store.Directory#createOutput(java.lang.String)
	 */
	@Override
	public IndexOutput createOutput(String name) throws IOException {
		return getDirectory(name).createOutput(name);
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.store.Directory#sync(java.util.Collection)
	 */
	@Override
	public void sync(Collection<String> names) throws IOException {
		List<String> primaryNames = new ArrayList<String>();
		List<String> secondaryNames = new ArrayList<String>();

		for (String name : names)
			if (primaryExtensions.contains(getExtension(name)))
				primaryNames.add(name);
			else
				secondaryNames.add(name);

		primaryDir.sync(primaryNames);
		secondaryDir.sync(secondaryNames);
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.store.Directory#sync(java.lang.String)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void sync(String name) throws IOException {
		getDirectory(name).sync(name);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.index.store.support.ForceSyncDirectory#forceSync(java.lang.String)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void forceSync(String name) throws IOException {
		Directory dir = getDirectory(name);
		if (dir instanceof ForceSyncDirectory) {
			((ForceSyncDirectory) dir).forceSync(name);
		} else {
			dir.sync(name);
		}
	}

	
	/* (non-Javadoc)
	 * @see org.apache.lucene.store.Directory#openInput(java.lang.String)
	 */
	@Override
	public IndexInput openInput(String name) throws IOException {
		return getDirectory(name).openInput(name);
	}
}
