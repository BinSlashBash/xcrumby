/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class UUIDDeserializer
extends FromStringDeserializer<UUID> {
    static final int[] HEX_DIGITS = new int[127];
    private static final long serialVersionUID = 1;

    static {
        Arrays.fill(HEX_DIGITS, -1);
        int n2 = 0;
        while (n2 < 10) {
            UUIDDeserializer.HEX_DIGITS[n2 + 48] = n2++;
        }
        for (n2 = 0; n2 < 6; ++n2) {
            UUIDDeserializer.HEX_DIGITS[n2 + 97] = n2 + 10;
            UUIDDeserializer.HEX_DIGITS[n2 + 65] = n2 + 10;
        }
    }

    public UUIDDeserializer() {
        super(UUID.class);
    }

    static int _badChar(String string2, int n2, char c2) {
        throw new NumberFormatException("Non-hex character '" + c2 + "', not valid character for a UUID String" + "' (value 0x" + Integer.toHexString(c2) + ") for UUID String \"" + string2 + "\"");
    }

    private void _badFormat(String string2) {
        throw new NumberFormatException("UUID has to be represented by the standard 36-char representation");
    }

    private UUID _fromBytes(byte[] arrby, DeserializationContext deserializationContext) throws IOException {
        if (arrby.length != 16) {
            deserializationContext.mappingException("Can only construct UUIDs from byte[16]; got " + arrby.length + " bytes");
        }
        return new UUID(UUIDDeserializer._long(arrby, 0), UUIDDeserializer._long(arrby, 8));
    }

    private static int _int(byte[] arrby, int n2) {
        return arrby[n2] << 24 | (arrby[n2 + 1] & 255) << 16 | (arrby[n2 + 2] & 255) << 8 | arrby[n2 + 3] & 255;
    }

    private static long _long(byte[] arrby, int n2) {
        return (long)UUIDDeserializer._int(arrby, n2) << 32 | (long)UUIDDeserializer._int(arrby, n2 + 4) << 32 >>> 32;
    }

    static int byteFromChars(String string2, int n2) {
        int n3;
        char c2 = string2.charAt(n2);
        char c3 = string2.charAt(n2 + 1);
        if (c2 <= '' && c3 <= '' && (n3 = HEX_DIGITS[c2] << 4 | HEX_DIGITS[c3]) >= 0) {
            return n3;
        }
        if (c2 > '' || HEX_DIGITS[c2] < 0) {
            return UUIDDeserializer._badChar(string2, n2, c2);
        }
        return UUIDDeserializer._badChar(string2, n2 + 1, c3);
    }

    static int intFromChars(String string2, int n2) {
        return (UUIDDeserializer.byteFromChars(string2, n2) << 24) + (UUIDDeserializer.byteFromChars(string2, n2 + 2) << 16) + (UUIDDeserializer.byteFromChars(string2, n2 + 4) << 8) + UUIDDeserializer.byteFromChars(string2, n2 + 6);
    }

    static int shortFromChars(String string2, int n2) {
        return (UUIDDeserializer.byteFromChars(string2, n2) << 8) + UUIDDeserializer.byteFromChars(string2, n2 + 2);
    }

    @Override
    protected UUID _deserialize(String string2, DeserializationContext deserializationContext) throws IOException {
        if (string2.length() != 36) {
            if (string2.length() == 24) {
                return this._fromBytes(Base64Variants.getDefaultVariant().decode(string2), deserializationContext);
            }
            this._badFormat(string2);
        }
        if (string2.charAt(8) != '-' || string2.charAt(13) != '-' || string2.charAt(18) != '-' || string2.charAt(23) != '-') {
            this._badFormat(string2);
        }
        return new UUID(((long)UUIDDeserializer.intFromChars(string2, 0) << 32) + ((long)UUIDDeserializer.shortFromChars(string2, 9) << 16 | (long)UUIDDeserializer.shortFromChars(string2, 14)), (long)(UUIDDeserializer.shortFromChars(string2, 19) << 16 | UUIDDeserializer.shortFromChars(string2, 24)) << 32 | (long)UUIDDeserializer.intFromChars(string2, 28) << 32 >>> 32);
    }

    @Override
    protected UUID _deserializeEmbedded(Object object, DeserializationContext deserializationContext) throws IOException {
        if (object instanceof byte[]) {
            return this._fromBytes((byte[])object, deserializationContext);
        }
        super._deserializeEmbedded(object, deserializationContext);
        return null;
    }
}

