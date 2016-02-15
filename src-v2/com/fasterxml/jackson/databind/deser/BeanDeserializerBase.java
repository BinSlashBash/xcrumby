/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.SettableAnyProperty;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler;
import com.fasterxml.jackson.databind.deser.impl.InnerClassProperty;
import com.fasterxml.jackson.databind.deser.impl.ManagedReferenceProperty;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReferenceProperty;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdValueProperty;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedObjectIdGenerator;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.deser.impl.UnwrappedPropertyHandler;
import com.fasterxml.jackson.databind.deser.impl.ValueInjector;
import com.fasterxml.jackson.databind.deser.std.StdDelegatingDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class BeanDeserializerBase
extends StdDeserializer<Object>
implements ContextualDeserializer,
ResolvableDeserializer,
Serializable {
    protected static final PropertyName TEMP_PROPERTY_NAME = new PropertyName("#temporary-name");
    private static final long serialVersionUID = 2960120955735322578L;
    protected SettableAnyProperty _anySetter;
    protected final Map<String, SettableBeanProperty> _backRefs;
    protected final BeanPropertyMap _beanProperties;
    protected final JavaType _beanType;
    private final transient Annotations _classAnnotations;
    protected JsonDeserializer<Object> _delegateDeserializer;
    protected ExternalTypeHandler _externalTypeIdHandler;
    protected final HashSet<String> _ignorableProps;
    protected final boolean _ignoreAllUnknown;
    protected final ValueInjector[] _injectables;
    protected final boolean _needViewProcesing;
    protected boolean _nonStandardCreation;
    protected final ObjectIdReader _objectIdReader;
    protected PropertyBasedCreator _propertyBasedCreator;
    protected final JsonFormat.Shape _serializationShape;
    protected transient HashMap<ClassKey, JsonDeserializer<Object>> _subDeserializers;
    protected UnwrappedPropertyHandler _unwrappedPropertyHandler;
    protected final ValueInstantiator _valueInstantiator;
    protected boolean _vanillaProcessing;

    protected BeanDeserializerBase(BeanDeserializerBase beanDeserializerBase) {
        this(beanDeserializerBase, beanDeserializerBase._ignoreAllUnknown);
    }

    public BeanDeserializerBase(BeanDeserializerBase beanDeserializerBase, ObjectIdReader serializable) {
        super(beanDeserializerBase._beanType);
        this._classAnnotations = beanDeserializerBase._classAnnotations;
        this._beanType = beanDeserializerBase._beanType;
        this._valueInstantiator = beanDeserializerBase._valueInstantiator;
        this._delegateDeserializer = beanDeserializerBase._delegateDeserializer;
        this._propertyBasedCreator = beanDeserializerBase._propertyBasedCreator;
        this._backRefs = beanDeserializerBase._backRefs;
        this._ignorableProps = beanDeserializerBase._ignorableProps;
        this._ignoreAllUnknown = beanDeserializerBase._ignoreAllUnknown;
        this._anySetter = beanDeserializerBase._anySetter;
        this._injectables = beanDeserializerBase._injectables;
        this._nonStandardCreation = beanDeserializerBase._nonStandardCreation;
        this._unwrappedPropertyHandler = beanDeserializerBase._unwrappedPropertyHandler;
        this._needViewProcesing = beanDeserializerBase._needViewProcesing;
        this._serializationShape = beanDeserializerBase._serializationShape;
        this._objectIdReader = serializable;
        if (serializable == null) {
            this._beanProperties = beanDeserializerBase._beanProperties;
            this._vanillaProcessing = beanDeserializerBase._vanillaProcessing;
            return;
        }
        serializable = new ObjectIdValueProperty((ObjectIdReader)serializable, PropertyMetadata.STD_REQUIRED);
        this._beanProperties = beanDeserializerBase._beanProperties.withProperty((SettableBeanProperty)serializable);
        this._vanillaProcessing = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected BeanDeserializerBase(BeanDeserializerBase beanDeserializerBase, NameTransformer nameTransformer) {
        UnwrappedPropertyHandler unwrappedPropertyHandler;
        super(beanDeserializerBase._beanType);
        this._classAnnotations = beanDeserializerBase._classAnnotations;
        this._beanType = beanDeserializerBase._beanType;
        this._valueInstantiator = beanDeserializerBase._valueInstantiator;
        this._delegateDeserializer = beanDeserializerBase._delegateDeserializer;
        this._propertyBasedCreator = beanDeserializerBase._propertyBasedCreator;
        this._backRefs = beanDeserializerBase._backRefs;
        this._ignorableProps = beanDeserializerBase._ignorableProps;
        boolean bl2 = nameTransformer != null || beanDeserializerBase._ignoreAllUnknown;
        this._ignoreAllUnknown = bl2;
        this._anySetter = beanDeserializerBase._anySetter;
        this._injectables = beanDeserializerBase._injectables;
        this._objectIdReader = beanDeserializerBase._objectIdReader;
        this._nonStandardCreation = beanDeserializerBase._nonStandardCreation;
        UnwrappedPropertyHandler unwrappedPropertyHandler2 = beanDeserializerBase._unwrappedPropertyHandler;
        if (nameTransformer != null) {
            unwrappedPropertyHandler = unwrappedPropertyHandler2;
            if (unwrappedPropertyHandler2 != null) {
                unwrappedPropertyHandler = unwrappedPropertyHandler2.renameAll(nameTransformer);
            }
            this._beanProperties = beanDeserializerBase._beanProperties.renameAll(nameTransformer);
        } else {
            this._beanProperties = beanDeserializerBase._beanProperties;
            unwrappedPropertyHandler = unwrappedPropertyHandler2;
        }
        this._unwrappedPropertyHandler = unwrappedPropertyHandler;
        this._needViewProcesing = beanDeserializerBase._needViewProcesing;
        this._serializationShape = beanDeserializerBase._serializationShape;
        this._vanillaProcessing = false;
    }

    public BeanDeserializerBase(BeanDeserializerBase beanDeserializerBase, HashSet<String> hashSet) {
        super(beanDeserializerBase._beanType);
        this._classAnnotations = beanDeserializerBase._classAnnotations;
        this._beanType = beanDeserializerBase._beanType;
        this._valueInstantiator = beanDeserializerBase._valueInstantiator;
        this._delegateDeserializer = beanDeserializerBase._delegateDeserializer;
        this._propertyBasedCreator = beanDeserializerBase._propertyBasedCreator;
        this._backRefs = beanDeserializerBase._backRefs;
        this._ignorableProps = hashSet;
        this._ignoreAllUnknown = beanDeserializerBase._ignoreAllUnknown;
        this._anySetter = beanDeserializerBase._anySetter;
        this._injectables = beanDeserializerBase._injectables;
        this._nonStandardCreation = beanDeserializerBase._nonStandardCreation;
        this._unwrappedPropertyHandler = beanDeserializerBase._unwrappedPropertyHandler;
        this._needViewProcesing = beanDeserializerBase._needViewProcesing;
        this._serializationShape = beanDeserializerBase._serializationShape;
        this._vanillaProcessing = beanDeserializerBase._vanillaProcessing;
        this._objectIdReader = beanDeserializerBase._objectIdReader;
        this._beanProperties = beanDeserializerBase._beanProperties;
    }

    protected BeanDeserializerBase(BeanDeserializerBase beanDeserializerBase, boolean bl2) {
        super(beanDeserializerBase._beanType);
        this._classAnnotations = beanDeserializerBase._classAnnotations;
        this._beanType = beanDeserializerBase._beanType;
        this._valueInstantiator = beanDeserializerBase._valueInstantiator;
        this._delegateDeserializer = beanDeserializerBase._delegateDeserializer;
        this._propertyBasedCreator = beanDeserializerBase._propertyBasedCreator;
        this._beanProperties = beanDeserializerBase._beanProperties;
        this._backRefs = beanDeserializerBase._backRefs;
        this._ignorableProps = beanDeserializerBase._ignorableProps;
        this._ignoreAllUnknown = bl2;
        this._anySetter = beanDeserializerBase._anySetter;
        this._injectables = beanDeserializerBase._injectables;
        this._objectIdReader = beanDeserializerBase._objectIdReader;
        this._nonStandardCreation = beanDeserializerBase._nonStandardCreation;
        this._unwrappedPropertyHandler = beanDeserializerBase._unwrappedPropertyHandler;
        this._needViewProcesing = beanDeserializerBase._needViewProcesing;
        this._serializationShape = beanDeserializerBase._serializationShape;
        this._vanillaProcessing = beanDeserializerBase._vanillaProcessing;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected BeanDeserializerBase(BeanDeserializerBuilder object, BeanDescription beanDescription, BeanPropertyMap arrvalueInjector, Map<String, SettableBeanProperty> map, HashSet<String> hashSet, boolean bl2, boolean bl3) {
        boolean bl4 = true;
        Object var9_9 = null;
        super(beanDescription.getType());
        this._classAnnotations = beanDescription.getClassInfo().getAnnotations();
        this._beanType = beanDescription.getType();
        this._valueInstantiator = object.getValueInstantiator();
        this._beanProperties = arrvalueInjector;
        this._backRefs = map;
        this._ignorableProps = hashSet;
        this._ignoreAllUnknown = bl2;
        this._anySetter = object.getAnySetter();
        arrvalueInjector = object.getInjectables();
        arrvalueInjector = arrvalueInjector == null || arrvalueInjector.isEmpty() ? null : arrvalueInjector.toArray(new ValueInjector[arrvalueInjector.size()]);
        this._injectables = arrvalueInjector;
        this._objectIdReader = object.getObjectIdReader();
        bl2 = this._unwrappedPropertyHandler != null || this._valueInstantiator.canCreateUsingDelegate() || this._valueInstantiator.canCreateFromObjectWith() || !this._valueInstantiator.canCreateUsingDefault();
        this._nonStandardCreation = bl2;
        object = beanDescription.findExpectedFormat(null);
        object = object == null ? var9_9 : object.getShape();
        this._serializationShape = object;
        this._needViewProcesing = bl3;
        bl2 = !this._nonStandardCreation && this._injectables == null && !this._needViewProcesing && this._objectIdReader == null ? bl4 : false;
        this._vanillaProcessing = bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private Throwable throwOrReturnThrowable(Throwable throwable, DeserializationContext deserializationContext) throws IOException {
        while (throwable instanceof InvocationTargetException && throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        if (throwable instanceof Error) {
            throw (Error)throwable;
        }
        boolean bl2 = deserializationContext == null || deserializationContext.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS);
        if (!(throwable instanceof IOException)) return throwable;
        if (bl2 && throwable instanceof JsonProcessingException) {
            return throwable;
        }
        throw (IOException)throwable;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Object _convertObjectId(JsonParser jsonParser, DeserializationContext deserializationContext, Object object, JsonDeserializer<Object> jsonDeserializer) throws IOException, JsonProcessingException {
        jsonParser = new TokenBuffer(jsonParser);
        if (object instanceof String) {
            jsonParser.writeString((String)object);
        } else if (object instanceof Long) {
            jsonParser.writeNumber((Long)object);
        } else if (object instanceof Integer) {
            jsonParser.writeNumber((Integer)object);
        } else {
            jsonParser.writeObject(object);
        }
        jsonParser = jsonParser.asParser();
        jsonParser.nextToken();
        return jsonDeserializer.deserialize(jsonParser, deserializationContext);
    }

    protected abstract Object _deserializeUsingPropertyBased(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    protected JsonDeserializer<Object> _findSubclassDeserializer(DeserializationContext jsonDeserializer, Object object, TokenBuffer jsonDeserializer2) throws IOException, JsonProcessingException {
        // MONITORENTER : this
        jsonDeserializer2 = this._subDeserializers == null ? null : this._subDeserializers.get(new ClassKey(object.getClass()));
        // MONITOREXIT : this
        if (jsonDeserializer2 != null) {
            return jsonDeserializer2;
        }
        if ((jsonDeserializer = jsonDeserializer.findRootValueDeserializer(jsonDeserializer.constructType(object.getClass()))) == null) return jsonDeserializer;
        // MONITORENTER : this
        if (this._subDeserializers == null) {
            this._subDeserializers = new HashMap<K, V>();
        }
        this._subDeserializers.put(new ClassKey(object.getClass()), jsonDeserializer);
        // MONITOREXIT : this
        return jsonDeserializer;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Object _handleTypedObjectId(JsonParser object, DeserializationContext object2, Object object3, Object object4) throws IOException, JsonProcessingException {
        JsonDeserializer<Object> jsonDeserializer = this._objectIdReader.getDeserializer();
        object = jsonDeserializer.handledType() == object4.getClass() ? object4 : this._convertObjectId((JsonParser)object, (DeserializationContext)object2, object4, jsonDeserializer);
        object2.findObjectId(object, this._objectIdReader.generator, this._objectIdReader.resolver).bindItem(object3);
        object4 = this._objectIdReader.idProperty;
        object2 = object3;
        if (object4 == null) return object2;
        return object4.setAndReturn(object3, object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected SettableBeanProperty _resolveInnerClassValuedProperty(DeserializationContext deserializationContext, SettableBeanProperty settableBeanProperty) {
        Object object = settableBeanProperty.getValueDeserializer();
        Constructor<?> constructor = settableBeanProperty;
        if (!(object instanceof BeanDeserializerBase)) return constructor;
        constructor = settableBeanProperty;
        if (((BeanDeserializerBase)object).getValueInstantiator().canCreateUsingDefault()) return constructor;
        Constructor<?>[] arrconstructor = settableBeanProperty.getType().getRawClass();
        object = ClassUtil.getOuterClass(arrconstructor);
        constructor = settableBeanProperty;
        if (object == null) return constructor;
        constructor = settableBeanProperty;
        if (object != this._beanType.getRawClass()) return constructor;
        arrconstructor = arrconstructor.getConstructors();
        int n2 = arrconstructor.length;
        int n3 = 0;
        do {
            constructor = settableBeanProperty;
            if (n3 >= n2) return constructor;
            constructor = arrconstructor[n3];
            Class<?>[] arrclass = constructor.getParameterTypes();
            if (arrclass.length == 1 && arrclass[0] == object) {
                if (!deserializationContext.getConfig().canOverrideAccessModifiers()) return new InnerClassProperty(settableBeanProperty, constructor);
                ClassUtil.checkAndFixAccess(constructor);
                return new InnerClassProperty(settableBeanProperty, constructor);
            }
            ++n3;
        } while (true);
    }

    protected SettableBeanProperty _resolveManagedReferenceProperty(DeserializationContext object, SettableBeanProperty settableBeanProperty) {
        object = settableBeanProperty.getManagedReferenceName();
        if (object == null) {
            return settableBeanProperty;
        }
        SettableBeanProperty settableBeanProperty2 = settableBeanProperty.getValueDeserializer().findBackReference((String)object);
        if (settableBeanProperty2 == null) {
            throw new IllegalArgumentException("Can not handle managed/back reference '" + (String)object + "': no back reference property found from type " + settableBeanProperty.getType());
        }
        JavaType javaType = this._beanType;
        JavaType javaType2 = settableBeanProperty2.getType();
        boolean bl2 = settableBeanProperty.getType().isContainerType();
        if (!javaType2.getRawClass().isAssignableFrom(javaType.getRawClass())) {
            throw new IllegalArgumentException("Can not handle managed/back reference '" + (String)object + "': back reference type (" + javaType2.getRawClass().getName() + ") not compatible with managed type (" + javaType.getRawClass().getName() + ")");
        }
        return new ManagedReferenceProperty(settableBeanProperty, (String)object, settableBeanProperty2, this._classAnnotations, bl2);
    }

    protected SettableBeanProperty _resolveUnwrappedProperty(DeserializationContext object, SettableBeanProperty settableBeanProperty) {
        Object object2 = settableBeanProperty.getMember();
        if (object2 != null && (object2 = object.getAnnotationIntrospector().findUnwrappingNameTransformer((AnnotatedMember)object2)) != null && (object2 = (object = settableBeanProperty.getValueDeserializer()).unwrappingDeserializer((NameTransformer)object2)) != object && object2 != null) {
            return settableBeanProperty.withValueDeserializer(object2);
        }
        return null;
    }

    protected SettableBeanProperty _resolvedObjectIdProperty(DeserializationContext object, SettableBeanProperty settableBeanProperty) {
        object = settableBeanProperty.getObjectIdInfo();
        ObjectIdReader objectIdReader = settableBeanProperty.getValueDeserializer().getObjectIdReader();
        if (object == null && objectIdReader == null) {
            return settableBeanProperty;
        }
        return new ObjectIdReferenceProperty(settableBeanProperty, (ObjectIdInfo)object);
    }

    protected abstract BeanDeserializerBase asArrayDeserializer();

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext object, BeanProperty object2) throws JsonMappingException {
        void var4_12;
        Object var4_15;
        Serializable serializable = this._objectIdReader;
        AnnotationIntrospector annotationIntrospector = object.getAnnotationIntrospector();
        Object object3 = object2 == null || annotationIntrospector == null ? null : object2.getMember();
        ObjectIdReader objectIdReader = serializable;
        if (object3 != null) {
            ObjectIdReader objectIdReader2 = serializable;
            if (annotationIntrospector != null) {
                object2 = annotationIntrospector.findObjectIdInfo((Annotated)object3);
                ObjectIdReader objectIdReader3 = serializable;
                if (object2 != null) {
                    void var4_10;
                    ObjectIdInfo objectIdInfo = annotationIntrospector.findObjectReferenceInfo((Annotated)object3, (ObjectIdInfo)object2);
                    object2 = objectIdInfo.getGeneratorType();
                    ObjectIdResolver objectIdResolver = object.objectIdResolverInstance((Annotated)object3, objectIdInfo);
                    if (object2 == ObjectIdGenerators.PropertyGenerator.class) {
                        object2 = objectIdInfo.getPropertyName();
                        serializable = this.findProperty((PropertyName)object2);
                        if (serializable == null) {
                            throw new IllegalArgumentException("Invalid Object Id definition for " + this.handledType().getName() + ": can not find property with name '" + object2 + "'");
                        }
                        object2 = serializable.getType();
                        PropertyBasedObjectIdGenerator propertyBasedObjectIdGenerator = new PropertyBasedObjectIdGenerator(objectIdInfo.getScope());
                    } else {
                        object2 = object.constructType(object2);
                        object2 = object.getTypeFactory().findTypeParameters((JavaType)object2, ObjectIdGenerator.class)[0];
                        serializable = null;
                        ObjectIdGenerator<?> objectIdGenerator = object.objectIdGeneratorInstance((Annotated)object3, objectIdInfo);
                    }
                    object = object.findRootValueDeserializer((JavaType)object2);
                    ObjectIdReader objectIdReader4 = ObjectIdReader.construct((JavaType)object2, objectIdInfo.getPropertyName(), var4_10, object, (SettableBeanProperty)serializable, objectIdResolver);
                }
            }
        }
        object2 = object = this;
        if (var4_12 != null) {
            object2 = object;
            if (var4_12 != this._objectIdReader) {
                object2 = object.withObjectIdReader((ObjectIdReader)var4_12);
            }
        }
        object = object2;
        if (object3 != null) {
            String[] arrstring = annotationIntrospector.findPropertiesToIgnore((Annotated)object3);
            object = object2;
            if (arrstring != null) {
                object = object2;
                if (arrstring.length != 0) {
                    object = object2.withIgnorableProperties(ArrayBuilders.setAndArray(object2._ignorableProps, arrstring));
                }
            }
        }
        object2 = var4_15 = null;
        if (object3 != null) {
            object3 = annotationIntrospector.findFormat((Annotated)object3);
            object2 = var4_15;
            if (object3 != null) {
                object2 = object3.getShape();
            }
        }
        object3 = object2;
        if (object2 == null) {
            object3 = this._serializationShape;
        }
        object2 = object;
        if (object3 != JsonFormat.Shape.ARRAY) return object2;
        return object.asArrayDeserializer();
    }

    public Iterator<SettableBeanProperty> creatorProperties() {
        if (this._propertyBasedCreator == null) {
            return Collections.emptyList().iterator();
        }
        return this._propertyBasedCreator.properties().iterator();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public Object deserializeFromArray(JsonParser object, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (this._delegateDeserializer == null) {
            if (!deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) throw deserializationContext.mappingException(this.getBeanClass());
            object.nextToken();
            T t2 = this.deserialize((JsonParser)object, deserializationContext);
            if (object.nextToken() == JsonToken.END_ARRAY) return t2;
            throw deserializationContext.wrongTokenException((JsonParser)object, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single '" + this._valueClass.getName() + "' value but there was more than a single value in the array");
        }
        try {
            object = this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize((JsonParser)object, deserializationContext));
            if (this._injectables == null) return object;
            this.injectValues(deserializationContext, object);
            return object;
        }
        catch (Exception var1_2) {
            this.wrapInstantiationProblem(var1_2, deserializationContext);
            throw deserializationContext.mappingException(this.getBeanClass());
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Object deserializeFromBoolean(JsonParser object, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        boolean bl2;
        if (this._delegateDeserializer != null && !this._valueInstantiator.canCreateFromBoolean()) {
            object = this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize((JsonParser)object, deserializationContext));
            if (this._injectables == null) return object;
            this.injectValues(deserializationContext, object);
            return object;
        }
        if (object.getCurrentToken() == JsonToken.VALUE_TRUE) {
            bl2 = true;
            do {
                return this._valueInstantiator.createFromBoolean(deserializationContext, bl2);
                break;
            } while (true);
        }
        bl2 = false;
        return this._valueInstantiator.createFromBoolean(deserializationContext, bl2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Object deserializeFromDouble(JsonParser object, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        switch (.$SwitchMap$com$fasterxml$jackson$core$JsonParser$NumberType[object.getNumberType().ordinal()]) {
            default: {
                if (this._delegateDeserializer == null) throw deserializationContext.instantiationException(this.getBeanClass(), "no suitable creator method found to deserialize from JSON floating-point number");
                return this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize((JsonParser)object, deserializationContext));
            }
            case 3: 
            case 4: {
                Object object2;
                if (this._delegateDeserializer == null) return this._valueInstantiator.createFromDouble(deserializationContext, object.getDoubleValue());
                if (this._valueInstantiator.canCreateFromDouble()) return this._valueInstantiator.createFromDouble(deserializationContext, object.getDoubleValue());
                object = object2 = this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize((JsonParser)object, deserializationContext));
                if (this._injectables == null) return object;
                this.injectValues(deserializationContext, object2);
                return object2;
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Object deserializeFromNumber(JsonParser object, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (this._objectIdReader != null) {
            return this.deserializeFromObjectId((JsonParser)object, deserializationContext);
        }
        switch (.$SwitchMap$com$fasterxml$jackson$core$JsonParser$NumberType[object.getNumberType().ordinal()]) {
            default: {
                Object object2;
                if (this._delegateDeserializer == null) throw deserializationContext.instantiationException(this.getBeanClass(), "no suitable creator method found to deserialize from JSON integer number");
                object = object2 = this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize((JsonParser)object, deserializationContext));
                if (this._injectables == null) return object;
                this.injectValues(deserializationContext, object2);
                return object2;
            }
            case 1: {
                Object object3;
                if (this._delegateDeserializer == null) return this._valueInstantiator.createFromInt(deserializationContext, object.getIntValue());
                if (this._valueInstantiator.canCreateFromInt()) return this._valueInstantiator.createFromInt(deserializationContext, object.getIntValue());
                object = object3 = this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize((JsonParser)object, deserializationContext));
                if (this._injectables == null) return object;
                this.injectValues(deserializationContext, object3);
                return object3;
            }
            case 2: {
                Object object4;
                if (this._delegateDeserializer == null) return this._valueInstantiator.createFromLong(deserializationContext, object.getLongValue());
                if (this._valueInstantiator.canCreateFromInt()) return this._valueInstantiator.createFromLong(deserializationContext, object.getLongValue());
                object = object4 = this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize((JsonParser)object, deserializationContext));
                if (this._injectables == null) return object;
                this.injectValues(deserializationContext, object4);
                return object4;
            }
        }
    }

    public abstract Object deserializeFromObject(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException;

    protected Object deserializeFromObjectId(JsonParser jsonParser, DeserializationContext object) throws IOException, JsonProcessingException {
        Object object2 = this._objectIdReader.readObjectReference(jsonParser, (DeserializationContext)object);
        Object object3 = (object = object.findObjectId(object2, this._objectIdReader.generator, this._objectIdReader.resolver)).resolve();
        if (object3 == null) {
            throw new UnresolvedForwardReference("Could not resolve Object Id [" + object2 + "] (for " + this._beanType + ").", jsonParser.getCurrentLocation(), (ReadableObjectId)object);
        }
        return object3;
    }

    protected Object deserializeFromObjectUsingNonDefault(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (this._delegateDeserializer != null) {
            return this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
        }
        if (this._propertyBasedCreator != null) {
            return this._deserializeUsingPropertyBased(jsonParser, deserializationContext);
        }
        if (this._beanType.isAbstract()) {
            throw JsonMappingException.from(jsonParser, "Can not instantiate abstract type " + this._beanType + " (need to add/enable type information?)");
        }
        throw JsonMappingException.from(jsonParser, "No suitable constructor found for type " + this._beanType + ": can not instantiate from JSON object (need to add/enable type information?)");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Object deserializeFromString(JsonParser object, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Object object2;
        if (this._objectIdReader != null) {
            return this.deserializeFromObjectId((JsonParser)object, deserializationContext);
        }
        if (this._delegateDeserializer == null) return this._valueInstantiator.createFromString(deserializationContext, object.getText());
        if (this._valueInstantiator.canCreateFromString()) return this._valueInstantiator.createFromString(deserializationContext, object.getText());
        object = object2 = this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize((JsonParser)object, deserializationContext));
        if (this._injectables == null) return object;
        this.injectValues(deserializationContext, object2);
        return object2;
    }

    protected Object deserializeWithObjectId(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return this.deserializeFromObject(jsonParser, deserializationContext);
    }

    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        if (this._objectIdReader != null) {
            Object object;
            if (jsonParser.canReadObjectId() && (object = jsonParser.getObjectId()) != null) {
                return this._handleTypedObjectId(jsonParser, deserializationContext, typeDeserializer.deserializeTypedFromObject(jsonParser, deserializationContext), object);
            }
            object = jsonParser.getCurrentToken();
            if (object != null && object.isScalarValue()) {
                return this.deserializeFromObjectId(jsonParser, deserializationContext);
            }
        }
        return typeDeserializer.deserializeTypedFromObject(jsonParser, deserializationContext);
    }

    @Override
    public SettableBeanProperty findBackReference(String string2) {
        if (this._backRefs == null) {
            return null;
        }
        return this._backRefs.get(string2);
    }

    protected JsonDeserializer<Object> findConvertingDeserializer(DeserializationContext deserializationContext, SettableBeanProperty settableBeanProperty) throws JsonMappingException {
        Object object = deserializationContext.getAnnotationIntrospector();
        if (object != null && (object = object.findDeserializationConverter(settableBeanProperty.getMember())) != null) {
            object = deserializationContext.converterInstance(settableBeanProperty.getMember(), object);
            JavaType javaType = object.getInputType(deserializationContext.getTypeFactory());
            return new StdDelegatingDeserializer<Object>((Converter<Object, Object>)object, javaType, deserializationContext.findContextualValueDeserializer(javaType, settableBeanProperty));
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public SettableBeanProperty findProperty(int n2) {
        SettableBeanProperty settableBeanProperty = this._beanProperties == null ? null : this._beanProperties.find(n2);
        SettableBeanProperty settableBeanProperty2 = settableBeanProperty;
        if (settableBeanProperty != null) return settableBeanProperty2;
        settableBeanProperty2 = settableBeanProperty;
        if (this._propertyBasedCreator == null) return settableBeanProperty2;
        return this._propertyBasedCreator.findCreatorProperty(n2);
    }

    public SettableBeanProperty findProperty(PropertyName propertyName) {
        return this.findProperty(propertyName.getSimpleName());
    }

    /*
     * Enabled aggressive block sorting
     */
    public SettableBeanProperty findProperty(String string2) {
        SettableBeanProperty settableBeanProperty = this._beanProperties == null ? null : this._beanProperties.find(string2);
        SettableBeanProperty settableBeanProperty2 = settableBeanProperty;
        if (settableBeanProperty != null) return settableBeanProperty2;
        settableBeanProperty2 = settableBeanProperty;
        if (this._propertyBasedCreator == null) return settableBeanProperty2;
        return this._propertyBasedCreator.findCreatorProperty(string2);
    }

    @Deprecated
    public final Class<?> getBeanClass() {
        return this._beanType.getRawClass();
    }

    @Override
    public Collection<Object> getKnownPropertyNames() {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        Iterator<SettableBeanProperty> iterator = this._beanProperties.iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next().getName());
        }
        return arrayList;
    }

    @Override
    public ObjectIdReader getObjectIdReader() {
        return this._objectIdReader;
    }

    public int getPropertyCount() {
        return this._beanProperties.size();
    }

    public ValueInstantiator getValueInstantiator() {
        return this._valueInstantiator;
    }

    @Override
    public JavaType getValueType() {
        return this._beanType;
    }

    protected void handleIgnoredProperty(JsonParser jsonParser, DeserializationContext deserializationContext, Object object, String string2) throws IOException, JsonProcessingException {
        if (deserializationContext.isEnabled(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)) {
            throw IgnoredPropertyException.from(jsonParser, object, string2, this.getKnownPropertyNames());
        }
        jsonParser.skipChildren();
    }

    protected Object handlePolymorphic(JsonParser jsonParser, DeserializationContext deserializationContext, Object object, TokenBuffer closeable) throws IOException, JsonProcessingException {
        JsonDeserializer<Object> jsonDeserializer = this._findSubclassDeserializer(deserializationContext, object, (TokenBuffer)closeable);
        if (jsonDeserializer != null) {
            Object object2 = object;
            if (closeable != null) {
                closeable.writeEndObject();
                closeable = closeable.asParser();
                closeable.nextToken();
                object2 = jsonDeserializer.deserialize((JsonParser)closeable, deserializationContext, object);
            }
            object = object2;
            if (jsonParser != null) {
                object = jsonDeserializer.deserialize(jsonParser, deserializationContext, object2);
            }
            return object;
        }
        Object object3 = object;
        if (closeable != null) {
            object3 = this.handleUnknownProperties(deserializationContext, object, (TokenBuffer)closeable);
        }
        object = object3;
        if (jsonParser != null) {
            object = this.deserialize(jsonParser, deserializationContext, object3);
        }
        return object;
    }

    protected Object handleUnknownProperties(DeserializationContext deserializationContext, Object object, TokenBuffer closeable) throws IOException, JsonProcessingException {
        closeable.writeEndObject();
        closeable = closeable.asParser();
        while (closeable.nextToken() != JsonToken.END_OBJECT) {
            String string2 = closeable.getCurrentName();
            closeable.nextToken();
            this.handleUnknownProperty((JsonParser)closeable, deserializationContext, object, string2);
        }
        return object;
    }

    @Override
    protected void handleUnknownProperty(JsonParser jsonParser, DeserializationContext deserializationContext, Object object, String string2) throws IOException, JsonProcessingException {
        if (this._ignoreAllUnknown) {
            jsonParser.skipChildren();
            return;
        }
        if (this._ignorableProps != null && this._ignorableProps.contains(string2)) {
            this.handleIgnoredProperty(jsonParser, deserializationContext, object, string2);
        }
        super.handleUnknownProperty(jsonParser, deserializationContext, object, string2);
    }

    protected void handleUnknownVanilla(JsonParser jsonParser, DeserializationContext deserializationContext, Object object, String string2) throws IOException, JsonProcessingException {
        if (this._ignorableProps != null && this._ignorableProps.contains(string2)) {
            this.handleIgnoredProperty(jsonParser, deserializationContext, object, string2);
            return;
        }
        if (this._anySetter != null) {
            try {
                this._anySetter.deserializeAndSet(jsonParser, deserializationContext, object, string2);
                return;
            }
            catch (Exception var1_2) {
                this.wrapAndThrow((Throwable)var1_2, object, string2, deserializationContext);
                return;
            }
        }
        this.handleUnknownProperty(jsonParser, deserializationContext, object, string2);
    }

    @Override
    public Class<?> handledType() {
        return this._beanType.getRawClass();
    }

    public boolean hasProperty(String string2) {
        if (this._beanProperties.find(string2) != null) {
            return true;
        }
        return false;
    }

    public boolean hasViews() {
        return this._needViewProcesing;
    }

    protected void injectValues(DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
        ValueInjector[] arrvalueInjector = this._injectables;
        int n2 = arrvalueInjector.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            arrvalueInjector[i2].inject(deserializationContext, object);
        }
    }

    @Override
    public boolean isCachable() {
        return true;
    }

    public Iterator<SettableBeanProperty> properties() {
        if (this._beanProperties == null) {
            throw new IllegalStateException("Can only call after BeanDeserializer has been resolved");
        }
        return this._beanProperties.iterator();
    }

    public void replaceProperty(SettableBeanProperty settableBeanProperty, SettableBeanProperty settableBeanProperty2) {
        this._beanProperties.replace(settableBeanProperty2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void resolve(DeserializationContext deserializationContext) throws JsonMappingException {
        Object object;
        Object object2;
        Object object3;
        Object object4 = null;
        JsonDeserializer<Object> jsonDeserializer = null;
        if (this._valueInstantiator.canCreateFromObjectWith()) {
            object4 = this._valueInstantiator.getFromObjectArguments(deserializationContext.getConfig());
            this._propertyBasedCreator = PropertyBasedCreator.construct(deserializationContext, this._valueInstantiator, (SettableBeanProperty[])object4);
            object2 = this._propertyBasedCreator.properties().iterator();
            do {
                object4 = jsonDeserializer;
                if (!object2.hasNext()) break;
                object3 = (SettableBeanProperty)object2.next();
                if (!object3.hasValueTypeDeserializer() || (object = object3.getValueTypeDeserializer()).getTypeInclusion() != JsonTypeInfo.As.EXTERNAL_PROPERTY) continue;
                object4 = jsonDeserializer;
                if (jsonDeserializer == null) {
                    object4 = new ExternalTypeHandler.Builder();
                }
                object4.addExternal((SettableBeanProperty)object3, (TypeDeserializer)object);
                jsonDeserializer = object4;
            } while (true);
        }
        jsonDeserializer = null;
        Iterator<SettableBeanProperty> iterator = this._beanProperties.iterator();
        object2 = object4;
        while (iterator.hasNext()) {
            JsonDeserializer<Object> jsonDeserializer2;
            object = iterator.next();
            object3 = object;
            if (!object3.hasValueDeserializer()) {
                object4 = jsonDeserializer2 = this.findConvertingDeserializer(deserializationContext, (SettableBeanProperty)object3);
                if (jsonDeserializer2 == null) {
                    object4 = this.findDeserializer(deserializationContext, object3.getType(), (BeanProperty)object3);
                }
                object4 = object3.withValueDeserializer(object4);
            } else {
                jsonDeserializer2 = object3.getValueDeserializer();
                JsonDeserializer<?> jsonDeserializer3 = deserializationContext.handlePrimaryContextualization(jsonDeserializer2, (BeanProperty)object3);
                object4 = object3;
                if (jsonDeserializer3 != jsonDeserializer2) {
                    object4 = object3.withValueDeserializer(jsonDeserializer3);
                }
            }
            object4 = object3 = this._resolveManagedReferenceProperty(deserializationContext, (SettableBeanProperty)object4);
            if (!(object3 instanceof ManagedReferenceProperty)) {
                object4 = this._resolvedObjectIdProperty(deserializationContext, (SettableBeanProperty)object3);
            }
            if ((object3 = this._resolveUnwrappedProperty(deserializationContext, (SettableBeanProperty)object4)) != null) {
                object4 = jsonDeserializer;
                if (jsonDeserializer == null) {
                    object4 = new UnwrappedPropertyHandler();
                }
                object4.addProperty((SettableBeanProperty)object3);
                jsonDeserializer = object4;
                if (object3 == object) continue;
                this._beanProperties.replace((SettableBeanProperty)object3);
                jsonDeserializer = object4;
                continue;
            }
            object3 = this._resolveInnerClassValuedProperty(deserializationContext, (SettableBeanProperty)object4);
            if (object3 != object) {
                this._beanProperties.replace((SettableBeanProperty)object3);
            }
            if (!object3.hasValueTypeDeserializer() || (object = object3.getValueTypeDeserializer()).getTypeInclusion() != JsonTypeInfo.As.EXTERNAL_PROPERTY) continue;
            object4 = object2;
            if (object2 == null) {
                object4 = new ExternalTypeHandler.Builder();
            }
            object4.addExternal((SettableBeanProperty)object3, (TypeDeserializer)object);
            this._beanProperties.remove((SettableBeanProperty)object3);
            object2 = object4;
        }
        if (this._anySetter != null && !this._anySetter.hasValueDeserializer()) {
            this._anySetter = this._anySetter.withValueDeserializer(this.findDeserializer(deserializationContext, this._anySetter.getType(), this._anySetter.getProperty()));
        }
        if (this._valueInstantiator.canCreateUsingDelegate()) {
            object4 = this._valueInstantiator.getDelegateType(deserializationContext.getConfig());
            if (object4 == null) {
                throw new IllegalArgumentException("Invalid delegate-creator definition for " + this._beanType + ": value instantiator (" + this._valueInstantiator.getClass().getName() + ") returned true for 'canCreateUsingDelegate()', but null for 'getDelegateType()'");
            }
            object3 = this._valueInstantiator.getDelegateCreator();
            this._delegateDeserializer = this.findDeserializer(deserializationContext, (JavaType)object4, new BeanProperty.Std(TEMP_PROPERTY_NAME, (JavaType)object4, null, this._classAnnotations, (AnnotatedMember)object3, PropertyMetadata.STD_OPTIONAL));
        }
        if (object2 != null) {
            this._externalTypeIdHandler = object2.build();
            this._nonStandardCreation = true;
        }
        this._unwrappedPropertyHandler = jsonDeserializer;
        if (jsonDeserializer != null) {
            this._nonStandardCreation = true;
        }
        boolean bl2 = this._vanillaProcessing && !this._nonStandardCreation;
        this._vanillaProcessing = bl2;
    }

    @Override
    public abstract JsonDeserializer<Object> unwrappingDeserializer(NameTransformer var1);

    public abstract BeanDeserializerBase withIgnorableProperties(HashSet<String> var1);

    public abstract BeanDeserializerBase withObjectIdReader(ObjectIdReader var1);

    public void wrapAndThrow(Throwable throwable, Object object, int n2, DeserializationContext deserializationContext) throws IOException {
        throw JsonMappingException.wrapWithPath(this.throwOrReturnThrowable(throwable, deserializationContext), object, n2);
    }

    public void wrapAndThrow(Throwable throwable, Object object, String string2, DeserializationContext deserializationContext) throws IOException {
        throw JsonMappingException.wrapWithPath(this.throwOrReturnThrowable(throwable, deserializationContext), object, string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void wrapInstantiationProblem(Throwable throwable, DeserializationContext deserializationContext) throws IOException {
        while (throwable instanceof InvocationTargetException && throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        if (throwable instanceof Error) {
            throw (Error)throwable;
        }
        boolean bl2 = deserializationContext == null || deserializationContext.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS);
        if (throwable instanceof IOException) {
            throw (IOException)throwable;
        }
        if (throwable instanceof RuntimeException) {
            throw (RuntimeException)throwable;
        }
        throw deserializationContext.instantiationException(this._beanType.getRawClass(), throwable);
    }

}

