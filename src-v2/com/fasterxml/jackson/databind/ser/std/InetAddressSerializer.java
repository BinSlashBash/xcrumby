/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import java.io.IOException;
import java.net.InetAddress;

public class InetAddressSerializer
extends StdScalarSerializer<InetAddress> {
    public InetAddressSerializer() {
        super(InetAddress.class);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serialize(InetAddress object, JsonGenerator jsonGenerator, SerializerProvider object2) throws IOException, JsonGenerationException {
        object2 = object.toString().trim();
        int n2 = object2.indexOf(47);
        object = object2;
        if (n2 >= 0) {
            object = n2 == 0 ? object2.substring(1) : object2.substring(0, n2);
        }
        jsonGenerator.writeString((String)object);
    }

    @Override
    public void serializeWithType(InetAddress inetAddress, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        typeSerializer.writeTypePrefixForScalar(inetAddress, jsonGenerator, InetAddress.class);
        this.serialize(inetAddress, jsonGenerator, serializerProvider);
        typeSerializer.writeTypeSuffixForScalar(inetAddress, jsonGenerator);
    }
}

