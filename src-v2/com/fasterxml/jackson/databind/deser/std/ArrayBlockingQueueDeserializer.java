/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.std.CollectionDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueDeserializer
extends CollectionDeserializer {
    private static final long serialVersionUID = 1;

    public ArrayBlockingQueueDeserializer(JavaType javaType, JsonDeserializer<Object> jsonDeserializer, TypeDeserializer typeDeserializer, ValueInstantiator valueInstantiator, JsonDeserializer<Object> jsonDeserializer2) {
        super(javaType, jsonDeserializer, typeDeserializer, valueInstantiator, jsonDeserializer2);
    }

    protected ArrayBlockingQueueDeserializer(ArrayBlockingQueueDeserializer arrayBlockingQueueDeserializer) {
        super(arrayBlockingQueueDeserializer);
    }

    @Override
    public Collection<Object> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String string2;
        if (this._delegateDeserializer != null) {
            return (Collection)this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
        }
        if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING && (string2 = jsonParser.getText()).length() == 0) {
            return (Collection)this._valueInstantiator.createFromString(deserializationContext, string2);
        }
        return this.deserialize(jsonParser, deserializationContext, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Collection<Object> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Collection<Object> collection) throws IOException {
        Object object;
        if (!jsonParser.isExpectedStartArrayToken()) {
            return this.handleNonArray(jsonParser, deserializationContext, new ArrayBlockingQueue<Object>(1));
        }
        ArrayList<JsonToken> arrayList = new ArrayList<JsonToken>();
        JsonDeserializer jsonDeserializer = this._valueDeserializer;
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        while ((object = jsonParser.nextToken()) != JsonToken.END_ARRAY) {
            object = object == JsonToken.VALUE_NULL ? jsonDeserializer.getNullValue() : (typeDeserializer == null ? jsonDeserializer.deserialize(jsonParser, deserializationContext) : jsonDeserializer.deserializeWithType(jsonParser, deserializationContext, typeDeserializer));
            arrayList.add((JsonToken)((Object)object));
        }
        if (collection != null) {
            collection.addAll(arrayList);
            return collection;
        }
        return new ArrayBlockingQueue<Object>(arrayList.size(), false, arrayList);
    }

    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromArray(jsonParser, deserializationContext);
    }

    @Override
    protected ArrayBlockingQueueDeserializer withResolved(JsonDeserializer<?> jsonDeserializer, JsonDeserializer<?> jsonDeserializer2, TypeDeserializer typeDeserializer) {
        if (jsonDeserializer == this._delegateDeserializer && jsonDeserializer2 == this._valueDeserializer && typeDeserializer == this._valueTypeDeserializer) {
            return this;
        }
        return new ArrayBlockingQueueDeserializer(this._collectionType, jsonDeserializer2, typeDeserializer, this._valueInstantiator, jsonDeserializer);
    }
}

