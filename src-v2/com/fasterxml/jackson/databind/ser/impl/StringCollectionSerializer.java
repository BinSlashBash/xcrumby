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
import java.util.Collection;
import java.util.Iterator;

@JacksonStdImpl
public class StringCollectionSerializer
extends StaticListSerializerBase<Collection<String>>
implements ContextualSerializer {
    public static final StringCollectionSerializer instance = new StringCollectionSerializer();
    protected final JsonSerializer<String> _serializer;

    protected StringCollectionSerializer() {
        this(null);
    }

    protected StringCollectionSerializer(JsonSerializer<?> jsonSerializer) {
        super(Collection.class);
        this._serializer = jsonSerializer;
    }

    private final void _serializeUnwrapped(Collection<String> collection, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        if (this._serializer == null) {
            this.serializeContents(collection, jsonGenerator, serializerProvider);
            return;
        }
        this.serializeUsingCustom(collection, jsonGenerator, serializerProvider);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private final void serializeContents(Collection<String> var1_1, JsonGenerator var2_2, SerializerProvider var3_3) throws IOException, JsonGenerationException {
        if (this._serializer != null) {
            this.serializeUsingCustom(var1_1, var2_2, var3_3);
            return;
        }
        var4_4 = 0;
        var5_5 = var1_1.iterator();
        while (var5_5.hasNext() != false) {
            block4 : {
                var6_6 = var5_5.next();
                if (var6_6 != null) ** GOTO lbl12
                try {
                    var3_3.defaultSerializeNull(var2_2);
                    break block4;
lbl12: // 1 sources:
                    var2_2.writeString(var6_6);
                }
                catch (Exception var6_7) {
                    this.wrapAndThrow(var3_3, (Throwable)var6_7, var1_1, var4_4);
                    continue;
                }
            }
            ++var4_4;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void serializeUsingCustom(Collection<String> var1_1, JsonGenerator var2_2, SerializerProvider var3_3) throws IOException, JsonGenerationException {
        var4_4 = this._serializer;
        var5_5 = var1_1.iterator();
        while (var5_5.hasNext() != false) {
            var6_6 = var5_5.next();
            if (var6_6 != null) ** GOTO lbl9
            try {
                var3_3.defaultSerializeNull(var2_2);
lbl9: // 1 sources:
                var4_4.serialize(var6_6, var2_2, var3_3);
                continue;
            }
            catch (Exception var6_7) {
                this.wrapAndThrow(var3_3, (Throwable)var6_7, var1_1, 0);
                continue;
            }
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
        return new StringCollectionSerializer(jsonSerializer2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serialize(Collection<String> collection, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        if (collection.size() == 1 && serializerProvider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)) {
            this._serializeUnwrapped(collection, jsonGenerator, serializerProvider);
            return;
        }
        jsonGenerator.writeStartArray();
        if (this._serializer == null) {
            this.serializeContents(collection, jsonGenerator, serializerProvider);
        } else {
            this.serializeUsingCustom(collection, jsonGenerator, serializerProvider);
        }
        jsonGenerator.writeEndArray();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serializeWithType(Collection<String> collection, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        typeSerializer.writeTypePrefixForArray(collection, jsonGenerator);
        if (this._serializer == null) {
            this.serializeContents(collection, jsonGenerator, serializerProvider);
        } else {
            this.serializeUsingCustom(collection, jsonGenerator, serializerProvider);
        }
        typeSerializer.writeTypeSuffixForArray(collection, jsonGenerator);
    }
}

