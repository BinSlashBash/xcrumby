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

public class AsWrapperTypeDeserializer
extends TypeDeserializerBase
implements Serializable {
    private static final long serialVersionUID = 5345570420394408290L;

    public AsWrapperTypeDeserializer(JavaType javaType, TypeIdResolver typeIdResolver, String string2, boolean bl2, Class<?> class_) {
        super(javaType, typeIdResolver, string2, bl2, null);
    }

    protected AsWrapperTypeDeserializer(AsWrapperTypeDeserializer asWrapperTypeDeserializer, BeanProperty beanProperty) {
        super(asWrapperTypeDeserializer, beanProperty);
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
        if (object.getCurrentToken() != JsonToken.START_OBJECT) {
            throw deserializationContext.wrongTokenException((JsonParser)object, JsonToken.START_OBJECT, "need JSON Object to contain As.WRAPPER_OBJECT type information for class " + this.baseTypeName());
        }
        if (object.nextToken() != JsonToken.FIELD_NAME) {
            throw deserializationContext.wrongTokenException((JsonParser)object, JsonToken.FIELD_NAME, "need JSON String that contains type id (for subtype of " + this.baseTypeName() + ")");
        }
        String string2 = object.getText();
        JsonDeserializer<Object> jsonDeserializer = this._findDeserializer(deserializationContext, string2);
        object.nextToken();
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
        object = jsonDeserializer.deserialize((JsonParser)object2, deserializationContext);
        if (object2.nextToken() == JsonToken.END_OBJECT) return object;
        throw deserializationContext.wrongTokenException((JsonParser)object2, JsonToken.END_OBJECT, "expected closing END_OBJECT after type information and deserialized value");
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
        return new AsWrapperTypeDeserializer(this, beanProperty);
    }

    @Override
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.WRAPPER_OBJECT;
    }
}

