/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.util.UUID;

public class UUIDSerializer
extends StdScalarSerializer<UUID> {
    static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

    public UUIDSerializer() {
        super(UUID.class);
    }

    private static final void _appendInt(int n2, byte[] arrby, int n3) {
        arrby[n3] = (byte)(n2 >> 24);
        arrby[++n3] = (byte)(n2 >> 16);
        arrby[++n3] = (byte)(n2 >> 8);
        arrby[n3 + 1] = (byte)n2;
    }

    private static void _appendInt(int n2, char[] arrc, int n3) {
        UUIDSerializer._appendShort(n2 >> 16, arrc, n3);
        UUIDSerializer._appendShort(n2, arrc, n3 + 4);
    }

    private static void _appendShort(int n2, char[] arrc, int n3) {
        arrc[n3] = HEX_CHARS[n2 >> 12 & 15];
        arrc[++n3] = HEX_CHARS[n2 >> 8 & 15];
        arrc[++n3] = HEX_CHARS[n2 >> 4 & 15];
        arrc[n3 + 1] = HEX_CHARS[n2 & 15];
    }

    private static final byte[] _asBytes(UUID uUID) {
        byte[] arrby = new byte[16];
        long l2 = uUID.getMostSignificantBits();
        long l3 = uUID.getLeastSignificantBits();
        UUIDSerializer._appendInt((int)(l2 >> 32), arrby, 0);
        UUIDSerializer._appendInt((int)l2, arrby, 4);
        UUIDSerializer._appendInt((int)(l3 >> 32), arrby, 8);
        UUIDSerializer._appendInt((int)l3, arrby, 12);
        return arrby;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean isEmpty(UUID uUID) {
        if (uUID == null || uUID.getLeastSignificantBits() == 0 && uUID.getMostSignificantBits() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void serialize(UUID uUID, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        if (jsonGenerator.canWriteBinaryNatively() && !(jsonGenerator instanceof TokenBuffer)) {
            jsonGenerator.writeBinary(UUIDSerializer._asBytes(uUID));
            return;
        }
        serializerProvider = (SerializerProvider)new char[36];
        long l2 = uUID.getMostSignificantBits();
        UUIDSerializer._appendInt((int)(l2 >> 32), (char[])serializerProvider, 0);
        serializerProvider[8] = (SerializerProvider)45;
        int n2 = (int)l2;
        UUIDSerializer._appendShort(n2 >>> 16, (char[])serializerProvider, 9);
        serializerProvider[13] = (SerializerProvider)45;
        UUIDSerializer._appendShort(n2, (char[])serializerProvider, 14);
        serializerProvider[18] = (SerializerProvider)45;
        l2 = uUID.getLeastSignificantBits();
        UUIDSerializer._appendShort((int)(l2 >>> 48), (char[])serializerProvider, 19);
        serializerProvider[23] = (SerializerProvider)45;
        UUIDSerializer._appendShort((int)(l2 >>> 32), (char[])serializerProvider, 24);
        UUIDSerializer._appendInt((int)l2, (char[])serializerProvider, 28);
        jsonGenerator.writeString((char[])serializerProvider, 0, 36);
    }
}

