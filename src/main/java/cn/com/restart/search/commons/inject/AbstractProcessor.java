/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons AbstractProcessor.java 2012-3-29 15:15:14 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject;
import java.util.Iterator;
import java.util.List;

import cn.com.restart.search.commons.inject.internal.Errors;
import cn.com.restart.search.commons.inject.spi.Element;
import cn.com.restart.search.commons.inject.spi.ElementVisitor;
import cn.com.restart.search.commons.inject.spi.InjectionRequest;
import cn.com.restart.search.commons.inject.spi.MembersInjectorLookup;
import cn.com.restart.search.commons.inject.spi.Message;
import cn.com.restart.search.commons.inject.spi.PrivateElements;
import cn.com.restart.search.commons.inject.spi.ProviderLookup;
import cn.com.restart.search.commons.inject.spi.ScopeBinding;
import cn.com.restart.search.commons.inject.spi.StaticInjectionRequest;
import cn.com.restart.search.commons.inject.spi.TypeConverterBinding;
import cn.com.restart.search.commons.inject.spi.TypeListenerBinding;


/**
 * The Class AbstractProcessor.
 *
 * @author l.xue.nong
 */
abstract class AbstractProcessor implements ElementVisitor<Boolean> {

    
    /** The errors. */
    protected Errors errors;
    
    
    /** The injector. */
    protected InjectorImpl injector;

    
    /**
     * Instantiates a new abstract processor.
     *
     * @param errors the errors
     */
    protected AbstractProcessor(Errors errors) {
        this.errors = errors;
    }

    
    /**
     * Process.
     *
     * @param isolatedInjectorBuilders the isolated injector builders
     */
    public void process(Iterable<InjectorShell> isolatedInjectorBuilders) {
        for (InjectorShell injectorShell : isolatedInjectorBuilders) {
            process(injectorShell.getInjector(), injectorShell.getElements());
        }
    }

    
    /**
     * Process.
     *
     * @param injector the injector
     * @param elements the elements
     */
    public void process(InjectorImpl injector, List<Element> elements) {
        Errors errorsAnyElement = this.errors;
        this.injector = injector;
        try {
            for (Iterator<Element> i = elements.iterator(); i.hasNext(); ) {
                Element element = i.next();
                this.errors = errorsAnyElement.withSource(element.getSource());
                Boolean allDone = element.acceptVisitor(this);
                if (allDone) {
                    i.remove();
                }
            }
        } finally {
            this.errors = errorsAnyElement;
            this.injector = null;
        }
    }

    
    /* (non-Javadoc)
     * @see cn.com.summall.search.commons.inject.spi.ElementVisitor#visit(cn.com.summall.search.commons.inject.spi.Message)
     */
    public Boolean visit(Message message) {
        return false;
    }

    
    /* (non-Javadoc)
     * @see cn.com.summall.search.commons.inject.spi.ElementVisitor#visit(cn.com.summall.search.commons.inject.spi.ScopeBinding)
     */
    public Boolean visit(ScopeBinding scopeBinding) {
        return false;
    }

    
    /* (non-Javadoc)
     * @see cn.com.summall.search.commons.inject.spi.ElementVisitor#visit(cn.com.summall.search.commons.inject.spi.InjectionRequest)
     */
    @SuppressWarnings("rawtypes")
	public Boolean visit(InjectionRequest injectionRequest) {
        return false;
    }

    
    /* (non-Javadoc)
     * @see cn.com.summall.search.commons.inject.spi.ElementVisitor#visit(cn.com.summall.search.commons.inject.spi.StaticInjectionRequest)
     */
    public Boolean visit(StaticInjectionRequest staticInjectionRequest) {
        return false;
    }

    
    /* (non-Javadoc)
     * @see cn.com.summall.search.commons.inject.spi.ElementVisitor#visit(cn.com.summall.search.commons.inject.spi.TypeConverterBinding)
     */
    public Boolean visit(TypeConverterBinding typeConverterBinding) {
        return false;
    }

    
    /* (non-Javadoc)
     * @see cn.com.summall.search.commons.inject.spi.ElementVisitor#visit(cn.com.summall.search.commons.inject.Binding)
     */
    public <T> Boolean visit(Binding<T> binding) {
        return false;
    }

    
    /* (non-Javadoc)
     * @see cn.com.summall.search.commons.inject.spi.ElementVisitor#visit(cn.com.summall.search.commons.inject.spi.ProviderLookup)
     */
    public <T> Boolean visit(ProviderLookup<T> providerLookup) {
        return false;
    }

    
    /* (non-Javadoc)
     * @see cn.com.summall.search.commons.inject.spi.ElementVisitor#visit(cn.com.summall.search.commons.inject.spi.PrivateElements)
     */
    public Boolean visit(PrivateElements privateElements) {
        return false;
    }

    
    /* (non-Javadoc)
     * @see cn.com.summall.search.commons.inject.spi.ElementVisitor#visit(cn.com.summall.search.commons.inject.spi.MembersInjectorLookup)
     */
    public <T> Boolean visit(MembersInjectorLookup<T> lookup) {
        return false;
    }

    
    /* (non-Javadoc)
     * @see cn.com.summall.search.commons.inject.spi.ElementVisitor#visit(cn.com.summall.search.commons.inject.spi.TypeListenerBinding)
     */
    public Boolean visit(TypeListenerBinding binding) {
        return false;
    }
}
