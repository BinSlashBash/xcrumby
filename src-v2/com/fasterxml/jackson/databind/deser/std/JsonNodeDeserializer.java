/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.BaseNodeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;

public class JsonNodeDeserializer
extends BaseNodeDeserializer<JsonNode> {
    private static final JsonNodeDeserializer instance = new JsonNodeDeserializer();

    protected JsonNodeDeserializer() {
        super(JsonNode.class);
    }

    public static JsonDeserializer<? extends JsonNode> getDeserializer(Class<?> class_) {
        if (class_ == ObjectNode.class) {
            return ObjectDeserializer.getInstance();
        }
        if (class_ == ArrayNode.class) {
            return ArrayDeserializer.getInstance();
        }
        return instance;
    }

    @Override
    public JsonNode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        switch (jsonParser.getCurrentTokenId()) {
            default: {
                return this.deserializeAny(jsonParser, deserializationContext, deserializationContext.getNodeFactory());
            }
            case 1: {
                return this.deserializeObject(jsonParser, deserializationContext, deserializationContext.getNodeFactory());
            }
            case 3: 
        }
        return this.deserializeArray(jsonParser, deserializationContext, deserializationContext.getNodeFactory());
    }

    @Override
    public JsonNode getNullValue() {
        return NullNode.getInstance();
    }

    static final class ArrayDeserializer
    extends BaseNodeDeserializer<ArrayNode> {
        protected static final ArrayDeserializer _instance = new ArrayDeserializer();
        private static final long serialVersionUID = 1;

        protected ArrayDeserializer() {
            super(ArrayNode.class);
        }

        public static ArrayDeserializer getInstance() {
            return _instance;
        }

        @Override
        public ArrayNode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (jsonParser.isExpectedStartArrayToken()) {
                return this.deserializeArray(jsonParser, deserializationContext, deserializationContext.getNodeFactory());
            }
            throw deserializationContext.mappingException(ArrayNode.class);
        }
    }

    static final class ObjectDeserializer
    extends BaseNodeDeserializer<ObjectNode> {
        protected static final ObjectDeserializer _instance = new ObjectDeserializer();
        private static final long serialVersionUID = 1;

        protected ObjectDeserializer() {
            super(ObjectNode.class);
        }

        public static ObjectDeserializer getInstance() {
            return _instance;
        }

        @Override
        public ObjectNode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            if (jsonParser.getCurrentToken() == JsonToken.START_OBJECT) {
                jsonParser.nextToken();
                return this.deserializeObject(jsonParser, deserializationContext, deserializationContext.getNodeFactory());
            }
            if (jsonParser.getCurrentToken() == JsonToken.FIELD_NAME) {
                return this.deserializeObject(jsonParser, deserializationContext, deserializationContext.getNodeFactory());
            }
            throw deserializationContext.mappingException(ObjectNode.class);
        }
    }

}

