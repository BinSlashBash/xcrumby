/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public enum LongSerializationPolicy {
    DEFAULT{

        @Override
        public JsonElement serialize(Long l2) {
            return new JsonPrimitive(l2);
        }
    }
    ,
    STRING{

        @Override
        public JsonElement serialize(Long l2) {
            return new JsonPrimitive(String.valueOf(l2));
        }
    };
    

    private LongSerializationPolicy() {
    }

    public abstract JsonElement serialize(Long var1);

}

