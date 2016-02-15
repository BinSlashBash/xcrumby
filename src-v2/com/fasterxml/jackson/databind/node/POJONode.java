/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ValueNode;
import java.io.IOException;

public class POJONode
extends ValueNode {
    protected final Object _value;

    public POJONode(Object object) {
        this._value = object;
    }

    protected boolean _pojoEquals(POJONode pOJONode) {
        if (this._value == null) {
            if (pOJONode._value == null) {
                return true;
            }
            return false;
        }
        return this._value.equals(pOJONode._value);
    }

    @Override
    public boolean asBoolean(boolean bl2) {
        boolean bl3 = bl2;
        if (this._value != null) {
            bl3 = bl2;
            if (this._value instanceof Boolean) {
                bl3 = (Boolean)this._value;
            }
        }
        return bl3;
    }

    @Override
    public double asDouble(double d2) {
        if (this._value instanceof Number) {
            d2 = ((Number)this._value).doubleValue();
        }
        return d2;
    }

    @Override
    public int asInt(int n2) {
        if (this._value instanceof Number) {
            n2 = ((Number)this._value).intValue();
        }
        return n2;
    }

    @Override
    public long asLong(long l2) {
        if (this._value instanceof Number) {
            l2 = ((Number)this._value).longValue();
        }
        return l2;
    }

    @Override
    public String asText() {
        if (this._value == null) {
            return "null";
        }
        return this._value.toString();
    }

    @Override
    public String asText(String string2) {
        if (this._value == null) {
            return string2;
        }
        return this._value.toString();
    }

    @Override
    public JsonToken asToken() {
        return JsonToken.VALUE_EMBEDDED_OBJECT;
    }

    @Override
    public byte[] binaryValue() throws IOException {
        if (this._value instanceof byte[]) {
            return (byte[])this._value;
        }
        return super.binaryValue();
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
        if (!(object instanceof POJONode)) return bl3;
        return this._pojoEquals((POJONode)object);
    }

    @Override
    public JsonNodeType getNodeType() {
        return JsonNodeType.POJO;
    }

    public Object getPojo() {
        return this._value;
    }

    public int hashCode() {
        return this._value.hashCode();
    }

    @Override
    public final void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if (this._value == null) {
            serializerProvider.defaultSerializeNull(jsonGenerator);
            return;
        }
        jsonGenerator.writeObject(this._value);
    }

    @Override
    public String toString() {
        return String.valueOf(this._value);
    }
}

