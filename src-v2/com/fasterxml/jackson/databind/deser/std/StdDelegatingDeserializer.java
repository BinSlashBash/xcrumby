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
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import java.io.IOException;
import java.io.Serializable;

public class StdDelegatingDeserializer<T>
extends StdDeserializer<T>
implements ContextualDeserializer,
ResolvableDeserializer {
    private static final long serialVersionUID = 1;
    protected final Converter<Object, T> _converter;
    protected final JsonDeserializer<Object> _delegateDeserializer;
    protected final JavaType _delegateType;

    public StdDelegatingDeserializer(Converter<?, T> converter) {
        super(Object.class);
        this._converter = converter;
        this._delegateType = null;
        this._delegateDeserializer = null;
    }

    public StdDelegatingDeserializer(Converter<Object, T> converter, JavaType javaType, JsonDeserializer<?> jsonDeserializer) {
        super(javaType);
        this._converter = converter;
        this._delegateType = javaType;
        this._delegateDeserializer = jsonDeserializer;
    }

    protected T convertValue(Object object) {
        return this._converter.convert(object);
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext serializable, BeanProperty object) throws JsonMappingException {
        if (this._delegateDeserializer != null) {
            object = serializable.handleSecondaryContextualization(this._delegateDeserializer, (BeanProperty)object);
            serializable = this;
            if (object != this._delegateDeserializer) {
                serializable = this.withDelegate(this._converter, this._delegateType, object);
            }
            return serializable;
        }
        JavaType javaType = this._converter.getInputType(serializable.getTypeFactory());
        return this.withDelegate(this._converter, javaType, serializable.findContextualValueDeserializer(javaType, (BeanProperty)object));
    }

    @Override
    public T deserialize(JsonParser object, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if ((object = this._delegateDeserializer.deserialize((JsonParser)object, deserializationContext)) == null) {
            return null;
        }
        return this.convertValue(object);
    }

    @Override
    public Object deserializeWithType(JsonParser object, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        if ((object = this._delegateDeserializer.deserializeWithType((JsonParser)object, deserializationContext, typeDeserializer)) == null) {
            return null;
        }
        return this.convertValue(object);
    }

    @Override
    public JsonDeserializer<?> getDelegatee() {
        return this._delegateDeserializer;
    }

    @Override
    public Class<?> handledType() {
        return this._delegateDeserializer.handledType();
    }

    @Override
    public void resolve(DeserializationContext deserializationContext) throws JsonMappingException {
        if (this._delegateDeserializer != null && this._delegateDeserializer instanceof ResolvableDeserializer) {
            ((ResolvableDeserializer)((Object)this._delegateDeserializer)).resolve(deserializationContext);
        }
    }

    protected StdDelegatingDeserializer<T> withDelegate(Converter<Object, T> converter, JavaType javaType, JsonDeserializer<?> jsonDeserializer) {
        if (this.getClass() != StdDelegatingDeserializer.class) {
            throw new IllegalStateException("Sub-class " + this.getClass().getName() + " must override 'withDelegate'");
        }
        return new StdDelegatingDeserializer<T>(converter, javaType, jsonDeserializer);
    }
}

