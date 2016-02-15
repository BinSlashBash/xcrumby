/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.Primitives;
import com.google.gson.internal.Streams;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.google.gson.internal.bind.CollectionTypeAdapterFactory;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.internal.bind.JsonTreeWriter;
import com.google.gson.internal.bind.MapTypeAdapterFactory;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.internal.bind.SqlDateTypeAdapter;
import com.google.gson.internal.bind.TimeTypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class Gson {
    static final boolean DEFAULT_JSON_NON_EXECUTABLE = false;
    private static final String JSON_NON_EXECUTABLE_PREFIX = ")]}'\n";
    private final ThreadLocal<Map<TypeToken<?>, FutureTypeAdapter<?>>> calls = new ThreadLocal();
    private final ConstructorConstructor constructorConstructor;
    final JsonDeserializationContext deserializationContext;
    private final List<TypeAdapterFactory> factories;
    private final boolean generateNonExecutableJson;
    private final boolean htmlSafe;
    private final boolean prettyPrinting;
    final JsonSerializationContext serializationContext;
    private final boolean serializeNulls;
    private final Map<TypeToken<?>, TypeAdapter<?>> typeTokenCache = Collections.synchronizedMap(new HashMap());

    public Gson() {
        this(Excluder.DEFAULT, FieldNamingPolicy.IDENTITY, Collections.emptyMap(), false, false, false, true, false, false, LongSerializationPolicy.DEFAULT, Collections.emptyList());
    }

    Gson(Excluder excluder, FieldNamingStrategy fieldNamingStrategy, Map<Type, InstanceCreator<?>> object, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7, LongSerializationPolicy longSerializationPolicy, List<TypeAdapterFactory> list) {
        this.deserializationContext = new JsonDeserializationContext(){

            @Override
            public <T> T deserialize(JsonElement jsonElement, Type type) throws JsonParseException {
                return Gson.this.fromJson(jsonElement, type);
            }
        };
        this.serializationContext = new JsonSerializationContext(){

            @Override
            public JsonElement serialize(Object object) {
                return Gson.this.toJsonTree(object);
            }

            @Override
            public JsonElement serialize(Object object, Type type) {
                return Gson.this.toJsonTree(object, type);
            }
        };
        this.constructorConstructor = new ConstructorConstructor(object);
        this.serializeNulls = bl2;
        this.generateNonExecutableJson = bl4;
        this.htmlSafe = bl5;
        this.prettyPrinting = bl6;
        object = new ArrayList();
        object.add(TypeAdapters.JSON_ELEMENT_FACTORY);
        object.add(ObjectTypeAdapter.FACTORY);
        object.add(excluder);
        object.addAll(list);
        object.add(TypeAdapters.STRING_FACTORY);
        object.add(TypeAdapters.INTEGER_FACTORY);
        object.add(TypeAdapters.BOOLEAN_FACTORY);
        object.add(TypeAdapters.BYTE_FACTORY);
        object.add(TypeAdapters.SHORT_FACTORY);
        object.add(TypeAdapters.newFactory(Long.TYPE, Long.class, this.longAdapter(longSerializationPolicy)));
        object.add(TypeAdapters.newFactory(Double.TYPE, Double.class, this.doubleAdapter(bl7)));
        object.add(TypeAdapters.newFactory(Float.TYPE, Float.class, this.floatAdapter(bl7)));
        object.add(TypeAdapters.NUMBER_FACTORY);
        object.add(TypeAdapters.CHARACTER_FACTORY);
        object.add(TypeAdapters.STRING_BUILDER_FACTORY);
        object.add(TypeAdapters.STRING_BUFFER_FACTORY);
        object.add(TypeAdapters.newFactory(BigDecimal.class, TypeAdapters.BIG_DECIMAL));
        object.add(TypeAdapters.newFactory(BigInteger.class, TypeAdapters.BIG_INTEGER));
        object.add(TypeAdapters.URL_FACTORY);
        object.add(TypeAdapters.URI_FACTORY);
        object.add(TypeAdapters.UUID_FACTORY);
        object.add(TypeAdapters.LOCALE_FACTORY);
        object.add(TypeAdapters.INET_ADDRESS_FACTORY);
        object.add(TypeAdapters.BIT_SET_FACTORY);
        object.add(DateTypeAdapter.FACTORY);
        object.add(TypeAdapters.CALENDAR_FACTORY);
        object.add(TimeTypeAdapter.FACTORY);
        object.add(SqlDateTypeAdapter.FACTORY);
        object.add(TypeAdapters.TIMESTAMP_FACTORY);
        object.add(ArrayTypeAdapter.FACTORY);
        object.add(TypeAdapters.ENUM_FACTORY);
        object.add(TypeAdapters.CLASS_FACTORY);
        object.add(new CollectionTypeAdapterFactory(this.constructorConstructor));
        object.add(new MapTypeAdapterFactory(this.constructorConstructor, bl3));
        object.add(new ReflectiveTypeAdapterFactory(this.constructorConstructor, fieldNamingStrategy, excluder));
        this.factories = Collections.unmodifiableList(object);
    }

    private static void assertFullConsumption(Object object, JsonReader jsonReader) {
        if (object != null) {
            try {
                if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                    throw new JsonIOException("JSON document was not fully consumed.");
                }
            }
            catch (MalformedJsonException var0_1) {
                throw new JsonSyntaxException(var0_1);
            }
            catch (IOException var0_2) {
                throw new JsonIOException(var0_2);
            }
        }
    }

    private void checkValidFloatingPoint(double d2) {
        if (Double.isNaN(d2) || Double.isInfinite(d2)) {
            throw new IllegalArgumentException("" + d2 + " is not a valid double value as per JSON specification. To override this" + " behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
        }
    }

    private TypeAdapter<Number> doubleAdapter(boolean bl2) {
        if (bl2) {
            return TypeAdapters.DOUBLE;
        }
        return new TypeAdapter<Number>(){

            @Override
            public Double read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return jsonReader.nextDouble();
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                if (number == null) {
                    jsonWriter.nullValue();
                    return;
                }
                double d2 = number.doubleValue();
                Gson.this.checkValidFloatingPoint(d2);
                jsonWriter.value(number);
            }
        };
    }

    private TypeAdapter<Number> floatAdapter(boolean bl2) {
        if (bl2) {
            return TypeAdapters.FLOAT;
        }
        return new TypeAdapter<Number>(){

            @Override
            public Float read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return Float.valueOf((float)jsonReader.nextDouble());
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                if (number == null) {
                    jsonWriter.nullValue();
                    return;
                }
                float f2 = number.floatValue();
                Gson.this.checkValidFloatingPoint(f2);
                jsonWriter.value(number);
            }
        };
    }

    private TypeAdapter<Number> longAdapter(LongSerializationPolicy longSerializationPolicy) {
        if (longSerializationPolicy == LongSerializationPolicy.DEFAULT) {
            return TypeAdapters.LONG;
        }
        return new TypeAdapter<Number>(){

            @Override
            public Number read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return jsonReader.nextLong();
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                if (number == null) {
                    jsonWriter.nullValue();
                    return;
                }
                jsonWriter.value(number.toString());
            }
        };
    }

    private JsonWriter newJsonWriter(Writer closeable) throws IOException {
        if (this.generateNonExecutableJson) {
            closeable.write(")]}'\n");
        }
        closeable = new JsonWriter((Writer)closeable);
        if (this.prettyPrinting) {
            closeable.setIndent("  ");
        }
        closeable.setSerializeNulls(this.serializeNulls);
        return closeable;
    }

    public <T> T fromJson(JsonElement jsonElement, Class<T> class_) throws JsonSyntaxException {
        jsonElement = this.fromJson(jsonElement, (Type)class_);
        return Primitives.wrap(class_).cast(jsonElement);
    }

    public <T> T fromJson(JsonElement jsonElement, Type type) throws JsonSyntaxException {
        if (jsonElement == null) {
            return null;
        }
        return this.fromJson(new JsonTreeReader(jsonElement), type);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public <T> T fromJson(JsonReader jsonReader, Type type) throws JsonIOException, JsonSyntaxException {
        boolean bl2 = true;
        boolean bl3 = jsonReader.isLenient();
        jsonReader.setLenient(true);
        try {
            jsonReader.peek();
            bl2 = false;
            type = this.getAdapter(TypeToken.get(type)).read(jsonReader);
            return (T)type;
        }
        catch (EOFException var2_3) {
            if (!bl2) throw new JsonSyntaxException(var2_3);
            return null;
        }
        catch (IllegalStateException var2_5) {
            throw new JsonSyntaxException(var2_5);
        }
        catch (IOException var2_6) {
            throw new JsonSyntaxException(var2_6);
        }
        finally {
            jsonReader.setLenient(bl3);
        }
    }

    public <T> T fromJson(Reader closeable, Class<T> class_) throws JsonSyntaxException, JsonIOException {
        closeable = new JsonReader((Reader)closeable);
        T t2 = this.fromJson((JsonReader)closeable, class_);
        Gson.assertFullConsumption(t2, (JsonReader)closeable);
        return Primitives.wrap(class_).cast(t2);
    }

    public <T> T fromJson(Reader closeable, Type type) throws JsonIOException, JsonSyntaxException {
        closeable = new JsonReader((Reader)closeable);
        type = this.fromJson((JsonReader)closeable, type);
        Gson.assertFullConsumption(type, (JsonReader)closeable);
        return (T)type;
    }

    public <T> T fromJson(String string2, Class<T> class_) throws JsonSyntaxException {
        string2 = this.fromJson(string2, (Type)class_);
        return Primitives.wrap(class_).cast(string2);
    }

    public <T> T fromJson(String string2, Type type) throws JsonSyntaxException {
        if (string2 == null) {
            return null;
        }
        return this.fromJson((Reader)new StringReader(string2), type);
    }

    public <T> TypeAdapter<T> getAdapter(TypeToken<T> typeToken) {
        Object object = this.typeTokenCache.get(typeToken);
        if (object != null) {
            return object;
        }
        FutureTypeAdapter<T> futureTypeAdapter = this.calls.get();
        boolean bl2 = false;
        object = futureTypeAdapter;
        if (futureTypeAdapter == null) {
            object = new HashMap();
            this.calls.set(object);
            bl2 = true;
        }
        if ((futureTypeAdapter = (FutureTypeAdapter)object.get(typeToken)) != null) {
            return futureTypeAdapter;
        }
        try {
            futureTypeAdapter = new FutureTypeAdapter<T>();
            object.put(typeToken, futureTypeAdapter);
            Iterator<TypeAdapterFactory> iterator = this.factories.iterator();
            while (iterator.hasNext()) {
                TypeAdapter<T> typeAdapter = iterator.next().create(this, typeToken);
                if (typeAdapter == null) continue;
                futureTypeAdapter.setDelegate(typeAdapter);
                this.typeTokenCache.put(typeToken, typeAdapter);
                return typeAdapter;
            }
            throw new IllegalArgumentException("GSON cannot handle " + typeToken);
        }
        finally {
            object.remove(typeToken);
            if (bl2) {
                this.calls.remove();
            }
        }
    }

    public <T> TypeAdapter<T> getAdapter(Class<T> class_) {
        return this.getAdapter(TypeToken.get(class_));
    }

    public <T> TypeAdapter<T> getDelegateAdapter(TypeAdapterFactory typeAdapterFactory, TypeToken<T> typeToken) {
        boolean bl2 = false;
        for (TypeAdapterFactory typeAdapterFactory2 : this.factories) {
            if (!bl2) {
                if (typeAdapterFactory2 != typeAdapterFactory) continue;
                bl2 = true;
                continue;
            }
            TypeAdapter<T> typeAdapter = typeAdapterFactory2.create(this, typeToken);
            if (typeAdapter == null) continue;
            return typeAdapter;
        }
        throw new IllegalArgumentException("GSON cannot serialize " + typeToken);
    }

    public String toJson(JsonElement jsonElement) {
        StringWriter stringWriter = new StringWriter();
        this.toJson(jsonElement, (Appendable)stringWriter);
        return stringWriter.toString();
    }

    public String toJson(Object object) {
        if (object == null) {
            return this.toJson(JsonNull.INSTANCE);
        }
        return this.toJson(object, object.getClass());
    }

    public String toJson(Object object, Type type) {
        StringWriter stringWriter = new StringWriter();
        this.toJson(object, type, stringWriter);
        return stringWriter.toString();
    }

    public void toJson(JsonElement jsonElement, JsonWriter jsonWriter) throws JsonIOException {
        boolean bl2 = jsonWriter.isLenient();
        jsonWriter.setLenient(true);
        boolean bl3 = jsonWriter.isHtmlSafe();
        jsonWriter.setHtmlSafe(this.htmlSafe);
        boolean bl4 = jsonWriter.getSerializeNulls();
        jsonWriter.setSerializeNulls(this.serializeNulls);
        try {
            Streams.write(jsonElement, jsonWriter);
            return;
        }
        catch (IOException var1_2) {
            throw new JsonIOException(var1_2);
        }
        finally {
            jsonWriter.setLenient(bl2);
            jsonWriter.setHtmlSafe(bl3);
            jsonWriter.setSerializeNulls(bl4);
        }
    }

    public void toJson(JsonElement jsonElement, Appendable appendable) throws JsonIOException {
        try {
            this.toJson(jsonElement, this.newJsonWriter(Streams.writerForAppendable(appendable)));
            return;
        }
        catch (IOException var1_2) {
            throw new RuntimeException(var1_2);
        }
    }

    public void toJson(Object object, Appendable appendable) throws JsonIOException {
        if (object != null) {
            this.toJson(object, object.getClass(), appendable);
            return;
        }
        this.toJson((JsonElement)JsonNull.INSTANCE, appendable);
    }

    public void toJson(Object object, Type object2, JsonWriter jsonWriter) throws JsonIOException {
        object2 = this.getAdapter(TypeToken.get((Type)object2));
        boolean bl2 = jsonWriter.isLenient();
        jsonWriter.setLenient(true);
        boolean bl3 = jsonWriter.isHtmlSafe();
        jsonWriter.setHtmlSafe(this.htmlSafe);
        boolean bl4 = jsonWriter.getSerializeNulls();
        jsonWriter.setSerializeNulls(this.serializeNulls);
        try {
            object2.write(jsonWriter, object);
            return;
        }
        catch (IOException var1_2) {
            throw new JsonIOException(var1_2);
        }
        finally {
            jsonWriter.setLenient(bl2);
            jsonWriter.setHtmlSafe(bl3);
            jsonWriter.setSerializeNulls(bl4);
        }
    }

    public void toJson(Object object, Type type, Appendable appendable) throws JsonIOException {
        try {
            this.toJson(object, type, this.newJsonWriter(Streams.writerForAppendable(appendable)));
            return;
        }
        catch (IOException var1_2) {
            throw new JsonIOException(var1_2);
        }
    }

    public JsonElement toJsonTree(Object object) {
        if (object == null) {
            return JsonNull.INSTANCE;
        }
        return this.toJsonTree(object, object.getClass());
    }

    public JsonElement toJsonTree(Object object, Type type) {
        JsonTreeWriter jsonTreeWriter = new JsonTreeWriter();
        this.toJson(object, type, jsonTreeWriter);
        return jsonTreeWriter.get();
    }

    public String toString() {
        return "{serializeNulls:" + this.serializeNulls + "factories:" + this.factories + ",instanceCreators:" + this.constructorConstructor + "}";
    }

    static class FutureTypeAdapter<T>
    extends TypeAdapter<T> {
        private TypeAdapter<T> delegate;

        FutureTypeAdapter() {
        }

        @Override
        public T read(JsonReader jsonReader) throws IOException {
            if (this.delegate == null) {
                throw new IllegalStateException();
            }
            return this.delegate.read(jsonReader);
        }

        public void setDelegate(TypeAdapter<T> typeAdapter) {
            if (this.delegate != null) {
                throw new AssertionError();
            }
            this.delegate = typeAdapter;
        }

        @Override
        public void write(JsonWriter jsonWriter, T t2) throws IOException {
            if (this.delegate == null) {
                throw new IllegalStateException();
            }
            this.delegate.write(jsonWriter, t2);
        }
    }

}

