/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons SingleParameterInjector.java 2012-3-29 15:15:19 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject;
import cn.com.restart.search.commons.inject.internal.Errors;
import cn.com.restart.search.commons.inject.internal.ErrorsException;
import cn.com.restart.search.commons.inject.internal.InternalContext;
import cn.com.restart.search.commons.inject.internal.InternalFactory;
import cn.com.restart.search.commons.inject.spi.Dependency;


/**
 * The Class SingleParameterInjector.
 *
 * @param <T> the generic type
 * @author l.xue.nong
 */
class SingleParameterInjector<T> {
    
    
    /** The Constant NO_ARGUMENTS. */
    private static final Object[] NO_ARGUMENTS = {};

    
    /** The dependency. */
    private final Dependency<T> dependency;
    
    
    /** The factory. */
    private final InternalFactory<? extends T> factory;

    
    /**
     * Instantiates a new single parameter injector.
     *
     * @param dependency the dependency
     * @param factory the factory
     */
    SingleParameterInjector(Dependency<T> dependency, InternalFactory<? extends T> factory) {
        this.dependency = dependency;
        this.factory = factory;
    }

    
    /**
     * Inject.
     *
     * @param errors the errors
     * @param context the context
     * @return the t
     * @throws ErrorsException the errors exception
     */
    private T inject(Errors errors, InternalContext context) throws ErrorsException {
        context.setDependency(dependency);
        try {
            return factory.get(errors.withSource(dependency), context, dependency);
        } finally {
            context.setDependency(null);
        }
    }

    
    /**
     * Gets the all.
     *
     * @param errors the errors
     * @param context the context
     * @param parameterInjectors the parameter injectors
     * @return the all
     * @throws ErrorsException the errors exception
     */
    static Object[] getAll(Errors errors, InternalContext context,
                           SingleParameterInjector<?>[] parameterInjectors) throws ErrorsException {
        if (parameterInjectors == null) {
            return NO_ARGUMENTS;
        }

        int numErrorsBefore = errors.size();

        int size = parameterInjectors.length;
        Object[] parameters = new Object[size];

        
        for (int i = 0; i < size; i++) {
            SingleParameterInjector<?> parameterInjector = parameterInjectors[i];
            try {
                parameters[i] = parameterInjector.inject(errors, context);
            } catch (ErrorsException e) {
                errors.merge(e.getErrors());
            }
        }

        errors.throwIfNewErrors(numErrorsBefore);
        return parameters;
    }
}
