/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.ResolvableSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import java.io.IOException;
import java.lang.reflect.Type;

public class StdDelegatingSerializer
extends StdSerializer<Object>
implements ContextualSerializer,
ResolvableSerializer,
JsonFormatVisitable,
SchemaAware {
    protected final Converter<Object, ?> _converter;
    protected final JsonSerializer<Object> _delegateSerializer;
    protected final JavaType _delegateType;

    public StdDelegatingSerializer(Converter<?, ?> converter) {
        super(Object.class);
        this._converter = converter;
        this._delegateType = null;
        this._delegateSerializer = null;
    }

    public StdDelegatingSerializer(Converter<Object, ?> converter, JavaType javaType, JsonSerializer<?> jsonSerializer) {
        super(javaType);
        this._converter = converter;
        this._delegateType = javaType;
        this._delegateSerializer = jsonSerializer;
    }

    public <T> StdDelegatingSerializer(Class<T> class_, Converter<T, ?> converter) {
        super(class_, false);
        this._converter = converter;
        this._delegateType = null;
        this._delegateSerializer = null;
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        this._delegateSerializer.acceptJsonFormatVisitor(jsonFormatVisitorWrapper, javaType);
    }

    protected Object convertValue(Object object) {
        return this._converter.convert(object);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider object, BeanProperty beanProperty) throws JsonMappingException {
        JavaType javaType;
        if (this._delegateSerializer != null) {
            if (!(this._delegateSerializer instanceof ContextualSerializer) || (object = object.handleSecondaryContextualization(this._delegateSerializer, beanProperty)) == this._delegateSerializer) {
                return this;
            }
            return this.withDelegate(this._converter, this._delegateType, object);
        }
        JavaType javaType2 = javaType = this._delegateType;
        if (javaType == null) {
            javaType2 = this._converter.getOutputType(object.getTypeFactory());
        }
        return this.withDelegate(this._converter, javaType2, object.findValueSerializer(javaType2, beanProperty));
    }

    protected Converter<Object, ?> getConverter() {
        return this._converter;
    }

    @Override
    public JsonSerializer<?> getDelegatee() {
        return this._delegateSerializer;
    }

    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) throws JsonMappingException {
        if (this._delegateSerializer instanceof SchemaAware) {
            return ((SchemaAware)((Object)this._delegateSerializer)).getSchema(serializerProvider, type);
        }
        return super.getSchema(serializerProvider, type);
    }

    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type, boolean bl2) throws JsonMappingException {
        if (this._delegateSerializer instanceof SchemaAware) {
            return ((SchemaAware)((Object)this._delegateSerializer)).getSchema(serializerProvider, type, bl2);
        }
        return super.getSchema(serializerProvider, type);
    }

    @Override
    public boolean isEmpty(Object object) {
        object = this.convertValue(object);
        return this._delegateSerializer.isEmpty(object);
    }

    @Override
    public void resolve(SerializerProvider serializerProvider) throws JsonMappingException {
        if (this._delegateSerializer != null && this._delegateSerializer instanceof ResolvableSerializer) {
            ((ResolvableSerializer)((Object)this._delegateSerializer)).resolve(serializerProvider);
        }
    }

    @Override
    public void serialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if ((object = this.convertValue(object)) == null) {
            serializerProvider.defaultSerializeNull(jsonGenerator);
            return;
        }
        this._delegateSerializer.serialize(object, jsonGenerator, serializerProvider);
    }

    @Override
    public void serializeWithType(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonProcessingException {
        object = this.convertValue(object);
        this._delegateSerializer.serializeWithType(object, jsonGenerator, serializerProvider, typeSerializer);
    }

    protected StdDelegatingSerializer withDelegate(Converter<Object, ?> converter, JavaType javaType, JsonSerializer<?> jsonSerializer) {
        if (this.getClass() != StdDelegatingSerializer.class) {
            throw new IllegalStateException("Sub-class " + this.getClass().getName() + " must override 'withDelegate'");
        }
        return new StdDelegatingSerializer(converter, javaType, jsonSerializer);
    }
}

