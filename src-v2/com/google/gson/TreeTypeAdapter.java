/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.$Gson$Preconditions;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;

final class TreeTypeAdapter<T>
extends TypeAdapter<T> {
    private TypeAdapter<T> delegate;
    private final JsonDeserializer<T> deserializer;
    private final Gson gson;
    private final JsonSerializer<T> serializer;
    private final TypeAdapterFactory skipPast;
    private final TypeToken<T> typeToken;

    private TreeTypeAdapter(JsonSerializer<T> jsonSerializer, JsonDeserializer<T> jsonDeserializer, Gson gson, TypeToken<T> typeToken, TypeAdapterFactory typeAdapterFactory) {
        this.serializer = jsonSerializer;
        this.deserializer = jsonDeserializer;
        this.gson = gson;
        this.typeToken = typeToken;
        this.skipPast = typeAdapterFactory;
    }

    private TypeAdapter<T> delegate() {
        TypeAdapter<T> typeAdapter = this.delegate;
        if (typeAdapter != null) {
            return typeAdapter;
        }
        this.delegate = typeAdapter = this.gson.getDelegateAdapter(this.skipPast, this.typeToken);
        return typeAdapter;
    }

    public static TypeAdapterFactory newFactory(TypeToken<?> typeToken, Object object) {
        return new SingleTypeFactory(object, typeToken, false, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeAdapterFactory newFactoryWithMatchRawType(TypeToken<?> typeToken, Object object) {
        boolean bl2;
        if (typeToken.getType() == typeToken.getRawType()) {
            bl2 = true;
            do {
                return new SingleTypeFactory(object, typeToken, bl2, null);
                break;
            } while (true);
        }
        bl2 = false;
        return new SingleTypeFactory(object, typeToken, bl2, null);
    }

    public static TypeAdapterFactory newTypeHierarchyFactory(Class<?> class_, Object object) {
        return new SingleTypeFactory(object, null, false, class_);
    }

    @Override
    public T read(JsonReader object) throws IOException {
        if (this.deserializer == null) {
            return this.delegate().read((JsonReader)object);
        }
        if ((object = Streams.parse((JsonReader)object)).isJsonNull()) {
            return null;
        }
        return this.deserializer.deserialize((JsonElement)object, this.typeToken.getType(), this.gson.deserializationContext);
    }

    @Override
    public void write(JsonWriter jsonWriter, T t2) throws IOException {
        if (this.serializer == null) {
            this.delegate().write(jsonWriter, t2);
            return;
        }
        if (t2 == null) {
            jsonWriter.nullValue();
            return;
        }
        Streams.write(this.serializer.serialize(t2, this.typeToken.getType(), this.gson.serializationContext), jsonWriter);
    }

    private static class SingleTypeFactory
    implements TypeAdapterFactory {
        private final JsonDeserializer<?> deserializer;
        private final TypeToken<?> exactType;
        private final Class<?> hierarchyType;
        private final boolean matchRawType;
        private final JsonSerializer<?> serializer;

        /*
         * Enabled aggressive block sorting
         */
        private SingleTypeFactory(Object object, TypeToken<?> typeToken, boolean bl2, Class<?> class_) {
            JsonSerializer jsonSerializer = object instanceof JsonSerializer ? (JsonSerializer)object : null;
            this.serializer = jsonSerializer;
            object = object instanceof JsonDeserializer ? (JsonDeserializer)object : null;
            this.deserializer = object;
            boolean bl3 = this.serializer != null || this.deserializer != null;
            $Gson$Preconditions.checkArgument(bl3);
            this.exactType = typeToken;
            this.matchRawType = bl2;
            this.hierarchyType = class_;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            if (this.exactType != null) {
                if (this.exactType.equals(typeToken) || this.matchRawType && this.exactType.getType() == typeToken.getRawType()) {
                    return new TreeTypeAdapter(this.serializer, this.deserializer, gson, typeToken, this);
                }
                return null;
            }
            boolean bl2 = this.hierarchyType.isAssignableFrom(typeToken.getRawType());
            if (bl2) {
                return new TreeTypeAdapter(this.serializer, this.deserializer, gson, typeToken, this);
            }
            return null;
        }
    }

}

