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
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class InetSocketAddressSerializer
extends StdScalarSerializer<InetSocketAddress> {
    public InetSocketAddressSerializer() {
        super(InetSocketAddress.class);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serialize(InetSocketAddress inetSocketAddress, JsonGenerator jsonGenerator, SerializerProvider object) throws IOException, JsonGenerationException {
        InetAddress inetAddress = inetSocketAddress.getAddress();
        String string2 = inetAddress == null ? inetSocketAddress.getHostName() : inetAddress.toString().trim();
        int n2 = string2.indexOf(47);
        object = string2;
        if (n2 >= 0) {
            object = n2 == 0 ? (inetAddress instanceof Inet6Address ? "[" + string2.substring(1) + "]" : string2.substring(1)) : string2.substring(0, n2);
        }
        jsonGenerator.writeString((String)object + ":" + inetSocketAddress.getPort());
    }

    @Override
    public void serializeWithType(InetSocketAddress inetSocketAddress, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        typeSerializer.writeTypePrefixForScalar(inetSocketAddress, jsonGenerator, InetSocketAddress.class);
        this.serialize(inetSocketAddress, jsonGenerator, serializerProvider);
        typeSerializer.writeTypeSuffixForScalar(inetSocketAddress, jsonGenerator);
    }
}

