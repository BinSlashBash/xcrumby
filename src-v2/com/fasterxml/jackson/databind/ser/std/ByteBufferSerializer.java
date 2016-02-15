/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class ByteBufferSerializer
extends StdScalarSerializer<ByteBuffer> {
    public ByteBufferSerializer() {
        super(ByteBuffer.class);
    }

    @Override
    public void serialize(ByteBuffer byteBuffer, JsonGenerator jsonGenerator, SerializerProvider object) throws IOException, JsonGenerationException {
        if (byteBuffer.hasArray()) {
            jsonGenerator.writeBinary(byteBuffer.array(), 0, byteBuffer.limit());
            return;
        }
        if ((byteBuffer = byteBuffer.asReadOnlyBuffer()).position() > 0) {
            byteBuffer.rewind();
        }
        object = new ByteBufferBackedInputStream(byteBuffer);
        jsonGenerator.writeBinary((InputStream)object, byteBuffer.remaining());
        object.close();
    }
}

