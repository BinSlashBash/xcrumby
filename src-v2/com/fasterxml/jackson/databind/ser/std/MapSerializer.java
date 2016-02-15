/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonMapFormatVisitor;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.fasterxml.jackson.databind.ser.std.MapProperty;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

@JacksonStdImpl
public class MapSerializer
extends ContainerSerializer<Map<?, ?>>
implements ContextualSerializer {
    protected static final JavaType UNSPECIFIED_TYPE = TypeFactory.unknownType();
    protected PropertySerializerMap _dynamicValueSerializers;
    protected final Object _filterId;
    protected final HashSet<String> _ignoredEntries;
    protected JsonSerializer<Object> _keySerializer;
    protected final JavaType _keyType;
    protected final BeanProperty _property;
    protected final boolean _sortKeys;
    protected JsonSerializer<Object> _valueSerializer;
    protected final JavaType _valueType;
    protected final boolean _valueTypeIsStatic;
    protected final TypeSerializer _valueTypeSerializer;

    protected MapSerializer(MapSerializer mapSerializer, BeanProperty beanProperty, JsonSerializer<?> jsonSerializer, JsonSerializer<?> jsonSerializer2, HashSet<String> hashSet) {
        super(Map.class, false);
        this._ignoredEntries = hashSet;
        this._keyType = mapSerializer._keyType;
        this._valueType = mapSerializer._valueType;
        this._valueTypeIsStatic = mapSerializer._valueTypeIsStatic;
        this._valueTypeSerializer = mapSerializer._valueTypeSerializer;
        this._keySerializer = jsonSerializer;
        this._valueSerializer = jsonSerializer2;
        this._dynamicValueSerializers = mapSerializer._dynamicValueSerializers;
        this._property = beanProperty;
        this._filterId = mapSerializer._filterId;
        this._sortKeys = mapSerializer._sortKeys;
    }

    protected MapSerializer(MapSerializer mapSerializer, TypeSerializer typeSerializer) {
        super(Map.class, false);
        this._ignoredEntries = mapSerializer._ignoredEntries;
        this._keyType = mapSerializer._keyType;
        this._valueType = mapSerializer._valueType;
        this._valueTypeIsStatic = mapSerializer._valueTypeIsStatic;
        this._valueTypeSerializer = typeSerializer;
        this._keySerializer = mapSerializer._keySerializer;
        this._valueSerializer = mapSerializer._valueSerializer;
        this._dynamicValueSerializers = mapSerializer._dynamicValueSerializers;
        this._property = mapSerializer._property;
        this._filterId = mapSerializer._filterId;
        this._sortKeys = mapSerializer._sortKeys;
    }

    protected MapSerializer(MapSerializer mapSerializer, Object object, boolean bl2) {
        super(Map.class, false);
        this._ignoredEntries = mapSerializer._ignoredEntries;
        this._keyType = mapSerializer._keyType;
        this._valueType = mapSerializer._valueType;
        this._valueTypeIsStatic = mapSerializer._valueTypeIsStatic;
        this._valueTypeSerializer = mapSerializer._valueTypeSerializer;
        this._keySerializer = mapSerializer._keySerializer;
        this._valueSerializer = mapSerializer._valueSerializer;
        this._dynamicValueSerializers = mapSerializer._dynamicValueSerializers;
        this._property = mapSerializer._property;
        this._filterId = object;
        this._sortKeys = bl2;
    }

    protected MapSerializer(HashSet<String> hashSet, JavaType javaType, JavaType javaType2, boolean bl2, TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer, JsonSerializer<?> jsonSerializer2) {
        super(Map.class, false);
        this._ignoredEntries = hashSet;
        this._keyType = javaType;
        this._valueType = javaType2;
        this._valueTypeIsStatic = bl2;
        this._valueTypeSerializer = typeSerializer;
        this._keySerializer = jsonSerializer;
        this._valueSerializer = jsonSerializer2;
        this._dynamicValueSerializers = PropertySerializerMap.emptyMap();
        this._property = null;
        this._filterId = null;
        this._sortKeys = false;
    }

    @Deprecated
    public static MapSerializer construct(String[] arrstring, JavaType javaType, boolean bl2, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer, JsonSerializer<Object> jsonSerializer2) {
        return MapSerializer.construct(arrstring, javaType, bl2, typeSerializer, jsonSerializer, jsonSerializer2, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static MapSerializer construct(String[] object, JavaType object2, boolean bl2, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer, JsonSerializer<Object> jsonSerializer2, Object object3) {
        HashSet<String> hashSet = MapSerializer.toSet((String[])object);
        if (object2 == null) {
            object = object2 = UNSPECIFIED_TYPE;
        } else {
            object = object2.getKeyType();
            object2 = object2.getContentType();
        }
        if (!bl2) {
            bl2 = object2 != null && object2.isFinal();
        } else if (object2.getRawClass() == Object.class) {
            bl2 = false;
        }
        object = object2 = new MapSerializer(hashSet, (JavaType)object, (JavaType)object2, bl2, typeSerializer, jsonSerializer, jsonSerializer2);
        if (object3 == null) return object;
        return object2.withFilterId(object3);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static HashSet<String> toSet(String[] arrstring) {
        if (arrstring == null) return null;
        if (arrstring.length == 0) {
            return null;
        }
        HashSet<String> hashSet = new HashSet<String>(arrstring.length);
        int n2 = arrstring.length;
        int n3 = 0;
        do {
            HashSet<String> hashSet2 = hashSet;
            if (n3 >= n2) return hashSet2;
            hashSet.add(arrstring[n3]);
            ++n3;
        } while (true);
    }

    protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap propertySerializerMap, JavaType object, SerializerProvider serializerProvider) throws JsonMappingException {
        object = propertySerializerMap.findAndAddSecondarySerializer((JavaType)object, serializerProvider, this._property);
        if (propertySerializerMap != object.map) {
            this._dynamicValueSerializers = object.map;
        }
        return object.serializer;
    }

    protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap propertySerializerMap, Class<?> object, SerializerProvider serializerProvider) throws JsonMappingException {
        object = propertySerializerMap.findAndAddSecondarySerializer(object, serializerProvider, this._property);
        if (propertySerializerMap != object.map) {
            this._dynamicValueSerializers = object.map;
        }
        return object.serializer;
    }

    protected Map<?, ?> _orderEntries(Map<?, ?> map) {
        if (map instanceof SortedMap) {
            return map;
        }
        return new TreeMap(map);
    }

    public MapSerializer _withValueTypeSerializer(TypeSerializer typeSerializer) {
        return new MapSerializer(this, typeSerializer);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType object) throws JsonMappingException {
        if (jsonFormatVisitorWrapper == null) {
            return;
        }
        if ((object = jsonFormatVisitorWrapper.expectMapFormat((JavaType)object)) != null) {
            JsonSerializer<Object> jsonSerializer;
            object.keyFormat(this._keySerializer, this._keyType);
            JsonSerializer<Object> jsonSerializer2 = jsonSerializer = this._valueSerializer;
            if (jsonSerializer == null) {
                jsonSerializer2 = this._findAndAddDynamic(this._dynamicValueSerializers, this._valueType, jsonFormatVisitorWrapper.getProvider());
            }
            object.valueFormat(jsonSerializer2, this._valueType);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider object, BeanProperty object2) throws JsonMappingException {
        void var9_13;
        void var9_15;
        void var9_10;
        String[] arrstring = null;
        HashSet<String> hashSet = null;
        Object var9_5 = null;
        AnnotationIntrospector annotationIntrospector = object.getAnnotationIntrospector();
        AnnotatedMember annotatedMember = object2 == null ? null : object2.getMember();
        HashSet hashSet2 = hashSet;
        String[] arrstring2 = arrstring;
        if (annotatedMember != null) {
            hashSet2 = hashSet;
            arrstring2 = arrstring;
            if (annotationIntrospector != null) {
                void var9_7;
                arrstring2 = annotationIntrospector.findKeySerializer(annotatedMember);
                if (arrstring2 != null) {
                    JsonSerializer<Object> jsonSerializer = object.serializerInstance(annotatedMember, arrstring2);
                }
                hashSet = annotationIntrospector.findContentSerializer(annotatedMember);
                hashSet2 = var9_7;
                arrstring2 = arrstring;
                if (hashSet != null) {
                    arrstring2 = object.serializerInstance(annotatedMember, hashSet);
                    hashSet2 = var9_7;
                }
            }
        }
        String[] arrstring3 = arrstring2;
        if (arrstring2 == null) {
            JsonSerializer<Object> jsonSerializer = this._valueSerializer;
        }
        if ((arrstring2 = this.findConvertingContentSerializer((SerializerProvider)object, (BeanProperty)object2, var9_10)) == null) {
            if (this._valueTypeIsStatic && this._valueType.getRawClass() != Object.class || this.hasContentTypeAnnotation((SerializerProvider)object, (BeanProperty)object2)) {
                arrstring2 = object.findValueSerializer(this._valueType, (BeanProperty)object2);
            }
        } else {
            arrstring2 = object.handleSecondaryContextualization(arrstring2, (BeanProperty)object2);
        }
        HashSet<String> hashSet3 = hashSet2;
        if (hashSet2 == null) {
            JsonSerializer<Object> jsonSerializer = this._keySerializer;
        }
        if (var9_13 == null) {
            JsonSerializer<Object> jsonSerializer = object.findKeySerializer(this._keyType, (BeanProperty)object2);
        } else {
            JsonSerializer jsonSerializer = object.handleSecondaryContextualization(var9_13, (BeanProperty)object2);
        }
        hashSet2 = this._ignoredEntries;
        boolean bl2 = false;
        object = hashSet2;
        boolean bl3 = bl2;
        if (annotationIntrospector != null) {
            object = hashSet2;
            bl3 = bl2;
            if (annotatedMember != null) {
                arrstring = annotationIntrospector.findPropertiesToIgnore(annotatedMember);
                object = hashSet2;
                if (arrstring != null) {
                    hashSet2 = hashSet2 == null ? new HashSet() : new HashSet<String>(hashSet2);
                    int n2 = arrstring.length;
                    int n3 = 0;
                    do {
                        object = hashSet2;
                        if (n3 >= n2) break;
                        hashSet2.add(arrstring[n3]);
                        ++n3;
                    } while (true);
                }
                bl3 = (hashSet2 = annotationIntrospector.findSerializationSortAlphabetically(annotatedMember)) != null && hashSet2.booleanValue();
            }
        }
        object = arrstring2 = this.withResolved((BeanProperty)object2, var9_15, arrstring2, (HashSet<String>)object, bl3);
        if (object2 == null) return object;
        object2 = annotationIntrospector.findFilterId(object2.getMember());
        object = arrstring2;
        if (object2 == null) return object;
        return arrstring2.withFilterId(object2);
    }

    @Override
    public JsonSerializer<?> getContentSerializer() {
        return this._valueSerializer;
    }

    @Override
    public JavaType getContentType() {
        return this._valueType;
    }

    public JsonSerializer<?> getKeySerializer() {
        return this._keySerializer;
    }

    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
        return this.createSchemaNode("object", true);
    }

    @Override
    public boolean hasSingleElement(Map<?, ?> map) {
        if (map.size() == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty(Map<?, ?> map) {
        if (map == null || map.isEmpty()) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serialize(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        jsonGenerator.writeStartObject();
        if (!map.isEmpty()) {
            Map map2;
            block6 : {
                if (this._filterId != null) {
                    this.serializeFilteredFields(map, jsonGenerator, serializerProvider, this.findPropertyFilter(serializerProvider, this._filterId, map));
                    jsonGenerator.writeEndObject();
                    return;
                }
                if (!this._sortKeys) {
                    map2 = map;
                    if (!serializerProvider.isEnabled(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)) break block6;
                }
                map2 = this._orderEntries(map);
            }
            if (this._valueSerializer != null) {
                this.serializeFieldsUsing(map2, jsonGenerator, serializerProvider, this._valueSerializer);
            } else {
                this.serializeFields(map2, jsonGenerator, serializerProvider);
            }
        }
        jsonGenerator.writeEndObject();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void serializeFields(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        if (this._valueTypeSerializer != null) {
            this.serializeTypedFields(map, jsonGenerator, serializerProvider);
            return;
        }
        JsonSerializer<Object> jsonSerializer = this._keySerializer;
        HashSet<String> hashSet = this._ignoredEntries;
        boolean bl2 = !serializerProvider.isEnabled(SerializationFeature.WRITE_NULL_MAP_VALUES);
        JsonSerializer<Object> jsonSerializer2 = this._dynamicValueSerializers;
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            JsonSerializer<Object> jsonSerializer3;
            Object object = iterator.next();
            Object obj = object.getValue();
            Object obj2 = object.getKey();
            if (obj2 == null) {
                serializerProvider.findNullKeySerializer(this._keyType, this._property).serialize(null, jsonGenerator, serializerProvider);
            } else {
                if (bl2 && obj == null || hashSet != null && hashSet.contains(obj2)) continue;
                jsonSerializer.serialize(obj2, jsonGenerator, serializerProvider);
            }
            if (obj == null) {
                serializerProvider.defaultSerializeNull(jsonGenerator);
                continue;
            }
            Class class_ = obj.getClass();
            JsonSerializer<Object> jsonSerializer4 = jsonSerializer3 = jsonSerializer2.serializerFor(class_);
            object = jsonSerializer2;
            if (jsonSerializer3 == null) {
                jsonSerializer2 = this._valueType.hasGenericTypes() ? this._findAndAddDynamic((PropertySerializerMap)((Object)jsonSerializer2), serializerProvider.constructSpecializedType(this._valueType, class_), serializerProvider) : this._findAndAddDynamic((PropertySerializerMap)((Object)jsonSerializer2), class_, serializerProvider);
                object = this._dynamicValueSerializers;
                jsonSerializer4 = jsonSerializer2;
            }
            try {
                jsonSerializer4.serialize(obj, jsonGenerator, serializerProvider);
                jsonSerializer2 = object;
            }
            catch (Exception var5_8) {
                this.wrapAndThrow(serializerProvider, (Throwable)var5_8, map, "" + obj2);
                jsonSerializer2 = object;
                continue;
            }
            break;
        }
        return;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected void serializeFieldsUsing(Map<?, ?> var1_1, JsonGenerator var2_2, SerializerProvider var3_3, JsonSerializer<Object> var4_4) throws IOException, JsonGenerationException {
        var6_5 = this._keySerializer;
        var7_6 = this._ignoredEntries;
        var8_7 = this._valueTypeSerializer;
        var5_8 = var3_3.isEnabled(SerializationFeature.WRITE_NULL_MAP_VALUES) == false;
        var9_9 = var1_1.entrySet().iterator();
        while (var9_9.hasNext() != false) {
            var11_12 /* !! */  = var9_9.next();
            var10_10 = var11_12 /* !! */ .getValue();
            if ((var11_12 /* !! */  = var11_12 /* !! */ .getKey()) == null) {
                var3_3.findNullKeySerializer(this._keyType, this._property).serialize(null, var2_2, var3_3);
            } else {
                if (var5_8 && var10_10 == null || var7_6 != null && var7_6.contains(var11_12 /* !! */ )) continue;
                var6_5.serialize(var11_12 /* !! */ , var2_2, var3_3);
            }
            if (var10_10 == null) {
                var3_3.defaultSerializeNull(var2_2);
                continue;
            }
            if (var8_7 != null) ** GOTO lbl21
            try {
                var4_4.serialize(var10_10, var2_2, var3_3);
lbl21: // 1 sources:
                var4_4.serializeWithType(var10_10, var2_2, var3_3, var8_7);
                continue;
            }
            catch (Exception var10_11) {
                this.wrapAndThrow(var3_3, (Throwable)var10_11, var1_1, "" + var11_12 /* !! */ );
                continue;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void serializeFilteredFields(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, PropertyFilter propertyFilter) throws IOException, JsonGenerationException {
        HashSet<String> hashSet = this._ignoredEntries;
        boolean bl2 = !serializerProvider.isEnabled(SerializationFeature.WRITE_NULL_MAP_VALUES);
        PropertySerializerMap propertySerializerMap = this._dynamicValueSerializers;
        MapProperty mapProperty = new MapProperty(this._valueTypeSerializer);
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            PropertySerializerMap propertySerializerMap2;
            JsonSerializer<Object> jsonSerializer;
            JsonSerializer<Object> jsonSerializer2 = iterator.next();
            Object obj = jsonSerializer2.getKey();
            Object obj2 = jsonSerializer2.getValue();
            if (obj == null) {
                jsonSerializer = serializerProvider.findNullKeySerializer(this._keyType, this._property);
            } else {
                if (bl2 && obj2 == null || hashSet != null && hashSet.contains(obj)) continue;
                jsonSerializer = this._keySerializer;
            }
            if (obj2 == null) {
                jsonSerializer2 = serializerProvider.getDefaultNullValueSerializer();
                propertySerializerMap2 = propertySerializerMap;
            } else {
                Class class_ = obj2.getClass();
                JsonSerializer<Object> jsonSerializer3 = propertySerializerMap.serializerFor(class_);
                propertySerializerMap2 = propertySerializerMap;
                jsonSerializer2 = jsonSerializer3;
                if (jsonSerializer3 == null) {
                    jsonSerializer2 = this._valueType.hasGenericTypes() ? this._findAndAddDynamic(propertySerializerMap, serializerProvider.constructSpecializedType(this._valueType, class_), serializerProvider) : this._findAndAddDynamic(propertySerializerMap, class_, serializerProvider);
                    propertySerializerMap2 = this._dynamicValueSerializers;
                }
            }
            mapProperty.reset(obj, obj2, jsonSerializer, jsonSerializer2);
            try {
                propertyFilter.serializeAsField(map, jsonGenerator, serializerProvider, mapProperty);
                propertySerializerMap = propertySerializerMap2;
            }
            catch (Exception var6_11) {
                this.wrapAndThrow(serializerProvider, (Throwable)var6_11, map, "" + obj);
                propertySerializerMap = propertySerializerMap2;
                continue;
            }
            break;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void serializeTypedFields(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        JsonSerializer<Object> jsonSerializer = this._keySerializer;
        JsonSerializer<Object> jsonSerializer2 = null;
        JsonSerializer<Object> jsonSerializer3 = null;
        HashSet<String> hashSet = this._ignoredEntries;
        boolean bl2 = !serializerProvider.isEnabled(SerializationFeature.WRITE_NULL_MAP_VALUES);
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Object object = iterator.next();
            Object obj = object.getValue();
            Object obj2 = object.getKey();
            if (obj2 == null) {
                serializerProvider.findNullKeySerializer(this._keyType, this._property).serialize(null, jsonGenerator, serializerProvider);
            } else {
                if (bl2 && obj == null || hashSet != null && hashSet.contains(obj2)) continue;
                jsonSerializer.serialize(obj2, jsonGenerator, serializerProvider);
            }
            if (obj == null) {
                serializerProvider.defaultSerializeNull(jsonGenerator);
                continue;
            }
            Class class_ = obj.getClass();
            if (class_ == jsonSerializer3) {
                class_ = object = jsonSerializer2;
            } else {
                jsonSerializer2 = this._valueType.hasGenericTypes() ? serializerProvider.findValueSerializer(serializerProvider.constructSpecializedType(this._valueType, class_), this._property) : serializerProvider.findValueSerializer(class_, this._property);
                object = jsonSerializer2;
                jsonSerializer3 = class_;
                class_ = jsonSerializer2;
                jsonSerializer2 = object;
            }
            try {
                class_.serializeWithType(obj, jsonGenerator, serializerProvider, this._valueTypeSerializer);
            }
            catch (Exception var7_11) {
                this.wrapAndThrow(serializerProvider, (Throwable)var7_11, map, "" + obj2);
                continue;
            }
            break;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serializeWithType(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        typeSerializer.writeTypePrefixForObject(map, jsonGenerator);
        Map map2 = map;
        if (!map.isEmpty()) {
            block5 : {
                if (!this._sortKeys) {
                    map2 = map;
                    if (!serializerProvider.isEnabled(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)) break block5;
                }
                map2 = this._orderEntries(map);
            }
            if (this._valueSerializer != null) {
                this.serializeFieldsUsing(map2, jsonGenerator, serializerProvider, this._valueSerializer);
            } else {
                this.serializeFields(map2, jsonGenerator, serializerProvider);
            }
        }
        typeSerializer.writeTypeSuffixForObject(map2, jsonGenerator);
    }

    public MapSerializer withFilterId(Object object) {
        if (this._filterId == object) {
            return this;
        }
        return new MapSerializer(this, object, this._sortKeys);
    }

    @Deprecated
    public MapSerializer withResolved(BeanProperty beanProperty, JsonSerializer<?> jsonSerializer, JsonSerializer<?> jsonSerializer2, HashSet<String> hashSet) {
        return this.withResolved(beanProperty, jsonSerializer, jsonSerializer2, hashSet, this._sortKeys);
    }

    public MapSerializer withResolved(BeanProperty object, JsonSerializer<?> mapSerializer, JsonSerializer<?> jsonSerializer, HashSet<String> hashSet, boolean bl2) {
        object = mapSerializer = new MapSerializer(this, (BeanProperty)object, mapSerializer, jsonSerializer, hashSet);
        if (bl2 != mapSerializer._sortKeys) {
            object = new MapSerializer(mapSerializer, this._filterId, bl2);
        }
        return object;
    }
}

