package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.BeanProperty.Std;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler;
import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler.Builder;
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
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
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
  implements ContextualDeserializer, ResolvableDeserializer, Serializable
{
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
  
  protected BeanDeserializerBase(BeanDeserializerBase paramBeanDeserializerBase)
  {
    this(paramBeanDeserializerBase, paramBeanDeserializerBase._ignoreAllUnknown);
  }
  
  public BeanDeserializerBase(BeanDeserializerBase paramBeanDeserializerBase, ObjectIdReader paramObjectIdReader)
  {
    super(paramBeanDeserializerBase._beanType);
    this._classAnnotations = paramBeanDeserializerBase._classAnnotations;
    this._beanType = paramBeanDeserializerBase._beanType;
    this._valueInstantiator = paramBeanDeserializerBase._valueInstantiator;
    this._delegateDeserializer = paramBeanDeserializerBase._delegateDeserializer;
    this._propertyBasedCreator = paramBeanDeserializerBase._propertyBasedCreator;
    this._backRefs = paramBeanDeserializerBase._backRefs;
    this._ignorableProps = paramBeanDeserializerBase._ignorableProps;
    this._ignoreAllUnknown = paramBeanDeserializerBase._ignoreAllUnknown;
    this._anySetter = paramBeanDeserializerBase._anySetter;
    this._injectables = paramBeanDeserializerBase._injectables;
    this._nonStandardCreation = paramBeanDeserializerBase._nonStandardCreation;
    this._unwrappedPropertyHandler = paramBeanDeserializerBase._unwrappedPropertyHandler;
    this._needViewProcesing = paramBeanDeserializerBase._needViewProcesing;
    this._serializationShape = paramBeanDeserializerBase._serializationShape;
    this._objectIdReader = paramObjectIdReader;
    if (paramObjectIdReader == null)
    {
      this._beanProperties = paramBeanDeserializerBase._beanProperties;
      this._vanillaProcessing = paramBeanDeserializerBase._vanillaProcessing;
      return;
    }
    paramObjectIdReader = new ObjectIdValueProperty(paramObjectIdReader, PropertyMetadata.STD_REQUIRED);
    this._beanProperties = paramBeanDeserializerBase._beanProperties.withProperty(paramObjectIdReader);
    this._vanillaProcessing = false;
  }
  
  protected BeanDeserializerBase(BeanDeserializerBase paramBeanDeserializerBase, NameTransformer paramNameTransformer)
  {
    super(paramBeanDeserializerBase._beanType);
    this._classAnnotations = paramBeanDeserializerBase._classAnnotations;
    this._beanType = paramBeanDeserializerBase._beanType;
    this._valueInstantiator = paramBeanDeserializerBase._valueInstantiator;
    this._delegateDeserializer = paramBeanDeserializerBase._delegateDeserializer;
    this._propertyBasedCreator = paramBeanDeserializerBase._propertyBasedCreator;
    this._backRefs = paramBeanDeserializerBase._backRefs;
    this._ignorableProps = paramBeanDeserializerBase._ignorableProps;
    boolean bool;
    UnwrappedPropertyHandler localUnwrappedPropertyHandler2;
    UnwrappedPropertyHandler localUnwrappedPropertyHandler1;
    if ((paramNameTransformer != null) || (paramBeanDeserializerBase._ignoreAllUnknown))
    {
      bool = true;
      this._ignoreAllUnknown = bool;
      this._anySetter = paramBeanDeserializerBase._anySetter;
      this._injectables = paramBeanDeserializerBase._injectables;
      this._objectIdReader = paramBeanDeserializerBase._objectIdReader;
      this._nonStandardCreation = paramBeanDeserializerBase._nonStandardCreation;
      localUnwrappedPropertyHandler2 = paramBeanDeserializerBase._unwrappedPropertyHandler;
      if (paramNameTransformer == null) {
        break label186;
      }
      localUnwrappedPropertyHandler1 = localUnwrappedPropertyHandler2;
      if (localUnwrappedPropertyHandler2 != null) {
        localUnwrappedPropertyHandler1 = localUnwrappedPropertyHandler2.renameAll(paramNameTransformer);
      }
      this._beanProperties = paramBeanDeserializerBase._beanProperties.renameAll(paramNameTransformer);
    }
    for (;;)
    {
      this._unwrappedPropertyHandler = localUnwrappedPropertyHandler1;
      this._needViewProcesing = paramBeanDeserializerBase._needViewProcesing;
      this._serializationShape = paramBeanDeserializerBase._serializationShape;
      this._vanillaProcessing = false;
      return;
      bool = false;
      break;
      label186:
      this._beanProperties = paramBeanDeserializerBase._beanProperties;
      localUnwrappedPropertyHandler1 = localUnwrappedPropertyHandler2;
    }
  }
  
  public BeanDeserializerBase(BeanDeserializerBase paramBeanDeserializerBase, HashSet<String> paramHashSet)
  {
    super(paramBeanDeserializerBase._beanType);
    this._classAnnotations = paramBeanDeserializerBase._classAnnotations;
    this._beanType = paramBeanDeserializerBase._beanType;
    this._valueInstantiator = paramBeanDeserializerBase._valueInstantiator;
    this._delegateDeserializer = paramBeanDeserializerBase._delegateDeserializer;
    this._propertyBasedCreator = paramBeanDeserializerBase._propertyBasedCreator;
    this._backRefs = paramBeanDeserializerBase._backRefs;
    this._ignorableProps = paramHashSet;
    this._ignoreAllUnknown = paramBeanDeserializerBase._ignoreAllUnknown;
    this._anySetter = paramBeanDeserializerBase._anySetter;
    this._injectables = paramBeanDeserializerBase._injectables;
    this._nonStandardCreation = paramBeanDeserializerBase._nonStandardCreation;
    this._unwrappedPropertyHandler = paramBeanDeserializerBase._unwrappedPropertyHandler;
    this._needViewProcesing = paramBeanDeserializerBase._needViewProcesing;
    this._serializationShape = paramBeanDeserializerBase._serializationShape;
    this._vanillaProcessing = paramBeanDeserializerBase._vanillaProcessing;
    this._objectIdReader = paramBeanDeserializerBase._objectIdReader;
    this._beanProperties = paramBeanDeserializerBase._beanProperties;
  }
  
  protected BeanDeserializerBase(BeanDeserializerBase paramBeanDeserializerBase, boolean paramBoolean)
  {
    super(paramBeanDeserializerBase._beanType);
    this._classAnnotations = paramBeanDeserializerBase._classAnnotations;
    this._beanType = paramBeanDeserializerBase._beanType;
    this._valueInstantiator = paramBeanDeserializerBase._valueInstantiator;
    this._delegateDeserializer = paramBeanDeserializerBase._delegateDeserializer;
    this._propertyBasedCreator = paramBeanDeserializerBase._propertyBasedCreator;
    this._beanProperties = paramBeanDeserializerBase._beanProperties;
    this._backRefs = paramBeanDeserializerBase._backRefs;
    this._ignorableProps = paramBeanDeserializerBase._ignorableProps;
    this._ignoreAllUnknown = paramBoolean;
    this._anySetter = paramBeanDeserializerBase._anySetter;
    this._injectables = paramBeanDeserializerBase._injectables;
    this._objectIdReader = paramBeanDeserializerBase._objectIdReader;
    this._nonStandardCreation = paramBeanDeserializerBase._nonStandardCreation;
    this._unwrappedPropertyHandler = paramBeanDeserializerBase._unwrappedPropertyHandler;
    this._needViewProcesing = paramBeanDeserializerBase._needViewProcesing;
    this._serializationShape = paramBeanDeserializerBase._serializationShape;
    this._vanillaProcessing = paramBeanDeserializerBase._vanillaProcessing;
  }
  
  protected BeanDeserializerBase(BeanDeserializerBuilder paramBeanDeserializerBuilder, BeanDescription paramBeanDescription, BeanPropertyMap paramBeanPropertyMap, Map<String, SettableBeanProperty> paramMap, HashSet<String> paramHashSet, boolean paramBoolean1, boolean paramBoolean2)
  {
    super(paramBeanDescription.getType());
    this._classAnnotations = paramBeanDescription.getClassInfo().getAnnotations();
    this._beanType = paramBeanDescription.getType();
    this._valueInstantiator = paramBeanDeserializerBuilder.getValueInstantiator();
    this._beanProperties = paramBeanPropertyMap;
    this._backRefs = paramMap;
    this._ignorableProps = paramHashSet;
    this._ignoreAllUnknown = paramBoolean1;
    this._anySetter = paramBeanDeserializerBuilder.getAnySetter();
    paramBeanPropertyMap = paramBeanDeserializerBuilder.getInjectables();
    if ((paramBeanPropertyMap == null) || (paramBeanPropertyMap.isEmpty()))
    {
      paramBeanPropertyMap = null;
      this._injectables = paramBeanPropertyMap;
      this._objectIdReader = paramBeanDeserializerBuilder.getObjectIdReader();
      if ((this._unwrappedPropertyHandler == null) && (!this._valueInstantiator.canCreateUsingDelegate()) && (!this._valueInstantiator.canCreateFromObjectWith()) && (this._valueInstantiator.canCreateUsingDefault())) {
        break label236;
      }
      paramBoolean1 = true;
      label145:
      this._nonStandardCreation = paramBoolean1;
      paramBeanDeserializerBuilder = paramBeanDescription.findExpectedFormat(null);
      if (paramBeanDeserializerBuilder != null) {
        break label242;
      }
      paramBeanDeserializerBuilder = (BeanDeserializerBuilder)localObject;
      label164:
      this._serializationShape = paramBeanDeserializerBuilder;
      this._needViewProcesing = paramBoolean2;
      if ((this._nonStandardCreation) || (this._injectables != null) || (this._needViewProcesing) || (this._objectIdReader != null)) {
        break label250;
      }
    }
    label236:
    label242:
    label250:
    for (paramBoolean1 = bool;; paramBoolean1 = false)
    {
      this._vanillaProcessing = paramBoolean1;
      return;
      paramBeanPropertyMap = (ValueInjector[])paramBeanPropertyMap.toArray(new ValueInjector[paramBeanPropertyMap.size()]);
      break;
      paramBoolean1 = false;
      break label145;
      paramBeanDeserializerBuilder = paramBeanDeserializerBuilder.getShape();
      break label164;
    }
  }
  
  private Throwable throwOrReturnThrowable(Throwable paramThrowable, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    while (((paramThrowable instanceof InvocationTargetException)) && (paramThrowable.getCause() != null)) {
      paramThrowable = paramThrowable.getCause();
    }
    if ((paramThrowable instanceof Error)) {
      throw ((Error)paramThrowable);
    }
    if ((paramDeserializationContext == null) || (paramDeserializationContext.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS))) {}
    for (int i = 1; (paramThrowable instanceof IOException); i = 0)
    {
      if ((i != 0) && ((paramThrowable instanceof JsonProcessingException))) {
        return paramThrowable;
      }
      throw ((IOException)paramThrowable);
    }
    if ((i == 0) && ((paramThrowable instanceof RuntimeException))) {
      throw ((RuntimeException)paramThrowable);
    }
    return paramThrowable;
  }
  
  protected Object _convertObjectId(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject, JsonDeserializer<Object> paramJsonDeserializer)
    throws IOException, JsonProcessingException
  {
    paramJsonParser = new TokenBuffer(paramJsonParser);
    if ((paramObject instanceof String)) {
      paramJsonParser.writeString((String)paramObject);
    }
    for (;;)
    {
      paramJsonParser = paramJsonParser.asParser();
      paramJsonParser.nextToken();
      return paramJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
      if ((paramObject instanceof Long)) {
        paramJsonParser.writeNumber(((Long)paramObject).longValue());
      } else if ((paramObject instanceof Integer)) {
        paramJsonParser.writeNumber(((Integer)paramObject).intValue());
      } else {
        paramJsonParser.writeObject(paramObject);
      }
    }
  }
  
  protected abstract Object _deserializeUsingPropertyBased(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException, JsonProcessingException;
  
  protected JsonDeserializer<Object> _findSubclassDeserializer(DeserializationContext paramDeserializationContext, Object paramObject, TokenBuffer paramTokenBuffer)
    throws IOException, JsonProcessingException
  {
    try
    {
      if (this._subDeserializers == null) {}
      for (paramTokenBuffer = null;; paramTokenBuffer = (JsonDeserializer)this._subDeserializers.get(new ClassKey(paramObject.getClass())))
      {
        if (paramTokenBuffer == null) {
          break;
        }
        return paramTokenBuffer;
      }
      paramDeserializationContext = paramDeserializationContext.findRootValueDeserializer(paramDeserializationContext.constructType(paramObject.getClass()));
    }
    finally {}
    if (paramDeserializationContext != null) {
      try
      {
        if (this._subDeserializers == null) {
          this._subDeserializers = new HashMap();
        }
        this._subDeserializers.put(new ClassKey(paramObject.getClass()), paramDeserializationContext);
      }
      finally {}
    }
    return paramDeserializationContext;
  }
  
  protected Object _handleTypedObjectId(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject1, Object paramObject2)
    throws IOException, JsonProcessingException
  {
    JsonDeserializer localJsonDeserializer = this._objectIdReader.getDeserializer();
    if (localJsonDeserializer.handledType() == paramObject2.getClass()) {}
    for (paramJsonParser = (JsonParser)paramObject2;; paramJsonParser = _convertObjectId(paramJsonParser, paramDeserializationContext, paramObject2, localJsonDeserializer))
    {
      paramDeserializationContext.findObjectId(paramJsonParser, this._objectIdReader.generator, this._objectIdReader.resolver).bindItem(paramObject1);
      paramObject2 = this._objectIdReader.idProperty;
      paramDeserializationContext = (DeserializationContext)paramObject1;
      if (paramObject2 != null) {
        paramDeserializationContext = ((SettableBeanProperty)paramObject2).setAndReturn(paramObject1, paramJsonParser);
      }
      return paramDeserializationContext;
    }
  }
  
  protected SettableBeanProperty _resolveInnerClassValuedProperty(DeserializationContext paramDeserializationContext, SettableBeanProperty paramSettableBeanProperty)
  {
    Object localObject2 = paramSettableBeanProperty.getValueDeserializer();
    Object localObject1 = paramSettableBeanProperty;
    Object localObject3;
    int j;
    int i;
    if ((localObject2 instanceof BeanDeserializerBase))
    {
      localObject1 = paramSettableBeanProperty;
      if (!((BeanDeserializerBase)localObject2).getValueInstantiator().canCreateUsingDefault())
      {
        localObject3 = paramSettableBeanProperty.getType().getRawClass();
        localObject2 = ClassUtil.getOuterClass((Class)localObject3);
        localObject1 = paramSettableBeanProperty;
        if (localObject2 != null)
        {
          localObject1 = paramSettableBeanProperty;
          if (localObject2 == this._beanType.getRawClass())
          {
            localObject3 = ((Class)localObject3).getConstructors();
            j = localObject3.length;
            i = 0;
          }
        }
      }
    }
    for (;;)
    {
      localObject1 = paramSettableBeanProperty;
      if (i < j)
      {
        localObject1 = localObject3[i];
        Class[] arrayOfClass = ((Constructor)localObject1).getParameterTypes();
        if ((arrayOfClass.length == 1) && (arrayOfClass[0] == localObject2))
        {
          if (paramDeserializationContext.getConfig().canOverrideAccessModifiers()) {
            ClassUtil.checkAndFixAccess((Member)localObject1);
          }
          localObject1 = new InnerClassProperty(paramSettableBeanProperty, (Constructor)localObject1);
        }
      }
      else
      {
        return (SettableBeanProperty)localObject1;
      }
      i += 1;
    }
  }
  
  protected SettableBeanProperty _resolveManagedReferenceProperty(DeserializationContext paramDeserializationContext, SettableBeanProperty paramSettableBeanProperty)
  {
    paramDeserializationContext = paramSettableBeanProperty.getManagedReferenceName();
    if (paramDeserializationContext == null) {
      return paramSettableBeanProperty;
    }
    SettableBeanProperty localSettableBeanProperty = paramSettableBeanProperty.getValueDeserializer().findBackReference(paramDeserializationContext);
    if (localSettableBeanProperty == null) {
      throw new IllegalArgumentException("Can not handle managed/back reference '" + paramDeserializationContext + "': no back reference property found from type " + paramSettableBeanProperty.getType());
    }
    JavaType localJavaType1 = this._beanType;
    JavaType localJavaType2 = localSettableBeanProperty.getType();
    boolean bool = paramSettableBeanProperty.getType().isContainerType();
    if (!localJavaType2.getRawClass().isAssignableFrom(localJavaType1.getRawClass())) {
      throw new IllegalArgumentException("Can not handle managed/back reference '" + paramDeserializationContext + "': back reference type (" + localJavaType2.getRawClass().getName() + ") not compatible with managed type (" + localJavaType1.getRawClass().getName() + ")");
    }
    return new ManagedReferenceProperty(paramSettableBeanProperty, paramDeserializationContext, localSettableBeanProperty, this._classAnnotations, bool);
  }
  
  protected SettableBeanProperty _resolveUnwrappedProperty(DeserializationContext paramDeserializationContext, SettableBeanProperty paramSettableBeanProperty)
  {
    Object localObject = paramSettableBeanProperty.getMember();
    if (localObject != null)
    {
      localObject = paramDeserializationContext.getAnnotationIntrospector().findUnwrappingNameTransformer((AnnotatedMember)localObject);
      if (localObject != null)
      {
        paramDeserializationContext = paramSettableBeanProperty.getValueDeserializer();
        localObject = paramDeserializationContext.unwrappingDeserializer((NameTransformer)localObject);
        if ((localObject != paramDeserializationContext) && (localObject != null)) {
          return paramSettableBeanProperty.withValueDeserializer((JsonDeserializer)localObject);
        }
      }
    }
    return null;
  }
  
  protected SettableBeanProperty _resolvedObjectIdProperty(DeserializationContext paramDeserializationContext, SettableBeanProperty paramSettableBeanProperty)
  {
    paramDeserializationContext = paramSettableBeanProperty.getObjectIdInfo();
    ObjectIdReader localObjectIdReader = paramSettableBeanProperty.getValueDeserializer().getObjectIdReader();
    if ((paramDeserializationContext == null) && (localObjectIdReader == null)) {
      return paramSettableBeanProperty;
    }
    return new ObjectIdReferenceProperty(paramSettableBeanProperty, paramDeserializationContext);
  }
  
  protected abstract BeanDeserializerBase asArrayDeserializer();
  
  public JsonDeserializer<?> createContextual(DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty)
    throws JsonMappingException
  {
    Object localObject3 = this._objectIdReader;
    AnnotationIntrospector localAnnotationIntrospector = paramDeserializationContext.getAnnotationIntrospector();
    if ((paramBeanProperty == null) || (localAnnotationIntrospector == null)) {}
    ObjectIdInfo localObjectIdInfo;
    ObjectIdResolver localObjectIdResolver;
    for (Object localObject1 = null;; localObject1 = paramBeanProperty.getMember())
    {
      localObject2 = localObject3;
      if (localObject1 == null) {
        break label208;
      }
      localObject2 = localObject3;
      if (localAnnotationIntrospector == null) {
        break label208;
      }
      paramBeanProperty = localAnnotationIntrospector.findObjectIdInfo((Annotated)localObject1);
      localObject2 = localObject3;
      if (paramBeanProperty == null) {
        break label208;
      }
      localObjectIdInfo = localAnnotationIntrospector.findObjectReferenceInfo((Annotated)localObject1, paramBeanProperty);
      paramBeanProperty = localObjectIdInfo.getGeneratorType();
      localObjectIdResolver = paramDeserializationContext.objectIdResolverInstance((Annotated)localObject1, localObjectIdInfo);
      if (paramBeanProperty != ObjectIdGenerators.PropertyGenerator.class) {
        break label334;
      }
      paramBeanProperty = localObjectIdInfo.getPropertyName();
      localObject3 = findProperty(paramBeanProperty);
      if (localObject3 != null) {
        break;
      }
      throw new IllegalArgumentException("Invalid Object Id definition for " + handledType().getName() + ": can not find property with name '" + paramBeanProperty + "'");
    }
    paramBeanProperty = ((SettableBeanProperty)localObject3).getType();
    for (Object localObject2 = new PropertyBasedObjectIdGenerator(localObjectIdInfo.getScope());; localObject2 = paramDeserializationContext.objectIdGeneratorInstance((Annotated)localObject1, localObjectIdInfo))
    {
      paramDeserializationContext = paramDeserializationContext.findRootValueDeserializer(paramBeanProperty);
      localObject2 = ObjectIdReader.construct(paramBeanProperty, localObjectIdInfo.getPropertyName(), (ObjectIdGenerator)localObject2, paramDeserializationContext, (SettableBeanProperty)localObject3, localObjectIdResolver);
      label208:
      paramDeserializationContext = this;
      paramBeanProperty = paramDeserializationContext;
      if (localObject2 != null)
      {
        paramBeanProperty = paramDeserializationContext;
        if (localObject2 != this._objectIdReader) {
          paramBeanProperty = paramDeserializationContext.withObjectIdReader((ObjectIdReader)localObject2);
        }
      }
      paramDeserializationContext = paramBeanProperty;
      if (localObject1 != null)
      {
        localObject2 = localAnnotationIntrospector.findPropertiesToIgnore((Annotated)localObject1);
        paramDeserializationContext = paramBeanProperty;
        if (localObject2 != null)
        {
          paramDeserializationContext = paramBeanProperty;
          if (localObject2.length != 0) {
            paramDeserializationContext = paramBeanProperty.withIgnorableProperties(ArrayBuilders.setAndArray(paramBeanProperty._ignorableProps, (Object[])localObject2));
          }
        }
      }
      localObject2 = null;
      paramBeanProperty = (BeanProperty)localObject2;
      if (localObject1 != null)
      {
        localObject1 = localAnnotationIntrospector.findFormat((Annotated)localObject1);
        paramBeanProperty = (BeanProperty)localObject2;
        if (localObject1 != null) {
          paramBeanProperty = ((JsonFormat.Value)localObject1).getShape();
        }
      }
      localObject1 = paramBeanProperty;
      if (paramBeanProperty == null) {
        localObject1 = this._serializationShape;
      }
      paramBeanProperty = paramDeserializationContext;
      if (localObject1 == JsonFormat.Shape.ARRAY) {
        paramBeanProperty = paramDeserializationContext.asArrayDeserializer();
      }
      return paramBeanProperty;
      label334:
      paramBeanProperty = paramDeserializationContext.constructType(paramBeanProperty);
      paramBeanProperty = paramDeserializationContext.getTypeFactory().findTypeParameters(paramBeanProperty, ObjectIdGenerator.class)[0];
      localObject3 = null;
    }
  }
  
  public Iterator<SettableBeanProperty> creatorProperties()
  {
    if (this._propertyBasedCreator == null) {
      return Collections.emptyList().iterator();
    }
    return this._propertyBasedCreator.properties().iterator();
  }
  
  public Object deserializeFromArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException, JsonProcessingException
  {
    if (this._delegateDeserializer != null) {
      try
      {
        paramJsonParser = this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
        if (this._injectables != null) {
          injectValues(paramDeserializationContext, paramJsonParser);
        }
        return paramJsonParser;
      }
      catch (Exception paramJsonParser)
      {
        wrapInstantiationProblem(paramJsonParser, paramDeserializationContext);
      }
    }
    while (!paramDeserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
      throw paramDeserializationContext.mappingException(getBeanClass());
    }
    paramJsonParser.nextToken();
    Object localObject = deserialize(paramJsonParser, paramDeserializationContext);
    if (paramJsonParser.nextToken() != JsonToken.END_ARRAY) {
      throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single '" + this._valueClass.getName() + "' value but there was more than a single value in the array");
    }
    return localObject;
  }
  
  public Object deserializeFromBoolean(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException, JsonProcessingException
  {
    if ((this._delegateDeserializer != null) && (!this._valueInstantiator.canCreateFromBoolean()))
    {
      paramJsonParser = this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
      if (this._injectables != null) {
        injectValues(paramDeserializationContext, paramJsonParser);
      }
      return paramJsonParser;
    }
    if (paramJsonParser.getCurrentToken() == JsonToken.VALUE_TRUE) {}
    for (boolean bool = true;; bool = false) {
      return this._valueInstantiator.createFromBoolean(paramDeserializationContext, bool);
    }
  }
  
  public Object deserializeFromDouble(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException, JsonProcessingException
  {
    switch (paramJsonParser.getNumberType())
    {
    default: 
      if (this._delegateDeserializer != null) {
        paramJsonParser = this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
      }
      break;
    case ???: 
    case ???: 
      Object localObject;
      do
      {
        return paramJsonParser;
        if ((this._delegateDeserializer == null) || (this._valueInstantiator.canCreateFromDouble())) {
          break;
        }
        localObject = this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
        paramJsonParser = (JsonParser)localObject;
      } while (this._injectables == null);
      injectValues(paramDeserializationContext, localObject);
      return localObject;
      return this._valueInstantiator.createFromDouble(paramDeserializationContext, paramJsonParser.getDoubleValue());
    }
    throw paramDeserializationContext.instantiationException(getBeanClass(), "no suitable creator method found to deserialize from JSON floating-point number");
  }
  
  public Object deserializeFromNumber(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException, JsonProcessingException
  {
    if (this._objectIdReader != null) {
      paramJsonParser = deserializeFromObjectId(paramJsonParser, paramDeserializationContext);
    }
    Object localObject;
    do
    {
      do
      {
        do
        {
          return paramJsonParser;
          switch (paramJsonParser.getNumberType())
          {
          default: 
            if (this._delegateDeserializer == null) {
              break label220;
            }
            localObject = this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
            paramJsonParser = (JsonParser)localObject;
          }
        } while (this._injectables == null);
        injectValues(paramDeserializationContext, localObject);
        return localObject;
        if ((this._delegateDeserializer == null) || (this._valueInstantiator.canCreateFromInt())) {
          break;
        }
        localObject = this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
        paramJsonParser = (JsonParser)localObject;
      } while (this._injectables == null);
      injectValues(paramDeserializationContext, localObject);
      return localObject;
      return this._valueInstantiator.createFromInt(paramDeserializationContext, paramJsonParser.getIntValue());
      if ((this._delegateDeserializer == null) || (this._valueInstantiator.canCreateFromInt())) {
        break;
      }
      localObject = this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
      paramJsonParser = (JsonParser)localObject;
    } while (this._injectables == null);
    injectValues(paramDeserializationContext, localObject);
    return localObject;
    return this._valueInstantiator.createFromLong(paramDeserializationContext, paramJsonParser.getLongValue());
    label220:
    throw paramDeserializationContext.instantiationException(getBeanClass(), "no suitable creator method found to deserialize from JSON integer number");
  }
  
  public abstract Object deserializeFromObject(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException, JsonProcessingException;
  
  protected Object deserializeFromObjectId(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException, JsonProcessingException
  {
    Object localObject1 = this._objectIdReader.readObjectReference(paramJsonParser, paramDeserializationContext);
    paramDeserializationContext = paramDeserializationContext.findObjectId(localObject1, this._objectIdReader.generator, this._objectIdReader.resolver);
    Object localObject2 = paramDeserializationContext.resolve();
    if (localObject2 == null) {
      throw new UnresolvedForwardReference("Could not resolve Object Id [" + localObject1 + "] (for " + this._beanType + ").", paramJsonParser.getCurrentLocation(), paramDeserializationContext);
    }
    return localObject2;
  }
  
  protected Object deserializeFromObjectUsingNonDefault(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException, JsonProcessingException
  {
    if (this._delegateDeserializer != null) {
      return this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
    }
    if (this._propertyBasedCreator != null) {
      return _deserializeUsingPropertyBased(paramJsonParser, paramDeserializationContext);
    }
    if (this._beanType.isAbstract()) {
      throw JsonMappingException.from(paramJsonParser, "Can not instantiate abstract type " + this._beanType + " (need to add/enable type information?)");
    }
    throw JsonMappingException.from(paramJsonParser, "No suitable constructor found for type " + this._beanType + ": can not instantiate from JSON object (need to add/enable type information?)");
  }
  
  public Object deserializeFromString(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException, JsonProcessingException
  {
    if (this._objectIdReader != null) {
      paramJsonParser = deserializeFromObjectId(paramJsonParser, paramDeserializationContext);
    }
    Object localObject;
    do
    {
      return paramJsonParser;
      if ((this._delegateDeserializer == null) || (this._valueInstantiator.canCreateFromString())) {
        break;
      }
      localObject = this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
      paramJsonParser = (JsonParser)localObject;
    } while (this._injectables == null);
    injectValues(paramDeserializationContext, localObject);
    return localObject;
    return this._valueInstantiator.createFromString(paramDeserializationContext, paramJsonParser.getText());
  }
  
  protected Object deserializeWithObjectId(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException, JsonProcessingException
  {
    return deserializeFromObject(paramJsonParser, paramDeserializationContext);
  }
  
  public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
    throws IOException, JsonProcessingException
  {
    if (this._objectIdReader != null)
    {
      if (paramJsonParser.canReadObjectId())
      {
        localObject = paramJsonParser.getObjectId();
        if (localObject != null) {
          return _handleTypedObjectId(paramJsonParser, paramDeserializationContext, paramTypeDeserializer.deserializeTypedFromObject(paramJsonParser, paramDeserializationContext), localObject);
        }
      }
      Object localObject = paramJsonParser.getCurrentToken();
      if ((localObject != null) && (((JsonToken)localObject).isScalarValue())) {
        return deserializeFromObjectId(paramJsonParser, paramDeserializationContext);
      }
    }
    return paramTypeDeserializer.deserializeTypedFromObject(paramJsonParser, paramDeserializationContext);
  }
  
  public SettableBeanProperty findBackReference(String paramString)
  {
    if (this._backRefs == null) {
      return null;
    }
    return (SettableBeanProperty)this._backRefs.get(paramString);
  }
  
  protected JsonDeserializer<Object> findConvertingDeserializer(DeserializationContext paramDeserializationContext, SettableBeanProperty paramSettableBeanProperty)
    throws JsonMappingException
  {
    Object localObject = paramDeserializationContext.getAnnotationIntrospector();
    if (localObject != null)
    {
      localObject = ((AnnotationIntrospector)localObject).findDeserializationConverter(paramSettableBeanProperty.getMember());
      if (localObject != null)
      {
        localObject = paramDeserializationContext.converterInstance(paramSettableBeanProperty.getMember(), localObject);
        JavaType localJavaType = ((Converter)localObject).getInputType(paramDeserializationContext.getTypeFactory());
        return new StdDelegatingDeserializer((Converter)localObject, localJavaType, paramDeserializationContext.findContextualValueDeserializer(localJavaType, paramSettableBeanProperty));
      }
    }
    return null;
  }
  
  public SettableBeanProperty findProperty(int paramInt)
  {
    if (this._beanProperties == null) {}
    for (SettableBeanProperty localSettableBeanProperty1 = null;; localSettableBeanProperty1 = this._beanProperties.find(paramInt))
    {
      SettableBeanProperty localSettableBeanProperty2 = localSettableBeanProperty1;
      if (localSettableBeanProperty1 == null)
      {
        localSettableBeanProperty2 = localSettableBeanProperty1;
        if (this._propertyBasedCreator != null) {
          localSettableBeanProperty2 = this._propertyBasedCreator.findCreatorProperty(paramInt);
        }
      }
      return localSettableBeanProperty2;
    }
  }
  
  public SettableBeanProperty findProperty(PropertyName paramPropertyName)
  {
    return findProperty(paramPropertyName.getSimpleName());
  }
  
  public SettableBeanProperty findProperty(String paramString)
  {
    if (this._beanProperties == null) {}
    for (SettableBeanProperty localSettableBeanProperty1 = null;; localSettableBeanProperty1 = this._beanProperties.find(paramString))
    {
      SettableBeanProperty localSettableBeanProperty2 = localSettableBeanProperty1;
      if (localSettableBeanProperty1 == null)
      {
        localSettableBeanProperty2 = localSettableBeanProperty1;
        if (this._propertyBasedCreator != null) {
          localSettableBeanProperty2 = this._propertyBasedCreator.findCreatorProperty(paramString);
        }
      }
      return localSettableBeanProperty2;
    }
  }
  
  @Deprecated
  public final Class<?> getBeanClass()
  {
    return this._beanType.getRawClass();
  }
  
  public Collection<Object> getKnownPropertyNames()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this._beanProperties.iterator();
    while (localIterator.hasNext()) {
      localArrayList.add(((SettableBeanProperty)localIterator.next()).getName());
    }
    return localArrayList;
  }
  
  public ObjectIdReader getObjectIdReader()
  {
    return this._objectIdReader;
  }
  
  public int getPropertyCount()
  {
    return this._beanProperties.size();
  }
  
  public ValueInstantiator getValueInstantiator()
  {
    return this._valueInstantiator;
  }
  
  public JavaType getValueType()
  {
    return this._beanType;
  }
  
  protected void handleIgnoredProperty(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject, String paramString)
    throws IOException, JsonProcessingException
  {
    if (paramDeserializationContext.isEnabled(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)) {
      throw IgnoredPropertyException.from(paramJsonParser, paramObject, paramString, getKnownPropertyNames());
    }
    paramJsonParser.skipChildren();
  }
  
  protected Object handlePolymorphic(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject, TokenBuffer paramTokenBuffer)
    throws IOException, JsonProcessingException
  {
    JsonDeserializer localJsonDeserializer = _findSubclassDeserializer(paramDeserializationContext, paramObject, paramTokenBuffer);
    if (localJsonDeserializer != null)
    {
      localObject = paramObject;
      if (paramTokenBuffer != null)
      {
        paramTokenBuffer.writeEndObject();
        paramTokenBuffer = paramTokenBuffer.asParser();
        paramTokenBuffer.nextToken();
        localObject = localJsonDeserializer.deserialize(paramTokenBuffer, paramDeserializationContext, paramObject);
      }
      paramObject = localObject;
      if (paramJsonParser != null) {
        paramObject = localJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext, localObject);
      }
      return paramObject;
    }
    Object localObject = paramObject;
    if (paramTokenBuffer != null) {
      localObject = handleUnknownProperties(paramDeserializationContext, paramObject, paramTokenBuffer);
    }
    paramObject = localObject;
    if (paramJsonParser != null) {
      paramObject = deserialize(paramJsonParser, paramDeserializationContext, localObject);
    }
    return paramObject;
  }
  
  protected Object handleUnknownProperties(DeserializationContext paramDeserializationContext, Object paramObject, TokenBuffer paramTokenBuffer)
    throws IOException, JsonProcessingException
  {
    paramTokenBuffer.writeEndObject();
    paramTokenBuffer = paramTokenBuffer.asParser();
    while (paramTokenBuffer.nextToken() != JsonToken.END_OBJECT)
    {
      String str = paramTokenBuffer.getCurrentName();
      paramTokenBuffer.nextToken();
      handleUnknownProperty(paramTokenBuffer, paramDeserializationContext, paramObject, str);
    }
    return paramObject;
  }
  
  protected void handleUnknownProperty(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject, String paramString)
    throws IOException, JsonProcessingException
  {
    if (this._ignoreAllUnknown)
    {
      paramJsonParser.skipChildren();
      return;
    }
    if ((this._ignorableProps != null) && (this._ignorableProps.contains(paramString))) {
      handleIgnoredProperty(paramJsonParser, paramDeserializationContext, paramObject, paramString);
    }
    super.handleUnknownProperty(paramJsonParser, paramDeserializationContext, paramObject, paramString);
  }
  
  protected void handleUnknownVanilla(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject, String paramString)
    throws IOException, JsonProcessingException
  {
    if ((this._ignorableProps != null) && (this._ignorableProps.contains(paramString)))
    {
      handleIgnoredProperty(paramJsonParser, paramDeserializationContext, paramObject, paramString);
      return;
    }
    if (this._anySetter != null) {
      try
      {
        this._anySetter.deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject, paramString);
        return;
      }
      catch (Exception paramJsonParser)
      {
        wrapAndThrow(paramJsonParser, paramObject, paramString, paramDeserializationContext);
        return;
      }
    }
    handleUnknownProperty(paramJsonParser, paramDeserializationContext, paramObject, paramString);
  }
  
  public Class<?> handledType()
  {
    return this._beanType.getRawClass();
  }
  
  public boolean hasProperty(String paramString)
  {
    return this._beanProperties.find(paramString) != null;
  }
  
  public boolean hasViews()
  {
    return this._needViewProcesing;
  }
  
  protected void injectValues(DeserializationContext paramDeserializationContext, Object paramObject)
    throws IOException, JsonProcessingException
  {
    ValueInjector[] arrayOfValueInjector = this._injectables;
    int j = arrayOfValueInjector.length;
    int i = 0;
    while (i < j)
    {
      arrayOfValueInjector[i].inject(paramDeserializationContext, paramObject);
      i += 1;
    }
  }
  
  public boolean isCachable()
  {
    return true;
  }
  
  public Iterator<SettableBeanProperty> properties()
  {
    if (this._beanProperties == null) {
      throw new IllegalStateException("Can only call after BeanDeserializer has been resolved");
    }
    return this._beanProperties.iterator();
  }
  
  public void replaceProperty(SettableBeanProperty paramSettableBeanProperty1, SettableBeanProperty paramSettableBeanProperty2)
  {
    this._beanProperties.replace(paramSettableBeanProperty2);
  }
  
  public void resolve(DeserializationContext paramDeserializationContext)
    throws JsonMappingException
  {
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject4;
    Object localObject5;
    if (this._valueInstantiator.canCreateFromObjectWith())
    {
      localObject1 = this._valueInstantiator.getFromObjectArguments(paramDeserializationContext.getConfig());
      this._propertyBasedCreator = PropertyBasedCreator.construct(paramDeserializationContext, this._valueInstantiator, (SettableBeanProperty[])localObject1);
      localObject3 = this._propertyBasedCreator.properties().iterator();
      for (;;)
      {
        localObject1 = localObject2;
        if (!((Iterator)localObject3).hasNext()) {
          break;
        }
        localObject4 = (SettableBeanProperty)((Iterator)localObject3).next();
        if (((SettableBeanProperty)localObject4).hasValueTypeDeserializer())
        {
          localObject5 = ((SettableBeanProperty)localObject4).getValueTypeDeserializer();
          if (((TypeDeserializer)localObject5).getTypeInclusion() == JsonTypeInfo.As.EXTERNAL_PROPERTY)
          {
            localObject1 = localObject2;
            if (localObject2 == null) {
              localObject1 = new ExternalTypeHandler.Builder();
            }
            ((ExternalTypeHandler.Builder)localObject1).addExternal((SettableBeanProperty)localObject4, (TypeDeserializer)localObject5);
            localObject2 = localObject1;
          }
        }
      }
    }
    localObject2 = null;
    Iterator localIterator = this._beanProperties.iterator();
    Object localObject3 = localObject1;
    while (localIterator.hasNext())
    {
      localObject5 = (SettableBeanProperty)localIterator.next();
      localObject4 = localObject5;
      JsonDeserializer localJsonDeserializer1;
      if (!((SettableBeanProperty)localObject4).hasValueDeserializer())
      {
        localJsonDeserializer1 = findConvertingDeserializer(paramDeserializationContext, (SettableBeanProperty)localObject4);
        localObject1 = localJsonDeserializer1;
        if (localJsonDeserializer1 == null) {
          localObject1 = findDeserializer(paramDeserializationContext, ((SettableBeanProperty)localObject4).getType(), (BeanProperty)localObject4);
        }
        localObject1 = ((SettableBeanProperty)localObject4).withValueDeserializer((JsonDeserializer)localObject1);
      }
      for (;;)
      {
        localObject4 = _resolveManagedReferenceProperty(paramDeserializationContext, (SettableBeanProperty)localObject1);
        localObject1 = localObject4;
        if (!(localObject4 instanceof ManagedReferenceProperty)) {
          localObject1 = _resolvedObjectIdProperty(paramDeserializationContext, (SettableBeanProperty)localObject4);
        }
        localObject4 = _resolveUnwrappedProperty(paramDeserializationContext, (SettableBeanProperty)localObject1);
        if (localObject4 == null) {
          break label346;
        }
        localObject1 = localObject2;
        if (localObject2 == null) {
          localObject1 = new UnwrappedPropertyHandler();
        }
        ((UnwrappedPropertyHandler)localObject1).addProperty((SettableBeanProperty)localObject4);
        localObject2 = localObject1;
        if (localObject4 == localObject5) {
          break;
        }
        this._beanProperties.replace((SettableBeanProperty)localObject4);
        localObject2 = localObject1;
        break;
        localJsonDeserializer1 = ((SettableBeanProperty)localObject4).getValueDeserializer();
        JsonDeserializer localJsonDeserializer2 = paramDeserializationContext.handlePrimaryContextualization(localJsonDeserializer1, (BeanProperty)localObject4);
        localObject1 = localObject4;
        if (localJsonDeserializer2 != localJsonDeserializer1) {
          localObject1 = ((SettableBeanProperty)localObject4).withValueDeserializer(localJsonDeserializer2);
        }
      }
      label346:
      localObject4 = _resolveInnerClassValuedProperty(paramDeserializationContext, (SettableBeanProperty)localObject1);
      if (localObject4 != localObject5) {
        this._beanProperties.replace((SettableBeanProperty)localObject4);
      }
      if (((SettableBeanProperty)localObject4).hasValueTypeDeserializer())
      {
        localObject5 = ((SettableBeanProperty)localObject4).getValueTypeDeserializer();
        if (((TypeDeserializer)localObject5).getTypeInclusion() == JsonTypeInfo.As.EXTERNAL_PROPERTY)
        {
          localObject1 = localObject3;
          if (localObject3 == null) {
            localObject1 = new ExternalTypeHandler.Builder();
          }
          ((ExternalTypeHandler.Builder)localObject1).addExternal((SettableBeanProperty)localObject4, (TypeDeserializer)localObject5);
          this._beanProperties.remove((SettableBeanProperty)localObject4);
          localObject3 = localObject1;
        }
      }
    }
    if ((this._anySetter != null) && (!this._anySetter.hasValueDeserializer())) {
      this._anySetter = this._anySetter.withValueDeserializer(findDeserializer(paramDeserializationContext, this._anySetter.getType(), this._anySetter.getProperty()));
    }
    if (this._valueInstantiator.canCreateUsingDelegate())
    {
      localObject1 = this._valueInstantiator.getDelegateType(paramDeserializationContext.getConfig());
      if (localObject1 == null) {
        throw new IllegalArgumentException("Invalid delegate-creator definition for " + this._beanType + ": value instantiator (" + this._valueInstantiator.getClass().getName() + ") returned true for 'canCreateUsingDelegate()', but null for 'getDelegateType()'");
      }
      localObject4 = this._valueInstantiator.getDelegateCreator();
      this._delegateDeserializer = findDeserializer(paramDeserializationContext, (JavaType)localObject1, new BeanProperty.Std(TEMP_PROPERTY_NAME, (JavaType)localObject1, null, this._classAnnotations, (AnnotatedMember)localObject4, PropertyMetadata.STD_OPTIONAL));
    }
    if (localObject3 != null)
    {
      this._externalTypeIdHandler = ((ExternalTypeHandler.Builder)localObject3).build();
      this._nonStandardCreation = true;
    }
    this._unwrappedPropertyHandler = ((UnwrappedPropertyHandler)localObject2);
    if (localObject2 != null) {
      this._nonStandardCreation = true;
    }
    if ((this._vanillaProcessing) && (!this._nonStandardCreation)) {}
    for (boolean bool = true;; bool = false)
    {
      this._vanillaProcessing = bool;
      return;
    }
  }
  
  public abstract JsonDeserializer<Object> unwrappingDeserializer(NameTransformer paramNameTransformer);
  
  public abstract BeanDeserializerBase withIgnorableProperties(HashSet<String> paramHashSet);
  
  public abstract BeanDeserializerBase withObjectIdReader(ObjectIdReader paramObjectIdReader);
  
  public void wrapAndThrow(Throwable paramThrowable, Object paramObject, int paramInt, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    throw JsonMappingException.wrapWithPath(throwOrReturnThrowable(paramThrowable, paramDeserializationContext), paramObject, paramInt);
  }
  
  public void wrapAndThrow(Throwable paramThrowable, Object paramObject, String paramString, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    throw JsonMappingException.wrapWithPath(throwOrReturnThrowable(paramThrowable, paramDeserializationContext), paramObject, paramString);
  }
  
  protected void wrapInstantiationProblem(Throwable paramThrowable, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    while (((paramThrowable instanceof InvocationTargetException)) && (paramThrowable.getCause() != null)) {
      paramThrowable = paramThrowable.getCause();
    }
    if ((paramThrowable instanceof Error)) {
      throw ((Error)paramThrowable);
    }
    if ((paramDeserializationContext == null) || (paramDeserializationContext.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS))) {}
    for (int i = 1; (paramThrowable instanceof IOException); i = 0) {
      throw ((IOException)paramThrowable);
    }
    if ((i == 0) && ((paramThrowable instanceof RuntimeException))) {
      throw ((RuntimeException)paramThrowable);
    }
    throw paramDeserializationContext.instantiationException(this._beanType.getRawClass(), paramThrowable);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/BeanDeserializerBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */