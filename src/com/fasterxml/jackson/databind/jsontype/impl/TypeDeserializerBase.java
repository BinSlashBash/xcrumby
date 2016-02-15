package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.NullifyingDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

public abstract class TypeDeserializerBase
  extends TypeDeserializer
  implements Serializable
{
  private static final long serialVersionUID = 278445030337366675L;
  protected final JavaType _baseType;
  protected final JavaType _defaultImpl;
  protected JsonDeserializer<Object> _defaultImplDeserializer;
  protected final HashMap<String, JsonDeserializer<Object>> _deserializers;
  protected final TypeIdResolver _idResolver;
  protected final BeanProperty _property;
  protected final boolean _typeIdVisible;
  protected final String _typePropertyName;
  
  protected TypeDeserializerBase(JavaType paramJavaType, TypeIdResolver paramTypeIdResolver, String paramString, boolean paramBoolean, Class<?> paramClass)
  {
    this._baseType = paramJavaType;
    this._idResolver = paramTypeIdResolver;
    this._typePropertyName = paramString;
    this._typeIdVisible = paramBoolean;
    this._deserializers = new HashMap();
    if (paramClass == null) {}
    for (this._defaultImpl = null;; this._defaultImpl = paramJavaType.forcedNarrowBy(paramClass))
    {
      this._property = null;
      return;
    }
  }
  
  protected TypeDeserializerBase(TypeDeserializerBase paramTypeDeserializerBase, BeanProperty paramBeanProperty)
  {
    this._baseType = paramTypeDeserializerBase._baseType;
    this._idResolver = paramTypeDeserializerBase._idResolver;
    this._typePropertyName = paramTypeDeserializerBase._typePropertyName;
    this._typeIdVisible = paramTypeDeserializerBase._typeIdVisible;
    this._deserializers = paramTypeDeserializerBase._deserializers;
    this._defaultImpl = paramTypeDeserializerBase._defaultImpl;
    this._defaultImplDeserializer = paramTypeDeserializerBase._defaultImplDeserializer;
    this._property = paramBeanProperty;
  }
  
  @Deprecated
  protected Object _deserializeWithNativeTypeId(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    return _deserializeWithNativeTypeId(paramJsonParser, paramDeserializationContext, paramJsonParser.getTypeId());
  }
  
  protected Object _deserializeWithNativeTypeId(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
    throws IOException
  {
    if (paramObject == null)
    {
      if (this._defaultImpl == null) {
        throw paramDeserializationContext.mappingException("No (native) type id found when one was expected for polymorphic type handling");
      }
      paramObject = _findDefaultImplDeserializer(paramDeserializationContext);
      return ((JsonDeserializer)paramObject).deserialize(paramJsonParser, paramDeserializationContext);
    }
    if ((paramObject instanceof String)) {}
    for (paramObject = (String)paramObject;; paramObject = String.valueOf(paramObject))
    {
      paramObject = _findDeserializer(paramDeserializationContext, (String)paramObject);
      break;
    }
  }
  
  protected final JsonDeserializer<Object> _findDefaultImplDeserializer(DeserializationContext paramDeserializationContext)
    throws IOException
  {
    if (this._defaultImpl == null)
    {
      if (!paramDeserializationContext.isEnabled(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE)) {
        return NullifyingDeserializer.instance;
      }
      return null;
    }
    if (ClassUtil.isBogusClass(this._defaultImpl.getRawClass())) {
      return NullifyingDeserializer.instance;
    }
    synchronized (this._defaultImpl)
    {
      if (this._defaultImplDeserializer == null) {
        this._defaultImplDeserializer = paramDeserializationContext.findContextualValueDeserializer(this._defaultImpl, this._property);
      }
      paramDeserializationContext = this._defaultImplDeserializer;
      return paramDeserializationContext;
    }
  }
  
  protected final JsonDeserializer<Object> _findDeserializer(DeserializationContext paramDeserializationContext, String paramString)
    throws IOException
  {
    Object localObject2;
    Object localObject1;
    for (;;)
    {
      synchronized (this._deserializers)
      {
        localObject2 = (JsonDeserializer)this._deserializers.get(paramString);
        localObject1 = localObject2;
        if (localObject2 != null) {
          break label112;
        }
        if ((this._idResolver instanceof TypeIdResolverBase))
        {
          localObject1 = ((TypeIdResolverBase)this._idResolver).typeFromId(paramDeserializationContext, paramString);
          if (localObject1 != null) {
            break label117;
          }
          if (this._defaultImpl != null) {
            break;
          }
          throw paramDeserializationContext.unknownTypeException(this._baseType, paramString);
        }
      }
      localObject1 = this._idResolver.typeFromId(paramString);
    }
    for (paramDeserializationContext = _findDefaultImplDeserializer(paramDeserializationContext);; paramDeserializationContext = paramDeserializationContext.findContextualValueDeserializer((JavaType)localObject2, this._property))
    {
      this._deserializers.put(paramString, paramDeserializationContext);
      localObject1 = paramDeserializationContext;
      label112:
      return (JsonDeserializer<Object>)localObject1;
      label117:
      localObject2 = localObject1;
      if (this._baseType != null)
      {
        localObject2 = localObject1;
        if (this._baseType.getClass() == localObject1.getClass()) {
          localObject2 = this._baseType.narrowBy(((JavaType)localObject1).getRawClass());
        }
      }
    }
  }
  
  public String baseTypeName()
  {
    return this._baseType.getRawClass().getName();
  }
  
  public abstract TypeDeserializer forProperty(BeanProperty paramBeanProperty);
  
  public Class<?> getDefaultImpl()
  {
    if (this._defaultImpl == null) {
      return null;
    }
    return this._defaultImpl.getRawClass();
  }
  
  public final String getPropertyName()
  {
    return this._typePropertyName;
  }
  
  public TypeIdResolver getTypeIdResolver()
  {
    return this._idResolver;
  }
  
  public abstract JsonTypeInfo.As getTypeInclusion();
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append('[').append(getClass().getName());
    localStringBuilder.append("; base-type:").append(this._baseType);
    localStringBuilder.append("; id-resolver: ").append(this._idResolver);
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/jsontype/impl/TypeDeserializerBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */