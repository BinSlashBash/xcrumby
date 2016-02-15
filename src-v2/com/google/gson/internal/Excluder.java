/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson.internal;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Excluder
implements TypeAdapterFactory,
Cloneable {
    public static final Excluder DEFAULT = new Excluder();
    private static final double IGNORE_VERSIONS = -1.0;
    private List<ExclusionStrategy> deserializationStrategies = Collections.emptyList();
    private int modifiers = 136;
    private boolean requireExpose;
    private List<ExclusionStrategy> serializationStrategies = Collections.emptyList();
    private boolean serializeInnerClasses = true;
    private double version = -1.0;

    private boolean isAnonymousOrLocal(Class<?> class_) {
        if (!Enum.class.isAssignableFrom(class_) && (class_.isAnonymousClass() || class_.isLocalClass())) {
            return true;
        }
        return false;
    }

    private boolean isInnerClass(Class<?> class_) {
        if (class_.isMemberClass() && !this.isStatic(class_)) {
            return true;
        }
        return false;
    }

    private boolean isStatic(Class<?> class_) {
        if ((class_.getModifiers() & 8) != 0) {
            return true;
        }
        return false;
    }

    private boolean isValidSince(Since since) {
        if (since != null && since.value() > this.version) {
            return false;
        }
        return true;
    }

    private boolean isValidUntil(Until until) {
        if (until != null && until.value() <= this.version) {
            return false;
        }
        return true;
    }

    private boolean isValidVersion(Since since, Until until) {
        if (this.isValidSince(since) && this.isValidUntil(until)) {
            return true;
        }
        return false;
    }

    protected Excluder clone() {
        try {
            Excluder excluder = (Excluder)super.clone();
            return excluder;
        }
        catch (CloneNotSupportedException var1_2) {
            throw new AssertionError();
        }
    }

    @Override
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
        Class<T> class_ = typeToken.getRawType();
        final boolean bl2 = this.excludeClass(class_, true);
        final boolean bl3 = this.excludeClass(class_, false);
        if (!bl2 && !bl3) {
            return null;
        }
        return new TypeAdapter<T>(){
            private TypeAdapter<T> delegate;

            private TypeAdapter<T> delegate() {
                TypeAdapter<T> typeAdapter = this.delegate;
                if (typeAdapter != null) {
                    return typeAdapter;
                }
                this.delegate = typeAdapter = gson.getDelegateAdapter(Excluder.this, typeToken);
                return typeAdapter;
            }

            @Override
            public T read(JsonReader jsonReader) throws IOException {
                if (bl3) {
                    jsonReader.skipValue();
                    return null;
                }
                return this.delegate().read(jsonReader);
            }

            @Override
            public void write(JsonWriter jsonWriter, T t2) throws IOException {
                if (bl2) {
                    jsonWriter.nullValue();
                    return;
                }
                this.delegate().write(jsonWriter, t2);
            }
        };
    }

    public Excluder disableInnerClassSerialization() {
        Excluder excluder = this.clone();
        excluder.serializeInnerClasses = false;
        return excluder;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean excludeClass(Class<?> class_, boolean bl2) {
        if (this.version != -1.0 && !this.isValidVersion((Since)class_.getAnnotation(Since.class), (Until)class_.getAnnotation(Until.class))) {
            return true;
        }
        if (!this.serializeInnerClasses && this.isInnerClass(class_)) {
            return true;
        }
        if (this.isAnonymousOrLocal(class_)) {
            return true;
        }
        List<ExclusionStrategy> list = bl2 ? this.serializationStrategies : this.deserializationStrategies;
        list = list.iterator();
        do {
            if (list.hasNext()) continue;
            return false;
        } while (!((ExclusionStrategy)list.next()).shouldSkipClass(class_));
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean excludeField(Field object, boolean bl2) {
        List<ExclusionStrategy> list;
        if ((this.modifiers & object.getModifiers()) != 0) {
            return true;
        }
        if (this.version != -1.0 && !this.isValidVersion((Since)object.getAnnotation(Since.class), (Until)object.getAnnotation(Until.class))) {
            return true;
        }
        if (object.isSynthetic()) {
            return true;
        }
        if (this.requireExpose && ((list = (Expose)object.getAnnotation(Expose.class)) == null || (bl2 ? !list.serialize() : !list.deserialize()))) {
            return true;
        }
        if (!this.serializeInnerClasses && this.isInnerClass(object.getType())) {
            return true;
        }
        if (this.isAnonymousOrLocal(object.getType())) {
            return true;
        }
        list = bl2 ? this.serializationStrategies : this.deserializationStrategies;
        if (!list.isEmpty()) {
            object = new FieldAttributes((Field)object);
            list = list.iterator();
            while (list.hasNext()) {
                if (!((ExclusionStrategy)list.next()).shouldSkipField((FieldAttributes)object)) continue;
                return true;
            }
        }
        return false;
    }

    public Excluder excludeFieldsWithoutExposeAnnotation() {
        Excluder excluder = this.clone();
        excluder.requireExpose = true;
        return excluder;
    }

    public Excluder withExclusionStrategy(ExclusionStrategy exclusionStrategy, boolean bl2, boolean bl3) {
        Excluder excluder = this.clone();
        if (bl2) {
            excluder.serializationStrategies = new ArrayList<ExclusionStrategy>(this.serializationStrategies);
            excluder.serializationStrategies.add(exclusionStrategy);
        }
        if (bl3) {
            excluder.deserializationStrategies = new ArrayList<ExclusionStrategy>(this.deserializationStrategies);
            excluder.deserializationStrategies.add(exclusionStrategy);
        }
        return excluder;
    }

    public /* varargs */ Excluder withModifiers(int ... arrn) {
        Excluder excluder = this.clone();
        excluder.modifiers = 0;
        for (int n2 : arrn) {
            excluder.modifiers |= n2;
        }
        return excluder;
    }

    public Excluder withVersion(double d2) {
        Excluder excluder = this.clone();
        excluder.version = d2;
        return excluder;
    }

}

