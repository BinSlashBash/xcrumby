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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BaseJsonNode;
import com.fasterxml.jackson.databind.node.BinaryNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ObjectNode
extends ContainerNode<ObjectNode> {
    protected final Map<String, JsonNode> _children;

    public ObjectNode(JsonNodeFactory jsonNodeFactory) {
        super(jsonNodeFactory);
        this._children = new LinkedHashMap<String, JsonNode>();
    }

    public ObjectNode(JsonNodeFactory jsonNodeFactory, Map<String, JsonNode> map) {
        super(jsonNodeFactory);
        this._children = map;
    }

    @Override
    protected JsonNode _at(JsonPointer jsonPointer) {
        return this.get(jsonPointer.getMatchingProperty());
    }

    protected boolean _childrenEqual(ObjectNode objectNode) {
        return this._children.equals(objectNode._children);
    }

    protected ObjectNode _put(String string2, JsonNode jsonNode) {
        this._children.put(string2, jsonNode);
        return this;
    }

    @Override
    public JsonToken asToken() {
        return JsonToken.START_OBJECT;
    }

    public ObjectNode deepCopy() {
        ObjectNode objectNode = new ObjectNode(this._nodeFactory);
        for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
            objectNode._children.put(entry.getKey(), (JsonNode)entry.getValue().deepCopy());
        }
        return objectNode;
    }

    @Override
    public Iterator<JsonNode> elements() {
        return this._children.values().iterator();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object == this) {
            return true;
        }
        boolean bl3 = bl2;
        if (object == null) return bl3;
        bl3 = bl2;
        if (!(object instanceof ObjectNode)) return bl3;
        return this._childrenEqual((ObjectNode)object);
    }

    @Override
    public Iterator<String> fieldNames() {
        return this._children.keySet().iterator();
    }

    @Override
    public Iterator<Map.Entry<String, JsonNode>> fields() {
        return this._children.entrySet().iterator();
    }

    @Override
    public ObjectNode findParent(String string2) {
        for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
            if (string2.equals(entry.getKey())) {
                return this;
            }
            if ((entry = entry.getValue().findParent(string2)) == null) continue;
            return (ObjectNode)((Object)entry);
        }
        return null;
    }

    @Override
    public List<JsonNode> findParents(String string2, List<JsonNode> list) {
        Iterator<Map.Entry<String, JsonNode>> iterator = this._children.entrySet().iterator();
        while (iterator.hasNext()) {
            Object object = iterator.next();
            if (string2.equals(object.getKey())) {
                object = list;
                if (list == null) {
                    object = new ArrayList<JsonNode>();
                }
                object.add(this);
                list = object;
                continue;
            }
            list = object.getValue().findParents(string2, list);
        }
        return list;
    }

    @Override
    public JsonNode findValue(String string2) {
        for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
            if (string2.equals(entry.getKey())) {
                return entry.getValue();
            }
            if ((entry = entry.getValue().findValue(string2)) == null) continue;
            return entry;
        }
        return null;
    }

    @Override
    public List<JsonNode> findValues(String string2, List<JsonNode> list) {
        for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
            if (string2.equals(entry.getKey())) {
                List<JsonNode> list2 = list;
                if (list == null) {
                    list2 = new ArrayList<JsonNode>();
                }
                list2.add(entry.getValue());
                list = list2;
                continue;
            }
            list = entry.getValue().findValues(string2, list);
        }
        return list;
    }

    @Override
    public List<String> findValuesAsText(String string2, List<String> list) {
        for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
            if (string2.equals(entry.getKey())) {
                List<String> list2 = list;
                if (list == null) {
                    list2 = new ArrayList<String>();
                }
                list2.add(entry.getValue().asText());
                list = list2;
                continue;
            }
            list = entry.getValue().findValuesAsText(string2, list);
        }
        return list;
    }

    @Override
    public JsonNode get(int n2) {
        return null;
    }

    @Override
    public JsonNode get(String string2) {
        return this._children.get(string2);
    }

    @Override
    public JsonNodeType getNodeType() {
        return JsonNodeType.OBJECT;
    }

    public int hashCode() {
        return this._children.hashCode();
    }

    @Override
    public JsonNode path(int n2) {
        return MissingNode.getInstance();
    }

    @Override
    public JsonNode path(String object) {
        if ((object = this._children.get(object)) != null) {
            return object;
        }
        return MissingNode.getInstance();
    }

    @Deprecated
    public JsonNode put(String string2, JsonNode jsonNode) {
        JsonNode jsonNode2 = jsonNode;
        if (jsonNode == null) {
            jsonNode2 = this.nullNode();
        }
        return this._children.put(string2, jsonNode2);
    }

    public ObjectNode put(String string2, double d2) {
        return this._put(string2, this.numberNode(d2));
    }

    public ObjectNode put(String string2, float f2) {
        return this._put(string2, this.numberNode(f2));
    }

    public ObjectNode put(String string2, int n2) {
        return this._put(string2, this.numberNode(n2));
    }

    public ObjectNode put(String string2, long l2) {
        return this._put(string2, this.numberNode(l2));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ObjectNode put(String string2, Boolean object) {
        if (object == null) {
            object = this.nullNode();
            do {
                return this._put(string2, (JsonNode)object);
                break;
            } while (true);
        }
        object = this.booleanNode(object.booleanValue());
        return this._put(string2, (JsonNode)object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ObjectNode put(String string2, Double object) {
        if (object == null) {
            object = this.nullNode();
            do {
                return this._put(string2, (JsonNode)object);
                break;
            } while (true);
        }
        object = this.numberNode(object.doubleValue());
        return this._put(string2, (JsonNode)object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ObjectNode put(String string2, Float object) {
        if (object == null) {
            object = this.nullNode();
            do {
                return this._put(string2, (JsonNode)object);
                break;
            } while (true);
        }
        object = this.numberNode(object.floatValue());
        return this._put(string2, (JsonNode)object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ObjectNode put(String string2, Integer object) {
        if (object == null) {
            object = this.nullNode();
            do {
                return this._put(string2, (JsonNode)object);
                break;
            } while (true);
        }
        object = this.numberNode(object.intValue());
        return this._put(string2, (JsonNode)object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ObjectNode put(String string2, Long object) {
        if (object == null) {
            object = this.nullNode();
            do {
                return this._put(string2, (JsonNode)object);
                break;
            } while (true);
        }
        object = this.numberNode(object.longValue());
        return this._put(string2, (JsonNode)object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ObjectNode put(String string2, Short object) {
        if (object == null) {
            object = this.nullNode();
            do {
                return this._put(string2, (JsonNode)object);
                break;
            } while (true);
        }
        object = this.numberNode(object.shortValue());
        return this._put(string2, (JsonNode)object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ObjectNode put(String string2, String object) {
        if (object == null) {
            object = this.nullNode();
            do {
                return this._put(string2, (JsonNode)object);
                break;
            } while (true);
        }
        object = this.textNode((String)object);
        return this._put(string2, (JsonNode)object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ObjectNode put(String string2, BigDecimal object) {
        if (object == null) {
            object = this.nullNode();
            do {
                return this._put(string2, (JsonNode)object);
                break;
            } while (true);
        }
        object = this.numberNode((BigDecimal)object);
        return this._put(string2, (JsonNode)object);
    }

    public ObjectNode put(String string2, short s2) {
        return this._put(string2, this.numberNode(s2));
    }

    public ObjectNode put(String string2, boolean bl2) {
        return this._put(string2, this.booleanNode(bl2));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ObjectNode put(String string2, byte[] object) {
        if (object == null) {
            object = this.nullNode();
            do {
                return this._put(string2, (JsonNode)object);
                break;
            } while (true);
        }
        object = this.binaryNode((byte[])object);
        return this._put(string2, (JsonNode)object);
    }

    @Deprecated
    public JsonNode putAll(ObjectNode objectNode) {
        return this.setAll(objectNode);
    }

    @Deprecated
    public JsonNode putAll(Map<String, ? extends JsonNode> map) {
        return this.setAll(map);
    }

    public ArrayNode putArray(String string2) {
        ArrayNode arrayNode = this.arrayNode();
        this._put(string2, arrayNode);
        return arrayNode;
    }

    public ObjectNode putNull(String string2) {
        this._children.put(string2, this.nullNode());
        return this;
    }

    public ObjectNode putObject(String string2) {
        ObjectNode objectNode = this.objectNode();
        this._put(string2, objectNode);
        return objectNode;
    }

    public ObjectNode putPOJO(String string2, Object object) {
        return this._put(string2, this.pojoNode(object));
    }

    public JsonNode remove(String string2) {
        return this._children.remove(string2);
    }

    public ObjectNode remove(Collection<String> collection) {
        this._children.keySet().removeAll(collection);
        return this;
    }

    @Override
    public ObjectNode removeAll() {
        this._children.clear();
        return this;
    }

    public JsonNode replace(String string2, JsonNode jsonNode) {
        JsonNode jsonNode2 = jsonNode;
        if (jsonNode == null) {
            jsonNode2 = this.nullNode();
        }
        return this._children.put(string2, jsonNode2);
    }

    public ObjectNode retain(Collection<String> collection) {
        this._children.keySet().retainAll(collection);
        return this;
    }

    public /* varargs */ ObjectNode retain(String ... arrstring) {
        return this.retain(Arrays.asList(arrstring));
    }

    @Override
    public void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
            jsonGenerator.writeFieldName(entry.getKey());
            ((BaseJsonNode)entry.getValue()).serialize(jsonGenerator, serializerProvider);
        }
        jsonGenerator.writeEndObject();
    }

    @Override
    public void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonProcessingException {
        typeSerializer.writeTypePrefixForObject(this, jsonGenerator);
        for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
            jsonGenerator.writeFieldName(entry.getKey());
            ((BaseJsonNode)entry.getValue()).serialize(jsonGenerator, serializerProvider);
        }
        typeSerializer.writeTypeSuffixForObject(this, jsonGenerator);
    }

    public JsonNode set(String string2, JsonNode jsonNode) {
        JsonNode jsonNode2 = jsonNode;
        if (jsonNode == null) {
            jsonNode2 = this.nullNode();
        }
        this._children.put(string2, jsonNode2);
        return this;
    }

    public JsonNode setAll(ObjectNode objectNode) {
        this._children.putAll(objectNode._children);
        return this;
    }

    public JsonNode setAll(Map<String, ? extends JsonNode> object) {
        for (Map.Entry<String, ? extends JsonNode> entry : object.entrySet()) {
            JsonNode jsonNode;
            object = jsonNode = entry.getValue();
            if (jsonNode == null) {
                object = this.nullNode();
            }
            this._children.put(entry.getKey(), (JsonNode)object);
        }
        return this;
    }

    @Override
    public int size() {
        return this._children.size();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder((this.size() << 4) + 32);
        stringBuilder.append("{");
        int n2 = 0;
        for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
            if (n2 > 0) {
                stringBuilder.append(",");
            }
            ++n2;
            TextNode.appendQuoted(stringBuilder, entry.getKey());
            stringBuilder.append(':');
            stringBuilder.append(entry.getValue().toString());
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public ObjectNode with(String string2) {
        JsonNode jsonNode = this._children.get(string2);
        if (jsonNode != null) {
            if (jsonNode instanceof ObjectNode) {
                return (ObjectNode)jsonNode;
            }
            throw new UnsupportedOperationException("Property '" + string2 + "' has value that is not of type ObjectNode (but " + jsonNode.getClass().getName() + ")");
        }
        jsonNode = this.objectNode();
        this._children.put(string2, jsonNode);
        return jsonNode;
    }

    @Override
    public ArrayNode withArray(String string2) {
        JsonNode jsonNode = this._children.get(string2);
        if (jsonNode != null) {
            if (jsonNode instanceof ArrayNode) {
                return (ArrayNode)jsonNode;
            }
            throw new UnsupportedOperationException("Property '" + string2 + "' has value that is not of type ArrayNode (but " + jsonNode.getClass().getName() + ")");
        }
        jsonNode = this.arrayNode();
        this._children.put(string2, jsonNode);
        return jsonNode;
    }

    public JsonNode without(String string2) {
        this._children.remove(string2);
        return this;
    }

    public ObjectNode without(Collection<String> collection) {
        this._children.keySet().removeAll(collection);
        return this;
    }
}

