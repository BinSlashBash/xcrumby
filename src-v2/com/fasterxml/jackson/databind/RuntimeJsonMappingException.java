/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.databind.JsonMappingException;

public class RuntimeJsonMappingException
extends RuntimeException {
    public RuntimeJsonMappingException(JsonMappingException jsonMappingException) {
        super(jsonMappingException);
    }

    public RuntimeJsonMappingException(String string2) {
        super(string2);
    }

    public RuntimeJsonMappingException(String string2, JsonMappingException jsonMappingException) {
        super(string2, jsonMappingException);
    }
}

