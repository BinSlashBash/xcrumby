/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.otto;

import com.squareup.otto.DeadEvent;
import com.squareup.otto.EventHandler;
import com.squareup.otto.EventProducer;
import com.squareup.otto.HandlerFinder;
import com.squareup.otto.ThreadEnforcer;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class Bus {
    public static final String DEFAULT_IDENTIFIER = "default";
    private final ThreadEnforcer enforcer;
    private final ThreadLocal<ConcurrentLinkedQueue<EventWithHandler>> eventsToDispatch;
    private final Map<Class<?>, Set<Class<?>>> flattenHierarchyCache;
    private final HandlerFinder handlerFinder;
    private final ConcurrentMap<Class<?>, Set<EventHandler>> handlersByType = new ConcurrentHashMap();
    private final String identifier;
    private final ThreadLocal<Boolean> isDispatching;
    private final ConcurrentMap<Class<?>, EventProducer> producersByType = new ConcurrentHashMap();

    public Bus() {
        this("default");
    }

    public Bus(ThreadEnforcer threadEnforcer) {
        this(threadEnforcer, "default");
    }

    public Bus(ThreadEnforcer threadEnforcer, String string2) {
        this(threadEnforcer, string2, HandlerFinder.ANNOTATED);
    }

    Bus(ThreadEnforcer threadEnforcer, String string2, HandlerFinder handlerFinder) {
        this.eventsToDispatch = new ThreadLocal<ConcurrentLinkedQueue<EventWithHandler>>(){

            @Override
            protected ConcurrentLinkedQueue<EventWithHandler> initialValue() {
                return new ConcurrentLinkedQueue<EventWithHandler>();
            }
        };
        this.isDispatching = new ThreadLocal<Boolean>(){

            @Override
            protected Boolean initialValue() {
                return false;
            }
        };
        this.flattenHierarchyCache = new HashMap();
        this.enforcer = threadEnforcer;
        this.identifier = string2;
        this.handlerFinder = handlerFinder;
    }

    public Bus(String string2) {
        this(ThreadEnforcer.MAIN, string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void dispatchProducerResultToHandler(EventHandler eventHandler, EventProducer object) {
        Object var3_3 = null;
        try {
            Object object2;
            object = object2 = object.produceEvent();
        }
        catch (InvocationTargetException var4_5) {
            Bus.throwRuntimeException("Producer " + object + " threw an exception.", var4_5);
            object = var3_3;
        }
        if (object == null) {
            return;
        }
        this.dispatch(object, eventHandler);
    }

    private Set<Class<?>> getClassesFor(Class<?> class_) {
        LinkedList linkedList = new LinkedList();
        HashSet hashSet = new HashSet();
        linkedList.add(class_);
        while (!linkedList.isEmpty()) {
            class_ = (Class)linkedList.remove(0);
            hashSet.add(class_);
            if ((class_ = class_.getSuperclass()) == null) continue;
            linkedList.add(class_);
        }
        return hashSet;
    }

    private static void throwRuntimeException(String string2, InvocationTargetException throwable) {
        if ((throwable = throwable.getCause()) != null) {
            throw new RuntimeException(string2, throwable);
        }
        throw new RuntimeException(string2);
    }

    protected void dispatch(Object object, EventHandler eventHandler) {
        try {
            eventHandler.handleEvent(object);
            return;
        }
        catch (InvocationTargetException var3_3) {
            Bus.throwRuntimeException("Could not dispatch event: " + object.getClass() + " to handler " + eventHandler, var3_3);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void dispatchQueuedEvents() {
        if (this.isDispatching.get().booleanValue()) {
            return;
        }
        this.isDispatching.set(true);
        try {
            do {
                EventWithHandler eventWithHandler;
                if ((eventWithHandler = this.eventsToDispatch.get().poll()) == null) {
                    this.isDispatching.set(false);
                    return;
                }
                if (!eventWithHandler.handler.isValid()) continue;
                this.dispatch(eventWithHandler.event, eventWithHandler.handler);
            } while (true);
        }
        catch (Throwable var1_2) {
            this.isDispatching.set(false);
            throw var1_2;
        }
    }

    protected void enqueueEvent(Object object, EventHandler eventHandler) {
        this.eventsToDispatch.get().offer(new EventWithHandler(object, eventHandler));
    }

    Set<Class<?>> flattenHierarchy(Class<?> class_) {
        Set set;
        Set set2 = set = this.flattenHierarchyCache.get(class_);
        if (set == null) {
            set2 = this.getClassesFor(class_);
            this.flattenHierarchyCache.put(class_, set2);
        }
        return set2;
    }

    Set<EventHandler> getHandlersForEventType(Class<?> class_) {
        return this.handlersByType.get(class_);
    }

    EventProducer getProducerForEventType(Class<?> class_) {
        return this.producersByType.get(class_);
    }

    public void post(Object object) {
        if (object == null) {
            throw new NullPointerException("Event to post must not be null.");
        }
        this.enforcer.enforce(this);
        Object object2 = this.flattenHierarchy(object.getClass());
        boolean bl2 = false;
        object2 = object2.iterator();
        block0 : while (object2.hasNext()) {
            Set<EventHandler> set = this.getHandlersForEventType((Class)object2.next());
            if (set == null || set.isEmpty()) continue;
            boolean bl3 = true;
            set = set.iterator();
            do {
                bl2 = bl3;
                if (!set.hasNext()) continue block0;
                this.enqueueEvent(object, (EventHandler)set.next());
            } while (true);
        }
        if (!bl2 && !(object instanceof DeadEvent)) {
            this.post(new DeadEvent(this, object));
        }
        this.dispatchQueuedEvents();
    }

    public void register(Object iterator) {
        Object object42;
        if (iterator == null) {
            throw new NullPointerException("Object to register must not be null.");
        }
        this.enforcer.enforce(this);
        Object object2 = this.handlerFinder.findAllProducers(iterator);
        Iterator iterator2 = object2.keySet().iterator();
        while (iterator2.hasNext()) {
            Class class_ = iterator2.next();
            EventProducer object3 = (EventProducer)this.producersByType.putIfAbsent(class_, (EventProducer)(object42 = object2.get(class_)));
            if (object3 != null) {
                throw new IllegalArgumentException("Producer method for type " + class_ + " found on type " + object42.target.getClass() + ", but already registered by type " + object3.target.getClass() + ".");
            }
            if ((class_ = this.handlersByType.get(class_)) == null || class_.isEmpty()) continue;
            class_ = class_.iterator();
            while (class_.hasNext()) {
                this.dispatchProducerResultToHandler((EventHandler)class_.next(), (EventProducer)object42);
            }
        }
        object42 = this.handlerFinder.findAllSubscribers(iterator);
        for (Class class_ : object42.keySet()) {
            iterator = object2 = this.handlersByType.get(class_);
            if (object2 == null) {
                object2 = new CopyOnWriteArraySet();
                iterator = iterator2 = (Set)this.handlersByType.putIfAbsent(class_, (Set<EventHandler>)object2);
                if (iterator2 == null) {
                    iterator = object2;
                }
            }
            iterator.addAll((Set)object42.get(class_));
        }
        iterator = object42.entrySet().iterator();
        block3 : while (iterator.hasNext()) {
            iterator2 = (Map.Entry)iterator.next();
            object2 = (Class)iterator2.getKey();
            if ((object2 = this.producersByType.get(object2)) == null || !object2.isValid()) continue;
            for (Object object42 : (Set)iterator2.getValue()) {
                if (!object2.isValid()) continue block3;
                if (!object42.isValid()) continue;
                this.dispatchProducerResultToHandler((EventHandler)object42, (EventProducer)object2);
            }
        }
    }

    public String toString() {
        return "[Bus \"" + this.identifier + "\"]";
    }

    public void unregister(Object object) {
        Object object3;
        Object object2;
        if (object == null) {
            throw new NullPointerException("Object to unregister must not be null.");
        }
        this.enforcer.enforce(this);
        for (Map.Entry entry : this.handlerFinder.findAllProducers(object).entrySet()) {
            object3 = entry.getKey();
            object2 = this.getProducerForEventType(object3);
            EventProducer iterator3 = entry.getValue();
            if (iterator3 == null || !iterator3.equals(object2)) {
                throw new IllegalArgumentException("Missing event producer for an annotated method. Is " + object.getClass() + " registered?");
            }
            this.producersByType.remove(object3).invalidate();
        }
        Iterator iterator2 = this.handlerFinder.findAllSubscribers(object).entrySet().iterator();
        while (iterator2.hasNext()) {
            object2 = (Map.Entry)iterator2.next();
            object3 = this.getHandlersForEventType((Class)object2.getKey());
            object2 = (Collection)object2.getValue();
            if (object3 == null || !object3.containsAll(object2)) {
                throw new IllegalArgumentException("Missing event handler for an annotated method. Is " + object.getClass() + " registered?");
            }
            Iterator iterator = object3.iterator();
            while (iterator.hasNext()) {
                EventHandler eventHandler = (EventHandler)iterator.next();
                if (!object2.contains(eventHandler)) continue;
                eventHandler.invalidate();
            }
            object3.removeAll(object2);
        }
    }

    static class EventWithHandler {
        final Object event;
        final EventHandler handler;

        public EventWithHandler(Object object, EventHandler eventHandler) {
            this.event = object;
            this.handler = eventHandler;
        }
    }

}

