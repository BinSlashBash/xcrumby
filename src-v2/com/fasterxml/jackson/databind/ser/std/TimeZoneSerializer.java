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
import java.util.TimeZone;

public class TimeZoneSerializer
extends StdScalarSerializer<TimeZone> {
    public TimeZoneSerializer() {
        super(TimeZone.class);
    }

    @Override
    public void serialize(TimeZone timeZone, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        jsonGenerator.writeString(timeZone.getID());
    }

    @Override
    public void serializeWithType(TimeZone timeZone, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        typeSerializer.writeTypePrefixForScalar(timeZone, jsonGenerator, TimeZone.class);
        this.serialize(timeZone, jsonGenerator, serializerProvider);
        typeSerializer.writeTypeSuffixForScalar(timeZone, jsonGenerator);
    }
}

