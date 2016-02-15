/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public abstract class DeserializationProblemHandler {
    public boolean handleUnknownProperty(DeserializationContext deserializationContext, JsonParser jsonParser, JsonDeserializer<?> jsonDeserializer, Object object, String string2) throws IOException, JsonProcessingException {
        return false;
    }
}

