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
import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.deser.std.ContainerDeserializerBase;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@JacksonStdImpl
public class CollectionDeserializer
extends ContainerDeserializerBase<Collection<Object>>
implements ContextualDeserializer {
    private static final long serialVersionUID = 3917273725180652224L;
    protected final JavaType _collectionType;
    protected final JsonDeserializer<Object> _delegateDeserializer;
    protected final JsonDeserializer<Object> _valueDeserializer;
    protected final ValueInstantiator _valueInstantiator;
    protected final TypeDeserializer _valueTypeDeserializer;

    public CollectionDeserializer(JavaType javaType, JsonDeserializer<Object> jsonDeserializer, TypeDeserializer typeDeserializer, ValueInstantiator valueInstantiator) {
        this(javaType, jsonDeserializer, typeDeserializer, valueInstantiator, null);
    }

    protected CollectionDeserializer(JavaType javaType, JsonDeserializer<Object> jsonDeserializer, TypeDeserializer typeDeserializer, ValueInstantiator valueInstantiator, JsonDeserializer<Object> jsonDeserializer2) {
        super(javaType);
        this._collectionType = javaType;
        this._valueDeserializer = jsonDeserializer;
        this._valueTypeDeserializer = typeDeserializer;
        this._valueInstantiator = valueInstantiator;
        this._delegateDeserializer = jsonDeserializer2;
    }

    protected CollectionDeserializer(CollectionDeserializer collectionDeserializer) {
        super(collectionDeserializer._collectionType);
        this._collectionType = collectionDeserializer._collectionType;
        this._valueDeserializer = collectionDeserializer._valueDeserializer;
        this._valueTypeDeserializer = collectionDeserializer._valueTypeDeserializer;
        this._valueInstantiator = collectionDeserializer._valueInstantiator;
        this._delegateDeserializer = collectionDeserializer._delegateDeserializer;
    }

    /*
     * Enabled aggressive block sorting
     */
    public CollectionDeserializer createContextual(DeserializationContext jsonDeserializer, BeanProperty beanProperty) throws JsonMappingException {
        Object object;
        TypeDeserializer typeDeserializer;
        Object object2 = object = null;
        if (this._valueInstantiator != null) {
            object2 = object;
            if (this._valueInstantiator.canCreateUsingDelegate()) {
                object2 = this._valueInstantiator.getDelegateType(jsonDeserializer.getConfig());
                if (object2 == null) {
                    throw new IllegalArgumentException("Invalid delegate-creator definition for " + this._collectionType + ": value instantiator (" + this._valueInstantiator.getClass().getName() + ") returned true for 'canCreateUsingDelegate()', but null for 'getDelegateType()'");
                }
                object2 = this.findDeserializer((DeserializationContext)((Object)jsonDeserializer), (JavaType)object2, beanProperty);
            }
        }
        jsonDeserializer = (object = this.findConvertingContentDeserializer((DeserializationContext)((Object)jsonDeserializer), beanProperty, this._valueDeserializer)) == null ? jsonDeserializer.findContextualValueDeserializer((JavaType)this._collectionType.getContentType(), beanProperty) : jsonDeserializer.handleSecondaryContextualization(object, beanProperty);
        object = typeDeserializer = this._valueTypeDeserializer;
        if (typeDeserializer != null) {
            object = typeDeserializer.forProperty(beanProperty);
        }
        return this.withResolved(object2, jsonDeserializer, (TypeDeserializer)object);
    }

    @Override
    public Collection<Object> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String string2;
        if (this._delegateDeserializer != null) {
            return (Collection)this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
        }
        if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING && (string2 = jsonParser.getText()).length() == 0) {
            return (Collection)this._valueInstantiator.createFromString(deserializationContext, string2);
        }
        return this.deserialize(jsonParser, deserializationContext, (Collection)this._valueInstantiator.createUsingDefault(deserializationContext));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Collection<Object> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Collection<Object> collection) throws IOException, JsonProcessingException {
        if (!jsonParser.isExpectedStartArrayToken()) {
            return this.handleNonArray(jsonParser, deserializationContext, collection);
        }
        JsonDeserializer<Object> jsonDeserializer = this._valueDeserializer;
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        CollectionReferringAccumulator collectionReferringAccumulator = jsonDeserializer.getObjectIdReader() == null ? null : new CollectionReferringAccumulator(this._collectionType.getContentType().getRawClass(), collection);
        do {
            Object object;
            UnresolvedForwardReference unresolvedForwardReference;
            block7 : {
                object = jsonParser.nextToken();
                Object object2 = collection;
                if (object == JsonToken.END_ARRAY) return object2;
                try {
                    object2 = object == JsonToken.VALUE_NULL ? jsonDeserializer.getNullValue() : (typeDeserializer == null ? jsonDeserializer.deserialize(jsonParser, deserializationContext) : jsonDeserializer.deserializeWithType(jsonParser, deserializationContext, typeDeserializer));
                }
                catch (UnresolvedForwardReference unresolvedForwardReference) {
                    if (collectionReferringAccumulator == null) {
                        throw JsonMappingException.from(jsonParser, "Unresolved forward reference but no identity info.", unresolvedForwardReference);
                    }
                    break block7;
                }
                if (collectionReferringAccumulator != null) {
                    collectionReferringAccumulator.add(object2);
                    continue;
                }
                collection.add(object2);
                continue;
            }
            object = collectionReferringAccumulator.handleUnresolvedReference(unresolvedForwardReference);
            unresolvedForwardReference.getRoid().appendReferring((ReadableObjectId.Referring)object);
        } while (true);
    }

    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
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

    /*
     * Enabled aggressive block sorting
     */
    protected final Collection<Object> handleNonArray(JsonParser object, DeserializationContext deserializationContext, Collection<Object> collection) throws IOException, JsonProcessingException {
        if (!deserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
            throw deserializationContext.mappingException(this._collectionType.getRawClass());
        }
        JsonDeserializer<Object> jsonDeserializer = this._valueDeserializer;
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        object = object.getCurrentToken() == JsonToken.VALUE_NULL ? jsonDeserializer.getNullValue() : (typeDeserializer == null ? jsonDeserializer.deserialize((JsonParser)object, deserializationContext) : jsonDeserializer.deserializeWithType((JsonParser)object, deserializationContext, typeDeserializer));
        collection.add(object);
        return collection;
    }

    protected CollectionDeserializer withResolved(JsonDeserializer<?> jsonDeserializer, JsonDeserializer<?> jsonDeserializer2, TypeDeserializer typeDeserializer) {
        if (jsonDeserializer == this._delegateDeserializer && jsonDeserializer2 == this._valueDeserializer && typeDeserializer == this._valueTypeDeserializer) {
            return this;
        }
        return new CollectionDeserializer(this._collectionType, jsonDeserializer2, typeDeserializer, this._valueInstantiator, jsonDeserializer);
    }

    private static final class CollectionReferring
    extends ReadableObjectId.Referring {
        private final CollectionReferringAccumulator _parent;
        public final List<Object> next = new ArrayList<Object>();

        private CollectionReferring(CollectionReferringAccumulator collectionReferringAccumulator, UnresolvedForwardReference unresolvedForwardReference, Class<?> class_) {
            super(unresolvedForwardReference, class_);
            this._parent = collectionReferringAccumulator;
        }

        @Override
        public void handleResolvedForwardReference(Object object, Object object2) throws IOException {
            this._parent.resolveForwardReference(object, object2);
        }
    }

    public static final class CollectionReferringAccumulator {
        private List<CollectionReferring> _accumulator = new ArrayList<CollectionReferring>();
        private final Class<?> _elementType;
        private final Collection<Object> _result;

        public CollectionReferringAccumulator(Class<?> class_, Collection<Object> collection) {
            this._elementType = class_;
            this._result = collection;
        }

        public void add(Object object) {
            if (this._accumulator.isEmpty()) {
                this._result.add(object);
                return;
            }
            this._accumulator.get((int)(this._accumulator.size() - 1)).next.add(object);
        }

        public ReadableObjectId.Referring handleUnresolvedReference(UnresolvedForwardReference object) {
            object = new CollectionReferring(this, (UnresolvedForwardReference)object, this._elementType);
            this._accumulator.add((CollectionReferring)object);
            return object;
        }

        public void resolveForwardReference(Object object, Object object2) throws IOException {
            Iterator<CollectionReferring> iterator = this._accumulator.iterator();
            Collection<Object> collection = this._result;
            while (iterator.hasNext()) {
                CollectionReferring collectionReferring = iterator.next();
                if (collectionReferring.hasId(object)) {
                    iterator.remove();
                    collection.add(object2);
                    collection.addAll(collectionReferring.next);
                    return;
                }
                collection = collectionReferring.next;
            }
            throw new IllegalArgumentException("Trying to resolve a forward reference with id [" + object + "] that wasn't previously seen as unresolved.");
        }
    }

}

