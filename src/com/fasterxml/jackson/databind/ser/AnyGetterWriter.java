package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import java.util.Map;

public class AnyGetterWriter
{
  protected final AnnotatedMember _accessor;
  protected final BeanProperty _property;
  protected MapSerializer _serializer;
  
  public AnyGetterWriter(BeanProperty paramBeanProperty, AnnotatedMember paramAnnotatedMember, MapSerializer paramMapSerializer)
  {
    this._accessor = paramAnnotatedMember;
    this._property = paramBeanProperty;
    this._serializer = paramMapSerializer;
  }
  
  public void getAndFilter(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, PropertyFilter paramPropertyFilter)
    throws Exception
  {
    paramObject = this._accessor.getValue(paramObject);
    if (paramObject == null) {
      return;
    }
    if (!(paramObject instanceof Map)) {
      throw new JsonMappingException("Value returned by 'any-getter' (" + this._accessor.getName() + "()) not java.util.Map but " + paramObject.getClass().getName());
    }
    this._serializer.serializeFilteredFields((Map)paramObject, paramJsonGenerator, paramSerializerProvider, paramPropertyFilter);
  }
  
  public void getAndSerialize(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws Exception
  {
    paramObject = this._accessor.getValue(paramObject);
    if (paramObject == null) {
      return;
    }
    if (!(paramObject instanceof Map)) {
      throw new JsonMappingException("Value returned by 'any-getter' (" + this._accessor.getName() + "()) not java.util.Map but " + paramObject.getClass().getName());
    }
    this._serializer.serializeFields((Map)paramObject, paramJsonGenerator, paramSerializerProvider);
  }
  
  public void resolve(SerializerProvider paramSerializerProvider)
    throws JsonMappingException
  {
    this._serializer = ((MapSerializer)paramSerializerProvider.handlePrimaryContextualization(this._serializer, this._property));
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/ser/AnyGetterWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */