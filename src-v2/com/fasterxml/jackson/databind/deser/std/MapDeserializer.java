/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualKeyDeserializer;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
import com.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.deser.std.ContainerDeserializerBase;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JacksonStdImpl
public class MapDeserializer
extends ContainerDeserializerBase<Map<Object, Object>>
implements ContextualDeserializer,
ResolvableDeserializer {
    private static final long serialVersionUID = -3378654289961736240L;
    protected JsonDeserializer<Object> _delegateDeserializer;
    protected final boolean _hasDefaultCreator;
    protected HashSet<String> _ignorableProperties;
    protected final KeyDeserializer _keyDeserializer;
    protected final JavaType _mapType;
    protected PropertyBasedCreator _propertyBasedCreator;
    protected boolean _standardStringKey;
    protected final JsonDeserializer<Object> _valueDeserializer;
    protected final ValueInstantiator _valueInstantiator;
    protected final TypeDeserializer _valueTypeDeserializer;

    public MapDeserializer(JavaType javaType, ValueInstantiator valueInstantiator, KeyDeserializer keyDeserializer, JsonDeserializer<Object> jsonDeserializer, TypeDeserializer typeDeserializer) {
        super(javaType);
        this._mapType = javaType;
        this._keyDeserializer = keyDeserializer;
        this._valueDeserializer = jsonDeserializer;
        this._valueTypeDeserializer = typeDeserializer;
        this._valueInstantiator = valueInstantiator;
        this._hasDefaultCreator = valueInstantiator.canCreateUsingDefault();
        this._delegateDeserializer = null;
        this._propertyBasedCreator = null;
        this._standardStringKey = this._isStdKeyDeser(javaType, keyDeserializer);
    }

    protected MapDeserializer(MapDeserializer mapDeserializer) {
        super(mapDeserializer._mapType);
        this._mapType = mapDeserializer._mapType;
        this._keyDeserializer = mapDeserializer._keyDeserializer;
        this._valueDeserializer = mapDeserializer._valueDeserializer;
        this._valueTypeDeserializer = mapDeserializer._valueTypeDeserializer;
        this._valueInstantiator = mapDeserializer._valueInstantiator;
        this._propertyBasedCreator = mapDeserializer._propertyBasedCreator;
        this._delegateDeserializer = mapDeserializer._delegateDeserializer;
        this._hasDefaultCreator = mapDeserializer._hasDefaultCreator;
        this._ignorableProperties = mapDeserializer._ignorableProperties;
        this._standardStringKey = mapDeserializer._standardStringKey;
    }

    protected MapDeserializer(MapDeserializer mapDeserializer, KeyDeserializer keyDeserializer, JsonDeserializer<Object> jsonDeserializer, TypeDeserializer typeDeserializer, HashSet<String> hashSet) {
        super(mapDeserializer._mapType);
        this._mapType = mapDeserializer._mapType;
        this._keyDeserializer = keyDeserializer;
        this._valueDeserializer = jsonDeserializer;
        this._valueTypeDeserializer = typeDeserializer;
        this._valueInstantiator = mapDeserializer._valueInstantiator;
        this._propertyBasedCreator = mapDeserializer._propertyBasedCreator;
        this._delegateDeserializer = mapDeserializer._delegateDeserializer;
        this._hasDefaultCreator = mapDeserializer._hasDefaultCreator;
        this._ignorableProperties = hashSet;
        this._standardStringKey = this._isStdKeyDeser(this._mapType, keyDeserializer);
    }

    private void handleUnresolvedReference(JsonParser object, MapReferringAccumulator mapReferringAccumulator, Object object2, UnresolvedForwardReference unresolvedForwardReference) throws JsonMappingException {
        if (mapReferringAccumulator == null) {
            throw JsonMappingException.from((JsonParser)object, "Unresolved forward reference but no identity info.", unresolvedForwardReference);
        }
        object = mapReferringAccumulator.handleUnresolvedReference(unresolvedForwardReference, object2);
        unresolvedForwardReference.getRoid().appendReferring((ReadableObjectId.Referring)object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Map<Object, Object> _deserializeUsingCreator(JsonParser object, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Object object2;
        PropertyBasedCreator propertyBasedCreator = this._propertyBasedCreator;
        PropertyValueBuffer propertyValueBuffer = propertyBasedCreator.startBuilding((JsonParser)object, deserializationContext, null);
        Object object3 = object2 = object.getCurrentToken();
        if (object2 == JsonToken.START_OBJECT) {
            object3 = object.nextToken();
        }
        object2 = this._valueDeserializer;
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        while (object3 == JsonToken.FIELD_NAME) {
            Object object4 = object.getCurrentName();
            object3 = object.nextToken();
            if (this._ignorableProperties != null && this._ignorableProperties.contains(object4)) {
                object.skipChildren();
            } else if ((object4 = propertyBasedCreator.findCreatorProperty((String)object4)) != null) {
                object3 = object4.deserialize((JsonParser)object, deserializationContext);
                if (propertyValueBuffer.assignParameter(object4.getCreatorIndex(), object3)) {
                    object.nextToken();
                    try {
                        object3 = (Map)propertyBasedCreator.build(deserializationContext, propertyValueBuffer);
                    }
                    catch (Exception var1_2) {
                        this.wrapAndThrow(var1_2, this._mapType.getRawClass());
                        return null;
                    }
                    this._readAndBind((JsonParser)object, deserializationContext, (Map<Object, Object>)object3);
                    return object3;
                }
            } else {
                object4 = object.getCurrentName();
                object4 = this._keyDeserializer.deserializeKey((String)object4, deserializationContext);
                object3 = object3 == JsonToken.VALUE_NULL ? object2.getNullValue() : (typeDeserializer == null ? object2.deserialize((JsonParser)object, deserializationContext) : object2.deserializeWithType((JsonParser)object, deserializationContext, typeDeserializer));
                propertyValueBuffer.bufferMapProperty(object4, object3);
            }
            object3 = object.nextToken();
        }
        try {
            return (Map)propertyBasedCreator.build(deserializationContext, propertyValueBuffer);
        }
        catch (Exception var1_3) {
            this.wrapAndThrow(var1_3, this._mapType.getRawClass());
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final boolean _isStdKeyDeser(JavaType class_, KeyDeserializer keyDeserializer) {
        if (keyDeserializer == null || (class_ = class_.getKeyType()) == null || ((class_ = class_.getRawClass()) == String.class || class_ == Object.class) && this.isDefaultKeyDeserializer(keyDeserializer)) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final void _readAndBind(JsonParser jsonParser, DeserializationContext deserializationContext, Map<Object, Object> map) throws IOException, JsonProcessingException {
        Object object;
        Object object2 = object = jsonParser.getCurrentToken();
        if (object == JsonToken.START_OBJECT) {
            object2 = jsonParser.nextToken();
        }
        KeyDeserializer keyDeserializer = this._keyDeserializer;
        JsonDeserializer<Object> jsonDeserializer = this._valueDeserializer;
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        object = null;
        boolean bl2 = jsonDeserializer.getObjectIdReader() != null;
        Object object3 = object2;
        if (bl2) {
            object = new MapReferringAccumulator(this._mapType.getContentType().getRawClass(), map);
            object3 = object2;
        }
        while (object3 == JsonToken.FIELD_NAME) {
            object2 = jsonParser.getCurrentName();
            object3 = keyDeserializer.deserializeKey((String)object2, deserializationContext);
            JsonToken jsonToken = jsonParser.nextToken();
            if (this._ignorableProperties != null && this._ignorableProperties.contains(object2)) {
                jsonParser.skipChildren();
            } else {
                try {
                    object2 = jsonToken == JsonToken.VALUE_NULL ? jsonDeserializer.getNullValue() : (typeDeserializer == null ? jsonDeserializer.deserialize(jsonParser, deserializationContext) : jsonDeserializer.deserializeWithType(jsonParser, deserializationContext, typeDeserializer));
                }
                catch (UnresolvedForwardReference var5_6) {
                    this.handleUnresolvedReference(jsonParser, (MapReferringAccumulator)object, object3, var5_6);
                }
                if (bl2) {
                    object.put(object3, object2);
                } else {
                    map.put(object3, object2);
                }
            }
            object3 = jsonParser.nextToken();
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final void _readAndBindStringMap(JsonParser jsonParser, DeserializationContext deserializationContext, Map<Object, Object> map) throws IOException, JsonProcessingException {
        Object object;
        Object object2 = object = jsonParser.getCurrentToken();
        if (object == JsonToken.START_OBJECT) {
            object2 = jsonParser.nextToken();
        }
        JsonDeserializer<Object> jsonDeserializer = this._valueDeserializer;
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        object = null;
        boolean bl2 = jsonDeserializer.getObjectIdReader() != null;
        Object object3 = object2;
        if (bl2) {
            object = new MapReferringAccumulator(this._mapType.getContentType().getRawClass(), map);
            object3 = object2;
        }
        while (object3 == JsonToken.FIELD_NAME) {
            object3 = jsonParser.getCurrentName();
            object2 = jsonParser.nextToken();
            if (this._ignorableProperties != null && this._ignorableProperties.contains(object3)) {
                jsonParser.skipChildren();
            } else {
                try {
                    object2 = object2 == JsonToken.VALUE_NULL ? jsonDeserializer.getNullValue() : (typeDeserializer == null ? jsonDeserializer.deserialize(jsonParser, deserializationContext) : jsonDeserializer.deserializeWithType(jsonParser, deserializationContext, typeDeserializer));
                }
                catch (UnresolvedForwardReference var5_6) {
                    this.handleUnresolvedReference(jsonParser, (MapReferringAccumulator)object, object3, var5_6);
                }
                if (bl2) {
                    object.put(object3, object2);
                } else {
                    map.put(object3, object2);
                }
            }
            object3 = jsonParser.nextToken();
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty arrstring) throws JsonMappingException {
        KeyDeserializer keyDeserializer;
        String[] arrstring2;
        void var8_15;
        TypeDeserializer typeDeserializer;
        Object object = this._keyDeserializer;
        if (object == null) {
            keyDeserializer = deserializationContext.findKeyDeserializer((JavaType)this._mapType.getKeyType(), (BeanProperty)arrstring2);
        } else {
            keyDeserializer = object;
            if (object instanceof ContextualKeyDeserializer) {
                keyDeserializer = ((ContextualKeyDeserializer)object).createContextual(deserializationContext, (BeanProperty)arrstring2);
            }
        }
        object = (object = this.findConvertingContentDeserializer(deserializationContext, (BeanProperty)arrstring2, this._valueDeserializer)) == null ? deserializationContext.findContextualValueDeserializer((JavaType)this._mapType.getContentType(), (BeanProperty)arrstring2) : deserializationContext.handleSecondaryContextualization(object, (BeanProperty)arrstring2);
        TypeDeserializer typeDeserializer2 = typeDeserializer = this._valueTypeDeserializer;
        if (typeDeserializer != null) {
            typeDeserializer2 = typeDeserializer.forProperty((BeanProperty)arrstring2);
        }
        HashSet<String> hashSet = this._ignorableProperties;
        AnnotationIntrospector annotationIntrospector = deserializationContext.getAnnotationIntrospector();
        HashSet<String> hashSet2 = hashSet;
        if (annotationIntrospector != null) {
            HashSet<String> hashSet3 = hashSet;
            if (arrstring2 != null) {
                arrstring2 = annotationIntrospector.findPropertiesToIgnore(arrstring2.getMember());
                HashSet<String> hashSet4 = hashSet;
                if (arrstring2 != null) {
                    if (hashSet == null) {
                        HashSet hashSet5 = new HashSet();
                    } else {
                        HashSet<String> hashSet6 = new HashSet<String>(hashSet);
                    }
                    int n2 = arrstring2.length;
                    int n3 = 0;
                    do {
                        void var1_4;
                        void var8_14 = var1_4;
                        if (n3 >= n2) break;
                        var1_4.add(arrstring2[n3]);
                        ++n3;
                    } while (true);
                }
            }
        }
        return this.withResolved(keyDeserializer, typeDeserializer2, object, (HashSet<String>)var8_15);
    }

    @Override
    public Map<Object, Object> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (this._propertyBasedCreator != null) {
            return this._deserializeUsingCreator(jsonParser, deserializationContext);
        }
        if (this._delegateDeserializer != null) {
            return (Map)this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
        }
        if (!this._hasDefaultCreator) {
            throw deserializationContext.instantiationException(this.getMapClass(), "No default constructor found");
        }
        Object object = jsonParser.getCurrentToken();
        if (object != JsonToken.START_OBJECT && object != JsonToken.FIELD_NAME && object != JsonToken.END_OBJECT) {
            if (object == JsonToken.VALUE_STRING) {
                return (Map)this._valueInstantiator.createFromString(deserializationContext, jsonParser.getText());
            }
            throw deserializationContext.mappingException(this.getMapClass());
        }
        object = (Map)this._valueInstantiator.createUsingDefault(deserializationContext);
        if (this._standardStringKey) {
            this._readAndBindStringMap(jsonParser, deserializationContext, (Map<Object, Object>)object);
            return object;
        }
        this._readAndBind(jsonParser, deserializationContext, (Map<Object, Object>)object);
        return object;
    }

    @Override
    public Map<Object, Object> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Map<Object, Object> map) throws IOException, JsonProcessingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken != JsonToken.START_OBJECT && jsonToken != JsonToken.FIELD_NAME) {
            throw deserializationContext.mappingException(this.getMapClass());
        }
        if (this._standardStringKey) {
            this._readAndBindStringMap(jsonParser, deserializationContext, map);
            return map;
        }
        this._readAndBind(jsonParser, deserializationContext, map);
        return map;
    }

    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        return typeDeserializer.deserializeTypedFromObject(jsonParser, deserializationContext);
    }

    @Override
    public JsonDeserializer<Object> getContentDeserializer() {
        return this._valueDeserializer;
    }

    @Override
    public JavaType getContentType() {
        return this._mapType.getContentType();
    }

    public final Class<?> getMapClass() {
        return this._mapType.getRawClass();
    }

    @Override
    public JavaType getValueType() {
        return this._mapType;
    }

    @Override
    public void resolve(DeserializationContext deserializationContext) throws JsonMappingException {
        SettableBeanProperty[] arrsettableBeanProperty;
        if (this._valueInstantiator.canCreateUsingDelegate()) {
            arrsettableBeanProperty = this._valueInstantiator.getDelegateType(deserializationContext.getConfig());
            if (arrsettableBeanProperty == null) {
                throw new IllegalArgumentException("Invalid delegate-creator definition for " + this._mapType + ": value instantiator (" + this._valueInstantiator.getClass().getName() + ") returned true for 'canCreateUsingDelegate()', but null for 'getDelegateType()'");
            }
            this._delegateDeserializer = this.findDeserializer(deserializationContext, (JavaType)arrsettableBeanProperty, null);
        }
        if (this._valueInstantiator.canCreateFromObjectWith()) {
            arrsettableBeanProperty = this._valueInstantiator.getFromObjectArguments(deserializationContext.getConfig());
            this._propertyBasedCreator = PropertyBasedCreator.construct(deserializationContext, this._valueInstantiator, arrsettableBeanProperty);
        }
        this._standardStringKey = this._isStdKeyDeser(this._mapType, this._keyDeserializer);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setIgnorableProperties(String[] hashSet) {
        hashSet = hashSet == null || hashSet.length == 0 ? null : ArrayBuilders.arrayToSet(hashSet);
        this._ignorableProperties = hashSet;
    }

    protected MapDeserializer withResolved(KeyDeserializer keyDeserializer, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer, HashSet<String> hashSet) {
        if (this._keyDeserializer == keyDeserializer && this._valueDeserializer == jsonDeserializer && this._valueTypeDeserializer == typeDeserializer && this._ignorableProperties == hashSet) {
            return this;
        }
        return new MapDeserializer(this, keyDeserializer, jsonDeserializer, typeDeserializer, hashSet);
    }

    protected void wrapAndThrow(Throwable throwable, Object object) throws IOException {
        while (throwable instanceof InvocationTargetException && throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        if (throwable instanceof Error) {
            throw (Error)throwable;
        }
        if (throwable instanceof IOException && !(throwable instanceof JsonMappingException)) {
            throw (IOException)throwable;
        }
        throw JsonMappingException.wrapWithPath(throwable, object, null);
    }

    private static final class MapReferring
    extends ReadableObjectId.Referring {
        private final MapReferringAccumulator _parent;
        public final Object key;
        public final Map<Object, Object> next = new LinkedHashMap<Object, Object>();

        private MapReferring(MapReferringAccumulator mapReferringAccumulator, UnresolvedForwardReference unresolvedForwardReference, Class<?> class_, Object object) {
            super(unresolvedForwardReference, class_);
            this._parent = mapReferringAccumulator;
            this.key = object;
        }

        @Override
        public void handleResolvedForwardReference(Object object, Object object2) throws IOException {
            this._parent.resolveForwardReference(object, object2);
        }
    }

    private static final class MapReferringAccumulator {
        private List<MapReferring> _accumulator = new ArrayList<MapReferring>();
        private Map<Object, Object> _result;
        private final Class<?> _valueType;

        public MapReferringAccumulator(Class<?> class_, Map<Object, Object> map) {
            this._valueType = class_;
            this._result = map;
        }

        public ReadableObjectId.Referring handleUnresolvedReference(UnresolvedForwardReference object, Object object2) {
            object = new MapReferring(this, (UnresolvedForwardReference)object, this._valueType, object2);
            this._accumulator.add((MapReferring)object);
            return object;
        }

        public void put(Object object, Object object2) {
            if (this._accumulator.isEmpty()) {
                this._result.put(object, object2);
                return;
            }
            this._accumulator.get((int)(this._accumulator.size() - 1)).next.put(object, object2);
        }

        public void resolveForwardReference(Object object, Object object2) throws IOException {
            Iterator<MapReferring> iterator = this._accumulator.iterator();
            Map<Object, Object> map = this._result;
            while (iterator.hasNext()) {
                MapReferring mapReferring = iterator.next();
                if (mapReferring.hasId(object)) {
                    iterator.remove();
                    map.put(mapReferring.key, object2);
                    map.putAll(mapReferring.next);
                    return;
                }
                map = mapReferring.next;
            }
            throw new IllegalArgumentException("Trying to resolve a forward reference with id [" + object + "] that wasn't previously seen as unresolved.");
        }
    }

}

