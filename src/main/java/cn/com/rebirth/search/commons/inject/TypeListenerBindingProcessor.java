/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons TypeListenerBindingProcessor.java 2012-3-29 15:15:18 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.inject;

import cn.com.rebirth.search.commons.inject.internal.Errors;
import cn.com.rebirth.search.commons.inject.spi.TypeListenerBinding;


/**
 * The Class TypeListenerBindingProcessor.
 *
 * @author l.xue.nong
 */
class TypeListenerBindingProcessor extends AbstractProcessor {

	
	/**
	 * Instantiates a new type listener binding processor.
	 *
	 * @param errors the errors
	 */
	TypeListenerBindingProcessor(Errors errors) {
		super(errors);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.AbstractProcessor#visit(cn.com.summall.search.commons.inject.spi.TypeListenerBinding)
	 */
	@Override
	public Boolean visit(TypeListenerBinding binding) {
		injector.state.addTypeListener(binding);
		return true;
	}
}