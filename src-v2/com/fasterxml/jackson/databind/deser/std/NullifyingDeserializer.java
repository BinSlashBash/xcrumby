/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;

public class NullifyingDeserializer
extends StdDeserializer<Object> {
    public static final NullifyingDeserializer instance = new NullifyingDeserializer();
    private static final long serialVersionUID = 1;

    public NullifyingDeserializer() {
        super(Object.class);
    }

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        jsonParser.skipChildren();
        return null;
    }

    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        switch (.$SwitchMap$com$fasterxml$jackson$core$JsonToken[jsonToken.ordinal()]) {
            default: {
                return null;
            }
            case 1: 
            case 2: 
            case 3: 
        }
        return typeDeserializer.deserializeTypedFromAny(jsonParser, deserializationContext);
    }

}

