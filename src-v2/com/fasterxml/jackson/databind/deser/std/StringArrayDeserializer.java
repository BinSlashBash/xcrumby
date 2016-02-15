/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
import java.io.IOException;

@JacksonStdImpl
public final class StringArrayDeserializer
extends StdDeserializer<String[]>
implements ContextualDeserializer {
    public static final StringArrayDeserializer instance = new StringArrayDeserializer();
    private static final long serialVersionUID = -7589512013334920693L;
    protected JsonDeserializer<String> _elementDeserializer;

    public StringArrayDeserializer() {
        super(String[].class);
        this._elementDeserializer = null;
    }

    protected StringArrayDeserializer(JsonDeserializer<?> jsonDeserializer) {
        super(String[].class);
        this._elementDeserializer = jsonDeserializer;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final String[] handleNonArray(JsonParser object, DeserializationContext deserializationContext) throws IOException {
        Object var3_3 = null;
        if (!deserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
            if (object.getCurrentToken() != JsonToken.VALUE_STRING || !deserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) || object.getText().length() != 0) throw deserializationContext.mappingException(this._valueClass);
            return null;
        }
        if (object.getCurrentToken() == JsonToken.VALUE_NULL) {
            object = var3_3;
            do {
                return new String[]{object};
                break;
            } while (true);
        }
        object = this._parseString((JsonParser)object, deserializationContext);
        return new String[]{object};
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final String[] _deserializeCustom(JsonParser arrstring, DeserializationContext deserializationContext) throws IOException {
        ObjectBuffer objectBuffer = deserializationContext.leaseObjectBuffer();
        Object[] arrobject = objectBuffer.resetAndStart();
        JsonDeserializer<String> jsonDeserializer = this._elementDeserializer;
        int n2 = 0;
        do {
            Object object;
            if ((object = arrstring.nextToken()) == JsonToken.END_ARRAY) {
                arrstring = (String[])objectBuffer.completeAndClearBuffer(arrobject, n2, String.class);
                deserializationContext.returnObjectBuffer(objectBuffer);
                return arrstring;
            }
            object = object == JsonToken.VALUE_NULL ? jsonDeserializer.getNullValue() : jsonDeserializer.deserialize((JsonParser)arrstring, deserializationContext);
            Object[] arrobject2 = arrobject;
            int n3 = n2;
            if (n2 >= arrobject.length) {
                arrobject2 = objectBuffer.appendCompletedChunk(arrobject);
                n3 = 0;
            }
            arrobject2[n3] = object;
            n2 = n3 + 1;
            arrobject = arrobject2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext jsonDeserializer, BeanProperty stringArrayDeserializer) throws JsonMappingException {
        JsonDeserializer<String> jsonDeserializer2 = this.findConvertingContentDeserializer((DeserializationContext)((Object)jsonDeserializer), (BeanProperty)((Object)stringArrayDeserializer), this._elementDeserializer);
        jsonDeserializer = jsonDeserializer2 == null ? jsonDeserializer.findContextualValueDeserializer(jsonDeserializer.constructType(String.class), (BeanProperty)((Object)stringArrayDeserializer)) : jsonDeserializer.handleSecondaryContextualization(jsonDeserializer2, (BeanProperty)((Object)stringArrayDeserializer));
        stringArrayDeserializer = jsonDeserializer;
        if (jsonDeserializer != null) {
            stringArrayDeserializer = jsonDeserializer;
            if (this.isDefaultDeserializer(jsonDeserializer)) {
                stringArrayDeserializer = null;
            }
        }
        jsonDeserializer = this;
        if (this._elementDeserializer == stringArrayDeserializer) return jsonDeserializer;
        return new StringArrayDeserializer(stringArrayDeserializer);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public String[] deserialize(JsonParser arrstring, DeserializationContext deserializationContext) throws IOException {
        if (!arrstring.isExpectedStartArrayToken()) {
            return this.handleNonArray((JsonParser)arrstring, deserializationContext);
        }
        if (this._elementDeserializer != null) {
            return this._deserializeCustom((JsonParser)arrstring, deserializationContext);
        }
        ObjectBuffer objectBuffer = deserializationContext.leaseObjectBuffer();
        Object[] arrobject = objectBuffer.resetAndStart();
        int n2 = 0;
        do {
            Object object;
            if ((object = arrstring.nextToken()) == JsonToken.END_ARRAY) {
                arrstring = (String[])objectBuffer.completeAndClearBuffer(arrobject, n2, String.class);
                deserializationContext.returnObjectBuffer(objectBuffer);
                return arrstring;
            }
            object = object == JsonToken.VALUE_STRING ? arrstring.getText() : (object == JsonToken.VALUE_NULL ? null : this._parseString((JsonParser)arrstring, deserializationContext));
            Object[] arrobject2 = arrobject;
            int n3 = n2;
            if (n2 >= arrobject.length) {
                arrobject2 = objectBuffer.appendCompletedChunk(arrobject);
                n3 = 0;
            }
            arrobject2[n3] = object;
            n2 = n3 + 1;
            arrobject = arrobject2;
        } while (true);
    }

    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromArray(jsonParser, deserializationContext);
    }
}

