/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ValueNode;
import java.io.IOException;
import java.util.Arrays;

public class BinaryNode
extends ValueNode {
    static final BinaryNode EMPTY_BINARY_NODE = new BinaryNode(new byte[0]);
    protected final byte[] _data;

    public BinaryNode(byte[] arrby) {
        this._data = arrby;
    }

    public BinaryNode(byte[] arrby, int n2, int n3) {
        if (n2 == 0 && n3 == arrby.length) {
            this._data = arrby;
            return;
        }
        this._data = new byte[n3];
        System.arraycopy(arrby, n2, this._data, 0, n3);
    }

    public static BinaryNode valueOf(byte[] arrby) {
        if (arrby == null) {
            return null;
        }
        if (arrby.length == 0) {
            return EMPTY_BINARY_NODE;
        }
        return new BinaryNode(arrby);
    }

    public static BinaryNode valueOf(byte[] arrby, int n2, int n3) {
        if (arrby == null) {
            return null;
        }
        if (n3 == 0) {
            return EMPTY_BINARY_NODE;
        }
        return new BinaryNode(arrby, n2, n3);
    }

    @Override
    public String asText() {
        return Base64Variants.getDefaultVariant().encode(this._data, false);
    }

    @Override
    public JsonToken asToken() {
        return JsonToken.VALUE_EMBEDDED_OBJECT;
    }

    @Override
    public byte[] binaryValue() {
        return this._data;
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
        if (!(object instanceof BinaryNode)) return bl3;
        return Arrays.equals(((BinaryNode)object)._data, this._data);
    }

    @Override
    public JsonNodeType getNodeType() {
        return JsonNodeType.BINARY;
    }

    public int hashCode() {
        if (this._data == null) {
            return -1;
        }
        return this._data.length;
    }

    @Override
    public final void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeBinary(serializerProvider.getConfig().getBase64Variant(), this._data, 0, this._data.length);
    }

    @Override
    public String toString() {
        return Base64Variants.getDefaultVariant().encode(this._data, true);
    }
}

