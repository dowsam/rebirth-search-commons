/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Multibinder.java 2012-3-29 15:15:08 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject.multibindings;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import cn.com.restart.search.commons.inject.Binder;
import cn.com.restart.search.commons.inject.Binding;
import cn.com.restart.search.commons.inject.ConfigurationException;
import cn.com.restart.search.commons.inject.Inject;
import cn.com.restart.search.commons.inject.Injector;
import cn.com.restart.search.commons.inject.Key;
import cn.com.restart.search.commons.inject.Module;
import cn.com.restart.search.commons.inject.Provider;
import cn.com.restart.search.commons.inject.TypeLiteral;
import cn.com.restart.search.commons.inject.binder.LinkedBindingBuilder;
import cn.com.restart.search.commons.inject.internal.Errors;
import cn.com.restart.search.commons.inject.spi.Dependency;
import cn.com.restart.search.commons.inject.spi.HasDependencies;
import cn.com.restart.search.commons.inject.spi.Message;
import cn.com.restart.search.commons.inject.util.Types;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;


/**
 * The Class Multibinder.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
public abstract class Multibinder<T> {

	
	/**
	 * Instantiates a new multibinder.
	 */
	private Multibinder() {
	}

	
	/**
	 * New set binder.
	 *
	 * @param <T> the generic type
	 * @param binder the binder
	 * @param type the type
	 * @return the multibinder
	 */
	public static <T> Multibinder<T> newSetBinder(Binder binder, TypeLiteral<T> type) {
		binder = binder.skipSources(RealMultibinder.class, Multibinder.class);
		RealMultibinder<T> result = new RealMultibinder<T>(binder, type, "", Key.get(Multibinder.<T> setOf(type)));
		binder.install(result);
		return result;
	}

	
	/**
	 * New set binder.
	 *
	 * @param <T> the generic type
	 * @param binder the binder
	 * @param type the type
	 * @return the multibinder
	 */
	public static <T> Multibinder<T> newSetBinder(Binder binder, Class<T> type) {
		return newSetBinder(binder, TypeLiteral.get(type));
	}

	
	/**
	 * New set binder.
	 *
	 * @param <T> the generic type
	 * @param binder the binder
	 * @param type the type
	 * @param annotation the annotation
	 * @return the multibinder
	 */
	public static <T> Multibinder<T> newSetBinder(Binder binder, TypeLiteral<T> type, Annotation annotation) {
		binder = binder.skipSources(RealMultibinder.class, Multibinder.class);
		RealMultibinder<T> result = new RealMultibinder<T>(binder, type, annotation.toString(), Key.get(
				Multibinder.<T> setOf(type), annotation));
		binder.install(result);
		return result;
	}

	
	/**
	 * New set binder.
	 *
	 * @param <T> the generic type
	 * @param binder the binder
	 * @param type the type
	 * @param annotation the annotation
	 * @return the multibinder
	 */
	public static <T> Multibinder<T> newSetBinder(Binder binder, Class<T> type, Annotation annotation) {
		return newSetBinder(binder, TypeLiteral.get(type), annotation);
	}

	
	/**
	 * New set binder.
	 *
	 * @param <T> the generic type
	 * @param binder the binder
	 * @param type the type
	 * @param annotationType the annotation type
	 * @return the multibinder
	 */
	public static <T> Multibinder<T> newSetBinder(Binder binder, TypeLiteral<T> type,
			Class<? extends Annotation> annotationType) {
		binder = binder.skipSources(RealMultibinder.class, Multibinder.class);
		RealMultibinder<T> result = new RealMultibinder<T>(binder, type, "@" + annotationType.getName(), Key.get(
				Multibinder.<T> setOf(type), annotationType));
		binder.install(result);
		return result;
	}

	
	/**
	 * New set binder.
	 *
	 * @param <T> the generic type
	 * @param binder the binder
	 * @param type the type
	 * @param annotationType the annotation type
	 * @return the multibinder
	 */
	public static <T> Multibinder<T> newSetBinder(Binder binder, Class<T> type,
			Class<? extends Annotation> annotationType) {
		return newSetBinder(binder, TypeLiteral.get(type), annotationType);
	}

	
	/**
	 * Sets the of.
	 *
	 * @param <T> the generic type
	 * @param elementType the element type
	 * @return the type literal
	 */
	@SuppressWarnings("unchecked")
	
	private static <T> TypeLiteral<Set<T>> setOf(TypeLiteral<T> elementType) {
		Type type = Types.setOf(elementType.getType());
		return (TypeLiteral<Set<T>>) TypeLiteral.get(type);
	}

	
	/**
	 * Adds the binding.
	 *
	 * @return the linked binding builder
	 */
	public abstract LinkedBindingBuilder<T> addBinding();

	
	/**
	 * The Class RealMultibinder.
	 *
	 * @param <T> the generic type
	 * @author l.xue.nong
	 */
	static final class RealMultibinder<T> extends Multibinder<T> implements Module, Provider<Set<T>>, HasDependencies {

		
		/** The element type. */
		private final TypeLiteral<T> elementType;

		
		/** The set name. */
		private final String setName;

		
		/** The set key. */
		private final Key<Set<T>> setKey;

		
		
		/** The binder. */
		private Binder binder;

		
		
		/** The providers. */
		private List<Provider<T>> providers;

		
		/** The dependencies. */
		private Set<Dependency<?>> dependencies;

		
		/**
		 * Instantiates a new real multibinder.
		 *
		 * @param binder the binder
		 * @param elementType the element type
		 * @param setName the set name
		 * @param setKey the set key
		 */
		private RealMultibinder(Binder binder, TypeLiteral<T> elementType, String setName, Key<Set<T>> setKey) {
			this.binder = checkNotNull(binder, "binder");
			this.elementType = checkNotNull(elementType, "elementType");
			this.setName = checkNotNull(setName, "setName");
			this.setKey = checkNotNull(setKey, "setKey");
		}

		
		/* (non-Javadoc)
		 * @see cn.com.summall.search.commons.inject.Module#configure(cn.com.summall.search.commons.inject.Binder)
		 */
		public void configure(Binder binder) {
			checkConfiguration(!isInitialized(), "Multibinder was already initialized");

			binder.bind(setKey).toProvider(this);
		}

		
		/* (non-Javadoc)
		 * @see cn.com.summall.search.commons.inject.multibindings.Multibinder#addBinding()
		 */
		@Override
		public LinkedBindingBuilder<T> addBinding() {
			checkConfiguration(!isInitialized(), "Multibinder was already initialized");

			return binder.bind(Key.get(elementType, new RealElement(setName)));
		}

		
		/**
		 * Initialize.
		 *
		 * @param injector the injector
		 */
		@Inject
		void initialize(Injector injector) {
			providers = Lists.newArrayList();
			List<Dependency<?>> dependencies = Lists.newArrayList();
			for (Binding<?> entry : injector.findBindingsByType(elementType)) {

				if (keyMatches(entry.getKey())) {
					@SuppressWarnings("unchecked")
					
					Binding<T> binding = (Binding<T>) entry;
					providers.add(binding.getProvider());
					dependencies.add(Dependency.get(binding.getKey()));
				}
			}

			this.dependencies = ImmutableSet.copyOf(dependencies);
			this.binder = null;
		}

		
		/**
		 * Key matches.
		 *
		 * @param key the key
		 * @return true, if successful
		 */
		private boolean keyMatches(Key<?> key) {
			return key.getTypeLiteral().equals(elementType) && key.getAnnotation() instanceof Element
					&& ((Element) key.getAnnotation()).setName().equals(setName);
		}

		
		/**
		 * Checks if is initialized.
		 *
		 * @return true, if is initialized
		 */
		private boolean isInitialized() {
			return binder == null;
		}

		
		/* (non-Javadoc)
		 * @see cn.com.summall.search.commons.inject.Provider#get()
		 */
		public Set<T> get() {
			checkConfiguration(isInitialized(), "Multibinder is not initialized");

			Set<T> result = new LinkedHashSet<T>();
			for (Provider<T> provider : providers) {
				final T newValue = provider.get();
				checkConfiguration(newValue != null, "Set injection failed due to null element");
				checkConfiguration(result.add(newValue), "Set injection failed due to duplicated element \"%s\"",
						newValue);
			}
			return Collections.unmodifiableSet(result);
		}

		
		/**
		 * Gets the sets the name.
		 *
		 * @return the sets the name
		 */
		String getSetName() {
			return setName;
		}

		
		/**
		 * Gets the sets the key.
		 *
		 * @return the sets the key
		 */
		Key<Set<T>> getSetKey() {
			return setKey;
		}

		
		/* (non-Javadoc)
		 * @see cn.com.summall.search.commons.inject.spi.HasDependencies#getDependencies()
		 */
		public Set<Dependency<?>> getDependencies() {
			return dependencies;
		}

		
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object o) {
			return o instanceof RealMultibinder && ((RealMultibinder<?>) o).setKey.equals(setKey);
		}

		
		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			return setKey.hashCode();
		}

		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return new StringBuilder().append(setName).append(setName.length() > 0 ? " " : "").append("Multibinder<")
					.append(elementType).append(">").toString();
		}
	}

	
	/**
	 * Check configuration.
	 *
	 * @param condition the condition
	 * @param format the format
	 * @param args the args
	 */
	static void checkConfiguration(boolean condition, String format, Object... args) {
		if (condition) {
			return;
		}

		throw new ConfigurationException(ImmutableSet.of(new Message(Errors.format(format, args))));
	}

	
	/**
	 * Check not null.
	 *
	 * @param <T> the generic type
	 * @param reference the reference
	 * @param name the name
	 * @return the t
	 */
	static <T> T checkNotNull(T reference, String name) {
		if (reference != null) {
			return reference;
		}

		NullPointerException npe = new NullPointerException(name);
		throw new ConfigurationException(ImmutableSet.of(new Message(ImmutableList.of(), npe.toString(), npe)));
	}
}
