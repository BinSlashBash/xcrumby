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

public class BooleanNode
extends ValueNode {
    public static final BooleanNode FALSE;
    public static final BooleanNode TRUE;
    private final boolean _value;

    static {
        TRUE = new BooleanNode(true);
        FALSE = new BooleanNode(false);
    }

    private BooleanNode(boolean bl2) {
        this._value = bl2;
    }

    public static BooleanNode getFalse() {
        return FALSE;
    }

    public static BooleanNode getTrue() {
        return TRUE;
    }

    public static BooleanNode valueOf(boolean bl2) {
        if (bl2) {
            return TRUE;
        }
        return FALSE;
    }

    @Override
    public boolean asBoolean() {
        return this._value;
    }

    @Override
    public boolean asBoolean(boolean bl2) {
        return this._value;
    }

    @Override
    public double asDouble(double d2) {
        if (this._value) {
            return 1.0;
        }
        return 0.0;
    }

    @Override
    public int asInt(int n2) {
        if (this._value) {
            return 1;
        }
        return 0;
    }

    @Override
    public long asLong(long l2) {
        if (this._value) {
            return 1;
        }
        return 0;
    }

    @Override
    public String asText() {
        if (this._value) {
            return "true";
        }
        return "false";
    }

    @Override
    public JsonToken asToken() {
        if (this._value) {
            return JsonToken.VALUE_TRUE;
        }
        return JsonToken.VALUE_FALSE;
    }

    @Override
    public boolean booleanValue() {
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
        if (!(object instanceof BooleanNode)) {
            return false;
        }
        if (this._value == ((BooleanNode)object)._value) return true;
        return false;
    }

    @Override
    public JsonNodeType getNodeType() {
        return JsonNodeType.BOOLEAN;
    }

    @Override
    public final void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeBoolean(this._value);
    }
}

