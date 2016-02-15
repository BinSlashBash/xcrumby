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

public class DecimalNode
extends NumericNode {
    private static final BigDecimal MAX_INTEGER;
    private static final BigDecimal MAX_LONG;
    private static final BigDecimal MIN_INTEGER;
    private static final BigDecimal MIN_LONG;
    public static final DecimalNode ZERO;
    protected final BigDecimal _value;

    static {
        ZERO = new DecimalNode(BigDecimal.ZERO);
        MIN_INTEGER = BigDecimal.valueOf(Integer.MIN_VALUE);
        MAX_INTEGER = BigDecimal.valueOf(Integer.MAX_VALUE);
        MIN_LONG = BigDecimal.valueOf(Long.MIN_VALUE);
        MAX_LONG = BigDecimal.valueOf(Long.MAX_VALUE);
    }

    public DecimalNode(BigDecimal bigDecimal) {
        this._value = bigDecimal;
    }

    public static DecimalNode valueOf(BigDecimal bigDecimal) {
        return new DecimalNode(bigDecimal);
    }

    @Override
    public String asText() {
        return this._value.toString();
    }

    @Override
    public JsonToken asToken() {
        return JsonToken.VALUE_NUMBER_FLOAT;
    }

    @Override
    public BigInteger bigIntegerValue() {
        return this._value.toBigInteger();
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
        return this._value;
    }

    @Override
    public double doubleValue() {
        return this._value.doubleValue();
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
        if (!(object instanceof DecimalNode)) return false;
        if (((DecimalNode)object)._value.compareTo(this._value) == 0) return true;
        return false;
    }

    @Override
    public float floatValue() {
        return this._value.floatValue();
    }

    public int hashCode() {
        return Double.valueOf(this.doubleValue()).hashCode();
    }

    @Override
    public int intValue() {
        return this._value.intValue();
    }

    @Override
    public boolean isBigDecimal() {
        return true;
    }

    @Override
    public boolean isFloatingPointNumber() {
        return true;
    }

    @Override
    public long longValue() {
        return this._value.longValue();
    }

    @Override
    public JsonParser.NumberType numberType() {
        return JsonParser.NumberType.BIG_DECIMAL;
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

