package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import java.io.IOException;

public class AsPropertyTypeSerializer
  extends AsArrayTypeSerializer
{
  protected final String _typePropertyName;
  
  public AsPropertyTypeSerializer(TypeIdResolver paramTypeIdResolver, BeanProperty paramBeanProperty, String paramString)
  {
    super(paramTypeIdResolver, paramBeanProperty);
    this._typePropertyName = paramString;
  }
  
  public AsPropertyTypeSerializer forProperty(BeanProperty paramBeanProperty)
  {
    if (this._property == paramBeanProperty) {
      return this;
    }
    return new AsPropertyTypeSerializer(this._idResolver, paramBeanProperty, this._typePropertyName);
  }
  
  public String getPropertyName()
  {
    return this._typePropertyName;
  }
  
  public JsonTypeInfo.As getTypeInclusion()
  {
    return JsonTypeInfo.As.PROPERTY;
  }
  
  public void writeCustomTypePrefixForObject(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
    throws IOException
  {
    if (paramJsonGenerator.canWriteTypeId())
    {
      paramJsonGenerator.writeTypeId(paramString);
      paramJsonGenerator.writeStartObject();
      return;
    }
    paramJsonGenerator.writeStartObject();
    paramJsonGenerator.writeStringField(this._typePropertyName, paramString);
  }
  
  public void writeCustomTypeSuffixForObject(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
    throws IOException
  {
    paramJsonGenerator.writeEndObject();
  }
  
  public void writeTypePrefixForObject(Object paramObject, JsonGenerator paramJsonGenerator)
    throws IOException
  {
    paramObject = idFromValue(paramObject);
    if (paramJsonGenerator.canWriteTypeId())
    {
      paramJsonGenerator.writeTypeId(paramObject);
      paramJsonGenerator.writeStartObject();
      return;
    }
    paramJsonGenerator.writeStartObject();
    paramJsonGenerator.writeStringField(this._typePropertyName, (String)paramObject);
  }
  
  public void writeTypePrefixForObject(Object paramObject, JsonGenerator paramJsonGenerator, Class<?> paramClass)
    throws IOException
  {
    paramObject = idFromValueAndType(paramObject, paramClass);
    if (paramJsonGenerator.canWriteTypeId())
    {
      paramJsonGenerator.writeTypeId(paramObject);
      paramJsonGenerator.writeStartObject();
      return;
    }
    paramJsonGenerator.writeStartObject();
    paramJsonGenerator.writeStringField(this._typePropertyName, (String)paramObject);
  }
  
  public void writeTypeSuffixForObject(Object paramObject, JsonGenerator paramJsonGenerator)
    throws IOException
  {
    paramJsonGenerator.writeEndObject();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/jsontype/impl/AsPropertyTypeSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */