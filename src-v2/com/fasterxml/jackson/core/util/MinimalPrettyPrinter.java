/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.PrettyPrinter;
import java.io.IOException;
import java.io.Serializable;

public class MinimalPrettyPrinter
implements PrettyPrinter,
Serializable {
    public static final String DEFAULT_ROOT_VALUE_SEPARATOR = " ";
    private static final long serialVersionUID = -562765100295218442L;
    protected String _rootValueSeparator = " ";

    public MinimalPrettyPrinter() {
        this(" ");
    }

    public MinimalPrettyPrinter(String string2) {
        this._rootValueSeparator = string2;
    }

    @Override
    public void beforeArrayValues(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
    }

    @Override
    public void beforeObjectEntries(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
    }

    public void setRootValueSeparator(String string2) {
        this._rootValueSeparator = string2;
    }

    @Override
    public void writeArrayValueSeparator(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        jsonGenerator.writeRaw(',');
    }

    @Override
    public void writeEndArray(JsonGenerator jsonGenerator, int n2) throws IOException, JsonGenerationException {
        jsonGenerator.writeRaw(']');
    }

    @Override
    public void writeEndObject(JsonGenerator jsonGenerator, int n2) throws IOException, JsonGenerationException {
        jsonGenerator.writeRaw('}');
    }

    @Override
    public void writeObjectEntrySeparator(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        jsonGenerator.writeRaw(',');
    }

    @Override
    public void writeObjectFieldValueSeparator(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        jsonGenerator.writeRaw(':');
    }

    @Override
    public void writeRootValueSeparator(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        if (this._rootValueSeparator != null) {
            jsonGenerator.writeRaw(this._rootValueSeparator);
        }
    }

    @Override
    public void writeStartArray(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        jsonGenerator.writeRaw('[');
    }

    @Override
    public void writeStartObject(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        jsonGenerator.writeRaw('{');
    }
}

