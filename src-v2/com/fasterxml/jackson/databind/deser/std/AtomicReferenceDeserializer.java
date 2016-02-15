/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDeserializer
extends StdDeserializer<AtomicReference<?>>
implements ContextualDeserializer {
    private static final long serialVersionUID = 1;
    protected final JavaType _referencedType;
    protected final JsonDeserializer<?> _valueDeserializer;
    protected final TypeDeserializer _valueTypeDeserializer;

    public AtomicReferenceDeserializer(JavaType javaType) {
        this(javaType, null, null);
    }

    public AtomicReferenceDeserializer(JavaType javaType, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) {
        super(AtomicReference.class);
        this._referencedType = javaType;
        this._valueDeserializer = jsonDeserializer;
        this._valueTypeDeserializer = typeDeserializer;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext object, BeanProperty beanProperty) throws JsonMappingException {
        JsonDeserializer jsonDeserializer = this._valueDeserializer;
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        JsonDeserializer jsonDeserializer2 = jsonDeserializer;
        if (jsonDeserializer == null) {
            jsonDeserializer2 = object.findContextualValueDeserializer(this._referencedType, beanProperty);
        }
        object = typeDeserializer;
        if (typeDeserializer != null) {
            object = typeDeserializer.forProperty(beanProperty);
        }
        if (jsonDeserializer2 == this._valueDeserializer && object == this._valueTypeDeserializer) {
            return this;
        }
        return this.withResolved((TypeDeserializer)object, jsonDeserializer2);
    }

    @Override
    public AtomicReference<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (this._valueTypeDeserializer != null) {
            return new AtomicReference<Object>(this._valueDeserializer.deserializeWithType(jsonParser, deserializationContext, this._valueTypeDeserializer));
        }
        return new AtomicReference(this._valueDeserializer.deserialize(jsonParser, deserializationContext));
    }

    public Object[] deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
        return (Object[])typeDeserializer.deserializeTypedFromAny(jsonParser, deserializationContext);
    }

    @Override
    public AtomicReference<?> getNullValue() {
        return new AtomicReference();
    }

    public AtomicReferenceDeserializer withResolved(TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) {
        return new AtomicReferenceDeserializer(this._referencedType, typeDeserializer, jsonDeserializer);
    }
}

