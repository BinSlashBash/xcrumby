/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BaseJsonNode;
import com.fasterxml.jackson.databind.node.BinaryNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.JsonNodeCreator;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.POJONode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class ContainerNode<T extends ContainerNode<T>>
extends BaseJsonNode
implements JsonNodeCreator {
    protected final JsonNodeFactory _nodeFactory;

    protected ContainerNode(JsonNodeFactory jsonNodeFactory) {
        this._nodeFactory = jsonNodeFactory;
    }

    @Deprecated
    public final POJONode POJONode(Object object) {
        return (POJONode)this._nodeFactory.pojoNode(object);
    }

    @Override
    public final ArrayNode arrayNode() {
        return this._nodeFactory.arrayNode();
    }

    @Override
    public String asText() {
        return "";
    }

    @Override
    public abstract JsonToken asToken();

    @Override
    public final BinaryNode binaryNode(byte[] arrby) {
        return this._nodeFactory.binaryNode(arrby);
    }

    @Override
    public final BinaryNode binaryNode(byte[] arrby, int n2, int n3) {
        return this._nodeFactory.binaryNode(arrby, n2, n3);
    }

    @Override
    public final BooleanNode booleanNode(boolean bl2) {
        return this._nodeFactory.booleanNode(bl2);
    }

    @Override
    public abstract JsonNode get(int var1);

    @Override
    public abstract JsonNode get(String var1);

    @Override
    public final NullNode nullNode() {
        return this._nodeFactory.nullNode();
    }

    @Override
    public final NumericNode numberNode(byte by2) {
        return this._nodeFactory.numberNode(by2);
    }

    @Override
    public final NumericNode numberNode(double d2) {
        return this._nodeFactory.numberNode(d2);
    }

    @Override
    public final NumericNode numberNode(float f2) {
        return this._nodeFactory.numberNode(f2);
    }

    @Override
    public final NumericNode numberNode(int n2) {
        return this._nodeFactory.numberNode(n2);
    }

    @Override
    public final NumericNode numberNode(long l2) {
        return this._nodeFactory.numberNode(l2);
    }

    @Override
    public final NumericNode numberNode(BigDecimal bigDecimal) {
        return this._nodeFactory.numberNode(bigDecimal);
    }

    @Override
    public final NumericNode numberNode(BigInteger bigInteger) {
        return this._nodeFactory.numberNode(bigInteger);
    }

    @Override
    public final NumericNode numberNode(short s2) {
        return this._nodeFactory.numberNode(s2);
    }

    @Override
    public final ValueNode numberNode(Byte by2) {
        return this._nodeFactory.numberNode(by2);
    }

    @Override
    public final ValueNode numberNode(Double d2) {
        return this._nodeFactory.numberNode(d2);
    }

    @Override
    public final ValueNode numberNode(Float f2) {
        return this._nodeFactory.numberNode(f2);
    }

    @Override
    public final ValueNode numberNode(Integer n2) {
        return this._nodeFactory.numberNode(n2);
    }

    @Override
    public final ValueNode numberNode(Long l2) {
        return this._nodeFactory.numberNode(l2);
    }

    @Override
    public final ValueNode numberNode(Short s2) {
        return this._nodeFactory.numberNode(s2);
    }

    @Override
    public final ObjectNode objectNode() {
        return this._nodeFactory.objectNode();
    }

    @Override
    public final ValueNode pojoNode(Object object) {
        return this._nodeFactory.pojoNode(object);
    }

    public abstract T removeAll();

    @Override
    public abstract int size();

    @Override
    public final TextNode textNode(String string2) {
        return this._nodeFactory.textNode(string2);
    }
}

