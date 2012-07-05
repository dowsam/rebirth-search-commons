/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ScopeBindingProcessor.java 2012-3-29 15:15:15 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.annotation.Annotation;

import cn.com.restart.search.commons.inject.internal.Annotations;
import cn.com.restart.search.commons.inject.internal.Errors;
import cn.com.restart.search.commons.inject.spi.ScopeBinding;


/**
 * The Class ScopeBindingProcessor.
 *
 * @author l.xue.nong
 */
class ScopeBindingProcessor extends AbstractProcessor {

	
	/**
	 * Instantiates a new scope binding processor.
	 *
	 * @param errors the errors
	 */
	ScopeBindingProcessor(Errors errors) {
		super(errors);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.AbstractProcessor#visit(cn.com.summall.search.commons.inject.spi.ScopeBinding)
	 */
	@Override
	public Boolean visit(ScopeBinding command) {
		Scope scope = command.getScope();
		Class<? extends Annotation> annotationType = command.getAnnotationType();

		if (!Annotations.isScopeAnnotation(annotationType)) {
			errors.withSource(annotationType).missingScopeAnnotation();
			
		}

		if (!Annotations.isRetainedAtRuntime(annotationType)) {
			errors.withSource(annotationType).missingRuntimeRetention(command.getSource());
			
		}

		Scope existing = injector.state.getScope(checkNotNull(annotationType, "annotation type"));
		if (existing != null) {
			errors.duplicateScopes(existing, annotationType, scope);
		} else {
			injector.state.putAnnotation(annotationType, checkNotNull(scope, "scope"));
		}

		return true;
	}
}