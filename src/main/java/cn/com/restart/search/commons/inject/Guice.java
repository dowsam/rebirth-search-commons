/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Guice.java 2012-3-29 15:15:18 l.xue.nong$$
 */


package cn.com.restart.search.commons.inject;
import java.util.Arrays;


/**
 * The Class Guice.
 *
 * @author l.xue.nong
 */
public final class Guice {

    
    /**
     * Instantiates a new guice.
     */
    private Guice() {
    }

    
    /**
     * Creates the injector.
     *
     * @param modules the modules
     * @return the injector
     */
    public static Injector createInjector(Module... modules) {
        return createInjector(Arrays.asList(modules));
    }

    
    /**
     * Creates the injector.
     *
     * @param modules the modules
     * @return the injector
     */
    public static Injector createInjector(Iterable<? extends Module> modules) {
        return createInjector(Stage.DEVELOPMENT, modules);
    }

    
    /**
     * Creates the injector.
     *
     * @param stage the stage
     * @param modules the modules
     * @return the injector
     */
    public static Injector createInjector(Stage stage, Module... modules) {
        return createInjector(stage, Arrays.asList(modules));
    }

    
    /**
     * Creates the injector.
     *
     * @param stage the stage
     * @param modules the modules
     * @return the injector
     */
    public static Injector createInjector(Stage stage,
                                          Iterable<? extends Module> modules) {
        return new InjectorBuilder()
                .stage(stage)
                .addModules(modules)
                .build();
    }
}
