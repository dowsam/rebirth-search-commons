/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ReferenceManager.java 2012-3-29 15:15:08 l.xue.nong$$
 */

package cn.com.rebirth.search.commons.lucene.manager;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.Semaphore;

import org.apache.lucene.store.AlreadyClosedException;


/**
 * The Class ReferenceManager.
 *
 * @param <G> the generic type
 * @author l.xue.nong
 */
public abstract class ReferenceManager<G> implements Closeable {

	
	/** The Constant REFERENCE_MANAGER_IS_CLOSED_MSG. */
	private static final String REFERENCE_MANAGER_IS_CLOSED_MSG = "this ReferenceManager is closed";

	
	/** The current. */
	protected volatile G current;

	
	/** The reopen lock. */
	private final Semaphore reopenLock = new Semaphore(1);

	
	/**
	 * Ensure open.
	 */
	private void ensureOpen() {
		if (current == null) {
			throw new AlreadyClosedException(REFERENCE_MANAGER_IS_CLOSED_MSG);
		}
	}

	
	/**
	 * Swap reference.
	 *
	 * @param newReference the new reference
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private synchronized void swapReference(G newReference) throws IOException {
		ensureOpen();
		final G oldReference = current;
		current = newReference;
		release(oldReference);
	}

	
	/**
	 * Dec ref.
	 *
	 * @param reference the reference
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected abstract void decRef(G reference) throws IOException;

	
	/**
	 * Refresh if needed.
	 *
	 * @param referenceToRefresh the reference to refresh
	 * @return the g
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected abstract G refreshIfNeeded(G referenceToRefresh) throws IOException;

	
	/**
	 * Try inc ref.
	 *
	 * @param reference the reference
	 * @return true, if successful
	 */
	protected abstract boolean tryIncRef(G reference);

	
	/**
	 * Acquire.
	 *
	 * @return the g
	 */
	public final G acquire() {
		G ref;
		do {
			if ((ref = current) == null) {
				throw new AlreadyClosedException(REFERENCE_MANAGER_IS_CLOSED_MSG);
			}
		} while (!tryIncRef(ref));
		return ref;
	}

	
	/* (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	public final synchronized void close() throws IOException {
		if (current != null) {
			
			
			
			swapReference(null);
			afterClose();
		}
	}

	
	/**
	 * After close.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void afterClose() throws IOException {
	}

	
	/**
	 * Maybe refresh.
	 *
	 * @return true, if successful
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public final boolean maybeRefresh() throws IOException {
		ensureOpen();

		
		final boolean doTryRefresh = reopenLock.tryAcquire();
		if (doTryRefresh) {
			try {
				final G reference = acquire();
				try {
					G newReference = refreshIfNeeded(reference);
					if (newReference != null) {
						assert newReference != reference : "refreshIfNeeded should return null if refresh wasn't needed";
						boolean success = false;
						try {
							swapReference(newReference);
							success = true;
						} finally {
							if (!success) {
								release(newReference);
							}
						}
					}
				} finally {
					release(reference);
				}
				afterRefresh();
			} finally {
				reopenLock.release();
			}
		}

		return doTryRefresh;
	}

	
	/**
	 * After refresh.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void afterRefresh() throws IOException {
	}

	
	/**
	 * Release.
	 *
	 * @param reference the reference
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public final void release(G reference) throws IOException {
		assert reference != null;
		decRef(reference);
	}
}
