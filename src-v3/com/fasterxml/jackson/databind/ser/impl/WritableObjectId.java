package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public final class WritableObjectId {
    public final ObjectIdGenerator<?> generator;
    public Object id;
    protected boolean idWritten;

    public WritableObjectId(ObjectIdGenerator<?> generator) {
        this.idWritten = false;
        this.generator = generator;
    }

    public boolean writeAsId(JsonGenerator jgen, SerializerProvider provider, ObjectIdWriter w) throws IOException, JsonGenerationException {
        if (this.id == null || (!this.idWritten && !w.alwaysAsId)) {
            return false;
        }
        if (jgen.canWriteObjectId()) {
            jgen.writeObjectRef(String.valueOf(this.id));
        } else {
            w.serializer.serialize(this.id, jgen, provider);
        }
        return true;
    }

    public Object generateId(Object forPojo) {
        Object generateId = this.generator.generateId(forPojo);
        this.id = generateId;
        return generateId;
    }

    public void writeAsField(JsonGenerator jgen, SerializerProvider provider, ObjectIdWriter w) throws IOException, JsonGenerationException {
        this.idWritten = true;
        if (jgen.canWriteObjectId()) {
            jgen.writeObjectId(String.valueOf(this.id));
            return;
        }
        SerializableString name = w.propertyName;
        if (name != null) {
            jgen.writeFieldName(name);
            w.serializer.serialize(this.id, jgen, provider);
        }
    }
}
