/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.internal.bind.TypeAdapterRuntimeTypeWrapper;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public final class ArrayTypeAdapter<E>
extends TypeAdapter<Object> {
    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory(){

        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> object) {
            if (!((object = object.getType()) instanceof GenericArrayType || object instanceof Class && ((Class)object).isArray())) {
                return null;
            }
            object = $Gson$Types.getArrayComponentType((Type)object);
            return new ArrayTypeAdapter(gson, gson.getAdapter(TypeToken.get((Type)object)), $Gson$Types.getRawType((Type)object));
        }
    };
    private final Class<E> componentType;
    private final TypeAdapter<E> componentTypeAdapter;

    public ArrayTypeAdapter(Gson gson, TypeAdapter<E> typeAdapter, Class<E> class_) {
        this.componentTypeAdapter = new TypeAdapterRuntimeTypeWrapper<E>(gson, typeAdapter, class_);
        this.componentType = class_;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Object read(JsonReader object) throws IOException {
        if (object.peek() == JsonToken.NULL) {
            object.nextNull();
            return null;
        }
        ArrayList<E> arrayList = new ArrayList<E>();
        object.beginArray();
        while (object.hasNext()) {
            arrayList.add(this.componentTypeAdapter.read((JsonReader)object));
        }
        object.endArray();
        Object object2 = Array.newInstance(this.componentType, arrayList.size());
        int n2 = 0;
        do {
            object = object2;
            if (n2 >= arrayList.size()) return object;
            Array.set(object2, n2, arrayList.get(n2));
            ++n2;
        } while (true);
    }

    @Override
    public void write(JsonWriter jsonWriter, Object object) throws IOException {
        if (object == null) {
            jsonWriter.nullValue();
            return;
        }
        jsonWriter.beginArray();
        int n2 = Array.getLength(object);
        for (int i2 = 0; i2 < n2; ++i2) {
            Object object2 = Array.get(object, i2);
            this.componentTypeAdapter.write(jsonWriter, (Object)object2);
        }
        jsonWriter.endArray();
    }

}

