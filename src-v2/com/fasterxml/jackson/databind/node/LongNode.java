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

public class LongNode
extends NumericNode {
    protected final long _value;

    public LongNode(long l2) {
        this._value = l2;
    }

    public static LongNode valueOf(long l2) {
        return new LongNode(l2);
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
        if (this._value >= Integer.MIN_VALUE && this._value <= Integer.MAX_VALUE) {
            return true;
        }
        return false;
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
        if (!(object instanceof LongNode)) return false;
        if (((LongNode)object)._value == this._value) return true;
        return false;
    }

    @Override
    public float floatValue() {
        return this._value;
    }

    public int hashCode() {
        return (int)this._value ^ (int)(this._value >> 32);
    }

    @Override
    public int intValue() {
        return (int)this._value;
    }

    @Override
    public boolean isIntegralNumber() {
        return true;
    }

    @Override
    public boolean isLong() {
        return true;
    }

    @Override
    public long longValue() {
        return this._value;
    }

    @Override
    public JsonParser.NumberType numberType() {
        return JsonParser.NumberType.LONG;
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

