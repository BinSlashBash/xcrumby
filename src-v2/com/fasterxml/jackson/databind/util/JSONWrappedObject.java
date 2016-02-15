/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;

public class JSONWrappedObject
implements JsonSerializable {
    protected final String _prefix;
    protected final JavaType _serializationType;
    protected final String _suffix;
    protected final Object _value;

    public JSONWrappedObject(String string2, String string3, Object object) {
        this(string2, string3, object, null);
    }

    public JSONWrappedObject(String string2, String string3, Object object, JavaType javaType) {
        this._prefix = string2;
        this._suffix = string3;
        this._value = object;
        this._serializationType = javaType;
    }

    public String getPrefix() {
        return this._prefix;
    }

    public JavaType getSerializationType() {
        return this._serializationType;
    }

    public String getSuffix() {
        return this._suffix;
    }

    public Object getValue() {
        return this._value;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if (this._prefix != null) {
            jsonGenerator.writeRaw(this._prefix);
        }
        if (this._value == null) {
            serializerProvider.defaultSerializeNull(jsonGenerator);
        } else if (this._serializationType != null) {
            serializerProvider.findTypedValueSerializer(this._serializationType, true, null).serialize(this._value, jsonGenerator, serializerProvider);
        } else {
            serializerProvider.findTypedValueSerializer(this._value.getClass(), true, null).serialize(this._value, jsonGenerator, serializerProvider);
        }
        if (this._suffix != null) {
            jsonGenerator.writeRaw(this._suffix);
        }
    }

    @Override
    public void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonProcessingException {
        this.serialize(jsonGenerator, serializerProvider);
    }
}

