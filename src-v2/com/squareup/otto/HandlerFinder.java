/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.otto;

import com.squareup.otto.AnnotatedHandlerFinder;
import com.squareup.otto.EventHandler;
import com.squareup.otto.EventProducer;
import java.util.Map;
import java.util.Set;

interface HandlerFinder {
    public static final HandlerFinder ANNOTATED = new HandlerFinder(){

        @Override
        public Map<Class<?>, EventProducer> findAllProducers(Object object) {
            return AnnotatedHandlerFinder.findAllProducers(object);
        }

        @Override
        public Map<Class<?>, Set<EventHandler>> findAllSubscribers(Object object) {
            return AnnotatedHandlerFinder.findAllSubscribers(object);
        }
    };

    public Map<Class<?>, EventProducer> findAllProducers(Object var1);

    public Map<Class<?>, Set<EventHandler>> findAllSubscribers(Object var1);

}

