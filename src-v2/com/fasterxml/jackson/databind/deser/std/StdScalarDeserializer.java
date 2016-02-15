/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;

public abstract class StdScalarDeserializer<T>
extends StdDeserializer<T> {
    private static final long serialVersionUID = 1;

    protected StdScalarDeserializer(JavaType javaType) {
        super(javaType);
    }

    protected StdScalarDeserializer(Class<?> class_) {
        super(class_);
    }

    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromScalar(jsonParser, deserializationContext);
    }
}

