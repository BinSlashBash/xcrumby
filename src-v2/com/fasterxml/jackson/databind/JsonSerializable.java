/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;

public interface JsonSerializable {
    public void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException, JsonProcessingException;

    public void serializeWithType(JsonGenerator var1, SerializerProvider var2, TypeSerializer var3) throws IOException, JsonProcessingException;
}

