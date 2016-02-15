/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BinaryNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

abstract class BaseNodeDeserializer<T extends JsonNode>
extends StdDeserializer<T> {
    public BaseNodeDeserializer(Class<T> class_) {
        super(class_);
    }

    protected final JsonNode _fromEmbedded(JsonParser object, DeserializationContext serializable, JsonNodeFactory jsonNodeFactory) throws IOException {
        if ((object = object.getEmbeddedObject()) == null) {
            return jsonNodeFactory.nullNode();
        }
        serializable = object.getClass();
        if (serializable == byte[].class) {
            return jsonNodeFactory.binaryNode((byte[])object);
        }
        if (JsonNode.class.isAssignableFrom(serializable)) {
            return (JsonNode)object;
        }
        return jsonNodeFactory.pojoNode(object);
    }

    protected final JsonNode _fromFloat(JsonParser jsonParser, DeserializationContext deserializationContext, JsonNodeFactory jsonNodeFactory) throws IOException {
        if (jsonParser.getNumberType() == JsonParser.NumberType.BIG_DECIMAL || deserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
            return jsonNodeFactory.numberNode(jsonParser.getDecimalValue());
        }
        return jsonNodeFactory.numberNode(jsonParser.getDoubleValue());
    }

    protected final JsonNode _fromInt(JsonParser jsonParser, DeserializationContext deserializationContext, JsonNodeFactory jsonNodeFactory) throws IOException {
        JsonParser.NumberType numberType = jsonParser.getNumberType();
        if (numberType == JsonParser.NumberType.BIG_INTEGER || deserializationContext.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
            return jsonNodeFactory.numberNode(jsonParser.getBigIntegerValue());
        }
        if (numberType == JsonParser.NumberType.INT) {
            return jsonNodeFactory.numberNode(jsonParser.getIntValue());
        }
        return jsonNodeFactory.numberNode(jsonParser.getLongValue());
    }

    protected void _handleDuplicateField(JsonParser jsonParser, DeserializationContext deserializationContext, JsonNodeFactory jsonNodeFactory, String string2, ObjectNode objectNode, JsonNode jsonNode, JsonNode jsonNode2) throws JsonProcessingException {
        if (deserializationContext.isEnabled(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY)) {
            this._reportProblem(jsonParser, "Duplicate field '" + string2 + "' for ObjectNode: not allowed when FAIL_ON_READING_DUP_TREE_KEY enabled");
        }
        this._handleDuplicateField(string2, objectNode, jsonNode, jsonNode2);
    }

    @Deprecated
    protected void _handleDuplicateField(String string2, ObjectNode objectNode, JsonNode jsonNode, JsonNode jsonNode2) throws JsonProcessingException {
    }

    protected void _reportProblem(JsonParser jsonParser, String string2) throws JsonMappingException {
        throw new JsonMappingException(string2, jsonParser.getTokenLocation());
    }

    protected final JsonNode deserializeAny(JsonParser jsonParser, DeserializationContext deserializationContext, JsonNodeFactory jsonNodeFactory) throws IOException {
        switch (jsonParser.getCurrentTokenId()) {
            default: {
                throw deserializationContext.mappingException(this.handledType());
            }
            case 1: 
            case 2: {
                return this.deserializeObject(jsonParser, deserializationContext, jsonNodeFactory);
            }
            case 3: {
                return this.deserializeArray(jsonParser, deserializationContext, jsonNodeFactory);
            }
            case 5: {
                return this.deserializeObject(jsonParser, deserializationContext, jsonNodeFactory);
            }
            case 12: {
                return this._fromEmbedded(jsonParser, deserializationContext, jsonNodeFactory);
            }
            case 6: {
                return jsonNodeFactory.textNode(jsonParser.getText());
            }
            case 7: {
                return this._fromInt(jsonParser, deserializationContext, jsonNodeFactory);
            }
            case 8: {
                return this._fromFloat(jsonParser, deserializationContext, jsonNodeFactory);
            }
            case 9: {
                return jsonNodeFactory.booleanNode(true);
            }
            case 10: {
                return jsonNodeFactory.booleanNode(false);
            }
            case 11: 
        }
        return jsonNodeFactory.nullNode();
    }

    protected final ArrayNode deserializeArray(JsonParser jsonParser, DeserializationContext deserializationContext, JsonNodeFactory jsonNodeFactory) throws IOException, JsonProcessingException {
        ArrayNode arrayNode = jsonNodeFactory.arrayNode();
        block10 : do {
            JsonToken jsonToken;
            if ((jsonToken = jsonParser.nextToken()) == null) {
                throw deserializationContext.mappingException("Unexpected end-of-input when binding data into ArrayNode");
            }
            switch (jsonToken.id()) {
                default: {
                    arrayNode.add(this.deserializeAny(jsonParser, deserializationContext, jsonNodeFactory));
                    continue block10;
                }
                case 1: {
                    arrayNode.add(this.deserializeObject(jsonParser, deserializationContext, jsonNodeFactory));
                    continue block10;
                }
                case 3: {
                    arrayNode.add(this.deserializeArray(jsonParser, deserializationContext, jsonNodeFactory));
                    continue block10;
                }
                case 6: {
                    arrayNode.add(jsonNodeFactory.textNode(jsonParser.getText()));
                    continue block10;
                }
                case 7: {
                    arrayNode.add(this._fromInt(jsonParser, deserializationContext, jsonNodeFactory));
                    continue block10;
                }
                case 9: {
                    arrayNode.add(jsonNodeFactory.booleanNode(true));
                    continue block10;
                }
                case 10: {
                    arrayNode.add(jsonNodeFactory.booleanNode(false));
                    continue block10;
                }
                case 11: {
                    arrayNode.add(jsonNodeFactory.nullNode());
                    continue block10;
                }
                case 4: 
            }
            break;
        } while (true);
        return arrayNode;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final ObjectNode deserializeObject(JsonParser jsonParser, DeserializationContext deserializationContext, JsonNodeFactory jsonNodeFactory) throws IOException, JsonProcessingException {
        Object object;
        ObjectNode objectNode = jsonNodeFactory.objectNode();
        Object object2 = object = jsonParser.getCurrentToken();
        if (object == JsonToken.START_OBJECT) {
            object2 = jsonParser.nextToken();
        }
        while (object2 == JsonToken.FIELD_NAME) {
            JsonNode jsonNode;
            object = jsonParser.getCurrentName();
            switch (jsonParser.nextToken().id()) {
                default: {
                    object2 = this.deserializeAny(jsonParser, deserializationContext, jsonNodeFactory);
                    break;
                }
                case 1: {
                    object2 = this.deserializeObject(jsonParser, deserializationContext, jsonNodeFactory);
                    break;
                }
                case 3: {
                    object2 = this.deserializeArray(jsonParser, deserializationContext, jsonNodeFactory);
                    break;
                }
                case 6: {
                    object2 = jsonNodeFactory.textNode(jsonParser.getText());
                    break;
                }
                case 7: {
                    object2 = this._fromInt(jsonParser, deserializationContext, jsonNodeFactory);
                    break;
                }
                case 9: {
                    object2 = jsonNodeFactory.booleanNode(true);
                    break;
                }
                case 10: {
                    object2 = jsonNodeFactory.booleanNode(false);
                    break;
                }
                case 11: {
                    object2 = jsonNodeFactory.nullNode();
                }
            }
            if ((jsonNode = objectNode.replace((String)object, (JsonNode)object2)) != null) {
                this._handleDuplicateField(jsonParser, deserializationContext, jsonNodeFactory, (String)object, objectNode, jsonNode, (JsonNode)object2);
            }
            object2 = jsonParser.nextToken();
        }
        return objectNode;
    }

    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromAny(jsonParser, deserializationContext);
    }
}

