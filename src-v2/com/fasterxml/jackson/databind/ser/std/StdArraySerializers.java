/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWithSerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.std.ArraySerializerBase;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

public class StdArraySerializers {
    protected static final HashMap<String, JsonSerializer<?>> _arraySerializers = new HashMap();

    static {
        _arraySerializers.put(boolean[].class.getName(), new BooleanArraySerializer());
        _arraySerializers.put(byte[].class.getName(), new ByteArraySerializer());
        _arraySerializers.put(char[].class.getName(), new CharArraySerializer());
        _arraySerializers.put(short[].class.getName(), new ShortArraySerializer());
        _arraySerializers.put(int[].class.getName(), new IntArraySerializer());
        _arraySerializers.put(long[].class.getName(), new LongArraySerializer());
        _arraySerializers.put(float[].class.getName(), new FloatArraySerializer());
        _arraySerializers.put(double[].class.getName(), new DoubleArraySerializer());
    }

    protected StdArraySerializers() {
    }

    public static JsonSerializer<?> findStandardImpl(Class<?> class_) {
        return _arraySerializers.get(class_.getName());
    }

    @JacksonStdImpl
    public static final class BooleanArraySerializer
    extends ArraySerializerBase<boolean[]> {
        private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(Boolean.class);

        public BooleanArraySerializer() {
            super(boolean[].class, (BeanProperty)null);
        }

        @Override
        public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
            return this;
        }

        @Override
        public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWithSerializerProvider, JavaType javaType) throws JsonMappingException {
            if (jsonFormatVisitorWithSerializerProvider != null && (jsonFormatVisitorWithSerializerProvider = jsonFormatVisitorWithSerializerProvider.expectArrayFormat(javaType)) != null) {
                jsonFormatVisitorWithSerializerProvider.itemsFormat(JsonFormatTypes.BOOLEAN);
            }
        }

        @Override
        public JsonSerializer<?> getContentSerializer() {
            return null;
        }

        @Override
        public JavaType getContentType() {
            return VALUE_TYPE;
        }

        @Override
        public JsonNode getSchema(SerializerProvider object, Type type) {
            object = this.createSchemaNode("array", true);
            object.set("items", this.createSchemaNode("boolean"));
            return object;
        }

        @Override
        public boolean hasSingleElement(boolean[] arrbl) {
            if (arrbl.length == 1) {
                return true;
            }
            return false;
        }

        @Override
        public boolean isEmpty(boolean[] arrbl) {
            if (arrbl == null || arrbl.length == 0) {
                return true;
            }
            return false;
        }

        @Override
        public void serializeContents(boolean[] arrbl, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            int n2 = arrbl.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                jsonGenerator.writeBoolean(arrbl[i2]);
            }
        }
    }

    @JacksonStdImpl
    public static final class ByteArraySerializer
    extends StdSerializer<byte[]> {
        public ByteArraySerializer() {
            super(byte[].class);
        }

        @Override
        public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWithSerializerProvider, JavaType javaType) throws JsonMappingException {
            if (jsonFormatVisitorWithSerializerProvider != null && (jsonFormatVisitorWithSerializerProvider = jsonFormatVisitorWithSerializerProvider.expectArrayFormat(javaType)) != null) {
                jsonFormatVisitorWithSerializerProvider.itemsFormat(JsonFormatTypes.STRING);
            }
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return this.createSchemaNode("array", true).set("items", this.createSchemaNode("string"));
        }

        @Override
        public boolean isEmpty(byte[] arrby) {
            if (arrby == null || arrby.length == 0) {
                return true;
            }
            return false;
        }

        @Override
        public void serialize(byte[] arrby, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            jsonGenerator.writeBinary(serializerProvider.getConfig().getBase64Variant(), arrby, 0, arrby.length);
        }

        @Override
        public void serializeWithType(byte[] arrby, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
            typeSerializer.writeTypePrefixForScalar(arrby, jsonGenerator);
            jsonGenerator.writeBinary(serializerProvider.getConfig().getBase64Variant(), arrby, 0, arrby.length);
            typeSerializer.writeTypeSuffixForScalar(arrby, jsonGenerator);
        }
    }

    @JacksonStdImpl
    public static final class CharArraySerializer
    extends StdSerializer<char[]> {
        public CharArraySerializer() {
            super(char[].class);
        }

        private final void _writeArrayContents(JsonGenerator jsonGenerator, char[] arrc) throws IOException, JsonGenerationException {
            int n2 = arrc.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                jsonGenerator.writeString(arrc, i2, 1);
            }
        }

        @Override
        public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWithSerializerProvider, JavaType javaType) throws JsonMappingException {
            if (jsonFormatVisitorWithSerializerProvider != null && (jsonFormatVisitorWithSerializerProvider = jsonFormatVisitorWithSerializerProvider.expectArrayFormat(javaType)) != null) {
                jsonFormatVisitorWithSerializerProvider.itemsFormat(JsonFormatTypes.STRING);
            }
        }

        @Override
        public JsonNode getSchema(SerializerProvider object, Type object2) {
            object = this.createSchemaNode("array", true);
            object2 = this.createSchemaNode("string");
            object2.put("type", "string");
            return object.set("items", (JsonNode)object2);
        }

        @Override
        public boolean isEmpty(char[] arrc) {
            if (arrc == null || arrc.length == 0) {
                return true;
            }
            return false;
        }

        @Override
        public void serialize(char[] arrc, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            if (serializerProvider.isEnabled(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS)) {
                jsonGenerator.writeStartArray();
                this._writeArrayContents(jsonGenerator, arrc);
                jsonGenerator.writeEndArray();
                return;
            }
            jsonGenerator.writeString(arrc, 0, arrc.length);
        }

        @Override
        public void serializeWithType(char[] arrc, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
            if (serializerProvider.isEnabled(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS)) {
                typeSerializer.writeTypePrefixForArray(arrc, jsonGenerator);
                this._writeArrayContents(jsonGenerator, arrc);
                typeSerializer.writeTypeSuffixForArray(arrc, jsonGenerator);
                return;
            }
            typeSerializer.writeTypePrefixForScalar(arrc, jsonGenerator);
            jsonGenerator.writeString(arrc, 0, arrc.length);
            typeSerializer.writeTypeSuffixForScalar(arrc, jsonGenerator);
        }
    }

    @JacksonStdImpl
    public static final class DoubleArraySerializer
    extends ArraySerializerBase<double[]> {
        private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(Double.TYPE);

        public DoubleArraySerializer() {
            super(double[].class, (BeanProperty)null);
        }

        @Override
        public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
            return this;
        }

        @Override
        public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWithSerializerProvider, JavaType javaType) throws JsonMappingException {
            if (jsonFormatVisitorWithSerializerProvider != null && (jsonFormatVisitorWithSerializerProvider = jsonFormatVisitorWithSerializerProvider.expectArrayFormat(javaType)) != null) {
                jsonFormatVisitorWithSerializerProvider.itemsFormat(JsonFormatTypes.NUMBER);
            }
        }

        @Override
        public JsonSerializer<?> getContentSerializer() {
            return null;
        }

        @Override
        public JavaType getContentType() {
            return VALUE_TYPE;
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return this.createSchemaNode("array", true).set("items", this.createSchemaNode("number"));
        }

        @Override
        public boolean hasSingleElement(double[] arrd) {
            if (arrd.length == 1) {
                return true;
            }
            return false;
        }

        @Override
        public boolean isEmpty(double[] arrd) {
            if (arrd == null || arrd.length == 0) {
                return true;
            }
            return false;
        }

        @Override
        public void serializeContents(double[] arrd, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            int n2 = arrd.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                jsonGenerator.writeNumber(arrd[i2]);
            }
        }
    }

    @JacksonStdImpl
    public static final class FloatArraySerializer
    extends TypedPrimitiveArraySerializer<float[]> {
        private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(Float.TYPE);

        public FloatArraySerializer() {
            super(float[].class);
        }

        public FloatArraySerializer(FloatArraySerializer floatArraySerializer, BeanProperty beanProperty, TypeSerializer typeSerializer) {
            super(floatArraySerializer, beanProperty, typeSerializer);
        }

        @Override
        public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
            return new FloatArraySerializer(this, this._property, typeSerializer);
        }

        @Override
        public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWithSerializerProvider, JavaType javaType) throws JsonMappingException {
            if (jsonFormatVisitorWithSerializerProvider != null && (jsonFormatVisitorWithSerializerProvider = jsonFormatVisitorWithSerializerProvider.expectArrayFormat(javaType)) != null) {
                jsonFormatVisitorWithSerializerProvider.itemsFormat(JsonFormatTypes.NUMBER);
            }
        }

        @Override
        public JsonSerializer<?> getContentSerializer() {
            return null;
        }

        @Override
        public JavaType getContentType() {
            return VALUE_TYPE;
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return this.createSchemaNode("array", true).set("items", this.createSchemaNode("number"));
        }

        @Override
        public boolean hasSingleElement(float[] arrf) {
            if (arrf.length == 1) {
                return true;
            }
            return false;
        }

        @Override
        public boolean isEmpty(float[] arrf) {
            if (arrf == null || arrf.length == 0) {
                return true;
            }
            return false;
        }

        @Override
        public void serializeContents(float[] arrf, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            if (this._valueTypeSerializer != null) {
                int n2 = arrf.length;
                for (int i2 = 0; i2 < n2; ++i2) {
                    this._valueTypeSerializer.writeTypePrefixForScalar(null, jsonGenerator, Float.TYPE);
                    jsonGenerator.writeNumber(arrf[i2]);
                    this._valueTypeSerializer.writeTypeSuffixForScalar(null, jsonGenerator);
                }
            } else {
                int n3 = arrf.length;
                for (int i3 = 0; i3 < n3; ++i3) {
                    jsonGenerator.writeNumber(arrf[i3]);
                }
            }
        }
    }

    @JacksonStdImpl
    public static final class IntArraySerializer
    extends ArraySerializerBase<int[]> {
        private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(Integer.TYPE);

        public IntArraySerializer() {
            super(int[].class, (BeanProperty)null);
        }

        @Override
        public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
            return this;
        }

        @Override
        public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWithSerializerProvider, JavaType javaType) throws JsonMappingException {
            if (jsonFormatVisitorWithSerializerProvider != null && (jsonFormatVisitorWithSerializerProvider = jsonFormatVisitorWithSerializerProvider.expectArrayFormat(javaType)) != null) {
                jsonFormatVisitorWithSerializerProvider.itemsFormat(JsonFormatTypes.INTEGER);
            }
        }

        @Override
        public JsonSerializer<?> getContentSerializer() {
            return null;
        }

        @Override
        public JavaType getContentType() {
            return VALUE_TYPE;
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return this.createSchemaNode("array", true).set("items", this.createSchemaNode("integer"));
        }

        @Override
        public boolean hasSingleElement(int[] arrn) {
            if (arrn.length == 1) {
                return true;
            }
            return false;
        }

        @Override
        public boolean isEmpty(int[] arrn) {
            if (arrn == null || arrn.length == 0) {
                return true;
            }
            return false;
        }

        @Override
        public void serializeContents(int[] arrn, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            int n2 = arrn.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                jsonGenerator.writeNumber(arrn[i2]);
            }
        }
    }

    @JacksonStdImpl
    public static final class LongArraySerializer
    extends TypedPrimitiveArraySerializer<long[]> {
        private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(Long.TYPE);

        public LongArraySerializer() {
            super(long[].class);
        }

        public LongArraySerializer(LongArraySerializer longArraySerializer, BeanProperty beanProperty, TypeSerializer typeSerializer) {
            super(longArraySerializer, beanProperty, typeSerializer);
        }

        @Override
        public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
            return new LongArraySerializer(this, this._property, typeSerializer);
        }

        @Override
        public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWithSerializerProvider, JavaType javaType) throws JsonMappingException {
            if (jsonFormatVisitorWithSerializerProvider != null && (jsonFormatVisitorWithSerializerProvider = jsonFormatVisitorWithSerializerProvider.expectArrayFormat(javaType)) != null) {
                jsonFormatVisitorWithSerializerProvider.itemsFormat(JsonFormatTypes.NUMBER);
            }
        }

        @Override
        public JsonSerializer<?> getContentSerializer() {
            return null;
        }

        @Override
        public JavaType getContentType() {
            return VALUE_TYPE;
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return this.createSchemaNode("array", true).set("items", this.createSchemaNode("number", true));
        }

        @Override
        public boolean hasSingleElement(long[] arrl) {
            if (arrl.length == 1) {
                return true;
            }
            return false;
        }

        @Override
        public boolean isEmpty(long[] arrl) {
            if (arrl == null || arrl.length == 0) {
                return true;
            }
            return false;
        }

        @Override
        public void serializeContents(long[] arrl, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            if (this._valueTypeSerializer != null) {
                int n2 = arrl.length;
                for (int i2 = 0; i2 < n2; ++i2) {
                    this._valueTypeSerializer.writeTypePrefixForScalar(null, jsonGenerator, Long.TYPE);
                    jsonGenerator.writeNumber(arrl[i2]);
                    this._valueTypeSerializer.writeTypeSuffixForScalar(null, jsonGenerator);
                }
            } else {
                int n3 = arrl.length;
                for (int i3 = 0; i3 < n3; ++i3) {
                    jsonGenerator.writeNumber(arrl[i3]);
                }
            }
        }
    }

    @JacksonStdImpl
    public static final class ShortArraySerializer
    extends TypedPrimitiveArraySerializer<short[]> {
        private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(Short.TYPE);

        public ShortArraySerializer() {
            super(short[].class);
        }

        public ShortArraySerializer(ShortArraySerializer shortArraySerializer, BeanProperty beanProperty, TypeSerializer typeSerializer) {
            super(shortArraySerializer, beanProperty, typeSerializer);
        }

        @Override
        public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
            return new ShortArraySerializer(this, this._property, typeSerializer);
        }

        @Override
        public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWithSerializerProvider, JavaType javaType) throws JsonMappingException {
            if (jsonFormatVisitorWithSerializerProvider != null && (jsonFormatVisitorWithSerializerProvider = jsonFormatVisitorWithSerializerProvider.expectArrayFormat(javaType)) != null) {
                jsonFormatVisitorWithSerializerProvider.itemsFormat(JsonFormatTypes.INTEGER);
            }
        }

        @Override
        public JsonSerializer<?> getContentSerializer() {
            return null;
        }

        @Override
        public JavaType getContentType() {
            return VALUE_TYPE;
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return this.createSchemaNode("array", true).set("items", this.createSchemaNode("integer"));
        }

        @Override
        public boolean hasSingleElement(short[] arrs) {
            if (arrs.length == 1) {
                return true;
            }
            return false;
        }

        @Override
        public boolean isEmpty(short[] arrs) {
            if (arrs == null || arrs.length == 0) {
                return true;
            }
            return false;
        }

        @Override
        public void serializeContents(short[] arrs, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            if (this._valueTypeSerializer != null) {
                int n2 = arrs.length;
                for (int i2 = 0; i2 < n2; ++i2) {
                    this._valueTypeSerializer.writeTypePrefixForScalar(null, jsonGenerator, Short.TYPE);
                    jsonGenerator.writeNumber(arrs[i2]);
                    this._valueTypeSerializer.writeTypeSuffixForScalar(null, jsonGenerator);
                }
            } else {
                int n3 = arrs.length;
                for (int i3 = 0; i3 < n3; ++i3) {
                    jsonGenerator.writeNumber((int)arrs[i3]);
                }
            }
        }
    }

    protected static abstract class TypedPrimitiveArraySerializer<T>
    extends ArraySerializerBase<T> {
        protected final TypeSerializer _valueTypeSerializer;

        protected TypedPrimitiveArraySerializer(TypedPrimitiveArraySerializer<T> typedPrimitiveArraySerializer, BeanProperty beanProperty, TypeSerializer typeSerializer) {
            super(typedPrimitiveArraySerializer, beanProperty);
            this._valueTypeSerializer = typeSerializer;
        }

        protected TypedPrimitiveArraySerializer(Class<T> class_) {
            super(class_);
            this._valueTypeSerializer = null;
        }
    }

}

