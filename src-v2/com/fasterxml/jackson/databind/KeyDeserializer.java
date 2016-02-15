/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.IOException;

public abstract class KeyDeserializer {
    public abstract Object deserializeKey(String var1, DeserializationContext var2) throws IOException, JsonProcessingException;

    public static abstract class None
    extends KeyDeserializer {
    }

}

