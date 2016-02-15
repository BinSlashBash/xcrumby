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

public class ShortNode
extends NumericNode {
    protected final short _value;

    public ShortNode(short s2) {
        this._value = s2;
    }

    public static ShortNode valueOf(short s2) {
        return new ShortNode(s2);
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
        if (!(object instanceof ShortNode)) return false;
        if (((ShortNode)object)._value == this._value) return true;
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
    public boolean isIntegralNumber() {
        return true;
    }

    @Override
    public boolean isShort() {
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
        return this._value;
    }
}

