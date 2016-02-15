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
import com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeDeserializer;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.Closeable;
import java.io.IOException;

public class AsPropertyTypeDeserializer
extends AsArrayTypeDeserializer {
    private static final long serialVersionUID = 1;

    public AsPropertyTypeDeserializer(JavaType javaType, TypeIdResolver typeIdResolver, String string2, boolean bl2, Class<?> class_) {
        super(javaType, typeIdResolver, string2, bl2, class_);
    }

    public AsPropertyTypeDeserializer(AsPropertyTypeDeserializer asPropertyTypeDeserializer, BeanProperty beanProperty) {
        super(asPropertyTypeDeserializer, beanProperty);
    }

    protected final Object _deserializeTypedForId(JsonParser jsonParser, DeserializationContext deserializationContext, TokenBuffer closeable) throws IOException {
        String string2 = jsonParser.getText();
        JsonDeserializer<Object> jsonDeserializer = this._findDeserializer(deserializationContext, string2);
        TokenBuffer tokenBuffer = closeable;
        if (this._typeIdVisible) {
            tokenBuffer = closeable;
            if (closeable == null) {
                tokenBuffer = new TokenBuffer(null, false);
            }
            tokenBuffer.writeFieldName(jsonParser.getCurrentName());
            tokenBuffer.writeString(string2);
        }
        closeable = jsonParser;
        if (tokenBuffer != null) {
            closeable = JsonParserSequence.createFlattened(tokenBuffer.asParser(jsonParser), jsonParser);
        }
        closeable.nextToken();
        return jsonDeserializer.deserialize((JsonParser)closeable, deserializationContext);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected Object _deserializeTypedUsingDefaultImpl(JsonParser jsonParser, DeserializationContext deserializationContext, TokenBuffer object) throws IOException {
        Object object2;
        JsonDeserializer<Object> jsonDeserializer = this._findDefaultImplDeserializer(deserializationContext);
        if (jsonDeserializer != null) {
            JsonParser jsonParser2 = jsonParser;
            if (object == null) return jsonDeserializer.deserialize(jsonParser2, deserializationContext);
            object.writeEndObject();
            jsonParser2 = object.asParser(jsonParser);
            jsonParser2.nextToken();
            return jsonDeserializer.deserialize(jsonParser2, deserializationContext);
        }
        object = object2 = TypeDeserializer.deserializeIfNatural(jsonParser, deserializationContext, this._baseType);
        if (object2 != null) return object;
        if (jsonParser.getCurrentToken() != JsonToken.START_ARRAY) throw deserializationContext.wrongTokenException(jsonParser, JsonToken.FIELD_NAME, "missing property '" + this._typePropertyName + "' that is to contain type id  (for class " + this.baseTypeName() + ")");
        return super.deserializeTypedFromAny(jsonParser, deserializationContext);
    }

    @Override
    public Object deserializeTypedFromAny(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.getCurrentToken() == JsonToken.START_ARRAY) {
            return super.deserializeTypedFromArray(jsonParser, deserializationContext);
        }
        return this.deserializeTypedFromObject(jsonParser, deserializationContext);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Object deserializeTypedFromObject(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Object object;
        if (jsonParser.canReadTypeId() && (object = jsonParser.getTypeId()) != null) {
            return this._deserializeWithNativeTypeId(jsonParser, deserializationContext, object);
        }
        Object object2 = jsonParser.getCurrentToken();
        if (object2 == JsonToken.START_OBJECT) {
            object = jsonParser.nextToken();
        } else {
            if (object2 == JsonToken.START_ARRAY) {
                return this._deserializeTypedUsingDefaultImpl(jsonParser, deserializationContext, null);
            }
            object = object2;
            if (object2 != JsonToken.FIELD_NAME) {
                return this._deserializeTypedUsingDefaultImpl(jsonParser, deserializationContext, null);
            }
        }
        object2 = null;
        Object object3 = object;
        while (object3 == JsonToken.FIELD_NAME) {
            object3 = jsonParser.getCurrentName();
            jsonParser.nextToken();
            if (this._typePropertyName.equals(object3)) {
                return this._deserializeTypedForId(jsonParser, deserializationContext, (TokenBuffer)object2);
            }
            object = object2;
            if (object2 == null) {
                object = new TokenBuffer(null, false);
            }
            object.writeFieldName((String)object3);
            object.copyCurrentStructure(jsonParser);
            object3 = jsonParser.nextToken();
            object2 = object;
        }
        return this._deserializeTypedUsingDefaultImpl(jsonParser, deserializationContext, (TokenBuffer)object2);
    }

    @Override
    public TypeDeserializer forProperty(BeanProperty beanProperty) {
        if (beanProperty == this._property) {
            return this;
        }
        return new AsPropertyTypeDeserializer(this, beanProperty);
    }

    @Override
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.PROPERTY;
    }
}

