/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.otto;

import com.squareup.otto.EventHandler;
import com.squareup.otto.EventProducer;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

final class AnnotatedHandlerFinder {
    private static final Map<Class<?>, Map<Class<?>, Method>> PRODUCERS_CACHE = new HashMap();
    private static final Map<Class<?>, Map<Class<?>, Set<Method>>> SUBSCRIBERS_CACHE = new HashMap();

    private AnnotatedHandlerFinder() {
    }

    static Map<Class<?>, EventProducer> findAllProducers(Object object) {
        Iterator iterator = object.getClass();
        HashMap hashMap = new HashMap();
        if (!PRODUCERS_CACHE.containsKey(iterator)) {
            AnnotatedHandlerFinder.loadAnnotatedMethods(iterator);
        }
        if (!(iterator = PRODUCERS_CACHE.get(iterator)).isEmpty()) {
            for (Map.Entry entry : iterator.entrySet()) {
                EventProducer eventProducer = new EventProducer(object, (Method)entry.getValue());
                hashMap.put(entry.getKey(), eventProducer);
            }
        }
        return hashMap;
    }

    static Map<Class<?>, Set<EventHandler>> findAllSubscribers(Object object) {
        Iterator iterator = object.getClass();
        HashMap hashMap = new HashMap();
        if (!SUBSCRIBERS_CACHE.containsKey(iterator)) {
            AnnotatedHandlerFinder.loadAnnotatedMethods(iterator);
        }
        if (!(iterator = SUBSCRIBERS_CACHE.get(iterator)).isEmpty()) {
            for (Map.Entry entry : iterator.entrySet()) {
                HashSet<EventHandler> hashSet = new HashSet<EventHandler>();
                Iterator iterator2 = ((Set)entry.getValue()).iterator();
                while (iterator2.hasNext()) {
                    hashSet.add(new EventHandler(object, (Method)iterator2.next()));
                }
                hashMap.put(entry.getKey(), (Set<EventHandler>)hashSet);
            }
        }
        return hashMap;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void loadAnnotatedMethods(Class<?> class_) {
        HashMap hashMap = new HashMap();
        HashMap<HashSet<Method>, Method> hashMap2 = new HashMap<HashSet<Method>, Method>();
        Method[] arrmethod = class_.getDeclaredMethods();
        int n2 = arrmethod.length;
        int n3 = 0;
        do {
            Object object;
            if (n3 >= n2) {
                PRODUCERS_CACHE.put(class_, hashMap2);
                SUBSCRIBERS_CACHE.put(class_, hashMap);
                return;
            }
            Method method = arrmethod[n3];
            if (method.isAnnotationPresent(Subscribe.class)) {
                Set set;
                object = method.getParameterTypes();
                if (object.length != 1) {
                    throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation but requires " + object.length + " arguments.  Methods must require a single argument.");
                }
                Class class_2 = object[0];
                if (class_2.isInterface()) {
                    throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation on " + class_2 + " which is an interface.  Subscription must be on a concrete class type.");
                }
                if ((method.getModifiers() & 1) == 0) {
                    throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation on " + class_2 + " but is not 'public'.");
                }
                object = set = (Set)hashMap.get(class_2);
                if (set == null) {
                    object = new HashSet<Method>();
                    hashMap.put(class_2, (HashSet<Method>)object);
                }
                object.add(method);
            } else if (method.isAnnotationPresent(Produce.class)) {
                object = method.getParameterTypes();
                if (object.length != 0) {
                    throw new IllegalArgumentException("Method " + method + "has @Produce annotation but requires " + object.length + " arguments.  Methods must require zero arguments.");
                }
                if (method.getReturnType() == Void.class) {
                    throw new IllegalArgumentException("Method " + method + " has a return type of void.  Must declare a non-void type.");
                }
                object = method.getReturnType();
                if (object.isInterface()) {
                    throw new IllegalArgumentException("Method " + method + " has @Produce annotation on " + object + " which is an interface.  Producers must return a concrete class type.");
                }
                if (object.equals(Void.TYPE)) {
                    throw new IllegalArgumentException("Method " + method + " has @Produce annotation but has no return type.");
                }
                if ((method.getModifiers() & 1) == 0) {
                    throw new IllegalArgumentException("Method " + method + " has @Produce annotation on " + object + " but is not 'public'.");
                }
                if (hashMap2.containsKey(object)) {
                    throw new IllegalArgumentException("Producer for type " + object + " has already been registered.");
                }
                hashMap2.put((HashSet<Method>)object, method);
            }
            ++n3;
        } while (true);
    }
}

