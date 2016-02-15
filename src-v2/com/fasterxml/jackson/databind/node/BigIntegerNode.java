/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.NumericNode;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class BigIntegerNode
extends NumericNode {
    private static final BigInteger MAX_INTEGER;
    private static final BigInteger MAX_LONG;
    private static final BigInteger MIN_INTEGER;
    private static final BigInteger MIN_LONG;
    protected final BigInteger _value;

    static {
        MIN_INTEGER = BigInteger.valueOf(Integer.MIN_VALUE);
        MAX_INTEGER = BigInteger.valueOf(Integer.MAX_VALUE);
        MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
        MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
    }

    public BigIntegerNode(BigInteger bigInteger) {
        this._value = bigInteger;
    }

    public static BigIntegerNode valueOf(BigInteger bigInteger) {
        return new BigIntegerNode(bigInteger);
    }

    @Override
    public boolean asBoolean(boolean bl2) {
        if (!BigInteger.ZERO.equals(this._value)) {
            return true;
        }
        return false;
    }

    @Override
    public String asText() {
        return this._value.toString();
    }

    @Override
    public JsonToken asToken() {
        return JsonToken.VALUE_NUMBER_INT;
    }

    @Override
    public BigInteger bigIntegerValue() {
        return this._value;
    }

    @Override
    public boolean canConvertToInt() {
        if (this._value.compareTo(MIN_INTEGER) >= 0 && this._value.compareTo(MAX_INTEGER) <= 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canConvertToLong() {
        if (this._value.compareTo(MIN_LONG) >= 0 && this._value.compareTo(MAX_LONG) <= 0) {
            return true;
        }
        return false;
    }

    @Override
    public BigDecimal decimalValue() {
        return new BigDecimal(this._value);
    }

    @Override
    public double doubleValue() {
        return this._value.doubleValue();
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
        if (!(object instanceof BigIntegerNode)) return bl3;
        return ((BigIntegerNode)object)._value.equals(this._value);
    }

    @Override
    public float floatValue() {
        return this._value.floatValue();
    }

    public int hashCode() {
        return this._value.hashCode();
    }

    @Override
    public int intValue() {
        return this._value.intValue();
    }

    @Override
    public boolean isBigInteger() {
        return true;
    }

    @Override
    public boolean isIntegralNumber() {
        return true;
    }

    @Override
    public long longValue() {
        return this._value.longValue();
    }

    @Override
    public JsonParser.NumberType numberType() {
        return JsonParser.NumberType.BIG_INTEGER;
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
        return this._value.shortValue();
    }
}

