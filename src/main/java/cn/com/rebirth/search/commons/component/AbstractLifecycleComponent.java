/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons AbstractLifecycleComponent.java 2012-7-6 10:23:47 l.xue.nong$$
 */
package cn.com.rebirth.search.commons.component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.com.rebirth.commons.exception.RebirthException;
import cn.com.rebirth.commons.settings.Settings;


/**
 * The Class AbstractLifecycleComponent.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
public abstract class AbstractLifecycleComponent<T> extends AbstractComponent implements LifecycleComponent<T> {

	
	/** The lifecycle. */
	protected final Lifecycle lifecycle = new Lifecycle();

	
	/** The listeners. */
	private final List<LifecycleListener> listeners = new CopyOnWriteArrayList<LifecycleListener>();

	
	/**
	 * Instantiates a new abstract lifecycle component.
	 *
	 * @param settings the settings
	 */
	protected AbstractLifecycleComponent(Settings settings) {
		super(settings);
	}

	
	/**
	 * Instantiates a new abstract lifecycle component.
	 *
	 * @param settings the settings
	 * @param customClass the custom class
	 */
	protected AbstractLifecycleComponent(Settings settings, Class<?> customClass) {
		super(settings, customClass);
	}

	
	/**
	 * Instantiates a new abstract lifecycle component.
	 *
	 * @param settings the settings
	 * @param loggerClass the logger class
	 * @param componentClass the component class
	 */
	protected AbstractLifecycleComponent(Settings settings, Class<?> loggerClass, Class<?> componentClass) {
		super(settings, loggerClass, componentClass);
	}

	
	/**
	 * Instantiates a new abstract lifecycle component.
	 *
	 * @param settings the settings
	 * @param prefixSettings the prefix settings
	 */
	protected AbstractLifecycleComponent(Settings settings, String prefixSettings) {
		super(settings, prefixSettings);
	}

	
	/**
	 * Instantiates a new abstract lifecycle component.
	 *
	 * @param settings the settings
	 * @param prefixSettings the prefix settings
	 * @param customClass the custom class
	 */
	protected AbstractLifecycleComponent(Settings settings, String prefixSettings, Class<?> customClass) {
		super(settings, prefixSettings, customClass);
	}

	
	/**
	 * Instantiates a new abstract lifecycle component.
	 *
	 * @param settings the settings
	 * @param prefixSettings the prefix settings
	 * @param loggerClass the logger class
	 * @param componentClass the component class
	 */
	protected AbstractLifecycleComponent(Settings settings, String prefixSettings, Class<?> loggerClass,
			Class<?> componentClass) {
		super(settings, prefixSettings, loggerClass, componentClass);
	}

	
	@Override
	public Lifecycle.State lifecycleState() {
		return this.lifecycle.state();
	}

	
	@Override
	public void addLifecycleListener(LifecycleListener listener) {
		listeners.add(listener);
	}

	
	@Override
	public void removeLifecycleListener(LifecycleListener listener) {
		listeners.remove(listener);
	}

	
	@SuppressWarnings({ "unchecked" })
	@Override
	public T start() throws RebirthException {
		if (!lifecycle.canMoveToStarted()) {
			return (T) this;
		}
		for (LifecycleListener listener : listeners) {
			listener.beforebirth();
		}
		doStart();
		lifecycle.moveToStarted();
		for (LifecycleListener listener : listeners) {
			listener.afterStart();
		}
		return (T) this;
	}

	
	/**
	 * Do start.
	 *
	 * @throws rebirthException the rebirth exception
	 */
	protected abstract void doStart() throws RebirthException;

	
	@SuppressWarnings({ "unchecked" })
	@Override
	public T stop() throws RebirthException {
		if (!lifecycle.canMoveToStopped()) {
			return (T) this;
		}
		for (LifecycleListener listener : listeners) {
			listener.beforeStop();
		}
		lifecycle.moveToStopped();
		doStop();
		for (LifecycleListener listener : listeners) {
			listener.afterStop();
		}
		return (T) this;
	}

	
	/**
	 * Do stop.
	 *
	 * @throws rebirthException the rebirth exception
	 */
	protected abstract void doStop() throws RebirthException;

	
	@Override
	public void close() throws RebirthException {
		if (lifecycle.started()) {
			stop();
		}
		if (!lifecycle.canMoveToClosed()) {
			return;
		}
		for (LifecycleListener listener : listeners) {
			listener.beforeClose();
		}
		lifecycle.moveToClosed();
		doClose();
		for (LifecycleListener listener : listeners) {
			listener.afterClose();
		}
	}

	
	/**
	 * Do close.
	 *
	 * @throws rebirthException the rebirth exception
	 */
	protected abstract void doClose() throws RebirthException;
}
