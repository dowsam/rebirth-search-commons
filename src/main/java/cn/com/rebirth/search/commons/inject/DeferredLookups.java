/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons DeferredLookups.java 2012-3-29 15:15:09 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject;

import java.util.List;

import cn.com.rebirth.search.commons.inject.internal.Errors;
import cn.com.rebirth.search.commons.inject.spi.Element;
import cn.com.rebirth.search.commons.inject.spi.MembersInjectorLookup;
import cn.com.rebirth.search.commons.inject.spi.ProviderLookup;

import com.google.common.collect.Lists;


/**
 * The Class DeferredLookups.
 *
 * @author l.xue.nong
 */
class DeferredLookups implements Lookups {

	
	/** The injector. */
	private final InjectorImpl injector;

	
	/** The lookups. */
	private final List<Element> lookups = Lists.newArrayList();

	
	/**
	 * Instantiates a new deferred lookups.
	 *
	 * @param injector the injector
	 */
	public DeferredLookups(InjectorImpl injector) {
		this.injector = injector;
	}

	
	/**
	 * Initialize.
	 *
	 * @param errors the errors
	 */
	public void initialize(Errors errors) {
		injector.lookups = injector;
		new LookupProcessor(errors).process(injector, lookups);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.Lookups#getProvider(cn.com.summall.search.commons.inject.Key)
	 */
	public <T> Provider<T> getProvider(Key<T> key) {
		ProviderLookup<T> lookup = new ProviderLookup<T>(key, key);
		lookups.add(lookup);
		return lookup.getProvider();
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.Lookups#getMembersInjector(cn.com.summall.search.commons.inject.TypeLiteral)
	 */
	public <T> MembersInjector<T> getMembersInjector(TypeLiteral<T> type) {
		MembersInjectorLookup<T> lookup = new MembersInjectorLookup<T>(type, type);
		lookups.add(lookup);
		return lookup.getMembersInjector();
	}
}
