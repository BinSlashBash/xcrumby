/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.util.EnumSet;

public class EnumSetDeserializer
extends StdDeserializer<EnumSet<?>>
implements ContextualDeserializer {
    private static final long serialVersionUID = 3479455075597887177L;
    protected final Class<Enum> _enumClass;
    protected JsonDeserializer<Enum<?>> _enumDeserializer;
    protected final JavaType _enumType;

    public EnumSetDeserializer(JavaType javaType, JsonDeserializer<?> jsonDeserializer) {
        super(EnumSet.class);
        this._enumType = javaType;
        this._enumClass = javaType.getRawClass();
        this._enumDeserializer = jsonDeserializer;
    }

    private EnumSet constructSet() {
        return EnumSet.noneOf(this._enumClass);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext jsonDeserializer, BeanProperty beanProperty) throws JsonMappingException {
        JsonDeserializer jsonDeserializer2 = this._enumDeserializer;
        if (jsonDeserializer2 == null) {
            jsonDeserializer = jsonDeserializer.findContextualValueDeserializer(this._enumType, beanProperty);
            do {
                return this.withDeserializer(jsonDeserializer);
                break;
            } while (true);
        }
        jsonDeserializer = jsonDeserializer.handleSecondaryContextualization(jsonDeserializer2, beanProperty);
        return this.withDeserializer(jsonDeserializer);
    }

    @Override
    public EnumSet<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Enum enum_;
        if (!jsonParser.isExpectedStartArrayToken()) {
            throw deserializationContext.mappingException(EnumSet.class);
        }
        EnumSet enumSet = this.constructSet();
        while ((enum_ = jsonParser.nextToken()) != JsonToken.END_ARRAY) {
            if (enum_ == JsonToken.VALUE_NULL) {
                throw deserializationContext.mappingException(this._enumClass);
            }
            enum_ = this._enumDeserializer.deserialize(jsonParser, deserializationContext);
            if (enum_ == null) continue;
            enumSet.add(enum_);
        }
        return enumSet;
    }

    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        return typeDeserializer.deserializeTypedFromArray(jsonParser, deserializationContext);
    }

    @Override
    public boolean isCachable() {
        return true;
    }

    public EnumSetDeserializer withDeserializer(JsonDeserializer<?> jsonDeserializer) {
        if (this._enumDeserializer == jsonDeserializer) {
            return this;
        }
        return new EnumSetDeserializer(this._enumType, jsonDeserializer);
    }
}

