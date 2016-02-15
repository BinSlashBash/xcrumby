/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import java.io.IOException;

public abstract class NonTypedScalarSerializerBase<T>
extends StdScalarSerializer<T> {
    protected NonTypedScalarSerializerBase(Class<T> class_) {
        super(class_);
    }

    @Override
    public final void serializeWithType(T t2, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        this.serialize(t2, jsonGenerator, serializerProvider);
    }
}

