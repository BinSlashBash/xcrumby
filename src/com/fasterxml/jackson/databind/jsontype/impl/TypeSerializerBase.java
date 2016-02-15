package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

public abstract class TypeSerializerBase
  extends TypeSerializer
{
  protected final TypeIdResolver _idResolver;
  protected final BeanProperty _property;
  
  protected TypeSerializerBase(TypeIdResolver paramTypeIdResolver, BeanProperty paramBeanProperty)
  {
    this._idResolver = paramTypeIdResolver;
    this._property = paramBeanProperty;
  }
  
  public String getPropertyName()
  {
    return null;
  }
  
  public TypeIdResolver getTypeIdResolver()
  {
    return this._idResolver;
  }
  
  public abstract JsonTypeInfo.As getTypeInclusion();
  
  protected String idFromValue(Object paramObject)
  {
    String str = this._idResolver.idFromValue(paramObject);
    if (str == null)
    {
      if (paramObject == null) {}
      for (paramObject = "NULL";; paramObject = paramObject.getClass().getName()) {
        throw new IllegalArgumentException("Can not resolve type id for " + (String)paramObject + " (using " + this._idResolver.getClass().getName() + ")");
      }
    }
    return str;
  }
  
  protected String idFromValueAndType(Object paramObject, Class<?> paramClass)
  {
    paramClass = this._idResolver.idFromValueAndType(paramObject, paramClass);
    if (paramClass == null)
    {
      if (paramObject == null) {}
      for (paramObject = "NULL";; paramObject = paramObject.getClass().getName()) {
        throw new IllegalArgumentException("Can not resolve type id for " + (String)paramObject + " (using " + this._idResolver.getClass().getName() + ")");
      }
    }
    return paramClass;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/jsontype/impl/TypeSerializerBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */