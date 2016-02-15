/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;

public final class TypeAdapters {
    public static final TypeAdapter<BigDecimal> BIG_DECIMAL;
    public static final TypeAdapter<BigInteger> BIG_INTEGER;
    public static final TypeAdapter<BitSet> BIT_SET;
    public static final TypeAdapterFactory BIT_SET_FACTORY;
    public static final TypeAdapter<Boolean> BOOLEAN;
    public static final TypeAdapter<Boolean> BOOLEAN_AS_STRING;
    public static final TypeAdapterFactory BOOLEAN_FACTORY;
    public static final TypeAdapter<Number> BYTE;
    public static final TypeAdapterFactory BYTE_FACTORY;
    public static final TypeAdapter<Calendar> CALENDAR;
    public static final TypeAdapterFactory CALENDAR_FACTORY;
    public static final TypeAdapter<Character> CHARACTER;
    public static final TypeAdapterFactory CHARACTER_FACTORY;
    public static final TypeAdapter<Class> CLASS;
    public static final TypeAdapterFactory CLASS_FACTORY;
    public static final TypeAdapter<Number> DOUBLE;
    public static final TypeAdapterFactory ENUM_FACTORY;
    public static final TypeAdapter<Number> FLOAT;
    public static final TypeAdapter<InetAddress> INET_ADDRESS;
    public static final TypeAdapterFactory INET_ADDRESS_FACTORY;
    public static final TypeAdapter<Number> INTEGER;
    public static final TypeAdapterFactory INTEGER_FACTORY;
    public static final TypeAdapter<JsonElement> JSON_ELEMENT;
    public static final TypeAdapterFactory JSON_ELEMENT_FACTORY;
    public static final TypeAdapter<Locale> LOCALE;
    public static final TypeAdapterFactory LOCALE_FACTORY;
    public static final TypeAdapter<Number> LONG;
    public static final TypeAdapter<Number> NUMBER;
    public static final TypeAdapterFactory NUMBER_FACTORY;
    public static final TypeAdapter<Number> SHORT;
    public static final TypeAdapterFactory SHORT_FACTORY;
    public static final TypeAdapter<String> STRING;
    public static final TypeAdapter<StringBuffer> STRING_BUFFER;
    public static final TypeAdapterFactory STRING_BUFFER_FACTORY;
    public static final TypeAdapter<StringBuilder> STRING_BUILDER;
    public static final TypeAdapterFactory STRING_BUILDER_FACTORY;
    public static final TypeAdapterFactory STRING_FACTORY;
    public static final TypeAdapterFactory TIMESTAMP_FACTORY;
    public static final TypeAdapter<URI> URI;
    public static final TypeAdapterFactory URI_FACTORY;
    public static final TypeAdapter<URL> URL;
    public static final TypeAdapterFactory URL_FACTORY;
    public static final TypeAdapter<UUID> UUID;
    public static final TypeAdapterFactory UUID_FACTORY;

    static {
        CLASS = new TypeAdapter<Class>(){

            @Override
            public Class read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
            }

            @Override
            public void write(JsonWriter jsonWriter, Class class_) throws IOException {
                if (class_ == null) {
                    jsonWriter.nullValue();
                    return;
                }
                throw new UnsupportedOperationException("Attempted to serialize java.lang.Class: " + class_.getName() + ". Forgot to register a type adapter?");
            }
        };
        CLASS_FACTORY = TypeAdapters.newFactory(Class.class, CLASS);
        BIT_SET = new TypeAdapter<BitSet>(){

            /*
             * Loose catch block
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public BitSet read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                BitSet bitSet = new BitSet();
                jsonReader.beginArray();
                int n2 = 0;
                Object object = jsonReader.peek();
                do {
                    boolean bl2;
                    if (object == JsonToken.END_ARRAY) {
                        jsonReader.endArray();
                        return bitSet;
                    }
                    switch (.$SwitchMap$com$google$gson$stream$JsonToken[object.ordinal()]) {
                        default: {
                            throw new JsonSyntaxException("Invalid bitset value type: " + object);
                        }
                        case 1: {
                            if (jsonReader.nextInt() != 0) {
                                bl2 = true;
                                break;
                            }
                            bl2 = false;
                            break;
                        }
                        case 2: {
                            bl2 = jsonReader.nextBoolean();
                            break;
                        }
                        case 3: {
                            object = jsonReader.nextString();
                            int n3 = Integer.parseInt((String)object);
                            bl2 = n3 != 0;
                        }
                    }
                    if (bl2) {
                        bitSet.set(n2);
                    }
                    ++n2;
                    object = jsonReader.peek();
                } while (true);
                catch (NumberFormatException numberFormatException) {
                    throw new JsonSyntaxException("Error: Expecting: bitset number value (1, 0), Found: " + (String)object);
                }
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void write(JsonWriter jsonWriter, BitSet bitSet) throws IOException {
                if (bitSet == null) {
                    jsonWriter.nullValue();
                    return;
                }
                jsonWriter.beginArray();
                int n2 = 0;
                do {
                    if (n2 >= bitSet.length()) {
                        jsonWriter.endArray();
                        return;
                    }
                    boolean bl2 = bitSet.get(n2);
                    jsonWriter.value((long)bl2 ? 1 : 0);
                    ++n2;
                } while (true);
            }
        };
        BIT_SET_FACTORY = TypeAdapters.newFactory(BitSet.class, BIT_SET);
        BOOLEAN = new TypeAdapter<Boolean>(){

            @Override
            public Boolean read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                if (jsonReader.peek() == JsonToken.STRING) {
                    return Boolean.parseBoolean(jsonReader.nextString());
                }
                return jsonReader.nextBoolean();
            }

            @Override
            public void write(JsonWriter jsonWriter, Boolean bl2) throws IOException {
                if (bl2 == null) {
                    jsonWriter.nullValue();
                    return;
                }
                jsonWriter.value(bl2);
            }
        };
        BOOLEAN_AS_STRING = new TypeAdapter<Boolean>(){

            @Override
            public Boolean read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return Boolean.valueOf(jsonReader.nextString());
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void write(JsonWriter jsonWriter, Boolean object) throws IOException {
                object = object == null ? "null" : object.toString();
                jsonWriter.value((String)object);
            }
        };
        BOOLEAN_FACTORY = TypeAdapters.newFactory(Boolean.TYPE, Boolean.class, BOOLEAN);
        BYTE = new TypeAdapter<Number>(){

            @Override
            public Number read(JsonReader jsonReader) throws IOException {
                byte by2;
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                try {
                    by2 = (byte)jsonReader.nextInt();
                }
                catch (NumberFormatException var1_2) {
                    throw new JsonSyntaxException(var1_2);
                }
                return Byte.valueOf(by2);
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                jsonWriter.value(number);
            }
        };
        BYTE_FACTORY = TypeAdapters.newFactory(Byte.TYPE, Byte.class, BYTE);
        SHORT = new TypeAdapter<Number>(){

            @Override
            public Number read(JsonReader jsonReader) throws IOException {
                short s2;
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                try {
                    s2 = (short)jsonReader.nextInt();
                }
                catch (NumberFormatException var1_2) {
                    throw new JsonSyntaxException(var1_2);
                }
                return s2;
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                jsonWriter.value(number);
            }
        };
        SHORT_FACTORY = TypeAdapters.newFactory(Short.TYPE, Short.class, SHORT);
        INTEGER = new TypeAdapter<Number>(){

            @Override
            public Number read(JsonReader jsonReader) throws IOException {
                int n2;
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                try {
                    n2 = jsonReader.nextInt();
                }
                catch (NumberFormatException var1_2) {
                    throw new JsonSyntaxException(var1_2);
                }
                return n2;
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                jsonWriter.value(number);
            }
        };
        INTEGER_FACTORY = TypeAdapters.newFactory(Integer.TYPE, Integer.class, INTEGER);
        LONG = new TypeAdapter<Number>(){

            @Override
            public Number read(JsonReader jsonReader) throws IOException {
                long l2;
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                try {
                    l2 = jsonReader.nextLong();
                }
                catch (NumberFormatException var1_2) {
                    throw new JsonSyntaxException(var1_2);
                }
                return l2;
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                jsonWriter.value(number);
            }
        };
        FLOAT = new TypeAdapter<Number>(){

            @Override
            public Number read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return Float.valueOf((float)jsonReader.nextDouble());
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                jsonWriter.value(number);
            }
        };
        DOUBLE = new TypeAdapter<Number>(){

            @Override
            public Number read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return jsonReader.nextDouble();
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                jsonWriter.value(number);
            }
        };
        NUMBER = new TypeAdapter<Number>(){

            @Override
            public Number read(JsonReader jsonReader) throws IOException {
                JsonToken jsonToken = jsonReader.peek();
                switch (.$SwitchMap$com$google$gson$stream$JsonToken[jsonToken.ordinal()]) {
                    default: {
                        throw new JsonSyntaxException("Expecting number, got: " + (Object)((Object)jsonToken));
                    }
                    case 4: {
                        jsonReader.nextNull();
                        return null;
                    }
                    case 1: 
                }
                return new LazilyParsedNumber(jsonReader.nextString());
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                jsonWriter.value(number);
            }
        };
        NUMBER_FACTORY = TypeAdapters.newFactory(Number.class, NUMBER);
        CHARACTER = new TypeAdapter<Character>(){

            @Override
            public Character read(JsonReader object) throws IOException {
                if (object.peek() == JsonToken.NULL) {
                    object.nextNull();
                    return null;
                }
                if ((object = object.nextString()).length() != 1) {
                    throw new JsonSyntaxException("Expecting character, got: " + (String)object);
                }
                return Character.valueOf(object.charAt(0));
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void write(JsonWriter jsonWriter, Character object) throws IOException {
                object = object == null ? null : String.valueOf(object);
                jsonWriter.value((String)object);
            }
        };
        CHARACTER_FACTORY = TypeAdapters.newFactory(Character.TYPE, Character.class, CHARACTER);
        STRING = new TypeAdapter<String>(){

            @Override
            public String read(JsonReader jsonReader) throws IOException {
                JsonToken jsonToken = jsonReader.peek();
                if (jsonToken == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                if (jsonToken == JsonToken.BOOLEAN) {
                    return Boolean.toString(jsonReader.nextBoolean());
                }
                return jsonReader.nextString();
            }

            @Override
            public void write(JsonWriter jsonWriter, String string2) throws IOException {
                jsonWriter.value(string2);
            }
        };
        BIG_DECIMAL = new TypeAdapter<BigDecimal>(){

            @Override
            public BigDecimal read(JsonReader object) throws IOException {
                if (object.peek() == JsonToken.NULL) {
                    object.nextNull();
                    return null;
                }
                try {
                    object = new BigDecimal(object.nextString());
                    return object;
                }
                catch (NumberFormatException var1_2) {
                    throw new JsonSyntaxException(var1_2);
                }
            }

            @Override
            public void write(JsonWriter jsonWriter, BigDecimal bigDecimal) throws IOException {
                jsonWriter.value(bigDecimal);
            }
        };
        BIG_INTEGER = new TypeAdapter<BigInteger>(){

            @Override
            public BigInteger read(JsonReader object) throws IOException {
                if (object.peek() == JsonToken.NULL) {
                    object.nextNull();
                    return null;
                }
                try {
                    object = new BigInteger(object.nextString());
                    return object;
                }
                catch (NumberFormatException var1_2) {
                    throw new JsonSyntaxException(var1_2);
                }
            }

            @Override
            public void write(JsonWriter jsonWriter, BigInteger bigInteger) throws IOException {
                jsonWriter.value(bigInteger);
            }
        };
        STRING_FACTORY = TypeAdapters.newFactory(String.class, STRING);
        STRING_BUILDER = new TypeAdapter<StringBuilder>(){

            @Override
            public StringBuilder read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return new StringBuilder(jsonReader.nextString());
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void write(JsonWriter jsonWriter, StringBuilder stringBuilder) throws IOException {
                void var2_4;
                if (stringBuilder == null) {
                    Object var2_3 = null;
                } else {
                    String string2 = stringBuilder.toString();
                }
                jsonWriter.value((String)var2_4);
            }
        };
        STRING_BUILDER_FACTORY = TypeAdapters.newFactory(StringBuilder.class, STRING_BUILDER);
        STRING_BUFFER = new TypeAdapter<StringBuffer>(){

            @Override
            public StringBuffer read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return new StringBuffer(jsonReader.nextString());
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void write(JsonWriter jsonWriter, StringBuffer stringBuffer) throws IOException {
                void var2_4;
                if (stringBuffer == null) {
                    Object var2_3 = null;
                } else {
                    String string2 = stringBuffer.toString();
                }
                jsonWriter.value((String)var2_4);
            }
        };
        STRING_BUFFER_FACTORY = TypeAdapters.newFactory(StringBuffer.class, STRING_BUFFER);
        URL = new TypeAdapter<URL>(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public URL read(JsonReader object) throws IOException {
                if (object.peek() == JsonToken.NULL) {
                    object.nextNull();
                    return null;
                } else {
                    if ("null".equals(object = object.nextString())) return null;
                    return new URL((String)object);
                }
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void write(JsonWriter jsonWriter, URL object) throws IOException {
                object = object == null ? null : object.toExternalForm();
                jsonWriter.value((String)object);
            }
        };
        URL_FACTORY = TypeAdapters.newFactory(URL.class, URL);
        URI = new TypeAdapter<URI>(){

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Override
            public URI read(JsonReader object) throws IOException {
                if (object.peek() == JsonToken.NULL) {
                    object.nextNull();
                    return null;
                }
                try {
                    if ("null".equals(object = object.nextString())) return null;
                    return new URI((String)object);
                }
                catch (URISyntaxException var1_2) {
                    throw new JsonIOException(var1_2);
                }
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void write(JsonWriter jsonWriter, URI object) throws IOException {
                object = object == null ? null : object.toASCIIString();
                jsonWriter.value((String)object);
            }
        };
        URI_FACTORY = TypeAdapters.newFactory(URI.class, URI);
        INET_ADDRESS = new TypeAdapter<InetAddress>(){

            @Override
            public InetAddress read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return InetAddress.getByName(jsonReader.nextString());
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void write(JsonWriter jsonWriter, InetAddress object) throws IOException {
                object = object == null ? null : object.getHostAddress();
                jsonWriter.value((String)object);
            }
        };
        INET_ADDRESS_FACTORY = TypeAdapters.newTypeHierarchyFactory(InetAddress.class, INET_ADDRESS);
        UUID = new TypeAdapter<UUID>(){

            @Override
            public UUID read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return UUID.fromString(jsonReader.nextString());
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void write(JsonWriter jsonWriter, UUID object) throws IOException {
                object = object == null ? null : object.toString();
                jsonWriter.value((String)object);
            }
        };
        UUID_FACTORY = TypeAdapters.newFactory(UUID.class, UUID);
        TIMESTAMP_FACTORY = new TypeAdapterFactory(){

            @Override
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
                if (typeToken.getRawType() != Timestamp.class) {
                    return null;
                }
                return new TypeAdapter<Timestamp>(gson.getAdapter(Date.class)){
                    final /* synthetic */ TypeAdapter val$dateTypeAdapter;

                    @Override
                    public Timestamp read(JsonReader object) throws IOException {
                        if ((object = (Date)this.val$dateTypeAdapter.read((JsonReader)object)) != null) {
                            return new Timestamp(object.getTime());
                        }
                        return null;
                    }

                    @Override
                    public void write(JsonWriter jsonWriter, Timestamp timestamp) throws IOException {
                        this.val$dateTypeAdapter.write(jsonWriter, timestamp);
                    }
                };
            }

        };
        CALENDAR = new TypeAdapter<Calendar>(){
            private static final String DAY_OF_MONTH = "dayOfMonth";
            private static final String HOUR_OF_DAY = "hourOfDay";
            private static final String MINUTE = "minute";
            private static final String MONTH = "month";
            private static final String SECOND = "second";
            private static final String YEAR = "year";

            @Override
            public Calendar read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                jsonReader.beginObject();
                int n2 = 0;
                int n3 = 0;
                int n4 = 0;
                int n5 = 0;
                int n6 = 0;
                int n7 = 0;
                while (jsonReader.peek() != JsonToken.END_OBJECT) {
                    String string2 = jsonReader.nextName();
                    int n8 = jsonReader.nextInt();
                    if ("year".equals(string2)) {
                        n2 = n8;
                        continue;
                    }
                    if ("month".equals(string2)) {
                        n3 = n8;
                        continue;
                    }
                    if ("dayOfMonth".equals(string2)) {
                        n4 = n8;
                        continue;
                    }
                    if ("hourOfDay".equals(string2)) {
                        n5 = n8;
                        continue;
                    }
                    if ("minute".equals(string2)) {
                        n6 = n8;
                        continue;
                    }
                    if (!"second".equals(string2)) continue;
                    n7 = n8;
                }
                jsonReader.endObject();
                return new GregorianCalendar(n2, n3, n4, n5, n6, n7);
            }

            @Override
            public void write(JsonWriter jsonWriter, Calendar calendar) throws IOException {
                if (calendar == null) {
                    jsonWriter.nullValue();
                    return;
                }
                jsonWriter.beginObject();
                jsonWriter.name("year");
                jsonWriter.value(calendar.get(1));
                jsonWriter.name("month");
                jsonWriter.value(calendar.get(2));
                jsonWriter.name("dayOfMonth");
                jsonWriter.value(calendar.get(5));
                jsonWriter.name("hourOfDay");
                jsonWriter.value(calendar.get(11));
                jsonWriter.name("minute");
                jsonWriter.value(calendar.get(12));
                jsonWriter.name("second");
                jsonWriter.value(calendar.get(13));
                jsonWriter.endObject();
            }
        };
        CALENDAR_FACTORY = TypeAdapters.newFactoryForMultipleTypes(Calendar.class, GregorianCalendar.class, CALENDAR);
        LOCALE = new TypeAdapter<Locale>(){

            @Override
            public Locale read(JsonReader object) throws IOException {
                if (object.peek() == JsonToken.NULL) {
                    object.nextNull();
                    return null;
                }
                StringTokenizer stringTokenizer = new StringTokenizer(object.nextString(), "_");
                object = null;
                String string2 = null;
                String string3 = null;
                if (stringTokenizer.hasMoreElements()) {
                    object = stringTokenizer.nextToken();
                }
                if (stringTokenizer.hasMoreElements()) {
                    string2 = stringTokenizer.nextToken();
                }
                if (stringTokenizer.hasMoreElements()) {
                    string3 = stringTokenizer.nextToken();
                }
                if (string2 == null && string3 == null) {
                    return new Locale((String)object);
                }
                if (string3 == null) {
                    return new Locale((String)object, string2);
                }
                return new Locale((String)object, string2, string3);
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void write(JsonWriter jsonWriter, Locale object) throws IOException {
                object = object == null ? null : object.toString();
                jsonWriter.value((String)object);
            }
        };
        LOCALE_FACTORY = TypeAdapters.newFactory(Locale.class, LOCALE);
        JSON_ELEMENT = new TypeAdapter<JsonElement>(){

            @Override
            public JsonElement read(JsonReader jsonReader) throws IOException {
                switch (.$SwitchMap$com$google$gson$stream$JsonToken[jsonReader.peek().ordinal()]) {
                    default: {
                        throw new IllegalArgumentException();
                    }
                    case 3: {
                        return new JsonPrimitive(jsonReader.nextString());
                    }
                    case 1: {
                        return new JsonPrimitive(new LazilyParsedNumber(jsonReader.nextString()));
                    }
                    case 2: {
                        return new JsonPrimitive(jsonReader.nextBoolean());
                    }
                    case 4: {
                        jsonReader.nextNull();
                        return JsonNull.INSTANCE;
                    }
                    case 5: {
                        JsonArray jsonArray = new JsonArray();
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            jsonArray.add(this.read(jsonReader));
                        }
                        jsonReader.endArray();
                        return jsonArray;
                    }
                    case 6: 
                }
                JsonObject jsonObject = new JsonObject();
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    jsonObject.add(jsonReader.nextName(), this.read(jsonReader));
                }
                jsonReader.endObject();
                return jsonObject;
            }

            @Override
            public void write(JsonWriter jsonWriter, JsonElement iterator) throws IOException {
                if (iterator == null || iterator.isJsonNull()) {
                    jsonWriter.nullValue();
                    return;
                }
                if (iterator.isJsonPrimitive()) {
                    if ((iterator = iterator.getAsJsonPrimitive()).isNumber()) {
                        jsonWriter.value(iterator.getAsNumber());
                        return;
                    }
                    if (iterator.isBoolean()) {
                        jsonWriter.value(iterator.getAsBoolean());
                        return;
                    }
                    jsonWriter.value(iterator.getAsString());
                    return;
                }
                if (iterator.isJsonArray()) {
                    jsonWriter.beginArray();
                    iterator = iterator.getAsJsonArray().iterator();
                    while (iterator.hasNext()) {
                        this.write(jsonWriter, (JsonElement)((Object)iterator.next()));
                    }
                    jsonWriter.endArray();
                    return;
                }
                if (iterator.isJsonObject()) {
                    jsonWriter.beginObject();
                    for (Map.Entry<String, JsonElement> entry : iterator.getAsJsonObject().entrySet()) {
                        jsonWriter.name(entry.getKey());
                        this.write(jsonWriter, entry.getValue());
                    }
                    jsonWriter.endObject();
                    return;
                }
                throw new IllegalArgumentException("Couldn't write " + iterator.getClass());
            }
        };
        JSON_ELEMENT_FACTORY = TypeAdapters.newTypeHierarchyFactory(JsonElement.class, JSON_ELEMENT);
        ENUM_FACTORY = TypeAdapters.newEnumTypeHierarchyFactory();
    }

    private TypeAdapters() {
    }

    public static TypeAdapterFactory newEnumTypeHierarchyFactory() {
        return new TypeAdapterFactory(){

            @Override
            public <T> TypeAdapter<T> create(Gson class_, TypeToken<T> object) {
                if (!Enum.class.isAssignableFrom(object = object.getRawType()) || object == Enum.class) {
                    return null;
                }
                class_ = object;
                if (!object.isEnum()) {
                    class_ = object.getSuperclass();
                }
                return new EnumTypeAdapter(class_);
            }
        };
    }

    public static <TT> TypeAdapterFactory newFactory(final TypeToken<TT> typeToken, final TypeAdapter<TT> typeAdapter) {
        return new TypeAdapterFactory(){

            @Override
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken2) {
                if (typeToken2.equals(typeToken)) {
                    return typeAdapter;
                }
                return null;
            }
        };
    }

    public static <TT> TypeAdapterFactory newFactory(final Class<TT> class_, final TypeAdapter<TT> typeAdapter) {
        return new TypeAdapterFactory(){

            @Override
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
                if (typeToken.getRawType() == class_) {
                    return typeAdapter;
                }
                return null;
            }

            public String toString() {
                return "Factory[type=" + class_.getName() + ",adapter=" + typeAdapter + "]";
            }
        };
    }

    public static <TT> TypeAdapterFactory newFactory(final Class<TT> class_, final Class<TT> class_2, final TypeAdapter<? super TT> typeAdapter) {
        return new TypeAdapterFactory(){

            @Override
            public <T> TypeAdapter<T> create(Gson class_3, TypeToken<T> typeToken) {
                class_3 = typeToken.getRawType();
                if (class_3 == class_ || class_3 == class_2) {
                    return typeAdapter;
                }
                return null;
            }

            public String toString() {
                return "Factory[type=" + class_2.getName() + "+" + class_.getName() + ",adapter=" + typeAdapter + "]";
            }
        };
    }

    public static <TT> TypeAdapterFactory newFactoryForMultipleTypes(final Class<TT> class_, final Class<? extends TT> class_2, final TypeAdapter<? super TT> typeAdapter) {
        return new TypeAdapterFactory(){

            @Override
            public <T> TypeAdapter<T> create(Gson class_3, TypeToken<T> typeToken) {
                class_3 = typeToken.getRawType();
                if (class_3 == class_ || class_3 == class_2) {
                    return typeAdapter;
                }
                return null;
            }

            public String toString() {
                return "Factory[type=" + class_.getName() + "+" + class_2.getName() + ",adapter=" + typeAdapter + "]";
            }
        };
    }

    public static <TT> TypeAdapterFactory newTypeHierarchyFactory(final Class<TT> class_, final TypeAdapter<TT> typeAdapter) {
        return new TypeAdapterFactory(){

            @Override
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
                if (class_.isAssignableFrom(typeToken.getRawType())) {
                    return typeAdapter;
                }
                return null;
            }

            public String toString() {
                return "Factory[typeHierarchy=" + class_.getName() + ",adapter=" + typeAdapter + "]";
            }
        };
    }

    private static final class EnumTypeAdapter<T extends Enum<T>>
    extends TypeAdapter<T> {
        private final Map<T, String> constantToName = new HashMap<T, String>();
        private final Map<String, T> nameToConstant = new HashMap<String, T>();

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public EnumTypeAdapter(Class<T> class_) {
            int n2;
            int n3;
            Enum[] arrenum;
            try {
                arrenum = (Enum[])class_.getEnumConstants();
                n3 = arrenum.length;
                n2 = 0;
            }
            catch (NoSuchFieldException var1_2) {
                throw new AssertionError();
            }
            while (n2 < n3) {
                Enum enum_ = arrenum[n2];
                String string2 = enum_.name();
                SerializedName serializedName = (SerializedName)class_.getField(string2).getAnnotation(SerializedName.class);
                if (serializedName != null) {
                    string2 = serializedName.value();
                }
                this.nameToConstant.put(string2, (Enum)enum_);
                this.constantToName.put((Enum)enum_, string2);
                ++n2;
                continue;
            }
            return;
        }

        @Override
        public T read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            return (T)((Enum)this.nameToConstant.get(jsonReader.nextString()));
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void write(JsonWriter jsonWriter, T object) throws IOException {
            object = object == null ? null : this.constantToName.get(object);
            jsonWriter.value((String)object);
        }
    }

}

