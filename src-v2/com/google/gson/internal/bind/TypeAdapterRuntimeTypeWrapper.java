/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

final class TypeAdapterRuntimeTypeWrapper<T>
extends TypeAdapter<T> {
    private final Gson context;
    private final TypeAdapter<T> delegate;
    private final Type type;

    TypeAdapterRuntimeTypeWrapper(Gson gson, TypeAdapter<T> typeAdapter, Type type) {
        this.context = gson;
        this.delegate = typeAdapter;
        this.type = type;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Type getRuntimeTypeIfMoreSpecific(Type class_, Object object) {
        Class class_2 = class_;
        if (object == null) return class_2;
        if (class_ == Object.class) return object.getClass();
        if (class_ instanceof TypeVariable) return object.getClass();
        class_2 = class_;
        if (!(class_ instanceof Class)) return class_2;
        return object.getClass();
    }

    @Override
    public T read(JsonReader jsonReader) throws IOException {
        return this.delegate.read(jsonReader);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void write(JsonWriter jsonWriter, T t2) throws IOException {
        void var3_5;
        TypeAdapter typeAdapter;
        TypeAdapter<T> typeAdapter2 = this.delegate;
        Type type = this.getRuntimeTypeIfMoreSpecific(this.type, t2);
        if (type != this.type && (typeAdapter = this.context.getAdapter(TypeToken.get(type))) instanceof ReflectiveTypeAdapterFactory.Adapter && !(this.delegate instanceof ReflectiveTypeAdapterFactory.Adapter)) {
            TypeAdapter<T> typeAdapter3 = this.delegate;
        }
        var3_5.write(jsonWriter, t2);
    }
}

