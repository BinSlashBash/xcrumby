package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;

public class UnwrappingBeanSerializer
  extends BeanSerializerBase
{
  protected final NameTransformer _nameTransformer;
  
  public UnwrappingBeanSerializer(UnwrappingBeanSerializer paramUnwrappingBeanSerializer, ObjectIdWriter paramObjectIdWriter)
  {
    super(paramUnwrappingBeanSerializer, paramObjectIdWriter);
    this._nameTransformer = paramUnwrappingBeanSerializer._nameTransformer;
  }
  
  public UnwrappingBeanSerializer(UnwrappingBeanSerializer paramUnwrappingBeanSerializer, ObjectIdWriter paramObjectIdWriter, Object paramObject)
  {
    super(paramUnwrappingBeanSerializer, paramObjectIdWriter, paramObject);
    this._nameTransformer = paramUnwrappingBeanSerializer._nameTransformer;
  }
  
  protected UnwrappingBeanSerializer(UnwrappingBeanSerializer paramUnwrappingBeanSerializer, String[] paramArrayOfString)
  {
    super(paramUnwrappingBeanSerializer, paramArrayOfString);
    this._nameTransformer = paramUnwrappingBeanSerializer._nameTransformer;
  }
  
  public UnwrappingBeanSerializer(BeanSerializerBase paramBeanSerializerBase, NameTransformer paramNameTransformer)
  {
    super(paramBeanSerializerBase, paramNameTransformer);
    this._nameTransformer = paramNameTransformer;
  }
  
  protected BeanSerializerBase asArraySerializer()
  {
    return this;
  }
  
  public boolean isUnwrappingSerializer()
  {
    return true;
  }
  
  public final void serialize(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException, JsonGenerationException
  {
    if (this._objectIdWriter != null)
    {
      _serializeWithObjectId(paramObject, paramJsonGenerator, paramSerializerProvider, false);
      return;
    }
    if (this._propertyFilterId != null)
    {
      serializeFieldsFiltered(paramObject, paramJsonGenerator, paramSerializerProvider);
      return;
    }
    serializeFields(paramObject, paramJsonGenerator, paramSerializerProvider);
  }
  
  public void serializeWithType(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
    throws IOException, JsonGenerationException
  {
    if (paramSerializerProvider.isEnabled(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS)) {
      throw new JsonMappingException("Unwrapped property requires use of type information: can not serialize without disabling `SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS`");
    }
    if (this._objectIdWriter != null)
    {
      _serializeWithObjectId(paramObject, paramJsonGenerator, paramSerializerProvider, paramTypeSerializer);
      return;
    }
    if (this._propertyFilterId != null)
    {
      serializeFieldsFiltered(paramObject, paramJsonGenerator, paramSerializerProvider);
      return;
    }
    serializeFields(paramObject, paramJsonGenerator, paramSerializerProvider);
  }
  
  public String toString()
  {
    return "UnwrappingBeanSerializer for " + handledType().getName();
  }
  
  public JsonSerializer<Object> unwrappingSerializer(NameTransformer paramNameTransformer)
  {
    return new UnwrappingBeanSerializer(this, paramNameTransformer);
  }
  
  protected BeanSerializerBase withFilterId(Object paramObject)
  {
    return new UnwrappingBeanSerializer(this, this._objectIdWriter, paramObject);
  }
  
  protected BeanSerializerBase withIgnorals(String[] paramArrayOfString)
  {
    return new UnwrappingBeanSerializer(this, paramArrayOfString);
  }
  
  public BeanSerializerBase withObjectIdWriter(ObjectIdWriter paramObjectIdWriter)
  {
    return new UnwrappingBeanSerializer(this, paramObjectIdWriter);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/ser/impl/UnwrappingBeanSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */