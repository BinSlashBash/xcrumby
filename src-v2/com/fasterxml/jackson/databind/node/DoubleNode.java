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

public class DoubleNode
extends NumericNode {
    protected final double _value;

    public DoubleNode(double d2) {
        this._value = d2;
    }

    public static DoubleNode valueOf(double d2) {
        return new DoubleNode(d2);
    }

    @Override
    public String asText() {
        return NumberOutput.toString(this._value);
    }

    @Override
    public JsonToken asToken() {
        return JsonToken.VALUE_NUMBER_FLOAT;
    }

    @Override
    public BigInteger bigIntegerValue() {
        return this.decimalValue().toBigInteger();
    }

    @Override
    public boolean canConvertToInt() {
        if (this._value >= -2.147483648E9 && this._value <= 2.147483647E9) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canConvertToLong() {
        if (this._value >= -9.223372036854776E18 && this._value <= 9.223372036854776E18) {
            return true;
        }
        return false;
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
        if (!(object instanceof DoubleNode)) return false;
        double d2 = ((DoubleNode)object)._value;
        if (Double.compare(this._value, d2) == 0) return true;
        return false;
    }

    @Override
    public float floatValue() {
        return (float)this._value;
    }

    public int hashCode() {
        long l2 = Double.doubleToLongBits(this._value);
        return (int)l2 ^ (int)(l2 >> 32);
    }

    @Override
    public int intValue() {
        return (int)this._value;
    }

    @Override
    public boolean isDouble() {
        return true;
    }

    @Override
    public boolean isFloatingPointNumber() {
        return true;
    }

    @Override
    public long longValue() {
        return (long)this._value;
    }

    @Override
    public JsonParser.NumberType numberType() {
        return JsonParser.NumberType.DOUBLE;
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

