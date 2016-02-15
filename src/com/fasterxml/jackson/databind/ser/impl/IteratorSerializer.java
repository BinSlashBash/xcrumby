package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.std.AsArraySerializerBase;
import java.io.IOException;
import java.util.Iterator;

@JacksonStdImpl
public class IteratorSerializer
  extends AsArraySerializerBase<Iterator<?>>
{
  public IteratorSerializer(JavaType paramJavaType, boolean paramBoolean, TypeSerializer paramTypeSerializer, BeanProperty paramBeanProperty)
  {
    super(Iterator.class, paramJavaType, paramBoolean, paramTypeSerializer, paramBeanProperty, null);
  }
  
  public IteratorSerializer(IteratorSerializer paramIteratorSerializer, BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer, JsonSerializer<?> paramJsonSerializer)
  {
    super(paramIteratorSerializer, paramBeanProperty, paramTypeSerializer, paramJsonSerializer);
  }
  
  public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer paramTypeSerializer)
  {
    return new IteratorSerializer(this._elementType, this._staticTyping, paramTypeSerializer, this._property);
  }
  
  public boolean hasSingleElement(Iterator<?> paramIterator)
  {
    return false;
  }
  
  public boolean isEmpty(Iterator<?> paramIterator)
  {
    return (paramIterator == null) || (!paramIterator.hasNext());
  }
  
  public void serializeContents(Iterator<?> paramIterator, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException, JsonGenerationException
  {
    TypeSerializer localTypeSerializer;
    Object localObject1;
    Object localObject2;
    if (paramIterator.hasNext())
    {
      localTypeSerializer = this._valueTypeSerializer;
      localObject1 = null;
      localObject2 = null;
    }
    for (;;)
    {
      Object localObject6 = paramIterator.next();
      if (localObject6 == null) {
        paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
      }
      while (!paramIterator.hasNext())
      {
        return;
        JsonSerializer localJsonSerializer = this._elementSerializer;
        Object localObject3 = localJsonSerializer;
        Object localObject4 = localObject2;
        Object localObject5 = localObject1;
        if (localJsonSerializer == null)
        {
          localObject4 = localObject6.getClass();
          if (localObject4 != localObject2) {
            break label123;
          }
          localObject3 = localObject1;
          localObject5 = localObject1;
          localObject4 = localObject2;
        }
        for (;;)
        {
          if (localTypeSerializer != null) {
            break label142;
          }
          ((JsonSerializer)localObject3).serialize(localObject6, paramJsonGenerator, paramSerializerProvider);
          localObject2 = localObject4;
          localObject1 = localObject5;
          break;
          label123:
          localObject3 = paramSerializerProvider.findValueSerializer((Class)localObject4, this._property);
          localObject5 = localObject3;
        }
        label142:
        ((JsonSerializer)localObject3).serializeWithType(localObject6, paramJsonGenerator, paramSerializerProvider, localTypeSerializer);
        localObject2 = localObject4;
        localObject1 = localObject5;
      }
    }
  }
  
  public IteratorSerializer withResolved(BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer, JsonSerializer<?> paramJsonSerializer)
  {
    return new IteratorSerializer(this, paramBeanProperty, paramTypeSerializer, paramJsonSerializer);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/ser/impl/IteratorSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */