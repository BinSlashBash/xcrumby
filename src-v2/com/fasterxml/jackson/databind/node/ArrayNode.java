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
import com.fasterxml.jackson.databind.node.BinaryNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ArrayNode
extends ContainerNode<ArrayNode> {
    private final List<JsonNode> _children = new ArrayList<JsonNode>();

    public ArrayNode(JsonNodeFactory jsonNodeFactory) {
        super(jsonNodeFactory);
    }

    protected ArrayNode _add(JsonNode jsonNode) {
        this._children.add(jsonNode);
        return this;
    }

    @Override
    protected JsonNode _at(JsonPointer jsonPointer) {
        return this.get(jsonPointer.getMatchingIndex());
    }

    protected boolean _childrenEqual(ArrayNode arrayNode) {
        return this._children.equals(arrayNode._children);
    }

    protected ArrayNode _insert(int n2, JsonNode jsonNode) {
        if (n2 < 0) {
            this._children.add(0, jsonNode);
            return this;
        }
        if (n2 >= this._children.size()) {
            this._children.add(jsonNode);
            return this;
        }
        this._children.add(n2, jsonNode);
        return this;
    }

    public ArrayNode add(double d2) {
        return this._add(this.numberNode(d2));
    }

    public ArrayNode add(float f2) {
        return this._add(this.numberNode(f2));
    }

    public ArrayNode add(int n2) {
        this._add(this.numberNode(n2));
        return this;
    }

    public ArrayNode add(long l2) {
        return this._add(this.numberNode(l2));
    }

    public ArrayNode add(JsonNode jsonNode) {
        JsonNode jsonNode2 = jsonNode;
        if (jsonNode == null) {
            jsonNode2 = this.nullNode();
        }
        this._add(jsonNode2);
        return this;
    }

    public ArrayNode add(Boolean bl2) {
        if (bl2 == null) {
            return this.addNull();
        }
        return this._add(this.booleanNode(bl2));
    }

    public ArrayNode add(Double d2) {
        if (d2 == null) {
            return this.addNull();
        }
        return this._add(this.numberNode((double)d2));
    }

    public ArrayNode add(Float f2) {
        if (f2 == null) {
            return this.addNull();
        }
        return this._add(this.numberNode(f2.floatValue()));
    }

    public ArrayNode add(Integer n2) {
        if (n2 == null) {
            return this.addNull();
        }
        return this._add(this.numberNode((int)n2));
    }

    public ArrayNode add(Long l2) {
        if (l2 == null) {
            return this.addNull();
        }
        return this._add(this.numberNode((long)l2));
    }

    public ArrayNode add(String string2) {
        if (string2 == null) {
            return this.addNull();
        }
        return this._add(this.textNode(string2));
    }

    public ArrayNode add(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return this.addNull();
        }
        return this._add(this.numberNode(bigDecimal));
    }

    public ArrayNode add(boolean bl2) {
        return this._add(this.booleanNode(bl2));
    }

    public ArrayNode add(byte[] arrby) {
        if (arrby == null) {
            return this.addNull();
        }
        return this._add(this.binaryNode(arrby));
    }

    public ArrayNode addAll(ArrayNode arrayNode) {
        this._children.addAll(arrayNode._children);
        return this;
    }

    public ArrayNode addAll(Collection<? extends JsonNode> collection) {
        this._children.addAll(collection);
        return this;
    }

    public ArrayNode addArray() {
        ArrayNode arrayNode = this.arrayNode();
        this._add(arrayNode);
        return arrayNode;
    }

    public ArrayNode addNull() {
        this._add(this.nullNode());
        return this;
    }

    public ObjectNode addObject() {
        ObjectNode objectNode = this.objectNode();
        this._add(objectNode);
        return objectNode;
    }

    public ArrayNode addPOJO(Object object) {
        if (object == null) {
            this.addNull();
            return this;
        }
        this._add(this.pojoNode(object));
        return this;
    }

    @Override
    public JsonToken asToken() {
        return JsonToken.START_ARRAY;
    }

    public ArrayNode deepCopy() {
        ArrayNode arrayNode = new ArrayNode(this._nodeFactory);
        for (JsonNode jsonNode : this._children) {
            arrayNode._children.add((JsonNode)jsonNode.deepCopy());
        }
        return arrayNode;
    }

    @Override
    public Iterator<JsonNode> elements() {
        return this._children.iterator();
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
        if (!(object instanceof ArrayNode)) return bl3;
        return this._children.equals(((ArrayNode)object)._children);
    }

    @Override
    public ObjectNode findParent(String string2) {
        Iterator<JsonNode> iterator = this._children.iterator();
        while (iterator.hasNext()) {
            JsonNode jsonNode = iterator.next().findParent(string2);
            if (jsonNode == null) continue;
            return (ObjectNode)jsonNode;
        }
        return null;
    }

    @Override
    public List<JsonNode> findParents(String string2, List<JsonNode> list) {
        Iterator<JsonNode> iterator = this._children.iterator();
        while (iterator.hasNext()) {
            list = iterator.next().findParents(string2, list);
        }
        return list;
    }

    @Override
    public JsonNode findValue(String string2) {
        Iterator<JsonNode> iterator = this._children.iterator();
        while (iterator.hasNext()) {
            JsonNode jsonNode = iterator.next().findValue(string2);
            if (jsonNode == null) continue;
            return jsonNode;
        }
        return null;
    }

    @Override
    public List<JsonNode> findValues(String string2, List<JsonNode> list) {
        Iterator<JsonNode> iterator = this._children.iterator();
        while (iterator.hasNext()) {
            list = iterator.next().findValues(string2, list);
        }
        return list;
    }

    @Override
    public List<String> findValuesAsText(String string2, List<String> list) {
        Iterator<JsonNode> iterator = this._children.iterator();
        while (iterator.hasNext()) {
            list = iterator.next().findValuesAsText(string2, list);
        }
        return list;
    }

    @Override
    public JsonNode get(int n2) {
        if (n2 >= 0 && n2 < this._children.size()) {
            return this._children.get(n2);
        }
        return null;
    }

    @Override
    public JsonNode get(String string2) {
        return null;
    }

    @Override
    public JsonNodeType getNodeType() {
        return JsonNodeType.ARRAY;
    }

    public int hashCode() {
        return this._children.hashCode();
    }

    public ArrayNode insert(int n2, double d2) {
        return this._insert(n2, this.numberNode(d2));
    }

    public ArrayNode insert(int n2, float f2) {
        return this._insert(n2, this.numberNode(f2));
    }

    public ArrayNode insert(int n2, int n3) {
        this._insert(n2, this.numberNode(n3));
        return this;
    }

    public ArrayNode insert(int n2, long l2) {
        return this._insert(n2, this.numberNode(l2));
    }

    public ArrayNode insert(int n2, JsonNode jsonNode) {
        JsonNode jsonNode2 = jsonNode;
        if (jsonNode == null) {
            jsonNode2 = this.nullNode();
        }
        this._insert(n2, jsonNode2);
        return this;
    }

    public ArrayNode insert(int n2, Boolean bl2) {
        if (bl2 == null) {
            return this.insertNull(n2);
        }
        return this._insert(n2, this.booleanNode(bl2));
    }

    public ArrayNode insert(int n2, Double d2) {
        if (d2 == null) {
            return this.insertNull(n2);
        }
        return this._insert(n2, this.numberNode((double)d2));
    }

    public ArrayNode insert(int n2, Float f2) {
        if (f2 == null) {
            return this.insertNull(n2);
        }
        return this._insert(n2, this.numberNode(f2.floatValue()));
    }

    public ArrayNode insert(int n2, Integer n3) {
        if (n3 == null) {
            this.insertNull(n2);
            return this;
        }
        this._insert(n2, this.numberNode((int)n3));
        return this;
    }

    public ArrayNode insert(int n2, Long l2) {
        if (l2 == null) {
            return this.insertNull(n2);
        }
        return this._insert(n2, this.numberNode((long)l2));
    }

    public ArrayNode insert(int n2, String string2) {
        if (string2 == null) {
            return this.insertNull(n2);
        }
        return this._insert(n2, this.textNode(string2));
    }

    public ArrayNode insert(int n2, BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return this.insertNull(n2);
        }
        return this._insert(n2, this.numberNode(bigDecimal));
    }

    public ArrayNode insert(int n2, boolean bl2) {
        return this._insert(n2, this.booleanNode(bl2));
    }

    public ArrayNode insert(int n2, byte[] arrby) {
        if (arrby == null) {
            return this.insertNull(n2);
        }
        return this._insert(n2, this.binaryNode(arrby));
    }

    public ArrayNode insertArray(int n2) {
        ArrayNode arrayNode = this.arrayNode();
        this._insert(n2, arrayNode);
        return arrayNode;
    }

    public ArrayNode insertNull(int n2) {
        this._insert(n2, this.nullNode());
        return this;
    }

    public ObjectNode insertObject(int n2) {
        ObjectNode objectNode = this.objectNode();
        this._insert(n2, objectNode);
        return objectNode;
    }

    public ArrayNode insertPOJO(int n2, Object object) {
        if (object == null) {
            return this.insertNull(n2);
        }
        return this._insert(n2, this.pojoNode(object));
    }

    @Override
    public JsonNode path(int n2) {
        if (n2 >= 0 && n2 < this._children.size()) {
            return this._children.get(n2);
        }
        return MissingNode.getInstance();
    }

    @Override
    public JsonNode path(String string2) {
        return MissingNode.getInstance();
    }

    public JsonNode remove(int n2) {
        if (n2 >= 0 && n2 < this._children.size()) {
            return this._children.remove(n2);
        }
        return null;
    }

    @Override
    public ArrayNode removeAll() {
        this._children.clear();
        return this;
    }

    @Override
    public void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartArray();
        Iterator<JsonNode> iterator = this._children.iterator();
        while (iterator.hasNext()) {
            ((BaseJsonNode)iterator.next()).serialize(jsonGenerator, serializerProvider);
        }
        jsonGenerator.writeEndArray();
    }

    @Override
    public void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonProcessingException {
        typeSerializer.writeTypePrefixForArray(this, jsonGenerator);
        Iterator<JsonNode> iterator = this._children.iterator();
        while (iterator.hasNext()) {
            ((BaseJsonNode)iterator.next()).serialize(jsonGenerator, serializerProvider);
        }
        typeSerializer.writeTypeSuffixForArray(this, jsonGenerator);
    }

    public JsonNode set(int n2, JsonNode jsonNode) {
        JsonNode jsonNode2 = jsonNode;
        if (jsonNode == null) {
            jsonNode2 = this.nullNode();
        }
        if (n2 < 0 || n2 >= this._children.size()) {
            throw new IndexOutOfBoundsException("Illegal index " + n2 + ", array size " + this.size());
        }
        return this._children.set(n2, jsonNode2);
    }

    @Override
    public int size() {
        return this._children.size();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder((this.size() << 4) + 16);
        stringBuilder.append('[');
        int n2 = this._children.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            if (i2 > 0) {
                stringBuilder.append(',');
            }
            stringBuilder.append(this._children.get(i2).toString());
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}

