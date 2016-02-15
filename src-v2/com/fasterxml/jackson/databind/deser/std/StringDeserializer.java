/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;

@JacksonStdImpl
public final class StringDeserializer
extends StdScalarDeserializer<String> {
    public static final StringDeserializer instance = new StringDeserializer();
    private static final long serialVersionUID = 1;

    public StringDeserializer() {
        super(String.class);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public String deserialize(JsonParser object, DeserializationContext deserializationContext) throws IOException {
        Object object2 = object.getCurrentToken();
        if (object2 == JsonToken.VALUE_STRING) {
            return object.getText();
        }
        if (object2 == JsonToken.START_ARRAY && deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
            object.nextToken();
            object2 = this._parseString((JsonParser)object, deserializationContext);
            if (object.nextToken() == JsonToken.END_ARRAY) return object2;
            throw deserializationContext.wrongTokenException((JsonParser)object, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'String' value but there was more than a single value in the array");
        }
        if (object2 == JsonToken.VALUE_EMBEDDED_OBJECT) {
            if ((object = object.getEmbeddedObject()) == null) {
                return null;
            }
            if (!(object instanceof byte[])) return object.toString();
            return Base64Variants.getDefaultVariant().encode((byte[])object, false);
        }
        if ((object = object.getValueAsString()) == null) throw deserializationContext.mappingException(this._valueClass, (JsonToken)((Object)object2));
        return object;
    }

    @Override
    public String deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
        return this.deserialize(jsonParser, deserializationContext);
    }
}

