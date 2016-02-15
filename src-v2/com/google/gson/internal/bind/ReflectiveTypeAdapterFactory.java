/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson.internal.bind;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Primitives;
import com.google.gson.internal.bind.TypeAdapterRuntimeTypeWrapper;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ReflectiveTypeAdapterFactory
implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;
    private final Excluder excluder;
    private final FieldNamingStrategy fieldNamingPolicy;

    public ReflectiveTypeAdapterFactory(ConstructorConstructor constructorConstructor, FieldNamingStrategy fieldNamingStrategy, Excluder excluder) {
        this.constructorConstructor = constructorConstructor;
        this.fieldNamingPolicy = fieldNamingStrategy;
        this.excluder = excluder;
    }

    private BoundField createBoundField(final Gson gson, final Field field, String string2, final TypeToken<?> typeToken, boolean bl2, boolean bl3) {
        return new BoundField(string2, bl2, bl3, Primitives.isPrimitive(typeToken.getRawType())){
            final TypeAdapter<?> typeAdapter;
            final /* synthetic */ boolean val$isPrimitive;

            @Override
            void read(JsonReader jsonReader, Object object) throws IOException, IllegalAccessException {
                if ((jsonReader = this.typeAdapter.read(jsonReader)) != null || !this.val$isPrimitive) {
                    field.set(object, jsonReader);
                }
            }

            @Override
            void write(JsonWriter jsonWriter, Object object) throws IOException, IllegalAccessException {
                object = field.get(object);
                new TypeAdapterRuntimeTypeWrapper(gson, this.typeAdapter, typeToken.getType()).write(jsonWriter, (Object)object);
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private Map<String, BoundField> getBoundFields(Gson gson, TypeToken<?> typeToken, Class<?> class_) {
        LinkedHashMap<String, BoundField> linkedHashMap = new LinkedHashMap<String, BoundField>();
        if (class_.isInterface()) {
            return linkedHashMap;
        }
        Type type = typeToken.getType();
        while (class_ != Object.class) {
            for (Field field : class_.getDeclaredFields()) {
                boolean bl2 = this.excludeField(field, true);
                boolean bl3 = this.excludeField(field, false);
                if (!bl2 && !bl3) continue;
                field.setAccessible(true);
                Type type2 = $Gson$Types.resolve(typeToken.getType(), class_, field.getGenericType());
                BoundField boundField = this.createBoundField(gson, field, this.getFieldName(field), TypeToken.get(type2), bl2, bl3);
                BoundField boundField2 = linkedHashMap.put(boundField.name, boundField);
                if (boundField2 == null) continue;
                throw new IllegalArgumentException(type + " declares multiple JSON fields named " + boundField2.name);
            }
            typeToken = TypeToken.get($Gson$Types.resolve(typeToken.getType(), class_, class_.getGenericSuperclass()));
            class_ = typeToken.getRawType();
        }
        return linkedHashMap;
    }

    private String getFieldName(Field field) {
        SerializedName serializedName = (SerializedName)field.getAnnotation(SerializedName.class);
        if (serializedName == null) {
            return this.fieldNamingPolicy.translateName(field);
        }
        return serializedName.value();
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Class<T> class_ = typeToken.getRawType();
        if (!Object.class.isAssignableFrom(class_)) {
            return null;
        }
        return new Adapter(this.constructorConstructor.get(typeToken), this.getBoundFields(gson, typeToken, class_));
    }

    public boolean excludeField(Field field, boolean bl2) {
        if (!this.excluder.excludeClass(field.getType(), bl2) && !this.excluder.excludeField(field, bl2)) {
            return true;
        }
        return false;
    }

    public static final class Adapter<T>
    extends TypeAdapter<T> {
        private final Map<String, BoundField> boundFields;
        private final ObjectConstructor<T> constructor;

        private Adapter(ObjectConstructor<T> objectConstructor, Map<String, BoundField> map) {
            this.constructor = objectConstructor;
            this.boundFields = map;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public T read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            T t2 = this.constructor.construct();
            try {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    Object object = jsonReader.nextName();
                    if ((object = this.boundFields.get(object)) == null || !object.deserialized) {
                        jsonReader.skipValue();
                        continue;
                    }
                    object.read(jsonReader, t2);
                }
            }
            catch (IllegalStateException var1_2) {
                throw new JsonSyntaxException(var1_2);
            }
            catch (IllegalAccessException var1_3) {
                throw new AssertionError(var1_3);
            }
            jsonReader.endObject();
            return t2;
        }

        @Override
        public void write(JsonWriter jsonWriter, T t2) throws IOException {
            if (t2 == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.beginObject();
            try {
                for (BoundField boundField : this.boundFields.values()) {
                    if (!boundField.serialized) continue;
                    jsonWriter.name(boundField.name);
                    boundField.write(jsonWriter, t2);
                }
            }
            catch (IllegalAccessException var1_2) {
                throw new AssertionError();
            }
            jsonWriter.endObject();
        }
    }

    static abstract class BoundField {
        final boolean deserialized;
        final String name;
        final boolean serialized;

        protected BoundField(String string2, boolean bl2, boolean bl3) {
            this.name = string2;
            this.serialized = bl2;
            this.deserialized = bl3;
        }

        abstract void read(JsonReader var1, Object var2) throws IOException, IllegalAccessException;

        abstract void write(JsonWriter var1, Object var2) throws IOException, IllegalAccessException;
    }

}

