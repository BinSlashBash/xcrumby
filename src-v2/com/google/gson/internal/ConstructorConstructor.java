/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson.internal;

import com.google.gson.InstanceCreator;
import com.google.gson.JsonIOException;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.UnsafeAllocator;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public final class ConstructorConstructor {
    private final Map<Type, InstanceCreator<?>> instanceCreators;

    public ConstructorConstructor(Map<Type, InstanceCreator<?>> map) {
        this.instanceCreators = map;
    }

    private <T> ObjectConstructor<T> newDefaultConstructor(Class<? super T> object) {
        try {
            object = object.getDeclaredConstructor(new Class[0]);
            if (!object.isAccessible()) {
                object.setAccessible(true);
            }
            object = new ObjectConstructor<T>((Constructor)object){
                final /* synthetic */ Constructor val$constructor;

                @Override
                public T construct() {
                    Object t2;
                    try {
                        t2 = this.val$constructor.newInstance(null);
                    }
                    catch (InstantiationException var1_2) {
                        throw new RuntimeException("Failed to invoke " + this.val$constructor + " with no args", var1_2);
                    }
                    catch (InvocationTargetException var1_3) {
                        throw new RuntimeException("Failed to invoke " + this.val$constructor + " with no args", var1_3.getTargetException());
                    }
                    catch (IllegalAccessException var1_4) {
                        throw new AssertionError(var1_4);
                    }
                    return t2;
                }
            };
            return object;
        }
        catch (NoSuchMethodException var1_2) {
            return null;
        }
    }

    private <T> ObjectConstructor<T> newDefaultImplementationConstructor(final Type type, Class<? super T> class_) {
        if (Collection.class.isAssignableFrom(class_)) {
            if (SortedSet.class.isAssignableFrom(class_)) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        return (T)new TreeSet();
                    }
                };
            }
            if (EnumSet.class.isAssignableFrom(class_)) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        if (type instanceof ParameterizedType) {
                            Type type2 = ((ParameterizedType)type).getActualTypeArguments()[0];
                            if (type2 instanceof Class) {
                                return (T)EnumSet.noneOf((Class)type2);
                            }
                            throw new JsonIOException("Invalid EnumSet type: " + type.toString());
                        }
                        throw new JsonIOException("Invalid EnumSet type: " + type.toString());
                    }
                };
            }
            if (Set.class.isAssignableFrom(class_)) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        return (T)new LinkedHashSet();
                    }
                };
            }
            if (Queue.class.isAssignableFrom(class_)) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        return (T)new LinkedList();
                    }
                };
            }
            return new ObjectConstructor<T>(){

                @Override
                public T construct() {
                    return (T)new ArrayList();
                }
            };
        }
        if (Map.class.isAssignableFrom(class_)) {
            if (SortedMap.class.isAssignableFrom(class_)) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        return (T)new TreeMap();
                    }
                };
            }
            if (type instanceof ParameterizedType && !String.class.isAssignableFrom(TypeToken.get(((ParameterizedType)type).getActualTypeArguments()[0]).getRawType())) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        return (T)new LinkedHashMap();
                    }
                };
            }
            return new ObjectConstructor<T>(){

                @Override
                public T construct() {
                    return (T)new LinkedTreeMap();
                }
            };
        }
        return null;
    }

    private <T> ObjectConstructor<T> newUnsafeAllocator(final Type type, final Class<? super T> class_) {
        return new ObjectConstructor<T>(){
            private final UnsafeAllocator unsafeAllocator;

            @Override
            public T construct() {
                Object t2;
                try {
                    t2 = this.unsafeAllocator.newInstance(class_);
                }
                catch (Exception var1_2) {
                    throw new RuntimeException("Unable to invoke no-args constructor for " + type + ". " + "Register an InstanceCreator with Gson for this type may fix this problem.", var1_2);
                }
                return t2;
            }
        };
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public <T> ObjectConstructor<T> get(TypeToken<T> object) {
        ObjectConstructor objectConstructor;
        Type type = object.getType();
        Class class_ = object.getRawType();
        object = this.instanceCreators.get(type);
        if (object != null) {
            return new ObjectConstructor<T>((InstanceCreator)object, type){
                final /* synthetic */ Type val$type;
                final /* synthetic */ InstanceCreator val$typeCreator;

                @Override
                public T construct() {
                    return this.val$typeCreator.createInstance(this.val$type);
                }
            };
        }
        object = this.instanceCreators.get(class_);
        if (object != null) {
            return new ObjectConstructor<T>((InstanceCreator)object, type){
                final /* synthetic */ InstanceCreator val$rawTypeCreator;
                final /* synthetic */ Type val$type;

                @Override
                public T construct() {
                    return this.val$rawTypeCreator.createInstance(this.val$type);
                }
            };
        }
        object = objectConstructor = this.newDefaultConstructor(class_);
        if (objectConstructor != null) return object;
        object = this.newDefaultImplementationConstructor(type, class_);
        if (object == null) return this.newUnsafeAllocator(type, class_);
        return object;
    }

    public String toString() {
        return this.instanceCreators.toString();
    }

}

