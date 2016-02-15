package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import java.io.IOException;

public abstract class ValueInstantiator
{
  protected Object _createFromStringFallbacks(DeserializationContext paramDeserializationContext, String paramString)
    throws IOException, JsonProcessingException
  {
    if (canCreateFromBoolean())
    {
      String str = paramString.trim();
      if ("true".equals(str)) {
        return createFromBoolean(paramDeserializationContext, true);
      }
      if ("false".equals(str)) {
        return createFromBoolean(paramDeserializationContext, false);
      }
    }
    if ((paramString.length() == 0) && (paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT))) {
      return null;
    }
    throw paramDeserializationContext.mappingException("Can not instantiate value of type " + getValueTypeDesc() + " from String value ('" + paramString + "'); no single-String constructor/factory method");
  }
  
  public boolean canCreateFromBoolean()
  {
    return false;
  }
  
  public boolean canCreateFromDouble()
  {
    return false;
  }
  
  public boolean canCreateFromInt()
  {
    return false;
  }
  
  public boolean canCreateFromLong()
  {
    return false;
  }
  
  public boolean canCreateFromObjectWith()
  {
    return false;
  }
  
  public boolean canCreateFromString()
  {
    return false;
  }
  
  public boolean canCreateUsingDefault()
  {
    return getDefaultCreator() != null;
  }
  
  public boolean canCreateUsingDelegate()
  {
    return false;
  }
  
  public boolean canInstantiate()
  {
    return (canCreateUsingDefault()) || (canCreateUsingDelegate()) || (canCreateFromObjectWith()) || (canCreateFromString()) || (canCreateFromInt()) || (canCreateFromLong()) || (canCreateFromDouble()) || (canCreateFromBoolean());
  }
  
  public Object createFromBoolean(DeserializationContext paramDeserializationContext, boolean paramBoolean)
    throws IOException
  {
    throw paramDeserializationContext.mappingException("Can not instantiate value of type " + getValueTypeDesc() + " from Boolean value (" + paramBoolean + ")");
  }
  
  public Object createFromDouble(DeserializationContext paramDeserializationContext, double paramDouble)
    throws IOException
  {
    throw paramDeserializationContext.mappingException("Can not instantiate value of type " + getValueTypeDesc() + " from Floating-point number (" + paramDouble + ", double)");
  }
  
  public Object createFromInt(DeserializationContext paramDeserializationContext, int paramInt)
    throws IOException
  {
    throw paramDeserializationContext.mappingException("Can not instantiate value of type " + getValueTypeDesc() + " from Integer number (" + paramInt + ", int)");
  }
  
  public Object createFromLong(DeserializationContext paramDeserializationContext, long paramLong)
    throws IOException
  {
    throw paramDeserializationContext.mappingException("Can not instantiate value of type " + getValueTypeDesc() + " from Integer number (" + paramLong + ", long)");
  }
  
  public Object createFromObjectWith(DeserializationContext paramDeserializationContext, Object[] paramArrayOfObject)
    throws IOException
  {
    throw paramDeserializationContext.mappingException("Can not instantiate value of type " + getValueTypeDesc() + " with arguments");
  }
  
  public Object createFromString(DeserializationContext paramDeserializationContext, String paramString)
    throws IOException
  {
    return _createFromStringFallbacks(paramDeserializationContext, paramString);
  }
  
  public Object createUsingDefault(DeserializationContext paramDeserializationContext)
    throws IOException
  {
    throw paramDeserializationContext.mappingException("Can not instantiate value of type " + getValueTypeDesc() + "; no default creator found");
  }
  
  public Object createUsingDelegate(DeserializationContext paramDeserializationContext, Object paramObject)
    throws IOException
  {
    throw paramDeserializationContext.mappingException("Can not instantiate value of type " + getValueTypeDesc() + " using delegate");
  }
  
  public AnnotatedWithParams getDefaultCreator()
  {
    return null;
  }
  
  public AnnotatedWithParams getDelegateCreator()
  {
    return null;
  }
  
  public JavaType getDelegateType(DeserializationConfig paramDeserializationConfig)
  {
    return null;
  }
  
  public SettableBeanProperty[] getFromObjectArguments(DeserializationConfig paramDeserializationConfig)
  {
    return null;
  }
  
  public AnnotatedParameter getIncompleteParameter()
  {
    return null;
  }
  
  public abstract String getValueTypeDesc();
  
  public AnnotatedWithParams getWithArgsCreator()
  {
    return null;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/ValueInstantiator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */