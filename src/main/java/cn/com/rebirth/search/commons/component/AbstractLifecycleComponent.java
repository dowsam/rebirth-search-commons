/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons AbstractLifecycleComponent.java 2012-3-29 15:15:14 l.xue.nong$$
 */
package cn.com.rebirth.search.commons.component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.com.rebirth.commons.exception.RestartException;
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

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.component.LifecycleComponent#lifecycleState()
	 */
	@Override
	public Lifecycle.State lifecycleState() {
		return this.lifecycle.state();
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.component.LifecycleComponent#addLifecycleListener(cn.com.summall.search.commons.component.LifecycleListener)
	 */
	@Override
	public void addLifecycleListener(LifecycleListener listener) {
		listeners.add(listener);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.component.LifecycleComponent#removeLifecycleListener(cn.com.summall.search.commons.component.LifecycleListener)
	 */
	@Override
	public void removeLifecycleListener(LifecycleListener listener) {
		listeners.remove(listener);
	}

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.component.LifecycleComponent#start()
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public T start() throws RestartException {
		if (!lifecycle.canMoveToStarted()) {
			return (T) this;
		}
		for (LifecycleListener listener : listeners) {
			listener.beforeStart();
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
	 * @throws SumMallSearchException the sum mall search exception
	 */
	protected abstract void doStart() throws RestartException;

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.component.LifecycleComponent#stop()
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public T stop() throws RestartException {
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
	 * @throws SumMallSearchException the sum mall search exception
	 */
	protected abstract void doStop() throws RestartException;

	
	/* (non-Javadoc)
	 * @see cn.com.summall.search.commons.component.CloseableComponent#close()
	 */
	@Override
	public void close() throws RestartException {
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
	 * @throws SumMallSearchException the sum mall search exception
	 */
	protected abstract void doClose() throws RestartException;
}
