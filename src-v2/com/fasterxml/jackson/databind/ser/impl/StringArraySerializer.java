/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
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
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWithSerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.ArraySerializerBase;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.lang.reflect.Type;

@JacksonStdImpl
public class StringArraySerializer
extends ArraySerializerBase<String[]>
implements ContextualSerializer {
    private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(String.class);
    public static final StringArraySerializer instance = new StringArraySerializer();
    protected final JsonSerializer<Object> _elementSerializer;

    protected StringArraySerializer() {
        super(String[].class, (BeanProperty)null);
        this._elementSerializer = null;
    }

    public StringArraySerializer(StringArraySerializer stringArraySerializer, BeanProperty beanProperty, JsonSerializer<?> jsonSerializer) {
        super(stringArraySerializer, beanProperty);
        this._elementSerializer = jsonSerializer;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void serializeContentsSlow(String[] arrstring, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, JsonSerializer<Object> jsonSerializer) throws IOException, JsonGenerationException {
        int n2 = 0;
        int n3 = arrstring.length;
        while (n2 < n3) {
            if (arrstring[n2] == null) {
                serializerProvider.defaultSerializeNull(jsonGenerator);
            } else {
                jsonSerializer.serialize(arrstring[n2], jsonGenerator, serializerProvider);
            }
            ++n2;
        }
        return;
    }

    @Override
    public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
        return this;
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWithSerializerProvider, JavaType javaType) throws JsonMappingException {
        if (jsonFormatVisitorWithSerializerProvider != null && (jsonFormatVisitorWithSerializerProvider = jsonFormatVisitorWithSerializerProvider.expectArrayFormat(javaType)) != null) {
            jsonFormatVisitorWithSerializerProvider.itemsFormat(JsonFormatTypes.STRING);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider jsonSerializer, BeanProperty beanProperty) throws JsonMappingException {
        void var3_12;
        JsonSerializer<Object> jsonSerializer2;
        JsonSerializer<Object> jsonSerializer3;
        void var3_8;
        JsonSerializer<Object> jsonSerializer4 = jsonSerializer3 = null;
        if (beanProperty != null) {
            AnnotatedMember annotatedMember = beanProperty.getMember();
            JsonSerializer<Object> jsonSerializer5 = jsonSerializer3;
            if (annotatedMember != null) {
                Object object = jsonSerializer.getAnnotationIntrospector().findContentSerializer(annotatedMember);
                JsonSerializer<Object> jsonSerializer6 = jsonSerializer3;
                if (object != null) {
                    JsonSerializer<Object> jsonSerializer7 = jsonSerializer.serializerInstance(annotatedMember, object);
                }
            }
        }
        jsonSerializer3 = var3_8;
        if (var3_8 == null) {
            jsonSerializer3 = this._elementSerializer;
        }
        jsonSerializer = (jsonSerializer2 = this.findConvertingContentSerializer((SerializerProvider)((Object)jsonSerializer), beanProperty, jsonSerializer3)) == null ? jsonSerializer.findValueSerializer(String.class, beanProperty) : jsonSerializer.handleSecondaryContextualization(jsonSerializer2, beanProperty);
        JsonSerializer jsonSerializer8 = jsonSerializer;
        if (this.isDefaultSerializer(jsonSerializer)) {
            Object var3_11 = null;
        }
        if (var3_12 != this._elementSerializer) return new StringArraySerializer(this, beanProperty, var3_12);
        return this;
    }

    @Override
    public JsonSerializer<?> getContentSerializer() {
        return this._elementSerializer;
    }

    @Override
    public JavaType getContentType() {
        return VALUE_TYPE;
    }

    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
        return this.createSchemaNode("array", true).set("items", this.createSchemaNode("string"));
    }

    @Override
    public boolean hasSingleElement(String[] arrstring) {
        if (arrstring.length == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty(String[] arrstring) {
        if (arrstring == null || arrstring.length == 0) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serializeContents(String[] arrstring, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        int n2 = arrstring.length;
        if (n2 == 0) {
            return;
        }
        if (this._elementSerializer != null) {
            this.serializeContentsSlow(arrstring, jsonGenerator, serializerProvider, this._elementSerializer);
            return;
        }
        int n3 = 0;
        while (n3 < n2) {
            if (arrstring[n3] == null) {
                jsonGenerator.writeNull();
            } else {
                jsonGenerator.writeString(arrstring[n3]);
            }
            ++n3;
        }
    }
}

