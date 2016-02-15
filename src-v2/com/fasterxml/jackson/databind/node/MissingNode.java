/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ValueNode;
import java.io.IOException;

public final class MissingNode
extends ValueNode {
    private static final MissingNode instance = new MissingNode();

    private MissingNode() {
    }

    public static MissingNode getInstance() {
        return instance;
    }

    @Override
    public String asText() {
        return "";
    }

    @Override
    public String asText(String string2) {
        return string2;
    }

    @Override
    public JsonToken asToken() {
        return JsonToken.NOT_AVAILABLE;
    }

    @Override
    public <T extends JsonNode> T deepCopy() {
        return (T)this;
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
        return JsonNodeType.MISSING;
    }

    @Override
    public final void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeNull();
    }

    @Override
    public void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonProcessingException {
        jsonGenerator.writeNull();
    }

    @Override
    public String toString() {
        return "";
    }
}

