package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.databind.AbstractTypeResolver;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty.Std;
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
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.EnumResolver;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.PrintStream;
import java.io.Serializable;
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
  implements Serializable
{
  private static final Class<?> CLASS_CHAR_BUFFER;
  private static final Class<?> CLASS_ITERABLE;
  private static final Class<?> CLASS_OBJECT = Object.class;
  private static final Class<?> CLASS_STRING = String.class;
  protected static final PropertyName UNWRAPPED_CREATOR_PARAM_NAME;
  static final HashMap<String, Class<? extends Collection>> _collectionFallbacks;
  static final HashMap<String, Class<? extends Map>> _mapFallbacks;
  protected final DeserializerFactoryConfig _factoryConfig;
  
  static
  {
    CLASS_CHAR_BUFFER = CharSequence.class;
    CLASS_ITERABLE = Iterable.class;
    UNWRAPPED_CREATOR_PARAM_NAME = new PropertyName("@JsonUnwrapped");
    _mapFallbacks = new HashMap();
    _mapFallbacks.put(Map.class.getName(), LinkedHashMap.class);
    _mapFallbacks.put(ConcurrentMap.class.getName(), ConcurrentHashMap.class);
    _mapFallbacks.put(SortedMap.class.getName(), TreeMap.class);
    _mapFallbacks.put("java.util.NavigableMap", TreeMap.class);
    try
    {
      _mapFallbacks.put(ConcurrentNavigableMap.class.getName(), ConcurrentSkipListMap.class);
      _collectionFallbacks = new HashMap();
      _collectionFallbacks.put(Collection.class.getName(), ArrayList.class);
      _collectionFallbacks.put(List.class.getName(), ArrayList.class);
      _collectionFallbacks.put(Set.class.getName(), HashSet.class);
      _collectionFallbacks.put(SortedSet.class.getName(), TreeSet.class);
      _collectionFallbacks.put(Queue.class.getName(), LinkedList.class);
      _collectionFallbacks.put("java.util.Deque", LinkedList.class);
      _collectionFallbacks.put("java.util.NavigableSet", TreeSet.class);
      return;
    }
    catch (Throwable localThrowable)
    {
      for (;;)
      {
        System.err.println("Problems with (optional) types: " + localThrowable);
      }
    }
  }
  
  protected BasicDeserializerFactory(DeserializerFactoryConfig paramDeserializerFactoryConfig)
  {
    this._factoryConfig = paramDeserializerFactoryConfig;
  }
  
  private KeyDeserializer _createEnumKeyDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType)
    throws JsonMappingException
  {
    DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
    Object localObject1 = localDeserializationConfig.introspect(paramJavaType);
    paramDeserializationContext = findDeserializerFromAnnotation(paramDeserializationContext, ((BeanDescription)localObject1).getClassInfo());
    if (paramDeserializationContext != null) {
      return StdKeyDeserializers.constructDelegatingKeyDeserializer(localDeserializationConfig, paramJavaType, paramDeserializationContext);
    }
    paramDeserializationContext = paramJavaType.getRawClass();
    Object localObject2 = _findCustomEnumDeserializer(paramDeserializationContext, localDeserializationConfig, (BeanDescription)localObject1);
    if (localObject2 != null) {
      return StdKeyDeserializers.constructDelegatingKeyDeserializer(localDeserializationConfig, paramJavaType, (JsonDeserializer)localObject2);
    }
    paramJavaType = constructEnumResolver(paramDeserializationContext, localDeserializationConfig, ((BeanDescription)localObject1).findJsonValueMethod());
    localObject1 = ((BeanDescription)localObject1).getFactoryMethods().iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (AnnotatedMethod)((Iterator)localObject1).next();
      if (localDeserializationConfig.getAnnotationIntrospector().hasCreatorAnnotation((Annotated)localObject2))
      {
        if ((((AnnotatedMethod)localObject2).getParameterCount() == 1) && (((AnnotatedMethod)localObject2).getRawReturnType().isAssignableFrom(paramDeserializationContext)))
        {
          if (((AnnotatedMethod)localObject2).getGenericParameterType(0) != String.class) {
            throw new IllegalArgumentException("Parameter #0 type for factory method (" + localObject2 + ") not suitable, must be java.lang.String");
          }
          if (localDeserializationConfig.canOverrideAccessModifiers()) {
            ClassUtil.checkAndFixAccess(((AnnotatedMethod)localObject2).getMember());
          }
          return StdKeyDeserializers.constructEnumKeyDeserializer(paramJavaType, (AnnotatedMethod)localObject2);
        }
        throw new IllegalArgumentException("Unsuitable method (" + localObject2 + ") decorated with @JsonCreator (for Enum type " + paramDeserializationContext.getName() + ")");
      }
    }
    return StdKeyDeserializers.constructEnumKeyDeserializer(paramJavaType);
  }
  
  private ValueInstantiator _findStdValueInstantiator(DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription)
    throws JsonMappingException
  {
    if (paramBeanDescription.getBeanClass() == JsonLocation.class) {
      return new JsonLocationInstantiator();
    }
    return null;
  }
  
  private JavaType _mapAbstractType2(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType)
    throws JsonMappingException
  {
    Class localClass = paramJavaType.getRawClass();
    if (this._factoryConfig.hasAbstractTypeResolvers())
    {
      Iterator localIterator = this._factoryConfig.abstractTypeResolvers().iterator();
      while (localIterator.hasNext())
      {
        JavaType localJavaType = ((AbstractTypeResolver)localIterator.next()).findTypeMapping(paramDeserializationConfig, paramJavaType);
        if ((localJavaType != null) && (localJavaType.getRawClass() != localClass)) {
          return localJavaType;
        }
      }
    }
    return null;
  }
  
  protected void _addDeserializerConstructors(DeserializationContext paramDeserializationContext, BeanDescription paramBeanDescription, VisibilityChecker<?> paramVisibilityChecker, AnnotationIntrospector paramAnnotationIntrospector, CreatorCollector paramCreatorCollector)
    throws JsonMappingException
  {
    Object localObject1 = paramBeanDescription.findDefaultConstructor();
    if ((localObject1 != null) && ((!paramCreatorCollector.hasDefaultCreator()) || (paramAnnotationIntrospector.hasCreatorAnnotation((Annotated)localObject1)))) {
      paramCreatorCollector.setDefaultCreator((AnnotatedWithParams)localObject1);
    }
    PropertyName[] arrayOfPropertyName = null;
    Object localObject2 = null;
    Object localObject3 = paramBeanDescription.findProperties().iterator();
    Object localObject4;
    AnnotatedParameter localAnnotatedParameter;
    while (((Iterator)localObject3).hasNext())
    {
      localObject4 = (BeanPropertyDefinition)((Iterator)localObject3).next();
      if (((BeanPropertyDefinition)localObject4).getConstructorParameter() != null)
      {
        localAnnotatedParameter = ((BeanPropertyDefinition)localObject4).getConstructorParameter();
        localObject5 = localAnnotatedParameter.getOwner();
        if ((localObject5 instanceof AnnotatedConstructor))
        {
          localObject1 = localObject2;
          if (localObject2 == null)
          {
            localObject1 = (AnnotatedConstructor)localObject5;
            arrayOfPropertyName = new PropertyName[((AnnotatedConstructor)localObject1).getParameterCount()];
          }
          arrayOfPropertyName[localAnnotatedParameter.getIndex()] = ((BeanPropertyDefinition)localObject4).getFullName();
          localObject2 = localObject1;
        }
      }
    }
    Object localObject5 = paramBeanDescription.getConstructors().iterator();
    while (((Iterator)localObject5).hasNext())
    {
      AnnotatedConstructor localAnnotatedConstructor = (AnnotatedConstructor)((Iterator)localObject5).next();
      int i1 = localAnnotatedConstructor.getParameterCount();
      boolean bool1;
      label211:
      boolean bool2;
      if ((paramAnnotationIntrospector.hasCreatorAnnotation(localAnnotatedConstructor)) || (localAnnotatedConstructor == localObject2))
      {
        bool1 = true;
        bool2 = paramVisibilityChecker.isCreatorVisible(localAnnotatedConstructor);
        if (i1 != 1) {
          break label275;
        }
        if (localAnnotatedConstructor != localObject2) {
          break label269;
        }
      }
      label269:
      for (localObject1 = arrayOfPropertyName[0];; localObject1 = null)
      {
        _handleSingleArgumentConstructor(paramDeserializationContext, paramBeanDescription, paramVisibilityChecker, paramAnnotationIntrospector, paramCreatorCollector, localAnnotatedConstructor, bool1, bool2, (PropertyName)localObject1);
        break;
        bool1 = false;
        break label211;
      }
      label275:
      if ((bool1) || (bool2))
      {
        localObject3 = null;
        int m = 0;
        int k = 0;
        CreatorProperty[] arrayOfCreatorProperty = new CreatorProperty[i1];
        int j = 0;
        if (j < i1)
        {
          localAnnotatedParameter = localAnnotatedConstructor.getParameter(j);
          localObject1 = null;
          if (localAnnotatedConstructor == localObject2) {
            localObject1 = arrayOfPropertyName[j];
          }
          localObject4 = localObject1;
          if (localObject1 == null) {
            localObject4 = _findParamName(localAnnotatedParameter, paramAnnotationIntrospector);
          }
          localObject1 = paramAnnotationIntrospector.findInjectableValueId(localAnnotatedParameter);
          int i;
          int n;
          if ((localObject4 != null) && (((PropertyName)localObject4).hasSimpleName()))
          {
            i = m + 1;
            arrayOfCreatorProperty[j] = constructCreatorProperty(paramDeserializationContext, paramBeanDescription, (PropertyName)localObject4, j, localAnnotatedParameter, localObject1);
            localObject1 = localObject3;
            n = k;
          }
          for (;;)
          {
            j += 1;
            k = n;
            m = i;
            localObject3 = localObject1;
            break;
            if (localObject1 != null)
            {
              n = k + 1;
              arrayOfCreatorProperty[j] = constructCreatorProperty(paramDeserializationContext, paramBeanDescription, (PropertyName)localObject4, j, localAnnotatedParameter, localObject1);
              i = m;
              localObject1 = localObject3;
            }
            else if (paramAnnotationIntrospector.findUnwrappingNameTransformer(localAnnotatedParameter) != null)
            {
              arrayOfCreatorProperty[j] = constructCreatorProperty(paramDeserializationContext, paramBeanDescription, UNWRAPPED_CREATOR_PARAM_NAME, j, localAnnotatedParameter, null);
              i = m + 1;
              n = k;
              localObject1 = localObject3;
            }
            else
            {
              n = k;
              i = m;
              localObject1 = localObject3;
              if (localObject3 == null)
              {
                localObject1 = localAnnotatedParameter;
                n = k;
                i = m;
              }
            }
          }
        }
        if ((bool1) || (m > 0) || (k > 0)) {
          if (m + k == i1) {
            paramCreatorCollector.addPropertyCreator(localAnnotatedConstructor, arrayOfCreatorProperty);
          } else if ((m == 0) && (k + 1 == i1)) {
            paramCreatorCollector.addDelegatingCreator(localAnnotatedConstructor, arrayOfCreatorProperty);
          } else {
            paramCreatorCollector.addIncompeteParameter((AnnotatedParameter)localObject3);
          }
        }
      }
    }
  }
  
  protected void _addDeserializerFactoryMethods(DeserializationContext paramDeserializationContext, BeanDescription paramBeanDescription, VisibilityChecker<?> paramVisibilityChecker, AnnotationIntrospector paramAnnotationIntrospector, CreatorCollector paramCreatorCollector)
    throws JsonMappingException
  {
    DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
    Iterator localIterator = paramBeanDescription.getFactoryMethods().iterator();
    while (localIterator.hasNext())
    {
      AnnotatedMethod localAnnotatedMethod = (AnnotatedMethod)localIterator.next();
      boolean bool = paramAnnotationIntrospector.hasCreatorAnnotation(localAnnotatedMethod);
      int i1 = localAnnotatedMethod.getParameterCount();
      if (i1 == 0)
      {
        if (bool) {
          paramCreatorCollector.setDefaultCreator(localAnnotatedMethod);
        }
      }
      else
      {
        Object localObject2;
        Object localObject1;
        if (i1 == 1)
        {
          localObject2 = localAnnotatedMethod.getParameter(0);
          localObject1 = _findParamName((AnnotatedParameter)localObject2, paramAnnotationIntrospector);
          if (localObject1 == null) {}
          for (localObject1 = null;; localObject1 = ((PropertyName)localObject1).getSimpleName())
          {
            if ((paramAnnotationIntrospector.findInjectableValueId((AnnotatedMember)localObject2) != null) || ((localObject1 != null) && (((String)localObject1).length() != 0))) {
              break label170;
            }
            _handleSingleArgumentFactory(localDeserializationConfig, paramBeanDescription, paramVisibilityChecker, paramAnnotationIntrospector, paramCreatorCollector, localAnnotatedMethod, bool);
            break;
          }
        }
        if (paramAnnotationIntrospector.hasCreatorAnnotation(localAnnotatedMethod))
        {
          label170:
          localObject1 = null;
          CreatorProperty[] arrayOfCreatorProperty = new CreatorProperty[i1];
          int m = 0;
          int k = 0;
          int j = 0;
          if (j < i1)
          {
            AnnotatedParameter localAnnotatedParameter = localAnnotatedMethod.getParameter(j);
            localObject2 = _findParamName(localAnnotatedParameter, paramAnnotationIntrospector);
            Object localObject3 = paramAnnotationIntrospector.findInjectableValueId(localAnnotatedParameter);
            int i;
            int n;
            if ((localObject2 != null) && (((PropertyName)localObject2).hasSimpleName()))
            {
              i = m + 1;
              arrayOfCreatorProperty[j] = constructCreatorProperty(paramDeserializationContext, paramBeanDescription, (PropertyName)localObject2, j, localAnnotatedParameter, localObject3);
              localObject2 = localObject1;
              n = k;
            }
            for (;;)
            {
              j += 1;
              k = n;
              m = i;
              localObject1 = localObject2;
              break;
              if (localObject3 != null)
              {
                n = k + 1;
                arrayOfCreatorProperty[j] = constructCreatorProperty(paramDeserializationContext, paramBeanDescription, (PropertyName)localObject2, j, localAnnotatedParameter, localObject3);
                i = m;
                localObject2 = localObject1;
              }
              else if (paramAnnotationIntrospector.findUnwrappingNameTransformer(localAnnotatedParameter) != null)
              {
                arrayOfCreatorProperty[j] = constructCreatorProperty(paramDeserializationContext, paramBeanDescription, UNWRAPPED_CREATOR_PARAM_NAME, j, localAnnotatedParameter, null);
                i = m + 1;
                n = k;
                localObject2 = localObject1;
              }
              else
              {
                n = k;
                i = m;
                localObject2 = localObject1;
                if (localObject1 == null)
                {
                  localObject2 = localAnnotatedParameter;
                  n = k;
                  i = m;
                }
              }
            }
          }
          if ((bool) || (m > 0) || (k > 0)) {
            if (m + k == i1) {
              paramCreatorCollector.addPropertyCreator(localAnnotatedMethod, arrayOfCreatorProperty);
            } else if ((m == 0) && (k + 1 == i1)) {
              paramCreatorCollector.addDelegatingCreator(localAnnotatedMethod, arrayOfCreatorProperty);
            } else {
              throw new IllegalArgumentException("Argument #" + ((AnnotatedParameter)localObject1).getIndex() + " of factory method " + localAnnotatedMethod + " has no property name annotation; must have name when multiple-parameter constructor annotated as Creator");
            }
          }
        }
      }
    }
  }
  
  protected ValueInstantiator _constructDefaultValueInstantiator(DeserializationContext paramDeserializationContext, BeanDescription paramBeanDescription)
    throws JsonMappingException
  {
    CreatorCollector localCreatorCollector = new CreatorCollector(paramBeanDescription, paramDeserializationContext.canOverrideAccessModifiers());
    AnnotationIntrospector localAnnotationIntrospector = paramDeserializationContext.getAnnotationIntrospector();
    DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
    VisibilityChecker localVisibilityChecker = localDeserializationConfig.getDefaultVisibilityChecker();
    localVisibilityChecker = localAnnotationIntrospector.findAutoDetectVisibility(paramBeanDescription.getClassInfo(), localVisibilityChecker);
    _addDeserializerFactoryMethods(paramDeserializationContext, paramBeanDescription, localVisibilityChecker, localAnnotationIntrospector, localCreatorCollector);
    if (paramBeanDescription.getType().isConcrete()) {
      _addDeserializerConstructors(paramDeserializationContext, paramBeanDescription, localVisibilityChecker, localAnnotationIntrospector, localCreatorCollector);
    }
    return localCreatorCollector.constructValueInstantiator(localDeserializationConfig);
  }
  
  protected JsonDeserializer<?> _findCustomArrayDeserializer(ArrayType paramArrayType, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription, TypeDeserializer paramTypeDeserializer, JsonDeserializer<?> paramJsonDeserializer)
    throws JsonMappingException
  {
    Iterator localIterator = this._factoryConfig.deserializers().iterator();
    while (localIterator.hasNext())
    {
      JsonDeserializer localJsonDeserializer = ((Deserializers)localIterator.next()).findArrayDeserializer(paramArrayType, paramDeserializationConfig, paramBeanDescription, paramTypeDeserializer, paramJsonDeserializer);
      if (localJsonDeserializer != null) {
        return localJsonDeserializer;
      }
    }
    return null;
  }
  
  protected JsonDeserializer<?> _findCustomCollectionDeserializer(CollectionType paramCollectionType, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription, TypeDeserializer paramTypeDeserializer, JsonDeserializer<?> paramJsonDeserializer)
    throws JsonMappingException
  {
    Iterator localIterator = this._factoryConfig.deserializers().iterator();
    while (localIterator.hasNext())
    {
      JsonDeserializer localJsonDeserializer = ((Deserializers)localIterator.next()).findCollectionDeserializer(paramCollectionType, paramDeserializationConfig, paramBeanDescription, paramTypeDeserializer, paramJsonDeserializer);
      if (localJsonDeserializer != null) {
        return localJsonDeserializer;
      }
    }
    return null;
  }
  
  protected JsonDeserializer<?> _findCustomCollectionLikeDeserializer(CollectionLikeType paramCollectionLikeType, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription, TypeDeserializer paramTypeDeserializer, JsonDeserializer<?> paramJsonDeserializer)
    throws JsonMappingException
  {
    Iterator localIterator = this._factoryConfig.deserializers().iterator();
    while (localIterator.hasNext())
    {
      JsonDeserializer localJsonDeserializer = ((Deserializers)localIterator.next()).findCollectionLikeDeserializer(paramCollectionLikeType, paramDeserializationConfig, paramBeanDescription, paramTypeDeserializer, paramJsonDeserializer);
      if (localJsonDeserializer != null) {
        return localJsonDeserializer;
      }
    }
    return null;
  }
  
  protected JsonDeserializer<?> _findCustomEnumDeserializer(Class<?> paramClass, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription)
    throws JsonMappingException
  {
    Iterator localIterator = this._factoryConfig.deserializers().iterator();
    while (localIterator.hasNext())
    {
      JsonDeserializer localJsonDeserializer = ((Deserializers)localIterator.next()).findEnumDeserializer(paramClass, paramDeserializationConfig, paramBeanDescription);
      if (localJsonDeserializer != null) {
        return localJsonDeserializer;
      }
    }
    return null;
  }
  
  protected JsonDeserializer<?> _findCustomMapDeserializer(MapType paramMapType, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription, KeyDeserializer paramKeyDeserializer, TypeDeserializer paramTypeDeserializer, JsonDeserializer<?> paramJsonDeserializer)
    throws JsonMappingException
  {
    Iterator localIterator = this._factoryConfig.deserializers().iterator();
    while (localIterator.hasNext())
    {
      JsonDeserializer localJsonDeserializer = ((Deserializers)localIterator.next()).findMapDeserializer(paramMapType, paramDeserializationConfig, paramBeanDescription, paramKeyDeserializer, paramTypeDeserializer, paramJsonDeserializer);
      if (localJsonDeserializer != null) {
        return localJsonDeserializer;
      }
    }
    return null;
  }
  
  protected JsonDeserializer<?> _findCustomMapLikeDeserializer(MapLikeType paramMapLikeType, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription, KeyDeserializer paramKeyDeserializer, TypeDeserializer paramTypeDeserializer, JsonDeserializer<?> paramJsonDeserializer)
    throws JsonMappingException
  {
    Iterator localIterator = this._factoryConfig.deserializers().iterator();
    while (localIterator.hasNext())
    {
      JsonDeserializer localJsonDeserializer = ((Deserializers)localIterator.next()).findMapLikeDeserializer(paramMapLikeType, paramDeserializationConfig, paramBeanDescription, paramKeyDeserializer, paramTypeDeserializer, paramJsonDeserializer);
      if (localJsonDeserializer != null) {
        return localJsonDeserializer;
      }
    }
    return null;
  }
  
  protected JsonDeserializer<?> _findCustomTreeNodeDeserializer(Class<? extends JsonNode> paramClass, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription)
    throws JsonMappingException
  {
    Iterator localIterator = this._factoryConfig.deserializers().iterator();
    while (localIterator.hasNext())
    {
      JsonDeserializer localJsonDeserializer = ((Deserializers)localIterator.next()).findTreeNodeDeserializer(paramClass, paramDeserializationConfig, paramBeanDescription);
      if (localJsonDeserializer != null) {
        return localJsonDeserializer;
      }
    }
    return null;
  }
  
  protected AnnotatedMethod _findJsonValueFor(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType)
  {
    if (paramJavaType == null) {
      return null;
    }
    return paramDeserializationConfig.introspect(paramJavaType).findJsonValueMethod();
  }
  
  protected PropertyName _findParamName(AnnotatedParameter paramAnnotatedParameter, AnnotationIntrospector paramAnnotationIntrospector)
  {
    if ((paramAnnotatedParameter != null) && (paramAnnotationIntrospector != null))
    {
      PropertyName localPropertyName = paramAnnotationIntrospector.findNameForDeserialization(paramAnnotatedParameter);
      if (localPropertyName != null) {
        return localPropertyName;
      }
      paramAnnotatedParameter = paramAnnotationIntrospector.findImplicitPropertyName(paramAnnotatedParameter);
      if ((paramAnnotatedParameter != null) && (!paramAnnotatedParameter.isEmpty())) {
        return new PropertyName(paramAnnotatedParameter);
      }
    }
    return null;
  }
  
  protected boolean _handleSingleArgumentConstructor(DeserializationContext paramDeserializationContext, BeanDescription paramBeanDescription, VisibilityChecker<?> paramVisibilityChecker, AnnotationIntrospector paramAnnotationIntrospector, CreatorCollector paramCreatorCollector, AnnotatedConstructor paramAnnotatedConstructor, boolean paramBoolean1, boolean paramBoolean2, PropertyName paramPropertyName)
    throws JsonMappingException
  {
    AnnotatedParameter localAnnotatedParameter = paramAnnotatedConstructor.getParameter(0);
    paramVisibilityChecker = paramPropertyName;
    if (paramPropertyName == null) {
      paramVisibilityChecker = _findParamName(localAnnotatedParameter, paramAnnotationIntrospector);
    }
    paramAnnotationIntrospector = paramAnnotationIntrospector.findInjectableValueId(localAnnotatedParameter);
    if ((paramAnnotationIntrospector != null) || ((paramVisibilityChecker != null) && (paramVisibilityChecker.hasSimpleName())))
    {
      paramCreatorCollector.addPropertyCreator(paramAnnotatedConstructor, new CreatorProperty[] { constructCreatorProperty(paramDeserializationContext, paramBeanDescription, paramVisibilityChecker, 0, localAnnotatedParameter, paramAnnotationIntrospector) });
      return true;
    }
    paramDeserializationContext = paramAnnotatedConstructor.getRawParameterType(0);
    if (paramDeserializationContext == String.class)
    {
      if ((paramBoolean1) || (paramBoolean2)) {
        paramCreatorCollector.addStringCreator(paramAnnotatedConstructor);
      }
      return true;
    }
    if ((paramDeserializationContext == Integer.TYPE) || (paramDeserializationContext == Integer.class))
    {
      if ((paramBoolean1) || (paramBoolean2)) {
        paramCreatorCollector.addIntCreator(paramAnnotatedConstructor);
      }
      return true;
    }
    if ((paramDeserializationContext == Long.TYPE) || (paramDeserializationContext == Long.class))
    {
      if ((paramBoolean1) || (paramBoolean2)) {
        paramCreatorCollector.addLongCreator(paramAnnotatedConstructor);
      }
      return true;
    }
    if ((paramDeserializationContext == Double.TYPE) || (paramDeserializationContext == Double.class))
    {
      if ((paramBoolean1) || (paramBoolean2)) {
        paramCreatorCollector.addDoubleCreator(paramAnnotatedConstructor);
      }
      return true;
    }
    if ((paramDeserializationContext == Boolean.TYPE) || (paramDeserializationContext == Boolean.class))
    {
      if ((paramBoolean1) || (paramBoolean2)) {
        paramCreatorCollector.addBooleanCreator(paramAnnotatedConstructor);
      }
      return true;
    }
    if (paramBoolean1)
    {
      paramCreatorCollector.addDelegatingCreator(paramAnnotatedConstructor, null);
      return true;
    }
    return false;
  }
  
  protected boolean _handleSingleArgumentFactory(DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription, VisibilityChecker<?> paramVisibilityChecker, AnnotationIntrospector paramAnnotationIntrospector, CreatorCollector paramCreatorCollector, AnnotatedMethod paramAnnotatedMethod, boolean paramBoolean)
    throws JsonMappingException
  {
    paramDeserializationConfig = paramAnnotatedMethod.getRawParameterType(0);
    if (paramDeserializationConfig == String.class) {
      if ((paramBoolean) || (paramVisibilityChecker.isCreatorVisible(paramAnnotatedMethod))) {
        paramCreatorCollector.addStringCreator(paramAnnotatedMethod);
      }
    }
    do
    {
      do
      {
        do
        {
          do
          {
            return true;
            if ((paramDeserializationConfig != Integer.TYPE) && (paramDeserializationConfig != Integer.class)) {
              break;
            }
          } while ((!paramBoolean) && (!paramVisibilityChecker.isCreatorVisible(paramAnnotatedMethod)));
          paramCreatorCollector.addIntCreator(paramAnnotatedMethod);
          return true;
          if ((paramDeserializationConfig != Long.TYPE) && (paramDeserializationConfig != Long.class)) {
            break;
          }
        } while ((!paramBoolean) && (!paramVisibilityChecker.isCreatorVisible(paramAnnotatedMethod)));
        paramCreatorCollector.addLongCreator(paramAnnotatedMethod);
        return true;
        if ((paramDeserializationConfig != Double.TYPE) && (paramDeserializationConfig != Double.class)) {
          break;
        }
      } while ((!paramBoolean) && (!paramVisibilityChecker.isCreatorVisible(paramAnnotatedMethod)));
      paramCreatorCollector.addDoubleCreator(paramAnnotatedMethod);
      return true;
      if ((paramDeserializationConfig != Boolean.TYPE) && (paramDeserializationConfig != Boolean.class)) {
        break;
      }
    } while ((!paramBoolean) && (!paramVisibilityChecker.isCreatorVisible(paramAnnotatedMethod)));
    paramCreatorCollector.addBooleanCreator(paramAnnotatedMethod);
    return true;
    if (paramAnnotationIntrospector.hasCreatorAnnotation(paramAnnotatedMethod))
    {
      paramCreatorCollector.addDelegatingCreator(paramAnnotatedMethod, null);
      return true;
    }
    return false;
  }
  
  protected CollectionType _mapAbstractCollectionType(JavaType paramJavaType, DeserializationConfig paramDeserializationConfig)
  {
    Class localClass = paramJavaType.getRawClass();
    localClass = (Class)_collectionFallbacks.get(localClass.getName());
    if (localClass == null) {
      return null;
    }
    return (CollectionType)paramDeserializationConfig.constructSpecializedType(paramJavaType, localClass);
  }
  
  public ValueInstantiator _valueInstantiatorInstance(DeserializationConfig paramDeserializationConfig, Annotated paramAnnotated, Object paramObject)
    throws JsonMappingException
  {
    if (paramObject == null) {
      return null;
    }
    if ((paramObject instanceof ValueInstantiator)) {
      return (ValueInstantiator)paramObject;
    }
    if (!(paramObject instanceof Class)) {
      throw new IllegalStateException("AnnotationIntrospector returned key deserializer definition of type " + paramObject.getClass().getName() + "; expected type KeyDeserializer or Class<KeyDeserializer> instead");
    }
    paramObject = (Class)paramObject;
    if (ClassUtil.isBogusClass((Class)paramObject)) {
      return null;
    }
    if (!ValueInstantiator.class.isAssignableFrom((Class)paramObject)) {
      throw new IllegalStateException("AnnotationIntrospector returned Class " + ((Class)paramObject).getName() + "; expected Class<ValueInstantiator>");
    }
    HandlerInstantiator localHandlerInstantiator = paramDeserializationConfig.getHandlerInstantiator();
    if (localHandlerInstantiator != null)
    {
      paramAnnotated = localHandlerInstantiator.valueInstantiatorInstance(paramDeserializationConfig, paramAnnotated, (Class)paramObject);
      if (paramAnnotated != null) {
        return paramAnnotated;
      }
    }
    return (ValueInstantiator)ClassUtil.createInstance((Class)paramObject, paramDeserializationConfig.canOverrideAccessModifiers());
  }
  
  protected CreatorProperty constructCreatorProperty(DeserializationContext paramDeserializationContext, BeanDescription paramBeanDescription, PropertyName paramPropertyName, int paramInt, AnnotatedParameter paramAnnotatedParameter, Object paramObject)
    throws JsonMappingException
  {
    DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
    Object localObject3 = paramDeserializationContext.getAnnotationIntrospector();
    Object localObject1;
    boolean bool;
    if (localObject3 == null)
    {
      localObject1 = null;
      if ((localObject1 == null) || (!((Boolean)localObject1).booleanValue())) {
        break label253;
      }
      bool = true;
      label36:
      if (localObject3 != null) {
        break label259;
      }
      localObject1 = null;
      label44:
      if (localObject3 != null) {
        break label271;
      }
    }
    label253:
    label259:
    label271:
    for (Object localObject2 = null;; localObject2 = ((AnnotationIntrospector)localObject3).findPropertyIndex(paramAnnotatedParameter))
    {
      PropertyMetadata localPropertyMetadata = PropertyMetadata.construct(bool, (String)localObject1, (Integer)localObject2);
      Object localObject4 = localDeserializationConfig.getTypeFactory().constructType(paramAnnotatedParameter.getParameterType(), paramBeanDescription.bindingsForBeanType());
      localObject2 = new BeanProperty.Std(paramPropertyName, (JavaType)localObject4, ((AnnotationIntrospector)localObject3).findWrapperName(paramAnnotatedParameter), paramBeanDescription.getClassAnnotations(), paramAnnotatedParameter, localPropertyMetadata);
      localObject3 = resolveType(paramDeserializationContext, paramBeanDescription, (JavaType)localObject4, paramAnnotatedParameter);
      localObject1 = localObject2;
      if (localObject3 != localObject4) {
        localObject1 = ((BeanProperty.Std)localObject2).withType((JavaType)localObject3);
      }
      localObject4 = findDeserializerFromAnnotation(paramDeserializationContext, paramAnnotatedParameter);
      JavaType localJavaType = modifyTypeByAnnotation(paramDeserializationContext, paramAnnotatedParameter, (JavaType)localObject3);
      localObject3 = (TypeDeserializer)localJavaType.getTypeHandler();
      localObject2 = localObject3;
      if (localObject3 == null) {
        localObject2 = findTypeDeserializer(localDeserializationConfig, localJavaType);
      }
      paramPropertyName = new CreatorProperty(paramPropertyName, localJavaType, ((BeanProperty.Std)localObject1).getWrapperName(), (TypeDeserializer)localObject2, paramBeanDescription.getClassAnnotations(), paramAnnotatedParameter, paramInt, paramObject, localPropertyMetadata);
      paramBeanDescription = paramPropertyName;
      if (localObject4 != null) {
        paramBeanDescription = paramPropertyName.withValueDeserializer(paramDeserializationContext.handlePrimaryContextualization((JsonDeserializer)localObject4, paramPropertyName));
      }
      return paramBeanDescription;
      localObject1 = ((AnnotationIntrospector)localObject3).hasRequiredMarker(paramAnnotatedParameter);
      break;
      bool = false;
      break label36;
      localObject1 = ((AnnotationIntrospector)localObject3).findPropertyDescription(paramAnnotatedParameter);
      break label44;
    }
  }
  
  protected EnumResolver<?> constructEnumResolver(Class<?> paramClass, DeserializationConfig paramDeserializationConfig, AnnotatedMethod paramAnnotatedMethod)
  {
    if (paramAnnotatedMethod != null)
    {
      paramAnnotatedMethod = paramAnnotatedMethod.getAnnotated();
      if (paramDeserializationConfig.canOverrideAccessModifiers()) {
        ClassUtil.checkAndFixAccess(paramAnnotatedMethod);
      }
      return EnumResolver.constructUnsafeUsingMethod(paramClass, paramAnnotatedMethod);
    }
    if (paramDeserializationConfig.isEnabled(DeserializationFeature.READ_ENUMS_USING_TO_STRING)) {
      return EnumResolver.constructUnsafeUsingToString(paramClass);
    }
    return EnumResolver.constructUnsafe(paramClass, paramDeserializationConfig.getAnnotationIntrospector());
  }
  
  public JsonDeserializer<?> createArrayDeserializer(DeserializationContext paramDeserializationContext, ArrayType paramArrayType, BeanDescription paramBeanDescription)
    throws JsonMappingException
  {
    DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
    JavaType localJavaType = paramArrayType.getContentType();
    JsonDeserializer localJsonDeserializer = (JsonDeserializer)localJavaType.getValueHandler();
    paramDeserializationContext = (TypeDeserializer)localJavaType.getTypeHandler();
    Object localObject1 = paramDeserializationContext;
    if (paramDeserializationContext == null) {
      localObject1 = findTypeDeserializer(localDeserializationConfig, localJavaType);
    }
    Object localObject2 = _findCustomArrayDeserializer(paramArrayType, localDeserializationConfig, paramBeanDescription, (TypeDeserializer)localObject1, localJsonDeserializer);
    paramDeserializationContext = (DeserializationContext)localObject2;
    if (localObject2 == null)
    {
      if (localJsonDeserializer == null)
      {
        paramDeserializationContext = localJavaType.getRawClass();
        if (localJavaType.isPrimitive()) {
          return PrimitiveArrayDeserializers.forType(paramDeserializationContext);
        }
        if (paramDeserializationContext == String.class) {
          return StringArrayDeserializer.instance;
        }
      }
      paramDeserializationContext = new ObjectArrayDeserializer(paramArrayType, localJsonDeserializer, (TypeDeserializer)localObject1);
    }
    localObject1 = paramDeserializationContext;
    if (this._factoryConfig.hasDeserializerModifiers())
    {
      localObject2 = this._factoryConfig.deserializerModifiers().iterator();
      for (;;)
      {
        localObject1 = paramDeserializationContext;
        if (!((Iterator)localObject2).hasNext()) {
          break;
        }
        paramDeserializationContext = ((BeanDeserializerModifier)((Iterator)localObject2).next()).modifyArrayDeserializer(localDeserializationConfig, paramArrayType, paramBeanDescription, paramDeserializationContext);
      }
    }
    return (JsonDeserializer<?>)localObject1;
  }
  
  public JsonDeserializer<?> createCollectionDeserializer(DeserializationContext paramDeserializationContext, CollectionType paramCollectionType, BeanDescription paramBeanDescription)
    throws JsonMappingException
  {
    JavaType localJavaType = paramCollectionType.getContentType();
    JsonDeserializer localJsonDeserializer = (JsonDeserializer)localJavaType.getValueHandler();
    DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
    Object localObject1 = (TypeDeserializer)localJavaType.getTypeHandler();
    Object localObject5 = localObject1;
    if (localObject1 == null) {
      localObject5 = findTypeDeserializer(localDeserializationConfig, localJavaType);
    }
    localObject1 = _findCustomCollectionDeserializer(paramCollectionType, localDeserializationConfig, paramBeanDescription, (TypeDeserializer)localObject5, localJsonDeserializer);
    Object localObject2 = localObject1;
    Object localObject3;
    if (localObject1 == null)
    {
      localObject3 = paramCollectionType.getRawClass();
      localObject2 = localObject1;
      if (localJsonDeserializer == null)
      {
        localObject2 = localObject1;
        if (EnumSet.class.isAssignableFrom((Class)localObject3)) {
          localObject2 = new EnumSetDeserializer(localJavaType, null);
        }
      }
    }
    localObject1 = localObject2;
    Object localObject6 = paramCollectionType;
    Object localObject7 = paramBeanDescription;
    BeanDescription localBeanDescription;
    if (localObject2 == null)
    {
      Object localObject4;
      if (!paramCollectionType.isInterface())
      {
        localObject4 = localObject2;
        localObject3 = paramCollectionType;
        localBeanDescription = paramBeanDescription;
        if (!paramCollectionType.isAbstract()) {}
      }
      else
      {
        localObject3 = _mapAbstractCollectionType(paramCollectionType, localDeserializationConfig);
        if (localObject3 != null) {
          break label275;
        }
        if (paramCollectionType.getTypeHandler() == null) {
          throw new IllegalArgumentException("Can not find a deserializer for non-concrete Collection type " + paramCollectionType);
        }
        localObject4 = AbstractDeserializer.constructForNonPOJO(paramBeanDescription);
        localBeanDescription = paramBeanDescription;
        localObject3 = paramCollectionType;
      }
      for (;;)
      {
        localObject1 = localObject4;
        localObject6 = localObject3;
        localObject7 = localBeanDescription;
        if (localObject4 != null) {
          break label323;
        }
        paramDeserializationContext = findValueInstantiator(paramDeserializationContext, localBeanDescription);
        if ((paramDeserializationContext.canCreateUsingDefault()) || (((CollectionType)localObject3).getRawClass() != ArrayBlockingQueue.class)) {
          break;
        }
        return new ArrayBlockingQueueDeserializer((JavaType)localObject3, localJsonDeserializer, (TypeDeserializer)localObject5, paramDeserializationContext, null);
        label275:
        localBeanDescription = localDeserializationConfig.introspectForCreation((JavaType)localObject3);
        localObject4 = localObject2;
      }
      if (localJavaType.getRawClass() != String.class) {
        break label386;
      }
      localObject1 = new StringCollectionDeserializer((JavaType)localObject3, localJsonDeserializer, paramDeserializationContext);
      localObject7 = localBeanDescription;
      localObject6 = localObject3;
    }
    for (;;)
    {
      label323:
      paramDeserializationContext = (DeserializationContext)localObject1;
      if (!this._factoryConfig.hasDeserializerModifiers()) {
        break;
      }
      paramCollectionType = this._factoryConfig.deserializerModifiers().iterator();
      for (;;)
      {
        paramDeserializationContext = (DeserializationContext)localObject1;
        if (!paramCollectionType.hasNext()) {
          break;
        }
        localObject1 = ((BeanDeserializerModifier)paramCollectionType.next()).modifyCollectionDeserializer(localDeserializationConfig, (CollectionType)localObject6, (BeanDescription)localObject7, (JsonDeserializer)localObject1);
      }
      label386:
      localObject1 = new CollectionDeserializer((JavaType)localObject3, localJsonDeserializer, (TypeDeserializer)localObject5, paramDeserializationContext);
      localObject6 = localObject3;
      localObject7 = localBeanDescription;
    }
    return paramDeserializationContext;
  }
  
  public JsonDeserializer<?> createCollectionLikeDeserializer(DeserializationContext paramDeserializationContext, CollectionLikeType paramCollectionLikeType, BeanDescription paramBeanDescription)
    throws JsonMappingException
  {
    JavaType localJavaType = paramCollectionLikeType.getContentType();
    Object localObject2 = (JsonDeserializer)localJavaType.getValueHandler();
    DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
    Object localObject1 = (TypeDeserializer)localJavaType.getTypeHandler();
    paramDeserializationContext = (DeserializationContext)localObject1;
    if (localObject1 == null) {
      paramDeserializationContext = findTypeDeserializer(localDeserializationConfig, localJavaType);
    }
    paramDeserializationContext = _findCustomCollectionLikeDeserializer(paramCollectionLikeType, localDeserializationConfig, paramBeanDescription, paramDeserializationContext, (JsonDeserializer)localObject2);
    localObject1 = paramDeserializationContext;
    if (paramDeserializationContext != null)
    {
      localObject1 = paramDeserializationContext;
      if (this._factoryConfig.hasDeserializerModifiers())
      {
        localObject2 = this._factoryConfig.deserializerModifiers().iterator();
        for (;;)
        {
          localObject1 = paramDeserializationContext;
          if (!((Iterator)localObject2).hasNext()) {
            break;
          }
          paramDeserializationContext = ((BeanDeserializerModifier)((Iterator)localObject2).next()).modifyCollectionLikeDeserializer(localDeserializationConfig, paramCollectionLikeType, paramBeanDescription, paramDeserializationContext);
        }
      }
    }
    return (JsonDeserializer<?>)localObject1;
  }
  
  public JsonDeserializer<?> createEnumDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType, BeanDescription paramBeanDescription)
    throws JsonMappingException
  {
    DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
    Class localClass = paramJavaType.getRawClass();
    JsonDeserializer localJsonDeserializer = _findCustomEnumDeserializer(localClass, localDeserializationConfig, paramBeanDescription);
    Object localObject1 = localJsonDeserializer;
    Object localObject2;
    if (localJsonDeserializer == null)
    {
      localObject1 = paramBeanDescription.getFactoryMethods().iterator();
      do
      {
        localObject2 = localJsonDeserializer;
        if (!((Iterator)localObject1).hasNext()) {
          break;
        }
        localObject2 = (AnnotatedMethod)((Iterator)localObject1).next();
      } while (!paramDeserializationContext.getAnnotationIntrospector().hasCreatorAnnotation((Annotated)localObject2));
      if ((((AnnotatedMethod)localObject2).getParameterCount() != 1) || (!((AnnotatedMethod)localObject2).getRawReturnType().isAssignableFrom(localClass))) {
        break label208;
      }
      localObject2 = EnumDeserializer.deserializerForCreator(localDeserializationConfig, localClass, (AnnotatedMethod)localObject2);
      localObject1 = localObject2;
      if (localObject2 == null) {
        localObject1 = new EnumDeserializer(constructEnumResolver(localClass, localDeserializationConfig, paramBeanDescription.findJsonValueMethod()));
      }
    }
    paramDeserializationContext = (DeserializationContext)localObject1;
    if (this._factoryConfig.hasDeserializerModifiers())
    {
      localObject2 = this._factoryConfig.deserializerModifiers().iterator();
      for (;;)
      {
        paramDeserializationContext = (DeserializationContext)localObject1;
        if (!((Iterator)localObject2).hasNext()) {
          break;
        }
        localObject1 = ((BeanDeserializerModifier)((Iterator)localObject2).next()).modifyEnumDeserializer(localDeserializationConfig, paramJavaType, paramBeanDescription, (JsonDeserializer)localObject1);
      }
      label208:
      throw new IllegalArgumentException("Unsuitable method (" + localObject2 + ") decorated with @JsonCreator (for Enum type " + localClass.getName() + ")");
    }
    return paramDeserializationContext;
  }
  
  public KeyDeserializer createKeyDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType)
    throws JsonMappingException
  {
    DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
    Object localObject1 = null;
    Object localObject2 = null;
    if (this._factoryConfig.hasKeyDeserializers())
    {
      BeanDescription localBeanDescription = localDeserializationConfig.introspectClassAnnotations(paramJavaType.getRawClass());
      Iterator localIterator = this._factoryConfig.keyDeserializers().iterator();
      localObject1 = localObject2;
      while (localIterator.hasNext())
      {
        localObject2 = ((KeyDeserializers)localIterator.next()).findKeyDeserializer(paramJavaType, localDeserializationConfig, localBeanDescription);
        localObject1 = localObject2;
        if (localObject2 != null) {
          localObject1 = localObject2;
        }
      }
    }
    localObject2 = localObject1;
    if (localObject1 == null)
    {
      if (paramJavaType.isEnumType()) {
        return _createEnumKeyDeserializer(paramDeserializationContext, paramJavaType);
      }
      localObject2 = StdKeyDeserializers.findStringBasedKeyDeserializer(localDeserializationConfig, paramJavaType);
    }
    paramDeserializationContext = (DeserializationContext)localObject2;
    if (localObject2 != null)
    {
      paramDeserializationContext = (DeserializationContext)localObject2;
      if (this._factoryConfig.hasDeserializerModifiers())
      {
        localObject1 = this._factoryConfig.deserializerModifiers().iterator();
        for (;;)
        {
          paramDeserializationContext = (DeserializationContext)localObject2;
          if (!((Iterator)localObject1).hasNext()) {
            break;
          }
          localObject2 = ((BeanDeserializerModifier)((Iterator)localObject1).next()).modifyKeyDeserializer(localDeserializationConfig, paramJavaType, (KeyDeserializer)localObject2);
        }
      }
    }
    return paramDeserializationContext;
  }
  
  public JsonDeserializer<?> createMapDeserializer(DeserializationContext paramDeserializationContext, MapType paramMapType, BeanDescription paramBeanDescription)
    throws JsonMappingException
  {
    DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
    Object localObject3 = paramMapType.getKeyType();
    Object localObject2 = paramMapType.getContentType();
    JsonDeserializer localJsonDeserializer = (JsonDeserializer)((JavaType)localObject2).getValueHandler();
    KeyDeserializer localKeyDeserializer = (KeyDeserializer)((JavaType)localObject3).getValueHandler();
    Object localObject1 = (TypeDeserializer)((JavaType)localObject2).getTypeHandler();
    Object localObject5 = localObject1;
    if (localObject1 == null) {
      localObject5 = findTypeDeserializer(localDeserializationConfig, (JavaType)localObject2);
    }
    localObject2 = _findCustomMapDeserializer(paramMapType, localDeserializationConfig, paramBeanDescription, localKeyDeserializer, (TypeDeserializer)localObject5, localJsonDeserializer);
    localObject1 = localObject2;
    Object localObject6 = paramMapType;
    Object localObject7 = paramBeanDescription;
    Object localObject4;
    MapType localMapType;
    if (localObject2 == null)
    {
      Class localClass = paramMapType.getRawClass();
      if (EnumMap.class.isAssignableFrom(localClass))
      {
        localObject1 = ((JavaType)localObject3).getRawClass();
        if ((localObject1 == null) || (!((Class)localObject1).isEnum())) {
          throw new IllegalArgumentException("Can not construct EnumMap; generic (key) type not available");
        }
        localObject2 = new EnumMapDeserializer(paramMapType, null, localJsonDeserializer, (TypeDeserializer)localObject5);
      }
      localObject1 = localObject2;
      localObject6 = paramMapType;
      localObject7 = paramBeanDescription;
      if (localObject2 == null) {
        if (!paramMapType.isInterface())
        {
          localObject4 = localObject2;
          localMapType = paramMapType;
          localObject3 = paramBeanDescription;
          if (!paramMapType.isAbstract()) {}
        }
        else
        {
          localObject1 = (Class)_mapFallbacks.get(localClass.getName());
          if (localObject1 == null) {
            break label377;
          }
          localMapType = (MapType)localDeserializationConfig.constructSpecializedType(paramMapType, (Class)localObject1);
          localObject3 = localDeserializationConfig.introspectForCreation(localMapType);
          localObject4 = localObject2;
        }
      }
    }
    for (;;)
    {
      localObject1 = localObject4;
      localObject6 = localMapType;
      localObject7 = localObject3;
      if (localObject4 == null)
      {
        localObject1 = new MapDeserializer(localMapType, findValueInstantiator(paramDeserializationContext, (BeanDescription)localObject3), localKeyDeserializer, localJsonDeserializer, (TypeDeserializer)localObject5);
        ((MapDeserializer)localObject1).setIgnorableProperties(localDeserializationConfig.getAnnotationIntrospector().findPropertiesToIgnore(((BeanDescription)localObject3).getClassInfo()));
        localObject7 = localObject3;
        localObject6 = localMapType;
      }
      paramDeserializationContext = (DeserializationContext)localObject1;
      if (!this._factoryConfig.hasDeserializerModifiers()) {
        break;
      }
      paramMapType = this._factoryConfig.deserializerModifiers().iterator();
      for (;;)
      {
        paramDeserializationContext = (DeserializationContext)localObject1;
        if (!paramMapType.hasNext()) {
          break;
        }
        localObject1 = ((BeanDeserializerModifier)paramMapType.next()).modifyMapDeserializer(localDeserializationConfig, (MapType)localObject6, (BeanDescription)localObject7, (JsonDeserializer)localObject1);
      }
      label377:
      if (paramMapType.getTypeHandler() == null) {
        throw new IllegalArgumentException("Can not find a deserializer for non-concrete Map type " + paramMapType);
      }
      localObject4 = AbstractDeserializer.constructForNonPOJO(paramBeanDescription);
      localMapType = paramMapType;
      localObject3 = paramBeanDescription;
    }
    return paramDeserializationContext;
  }
  
  public JsonDeserializer<?> createMapLikeDeserializer(DeserializationContext paramDeserializationContext, MapLikeType paramMapLikeType, BeanDescription paramBeanDescription)
    throws JsonMappingException
  {
    Object localObject1 = paramMapLikeType.getKeyType();
    Object localObject2 = paramMapLikeType.getContentType();
    DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
    JsonDeserializer localJsonDeserializer = (JsonDeserializer)((JavaType)localObject2).getValueHandler();
    KeyDeserializer localKeyDeserializer = (KeyDeserializer)((JavaType)localObject1).getValueHandler();
    localObject1 = (TypeDeserializer)((JavaType)localObject2).getTypeHandler();
    paramDeserializationContext = (DeserializationContext)localObject1;
    if (localObject1 == null) {
      paramDeserializationContext = findTypeDeserializer(localDeserializationConfig, (JavaType)localObject2);
    }
    paramDeserializationContext = _findCustomMapLikeDeserializer(paramMapLikeType, localDeserializationConfig, paramBeanDescription, localKeyDeserializer, paramDeserializationContext, localJsonDeserializer);
    localObject1 = paramDeserializationContext;
    if (paramDeserializationContext != null)
    {
      localObject1 = paramDeserializationContext;
      if (this._factoryConfig.hasDeserializerModifiers())
      {
        localObject2 = this._factoryConfig.deserializerModifiers().iterator();
        for (;;)
        {
          localObject1 = paramDeserializationContext;
          if (!((Iterator)localObject2).hasNext()) {
            break;
          }
          paramDeserializationContext = ((BeanDeserializerModifier)((Iterator)localObject2).next()).modifyMapLikeDeserializer(localDeserializationConfig, paramMapLikeType, paramBeanDescription, paramDeserializationContext);
        }
      }
    }
    return (JsonDeserializer<?>)localObject1;
  }
  
  public JsonDeserializer<?> createTreeDeserializer(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, BeanDescription paramBeanDescription)
    throws JsonMappingException
  {
    paramJavaType = paramJavaType.getRawClass();
    paramDeserializationConfig = _findCustomTreeNodeDeserializer(paramJavaType, paramDeserializationConfig, paramBeanDescription);
    if (paramDeserializationConfig != null) {
      return paramDeserializationConfig;
    }
    return JsonNodeDeserializer.getDeserializer(paramJavaType);
  }
  
  public JsonDeserializer<?> findDefaultDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType, BeanDescription paramBeanDescription)
    throws JsonMappingException
  {
    Object localObject = paramJavaType.getRawClass();
    if (localObject == CLASS_OBJECT) {
      paramJavaType = new UntypedObjectDeserializer();
    }
    do
    {
      return paramJavaType;
      if ((localObject == CLASS_STRING) || (localObject == CLASS_CHAR_BUFFER)) {
        return StringDeserializer.instance;
      }
      if (localObject == CLASS_ITERABLE)
      {
        localObject = paramDeserializationContext.getTypeFactory();
        if (paramJavaType.containedTypeCount() > 0) {}
        for (paramJavaType = paramJavaType.containedType(0);; paramJavaType = TypeFactory.unknownType()) {
          return createCollectionDeserializer(paramDeserializationContext, ((TypeFactory)localObject).constructCollectionType(Collection.class, paramJavaType), paramBeanDescription);
        }
      }
      paramBeanDescription = ((Class)localObject).getName();
      if ((!((Class)localObject).isPrimitive()) && (!paramBeanDescription.startsWith("java."))) {
        break;
      }
      paramJavaType = NumberDeserializers.find((Class)localObject, paramBeanDescription);
      paramDeserializationContext = paramJavaType;
      if (paramJavaType == null) {
        paramDeserializationContext = DateDeserializers.find((Class)localObject, paramBeanDescription);
      }
      paramJavaType = paramDeserializationContext;
    } while (paramDeserializationContext != null);
    if (localObject == TokenBuffer.class) {
      return new TokenBufferDeserializer();
    }
    return JdkDeserializers.find((Class)localObject, paramBeanDescription);
  }
  
  protected JsonDeserializer<Object> findDeserializerFromAnnotation(DeserializationContext paramDeserializationContext, Annotated paramAnnotated)
    throws JsonMappingException
  {
    Object localObject = paramDeserializationContext.getAnnotationIntrospector().findDeserializer(paramAnnotated);
    if (localObject == null) {
      return null;
    }
    return paramDeserializationContext.deserializerInstance(paramAnnotated, localObject);
  }
  
  public TypeDeserializer findPropertyContentTypeDeserializer(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, AnnotatedMember paramAnnotatedMember)
    throws JsonMappingException
  {
    AnnotationIntrospector localAnnotationIntrospector = paramDeserializationConfig.getAnnotationIntrospector();
    TypeResolverBuilder localTypeResolverBuilder = localAnnotationIntrospector.findPropertyContentTypeResolver(paramDeserializationConfig, paramAnnotatedMember, paramJavaType);
    paramJavaType = paramJavaType.getContentType();
    if (localTypeResolverBuilder == null) {
      return findTypeDeserializer(paramDeserializationConfig, paramJavaType);
    }
    return localTypeResolverBuilder.buildTypeDeserializer(paramDeserializationConfig, paramJavaType, paramDeserializationConfig.getSubtypeResolver().collectAndResolveSubtypes(paramAnnotatedMember, paramDeserializationConfig, localAnnotationIntrospector, paramJavaType));
  }
  
  public TypeDeserializer findPropertyTypeDeserializer(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, AnnotatedMember paramAnnotatedMember)
    throws JsonMappingException
  {
    AnnotationIntrospector localAnnotationIntrospector = paramDeserializationConfig.getAnnotationIntrospector();
    TypeResolverBuilder localTypeResolverBuilder = localAnnotationIntrospector.findPropertyTypeResolver(paramDeserializationConfig, paramAnnotatedMember, paramJavaType);
    if (localTypeResolverBuilder == null) {
      return findTypeDeserializer(paramDeserializationConfig, paramJavaType);
    }
    return localTypeResolverBuilder.buildTypeDeserializer(paramDeserializationConfig, paramJavaType, paramDeserializationConfig.getSubtypeResolver().collectAndResolveSubtypes(paramAnnotatedMember, paramDeserializationConfig, localAnnotationIntrospector, paramJavaType));
  }
  
  public TypeDeserializer findTypeDeserializer(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType)
    throws JsonMappingException
  {
    Object localObject2 = paramDeserializationConfig.introspectClassAnnotations(paramJavaType.getRawClass()).getClassInfo();
    Object localObject3 = paramDeserializationConfig.getAnnotationIntrospector();
    Object localObject1 = ((AnnotationIntrospector)localObject3).findTypeResolver(paramDeserializationConfig, (AnnotatedClass)localObject2, paramJavaType);
    Collection localCollection = null;
    if (localObject1 == null)
    {
      localObject2 = paramDeserializationConfig.getDefaultTyper(paramJavaType);
      localObject1 = localObject2;
      if (localObject2 == null) {
        return null;
      }
    }
    else
    {
      localCollection = paramDeserializationConfig.getSubtypeResolver().collectAndResolveSubtypes((AnnotatedClass)localObject2, paramDeserializationConfig, (AnnotationIntrospector)localObject3);
    }
    localObject2 = localObject1;
    if (((TypeResolverBuilder)localObject1).getDefaultImpl() == null)
    {
      localObject2 = localObject1;
      if (paramJavaType.isAbstract())
      {
        localObject3 = mapAbstractType(paramDeserializationConfig, paramJavaType);
        localObject2 = localObject1;
        if (localObject3 != null)
        {
          localObject2 = localObject1;
          if (((JavaType)localObject3).getRawClass() != paramJavaType.getRawClass()) {
            localObject2 = ((TypeResolverBuilder)localObject1).defaultImpl(((JavaType)localObject3).getRawClass());
          }
        }
      }
    }
    return ((TypeResolverBuilder)localObject2).buildTypeDeserializer(paramDeserializationConfig, paramJavaType, localCollection);
  }
  
  public ValueInstantiator findValueInstantiator(DeserializationContext paramDeserializationContext, BeanDescription paramBeanDescription)
    throws JsonMappingException
  {
    DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
    Object localObject2 = null;
    Object localObject1 = paramBeanDescription.getClassInfo();
    Object localObject3 = paramDeserializationContext.getAnnotationIntrospector().findValueInstantiator((AnnotatedClass)localObject1);
    if (localObject3 != null) {
      localObject2 = _valueInstantiatorInstance(localDeserializationConfig, (Annotated)localObject1, localObject3);
    }
    localObject1 = localObject2;
    if (localObject2 == null)
    {
      localObject2 = _findStdValueInstantiator(localDeserializationConfig, paramBeanDescription);
      localObject1 = localObject2;
      if (localObject2 == null) {
        localObject1 = _constructDefaultValueInstantiator(paramDeserializationContext, paramBeanDescription);
      }
    }
    paramDeserializationContext = (DeserializationContext)localObject1;
    if (this._factoryConfig.hasValueInstantiators())
    {
      localObject2 = this._factoryConfig.valueInstantiators().iterator();
      do
      {
        paramDeserializationContext = (DeserializationContext)localObject1;
        if (!((Iterator)localObject2).hasNext()) {
          break;
        }
        localObject3 = (ValueInstantiators)((Iterator)localObject2).next();
        paramDeserializationContext = ((ValueInstantiators)localObject3).findValueInstantiator(localDeserializationConfig, paramBeanDescription, (ValueInstantiator)localObject1);
        localObject1 = paramDeserializationContext;
      } while (paramDeserializationContext != null);
      throw new JsonMappingException("Broken registered ValueInstantiators (of type " + localObject3.getClass().getName() + "): returned null ValueInstantiator");
    }
    if (paramDeserializationContext.getIncompleteParameter() != null)
    {
      paramDeserializationContext = paramDeserializationContext.getIncompleteParameter();
      paramBeanDescription = paramDeserializationContext.getOwner();
      throw new IllegalArgumentException("Argument #" + paramDeserializationContext.getIndex() + " of constructor " + paramBeanDescription + " has no property name annotation; must have name when multiple-parameter constructor annotated as Creator");
    }
    return paramDeserializationContext;
  }
  
  public DeserializerFactoryConfig getFactoryConfig()
  {
    return this._factoryConfig;
  }
  
  public JavaType mapAbstractType(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType)
    throws JsonMappingException
  {
    for (;;)
    {
      JavaType localJavaType = _mapAbstractType2(paramDeserializationConfig, paramJavaType);
      if (localJavaType == null) {
        return paramJavaType;
      }
      Class localClass1 = paramJavaType.getRawClass();
      Class localClass2 = localJavaType.getRawClass();
      if ((localClass1 == localClass2) || (!localClass1.isAssignableFrom(localClass2))) {
        throw new IllegalArgumentException("Invalid abstract type resolution from " + paramJavaType + " to " + localJavaType + ": latter is not a subtype of former");
      }
      paramJavaType = localJavaType;
    }
  }
  
  /* Error */
  protected <T extends JavaType> T modifyTypeByAnnotation(DeserializationContext paramDeserializationContext, Annotated paramAnnotated, T paramT)
    throws JsonMappingException
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 415	com/fasterxml/jackson/databind/DeserializationContext:getAnnotationIntrospector	()Lcom/fasterxml/jackson/databind/AnnotationIntrospector;
    //   4: astore 6
    //   6: aload 6
    //   8: aload_2
    //   9: aload_3
    //   10: invokevirtual 1081	com/fasterxml/jackson/databind/AnnotationIntrospector:findDeserializationType	(Lcom/fasterxml/jackson/databind/introspect/Annotated;Lcom/fasterxml/jackson/databind/JavaType;)Ljava/lang/Class;
    //   13: astore 5
    //   15: aload_3
    //   16: astore 4
    //   18: aload 5
    //   20: ifnull +11 -> 31
    //   23: aload_3
    //   24: aload 5
    //   26: invokevirtual 1085	com/fasterxml/jackson/databind/JavaType:narrowBy	(Ljava/lang/Class;)Lcom/fasterxml/jackson/databind/JavaType;
    //   29: astore 4
    //   31: aload 4
    //   33: astore_3
    //   34: aload 4
    //   36: invokevirtual 1088	com/fasterxml/jackson/databind/JavaType:isContainerType	()Z
    //   39: ifeq +279 -> 318
    //   42: aload 6
    //   44: aload_2
    //   45: aload 4
    //   47: invokevirtual 1089	com/fasterxml/jackson/databind/JavaType:getKeyType	()Lcom/fasterxml/jackson/databind/JavaType;
    //   50: invokevirtual 1092	com/fasterxml/jackson/databind/AnnotationIntrospector:findDeserializationKeyType	(Lcom/fasterxml/jackson/databind/introspect/Annotated;Lcom/fasterxml/jackson/databind/JavaType;)Ljava/lang/Class;
    //   53: astore_3
    //   54: aload 4
    //   56: astore 5
    //   58: aload_3
    //   59: ifnull +128 -> 187
    //   62: aload 4
    //   64: instanceof 910
    //   67: ifne +109 -> 176
    //   70: new 144	com/fasterxml/jackson/databind/JsonMappingException
    //   73: dup
    //   74: new 117	java/lang/StringBuilder
    //   77: dup
    //   78: invokespecial 118	java/lang/StringBuilder:<init>	()V
    //   81: ldc_w 1094
    //   84: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   87: aload 4
    //   89: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   92: ldc_w 1096
    //   95: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   98: invokevirtual 130	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   101: invokespecial 1062	com/fasterxml/jackson/databind/JsonMappingException:<init>	(Ljava/lang/String;)V
    //   104: athrow
    //   105: astore_1
    //   106: new 144	com/fasterxml/jackson/databind/JsonMappingException
    //   109: dup
    //   110: new 117	java/lang/StringBuilder
    //   113: dup
    //   114: invokespecial 118	java/lang/StringBuilder:<init>	()V
    //   117: ldc_w 1098
    //   120: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   123: aload_3
    //   124: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   127: ldc_w 1100
    //   130: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   133: aload 5
    //   135: invokevirtual 65	java/lang/Class:getName	()Ljava/lang/String;
    //   138: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   141: ldc_w 1102
    //   144: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   147: aload_2
    //   148: invokevirtual 1105	com/fasterxml/jackson/databind/introspect/Annotated:getName	()Ljava/lang/String;
    //   151: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   154: ldc_w 1107
    //   157: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   160: aload_1
    //   161: invokevirtual 1110	java/lang/IllegalArgumentException:getMessage	()Ljava/lang/String;
    //   164: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   167: invokevirtual 130	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   170: aconst_null
    //   171: aload_1
    //   172: invokespecial 1113	com/fasterxml/jackson/databind/JsonMappingException:<init>	(Ljava/lang/String;Lcom/fasterxml/jackson/core/JsonLocation;Ljava/lang/Throwable;)V
    //   175: athrow
    //   176: aload 4
    //   178: checkcast 910	com/fasterxml/jackson/databind/type/MapLikeType
    //   181: aload_3
    //   182: invokevirtual 1116	com/fasterxml/jackson/databind/type/MapLikeType:narrowKey	(Ljava/lang/Class;)Lcom/fasterxml/jackson/databind/JavaType;
    //   185: astore 5
    //   187: aload 5
    //   189: invokevirtual 1089	com/fasterxml/jackson/databind/JavaType:getKeyType	()Lcom/fasterxml/jackson/databind/JavaType;
    //   192: astore 4
    //   194: aload 5
    //   196: astore_3
    //   197: aload 4
    //   199: ifnull +51 -> 250
    //   202: aload 5
    //   204: astore_3
    //   205: aload 4
    //   207: invokevirtual 702	com/fasterxml/jackson/databind/JavaType:getValueHandler	()Ljava/lang/Object;
    //   210: ifnonnull +40 -> 250
    //   213: aload_1
    //   214: aload_2
    //   215: aload 6
    //   217: aload_2
    //   218: invokevirtual 1118	com/fasterxml/jackson/databind/AnnotationIntrospector:findKeyDeserializer	(Lcom/fasterxml/jackson/databind/introspect/Annotated;)Ljava/lang/Object;
    //   221: invokevirtual 1122	com/fasterxml/jackson/databind/DeserializationContext:keyDeserializerInstance	(Lcom/fasterxml/jackson/databind/introspect/Annotated;Ljava/lang/Object;)Lcom/fasterxml/jackson/databind/KeyDeserializer;
    //   224: astore 4
    //   226: aload 5
    //   228: astore_3
    //   229: aload 4
    //   231: ifnull +19 -> 250
    //   234: aload 5
    //   236: checkcast 910	com/fasterxml/jackson/databind/type/MapLikeType
    //   239: aload 4
    //   241: invokevirtual 1126	com/fasterxml/jackson/databind/type/MapLikeType:withKeyValueHandler	(Ljava/lang/Object;)Lcom/fasterxml/jackson/databind/type/MapLikeType;
    //   244: astore_3
    //   245: aload_3
    //   246: invokevirtual 1089	com/fasterxml/jackson/databind/JavaType:getKeyType	()Lcom/fasterxml/jackson/databind/JavaType;
    //   249: pop
    //   250: aload 6
    //   252: aload_2
    //   253: aload_3
    //   254: invokevirtual 995	com/fasterxml/jackson/databind/JavaType:getContentType	()Lcom/fasterxml/jackson/databind/JavaType;
    //   257: invokevirtual 1129	com/fasterxml/jackson/databind/AnnotationIntrospector:findDeserializationContentType	(Lcom/fasterxml/jackson/databind/introspect/Annotated;Lcom/fasterxml/jackson/databind/JavaType;)Ljava/lang/Class;
    //   260: astore 5
    //   262: aload_3
    //   263: astore 4
    //   265: aload 5
    //   267: ifnull +11 -> 278
    //   270: aload_3
    //   271: aload 5
    //   273: invokevirtual 1132	com/fasterxml/jackson/databind/JavaType:narrowContentsBy	(Ljava/lang/Class;)Lcom/fasterxml/jackson/databind/JavaType;
    //   276: astore 4
    //   278: aload 4
    //   280: astore_3
    //   281: aload 4
    //   283: invokevirtual 995	com/fasterxml/jackson/databind/JavaType:getContentType	()Lcom/fasterxml/jackson/databind/JavaType;
    //   286: invokevirtual 702	com/fasterxml/jackson/databind/JavaType:getValueHandler	()Ljava/lang/Object;
    //   289: ifnonnull +29 -> 318
    //   292: aload_1
    //   293: aload_2
    //   294: aload 6
    //   296: aload_2
    //   297: invokevirtual 1135	com/fasterxml/jackson/databind/AnnotationIntrospector:findContentDeserializer	(Lcom/fasterxml/jackson/databind/introspect/Annotated;)Ljava/lang/Object;
    //   300: invokevirtual 987	com/fasterxml/jackson/databind/DeserializationContext:deserializerInstance	(Lcom/fasterxml/jackson/databind/introspect/Annotated;Ljava/lang/Object;)Lcom/fasterxml/jackson/databind/JsonDeserializer;
    //   303: astore_1
    //   304: aload 4
    //   306: astore_3
    //   307: aload_1
    //   308: ifnull +10 -> 318
    //   311: aload 4
    //   313: aload_1
    //   314: invokevirtual 1139	com/fasterxml/jackson/databind/JavaType:withContentValueHandler	(Ljava/lang/Object;)Lcom/fasterxml/jackson/databind/JavaType;
    //   317: astore_3
    //   318: aload_3
    //   319: areturn
    //   320: astore_1
    //   321: new 144	com/fasterxml/jackson/databind/JsonMappingException
    //   324: dup
    //   325: new 117	java/lang/StringBuilder
    //   328: dup
    //   329: invokespecial 118	java/lang/StringBuilder:<init>	()V
    //   332: ldc_w 1141
    //   335: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   338: aload 4
    //   340: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   343: ldc_w 1143
    //   346: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   349: aload_3
    //   350: invokevirtual 65	java/lang/Class:getName	()Ljava/lang/String;
    //   353: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   356: ldc_w 1145
    //   359: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   362: aload_1
    //   363: invokevirtual 1110	java/lang/IllegalArgumentException:getMessage	()Ljava/lang/String;
    //   366: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   369: invokevirtual 130	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   372: aconst_null
    //   373: aload_1
    //   374: invokespecial 1113	com/fasterxml/jackson/databind/JsonMappingException:<init>	(Ljava/lang/String;Lcom/fasterxml/jackson/core/JsonLocation;Ljava/lang/Throwable;)V
    //   377: athrow
    //   378: astore_1
    //   379: new 144	com/fasterxml/jackson/databind/JsonMappingException
    //   382: dup
    //   383: new 117	java/lang/StringBuilder
    //   386: dup
    //   387: invokespecial 118	java/lang/StringBuilder:<init>	()V
    //   390: ldc_w 1147
    //   393: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   396: aload_3
    //   397: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   400: ldc_w 1149
    //   403: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   406: aload 5
    //   408: invokevirtual 65	java/lang/Class:getName	()Ljava/lang/String;
    //   411: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   414: ldc_w 1145
    //   417: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   420: aload_1
    //   421: invokevirtual 1110	java/lang/IllegalArgumentException:getMessage	()Ljava/lang/String;
    //   424: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   427: invokevirtual 130	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   430: aconst_null
    //   431: aload_1
    //   432: invokespecial 1113	com/fasterxml/jackson/databind/JsonMappingException:<init>	(Ljava/lang/String;Lcom/fasterxml/jackson/core/JsonLocation;Ljava/lang/Throwable;)V
    //   435: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	436	0	this	BasicDeserializerFactory
    //   0	436	1	paramDeserializationContext	DeserializationContext
    //   0	436	2	paramAnnotated	Annotated
    //   0	436	3	paramT	T
    //   16	323	4	localObject1	Object
    //   13	394	5	localObject2	Object
    //   4	291	6	localAnnotationIntrospector	AnnotationIntrospector
    // Exception table:
    //   from	to	target	type
    //   23	31	105	java/lang/IllegalArgumentException
    //   176	187	320	java/lang/IllegalArgumentException
    //   270	278	378	java/lang/IllegalArgumentException
  }
  
  protected JavaType resolveType(DeserializationContext paramDeserializationContext, BeanDescription paramBeanDescription, JavaType paramJavaType, AnnotatedMember paramAnnotatedMember)
    throws JsonMappingException
  {
    paramBeanDescription = paramJavaType;
    if (paramJavaType.isContainerType())
    {
      Object localObject = paramDeserializationContext.getAnnotationIntrospector();
      paramBeanDescription = paramJavaType;
      if (paramJavaType.getKeyType() != null)
      {
        KeyDeserializer localKeyDeserializer = paramDeserializationContext.keyDeserializerInstance(paramAnnotatedMember, ((AnnotationIntrospector)localObject).findKeyDeserializer(paramAnnotatedMember));
        paramBeanDescription = paramJavaType;
        if (localKeyDeserializer != null)
        {
          paramBeanDescription = ((MapLikeType)paramJavaType).withKeyValueHandler(localKeyDeserializer);
          paramBeanDescription.getKeyType();
        }
      }
      localObject = paramDeserializationContext.deserializerInstance(paramAnnotatedMember, ((AnnotationIntrospector)localObject).findContentDeserializer(paramAnnotatedMember));
      paramJavaType = paramBeanDescription;
      if (localObject != null) {
        paramJavaType = paramBeanDescription.withContentValueHandler(localObject);
      }
      paramBeanDescription = paramJavaType;
      if ((paramAnnotatedMember instanceof AnnotatedMember))
      {
        localObject = findPropertyContentTypeDeserializer(paramDeserializationContext.getConfig(), paramJavaType, paramAnnotatedMember);
        paramBeanDescription = paramJavaType;
        if (localObject != null) {
          paramBeanDescription = paramJavaType.withContentTypeHandler(localObject);
        }
      }
    }
    if ((paramAnnotatedMember instanceof AnnotatedMember)) {}
    for (paramDeserializationContext = findPropertyTypeDeserializer(paramDeserializationContext.getConfig(), paramBeanDescription, paramAnnotatedMember);; paramDeserializationContext = findTypeDeserializer(paramDeserializationContext.getConfig(), paramBeanDescription))
    {
      paramJavaType = paramBeanDescription;
      if (paramDeserializationContext != null) {
        paramJavaType = paramBeanDescription.withTypeHandler(paramDeserializationContext);
      }
      return paramJavaType;
    }
  }
  
  public final DeserializerFactory withAbstractTypeResolver(AbstractTypeResolver paramAbstractTypeResolver)
  {
    return withConfig(this._factoryConfig.withAbstractTypeResolver(paramAbstractTypeResolver));
  }
  
  public final DeserializerFactory withAdditionalDeserializers(Deserializers paramDeserializers)
  {
    return withConfig(this._factoryConfig.withAdditionalDeserializers(paramDeserializers));
  }
  
  public final DeserializerFactory withAdditionalKeyDeserializers(KeyDeserializers paramKeyDeserializers)
  {
    return withConfig(this._factoryConfig.withAdditionalKeyDeserializers(paramKeyDeserializers));
  }
  
  protected abstract DeserializerFactory withConfig(DeserializerFactoryConfig paramDeserializerFactoryConfig);
  
  public final DeserializerFactory withDeserializerModifier(BeanDeserializerModifier paramBeanDeserializerModifier)
  {
    return withConfig(this._factoryConfig.withDeserializerModifier(paramBeanDeserializerModifier));
  }
  
  public final DeserializerFactory withValueInstantiators(ValueInstantiators paramValueInstantiators)
  {
    return withConfig(this._factoryConfig.withValueInstantiators(paramValueInstantiators));
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/BasicDeserializerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */