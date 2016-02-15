/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.std.ContainerDeserializerBase;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.util.Collection;

@JacksonStdImpl
public final class StringCollectionDeserializer
extends ContainerDeserializerBase<Collection<String>>
implements ContextualDeserializer {
    private static final long serialVersionUID = 1;
    protected final JavaType _collectionType;
    protected final JsonDeserializer<Object> _delegateDeserializer;
    protected final JsonDeserializer<String> _valueDeserializer;
    protected final ValueInstantiator _valueInstantiator;

    public StringCollectionDeserializer(JavaType javaType, JsonDeserializer<?> jsonDeserializer, ValueInstantiator valueInstantiator) {
        this(javaType, valueInstantiator, null, jsonDeserializer);
    }

    protected StringCollectionDeserializer(JavaType javaType, ValueInstantiator valueInstantiator, JsonDeserializer<?> jsonDeserializer, JsonDeserializer<?> jsonDeserializer2) {
        super(javaType);
        this._collectionType = javaType;
        this._valueDeserializer = jsonDeserializer2;
        this._valueInstantiator = valueInstantiator;
        this._delegateDeserializer = jsonDeserializer;
    }

    /*
     * Enabled aggressive block sorting
     */
    private Collection<String> deserializeUsingCustom(JsonParser jsonParser, DeserializationContext deserializationContext, Collection<String> collection, JsonDeserializer<String> jsonDeserializer) throws IOException {
        Object object;
        while ((object = jsonParser.nextToken()) != JsonToken.END_ARRAY) {
            object = object == JsonToken.VALUE_NULL ? jsonDeserializer.getNullValue() : jsonDeserializer.deserialize(jsonParser, deserializationContext);
            collection.add((String)object);
        }
        return collection;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final Collection<String> handleNonArray(JsonParser object, DeserializationContext deserializationContext, Collection<String> collection) throws IOException {
        if (!deserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
            throw deserializationContext.mappingException(this._collectionType.getRawClass());
        }
        JsonDeserializer<String> jsonDeserializer = this._valueDeserializer;
        object = object.getCurrentToken() == JsonToken.VALUE_NULL ? (jsonDeserializer == null ? null : jsonDeserializer.getNullValue()) : (jsonDeserializer == null ? this._parseString((JsonParser)object, deserializationContext) : jsonDeserializer.deserialize((JsonParser)object, deserializationContext));
        collection.add((String)object);
        return collection;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext object, BeanProperty beanProperty) throws JsonMappingException {
        JsonDeserializer<String> jsonDeserializer;
        Object var3_3;
        void var4_12;
        void var3_7;
        Object var4_9 = var3_3 = null;
        if (this._valueInstantiator != null) {
            Object var4_10 = var3_3;
            if (this._valueInstantiator.getDelegateCreator() != null) {
                JsonDeserializer<Object> jsonDeserializer2 = this.findDeserializer((DeserializationContext)object, this._valueInstantiator.getDelegateType(object.getConfig()), beanProperty);
            }
        }
        if ((jsonDeserializer = this._valueDeserializer) == null) {
            JsonDeserializer<String> jsonDeserializer3;
            JsonDeserializer<String> jsonDeserializer4 = jsonDeserializer3 = this.findConvertingContentDeserializer((DeserializationContext)object, beanProperty, jsonDeserializer);
            if (jsonDeserializer3 == null) {
                JsonDeserializer<Object> jsonDeserializer5 = object.findContextualValueDeserializer((JavaType)this._collectionType.getContentType(), beanProperty);
            }
        } else {
            JsonDeserializer jsonDeserializer6 = object.handleSecondaryContextualization(jsonDeserializer, beanProperty);
        }
        object = var3_7;
        if (this.isDefaultDeserializer(var3_7)) {
            object = null;
        }
        return this.withResolved(var4_12, object);
    }

    @Override
    public Collection<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (this._delegateDeserializer != null) {
            return (Collection)this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
        }
        return this.deserialize(jsonParser, deserializationContext, (Collection)this._valueInstantiator.createUsingDefault(deserializationContext));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Collection<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Collection<String> collection) throws IOException {
        if (!jsonParser.isExpectedStartArrayToken()) {
            return this.handleNonArray(jsonParser, deserializationContext, collection);
        }
        if (this._valueDeserializer != null) {
            return this.deserializeUsingCustom(jsonParser, deserializationContext, collection, this._valueDeserializer);
        }
        do {
            JsonToken jsonToken = jsonParser.nextToken();
            Object object = collection;
            if (jsonToken == JsonToken.END_ARRAY) return object;
            object = jsonToken == JsonToken.VALUE_STRING ? jsonParser.getText() : (jsonToken == JsonToken.VALUE_NULL ? null : this._parseString(jsonParser, deserializationContext));
            collection.add((String)object);
        } while (true);
    }

    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromArray(jsonParser, deserializationContext);
    }

    @Override
    public JsonDeserializer<Object> getContentDeserializer() {
        return this._valueDeserializer;
    }

    @Override
    public JavaType getContentType() {
        return this._collectionType.getContentType();
    }

    protected StringCollectionDeserializer withResolved(JsonDeserializer<?> jsonDeserializer, JsonDeserializer<?> jsonDeserializer2) {
        if (this._valueDeserializer == jsonDeserializer2 && this._delegateDeserializer == jsonDeserializer) {
            return this;
        }
        return new StringCollectionDeserializer(this._collectionType, this._valueInstantiator, jsonDeserializer, jsonDeserializer2);
    }
}

