/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ValueNode;
import java.io.IOException;

public final class NullNode
extends ValueNode {
    public static final NullNode instance = new NullNode();

    private NullNode() {
    }

    public static NullNode getInstance() {
        return instance;
    }

    @Override
    public String asText() {
        return "null";
    }

    @Override
    public String asText(String string2) {
        return string2;
    }

    @Override
    public JsonToken asToken() {
        return JsonToken.VALUE_NULL;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        return false;
    }

    @Override
    public JsonNodeType getNodeType() {
        return JsonNodeType.NULL;
    }

    @Override
    public final void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        serializerProvider.defaultSerializeNull(jsonGenerator);
    }
}

