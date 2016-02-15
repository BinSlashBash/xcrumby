/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ValueNode;
import java.io.IOException;

public class TextNode
extends ValueNode {
    static final TextNode EMPTY_STRING_NODE = new TextNode("");
    protected final String _value;

    public TextNode(String string2) {
        this._value = string2;
    }

    protected static void appendQuoted(StringBuilder stringBuilder, String string2) {
        stringBuilder.append('\"');
        CharTypes.appendQuoted(stringBuilder, string2);
        stringBuilder.append('\"');
    }

    public static TextNode valueOf(String string2) {
        if (string2 == null) {
            return null;
        }
        if (string2.length() == 0) {
            return EMPTY_STRING_NODE;
        }
        return new TextNode(string2);
    }

    protected void _reportBase64EOF() throws JsonParseException {
        throw new JsonParseException("Unexpected end-of-String when base64 content", JsonLocation.NA);
    }

    protected void _reportInvalidBase64(Base64Variant base64Variant, char c2, int n2) throws JsonParseException {
        this._reportInvalidBase64(base64Variant, c2, n2, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _reportInvalidBase64(Base64Variant object, char c2, int n2, String string2) throws JsonParseException {
        object = c2 <= ' ' ? "Illegal white space character (code 0x" + Integer.toHexString(c2) + ") as character #" + (n2 + 1) + " of 4-char base64 unit: can only used between units" : (object.usesPaddingChar(c2) ? "Unexpected padding character ('" + object.getPaddingChar() + "') as character #" + (n2 + 1) + " of 4-char base64 unit: padding only legal as 3rd or 4th character" : (!Character.isDefined(c2) || Character.isISOControl(c2) ? "Illegal character (code 0x" + Integer.toHexString(c2) + ") in base64 content" : "Illegal character '" + c2 + "' (code 0x" + Integer.toHexString(c2) + ") in base64 content"));
        Object object2 = object;
        if (string2 != null) {
            object2 = (String)object + ": " + string2;
        }
        throw new JsonParseException((String)object2, JsonLocation.NA);
    }

    @Override
    public boolean asBoolean(boolean bl2) {
        boolean bl3 = bl2;
        if (this._value != null) {
            bl3 = bl2;
            if ("true".equals(this._value.trim())) {
                bl3 = true;
            }
        }
        return bl3;
    }

    @Override
    public double asDouble(double d2) {
        return NumberInput.parseAsDouble(this._value, d2);
    }

    @Override
    public int asInt(int n2) {
        return NumberInput.parseAsInt(this._value, n2);
    }

    @Override
    public long asLong(long l2) {
        return NumberInput.parseAsLong(this._value, l2);
    }

    @Override
    public String asText() {
        return this._value;
    }

    @Override
    public String asText(String string2) {
        if (this._value == null) {
            return string2;
        }
        return this._value;
    }

    @Override
    public JsonToken asToken() {
        return JsonToken.VALUE_STRING;
    }

    @Override
    public byte[] binaryValue() throws IOException {
        return this.getBinaryValue(Base64Variants.getDefaultVariant());
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
        if (!(object instanceof TextNode)) return bl3;
        return ((TextNode)object)._value.equals(this._value);
    }

    /*
     * Enabled aggressive block sorting
     */
    public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder(100);
        String string2 = this._value;
        int n2 = 0;
        int n3 = string2.length();
        block0 : while (n2 < n3) {
            do {
                int n4 = n2 + 1;
                char c2 = string2.charAt(n2);
                if (n4 >= n3) {
                    return byteArrayBuilder.toByteArray();
                }
                if (c2 > ' ') {
                    int n5 = base64Variant.decodeBase64Char(c2);
                    if (n5 < 0) {
                        this._reportInvalidBase64(base64Variant, c2, 0);
                    }
                    if (n4 >= n3) {
                        this._reportBase64EOF();
                    }
                    n2 = n4 + 1;
                    c2 = string2.charAt(n4);
                    n4 = base64Variant.decodeBase64Char(c2);
                    if (n4 < 0) {
                        this._reportInvalidBase64(base64Variant, c2, 1);
                    }
                    n5 = n5 << 6 | n4;
                    if (n2 >= n3) {
                        if (!base64Variant.usesPadding()) {
                            byteArrayBuilder.append(n5 >> 4);
                            return byteArrayBuilder.toByteArray();
                        }
                        this._reportBase64EOF();
                    }
                    n4 = n2 + 1;
                    c2 = string2.charAt(n2);
                    n2 = base64Variant.decodeBase64Char(c2);
                    if (n2 < 0) {
                        if (n2 != -2) {
                            this._reportInvalidBase64(base64Variant, c2, 2);
                        }
                        if (n4 >= n3) {
                            this._reportBase64EOF();
                        }
                        n2 = n4 + 1;
                        c2 = string2.charAt(n4);
                        if (!base64Variant.usesPaddingChar(c2)) {
                            this._reportInvalidBase64(base64Variant, c2, 3, "expected padding character '" + base64Variant.getPaddingChar() + "'");
                        }
                        byteArrayBuilder.append(n5 >> 4);
                        continue block0;
                    }
                    n5 = n5 << 6 | n2;
                    if (n4 >= n3) {
                        if (!base64Variant.usesPadding()) {
                            byteArrayBuilder.appendTwoBytes(n5 >> 2);
                            return byteArrayBuilder.toByteArray();
                        }
                        this._reportBase64EOF();
                    }
                    n2 = n4 + 1;
                    c2 = string2.charAt(n4);
                    n4 = base64Variant.decodeBase64Char(c2);
                    if (n4 < 0) {
                        if (n4 != -2) {
                            this._reportInvalidBase64(base64Variant, c2, 3);
                        }
                        byteArrayBuilder.appendTwoBytes(n5 >> 2);
                        continue block0;
                    }
                    byteArrayBuilder.appendThreeBytes(n5 << 6 | n4);
                    continue block0;
                }
                n2 = n4;
            } while (true);
            break;
        }
        return byteArrayBuilder.toByteArray();
    }

    @Override
    public JsonNodeType getNodeType() {
        return JsonNodeType.STRING;
    }

    public int hashCode() {
        return this._value.hashCode();
    }

    @Override
    public final void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (this._value == null) {
            jsonGenerator.writeNull();
            return;
        }
        jsonGenerator.writeString(this._value);
    }

    @Override
    public String textValue() {
        return this._value;
    }

    @Override
    public String toString() {
        int n2 = this._value.length();
        StringBuilder stringBuilder = new StringBuilder(n2 + 2 + (n2 >> 4));
        TextNode.appendQuoted(stringBuilder, this._value);
        return stringBuilder.toString();
    }
}

