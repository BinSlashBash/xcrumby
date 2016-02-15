/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.util.JsonParserSequence;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.TypeDeserializerBase;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.io.Serializable;

public class AsArrayTypeDeserializer
extends TypeDeserializerBase
implements Serializable {
    private static final long serialVersionUID = 5345570420394408290L;

    public AsArrayTypeDeserializer(JavaType javaType, TypeIdResolver typeIdResolver, String string2, boolean bl2, Class<?> class_) {
        super(javaType, typeIdResolver, string2, bl2, class_);
    }

    public AsArrayTypeDeserializer(AsArrayTypeDeserializer asArrayTypeDeserializer, BeanProperty beanProperty) {
        super(asArrayTypeDeserializer, beanProperty);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final Object _deserialize(JsonParser object, DeserializationContext deserializationContext) throws IOException {
        Object object2;
        if (object.canReadTypeId() && (object2 = object.getTypeId()) != null) {
            return this._deserializeWithNativeTypeId((JsonParser)object, deserializationContext, object2);
        }
        boolean bl2 = object.isExpectedStartArrayToken();
        String string2 = this._locateTypeId((JsonParser)object, deserializationContext);
        Object object3 = this._findDeserializer(deserializationContext, string2);
        object2 = object;
        if (this._typeIdVisible) {
            object2 = object;
            if (object.getCurrentToken() == JsonToken.START_OBJECT) {
                object2 = new TokenBuffer(null, false);
                object2.writeStartObject();
                object2.writeFieldName(this._typePropertyName);
                object2.writeString(string2);
                object2 = JsonParserSequence.createFlattened(object2.asParser((JsonParser)object), (JsonParser)object);
                object2.nextToken();
            }
        }
        object = object3 = object3.deserialize((JsonParser)object2, deserializationContext);
        if (!bl2) return object;
        object = object3;
        if (object2.nextToken() == JsonToken.END_ARRAY) return object;
        throw deserializationContext.wrongTokenException((JsonParser)object2, JsonToken.END_ARRAY, "expected closing END_ARRAY after type information and deserialized value");
    }

    protected final String _locateTypeId(JsonParser jsonParser, DeserializationContext object) throws IOException {
        if (!jsonParser.isExpectedStartArrayToken()) {
            if (this._defaultImpl != null) {
                return this._idResolver.idFromBaseType();
            }
            throw object.wrongTokenException(jsonParser, JsonToken.START_ARRAY, "need JSON Array to contain As.WRAPPER_ARRAY type information for class " + this.baseTypeName());
        }
        if (jsonParser.nextToken() == JsonToken.VALUE_STRING) {
            object = jsonParser.getText();
            jsonParser.nextToken();
            return object;
        }
        if (this._defaultImpl != null) {
            return this._idResolver.idFromBaseType();
        }
        throw object.wrongTokenException(jsonParser, JsonToken.VALUE_STRING, "need JSON String that contains type id (for subtype of " + this.baseTypeName() + ")");
    }

    @Override
    public Object deserializeTypedFromAny(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return this._deserialize(jsonParser, deserializationContext);
    }

    @Override
    public Object deserializeTypedFromArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return this._deserialize(jsonParser, deserializationContext);
    }

    @Override
    public Object deserializeTypedFromObject(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return this._deserialize(jsonParser, deserializationContext);
    }

    @Override
    public Object deserializeTypedFromScalar(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return this._deserialize(jsonParser, deserializationContext);
    }

    @Override
    public TypeDeserializer forProperty(BeanProperty beanProperty) {
        if (beanProperty == this._property) {
            return this;
        }
        return new AsArrayTypeDeserializer(this, beanProperty);
    }

    @Override
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.WRAPPER_ARRAY;
    }
}

