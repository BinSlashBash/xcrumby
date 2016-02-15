package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StaticListSerializerBase;
import java.io.IOException;
import java.util.List;

@JacksonStdImpl
public final class IndexedStringListSerializer extends StaticListSerializerBase<List<String>> implements ContextualSerializer {
    public static final IndexedStringListSerializer instance;
    protected final JsonSerializer<String> _serializer;

    static {
        instance = new IndexedStringListSerializer();
    }

    protected IndexedStringListSerializer() {
        this(null);
    }

    public IndexedStringListSerializer(JsonSerializer<?> ser) {
        super(List.class);
        this._serializer = ser;
    }

    protected JsonNode contentSchema() {
        return createSchemaNode("string", true);
    }

    protected void acceptContentVisitor(JsonArrayFormatVisitor visitor) throws JsonMappingException {
        visitor.itemsFormat(JsonFormatTypes.STRING);
    }

    public JsonSerializer<?> createContextual(SerializerProvider provider, BeanProperty property) throws JsonMappingException {
        JsonSerializer<?> ser = null;
        if (property != null) {
            AnnotatedMember m = property.getMember();
            if (m != null) {
                Object serDef = provider.getAnnotationIntrospector().findContentSerializer(m);
                if (serDef != null) {
                    ser = provider.serializerInstance(m, serDef);
                }
            }
        }
        if (ser == null) {
            ser = this._serializer;
        }
        ser = findConvertingContentSerializer(provider, property, ser);
        if (ser == null) {
            ser = provider.findValueSerializer(String.class, property);
        } else {
            ser = provider.handleSecondaryContextualization(ser, property);
        }
        if (isDefaultSerializer(ser)) {
            ser = null;
        }
        if (ser == this._serializer) {
            return this;
        }
        this(ser);
        return this;
    }

    public void serialize(List<String> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        int len = value.size();
        if (len == 1 && provider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)) {
            _serializeUnwrapped(value, jgen, provider);
            return;
        }
        jgen.writeStartArray();
        if (this._serializer == null) {
            serializeContents(value, jgen, provider, len);
        } else {
            serializeUsingCustom(value, jgen, provider, len);
        }
        jgen.writeEndArray();
    }

    private final void _serializeUnwrapped(List<String> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (this._serializer == null) {
            serializeContents(value, jgen, provider, 1);
        } else {
            serializeUsingCustom(value, jgen, provider, 1);
        }
    }

    public void serializeWithType(List<String> value, JsonGenerator jgen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        int len = value.size();
        typeSer.writeTypePrefixForArray(value, jgen);
        if (this._serializer == null) {
            serializeContents(value, jgen, provider, len);
        } else {
            serializeUsingCustom(value, jgen, provider, len);
        }
        typeSer.writeTypeSuffixForArray(value, jgen);
    }

    private final void serializeContents(List<String> value, JsonGenerator jgen, SerializerProvider provider, int len) throws IOException {
        int i = 0;
        while (i < len) {
            try {
                String str = (String) value.get(i);
                if (str == null) {
                    provider.defaultSerializeNull(jgen);
                } else {
                    jgen.writeString(str);
                }
                i++;
            } catch (Exception e) {
                wrapAndThrow(provider, (Throwable) e, (Object) value, i);
                return;
            }
        }
    }

    private final void serializeUsingCustom(List<String> value, JsonGenerator jgen, SerializerProvider provider, int len) throws IOException {
        try {
            JsonSerializer<String> ser = this._serializer;
            for (int i = 0; i < len; i++) {
                String str = (String) value.get(i);
                if (str == null) {
                    provider.defaultSerializeNull(jgen);
                } else {
                    ser.serialize(str, jgen, provider);
                }
            }
        } catch (Exception e) {
            wrapAndThrow(provider, (Throwable) e, (Object) value, 0);
        }
    }
}
