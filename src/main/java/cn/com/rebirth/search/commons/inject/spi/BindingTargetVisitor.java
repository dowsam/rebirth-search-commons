/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons BindingTargetVisitor.java 2012-3-29 15:15:15 l.xue.nong$$
 */



package cn.com.rebirth.search.commons.inject.spi;


/**
 * The Interface BindingTargetVisitor.
 *
 * @param <T> the generic type
 * @param <V> the value type
 * @author l.xue.nong
 */
public interface BindingTargetVisitor<T, V> {

    
    /**
     * Visit.
     *
     * @param binding the binding
     * @return the v
     */
    V visit(InstanceBinding<? extends T> binding);

    
    /**
     * Visit.
     *
     * @param binding the binding
     * @return the v
     */
    V visit(ProviderInstanceBinding<? extends T> binding);

    
    /**
     * Visit.
     *
     * @param binding the binding
     * @return the v
     */
    V visit(ProviderKeyBinding<? extends T> binding);

    
    /**
     * Visit.
     *
     * @param binding the binding
     * @return the v
     */
    V visit(LinkedKeyBinding<? extends T> binding);

    
    /**
     * Visit.
     *
     * @param binding the binding
     * @return the v
     */
    V visit(ExposedBinding<? extends T> binding);

    
    /**
     * Visit.
     *
     * @param binding the binding
     * @return the v
     */
    V visit(UntargettedBinding<? extends T> binding);

    
    /**
     * Visit.
     *
     * @param binding the binding
     * @return the v
     */
    V visit(ConstructorBinding<? extends T> binding);

    
    /**
     * Visit.
     *
     * @param binding the binding
     * @return the v
     */
    V visit(ConvertedConstantBinding<? extends T> binding);

    
    /**
     * Visit.
     *
     * @param binding the binding
     * @return the v
     */
    V visit(ProviderBinding<? extends T> binding);
}
