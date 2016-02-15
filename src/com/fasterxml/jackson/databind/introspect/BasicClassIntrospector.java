package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder.Value;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.type.SimpleType;
import java.io.Serializable;

public class BasicClassIntrospector
  extends ClassIntrospector
  implements Serializable
{
  protected static final BasicBeanDescription BOOLEAN_DESC;
  protected static final BasicBeanDescription INT_DESC;
  protected static final BasicBeanDescription LONG_DESC;
  protected static final BasicBeanDescription STRING_DESC;
  public static final BasicClassIntrospector instance = new BasicClassIntrospector();
  private static final long serialVersionUID = 1L;
  
  static
  {
    AnnotatedClass localAnnotatedClass = AnnotatedClass.constructWithoutSuperTypes(String.class, null, null);
    STRING_DESC = BasicBeanDescription.forOtherUse(null, SimpleType.constructUnsafe(String.class), localAnnotatedClass);
    localAnnotatedClass = AnnotatedClass.constructWithoutSuperTypes(Boolean.TYPE, null, null);
    BOOLEAN_DESC = BasicBeanDescription.forOtherUse(null, SimpleType.constructUnsafe(Boolean.TYPE), localAnnotatedClass);
    localAnnotatedClass = AnnotatedClass.constructWithoutSuperTypes(Integer.TYPE, null, null);
    INT_DESC = BasicBeanDescription.forOtherUse(null, SimpleType.constructUnsafe(Integer.TYPE), localAnnotatedClass);
    localAnnotatedClass = AnnotatedClass.constructWithoutSuperTypes(Long.TYPE, null, null);
    LONG_DESC = BasicBeanDescription.forOtherUse(null, SimpleType.constructUnsafe(Long.TYPE), localAnnotatedClass);
  }
  
  protected BasicBeanDescription _findCachedDesc(JavaType paramJavaType)
  {
    paramJavaType = paramJavaType.getRawClass();
    if (paramJavaType == String.class) {
      return STRING_DESC;
    }
    if (paramJavaType == Boolean.TYPE) {
      return BOOLEAN_DESC;
    }
    if (paramJavaType == Integer.TYPE) {
      return INT_DESC;
    }
    if (paramJavaType == Long.TYPE) {
      return LONG_DESC;
    }
    return null;
  }
  
  protected POJOPropertiesCollector collectProperties(MapperConfig<?> paramMapperConfig, JavaType paramJavaType, ClassIntrospector.MixInResolver paramMixInResolver, boolean paramBoolean, String paramString)
  {
    boolean bool = paramMapperConfig.isAnnotationProcessingEnabled();
    Class localClass = paramJavaType.getRawClass();
    if (bool) {}
    for (AnnotationIntrospector localAnnotationIntrospector = paramMapperConfig.getAnnotationIntrospector();; localAnnotationIntrospector = null) {
      return constructPropertyCollector(paramMapperConfig, AnnotatedClass.construct(localClass, localAnnotationIntrospector, paramMixInResolver), paramJavaType, paramBoolean, paramString).collect();
    }
  }
  
  protected POJOPropertiesCollector collectPropertiesWithBuilder(MapperConfig<?> paramMapperConfig, JavaType paramJavaType, ClassIntrospector.MixInResolver paramMixInResolver, boolean paramBoolean)
  {
    AnnotationIntrospector localAnnotationIntrospector;
    AnnotatedClass localAnnotatedClass;
    if (paramMapperConfig.isAnnotationProcessingEnabled())
    {
      localAnnotationIntrospector = paramMapperConfig.getAnnotationIntrospector();
      localAnnotatedClass = AnnotatedClass.construct(paramJavaType.getRawClass(), localAnnotationIntrospector, paramMixInResolver);
      if (localAnnotationIntrospector != null) {
        break label60;
      }
      paramMixInResolver = null;
      label32:
      if (paramMixInResolver != null) {
        break label71;
      }
    }
    label60:
    label71:
    for (paramMixInResolver = "with";; paramMixInResolver = paramMixInResolver.withPrefix)
    {
      return constructPropertyCollector(paramMapperConfig, localAnnotatedClass, paramJavaType, paramBoolean, paramMixInResolver).collect();
      localAnnotationIntrospector = null;
      break;
      paramMixInResolver = localAnnotationIntrospector.findPOJOBuilderConfig(localAnnotatedClass);
      break label32;
    }
  }
  
  protected POJOPropertiesCollector constructPropertyCollector(MapperConfig<?> paramMapperConfig, AnnotatedClass paramAnnotatedClass, JavaType paramJavaType, boolean paramBoolean, String paramString)
  {
    return new POJOPropertiesCollector(paramMapperConfig, paramBoolean, paramJavaType, paramAnnotatedClass, paramString);
  }
  
  public BasicBeanDescription forClassAnnotations(MapperConfig<?> paramMapperConfig, JavaType paramJavaType, ClassIntrospector.MixInResolver paramMixInResolver)
  {
    boolean bool = paramMapperConfig.isAnnotationProcessingEnabled();
    Class localClass = paramJavaType.getRawClass();
    if (bool) {}
    for (AnnotationIntrospector localAnnotationIntrospector = paramMapperConfig.getAnnotationIntrospector();; localAnnotationIntrospector = null) {
      return BasicBeanDescription.forOtherUse(paramMapperConfig, paramJavaType, AnnotatedClass.construct(localClass, localAnnotationIntrospector, paramMixInResolver));
    }
  }
  
  public BasicBeanDescription forCreation(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, ClassIntrospector.MixInResolver paramMixInResolver)
  {
    BasicBeanDescription localBasicBeanDescription2 = _findCachedDesc(paramJavaType);
    BasicBeanDescription localBasicBeanDescription1 = localBasicBeanDescription2;
    if (localBasicBeanDescription2 == null) {
      localBasicBeanDescription1 = BasicBeanDescription.forDeserialization(collectProperties(paramDeserializationConfig, paramJavaType, paramMixInResolver, false, "set"));
    }
    return localBasicBeanDescription1;
  }
  
  public BasicBeanDescription forDeserialization(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, ClassIntrospector.MixInResolver paramMixInResolver)
  {
    BasicBeanDescription localBasicBeanDescription2 = _findCachedDesc(paramJavaType);
    BasicBeanDescription localBasicBeanDescription1 = localBasicBeanDescription2;
    if (localBasicBeanDescription2 == null) {
      localBasicBeanDescription1 = BasicBeanDescription.forDeserialization(collectProperties(paramDeserializationConfig, paramJavaType, paramMixInResolver, false, "set"));
    }
    return localBasicBeanDescription1;
  }
  
  public BasicBeanDescription forDeserializationWithBuilder(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, ClassIntrospector.MixInResolver paramMixInResolver)
  {
    return BasicBeanDescription.forDeserialization(collectPropertiesWithBuilder(paramDeserializationConfig, paramJavaType, paramMixInResolver, false));
  }
  
  public BasicBeanDescription forDirectClassAnnotations(MapperConfig<?> paramMapperConfig, JavaType paramJavaType, ClassIntrospector.MixInResolver paramMixInResolver)
  {
    boolean bool = paramMapperConfig.isAnnotationProcessingEnabled();
    AnnotationIntrospector localAnnotationIntrospector = paramMapperConfig.getAnnotationIntrospector();
    Class localClass = paramJavaType.getRawClass();
    if (bool) {}
    for (;;)
    {
      return BasicBeanDescription.forOtherUse(paramMapperConfig, paramJavaType, AnnotatedClass.constructWithoutSuperTypes(localClass, localAnnotationIntrospector, paramMixInResolver));
      localAnnotationIntrospector = null;
    }
  }
  
  public BasicBeanDescription forSerialization(SerializationConfig paramSerializationConfig, JavaType paramJavaType, ClassIntrospector.MixInResolver paramMixInResolver)
  {
    BasicBeanDescription localBasicBeanDescription2 = _findCachedDesc(paramJavaType);
    BasicBeanDescription localBasicBeanDescription1 = localBasicBeanDescription2;
    if (localBasicBeanDescription2 == null) {
      localBasicBeanDescription1 = BasicBeanDescription.forSerialization(collectProperties(paramSerializationConfig, paramJavaType, paramMixInResolver, true, "set"));
    }
    return localBasicBeanDescription1;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/introspect/BasicClassIntrospector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */