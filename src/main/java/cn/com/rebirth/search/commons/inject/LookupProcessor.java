/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons LookupProcessor.java 2012-3-29 15:15:08 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject;

import cn.com.rebirth.search.commons.inject.internal.Errors;
import cn.com.rebirth.search.commons.inject.internal.ErrorsException;
import cn.com.rebirth.search.commons.inject.spi.MembersInjectorLookup;
import cn.com.rebirth.search.commons.inject.spi.ProviderLookup;


/**
 * The Class LookupProcessor.
 *
 * @author l.xue.nong
 */
class LookupProcessor extends AbstractProcessor {

	
	/**
	 * Instantiates a new lookup processor.
	 *
	 * @param errors the errors
	 */
	LookupProcessor(Errors errors) {
		super(errors);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.AbstractProcessor#visit(cn.com.summall.search.commons.inject.spi.MembersInjectorLookup)
	 */
	@Override
	public <T> Boolean visit(MembersInjectorLookup<T> lookup) {
		try {
			MembersInjector<T> membersInjector = injector.membersInjectorStore.get(lookup.getType(), errors);
			lookup.initializeDelegate(membersInjector);
		} catch (ErrorsException e) {
			errors.merge(e.getErrors()); 
		}

		return true;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.AbstractProcessor#visit(cn.com.summall.search.commons.inject.spi.ProviderLookup)
	 */
	@Override
	public <T> Boolean visit(ProviderLookup<T> lookup) {
		
		try {
			Provider<T> provider = injector.getProviderOrThrow(lookup.getKey(), errors);
			lookup.initializeDelegate(provider);
		} catch (ErrorsException e) {
			errors.merge(e.getErrors()); 
		}

		return true;
	}
}
