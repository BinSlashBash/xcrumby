package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Typing;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.ext.OptionalHandlerFactory;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BasicBeanDescription;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.impl.IndexedListSerializer;
import com.fasterxml.jackson.databind.ser.impl.IndexedStringListSerializer;
import com.fasterxml.jackson.databind.ser.impl.IteratorSerializer;
import com.fasterxml.jackson.databind.ser.impl.StringArraySerializer;
import com.fasterxml.jackson.databind.ser.impl.StringCollectionSerializer;
import com.fasterxml.jackson.databind.ser.std.BooleanSerializer;
import com.fasterxml.jackson.databind.ser.std.ByteBufferSerializer;
import com.fasterxml.jackson.databind.ser.std.CalendarSerializer;
import com.fasterxml.jackson.databind.ser.std.CollectionSerializer;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.ser.std.EnumMapSerializer;
import com.fasterxml.jackson.databind.ser.std.EnumSerializer;
import com.fasterxml.jackson.databind.ser.std.EnumSetSerializer;
import com.fasterxml.jackson.databind.ser.std.InetAddressSerializer;
import com.fasterxml.jackson.databind.ser.std.InetSocketAddressSerializer;
import com.fasterxml.jackson.databind.ser.std.IterableSerializer;
import com.fasterxml.jackson.databind.ser.std.JsonValueSerializer;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import com.fasterxml.jackson.databind.ser.std.NumberSerializer;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers;
import com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer;
import com.fasterxml.jackson.databind.ser.std.SerializableSerializer;
import com.fasterxml.jackson.databind.ser.std.SqlDateSerializer;
import com.fasterxml.jackson.databind.ser.std.SqlTimeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdArraySerializers;
import com.fasterxml.jackson.databind.ser.std.StdDelegatingSerializer;
import com.fasterxml.jackson.databind.ser.std.StdJdkSerializers;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.fasterxml.jackson.databind.ser.std.TimeZoneSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.ser.std.TokenBufferSerializer;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.databind.util.EnumValues;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.RandomAccess;
import java.util.TimeZone;

public abstract class BasicSerializerFactory
  extends SerializerFactory
  implements Serializable
{
  protected static final HashMap<String, JsonSerializer<?>> _concrete = new HashMap();
  protected static final HashMap<String, Class<? extends JsonSerializer<?>>> _concreteLazy = new HashMap();
  protected final SerializerFactoryConfig _factoryConfig;
  
  static
  {
    _concrete.put(String.class.getName(), new StringSerializer());
    Object localObject1 = ToStringSerializer.instance;
    _concrete.put(StringBuffer.class.getName(), localObject1);
    _concrete.put(StringBuilder.class.getName(), localObject1);
    _concrete.put(Character.class.getName(), localObject1);
    _concrete.put(Character.TYPE.getName(), localObject1);
    NumberSerializers.addAll(_concrete);
    _concrete.put(Boolean.TYPE.getName(), new BooleanSerializer(true));
    _concrete.put(Boolean.class.getName(), new BooleanSerializer(false));
    localObject1 = NumberSerializer.instance;
    _concrete.put(BigInteger.class.getName(), localObject1);
    _concrete.put(BigDecimal.class.getName(), localObject1);
    _concrete.put(Calendar.class.getName(), CalendarSerializer.instance);
    localObject1 = DateSerializer.instance;
    _concrete.put(java.util.Date.class.getName(), localObject1);
    _concrete.put(Timestamp.class.getName(), localObject1);
    _concreteLazy.put(java.sql.Date.class.getName(), SqlDateSerializer.class);
    _concreteLazy.put(Time.class.getName(), SqlTimeSerializer.class);
    localObject1 = StdJdkSerializers.all().iterator();
    while (((Iterator)localObject1).hasNext())
    {
      Map.Entry localEntry = (Map.Entry)((Iterator)localObject1).next();
      Object localObject2 = localEntry.getValue();
      if ((localObject2 instanceof JsonSerializer))
      {
        _concrete.put(((Class)localEntry.getKey()).getName(), (JsonSerializer)localObject2);
      }
      else if ((localObject2 instanceof Class))
      {
        localObject2 = (Class)localObject2;
        _concreteLazy.put(((Class)localEntry.getKey()).getName(), localObject2);
      }
      else
      {
        throw new IllegalStateException("Internal error: unrecognized value of type " + localEntry.getClass().getName());
      }
    }
    _concreteLazy.put(TokenBuffer.class.getName(), TokenBufferSerializer.class);
  }
  
  protected BasicSerializerFactory(SerializerFactoryConfig paramSerializerFactoryConfig)
  {
    SerializerFactoryConfig localSerializerFactoryConfig = paramSerializerFactoryConfig;
    if (paramSerializerFactoryConfig == null) {
      localSerializerFactoryConfig = new SerializerFactoryConfig();
    }
    this._factoryConfig = localSerializerFactoryConfig;
  }
  
  /* Error */
  protected static <T extends JavaType> T modifySecondaryTypesByAnnotation(SerializationConfig paramSerializationConfig, Annotated paramAnnotated, T paramT)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 179	com/fasterxml/jackson/databind/SerializationConfig:getAnnotationIntrospector	()Lcom/fasterxml/jackson/databind/AnnotationIntrospector;
    //   4: astore 4
    //   6: aload_2
    //   7: astore_3
    //   8: aload_2
    //   9: invokevirtual 184	com/fasterxml/jackson/databind/JavaType:isContainerType	()Z
    //   12: ifeq +91 -> 103
    //   15: aload 4
    //   17: aload_1
    //   18: aload_2
    //   19: invokevirtual 188	com/fasterxml/jackson/databind/JavaType:getKeyType	()Lcom/fasterxml/jackson/databind/JavaType;
    //   22: invokevirtual 194	com/fasterxml/jackson/databind/AnnotationIntrospector:findSerializationKeyType	(Lcom/fasterxml/jackson/databind/introspect/Annotated;Lcom/fasterxml/jackson/databind/JavaType;)Ljava/lang/Class;
    //   25: astore_3
    //   26: aload_2
    //   27: astore_0
    //   28: aload_3
    //   29: ifnull +51 -> 80
    //   32: aload_2
    //   33: instanceof 196
    //   36: ifne +35 -> 71
    //   39: new 173	java/lang/IllegalArgumentException
    //   42: dup
    //   43: new 51	java/lang/StringBuilder
    //   46: dup
    //   47: invokespecial 139	java/lang/StringBuilder:<init>	()V
    //   50: ldc -58
    //   52: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   55: aload_2
    //   56: invokevirtual 201	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   59: ldc -53
    //   61: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   64: invokevirtual 154	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   67: invokespecial 204	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   70: athrow
    //   71: aload_2
    //   72: checkcast 196	com/fasterxml/jackson/databind/type/MapType
    //   75: aload_3
    //   76: invokevirtual 208	com/fasterxml/jackson/databind/type/MapType:widenKey	(Ljava/lang/Class;)Lcom/fasterxml/jackson/databind/JavaType;
    //   79: astore_0
    //   80: aload 4
    //   82: aload_1
    //   83: aload_0
    //   84: invokevirtual 211	com/fasterxml/jackson/databind/JavaType:getContentType	()Lcom/fasterxml/jackson/databind/JavaType;
    //   87: invokevirtual 214	com/fasterxml/jackson/databind/AnnotationIntrospector:findSerializationContentType	(Lcom/fasterxml/jackson/databind/introspect/Annotated;Lcom/fasterxml/jackson/databind/JavaType;)Ljava/lang/Class;
    //   90: astore_1
    //   91: aload_0
    //   92: astore_3
    //   93: aload_1
    //   94: ifnull +9 -> 103
    //   97: aload_0
    //   98: aload_1
    //   99: invokevirtual 217	com/fasterxml/jackson/databind/JavaType:widenContentsBy	(Ljava/lang/Class;)Lcom/fasterxml/jackson/databind/JavaType;
    //   102: astore_3
    //   103: aload_3
    //   104: areturn
    //   105: astore_0
    //   106: new 173	java/lang/IllegalArgumentException
    //   109: dup
    //   110: new 51	java/lang/StringBuilder
    //   113: dup
    //   114: invokespecial 139	java/lang/StringBuilder:<init>	()V
    //   117: ldc -37
    //   119: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   122: aload_2
    //   123: invokevirtual 201	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   126: ldc -35
    //   128: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   131: aload_3
    //   132: invokevirtual 34	java/lang/Class:getName	()Ljava/lang/String;
    //   135: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   138: ldc -33
    //   140: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   143: aload_0
    //   144: invokevirtual 226	java/lang/IllegalArgumentException:getMessage	()Ljava/lang/String;
    //   147: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   150: invokevirtual 154	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   153: invokespecial 204	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   156: athrow
    //   157: astore_2
    //   158: new 173	java/lang/IllegalArgumentException
    //   161: dup
    //   162: new 51	java/lang/StringBuilder
    //   165: dup
    //   166: invokespecial 139	java/lang/StringBuilder:<init>	()V
    //   169: ldc -28
    //   171: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   174: aload_0
    //   175: invokevirtual 201	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   178: ldc -26
    //   180: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   183: aload_1
    //   184: invokevirtual 34	java/lang/Class:getName	()Ljava/lang/String;
    //   187: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   190: ldc -33
    //   192: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   195: aload_2
    //   196: invokevirtual 226	java/lang/IllegalArgumentException:getMessage	()Ljava/lang/String;
    //   199: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   202: invokevirtual 154	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   205: invokespecial 204	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   208: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	209	0	paramSerializationConfig	SerializationConfig
    //   0	209	1	paramAnnotated	Annotated
    //   0	209	2	paramT	T
    //   7	125	3	localObject	Object
    //   4	77	4	localAnnotationIntrospector	AnnotationIntrospector
    // Exception table:
    //   from	to	target	type
    //   71	80	105	java/lang/IllegalArgumentException
    //   97	103	157	java/lang/IllegalArgumentException
  }
  
  protected JsonSerializer<Object> _findContentSerializer(SerializerProvider paramSerializerProvider, Annotated paramAnnotated)
    throws JsonMappingException
  {
    Object localObject = paramSerializerProvider.getAnnotationIntrospector().findContentSerializer(paramAnnotated);
    if (localObject != null) {
      return paramSerializerProvider.serializerInstance(paramAnnotated, localObject);
    }
    return null;
  }
  
  protected JsonSerializer<Object> _findKeySerializer(SerializerProvider paramSerializerProvider, Annotated paramAnnotated)
    throws JsonMappingException
  {
    Object localObject = paramSerializerProvider.getAnnotationIntrospector().findKeySerializer(paramAnnotated);
    if (localObject != null) {
      return paramSerializerProvider.serializerInstance(paramAnnotated, localObject);
    }
    return null;
  }
  
  protected Class<?> _verifyAsClass(Object paramObject, String paramString, Class<?> paramClass)
  {
    if (paramObject == null) {
      paramObject = null;
    }
    do
    {
      return (Class<?>)paramObject;
      if (!(paramObject instanceof Class)) {
        throw new IllegalStateException("AnnotationIntrospector." + paramString + "() returned value of type " + paramObject.getClass().getName() + ": expected type JsonSerializer or Class<JsonSerializer> instead");
      }
      paramString = (Class)paramObject;
      if (paramString == paramClass) {
        break;
      }
      paramObject = paramString;
    } while (!ClassUtil.isBogusClass(paramString));
    return null;
  }
  
  protected JsonSerializer<?> buildArraySerializer(SerializationConfig paramSerializationConfig, ArrayType paramArrayType, BeanDescription paramBeanDescription, boolean paramBoolean, TypeSerializer paramTypeSerializer, JsonSerializer<Object> paramJsonSerializer)
    throws JsonMappingException
  {
    Object localObject2 = null;
    Iterator localIterator = customSerializers().iterator();
    while (localIterator.hasNext())
    {
      localObject1 = ((Serializers)localIterator.next()).findArraySerializer(paramSerializationConfig, paramArrayType, paramBeanDescription, paramTypeSerializer, paramJsonSerializer);
      localObject2 = localObject1;
      if (localObject1 != null) {
        localObject2 = localObject1;
      }
    }
    Object localObject1 = localObject2;
    if (localObject2 == null)
    {
      localObject1 = paramArrayType.getRawClass();
      if ((paramJsonSerializer == null) || (ClassUtil.isJacksonStdImpl(paramJsonSerializer))) {
        if (String[].class != localObject1) {
          break label195;
        }
      }
    }
    label195:
    for (localObject2 = StringArraySerializer.instance;; localObject2 = StdArraySerializers.findStandardImpl((Class)localObject1))
    {
      localObject1 = localObject2;
      if (localObject2 == null) {
        localObject1 = new ObjectArraySerializer(paramArrayType.getContentType(), paramBoolean, paramTypeSerializer, paramJsonSerializer);
      }
      paramTypeSerializer = (TypeSerializer)localObject1;
      if (!this._factoryConfig.hasSerializerModifiers()) {
        break;
      }
      paramJsonSerializer = this._factoryConfig.serializerModifiers().iterator();
      for (;;)
      {
        paramTypeSerializer = (TypeSerializer)localObject1;
        if (!paramJsonSerializer.hasNext()) {
          break;
        }
        localObject1 = ((BeanSerializerModifier)paramJsonSerializer.next()).modifyArraySerializer(paramSerializationConfig, paramArrayType, paramBeanDescription, (JsonSerializer)localObject1);
      }
    }
    return paramTypeSerializer;
  }
  
  protected JsonSerializer<?> buildCollectionSerializer(SerializationConfig paramSerializationConfig, CollectionType paramCollectionType, BeanDescription paramBeanDescription, boolean paramBoolean, TypeSerializer paramTypeSerializer, JsonSerializer<Object> paramJsonSerializer)
    throws JsonMappingException
  {
    Object localObject3 = null;
    Object localObject2 = customSerializers().iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject1 = ((Serializers)((Iterator)localObject2).next()).findCollectionSerializer(paramSerializationConfig, paramCollectionType, paramBeanDescription, paramTypeSerializer, paramJsonSerializer);
      localObject3 = localObject1;
      if (localObject1 != null) {
        localObject3 = localObject1;
      }
    }
    Object localObject1 = localObject3;
    if (localObject3 == null)
    {
      localObject1 = paramBeanDescription.findExpectedFormat(null);
      if ((localObject1 != null) && (((JsonFormat.Value)localObject1).getShape() == JsonFormat.Shape.OBJECT)) {
        return null;
      }
      localObject2 = paramCollectionType.getRawClass();
      if (EnumSet.class.isAssignableFrom((Class)localObject2))
      {
        paramJsonSerializer = paramCollectionType.getContentType();
        paramTypeSerializer = paramJsonSerializer;
        if (!paramJsonSerializer.isEnumType()) {
          paramTypeSerializer = null;
        }
        localObject1 = buildEnumSetSerializer(paramTypeSerializer);
      }
    }
    else
    {
      paramTypeSerializer = (TypeSerializer)localObject1;
      if (!this._factoryConfig.hasSerializerModifiers()) {
        break label336;
      }
      paramJsonSerializer = this._factoryConfig.serializerModifiers().iterator();
      for (;;)
      {
        paramTypeSerializer = (TypeSerializer)localObject1;
        if (!paramJsonSerializer.hasNext()) {
          break;
        }
        localObject1 = ((BeanSerializerModifier)paramJsonSerializer.next()).modifyCollectionSerializer(paramSerializationConfig, paramCollectionType, paramBeanDescription, (JsonSerializer)localObject1);
      }
    }
    localObject1 = paramCollectionType.getContentType().getRawClass();
    if (isIndexedList((Class)localObject2)) {
      if (localObject1 == String.class) {
        if (paramJsonSerializer != null)
        {
          localObject2 = localObject3;
          if (!ClassUtil.isJacksonStdImpl(paramJsonSerializer)) {}
        }
        else
        {
          localObject2 = IndexedStringListSerializer.instance;
        }
      }
    }
    for (;;)
    {
      localObject1 = localObject2;
      if (localObject2 != null) {
        break;
      }
      localObject1 = buildCollectionSerializer(paramCollectionType.getContentType(), paramBoolean, paramTypeSerializer, paramJsonSerializer);
      break;
      localObject2 = buildIndexedListSerializer(paramCollectionType.getContentType(), paramBoolean, paramTypeSerializer, paramJsonSerializer);
      continue;
      localObject2 = localObject3;
      if (localObject1 == String.class) {
        if (paramJsonSerializer != null)
        {
          localObject2 = localObject3;
          if (!ClassUtil.isJacksonStdImpl(paramJsonSerializer)) {}
        }
        else
        {
          localObject2 = StringCollectionSerializer.instance;
        }
      }
    }
    label336:
    return paramTypeSerializer;
  }
  
  public ContainerSerializer<?> buildCollectionSerializer(JavaType paramJavaType, boolean paramBoolean, TypeSerializer paramTypeSerializer, JsonSerializer<Object> paramJsonSerializer)
  {
    return new CollectionSerializer(paramJavaType, paramBoolean, paramTypeSerializer, null, paramJsonSerializer);
  }
  
  protected JsonSerializer<?> buildContainerSerializer(SerializerProvider paramSerializerProvider, JavaType paramJavaType, BeanDescription paramBeanDescription, boolean paramBoolean)
    throws JsonMappingException
  {
    SerializationConfig localSerializationConfig = paramSerializerProvider.getConfig();
    boolean bool = paramBoolean;
    if (!paramBoolean)
    {
      bool = paramBoolean;
      if (paramJavaType.useStaticType()) {
        if (paramJavaType.isContainerType())
        {
          bool = paramBoolean;
          if (paramJavaType.getContentType().getRawClass() == Object.class) {}
        }
        else
        {
          bool = true;
        }
      }
    }
    Object localObject2 = createTypeSerializer(localSerializationConfig, paramJavaType.getContentType());
    if (localObject2 != null) {
      bool = false;
    }
    JsonSerializer localJsonSerializer = _findContentSerializer(paramSerializerProvider, paramBeanDescription.getClassInfo());
    Object localObject1;
    if (paramJavaType.isMapLikeType())
    {
      localObject1 = (MapLikeType)paramJavaType;
      paramSerializerProvider = _findKeySerializer(paramSerializerProvider, paramBeanDescription.getClassInfo());
      if (((MapLikeType)localObject1).isTrueMapType()) {
        paramSerializerProvider = buildMapSerializer(localSerializationConfig, (MapType)localObject1, paramBeanDescription, bool, paramSerializerProvider, (TypeSerializer)localObject2, localJsonSerializer);
      }
    }
    do
    {
      MapLikeType localMapLikeType;
      do
      {
        return paramSerializerProvider;
        Iterator localIterator = customSerializers().iterator();
        do
        {
          if (!localIterator.hasNext()) {
            break;
          }
          localObject1 = (Serializers)localIterator.next();
          localMapLikeType = (MapLikeType)paramJavaType;
          localObject1 = ((Serializers)localObject1).findMapLikeSerializer(localSerializationConfig, localMapLikeType, paramBeanDescription, paramSerializerProvider, (TypeSerializer)localObject2, localJsonSerializer);
        } while (localObject1 == null);
        paramSerializerProvider = (SerializerProvider)localObject1;
      } while (!this._factoryConfig.hasSerializerModifiers());
      localObject2 = this._factoryConfig.serializerModifiers().iterator();
      for (paramJavaType = (JavaType)localObject1;; paramJavaType = ((BeanSerializerModifier)((Iterator)localObject2).next()).modifyMapLikeSerializer(localSerializationConfig, localMapLikeType, paramBeanDescription, paramJavaType))
      {
        paramSerializerProvider = paramJavaType;
        if (!((Iterator)localObject2).hasNext()) {
          break;
        }
      }
      return null;
      if (!paramJavaType.isCollectionLikeType()) {
        break label419;
      }
      paramSerializerProvider = (CollectionLikeType)paramJavaType;
      if (paramSerializerProvider.isTrueCollectionType()) {
        return buildCollectionSerializer(localSerializationConfig, (CollectionType)paramSerializerProvider, paramBeanDescription, bool, (TypeSerializer)localObject2, localJsonSerializer);
      }
      localObject1 = (CollectionLikeType)paramJavaType;
      paramSerializerProvider = customSerializers().iterator();
      do
      {
        if (!paramSerializerProvider.hasNext()) {
          break;
        }
        paramJavaType = ((Serializers)paramSerializerProvider.next()).findCollectionLikeSerializer(localSerializationConfig, (CollectionLikeType)localObject1, paramBeanDescription, (TypeSerializer)localObject2, localJsonSerializer);
      } while (paramJavaType == null);
      paramSerializerProvider = paramJavaType;
    } while (!this._factoryConfig.hasSerializerModifiers());
    localObject2 = this._factoryConfig.serializerModifiers().iterator();
    for (;;)
    {
      paramSerializerProvider = paramJavaType;
      if (!((Iterator)localObject2).hasNext()) {
        break;
      }
      paramJavaType = ((BeanSerializerModifier)((Iterator)localObject2).next()).modifyCollectionLikeSerializer(localSerializationConfig, (CollectionLikeType)localObject1, paramBeanDescription, paramJavaType);
    }
    return null;
    label419:
    if (paramJavaType.isArrayType()) {
      return buildArraySerializer(localSerializationConfig, (ArrayType)paramJavaType, paramBeanDescription, bool, (TypeSerializer)localObject2, localJsonSerializer);
    }
    return null;
  }
  
  protected JsonSerializer<?> buildEnumSerializer(SerializationConfig paramSerializationConfig, JavaType paramJavaType, BeanDescription paramBeanDescription)
    throws JsonMappingException
  {
    Object localObject1 = null;
    Object localObject2 = paramBeanDescription.findExpectedFormat(null);
    if ((localObject2 != null) && (((JsonFormat.Value)localObject2).getShape() == JsonFormat.Shape.OBJECT)) {
      ((BasicBeanDescription)paramBeanDescription).removeProperty("declaringClass");
    }
    do
    {
      return (JsonSerializer<?>)localObject1;
      localObject2 = EnumSerializer.construct(paramJavaType.getRawClass(), paramSerializationConfig, paramBeanDescription, (JsonFormat.Value)localObject2);
      localObject1 = localObject2;
    } while (!this._factoryConfig.hasSerializerModifiers());
    Iterator localIterator = this._factoryConfig.serializerModifiers().iterator();
    for (;;)
    {
      localObject1 = localObject2;
      if (!localIterator.hasNext()) {
        break;
      }
      localObject2 = ((BeanSerializerModifier)localIterator.next()).modifyEnumSerializer(paramSerializationConfig, paramJavaType, paramBeanDescription, (JsonSerializer)localObject2);
    }
  }
  
  public JsonSerializer<?> buildEnumSetSerializer(JavaType paramJavaType)
  {
    return new EnumSetSerializer(paramJavaType, null);
  }
  
  public ContainerSerializer<?> buildIndexedListSerializer(JavaType paramJavaType, boolean paramBoolean, TypeSerializer paramTypeSerializer, JsonSerializer<Object> paramJsonSerializer)
  {
    return new IndexedListSerializer(paramJavaType, paramBoolean, paramTypeSerializer, null, paramJsonSerializer);
  }
  
  protected JsonSerializer<?> buildIterableSerializer(SerializationConfig paramSerializationConfig, JavaType paramJavaType, BeanDescription paramBeanDescription, boolean paramBoolean)
    throws JsonMappingException
  {
    paramBeanDescription = paramJavaType.containedType(0);
    paramJavaType = paramBeanDescription;
    if (paramBeanDescription == null) {
      paramJavaType = TypeFactory.unknownType();
    }
    return new IterableSerializer(paramJavaType, paramBoolean, createTypeSerializer(paramSerializationConfig, paramJavaType), null);
  }
  
  protected JsonSerializer<?> buildIteratorSerializer(SerializationConfig paramSerializationConfig, JavaType paramJavaType, BeanDescription paramBeanDescription, boolean paramBoolean)
    throws JsonMappingException
  {
    paramBeanDescription = paramJavaType.containedType(0);
    paramJavaType = paramBeanDescription;
    if (paramBeanDescription == null) {
      paramJavaType = TypeFactory.unknownType();
    }
    return new IteratorSerializer(paramJavaType, paramBoolean, createTypeSerializer(paramSerializationConfig, paramJavaType), null);
  }
  
  protected JsonSerializer<?> buildMapSerializer(SerializationConfig paramSerializationConfig, MapType paramMapType, BeanDescription paramBeanDescription, boolean paramBoolean, JsonSerializer<Object> paramJsonSerializer1, TypeSerializer paramTypeSerializer, JsonSerializer<Object> paramJsonSerializer2)
    throws JsonMappingException
  {
    Object localObject1 = null;
    Iterator localIterator = customSerializers().iterator();
    Object localObject2;
    do
    {
      localObject2 = localObject1;
      if (!localIterator.hasNext()) {
        break;
      }
      localObject2 = ((Serializers)localIterator.next()).findMapSerializer(paramSerializationConfig, paramMapType, paramBeanDescription, paramJsonSerializer1, paramTypeSerializer, paramJsonSerializer2);
      localObject1 = localObject2;
    } while (localObject2 == null);
    localObject1 = localObject2;
    if (localObject2 == null)
    {
      if (!EnumMap.class.isAssignableFrom(paramMapType.getRawClass())) {
        break label199;
      }
      localObject1 = paramMapType.getKeyType();
      paramJsonSerializer1 = null;
      if (((JavaType)localObject1).isEnumType()) {
        paramJsonSerializer1 = EnumValues.construct(paramSerializationConfig, ((JavaType)localObject1).getRawClass());
      }
    }
    for (localObject1 = new EnumMapSerializer(paramMapType.getContentType(), paramBoolean, paramJsonSerializer1, paramTypeSerializer, paramJsonSerializer2);; localObject1 = MapSerializer.construct(paramSerializationConfig.getAnnotationIntrospector().findPropertiesToIgnore(paramBeanDescription.getClassInfo()), paramMapType, paramBoolean, paramTypeSerializer, paramJsonSerializer1, paramJsonSerializer2, localObject1))
    {
      paramJsonSerializer1 = (JsonSerializer<Object>)localObject1;
      if (!this._factoryConfig.hasSerializerModifiers()) {
        break;
      }
      paramTypeSerializer = this._factoryConfig.serializerModifiers().iterator();
      for (;;)
      {
        paramJsonSerializer1 = (JsonSerializer<Object>)localObject1;
        if (!paramTypeSerializer.hasNext()) {
          break;
        }
        localObject1 = ((BeanSerializerModifier)paramTypeSerializer.next()).modifyMapSerializer(paramSerializationConfig, paramMapType, paramBeanDescription, (JsonSerializer)localObject1);
      }
      label199:
      localObject1 = findFilterId(paramSerializationConfig, paramBeanDescription);
    }
    return paramJsonSerializer1;
  }
  
  public JsonSerializer<Object> createKeySerializer(SerializationConfig paramSerializationConfig, JavaType paramJavaType, JsonSerializer<Object> paramJsonSerializer)
  {
    BeanDescription localBeanDescription = paramSerializationConfig.introspectClassAnnotations(paramJavaType.getRawClass());
    Object localObject2 = null;
    Object localObject1 = null;
    if (this._factoryConfig.hasKeySerializers())
    {
      Iterator localIterator = this._factoryConfig.keySerializers().iterator();
      localObject2 = localObject1;
      while (localIterator.hasNext())
      {
        localObject1 = ((Serializers)localIterator.next()).findSerializer(paramSerializationConfig, paramJavaType, localBeanDescription);
        localObject2 = localObject1;
        if (localObject1 != null) {
          localObject2 = localObject1;
        }
      }
    }
    localObject1 = localObject2;
    if (localObject2 == null)
    {
      localObject1 = paramJsonSerializer;
      if (paramJsonSerializer == null) {
        localObject1 = StdKeySerializers.getStdKeySerializer(paramJavaType);
      }
    }
    paramJsonSerializer = (JsonSerializer<Object>)localObject1;
    if (this._factoryConfig.hasSerializerModifiers())
    {
      localObject2 = this._factoryConfig.serializerModifiers().iterator();
      for (;;)
      {
        paramJsonSerializer = (JsonSerializer<Object>)localObject1;
        if (!((Iterator)localObject2).hasNext()) {
          break;
        }
        localObject1 = ((BeanSerializerModifier)((Iterator)localObject2).next()).modifyKeySerializer(paramSerializationConfig, paramJavaType, localBeanDescription, (JsonSerializer)localObject1);
      }
    }
    return paramJsonSerializer;
  }
  
  public abstract JsonSerializer<Object> createSerializer(SerializerProvider paramSerializerProvider, JavaType paramJavaType)
    throws JsonMappingException;
  
  public TypeSerializer createTypeSerializer(SerializationConfig paramSerializationConfig, JavaType paramJavaType)
  {
    AnnotatedClass localAnnotatedClass = paramSerializationConfig.introspectClassAnnotations(paramJavaType.getRawClass()).getClassInfo();
    AnnotationIntrospector localAnnotationIntrospector = paramSerializationConfig.getAnnotationIntrospector();
    TypeResolverBuilder localTypeResolverBuilder = localAnnotationIntrospector.findTypeResolver(paramSerializationConfig, localAnnotatedClass, paramJavaType);
    Collection localCollection = null;
    if (localTypeResolverBuilder == null) {
      localTypeResolverBuilder = paramSerializationConfig.getDefaultTyper(paramJavaType);
    }
    while (localTypeResolverBuilder == null)
    {
      return null;
      localCollection = paramSerializationConfig.getSubtypeResolver().collectAndResolveSubtypes(localAnnotatedClass, paramSerializationConfig, localAnnotationIntrospector);
    }
    return localTypeResolverBuilder.buildTypeSerializer(paramSerializationConfig, paramJavaType, localCollection);
  }
  
  protected abstract Iterable<Serializers> customSerializers();
  
  protected Converter<Object, Object> findConverter(SerializerProvider paramSerializerProvider, Annotated paramAnnotated)
    throws JsonMappingException
  {
    Object localObject = paramSerializerProvider.getAnnotationIntrospector().findSerializationConverter(paramAnnotated);
    if (localObject == null) {
      return null;
    }
    return paramSerializerProvider.converterInstance(paramAnnotated, localObject);
  }
  
  protected JsonSerializer<?> findConvertingSerializer(SerializerProvider paramSerializerProvider, Annotated paramAnnotated, JsonSerializer<?> paramJsonSerializer)
    throws JsonMappingException
  {
    paramAnnotated = findConverter(paramSerializerProvider, paramAnnotated);
    if (paramAnnotated == null) {
      return paramJsonSerializer;
    }
    return new StdDelegatingSerializer(paramAnnotated, paramAnnotated.getOutputType(paramSerializerProvider.getTypeFactory()), paramJsonSerializer);
  }
  
  protected Object findFilterId(SerializationConfig paramSerializationConfig, BeanDescription paramBeanDescription)
  {
    return paramSerializationConfig.getAnnotationIntrospector().findFilterId(paramBeanDescription.getClassInfo());
  }
  
  protected JsonSerializer<?> findOptionalStdSerializer(SerializerProvider paramSerializerProvider, JavaType paramJavaType, BeanDescription paramBeanDescription, boolean paramBoolean)
    throws JsonMappingException
  {
    return OptionalHandlerFactory.instance.findSerializer(paramSerializerProvider.getConfig(), paramJavaType, paramBeanDescription);
  }
  
  protected final JsonSerializer<?> findSerializerByAddonType(SerializationConfig paramSerializationConfig, JavaType paramJavaType, BeanDescription paramBeanDescription, boolean paramBoolean)
    throws JsonMappingException
  {
    Class localClass = paramJavaType.getRawClass();
    if (Iterator.class.isAssignableFrom(localClass)) {
      return buildIteratorSerializer(paramSerializationConfig, paramJavaType, paramBeanDescription, paramBoolean);
    }
    if (Iterable.class.isAssignableFrom(localClass)) {
      return buildIterableSerializer(paramSerializationConfig, paramJavaType, paramBeanDescription, paramBoolean);
    }
    if (CharSequence.class.isAssignableFrom(localClass)) {
      return ToStringSerializer.instance;
    }
    return null;
  }
  
  protected final JsonSerializer<?> findSerializerByAnnotations(SerializerProvider paramSerializerProvider, JavaType paramJavaType, BeanDescription paramBeanDescription)
    throws JsonMappingException
  {
    if (JsonSerializable.class.isAssignableFrom(paramJavaType.getRawClass())) {
      return SerializableSerializer.instance;
    }
    paramJavaType = paramBeanDescription.findJsonValueMethod();
    if (paramJavaType != null)
    {
      paramBeanDescription = paramJavaType.getAnnotated();
      if (paramSerializerProvider.canOverrideAccessModifiers()) {
        ClassUtil.checkAndFixAccess(paramBeanDescription);
      }
      return new JsonValueSerializer(paramBeanDescription, findSerializerFromAnnotation(paramSerializerProvider, paramJavaType));
    }
    return null;
  }
  
  protected final JsonSerializer<?> findSerializerByLookup(JavaType paramJavaType, SerializationConfig paramSerializationConfig, BeanDescription paramBeanDescription, boolean paramBoolean)
  {
    paramJavaType = paramJavaType.getRawClass().getName();
    paramSerializationConfig = (JsonSerializer)_concrete.get(paramJavaType);
    if (paramSerializationConfig == null)
    {
      paramJavaType = (Class)_concreteLazy.get(paramJavaType);
      if (paramJavaType != null) {
        try
        {
          paramSerializationConfig = (JsonSerializer)paramJavaType.newInstance();
          return paramSerializationConfig;
        }
        catch (Exception paramSerializationConfig)
        {
          throw new IllegalStateException("Failed to instantiate standard serializer (of type " + paramJavaType.getName() + "): " + paramSerializationConfig.getMessage(), paramSerializationConfig);
        }
      }
    }
    return paramSerializationConfig;
  }
  
  protected final JsonSerializer<?> findSerializerByPrimaryType(SerializerProvider paramSerializerProvider, JavaType paramJavaType, BeanDescription paramBeanDescription, boolean paramBoolean)
    throws JsonMappingException
  {
    Class localClass = paramJavaType.getRawClass();
    JsonSerializer localJsonSerializer = findOptionalStdSerializer(paramSerializerProvider, paramJavaType, paramBeanDescription, paramBoolean);
    if (localJsonSerializer != null) {
      return localJsonSerializer;
    }
    if (Calendar.class.isAssignableFrom(localClass)) {
      return CalendarSerializer.instance;
    }
    if (java.util.Date.class.isAssignableFrom(localClass)) {
      return DateSerializer.instance;
    }
    if (ByteBuffer.class.isAssignableFrom(localClass)) {
      return new ByteBufferSerializer();
    }
    if (InetAddress.class.isAssignableFrom(localClass)) {
      return new InetAddressSerializer();
    }
    if (InetSocketAddress.class.isAssignableFrom(localClass)) {
      return new InetSocketAddressSerializer();
    }
    if (TimeZone.class.isAssignableFrom(localClass)) {
      return new TimeZoneSerializer();
    }
    if (Charset.class.isAssignableFrom(localClass)) {
      return ToStringSerializer.instance;
    }
    if (Number.class.isAssignableFrom(localClass))
    {
      paramSerializerProvider = paramBeanDescription.findExpectedFormat(null);
      if (paramSerializerProvider != null) {}
      switch (paramSerializerProvider.getShape())
      {
      default: 
        return NumberSerializer.instance;
      case ???: 
        return ToStringSerializer.instance;
      }
      return null;
    }
    if (Enum.class.isAssignableFrom(localClass)) {
      return buildEnumSerializer(paramSerializerProvider.getConfig(), paramJavaType, paramBeanDescription);
    }
    return null;
  }
  
  protected JsonSerializer<Object> findSerializerFromAnnotation(SerializerProvider paramSerializerProvider, Annotated paramAnnotated)
    throws JsonMappingException
  {
    Object localObject = paramSerializerProvider.getAnnotationIntrospector().findSerializer(paramAnnotated);
    if (localObject == null) {
      return null;
    }
    return findConvertingSerializer(paramSerializerProvider, paramAnnotated, paramSerializerProvider.serializerInstance(paramAnnotated, localObject));
  }
  
  public SerializerFactoryConfig getFactoryConfig()
  {
    return this._factoryConfig;
  }
  
  protected boolean isIndexedList(Class<?> paramClass)
  {
    return RandomAccess.class.isAssignableFrom(paramClass);
  }
  
  protected <T extends JavaType> T modifyTypeByAnnotation(SerializationConfig paramSerializationConfig, Annotated paramAnnotated, T paramT)
  {
    Class localClass = paramSerializationConfig.getAnnotationIntrospector().findSerializationType(paramAnnotated);
    Object localObject = paramT;
    if (localClass != null) {}
    try
    {
      localObject = paramT.widenBy(localClass);
      return modifySecondaryTypesByAnnotation(paramSerializationConfig, paramAnnotated, (JavaType)localObject);
    }
    catch (IllegalArgumentException paramSerializationConfig)
    {
      throw new IllegalArgumentException("Failed to widen type " + paramT + " with concrete-type annotation (value " + localClass.getName() + "), method '" + paramAnnotated.getName() + "': " + paramSerializationConfig.getMessage());
    }
  }
  
  protected boolean usesStaticTyping(SerializationConfig paramSerializationConfig, BeanDescription paramBeanDescription, TypeSerializer paramTypeSerializer)
  {
    if (paramTypeSerializer != null) {}
    do
    {
      return false;
      paramBeanDescription = paramSerializationConfig.getAnnotationIntrospector().findSerializationTyping(paramBeanDescription.getClassInfo());
      if ((paramBeanDescription == null) || (paramBeanDescription == JsonSerialize.Typing.DEFAULT_TYPING)) {
        break;
      }
    } while (paramBeanDescription != JsonSerialize.Typing.STATIC);
    return true;
    return paramSerializationConfig.isEnabled(MapperFeature.USE_STATIC_TYPING);
  }
  
  public final SerializerFactory withAdditionalKeySerializers(Serializers paramSerializers)
  {
    return withConfig(this._factoryConfig.withAdditionalKeySerializers(paramSerializers));
  }
  
  public final SerializerFactory withAdditionalSerializers(Serializers paramSerializers)
  {
    return withConfig(this._factoryConfig.withAdditionalSerializers(paramSerializers));
  }
  
  public abstract SerializerFactory withConfig(SerializerFactoryConfig paramSerializerFactoryConfig);
  
  public final SerializerFactory withSerializerModifier(BeanSerializerModifier paramBeanSerializerModifier)
  {
    return withConfig(this._factoryConfig.withSerializerModifier(paramBeanSerializerModifier));
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/ser/BasicSerializerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */