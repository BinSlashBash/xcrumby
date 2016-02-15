/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import java.io.IOException;

public final class WritableObjectId {
    public final ObjectIdGenerator<?> generator;
    public Object id;
    protected boolean idWritten = false;

    public WritableObjectId(ObjectIdGenerator<?> objectIdGenerator) {
        this.generator = objectIdGenerator;
    }

    public Object generateId(Object object) {
        this.id = object = this.generator.generateId(object);
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeAsField(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, ObjectIdWriter objectIdWriter) throws IOException, JsonGenerationException {
        this.idWritten = true;
        if (jsonGenerator.canWriteObjectId()) {
            jsonGenerator.writeObjectId(String.valueOf(this.id));
            return;
        } else {
            SerializableString serializableString = objectIdWriter.propertyName;
            if (serializableString == null) return;
            {
                jsonGenerator.writeFieldName(serializableString);
                objectIdWriter.serializer.serialize(this.id, jsonGenerator, serializerProvider);
                return;
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean writeAsId(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, ObjectIdWriter objectIdWriter) throws IOException, JsonGenerationException {
        if (this.id == null || !this.idWritten && !objectIdWriter.alwaysAsId) return false;
        if (jsonGenerator.canWriteObjectId()) {
            jsonGenerator.writeObjectRef(String.valueOf(this.id));
            do {
                return true;
                break;
            } while (true);
        }
        objectIdWriter.serializer.serialize(this.id, jsonGenerator, serializerProvider);
        return true;
    }
}

