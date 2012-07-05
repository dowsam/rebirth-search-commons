/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons EncounterImpl.java 2012-3-29 15:15:17 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;

import cn.com.restart.search.commons.inject.internal.Errors;
import cn.com.restart.search.commons.inject.spi.InjectionListener;
import cn.com.restart.search.commons.inject.spi.Message;
import cn.com.restart.search.commons.inject.spi.TypeEncounter;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;


/**
 * The Class EncounterImpl.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
final class EncounterImpl<T> implements TypeEncounter<T> {

	
	/** The errors. */
	private final Errors errors;

	
	/** The lookups. */
	private final Lookups lookups;

	
	/** The members injectors. */
	private List<MembersInjector<? super T>> membersInjectors; 

	
	/** The injection listeners. */
	private List<InjectionListener<? super T>> injectionListeners; 

	
	/** The valid. */
	private boolean valid = true;

	
	/**
	 * Instantiates a new encounter impl.
	 *
	 * @param errors the errors
	 * @param lookups the lookups
	 */
	public EncounterImpl(Errors errors, Lookups lookups) {
		this.errors = errors;
		this.lookups = lookups;
	}

	
	/**
	 * Invalidate.
	 */
	public void invalidate() {
		valid = false;
	}

	
	/**
	 * Gets the members injectors.
	 *
	 * @return the members injectors
	 */
	public ImmutableList<MembersInjector<? super T>> getMembersInjectors() {
		return membersInjectors == null ? ImmutableList.<MembersInjector<? super T>> of() : ImmutableList
				.copyOf(membersInjectors);
	}

	
	/**
	 * Gets the injection listeners.
	 *
	 * @return the injection listeners
	 */
	public ImmutableList<InjectionListener<? super T>> getInjectionListeners() {
		return injectionListeners == null ? ImmutableList.<InjectionListener<? super T>> of() : ImmutableList
				.copyOf(injectionListeners);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.TypeEncounter#register(cn.com.summall.search.commons.inject.MembersInjector)
	 */
	public void register(MembersInjector<? super T> membersInjector) {
		checkState(valid, "Encounters may not be used after hear() returns.");

		if (membersInjectors == null) {
			membersInjectors = Lists.newArrayList();
		}

		membersInjectors.add(membersInjector);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.TypeEncounter#register(cn.com.summall.search.commons.inject.spi.InjectionListener)
	 */
	public void register(InjectionListener<? super T> injectionListener) {
		checkState(valid, "Encounters may not be used after hear() returns.");

		if (injectionListeners == null) {
			injectionListeners = Lists.newArrayList();
		}

		injectionListeners.add(injectionListener);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.TypeEncounter#addError(java.lang.String, java.lang.Object[])
	 */
	public void addError(String message, Object... arguments) {
		checkState(valid, "Encounters may not be used after hear() returns.");
		errors.addMessage(message, arguments);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.TypeEncounter#addError(java.lang.Throwable)
	 */
	public void addError(Throwable t) {
		checkState(valid, "Encounters may not be used after hear() returns.");
		errors.errorInUserCode(t, "An exception was caught and reported. Message: %s", t.getMessage());
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.TypeEncounter#addError(cn.com.summall.search.commons.inject.spi.Message)
	 */
	public void addError(Message message) {
		checkState(valid, "Encounters may not be used after hear() returns.");
		errors.addMessage(message);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.TypeEncounter#getProvider(cn.com.summall.search.commons.inject.Key)
	 */
	@SuppressWarnings("hiding")
	public <T> Provider<T> getProvider(Key<T> key) {
		checkState(valid, "Encounters may not be used after hear() returns.");
		return lookups.getProvider(key);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.TypeEncounter#getProvider(java.lang.Class)
	 */
	@SuppressWarnings("hiding")
	public <T> Provider<T> getProvider(Class<T> type) {
		return getProvider(Key.get(type));
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.TypeEncounter#getMembersInjector(cn.com.summall.search.commons.inject.TypeLiteral)
	 */
	@SuppressWarnings("hiding")
	public <T> MembersInjector<T> getMembersInjector(TypeLiteral<T> typeLiteral) {
		checkState(valid, "Encounters may not be used after hear() returns.");
		return lookups.getMembersInjector(typeLiteral);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.spi.TypeEncounter#getMembersInjector(java.lang.Class)
	 */
	@SuppressWarnings("hiding")
	public <T> MembersInjector<T> getMembersInjector(Class<T> type) {
		return getMembersInjector(TypeLiteral.get(type));
	}
}