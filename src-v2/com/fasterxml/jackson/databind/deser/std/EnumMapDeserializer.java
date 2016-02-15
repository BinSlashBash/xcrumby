/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.util.EnumMap;

public class EnumMapDeserializer
extends StdDeserializer<EnumMap<?, ?>>
implements ContextualDeserializer {
    private static final long serialVersionUID = 4564890642370311174L;
    protected final Class<?> _enumClass;
    protected JsonDeserializer<Enum<?>> _keyDeserializer;
    protected final JavaType _mapType;
    protected JsonDeserializer<Object> _valueDeserializer;
    protected final TypeDeserializer _valueTypeDeserializer;

    public EnumMapDeserializer(JavaType javaType, JsonDeserializer<?> jsonDeserializer, JsonDeserializer<?> jsonDeserializer2, TypeDeserializer typeDeserializer) {
        super(EnumMap.class);
        this._mapType = javaType;
        this._enumClass = javaType.getKeyType().getRawClass();
        this._keyDeserializer = jsonDeserializer;
        this._valueDeserializer = jsonDeserializer2;
        this._valueTypeDeserializer = typeDeserializer;
    }

    private EnumMap<?, ?> constructMap() {
        return new EnumMap(this._enumClass);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext jsonDeserializer, BeanProperty beanProperty) throws JsonMappingException {
        Object object;
        TypeDeserializer typeDeserializer;
        Object object2 = object = this._keyDeserializer;
        if (object == null) {
            object2 = jsonDeserializer.findContextualValueDeserializer((JavaType)this._mapType.getKeyType(), beanProperty);
        }
        jsonDeserializer = (object = this._valueDeserializer) == null ? jsonDeserializer.findContextualValueDeserializer((JavaType)this._mapType.getContentType(), beanProperty) : jsonDeserializer.handleSecondaryContextualization(object, beanProperty);
        object = typeDeserializer = this._valueTypeDeserializer;
        if (typeDeserializer != null) {
            object = typeDeserializer.forProperty(beanProperty);
        }
        return this.withResolved(object2, jsonDeserializer, (TypeDeserializer)object);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public EnumMap<?, ?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        EnumMap enumMap;
        if (jsonParser.getCurrentToken() != JsonToken.START_OBJECT) {
            throw deserializationContext.mappingException(EnumMap.class);
        }
        EnumMap enumMap2 = this.constructMap();
        JsonDeserializer<Object> jsonDeserializer = this._valueDeserializer;
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            Enum enum_ = this._keyDeserializer.deserialize(jsonParser, deserializationContext);
            if (enum_ == null) {
                if (!deserializationContext.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) {
                    enumMap = enumMap2 = null;
                    if (!jsonParser.hasCurrentToken()) throw deserializationContext.weirdStringException((String)((Object)enumMap), this._enumClass, "value not one of declared Enum instance names");
                    enumMap = jsonParser.getText();
                    throw deserializationContext.weirdStringException((String)((Object)enumMap), this._enumClass, "value not one of declared Enum instance names");
                }
                jsonParser.nextToken();
                jsonParser.skipChildren();
                continue;
            }
            enumMap = jsonParser.nextToken() == JsonToken.VALUE_NULL ? jsonDeserializer.getNullValue() : (typeDeserializer == null ? jsonDeserializer.deserialize(jsonParser, deserializationContext) : jsonDeserializer.deserializeWithType(jsonParser, deserializationContext, typeDeserializer));
            enumMap2.put(enum_, enumMap);
        }
        return enumMap2;
        catch (Exception exception) {
            enumMap = enumMap2;
            throw deserializationContext.weirdStringException((String)((Object)enumMap), this._enumClass, "value not one of declared Enum instance names");
        }
    }

    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        return typeDeserializer.deserializeTypedFromObject(jsonParser, deserializationContext);
    }

    @Override
    public boolean isCachable() {
        return true;
    }

    public EnumMapDeserializer withResolved(JsonDeserializer<?> jsonDeserializer, JsonDeserializer<?> jsonDeserializer2, TypeDeserializer typeDeserializer) {
        if (jsonDeserializer == this._keyDeserializer && jsonDeserializer2 == this._valueDeserializer && typeDeserializer == this._valueTypeDeserializer) {
            return this;
        }
        return new EnumMapDeserializer(this._mapType, jsonDeserializer, jsonDeserializer2, this._valueTypeDeserializer);
    }
}

