/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class NumberSerializers {
    protected NumberSerializers() {
    }

    public static void addAll(Map<String, JsonSerializer<?>> map) {
        IntegerSerializer integerSerializer = new IntegerSerializer();
        map.put(Integer.class.getName(), integerSerializer);
        map.put(Integer.TYPE.getName(), integerSerializer);
        map.put(Long.class.getName(), LongSerializer.instance);
        map.put(Long.TYPE.getName(), LongSerializer.instance);
        map.put(Byte.class.getName(), IntLikeSerializer.instance);
        map.put(Byte.TYPE.getName(), IntLikeSerializer.instance);
        map.put(Short.class.getName(), ShortSerializer.instance);
        map.put(Short.TYPE.getName(), ShortSerializer.instance);
        map.put(Float.class.getName(), FloatSerializer.instance);
        map.put(Float.TYPE.getName(), FloatSerializer.instance);
        map.put(Double.class.getName(), DoubleSerializer.instance);
        map.put(Double.TYPE.getName(), DoubleSerializer.instance);
    }

    protected static abstract class Base<T>
    extends StdScalarSerializer<T>
    implements ContextualSerializer {
        protected final JsonParser.NumberType _numberType;
        protected final String _schemaType;

        protected Base(Class<T> class_, JsonParser.NumberType numberType, String string2) {
            super(class_);
            this._numberType = numberType;
            this._schemaType = string2;
        }

        @Override
        public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper object, JavaType javaType) throws JsonMappingException {
            if ((object = object.expectIntegerFormat(javaType)) != null) {
                object.numberType(this._numberType);
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public JsonSerializer<?> createContextual(SerializerProvider object, BeanProperty beanProperty) throws JsonMappingException {
            if (beanProperty == null || (object = object.getAnnotationIntrospector().findFormat(beanProperty.getMember())) == null) return this;
            switch (object.getShape()) {
                default: {
                    return this;
                }
                case STRING: 
            }
            return ToStringSerializer.instance;
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return this.createSchemaNode(this._schemaType, true);
        }
    }

    @JacksonStdImpl
    public static final class DoubleSerializer
    extends Base<Double> {
        static final DoubleSerializer instance = new DoubleSerializer();

        public DoubleSerializer() {
            super(Double.class, JsonParser.NumberType.DOUBLE, "number");
        }

        @Override
        public void serialize(Double d2, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(d2);
        }

        @Override
        public void serializeWithType(Double d2, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
            this.serialize(d2, jsonGenerator, serializerProvider);
        }
    }

    @JacksonStdImpl
    public static final class FloatSerializer
    extends Base<Float> {
        static final FloatSerializer instance = new FloatSerializer();

        public FloatSerializer() {
            super(Float.class, JsonParser.NumberType.FLOAT, "number");
        }

        @Override
        public void serialize(Float f2, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(f2.floatValue());
        }
    }

    @JacksonStdImpl
    public static final class IntLikeSerializer
    extends Base<Number> {
        static final IntLikeSerializer instance = new IntLikeSerializer();

        public IntLikeSerializer() {
            super(Number.class, JsonParser.NumberType.INT, "integer");
        }

        @Override
        public void serialize(Number number, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(number.intValue());
        }
    }

    @JacksonStdImpl
    public static final class IntegerSerializer
    extends Base<Integer> {
        public IntegerSerializer() {
            super(Integer.class, JsonParser.NumberType.INT, "integer");
        }

        @Override
        public void serialize(Integer n2, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(n2);
        }

        @Override
        public void serializeWithType(Integer n2, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
            this.serialize(n2, jsonGenerator, serializerProvider);
        }
    }

    @JacksonStdImpl
    public static final class LongSerializer
    extends Base<Long> {
        static final LongSerializer instance = new LongSerializer();

        public LongSerializer() {
            super(Long.class, JsonParser.NumberType.LONG, "number");
        }

        @Override
        public void serialize(Long l2, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(l2);
        }
    }

    @JacksonStdImpl
    public static final class ShortSerializer
    extends Base<Short> {
        static final ShortSerializer instance = new ShortSerializer();

        public ShortSerializer() {
            super(Short.class, JsonParser.NumberType.INT, "number");
        }

        @Override
        public void serialize(Short s2, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(s2);
        }
    }

}

