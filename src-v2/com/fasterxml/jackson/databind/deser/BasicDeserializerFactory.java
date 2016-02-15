/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.databind.AbstractTypeResolver;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.deser.AbstractDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.CreatorProperty;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.deser.KeyDeserializers;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.ValueInstantiators;
import com.fasterxml.jackson.databind.deser.impl.CreatorCollector;
import com.fasterxml.jackson.databind.deser.std.ArrayBlockingQueueDeserializer;
import com.fasterxml.jackson.databind.deser.std.CollectionDeserializer;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.deser.std.EnumDeserializer;
import com.fasterxml.jackson.databind.deser.std.EnumMapDeserializer;
import com.fasterxml.jackson.databind.deser.std.EnumSetDeserializer;
import com.fasterxml.jackson.databind.deser.std.JdkDeserializers;
import com.fasterxml.jackson.databind.deser.std.JsonLocationInstantiator;
import com.fasterxml.jackson.databind.deser.std.JsonNodeDeserializer;
import com.fasterxml.jackson.databind.deser.std.MapDeserializer;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.fasterxml.jackson.databind.deser.std.ObjectArrayDeserializer;
import com.fasterxml.jackson.databind.deser.std.PrimitiveArrayDeserializers;
import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializers;
import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringCollectionDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.deser.std.TokenBufferDeserializer;
import com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.EnumResolver;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.PrintStream;
import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public abstract class BasicDeserializerFactory
extends DeserializerFactory
implements Serializable {
    private static final Class<?> CLASS_CHAR_BUFFER;
    private static final Class<?> CLASS_ITERABLE;
    private static final Class<?> CLASS_OBJECT;
    private static final Class<?> CLASS_STRING;
    protected static final PropertyName UNWRAPPED_CREATOR_PARAM_NAME;
    static final HashMap<String, Class<? extends Collection>> _collectionFallbacks;
    static final HashMap<String, Class<? extends Map>> _mapFallbacks;
    protected final DeserializerFactoryConfig _factoryConfig;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        CLASS_OBJECT = Object.class;
        CLASS_STRING = String.class;
        CLASS_CHAR_BUFFER = CharSequence.class;
        CLASS_ITERABLE = Iterable.class;
        UNWRAPPED_CREATOR_PARAM_NAME = new PropertyName("@JsonUnwrapped");
        _mapFallbacks = new HashMap();
        _mapFallbacks.put(Map.class.getName(), LinkedHashMap.class);
        _mapFallbacks.put(ConcurrentMap.class.getName(), ConcurrentHashMap.class);
        _mapFallbacks.put(SortedMap.class.getName(), TreeMap.class);
        _mapFallbacks.put("java.util.NavigableMap", TreeMap.class);
        try {
            _mapFallbacks.put(ConcurrentNavigableMap.class.getName(), ConcurrentSkipListMap.class);
        }
        catch (Throwable var0) {
            System.err.println("Problems with (optional) types: " + var0);
        }
        _collectionFallbacks = new HashMap();
        _collectionFallbacks.put(Collection.class.getName(), ArrayList.class);
        _collectionFallbacks.put(List.class.getName(), ArrayList.class);
        _collectionFallbacks.put(Set.class.getName(), HashSet.class);
        _collectionFallbacks.put(SortedSet.class.getName(), TreeSet.class);
        _collectionFallbacks.put(Queue.class.getName(), LinkedList.class);
        _collectionFallbacks.put("java.util.Deque", LinkedList.class);
        _collectionFallbacks.put("java.util.NavigableSet", TreeSet.class);
    }

    protected BasicDeserializerFactory(DeserializerFactoryConfig deserializerFactoryConfig) {
        this._factoryConfig = deserializerFactoryConfig;
    }

    private KeyDeserializer _createEnumKeyDeserializer(DeserializationContext class_, JavaType serializable) throws JsonMappingException {
        DeserializationConfig deserializationConfig = class_.getConfig();
        Object t2 = deserializationConfig.introspect((JavaType)serializable);
        if ((class_ = this.findDeserializerFromAnnotation((DeserializationContext)((Object)class_), t2.getClassInfo())) != null) {
            return StdKeyDeserializers.constructDelegatingKeyDeserializer(deserializationConfig, (JavaType)serializable, class_);
        }
        class_ = serializable.getRawClass();
        JsonDeserializer jsonDeserializer2 = this._findCustomEnumDeserializer(class_, deserializationConfig, (BeanDescription)t2);
        if (jsonDeserializer2 != null) {
            return StdKeyDeserializers.constructDelegatingKeyDeserializer(deserializationConfig, (JavaType)serializable, jsonDeserializer2);
        }
        serializable = this.constructEnumResolver(class_, deserializationConfig, t2.findJsonValueMethod());
        for (AnnotatedMethod annotatedMethod : t2.getFactoryMethods()) {
            if (!deserializationConfig.getAnnotationIntrospector().hasCreatorAnnotation(annotatedMethod)) continue;
            if (annotatedMethod.getParameterCount() == 1 && annotatedMethod.getRawReturnType().isAssignableFrom(class_)) {
                if (annotatedMethod.getGenericParameterType(0) != String.class) {
                    throw new IllegalArgumentException("Parameter #0 type for factory method (" + annotatedMethod + ") not suitable, must be java.lang.String");
                }
                if (deserializationConfig.canOverrideAccessModifiers()) {
                    ClassUtil.checkAndFixAccess(annotatedMethod.getMember());
                }
                return StdKeyDeserializers.constructEnumKeyDeserializer(serializable, annotatedMethod);
            }
            throw new IllegalArgumentException("Unsuitable method (" + annotatedMethod + ") decorated with @JsonCreator (for Enum type " + class_.getName() + ")");
        }
        return StdKeyDeserializers.constructEnumKeyDeserializer(serializable);
    }

    private ValueInstantiator _findStdValueInstantiator(DeserializationConfig deserializationConfig, BeanDescription beanDescription) throws JsonMappingException {
        if (beanDescription.getBeanClass() == JsonLocation.class) {
            return new JsonLocationInstantiator();
        }
        return null;
    }

    private JavaType _mapAbstractType2(DeserializationConfig deserializationConfig, JavaType javaType) throws JsonMappingException {
        Class class_ = javaType.getRawClass();
        if (this._factoryConfig.hasAbstractTypeResolvers()) {
            Iterator<AbstractTypeResolver> iterator = this._factoryConfig.abstractTypeResolvers().iterator();
            while (iterator.hasNext()) {
                JavaType javaType2 = iterator.next().findTypeMapping(deserializationConfig, javaType);
                if (javaType2 == null || javaType2.getRawClass() == class_) continue;
                return javaType2;
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _addDeserializerConstructors(DeserializationContext deserializationContext, BeanDescription beanDescription, VisibilityChecker<?> visibilityChecker, AnnotationIntrospector annotationIntrospector, CreatorCollector creatorCollector) throws JsonMappingException {
        AnnotatedParameter annotatedParameter;
        Object object;
        Object object2 = beanDescription.findDefaultConstructor();
        if (object2 != null && (!creatorCollector.hasDefaultCreator() || annotationIntrospector.hasCreatorAnnotation((Annotated)object2))) {
            creatorCollector.setDefaultCreator((AnnotatedWithParams)object2);
        }
        PropertyName[] arrpropertyName = null;
        AnnotatedConstructor annotatedConstructor = null;
        for (BeanPropertyDefinition beanPropertyDefinition : beanDescription.findProperties()) {
            if (beanPropertyDefinition.getConstructorParameter() == null || !((object = (annotatedParameter = beanPropertyDefinition.getConstructorParameter()).getOwner()) instanceof AnnotatedConstructor)) continue;
            object2 = annotatedConstructor;
            if (annotatedConstructor == null) {
                object2 = (AnnotatedConstructor)object;
                arrpropertyName = new PropertyName[object2.getParameterCount()];
            }
            arrpropertyName[annotatedParameter.getIndex()] = beanPropertyDefinition.getFullName();
            annotatedConstructor = object2;
        }
        object = beanDescription.getConstructors().iterator();
        while (object.hasNext()) {
            AnnotatedConstructor annotatedConstructor2 = object.next();
            int n2 = annotatedConstructor2.getParameterCount();
            boolean bl2 = annotationIntrospector.hasCreatorAnnotation(annotatedConstructor2) || annotatedConstructor2 == annotatedConstructor;
            boolean bl3 = visibilityChecker.isCreatorVisible(annotatedConstructor2);
            if (n2 == 1) {
                object2 = annotatedConstructor2 == annotatedConstructor ? arrpropertyName[0] : null;
                this._handleSingleArgumentConstructor(deserializationContext, beanDescription, visibilityChecker, annotationIntrospector, creatorCollector, annotatedConstructor2, bl2, bl3, (PropertyName)object2);
                continue;
            }
            if (!bl2 && !bl3) continue;
            Iterator<BeanPropertyDefinition> iterator = null;
            int n3 = 0;
            int n4 = 0;
            CreatorProperty[] arrcreatorProperty = new CreatorProperty[n2];
            for (int i2 = 0; i2 < n2; ++i2) {
                int n5;
                void var18_14;
                int n6;
                annotatedParameter = annotatedConstructor2.getParameter(i2);
                object2 = null;
                if (annotatedConstructor2 == annotatedConstructor) {
                    object2 = arrpropertyName[i2];
                }
                Serializable serializable = object2;
                if (object2 == null) {
                    PropertyName propertyName = this._findParamName(annotatedParameter, annotationIntrospector);
                }
                object2 = annotationIntrospector.findInjectableValueId(annotatedParameter);
                if (var18_14 != null && var18_14.hasSimpleName()) {
                    n6 = n3 + 1;
                    arrcreatorProperty[i2] = this.constructCreatorProperty(deserializationContext, beanDescription, (PropertyName)var18_14, i2, annotatedParameter, object2);
                    object2 = iterator;
                    n5 = n4;
                } else if (object2 != null) {
                    n5 = n4 + 1;
                    arrcreatorProperty[i2] = this.constructCreatorProperty(deserializationContext, beanDescription, (PropertyName)var18_14, i2, annotatedParameter, object2);
                    n6 = n3;
                    object2 = iterator;
                } else if (annotationIntrospector.findUnwrappingNameTransformer(annotatedParameter) != null) {
                    arrcreatorProperty[i2] = this.constructCreatorProperty(deserializationContext, beanDescription, UNWRAPPED_CREATOR_PARAM_NAME, i2, annotatedParameter, null);
                    n6 = n3 + 1;
                    n5 = n4;
                    object2 = iterator;
                } else {
                    n5 = n4;
                    n6 = n3;
                    object2 = iterator;
                    if (iterator == null) {
                        object2 = annotatedParameter;
                        n5 = n4;
                        n6 = n3;
                    }
                }
                n4 = n5;
                n3 = n6;
                iterator = object2;
            }
            if (!bl2 && n3 <= 0 && n4 <= 0) continue;
            if (n3 + n4 == n2) {
                creatorCollector.addPropertyCreator(annotatedConstructor2, arrcreatorProperty);
                continue;
            }
            if (n3 == 0 && n4 + 1 == n2) {
                creatorCollector.addDelegatingCreator(annotatedConstructor2, arrcreatorProperty);
                continue;
            }
            creatorCollector.addIncompeteParameter((AnnotatedParameter)((Object)iterator));
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _addDeserializerFactoryMethods(DeserializationContext deserializationContext, BeanDescription beanDescription, VisibilityChecker<?> visibilityChecker, AnnotationIntrospector annotationIntrospector, CreatorCollector creatorCollector) throws JsonMappingException {
        Object object;
        AnnotatedMethod annotatedMethod;
        DeserializationConfig deserializationConfig = deserializationContext.getConfig();
        Iterator<AnnotatedMethod> iterator = beanDescription.getFactoryMethods().iterator();
        do {
            Object object2;
            if (!iterator.hasNext()) {
                return;
            }
            annotatedMethod = iterator.next();
            boolean bl2 = annotationIntrospector.hasCreatorAnnotation(annotatedMethod);
            int n2 = annotatedMethod.getParameterCount();
            if (n2 == 0) {
                if (!bl2) continue;
                creatorCollector.setDefaultCreator(annotatedMethod);
                continue;
            }
            if (n2 == 1) {
                object2 = annotatedMethod.getParameter(0);
                object = this._findParamName((AnnotatedParameter)object2, annotationIntrospector);
                object = object == null ? null : object.getSimpleName();
                if (annotationIntrospector.findInjectableValueId((AnnotatedMember)object2) == null && (object == null || object.length() == 0)) {
                    this._handleSingleArgumentFactory(deserializationConfig, beanDescription, visibilityChecker, annotationIntrospector, creatorCollector, annotatedMethod, bl2);
                    continue;
                }
            } else if (!annotationIntrospector.hasCreatorAnnotation(annotatedMethod)) continue;
            object = null;
            CreatorProperty[] arrcreatorProperty = new CreatorProperty[n2];
            int n3 = 0;
            int n4 = 0;
            for (int i2 = 0; i2 < n2; ++i2) {
                int n5;
                int n6;
                AnnotatedParameter annotatedParameter = annotatedMethod.getParameter(i2);
                object2 = this._findParamName(annotatedParameter, annotationIntrospector);
                Object object3 = annotationIntrospector.findInjectableValueId(annotatedParameter);
                if (object2 != null && object2.hasSimpleName()) {
                    n5 = n3 + 1;
                    arrcreatorProperty[i2] = this.constructCreatorProperty(deserializationContext, beanDescription, (PropertyName)object2, i2, annotatedParameter, object3);
                    object2 = object;
                    n6 = n4;
                } else if (object3 != null) {
                    n6 = n4 + 1;
                    arrcreatorProperty[i2] = this.constructCreatorProperty(deserializationContext, beanDescription, (PropertyName)object2, i2, annotatedParameter, object3);
                    n5 = n3;
                    object2 = object;
                } else if (annotationIntrospector.findUnwrappingNameTransformer(annotatedParameter) != null) {
                    arrcreatorProperty[i2] = this.constructCreatorProperty(deserializationContext, beanDescription, UNWRAPPED_CREATOR_PARAM_NAME, i2, annotatedParameter, null);
                    n5 = n3 + 1;
                    n6 = n4;
                    object2 = object;
                } else {
                    n6 = n4;
                    n5 = n3;
                    object2 = object;
                    if (object == null) {
                        object2 = annotatedParameter;
                        n6 = n4;
                        n5 = n3;
                    }
                }
                n4 = n6;
                n3 = n5;
                object = object2;
            }
            if (!bl2 && n3 <= 0 && n4 <= 0) continue;
            if (n3 + n4 == n2) {
                creatorCollector.addPropertyCreator(annotatedMethod, arrcreatorProperty);
                continue;
            }
            if (n3 != 0 || n4 + 1 != n2) break;
            creatorCollector.addDelegatingCreator(annotatedMethod, arrcreatorProperty);
        } while (true);
        throw new IllegalArgumentException("Argument #" + object.getIndex() + " of factory method " + annotatedMethod + " has no property name annotation; must have name when multiple-parameter constructor annotated as Creator");
    }

    protected ValueInstantiator _constructDefaultValueInstantiator(DeserializationContext deserializationContext, BeanDescription beanDescription) throws JsonMappingException {
        CreatorCollector creatorCollector = new CreatorCollector(beanDescription, deserializationContext.canOverrideAccessModifiers());
        AnnotationIntrospector annotationIntrospector = deserializationContext.getAnnotationIntrospector();
        DeserializationConfig deserializationConfig = deserializationContext.getConfig();
        VisibilityChecker visibilityChecker = deserializationConfig.getDefaultVisibilityChecker();
        visibilityChecker = annotationIntrospector.findAutoDetectVisibility(beanDescription.getClassInfo(), visibilityChecker);
        this._addDeserializerFactoryMethods(deserializationContext, beanDescription, visibilityChecker, annotationIntrospector, creatorCollector);
        if (beanDescription.getType().isConcrete()) {
            this._addDeserializerConstructors(deserializationContext, beanDescription, visibilityChecker, annotationIntrospector, creatorCollector);
        }
        return creatorCollector.constructValueInstantiator(deserializationConfig);
    }

    protected JsonDeserializer<?> _findCustomArrayDeserializer(ArrayType arrayType, DeserializationConfig deserializationConfig, BeanDescription beanDescription, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
        Iterator<Deserializers> iterator = this._factoryConfig.deserializers().iterator();
        while (iterator.hasNext()) {
            JsonDeserializer jsonDeserializer2 = iterator.next().findArrayDeserializer(arrayType, deserializationConfig, beanDescription, typeDeserializer, jsonDeserializer);
            if (jsonDeserializer2 == null) continue;
            return jsonDeserializer2;
        }
        return null;
    }

    protected JsonDeserializer<?> _findCustomCollectionDeserializer(CollectionType collectionType, DeserializationConfig deserializationConfig, BeanDescription beanDescription, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
        Iterator<Deserializers> iterator = this._factoryConfig.deserializers().iterator();
        while (iterator.hasNext()) {
            JsonDeserializer jsonDeserializer2 = iterator.next().findCollectionDeserializer(collectionType, deserializationConfig, beanDescription, typeDeserializer, jsonDeserializer);
            if (jsonDeserializer2 == null) continue;
            return jsonDeserializer2;
        }
        return null;
    }

    protected JsonDeserializer<?> _findCustomCollectionLikeDeserializer(CollectionLikeType collectionLikeType, DeserializationConfig deserializationConfig, BeanDescription beanDescription, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
        Iterator<Deserializers> iterator = this._factoryConfig.deserializers().iterator();
        while (iterator.hasNext()) {
            JsonDeserializer jsonDeserializer2 = iterator.next().findCollectionLikeDeserializer(collectionLikeType, deserializationConfig, beanDescription, typeDeserializer, jsonDeserializer);
            if (jsonDeserializer2 == null) continue;
            return jsonDeserializer2;
        }
        return null;
    }

    protected JsonDeserializer<?> _findCustomEnumDeserializer(Class<?> class_, DeserializationConfig deserializationConfig, BeanDescription beanDescription) throws JsonMappingException {
        Iterator<Deserializers> iterator = this._factoryConfig.deserializers().iterator();
        while (iterator.hasNext()) {
            JsonDeserializer jsonDeserializer = iterator.next().findEnumDeserializer(class_, deserializationConfig, beanDescription);
            if (jsonDeserializer == null) continue;
            return jsonDeserializer;
        }
        return null;
    }

    protected JsonDeserializer<?> _findCustomMapDeserializer(MapType mapType, DeserializationConfig deserializationConfig, BeanDescription beanDescription, KeyDeserializer keyDeserializer, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
        Iterator<Deserializers> iterator = this._factoryConfig.deserializers().iterator();
        while (iterator.hasNext()) {
            JsonDeserializer jsonDeserializer2 = iterator.next().findMapDeserializer(mapType, deserializationConfig, beanDescription, keyDeserializer, typeDeserializer, jsonDeserializer);
            if (jsonDeserializer2 == null) continue;
            return jsonDeserializer2;
        }
        return null;
    }

    protected JsonDeserializer<?> _findCustomMapLikeDeserializer(MapLikeType mapLikeType, DeserializationConfig deserializationConfig, BeanDescription beanDescription, KeyDeserializer keyDeserializer, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
        Iterator<Deserializers> iterator = this._factoryConfig.deserializers().iterator();
        while (iterator.hasNext()) {
            JsonDeserializer jsonDeserializer2 = iterator.next().findMapLikeDeserializer(mapLikeType, deserializationConfig, beanDescription, keyDeserializer, typeDeserializer, jsonDeserializer);
            if (jsonDeserializer2 == null) continue;
            return jsonDeserializer2;
        }
        return null;
    }

    protected JsonDeserializer<?> _findCustomTreeNodeDeserializer(Class<? extends JsonNode> class_, DeserializationConfig deserializationConfig, BeanDescription beanDescription) throws JsonMappingException {
        Iterator<Deserializers> iterator = this._factoryConfig.deserializers().iterator();
        while (iterator.hasNext()) {
            JsonDeserializer jsonDeserializer = iterator.next().findTreeNodeDeserializer(class_, deserializationConfig, beanDescription);
            if (jsonDeserializer == null) continue;
            return jsonDeserializer;
        }
        return null;
    }

    protected AnnotatedMethod _findJsonValueFor(DeserializationConfig deserializationConfig, JavaType javaType) {
        if (javaType == null) {
            return null;
        }
        return deserializationConfig.introspect(javaType).findJsonValueMethod();
    }

    protected PropertyName _findParamName(AnnotatedParameter object, AnnotationIntrospector annotationIntrospector) {
        if (object != null && annotationIntrospector != null) {
            PropertyName propertyName = annotationIntrospector.findNameForDeserialization((Annotated)object);
            if (propertyName != null) {
                return propertyName;
            }
            if ((object = annotationIntrospector.findImplicitPropertyName((AnnotatedMember)object)) != null && !object.isEmpty()) {
                return new PropertyName((String)object);
            }
        }
        return null;
    }

    protected boolean _handleSingleArgumentConstructor(DeserializationContext serializable, BeanDescription beanDescription, VisibilityChecker<?> object, AnnotationIntrospector object2, CreatorCollector creatorCollector, AnnotatedConstructor annotatedConstructor, boolean bl2, boolean bl3, PropertyName propertyName) throws JsonMappingException {
        AnnotatedParameter annotatedParameter = annotatedConstructor.getParameter(0);
        object = propertyName;
        if (propertyName == null) {
            object = this._findParamName(annotatedParameter, (AnnotationIntrospector)object2);
        }
        if ((object2 = object2.findInjectableValueId(annotatedParameter)) != null || object != null && object.hasSimpleName()) {
            creatorCollector.addPropertyCreator(annotatedConstructor, new CreatorProperty[]{this.constructCreatorProperty((DeserializationContext)serializable, beanDescription, (PropertyName)object, 0, annotatedParameter, object2)});
            return true;
        }
        serializable = annotatedConstructor.getRawParameterType(0);
        if (serializable == String.class) {
            if (bl2 || bl3) {
                creatorCollector.addStringCreator(annotatedConstructor);
            }
            return true;
        }
        if (serializable == Integer.TYPE || serializable == Integer.class) {
            if (bl2 || bl3) {
                creatorCollector.addIntCreator(annotatedConstructor);
            }
            return true;
        }
        if (serializable == Long.TYPE || serializable == Long.class) {
            if (bl2 || bl3) {
                creatorCollector.addLongCreator(annotatedConstructor);
            }
            return true;
        }
        if (serializable == Double.TYPE || serializable == Double.class) {
            if (bl2 || bl3) {
                creatorCollector.addDoubleCreator(annotatedConstructor);
            }
            return true;
        }
        if (serializable == Boolean.TYPE || serializable == Boolean.class) {
            if (bl2 || bl3) {
                creatorCollector.addBooleanCreator(annotatedConstructor);
            }
            return true;
        }
        if (bl2) {
            creatorCollector.addDelegatingCreator(annotatedConstructor, null);
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean _handleSingleArgumentFactory(DeserializationConfig serializable, BeanDescription beanDescription, VisibilityChecker<?> visibilityChecker, AnnotationIntrospector annotationIntrospector, CreatorCollector creatorCollector, AnnotatedMethod annotatedMethod, boolean bl2) throws JsonMappingException {
        void var5_6;
        void var6_7;
        void var3_4;
        void var4_5;
        void var7_8;
        Class class_ = var6_7.getRawParameterType(0);
        if (class_ == String.class) {
            if (var7_8 == false && !var3_4.isCreatorVisible((AnnotatedMember)var6_7)) return true;
            {
                var5_6.addStringCreator((AnnotatedWithParams)var6_7);
            }
            return true;
        }
        if (class_ == Integer.TYPE || class_ == Integer.class) {
            if (var7_8 == false && !var3_4.isCreatorVisible((AnnotatedMember)var6_7)) return true;
            {
                var5_6.addIntCreator((AnnotatedWithParams)var6_7);
                return true;
            }
        }
        if (class_ == Long.TYPE || class_ == Long.class) {
            if (var7_8 == false && !var3_4.isCreatorVisible((AnnotatedMember)var6_7)) return true;
            {
                var5_6.addLongCreator((AnnotatedWithParams)var6_7);
                return true;
            }
        }
        if (class_ == Double.TYPE || class_ == Double.class) {
            if (var7_8 == false && !var3_4.isCreatorVisible((AnnotatedMember)var6_7)) return true;
            {
                var5_6.addDoubleCreator((AnnotatedWithParams)var6_7);
                return true;
            }
        }
        if (class_ == Boolean.TYPE || class_ == Boolean.class) {
            if (var7_8 == false && !var3_4.isCreatorVisible((AnnotatedMember)var6_7)) return true;
            {
                var5_6.addBooleanCreator((AnnotatedWithParams)var6_7);
                return true;
            }
        }
        if (!var4_5.hasCreatorAnnotation((Annotated)var6_7)) return false;
        {
            var5_6.addDelegatingCreator((AnnotatedWithParams)var6_7, null);
            return true;
        }
    }

    protected CollectionType _mapAbstractCollectionType(JavaType javaType, DeserializationConfig deserializationConfig) {
        Class class_ = javaType.getRawClass();
        if ((class_ = _collectionFallbacks.get(class_.getName())) == null) {
            return null;
        }
        return (CollectionType)deserializationConfig.constructSpecializedType(javaType, class_);
    }

    public ValueInstantiator _valueInstantiatorInstance(DeserializationConfig deserializationConfig, Annotated object, Object object2) throws JsonMappingException {
        if (object2 == null) {
            return null;
        }
        if (object2 instanceof ValueInstantiator) {
            return (ValueInstantiator)object2;
        }
        if (!(object2 instanceof Class)) {
            throw new IllegalStateException("AnnotationIntrospector returned key deserializer definition of type " + object2.getClass().getName() + "; expected type KeyDeserializer or Class<KeyDeserializer> instead");
        }
        if (ClassUtil.isBogusClass(object2 = (Class)object2)) {
            return null;
        }
        if (!ValueInstantiator.class.isAssignableFrom(object2)) {
            throw new IllegalStateException("AnnotationIntrospector returned Class " + object2.getName() + "; expected Class<ValueInstantiator>");
        }
        HandlerInstantiator handlerInstantiator = deserializationConfig.getHandlerInstantiator();
        if (handlerInstantiator != null && (object = handlerInstantiator.valueInstantiatorInstance(deserializationConfig, (Annotated)object, object2)) != null) {
            return object;
        }
        return (ValueInstantiator)ClassUtil.createInstance(object2, deserializationConfig.canOverrideAccessModifiers());
    }

    /*
     * Enabled aggressive block sorting
     */
    protected CreatorProperty constructCreatorProperty(DeserializationContext deserializationContext, BeanDescription object, PropertyName serializable, int n2, AnnotatedParameter annotatedParameter, Object object2) throws JsonMappingException {
        void var5_6;
        CreatorProperty creatorProperty;
        void var6_7;
        void var4_5;
        DeserializationConfig deserializationConfig = deserializationContext.getConfig();
        Object object3 = deserializationContext.getAnnotationIntrospector();
        Object object4 = object3 == null ? null : object3.hasRequiredMarker((AnnotatedMember)var5_6);
        boolean bl2 = object4 != null && object4.booleanValue();
        object4 = object3 == null ? null : object3.findPropertyDescription((Annotated)var5_6);
        Object object5 = object3 == null ? null : object3.findPropertyIndex((Annotated)var5_6);
        PropertyMetadata propertyMetadata = PropertyMetadata.construct(bl2, (String)object4, (Integer)object5);
        Object object6 = deserializationConfig.getTypeFactory().constructType(var5_6.getParameterType(), object.bindingsForBeanType());
        object5 = new BeanProperty.Std((PropertyName)serializable, (JavaType)object6, object3.findWrapperName((Annotated)var5_6), object.getClassAnnotations(), (AnnotatedMember)var5_6, propertyMetadata);
        object3 = this.resolveType(deserializationContext, (BeanDescription)object, (JavaType)object6, (AnnotatedMember)var5_6);
        object4 = object5;
        if (object3 != object6) {
            object4 = object5.withType((JavaType)object3);
        }
        object6 = this.findDeserializerFromAnnotation(deserializationContext, (Annotated)var5_6);
        AnnotationIntrospector annotationIntrospector = this.modifyTypeByAnnotation(deserializationContext, (Annotated)var5_6, object3);
        object5 = object3 = (TypeDeserializer)annotationIntrospector.getTypeHandler();
        if (object3 == null) {
            object5 = this.findTypeDeserializer(deserializationConfig, (JavaType)((Object)annotationIntrospector));
        }
        object = creatorProperty = new CreatorProperty((PropertyName)serializable, (JavaType)((Object)annotationIntrospector), object4.getWrapperName(), (TypeDeserializer)object5, object.getClassAnnotations(), (AnnotatedParameter)var5_6, (int)var4_5, (Object)var6_7, propertyMetadata);
        if (object6 == null) return object;
        return creatorProperty.withValueDeserializer(deserializationContext.handlePrimaryContextualization(object6, creatorProperty));
    }

    protected EnumResolver<?> constructEnumResolver(Class<?> class_, DeserializationConfig deserializationConfig, AnnotatedMethod object) {
        if (object != null) {
            object = object.getAnnotated();
            if (deserializationConfig.canOverrideAccessModifiers()) {
                ClassUtil.checkAndFixAccess((Member)object);
            }
            return EnumResolver.constructUnsafeUsingMethod(class_, (Method)object);
        }
        if (deserializationConfig.isEnabled(DeserializationFeature.READ_ENUMS_USING_TO_STRING)) {
            return EnumResolver.constructUnsafeUsingToString(class_);
        }
        return EnumResolver.constructUnsafe(class_, deserializationConfig.getAnnotationIntrospector());
    }

    @Override
    public JsonDeserializer<?> createArrayDeserializer(DeserializationContext object, ArrayType arrayType, BeanDescription beanDescription) throws JsonMappingException {
        Object object2;
        DeserializationConfig deserializationConfig = object.getConfig();
        ResolvedType resolvedType = arrayType.getContentType();
        JsonDeserializer jsonDeserializer = (JsonDeserializer)resolvedType.getValueHandler();
        JsonDeserializer jsonDeserializer2 = object = (TypeDeserializer)resolvedType.getTypeHandler();
        if (object == null) {
            jsonDeserializer2 = this.findTypeDeserializer(deserializationConfig, (JavaType)resolvedType);
        }
        object = object2 = this._findCustomArrayDeserializer(arrayType, deserializationConfig, beanDescription, (TypeDeserializer)((Object)jsonDeserializer2), jsonDeserializer);
        if (object2 == null) {
            if (jsonDeserializer == null) {
                object = resolvedType.getRawClass();
                if (resolvedType.isPrimitive()) {
                    return PrimitiveArrayDeserializers.forType(object);
                }
                if (object == String.class) {
                    return StringArrayDeserializer.instance;
                }
            }
            object = new ObjectArrayDeserializer(arrayType, jsonDeserializer, (TypeDeserializer)((Object)jsonDeserializer2));
        }
        jsonDeserializer2 = object;
        if (this._factoryConfig.hasDeserializerModifiers()) {
            object2 = this._factoryConfig.deserializerModifiers().iterator();
            do {
                jsonDeserializer2 = object;
                if (!object2.hasNext()) break;
                object = ((BeanDeserializerModifier)object2.next()).modifyArrayDeserializer(deserializationConfig, arrayType, beanDescription, object);
            } while (true);
        }
        return jsonDeserializer2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public JsonDeserializer<?> createCollectionDeserializer(DeserializationContext var1_1, CollectionType var2_2, BeanDescription var3_3) throws JsonMappingException {
        var13_4 = var2_2.getContentType();
        var14_5 = (JsonDeserializer)var13_4.getValueHandler();
        var12_6 = var1_1.getConfig();
        var9_16 = var4_7 = (TypeDeserializer)var13_4.getTypeHandler();
        if (var4_7 == null) {
            var9_16 = this.findTypeDeserializer(var12_6, (JavaType)var13_4);
        }
        var5_17 = var4_8 = this._findCustomCollectionDeserializer((CollectionType)var2_2, var12_6, var3_3, var9_16, var14_5);
        if (var4_8 == null) {
            var6_18 = var2_2.getRawClass();
            var5_17 = var4_8;
            if (var14_5 == null) {
                var5_17 = var4_8;
                if (EnumSet.class.isAssignableFrom(var6_18)) {
                    var5_17 = new EnumSetDeserializer((JavaType)var13_4, null);
                }
            }
        }
        var4_9 = var5_17;
        var10_19 = var2_2;
        var11_20 = var3_3;
        if (var5_17 != null) ** GOTO lbl47
        if (var2_2.isInterface()) ** GOTO lbl-1000
        var8_21 /* !! */  = var5_17;
        var6_18 = var2_2;
        var7_22 = var3_3;
        if (var2_2.isAbstract()) lbl-1000: // 2 sources:
        {
            if ((var6_18 = this._mapAbstractCollectionType((JavaType)var2_2, var12_6)) == null) {
                if (var2_2.getTypeHandler() == null) {
                    throw new IllegalArgumentException("Can not find a deserializer for non-concrete Collection type " + var2_2);
                }
                var8_21 /* !! */  = AbstractDeserializer.constructForNonPOJO(var3_3);
                var7_23 = var3_3;
                var6_18 = var2_2;
            } else {
                var7_25 = var12_6.introspectForCreation((JavaType)var6_18);
                var8_21 /* !! */  = var5_17;
            }
        }
        var4_10 = var8_21 /* !! */ ;
        var10_19 = var6_18;
        var11_20 = var7_24;
        if (var8_21 /* !! */  == null) {
            if (!(var1_1 = this.findValueInstantiator((DeserializationContext)var1_1, (BeanDescription)var7_24)).canCreateUsingDefault() && var6_18.getRawClass() == ArrayBlockingQueue.class) {
                return new ArrayBlockingQueueDeserializer((JavaType)var6_18, var14_5, var9_16, (ValueInstantiator)var1_1, null);
            }
            if (var13_4.getRawClass() == String.class) {
                var4_11 = new StringCollectionDeserializer((JavaType)var6_18, var14_5, (ValueInstantiator)var1_1);
                var11_20 = var7_24;
                var10_19 = var6_18;
            } else {
                var4_15 = new CollectionDeserializer((JavaType)var6_18, var14_5, var9_16, (ValueInstantiator)var1_1);
                var10_19 = var6_18;
                var11_20 = var7_24;
            }
        }
lbl47: // 5 sources:
        var1_1 = var4_12;
        if (this._factoryConfig.hasDeserializerModifiers() == false) return var1_1;
        var2_2 = this._factoryConfig.deserializerModifiers().iterator();
        do {
            var1_1 = var4_13;
            if (var2_2.hasNext() == false) return var1_1;
            var4_14 = var2_2.next().modifyCollectionDeserializer(var12_6, (CollectionType)var10_19, var11_20, var4_13);
        } while (true);
    }

    @Override
    public JsonDeserializer<?> createCollectionLikeDeserializer(DeserializationContext jsonDeserializer, CollectionLikeType collectionLikeType, BeanDescription beanDescription) throws JsonMappingException {
        JsonDeserializer jsonDeserializer2;
        ResolvedType resolvedType = collectionLikeType.getContentType();
        Object object = (JsonDeserializer)resolvedType.getValueHandler();
        DeserializationConfig deserializationConfig = jsonDeserializer.getConfig();
        jsonDeserializer = jsonDeserializer2 = (TypeDeserializer)resolvedType.getTypeHandler();
        if (jsonDeserializer2 == null) {
            jsonDeserializer = this.findTypeDeserializer(deserializationConfig, (JavaType)resolvedType);
        }
        jsonDeserializer2 = jsonDeserializer = this._findCustomCollectionLikeDeserializer(collectionLikeType, deserializationConfig, beanDescription, (TypeDeserializer)((Object)jsonDeserializer), object);
        if (jsonDeserializer != null) {
            jsonDeserializer2 = jsonDeserializer;
            if (this._factoryConfig.hasDeserializerModifiers()) {
                object = this._factoryConfig.deserializerModifiers().iterator();
                do {
                    jsonDeserializer2 = jsonDeserializer;
                    if (!object.hasNext()) break;
                    jsonDeserializer = ((BeanDeserializerModifier)object.next()).modifyCollectionLikeDeserializer(deserializationConfig, collectionLikeType, beanDescription, jsonDeserializer);
                } while (true);
            }
        }
        return jsonDeserializer2;
    }

    @Override
    public JsonDeserializer<?> createEnumDeserializer(DeserializationContext enumDeserializer, JavaType javaType, BeanDescription beanDescription) throws JsonMappingException {
        JsonDeserializer jsonDeserializer;
        EnumDeserializer enumDeserializer2;
        DeserializationConfig deserializationConfig = enumDeserializer.getConfig();
        Class class_ = javaType.getRawClass();
        JsonDeserializer jsonDeserializer2 = enumDeserializer2 = this._findCustomEnumDeserializer(class_, deserializationConfig, beanDescription);
        if (enumDeserializer2 == null) {
            block8 : {
                jsonDeserializer2 = beanDescription.getFactoryMethods().iterator();
                do {
                    jsonDeserializer = enumDeserializer2;
                    if (!jsonDeserializer2.hasNext()) break block8;
                    jsonDeserializer = (AnnotatedMethod)jsonDeserializer2.next();
                } while (!enumDeserializer.getAnnotationIntrospector().hasCreatorAnnotation((Annotated)((Object)jsonDeserializer)));
                if (jsonDeserializer.getParameterCount() != 1 || !jsonDeserializer.getRawReturnType().isAssignableFrom(class_)) {
                    throw new IllegalArgumentException("Unsuitable method (" + jsonDeserializer + ") decorated with @JsonCreator (for Enum type " + class_.getName() + ")");
                }
                jsonDeserializer = EnumDeserializer.deserializerForCreator(deserializationConfig, class_, (AnnotatedMethod)((Object)jsonDeserializer));
            }
            jsonDeserializer2 = jsonDeserializer;
            if (jsonDeserializer == null) {
                jsonDeserializer2 = new EnumDeserializer(this.constructEnumResolver(class_, deserializationConfig, beanDescription.findJsonValueMethod()));
            }
        }
        enumDeserializer = jsonDeserializer2;
        if (this._factoryConfig.hasDeserializerModifiers()) {
            jsonDeserializer = this._factoryConfig.deserializerModifiers().iterator();
            do {
                enumDeserializer = jsonDeserializer2;
                if (jsonDeserializer.hasNext()) {
                    jsonDeserializer2 = ((BeanDeserializerModifier)jsonDeserializer.next()).modifyEnumDeserializer(deserializationConfig, javaType, beanDescription, jsonDeserializer2);
                    continue;
                }
                break;
                break;
            } while (true);
        }
        return enumDeserializer;
    }

    @Override
    public KeyDeserializer createKeyDeserializer(DeserializationContext object, JavaType javaType) throws JsonMappingException {
        DeserializationConfig deserializationConfig = object.getConfig();
        Object object2 = null;
        KeyDeserializer keyDeserializer = null;
        if (this._factoryConfig.hasKeyDeserializers()) {
            BeanDescription beanDescription = deserializationConfig.introspectClassAnnotations(javaType.getRawClass());
            Iterator<KeyDeserializers> iterator = this._factoryConfig.keyDeserializers().iterator();
            object2 = keyDeserializer;
            while (iterator.hasNext()) {
                object2 = keyDeserializer = iterator.next().findKeyDeserializer(javaType, deserializationConfig, beanDescription);
                if (keyDeserializer == null) continue;
                object2 = keyDeserializer;
                break;
            }
        }
        keyDeserializer = object2;
        if (object2 == null) {
            if (javaType.isEnumType()) {
                return this._createEnumKeyDeserializer((DeserializationContext)object, javaType);
            }
            keyDeserializer = StdKeyDeserializers.findStringBasedKeyDeserializer(deserializationConfig, javaType);
        }
        object = keyDeserializer;
        if (keyDeserializer != null) {
            object = keyDeserializer;
            if (this._factoryConfig.hasDeserializerModifiers()) {
                object2 = this._factoryConfig.deserializerModifiers().iterator();
                do {
                    object = keyDeserializer;
                    if (!object2.hasNext()) break;
                    keyDeserializer = ((BeanDeserializerModifier)object2.next()).modifyKeyDeserializer(deserializationConfig, javaType, keyDeserializer);
                } while (true);
            }
        }
        return object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public JsonDeserializer<?> createMapDeserializer(DeserializationContext var1_1, MapType var2_2, BeanDescription var3_3) throws JsonMappingException {
        var12_4 = var1_1.getConfig();
        var6_5 = var2_2.getKeyType();
        var5_6 = var2_2.getContentType();
        var13_7 = (JsonDeserializer)var5_6.getValueHandler();
        var14_8 = (KeyDeserializer)var6_5.getValueHandler();
        var9_10 = var4_9 = (TypeDeserializer)var5_6.getTypeHandler();
        if (var4_9 == null) {
            var9_10 = this.findTypeDeserializer(var12_4, (JavaType)var5_6);
        }
        var4_9 = var5_6 = this._findCustomMapDeserializer((MapType)var2_2, var12_4, var3_3, var14_8, (TypeDeserializer)var9_10, var13_7);
        var10_11 = var2_2;
        var11_12 = var3_3;
        if (var5_6 != null) ** GOTO lbl47
        var15_13 = var2_2.getRawClass();
        if (EnumMap.class.isAssignableFrom(var15_13)) {
            var4_9 = var6_5.getRawClass();
            if (var4_9 == null) throw new IllegalArgumentException("Can not construct EnumMap; generic (key) type not available");
            if (!var4_9.isEnum()) {
                throw new IllegalArgumentException("Can not construct EnumMap; generic (key) type not available");
            }
            var5_6 = new EnumMapDeserializer((JavaType)var2_2, null, var13_7, (TypeDeserializer)var9_10);
        }
        var4_9 = var5_6;
        var10_11 = var2_2;
        var11_12 = var3_3;
        if (var5_6 != null) ** GOTO lbl47
        if (var2_2.isInterface()) ** GOTO lbl-1000
        var8_14 = var5_6;
        var7_15 = var2_2;
        var6_5 = var3_3;
        if (var2_2.isAbstract()) lbl-1000: // 2 sources:
        {
            if ((var4_9 = BasicDeserializerFactory._mapFallbacks.get(var15_13.getName())) != null) {
                var7_15 = (MapType)var12_4.constructSpecializedType((JavaType)var2_2, var4_9);
                var6_5 = var12_4.introspectForCreation((JavaType)var7_15);
                var8_14 = var5_6;
            } else {
                if (var2_2.getTypeHandler() == null) {
                    throw new IllegalArgumentException("Can not find a deserializer for non-concrete Map type " + var2_2);
                }
                var8_14 = AbstractDeserializer.constructForNonPOJO(var3_3);
                var7_15 = var2_2;
                var6_5 = var3_3;
            }
        }
        var4_9 = var8_14;
        var10_11 = var7_15;
        var11_12 = var6_5;
        if (var8_14 == null) {
            var4_9 = new MapDeserializer((JavaType)var7_15, this.findValueInstantiator((DeserializationContext)var1_1, (BeanDescription)var6_5), var14_8, var13_7, (TypeDeserializer)var9_10);
            var4_9.setIgnorableProperties(var12_4.getAnnotationIntrospector().findPropertiesToIgnore(var6_5.getClassInfo()));
            var11_12 = var6_5;
            var10_11 = var7_15;
        }
lbl47: // 5 sources:
        var1_1 = var4_9;
        if (this._factoryConfig.hasDeserializerModifiers() == false) return var1_1;
        var2_2 = this._factoryConfig.deserializerModifiers().iterator();
        do {
            var1_1 = var4_9;
            if (var2_2.hasNext() == false) return var1_1;
            var4_9 = var2_2.next().modifyMapDeserializer(var12_4, (MapType)var10_11, (BeanDescription)var11_12, var4_9);
        } while (true);
    }

    @Override
    public JsonDeserializer<?> createMapLikeDeserializer(DeserializationContext jsonDeserializer, MapLikeType mapLikeType, BeanDescription beanDescription) throws JsonMappingException {
        JsonDeserializer jsonDeserializer2 = mapLikeType.getKeyType();
        Object object = mapLikeType.getContentType();
        DeserializationConfig deserializationConfig = jsonDeserializer.getConfig();
        JsonDeserializer jsonDeserializer3 = (JsonDeserializer)object.getValueHandler();
        KeyDeserializer keyDeserializer = (KeyDeserializer)jsonDeserializer2.getValueHandler();
        jsonDeserializer = jsonDeserializer2 = (TypeDeserializer)object.getTypeHandler();
        if (jsonDeserializer2 == null) {
            jsonDeserializer = this.findTypeDeserializer(deserializationConfig, (JavaType)object);
        }
        jsonDeserializer2 = jsonDeserializer = this._findCustomMapLikeDeserializer(mapLikeType, deserializationConfig, beanDescription, keyDeserializer, (TypeDeserializer)((Object)jsonDeserializer), jsonDeserializer3);
        if (jsonDeserializer != null) {
            jsonDeserializer2 = jsonDeserializer;
            if (this._factoryConfig.hasDeserializerModifiers()) {
                object = this._factoryConfig.deserializerModifiers().iterator();
                do {
                    jsonDeserializer2 = jsonDeserializer;
                    if (!object.hasNext()) break;
                    jsonDeserializer = ((BeanDeserializerModifier)object.next()).modifyMapLikeDeserializer(deserializationConfig, mapLikeType, beanDescription, jsonDeserializer);
                } while (true);
            }
        }
        return jsonDeserializer2;
    }

    @Override
    public JsonDeserializer<?> createTreeDeserializer(DeserializationConfig jsonDeserializer, JavaType type, BeanDescription beanDescription) throws JsonMappingException {
        jsonDeserializer = this._findCustomTreeNodeDeserializer((Class<? extends JsonNode>)(type = type.getRawClass()), (DeserializationConfig)((Object)jsonDeserializer), beanDescription);
        if (jsonDeserializer != null) {
            return jsonDeserializer;
        }
        return JsonNodeDeserializer.getDeserializer(type);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public JsonDeserializer<?> findDefaultDeserializer(DeserializationContext jsonDeserializer, JavaType untypedObjectDeserializer, BeanDescription object) throws JsonMappingException {
        Class class_ = untypedObjectDeserializer.getRawClass();
        if (class_ == CLASS_OBJECT) {
            return new UntypedObjectDeserializer();
        }
        if (class_ == CLASS_STRING) return StringDeserializer.instance;
        if (class_ == CLASS_CHAR_BUFFER) {
            return StringDeserializer.instance;
        }
        if (class_ == CLASS_ITERABLE) {
            class_ = jsonDeserializer.getTypeFactory();
            if (untypedObjectDeserializer.containedTypeCount() > 0) {
                untypedObjectDeserializer = untypedObjectDeserializer.containedType(0);
                do {
                    return this.createCollectionDeserializer((DeserializationContext)((Object)jsonDeserializer), class_.constructCollectionType((Class<? extends Collection>)Collection.class, (JavaType)((Object)untypedObjectDeserializer)), (BeanDescription)object);
                    break;
                } while (true);
            }
            untypedObjectDeserializer = TypeFactory.unknownType();
            return this.createCollectionDeserializer((DeserializationContext)((Object)jsonDeserializer), class_.constructCollectionType((Class<? extends Collection>)Collection.class, (JavaType)((Object)untypedObjectDeserializer)), (BeanDescription)object);
        }
        object = class_.getName();
        if (class_.isPrimitive() || object.startsWith("java.")) {
            jsonDeserializer = untypedObjectDeserializer = NumberDeserializers.find(class_, (String)object);
            if (untypedObjectDeserializer == null) {
                jsonDeserializer = DateDeserializers.find(class_, (String)object);
            }
            untypedObjectDeserializer = jsonDeserializer;
            if (jsonDeserializer != null) return untypedObjectDeserializer;
        }
        if (class_ != TokenBuffer.class) return JdkDeserializers.find(class_, (String)object);
        return new TokenBufferDeserializer();
    }

    protected JsonDeserializer<Object> findDeserializerFromAnnotation(DeserializationContext deserializationContext, Annotated annotated) throws JsonMappingException {
        Object object = deserializationContext.getAnnotationIntrospector().findDeserializer(annotated);
        if (object == null) {
            return null;
        }
        return deserializationContext.deserializerInstance(annotated, object);
    }

    public TypeDeserializer findPropertyContentTypeDeserializer(DeserializationConfig deserializationConfig, JavaType resolvedType, AnnotatedMember annotatedMember) throws JsonMappingException {
        AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
        TypeResolverBuilder typeResolverBuilder = annotationIntrospector.findPropertyContentTypeResolver(deserializationConfig, annotatedMember, (JavaType)resolvedType);
        resolvedType = resolvedType.getContentType();
        if (typeResolverBuilder == null) {
            return this.findTypeDeserializer(deserializationConfig, (JavaType)resolvedType);
        }
        return typeResolverBuilder.buildTypeDeserializer(deserializationConfig, (JavaType)resolvedType, deserializationConfig.getSubtypeResolver().collectAndResolveSubtypes(annotatedMember, deserializationConfig, annotationIntrospector, (JavaType)resolvedType));
    }

    public TypeDeserializer findPropertyTypeDeserializer(DeserializationConfig deserializationConfig, JavaType javaType, AnnotatedMember annotatedMember) throws JsonMappingException {
        AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
        TypeResolverBuilder typeResolverBuilder = annotationIntrospector.findPropertyTypeResolver(deserializationConfig, annotatedMember, javaType);
        if (typeResolverBuilder == null) {
            return this.findTypeDeserializer(deserializationConfig, javaType);
        }
        return typeResolverBuilder.buildTypeDeserializer(deserializationConfig, javaType, deserializationConfig.getSubtypeResolver().collectAndResolveSubtypes(annotatedMember, deserializationConfig, annotationIntrospector, javaType));
    }

    @Override
    public TypeDeserializer findTypeDeserializer(DeserializationConfig deserializationConfig, JavaType javaType) throws JsonMappingException {
        Object object = deserializationConfig.introspectClassAnnotations(javaType.getRawClass()).getClassInfo();
        Serializable serializable = deserializationConfig.getAnnotationIntrospector();
        Object object2 = serializable.findTypeResolver(deserializationConfig, (AnnotatedClass)object, javaType);
        Collection<NamedType> collection = null;
        if (object2 == null) {
            object2 = object = deserializationConfig.getDefaultTyper(javaType);
            if (object == null) {
                return null;
            }
        } else {
            collection = deserializationConfig.getSubtypeResolver().collectAndResolveSubtypes((AnnotatedClass)object, deserializationConfig, (AnnotationIntrospector)serializable);
        }
        object = object2;
        if (object2.getDefaultImpl() == null) {
            object = object2;
            if (javaType.isAbstract()) {
                serializable = this.mapAbstractType(deserializationConfig, javaType);
                object = object2;
                if (serializable != null) {
                    object = object2;
                    if (serializable.getRawClass() != javaType.getRawClass()) {
                        object = object2.defaultImpl(serializable.getRawClass());
                    }
                }
            }
        }
        return object.buildTypeDeserializer(deserializationConfig, javaType, collection);
    }

    @Override
    public ValueInstantiator findValueInstantiator(DeserializationContext object, BeanDescription object2) throws JsonMappingException {
        block6 : {
            DeserializationConfig deserializationConfig = object.getConfig();
            Object object3 = null;
            Object object4 = object2.getClassInfo();
            Object object5 = object.getAnnotationIntrospector().findValueInstantiator((AnnotatedClass)object4);
            if (object5 != null) {
                object3 = this._valueInstantiatorInstance(deserializationConfig, (Annotated)object4, object5);
            }
            object4 = object3;
            if (object3 == null) {
                object4 = object3 = this._findStdValueInstantiator(deserializationConfig, (BeanDescription)object2);
                if (object3 == null) {
                    object4 = this._constructDefaultValueInstantiator((DeserializationContext)object, (BeanDescription)object2);
                }
            }
            object = object4;
            if (this._factoryConfig.hasValueInstantiators()) {
                object3 = this._factoryConfig.valueInstantiators().iterator();
                do {
                    object = object4;
                    if (!object3.hasNext()) break block6;
                    object5 = (ValueInstantiators)object3.next();
                    object4 = object = object5.findValueInstantiator(deserializationConfig, (BeanDescription)object2, (ValueInstantiator)object4);
                } while (object != null);
                throw new JsonMappingException("Broken registered ValueInstantiators (of type " + object5.getClass().getName() + "): returned null ValueInstantiator");
            }
        }
        if (object.getIncompleteParameter() != null) {
            object = object.getIncompleteParameter();
            object2 = object.getOwner();
            throw new IllegalArgumentException("Argument #" + object.getIndex() + " of constructor " + object2 + " has no property name annotation; must have name when multiple-parameter constructor annotated as Creator");
        }
        return object;
    }

    public DeserializerFactoryConfig getFactoryConfig() {
        return this._factoryConfig;
    }

    @Override
    public JavaType mapAbstractType(DeserializationConfig deserializationConfig, JavaType javaType) throws JsonMappingException {
        JavaType javaType2;
        while ((javaType2 = this._mapAbstractType2(deserializationConfig, javaType)) != null) {
            Class class_;
            Class class_2 = javaType.getRawClass();
            if (class_2 == (class_ = javaType2.getRawClass()) || !class_2.isAssignableFrom(class_)) {
                throw new IllegalArgumentException("Invalid abstract type resolution from " + javaType + " to " + javaType2 + ": latter is not a subtype of former");
            }
            javaType = javaType2;
        }
        return javaType;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected <T extends JavaType> T modifyTypeByAnnotation(DeserializationContext var1_1, Annotated var2_5, T var3_6) throws JsonMappingException {
        var6_7 = var1_1.getAnnotationIntrospector();
        var5_8 = var6_7.findDeserializationType(var2_5, (JavaType)var3_6);
        var4_9 = var3_6;
        if (var5_8 != null) {
            var4_9 = var3_6.narrowBy(var5_8);
        }
        var3_6 = var4_9;
        if (var4_9.isContainerType() == false) return (T)var3_6;
        var3_6 = var6_7.findDeserializationKeyType(var2_5, (JavaType)var4_9.getKeyType());
        var5_8 = var4_9;
        if (var3_6 == null) ** GOTO lbl21
        if (!(var4_9 instanceof MapLikeType)) {
            throw new JsonMappingException("Illegal key-type annotation: type " + var4_9 + " is not a Map(-like) type");
        }
        ** GOTO lbl17
        catch (IllegalArgumentException var1_2) {
            throw new JsonMappingException("Failed to narrow type " + var3_6 + " with concrete-type annotation (value " + var5_8.getName() + "), method '" + var2_5.getName() + "': " + var1_2.getMessage(), null, var1_2);
        }
lbl17: // 1 sources:
        try {
            var5_8 = ((MapLikeType)var4_9).narrowKey(var3_6);
        }
        catch (IllegalArgumentException var1_3) {
            throw new JsonMappingException("Failed to narrow key type " + var4_9 + " with key-type annotation (" + var3_6.getName() + "): " + var1_3.getMessage(), null, var1_3);
        }
lbl21: // 2 sources:
        var4_9 = var5_8.getKeyType();
        var3_6 = var5_8;
        if (var4_9 != null) {
            var3_6 = var5_8;
            if (var4_9.getValueHandler() == null) {
                var4_9 = var1_1.keyDeserializerInstance(var2_5, var6_7.findKeyDeserializer(var2_5));
                var3_6 = var5_8;
                if (var4_9 != null) {
                    var3_6 = ((MapLikeType)var5_8).withKeyValueHandler(var4_9);
                    var3_6.getKeyType();
                }
            }
        }
        var5_8 = var6_7.findDeserializationContentType(var2_5, (JavaType)var3_6.getContentType());
        var4_9 = var3_6;
        if (var5_8 != null) {
            var4_9 = var3_6.narrowContentsBy(var5_8);
        }
        var3_6 = var4_9;
        if (var4_9.getContentType().getValueHandler() != null) return (T)var3_6;
        var1_1 = var1_1.deserializerInstance(var2_5, var6_7.findContentDeserializer(var2_5));
        var3_6 = var4_9;
        if (var1_1 == null) return (T)var3_6;
        var3_6 = var4_9.withContentValueHandler(var1_1);
        return (T)var3_6;
        catch (IllegalArgumentException var1_4) {
            throw new JsonMappingException("Failed to narrow content type " + var3_6 + " with content-type annotation (" + var5_8.getName() + "): " + var1_4.getMessage(), null, var1_4);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected JavaType resolveType(DeserializationContext object, BeanDescription object2, JavaType object3, AnnotatedMember annotatedMember) throws JsonMappingException {
        object2 = object3;
        if (object3.isContainerType()) {
            Object object4 = object.getAnnotationIntrospector();
            object2 = object3;
            if (object3.getKeyType() != null) {
                KeyDeserializer keyDeserializer = object.keyDeserializerInstance(annotatedMember, object4.findKeyDeserializer(annotatedMember));
                object2 = object3;
                if (keyDeserializer != null) {
                    object2 = ((MapLikeType)object3).withKeyValueHandler(keyDeserializer);
                    object2.getKeyType();
                }
            }
            object4 = object.deserializerInstance(annotatedMember, object4.findContentDeserializer(annotatedMember));
            object3 = object2;
            if (object4 != null) {
                object3 = object2.withContentValueHandler(object4);
            }
            object2 = object3;
            if (annotatedMember instanceof AnnotatedMember) {
                object4 = this.findPropertyContentTypeDeserializer(object.getConfig(), (JavaType)object3, annotatedMember);
                object2 = object3;
                if (object4 != null) {
                    object2 = object3.withContentTypeHandler(object4);
                }
            }
        }
        object = annotatedMember instanceof AnnotatedMember ? this.findPropertyTypeDeserializer(object.getConfig(), (JavaType)object2, annotatedMember) : this.findTypeDeserializer(object.getConfig(), (JavaType)object2);
        object3 = object2;
        if (object == null) return object3;
        return object2.withTypeHandler(object);
    }

    @Override
    public final DeserializerFactory withAbstractTypeResolver(AbstractTypeResolver abstractTypeResolver) {
        return this.withConfig(this._factoryConfig.withAbstractTypeResolver(abstractTypeResolver));
    }

    @Override
    public final DeserializerFactory withAdditionalDeserializers(Deserializers deserializers) {
        return this.withConfig(this._factoryConfig.withAdditionalDeserializers(deserializers));
    }

    @Override
    public final DeserializerFactory withAdditionalKeyDeserializers(KeyDeserializers keyDeserializers) {
        return this.withConfig(this._factoryConfig.withAdditionalKeyDeserializers(keyDeserializers));
    }

    protected abstract DeserializerFactory withConfig(DeserializerFactoryConfig var1);

    @Override
    public final DeserializerFactory withDeserializerModifier(BeanDeserializerModifier beanDeserializerModifier) {
        return this.withConfig(this._factoryConfig.withDeserializerModifier(beanDeserializerModifier));
    }

    @Override
    public final DeserializerFactory withValueInstantiators(ValueInstantiators valueInstantiators) {
        return this.withConfig(this._factoryConfig.withValueInstantiators(valueInstantiators));
    }
}

