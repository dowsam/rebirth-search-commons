/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons ElementVisitor.java 2012-3-29 15:15:15 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.spi;

import cn.com.restart.search.commons.inject.Binding;


/**
 * The Interface ElementVisitor.
 *
 * @param <V> the value type
 * @author l.xue.nong
 */
public interface ElementVisitor<V> {

	
	/**
	 * Visit.
	 *
	 * @param <T> the generic type
	 * @param binding the binding
	 * @return the v
	 */
	<T> V visit(Binding<T> binding);

	
	/**
	 * Visit.
	 *
	 * @param binding the binding
	 * @return the v
	 */
	V visit(ScopeBinding binding);

	
	/**
	 * Visit.
	 *
	 * @param binding the binding
	 * @return the v
	 */
	V visit(TypeConverterBinding binding);

	
	/**
	 * Visit.
	 *
	 * @param request the request
	 * @return the v
	 */
	@SuppressWarnings("rawtypes")
	V visit(InjectionRequest request);

	
	/**
	 * Visit.
	 *
	 * @param request the request
	 * @return the v
	 */
	V visit(StaticInjectionRequest request);

	
	/**
	 * Visit.
	 *
	 * @param <T> the generic type
	 * @param lookup the lookup
	 * @return the v
	 */
	<T> V visit(ProviderLookup<T> lookup);

	
	/**
	 * Visit.
	 *
	 * @param <T> the generic type
	 * @param lookup the lookup
	 * @return the v
	 */
	<T> V visit(MembersInjectorLookup<T> lookup);

	
	/**
	 * Visit.
	 *
	 * @param message the message
	 * @return the v
	 */
	V visit(Message message);

	
	/**
	 * Visit.
	 *
	 * @param elements the elements
	 * @return the v
	 */
	V visit(PrivateElements elements);

	
	/**
	 * Visit.
	 *
	 * @param binding the binding
	 * @return the v
	 */
	V visit(TypeListenerBinding binding);
}
