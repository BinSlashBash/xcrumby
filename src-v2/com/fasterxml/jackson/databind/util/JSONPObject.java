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

public class JSONPObject
implements JsonSerializable {
    protected final String _function;
    protected final JavaType _serializationType;
    protected final Object _value;

    public JSONPObject(String string2, Object object) {
        this(string2, object, null);
    }

    public JSONPObject(String string2, Object object, JavaType javaType) {
        this._function = string2;
        this._value = object;
        this._serializationType = javaType;
    }

    public String getFunction() {
        return this._function;
    }

    public JavaType getSerializationType() {
        return this._serializationType;
    }

    public Object getValue() {
        return this._value;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeRaw(this._function);
        jsonGenerator.writeRaw('(');
        if (this._value == null) {
            serializerProvider.defaultSerializeNull(jsonGenerator);
        } else if (this._serializationType != null) {
            serializerProvider.findTypedValueSerializer(this._serializationType, true, null).serialize(this._value, jsonGenerator, serializerProvider);
        } else {
            serializerProvider.findTypedValueSerializer(this._value.getClass(), true, null).serialize(this._value, jsonGenerator, serializerProvider);
        }
        jsonGenerator.writeRaw(')');
    }

    @Override
    public void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonProcessingException {
        this.serialize(jsonGenerator, serializerProvider);
    }
}

