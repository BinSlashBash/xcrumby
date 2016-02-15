/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import java.io.IOException;

public abstract class BaseJsonNode
extends JsonNode
implements JsonSerializable {
    protected BaseJsonNode() {
    }

    @Override
    public abstract JsonToken asToken();

    @Override
    public final JsonNode findPath(String object) {
        JsonNode jsonNode;
        object = jsonNode = this.findValue((String)object);
        if (jsonNode == null) {
            object = MissingNode.getInstance();
        }
        return object;
    }

    @Override
    public JsonParser.NumberType numberType() {
        return null;
    }

    @Override
    public abstract void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException, JsonProcessingException;

    @Override
    public abstract void serializeWithType(JsonGenerator var1, SerializerProvider var2, TypeSerializer var3) throws IOException, JsonProcessingException;

    @Override
    public JsonParser traverse() {
        return new TreeTraversingParser(this);
    }

    @Override
    public JsonParser traverse(ObjectCodec objectCodec) {
        return new TreeTraversingParser(this, objectCodec);
    }
}

