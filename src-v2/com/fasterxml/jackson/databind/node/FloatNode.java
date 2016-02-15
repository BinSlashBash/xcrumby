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

public class FloatNode
extends NumericNode {
    protected final float _value;

    public FloatNode(float f2) {
        this._value = f2;
    }

    public static FloatNode valueOf(float f2) {
        return new FloatNode(f2);
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
        if (this._value >= -2.14748365E9f && this._value <= 2.14748365E9f) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canConvertToLong() {
        if (this._value >= -9.223372E18f && this._value <= 9.223372E18f) {
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
        if (!(object instanceof FloatNode)) return false;
        float f2 = ((FloatNode)object)._value;
        if (Float.compare(this._value, f2) == 0) return true;
        return false;
    }

    @Override
    public float floatValue() {
        return this._value;
    }

    public int hashCode() {
        return Float.floatToIntBits(this._value);
    }

    @Override
    public int intValue() {
        return (int)this._value;
    }

    @Override
    public boolean isFloat() {
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
        return JsonParser.NumberType.FLOAT;
    }

    @Override
    public Number numberValue() {
        return Float.valueOf(this._value);
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

