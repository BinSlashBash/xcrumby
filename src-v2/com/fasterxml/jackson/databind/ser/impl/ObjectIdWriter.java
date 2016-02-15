/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.PropertyName;

public final class ObjectIdWriter {
    public final boolean alwaysAsId;
    public final ObjectIdGenerator<?> generator;
    public final JavaType idType;
    public final SerializableString propertyName;
    public final JsonSerializer<Object> serializer;

    protected ObjectIdWriter(JavaType javaType, SerializableString serializableString, ObjectIdGenerator<?> objectIdGenerator, JsonSerializer<?> jsonSerializer, boolean bl2) {
        this.idType = javaType;
        this.propertyName = serializableString;
        this.generator = objectIdGenerator;
        this.serializer = jsonSerializer;
        this.alwaysAsId = bl2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ObjectIdWriter construct(JavaType javaType, PropertyName object, ObjectIdGenerator<?> objectIdGenerator, boolean bl2) {
        if (object == null) {
            object = null;
            do {
                return ObjectIdWriter.construct(javaType, (String)object, objectIdGenerator, bl2);
                break;
            } while (true);
        }
        object = object.getSimpleName();
        return ObjectIdWriter.construct(javaType, (String)object, objectIdGenerator, bl2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Deprecated
    public static ObjectIdWriter construct(JavaType javaType, String object, ObjectIdGenerator<?> objectIdGenerator, boolean bl2) {
        if (object == null) {
            object = null;
            do {
                return new ObjectIdWriter(javaType, (SerializableString)object, objectIdGenerator, null, bl2);
                break;
            } while (true);
        }
        object = new SerializedString((String)object);
        return new ObjectIdWriter(javaType, (SerializableString)object, objectIdGenerator, null, bl2);
    }

    public ObjectIdWriter withAlwaysAsId(boolean bl2) {
        if (bl2 == this.alwaysAsId) {
            return this;
        }
        return new ObjectIdWriter(this.idType, this.propertyName, this.generator, this.serializer, bl2);
    }

    public ObjectIdWriter withSerializer(JsonSerializer<?> jsonSerializer) {
        return new ObjectIdWriter(this.idType, this.propertyName, this.generator, jsonSerializer, this.alwaysAsId);
    }
}

