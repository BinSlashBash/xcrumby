/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StaticListSerializerBase;
import java.io.IOException;
import java.util.List;

@JacksonStdImpl
public final class IndexedStringListSerializer
extends StaticListSerializerBase<List<String>>
implements ContextualSerializer {
    public static final IndexedStringListSerializer instance = new IndexedStringListSerializer();
    protected final JsonSerializer<String> _serializer;

    protected IndexedStringListSerializer() {
        this(null);
    }

    public IndexedStringListSerializer(JsonSerializer<?> jsonSerializer) {
        super(List.class);
        this._serializer = jsonSerializer;
    }

    private final void _serializeUnwrapped(List<String> list, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (this._serializer == null) {
            this.serializeContents(list, jsonGenerator, serializerProvider, 1);
            return;
        }
        this.serializeUsingCustom(list, jsonGenerator, serializerProvider, 1);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void serializeContents(List<String> list, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, int n2) throws IOException {
        int n3 = 0;
        while (n3 < n2) {
            block5 : {
                try {
                    String string2 = list.get(n3);
                    if (string2 == null) {
                        serializerProvider.defaultSerializeNull(jsonGenerator);
                    } else {
                        jsonGenerator.writeString(string2);
                    }
                    break block5;
                }
                catch (Exception var2_3) {
                    this.wrapAndThrow(serializerProvider, (Throwable)var2_3, list, n3);
                }
                return;
            }
            ++n3;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final void serializeUsingCustom(List<String> list, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, int n2) throws IOException {
        int n3 = 0;
        JsonSerializer<String> jsonSerializer = this._serializer;
        int n4 = 0;
        while (n4 < n2) {
            block7 : {
                String string2;
                block6 : {
                    n3 = n4;
                    string2 = list.get(n4);
                    if (string2 != null) break block6;
                    n3 = n4;
                    serializerProvider.defaultSerializeNull(jsonGenerator);
                    break block7;
                }
                n3 = n4;
                try {
                    jsonSerializer.serialize(string2, jsonGenerator, serializerProvider);
                    break block7;
                }
                catch (Exception var2_3) {
                    this.wrapAndThrow(serializerProvider, (Throwable)var2_3, list, n3);
                }
                return;
            }
            ++n4;
        }
    }

    @Override
    protected void acceptContentVisitor(JsonArrayFormatVisitor jsonArrayFormatVisitor) throws JsonMappingException {
        jsonArrayFormatVisitor.itemsFormat(JsonFormatTypes.STRING);
    }

    @Override
    protected JsonNode contentSchema() {
        return this.createSchemaNode("string", true);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider jsonSerializer, BeanProperty jsonSerializer2) throws JsonMappingException {
        JsonSerializer<String> jsonSerializer3;
        JsonSerializer<String> jsonSerializer4;
        void var3_8;
        JsonSerializer<String> jsonSerializer5 = jsonSerializer4 = null;
        if (jsonSerializer2 != null) {
            AnnotatedMember annotatedMember = jsonSerializer2.getMember();
            JsonSerializer<String> jsonSerializer6 = jsonSerializer4;
            if (annotatedMember != null) {
                Object object = jsonSerializer.getAnnotationIntrospector().findContentSerializer(annotatedMember);
                JsonSerializer<String> jsonSerializer7 = jsonSerializer4;
                if (object != null) {
                    JsonSerializer<Object> jsonSerializer8 = jsonSerializer.serializerInstance(annotatedMember, object);
                }
            }
        }
        jsonSerializer4 = var3_8;
        if (var3_8 == null) {
            jsonSerializer4 = this._serializer;
        }
        jsonSerializer = (jsonSerializer3 = this.findConvertingContentSerializer((SerializerProvider)((Object)jsonSerializer), (BeanProperty)((Object)jsonSerializer2), jsonSerializer4)) == null ? jsonSerializer.findValueSerializer(String.class, (BeanProperty)((Object)jsonSerializer2)) : jsonSerializer.handleSecondaryContextualization(jsonSerializer3, (BeanProperty)((Object)jsonSerializer2));
        jsonSerializer2 = jsonSerializer;
        if (this.isDefaultSerializer(jsonSerializer)) {
            jsonSerializer2 = null;
        }
        if (jsonSerializer2 == this._serializer) {
            return this;
        }
        return new IndexedStringListSerializer(jsonSerializer2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serialize(List<String> list, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        int n2 = list.size();
        if (n2 == 1 && serializerProvider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)) {
            this._serializeUnwrapped(list, jsonGenerator, serializerProvider);
            return;
        }
        jsonGenerator.writeStartArray();
        if (this._serializer == null) {
            this.serializeContents(list, jsonGenerator, serializerProvider, n2);
        } else {
            this.serializeUsingCustom(list, jsonGenerator, serializerProvider, n2);
        }
        jsonGenerator.writeEndArray();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serializeWithType(List<String> list, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
        int n2 = list.size();
        typeSerializer.writeTypePrefixForArray(list, jsonGenerator);
        if (this._serializer == null) {
            this.serializeContents(list, jsonGenerator, serializerProvider, n2);
        } else {
            this.serializeUsingCustom(list, jsonGenerator, serializerProvider, n2);
        }
        typeSerializer.writeTypeSuffixForArray(list, jsonGenerator);
    }
}

