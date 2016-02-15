/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import java.io.IOException;

public class StackTraceElementDeserializer
extends StdScalarDeserializer<StackTraceElement> {
    private static final long serialVersionUID = 1;

    public StackTraceElementDeserializer() {
        super(StackTraceElement.class);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public StackTraceElement deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Object object = jsonParser.getCurrentToken();
        if (object == JsonToken.START_OBJECT) {
            JsonToken jsonToken;
            String string2 = "";
            object = "";
            String string3 = "";
            int n2 = -1;
            while ((jsonToken = jsonParser.nextValue()) != JsonToken.END_OBJECT) {
                String string4 = jsonParser.getCurrentName();
                if ("className".equals(string4)) {
                    string2 = jsonParser.getText();
                    continue;
                }
                if ("fileName".equals(string4)) {
                    string3 = jsonParser.getText();
                    continue;
                }
                if ("lineNumber".equals(string4)) {
                    if (!jsonToken.isNumeric()) throw JsonMappingException.from(jsonParser, "Non-numeric token (" + (Object)((Object)jsonToken) + ") for property 'lineNumber'");
                    n2 = jsonParser.getIntValue();
                    continue;
                }
                if ("methodName".equals(string4)) {
                    object = jsonParser.getText();
                    continue;
                }
                if ("nativeMethod".equals(string4)) continue;
                this.handleUnknownProperty(jsonParser, deserializationContext, this._valueClass, string4);
            }
            return new StackTraceElement(string2, (String)object, string3, n2);
        }
        if (object != JsonToken.START_ARRAY) throw deserializationContext.mappingException(this._valueClass, (JsonToken)((Object)((Object)object)));
        if (!deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) throw deserializationContext.mappingException(this._valueClass, (JsonToken)((Object)((Object)object)));
        jsonParser.nextToken();
        object = this.deserialize(jsonParser, deserializationContext);
        if (jsonParser.nextToken() == JsonToken.END_ARRAY) return object;
        throw deserializationContext.wrongTokenException(jsonParser, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'java.lang.StackTraceElement' value but there was more than a single value in the array");
    }
}

