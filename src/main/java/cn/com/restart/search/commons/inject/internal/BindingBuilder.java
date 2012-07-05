/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons BindingBuilder.java 2012-3-29 15:15:20 l.xue.nong$$
 */

package cn.com.restart.search.commons.inject.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import cn.com.restart.search.commons.inject.Binder;
import cn.com.restart.search.commons.inject.ConfigurationException;
import cn.com.restart.search.commons.inject.Key;
import cn.com.restart.search.commons.inject.Provider;
import cn.com.restart.search.commons.inject.TypeLiteral;
import cn.com.restart.search.commons.inject.binder.AnnotatedBindingBuilder;
import cn.com.restart.search.commons.inject.spi.Element;
import cn.com.restart.search.commons.inject.spi.InjectionPoint;
import cn.com.restart.search.commons.inject.spi.Message;

import com.google.common.collect.ImmutableSet;


/**
 * The Class BindingBuilder.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
public class BindingBuilder<T> extends AbstractBindingBuilder<T> implements AnnotatedBindingBuilder<T> {

	
	/**
	 * Instantiates a new binding builder.
	 *
	 * @param binder the binder
	 * @param elements the elements
	 * @param source the source
	 * @param key the key
	 */
	public BindingBuilder(Binder binder, List<Element> elements, Object source, Key<T> key) {
		super(binder, elements, source, key);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.binder.AnnotatedBindingBuilder#annotatedWith(java.lang.Class)
	 */
	public BindingBuilder<T> annotatedWith(Class<? extends Annotation> annotationType) {
		annotatedWithInternal(annotationType);
		return this;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.binder.AnnotatedBindingBuilder#annotatedWith(java.lang.annotation.Annotation)
	 */
	public BindingBuilder<T> annotatedWith(Annotation annotation) {
		annotatedWithInternal(annotation);
		return this;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.binder.LinkedBindingBuilder#to(java.lang.Class)
	 */
	public BindingBuilder<T> to(Class<? extends T> implementation) {
		return to(Key.get(implementation));
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.binder.LinkedBindingBuilder#to(cn.com.summall.search.commons.inject.TypeLiteral)
	 */
	public BindingBuilder<T> to(TypeLiteral<? extends T> implementation) {
		return to(Key.get(implementation));
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.binder.LinkedBindingBuilder#to(cn.com.summall.search.commons.inject.Key)
	 */
	public BindingBuilder<T> to(Key<? extends T> linkedKey) {
		checkNotNull(linkedKey, "linkedKey");
		checkNotTargetted();
		BindingImpl<T> base = getBinding();
		setBinding(new LinkedBindingImpl<T>(base.getSource(), base.getKey(), base.getScoping(), linkedKey));
		return this;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.binder.LinkedBindingBuilder#toInstance(java.lang.Object)
	 */
	public void toInstance(T instance) {
		checkNotTargetted();

		
		Set<InjectionPoint> injectionPoints;
		if (instance != null) {
			try {
				injectionPoints = InjectionPoint.forInstanceMethodsAndFields(instance.getClass());
			} catch (ConfigurationException e) {
				for (Message message : e.getErrorMessages()) {
					binder.addError(message);
				}
				injectionPoints = e.getPartialValue();
			}
		} else {
			binder.addError(BINDING_TO_NULL);
			injectionPoints = ImmutableSet.of();
		}

		BindingImpl<T> base = getBinding();
		setBinding(new InstanceBindingImpl<T>(base.getSource(), base.getKey(), base.getScoping(), injectionPoints,
				instance));
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.binder.LinkedBindingBuilder#toProvider(cn.com.summall.search.commons.inject.Provider)
	 */
	public BindingBuilder<T> toProvider(Provider<? extends T> provider) {
		checkNotNull(provider, "provider");
		checkNotTargetted();

		
		Set<InjectionPoint> injectionPoints;
		try {
			injectionPoints = InjectionPoint.forInstanceMethodsAndFields(provider.getClass());
		} catch (ConfigurationException e) {
			for (Message message : e.getErrorMessages()) {
				binder.addError(message);
			}
			injectionPoints = e.getPartialValue();
		}

		BindingImpl<T> base = getBinding();
		setBinding(new ProviderInstanceBindingImpl<T>(base.getSource(), base.getKey(), base.getScoping(),
				injectionPoints, provider));
		return this;
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.binder.LinkedBindingBuilder#toProvider(java.lang.Class)
	 */
	public BindingBuilder<T> toProvider(Class<? extends Provider<? extends T>> providerType) {
		return toProvider(Key.get(providerType));
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.inject.binder.LinkedBindingBuilder#toProvider(cn.com.summall.search.commons.inject.Key)
	 */
	public BindingBuilder<T> toProvider(Key<? extends Provider<? extends T>> providerKey) {
		checkNotNull(providerKey, "providerKey");
		checkNotTargetted();

		BindingImpl<T> base = getBinding();
		setBinding(new LinkedProviderBindingImpl<T>(base.getSource(), base.getKey(), base.getScoping(), providerKey));
		return this;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BindingBuilder<" + getBinding().getKey().getTypeLiteral() + ">";
	}
}
