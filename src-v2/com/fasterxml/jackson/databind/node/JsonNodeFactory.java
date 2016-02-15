/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BigIntegerNode;
import com.fasterxml.jackson.databind.node.BinaryNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.DecimalNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.FloatNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.JsonNodeCreator;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.POJONode;
import com.fasterxml.jackson.databind.node.ShortNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonNodeFactory
implements Serializable,
JsonNodeCreator {
    private static final JsonNodeFactory decimalsAsIs;
    private static final JsonNodeFactory decimalsNormalized;
    public static final JsonNodeFactory instance;
    private static final long serialVersionUID = -3271940633258788634L;
    private final boolean _cfgBigDecimalExact;

    static {
        decimalsNormalized = new JsonNodeFactory(false);
        decimalsAsIs = new JsonNodeFactory(true);
        instance = decimalsNormalized;
    }

    protected JsonNodeFactory() {
        this(false);
    }

    public JsonNodeFactory(boolean bl2) {
        this._cfgBigDecimalExact = bl2;
    }

    public static JsonNodeFactory withExactBigDecimals(boolean bl2) {
        if (bl2) {
            return decimalsAsIs;
        }
        return decimalsNormalized;
    }

    @Deprecated
    public POJONode POJONode(Object object) {
        return new POJONode(object);
    }

    protected boolean _inIntRange(long l2) {
        if ((long)((int)l2) == l2) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayNode arrayNode() {
        return new ArrayNode(this);
    }

    @Override
    public BinaryNode binaryNode(byte[] arrby) {
        return BinaryNode.valueOf(arrby);
    }

    @Override
    public BinaryNode binaryNode(byte[] arrby, int n2, int n3) {
        return BinaryNode.valueOf(arrby, n2, n3);
    }

    @Override
    public BooleanNode booleanNode(boolean bl2) {
        if (bl2) {
            return BooleanNode.getTrue();
        }
        return BooleanNode.getFalse();
    }

    @Override
    public NullNode nullNode() {
        return NullNode.getInstance();
    }

    @Override
    public NumericNode numberNode(byte by2) {
        return IntNode.valueOf(by2);
    }

    @Override
    public NumericNode numberNode(double d2) {
        return DoubleNode.valueOf(d2);
    }

    @Override
    public NumericNode numberNode(float f2) {
        return FloatNode.valueOf(f2);
    }

    @Override
    public NumericNode numberNode(int n2) {
        return IntNode.valueOf(n2);
    }

    @Override
    public NumericNode numberNode(long l2) {
        if (this._inIntRange(l2)) {
            return IntNode.valueOf((int)l2);
        }
        return LongNode.valueOf(l2);
    }

    @Override
    public NumericNode numberNode(BigDecimal bigDecimal) {
        if (this._cfgBigDecimalExact) {
            return DecimalNode.valueOf(bigDecimal);
        }
        if (bigDecimal.compareTo(BigDecimal.ZERO) == 0) {
            return DecimalNode.ZERO;
        }
        return DecimalNode.valueOf(bigDecimal.stripTrailingZeros());
    }

    @Override
    public NumericNode numberNode(BigInteger bigInteger) {
        return BigIntegerNode.valueOf(bigInteger);
    }

    @Override
    public NumericNode numberNode(short s2) {
        return ShortNode.valueOf(s2);
    }

    @Override
    public ValueNode numberNode(Byte by2) {
        if (by2 == null) {
            return this.nullNode();
        }
        return IntNode.valueOf(by2.intValue());
    }

    @Override
    public ValueNode numberNode(Double d2) {
        if (d2 == null) {
            return this.nullNode();
        }
        return DoubleNode.valueOf(d2);
    }

    @Override
    public ValueNode numberNode(Float f2) {
        if (f2 == null) {
            return this.nullNode();
        }
        return FloatNode.valueOf(f2.floatValue());
    }

    @Override
    public ValueNode numberNode(Integer n2) {
        if (n2 == null) {
            return this.nullNode();
        }
        return IntNode.valueOf(n2);
    }

    @Override
    public ValueNode numberNode(Long l2) {
        if (l2 == null) {
            return this.nullNode();
        }
        long l3 = l2;
        if (this._inIntRange(l3)) {
            return IntNode.valueOf((int)l3);
        }
        return LongNode.valueOf(l3);
    }

    @Override
    public ValueNode numberNode(Short s2) {
        if (s2 == null) {
            return this.nullNode();
        }
        return ShortNode.valueOf(s2);
    }

    @Override
    public ObjectNode objectNode() {
        return new ObjectNode(this);
    }

    @Override
    public ValueNode pojoNode(Object object) {
        return new POJONode(object);
    }

    @Override
    public TextNode textNode(String string2) {
        return TextNode.valueOf(string2);
    }
}

