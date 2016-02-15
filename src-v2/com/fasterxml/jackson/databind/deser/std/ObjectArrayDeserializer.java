/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.Base64Variant;
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
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.ContainerDeserializerBase;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
import java.io.IOException;
import java.lang.reflect.Array;

@JacksonStdImpl
public class ObjectArrayDeserializer
extends ContainerDeserializerBase<Object[]>
implements ContextualDeserializer {
    private static final long serialVersionUID = 1;
    protected final ArrayType _arrayType;
    protected final Class<?> _elementClass;
    protected JsonDeserializer<Object> _elementDeserializer;
    protected final TypeDeserializer _elementTypeDeserializer;
    protected final boolean _untyped;

    /*
     * Enabled aggressive block sorting
     */
    public ObjectArrayDeserializer(ArrayType arrayType, JsonDeserializer<Object> jsonDeserializer, TypeDeserializer typeDeserializer) {
        super(arrayType);
        this._arrayType = arrayType;
        this._elementClass = arrayType.getContentType().getRawClass();
        boolean bl2 = this._elementClass == Object.class;
        this._untyped = bl2;
        this._elementDeserializer = jsonDeserializer;
        this._elementTypeDeserializer = typeDeserializer;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final Object[] handleNonArray(JsonParser object, DeserializationContext arrobject) throws IOException, JsonProcessingException {
        if (object.getCurrentToken() == JsonToken.VALUE_STRING && arrobject.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) && object.getText().length() == 0) {
            return null;
        }
        if (!arrobject.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
            if (object.getCurrentToken() == JsonToken.VALUE_STRING && this._elementClass == Byte.class) {
                return this.deserializeFromBase64((JsonParser)object, (DeserializationContext)arrobject);
            }
            throw arrobject.mappingException(this._arrayType.getRawClass());
        }
        object = object.getCurrentToken() == JsonToken.VALUE_NULL ? this._elementDeserializer.getNullValue() : (this._elementTypeDeserializer == null ? this._elementDeserializer.deserialize((JsonParser)object, (DeserializationContext)arrobject) : this._elementDeserializer.deserializeWithType((JsonParser)object, (DeserializationContext)arrobject, this._elementTypeDeserializer));
        arrobject = this._untyped ? new Object[1] : (Object[])Array.newInstance(this._elementClass, 1);
        arrobject[0] = object;
        return arrobject;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext jsonDeserializer, BeanProperty beanProperty) throws JsonMappingException {
        TypeDeserializer typeDeserializer;
        JsonDeserializer<Object> jsonDeserializer2 = this.findConvertingContentDeserializer((DeserializationContext)((Object)jsonDeserializer), beanProperty, this._elementDeserializer);
        jsonDeserializer = jsonDeserializer2 == null ? jsonDeserializer.findContextualValueDeserializer((JavaType)this._arrayType.getContentType(), beanProperty) : jsonDeserializer.handleSecondaryContextualization(jsonDeserializer2, beanProperty);
        jsonDeserializer2 = typeDeserializer = this._elementTypeDeserializer;
        if (typeDeserializer != null) {
            jsonDeserializer2 = typeDeserializer.forProperty(beanProperty);
        }
        return this.withDeserializer((TypeDeserializer)((Object)jsonDeserializer2), jsonDeserializer);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Object[] deserialize(JsonParser arrobject, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Object object;
        if (!arrobject.isExpectedStartArrayToken()) {
            return this.handleNonArray((JsonParser)arrobject, deserializationContext);
        }
        ObjectBuffer objectBuffer = deserializationContext.leaseObjectBuffer();
        Object[] arrobject2 = objectBuffer.resetAndStart();
        int n2 = 0;
        TypeDeserializer typeDeserializer = this._elementTypeDeserializer;
        while ((object = arrobject.nextToken()) != JsonToken.END_ARRAY) {
            object = object == JsonToken.VALUE_NULL ? this._elementDeserializer.getNullValue() : (typeDeserializer == null ? this._elementDeserializer.deserialize((JsonParser)arrobject, deserializationContext) : this._elementDeserializer.deserializeWithType((JsonParser)arrobject, deserializationContext, typeDeserializer));
            Object[] arrobject3 = arrobject2;
            int n3 = n2;
            if (n2 >= arrobject2.length) {
                arrobject3 = objectBuffer.appendCompletedChunk(arrobject2);
                n3 = 0;
            }
            arrobject3[n3] = object;
            n2 = n3 + 1;
            arrobject2 = arrobject3;
        }
        arrobject = this._untyped ? objectBuffer.completeAndClearBuffer(arrobject2, n2) : objectBuffer.completeAndClearBuffer(arrobject2, n2, this._elementClass);
        deserializationContext.returnObjectBuffer(objectBuffer);
        return arrobject;
    }

    protected Byte[] deserializeFromBase64(JsonParser jsonParser, DeserializationContext arrbyte) throws IOException, JsonProcessingException {
        jsonParser = (JsonParser)jsonParser.getBinaryValue(arrbyte.getBase64Variant());
        arrbyte = new Byte[jsonParser.length];
        int n2 = jsonParser.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            arrbyte[i2] = Byte.valueOf((byte)jsonParser[i2]);
        }
        return arrbyte;
    }

    public Object[] deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        return (Object[])typeDeserializer.deserializeTypedFromArray(jsonParser, deserializationContext);
    }

    @Override
    public JsonDeserializer<Object> getContentDeserializer() {
        return this._elementDeserializer;
    }

    @Override
    public JavaType getContentType() {
        return this._arrayType.getContentType();
    }

    public ObjectArrayDeserializer withDeserializer(TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) {
        if (jsonDeserializer == this._elementDeserializer && typeDeserializer == this._elementTypeDeserializer) {
            return this;
        }
        return new ObjectArrayDeserializer(this._arrayType, jsonDeserializer, typeDeserializer);
    }
}

