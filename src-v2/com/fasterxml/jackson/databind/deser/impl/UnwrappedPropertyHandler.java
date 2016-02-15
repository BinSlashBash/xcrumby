/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UnwrappedPropertyHandler {
    protected final List<SettableBeanProperty> _properties;

    public UnwrappedPropertyHandler() {
        this._properties = new ArrayList<SettableBeanProperty>();
    }

    protected UnwrappedPropertyHandler(List<SettableBeanProperty> list) {
        this._properties = list;
    }

    public void addProperty(SettableBeanProperty settableBeanProperty) {
        this._properties.add(settableBeanProperty);
    }

    public Object processUnwrapped(JsonParser object, DeserializationContext deserializationContext, Object object2, TokenBuffer tokenBuffer) throws IOException, JsonProcessingException {
        int n2 = this._properties.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            object = this._properties.get(i2);
            JsonParser jsonParser = tokenBuffer.asParser();
            jsonParser.nextToken();
            object.deserializeAndSet(jsonParser, deserializationContext, object2);
        }
        return object2;
    }

    public UnwrappedPropertyHandler renameAll(NameTransformer nameTransformer) {
        ArrayList<SettableBeanProperty> arrayList = new ArrayList<SettableBeanProperty>(this._properties.size());
        Iterator<SettableBeanProperty> iterator = this._properties.iterator();
        while (iterator.hasNext()) {
            SettableBeanProperty settableBeanProperty = iterator.next();
            SettableBeanProperty settableBeanProperty2 = settableBeanProperty.withSimpleName(nameTransformer.transform(settableBeanProperty.getName()));
            JsonDeserializer<Object> jsonDeserializer = settableBeanProperty2.getValueDeserializer();
            settableBeanProperty = settableBeanProperty2;
            if (jsonDeserializer != null) {
                JsonDeserializer<Object> jsonDeserializer2 = jsonDeserializer.unwrappingDeserializer(nameTransformer);
                settableBeanProperty = settableBeanProperty2;
                if (jsonDeserializer2 != jsonDeserializer) {
                    settableBeanProperty = settableBeanProperty2.withValueDeserializer(jsonDeserializer2);
                }
            }
            arrayList.add(settableBeanProperty);
        }
        return new UnwrappedPropertyHandler(arrayList);
    }
}

