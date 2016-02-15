/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.bind.TypeAdapterRuntimeTypeWrapper;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;

public final class CollectionTypeAdapterFactory
implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;

    public CollectionTypeAdapterFactory(ConstructorConstructor constructorConstructor) {
        this.constructorConstructor = constructorConstructor;
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Type type = typeToken.getType();
        Class<T> class_ = typeToken.getRawType();
        if (!Collection.class.isAssignableFrom(class_)) {
            return null;
        }
        type = $Gson$Types.getCollectionElementType(type, class_);
        return new Adapter(gson, type, gson.getAdapter(TypeToken.get(type)), this.constructorConstructor.get(typeToken));
    }

    private static final class Adapter<E>
    extends TypeAdapter<Collection<E>> {
        private final ObjectConstructor<? extends Collection<E>> constructor;
        private final TypeAdapter<E> elementTypeAdapter;

        public Adapter(Gson gson, Type type, TypeAdapter<E> typeAdapter, ObjectConstructor<? extends Collection<E>> objectConstructor) {
            this.elementTypeAdapter = new TypeAdapterRuntimeTypeWrapper<E>(gson, typeAdapter, type);
            this.constructor = objectConstructor;
        }

        @Override
        public Collection<E> read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            Collection<E> collection = this.constructor.construct();
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                collection.add(this.elementTypeAdapter.read(jsonReader));
            }
            jsonReader.endArray();
            return collection;
        }

        @Override
        public void write(JsonWriter jsonWriter, Collection<E> object) throws IOException {
            if (object == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.beginArray();
            object = object.iterator();
            while (object.hasNext()) {
                Object e2 = object.next();
                this.elementTypeAdapter.write(jsonWriter, e2);
            }
            jsonWriter.endArray();
        }
    }

}

