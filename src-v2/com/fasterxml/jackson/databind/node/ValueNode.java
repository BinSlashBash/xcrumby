/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.BaseJsonNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.List;

public abstract class ValueNode
extends BaseJsonNode {
    protected ValueNode() {
    }

    @Override
    protected JsonNode _at(JsonPointer jsonPointer) {
        return MissingNode.getInstance();
    }

    @Override
    public abstract JsonToken asToken();

    @Override
    public <T extends JsonNode> T deepCopy() {
        return (T)this;
    }

    @Override
    public final ObjectNode findParent(String string2) {
        return null;
    }

    @Override
    public final List<JsonNode> findParents(String string2, List<JsonNode> list) {
        return list;
    }

    @Override
    public final JsonNode findValue(String string2) {
        return null;
    }

    @Override
    public final List<JsonNode> findValues(String string2, List<JsonNode> list) {
        return list;
    }

    @Override
    public final List<String> findValuesAsText(String string2, List<String> list) {
        return list;
    }

    @Override
    public final JsonNode get(int n2) {
        return null;
    }

    @Override
    public final JsonNode get(String string2) {
        return null;
    }

    @Override
    public final boolean has(int n2) {
        return false;
    }

    @Override
    public final boolean has(String string2) {
        return false;
    }

    @Override
    public final boolean hasNonNull(int n2) {
        return false;
    }

    @Override
    public final boolean hasNonNull(String string2) {
        return false;
    }

    @Override
    public final JsonNode path(int n2) {
        return MissingNode.getInstance();
    }

    @Override
    public final JsonNode path(String string2) {
        return MissingNode.getInstance();
    }

    @Override
    public void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonProcessingException {
        typeSerializer.writeTypePrefixForScalar(this, jsonGenerator);
        this.serialize(jsonGenerator, serializerProvider);
        typeSerializer.writeTypeSuffixForScalar(this, jsonGenerator);
    }

    @Override
    public String toString() {
        return this.asText();
    }
}

