/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.io.NumberOutput;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.NumericNode;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class IntNode
extends NumericNode {
    private static final IntNode[] CANONICALS = new IntNode[12];
    static final int MAX_CANONICAL = 10;
    static final int MIN_CANONICAL = -1;
    protected final int _value;

    static {
        for (int i2 = 0; i2 < 12; ++i2) {
            IntNode.CANONICALS[i2] = new IntNode(i2 - 1);
        }
    }

    public IntNode(int n2) {
        this._value = n2;
    }

    public static IntNode valueOf(int n2) {
        if (n2 > 10 || n2 < -1) {
            return new IntNode(n2);
        }
        return CANONICALS[n2 + 1];
    }

    @Override
    public boolean asBoolean(boolean bl2) {
        if (this._value != 0) {
            return true;
        }
        return false;
    }

    @Override
    public String asText() {
        return NumberOutput.toString(this._value);
    }

    @Override
    public JsonToken asToken() {
        return JsonToken.VALUE_NUMBER_INT;
    }

    @Override
    public BigInteger bigIntegerValue() {
        return BigInteger.valueOf(this._value);
    }

    @Override
    public boolean canConvertToInt() {
        return true;
    }

    @Override
    public boolean canConvertToLong() {
        return true;
    }

    @Override
    public BigDecimal decimalValue() {
        return BigDecimal.valueOf(this._value);
    }

    @Override
    public double doubleValue() {
        return this._value;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof IntNode)) return false;
        if (((IntNode)object)._value == this._value) return true;
        return false;
    }

    @Override
    public float floatValue() {
        return this._value;
    }

    public int hashCode() {
        return this._value;
    }

    @Override
    public int intValue() {
        return this._value;
    }

    @Override
    public boolean isInt() {
        return true;
    }

    @Override
    public boolean isIntegralNumber() {
        return true;
    }

    @Override
    public long longValue() {
        return this._value;
    }

    @Override
    public JsonParser.NumberType numberType() {
        return JsonParser.NumberType.INT;
    }

    @Override
    public Number numberValue() {
        return this._value;
    }

    @Override
    public final void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeNumber(this._value);
    }

    @Override
    public short shortValue() {
        return (short)this._value;
    }
}

