package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public abstract class ContainerSerializer<T>
  extends StdSerializer<T>
{
  protected ContainerSerializer(ContainerSerializer<?> paramContainerSerializer)
  {
    super(paramContainerSerializer._handledType, false);
  }
  
  protected ContainerSerializer(Class<T> paramClass)
  {
    super(paramClass);
  }
  
  protected ContainerSerializer(Class<?> paramClass, boolean paramBoolean)
  {
    super(paramClass, paramBoolean);
  }
  
  protected abstract ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer paramTypeSerializer);
  
  public abstract JsonSerializer<?> getContentSerializer();
  
  public abstract JavaType getContentType();
  
  protected boolean hasContentTypeAnnotation(SerializerProvider paramSerializerProvider, BeanProperty paramBeanProperty)
  {
    if (paramBeanProperty != null)
    {
      paramSerializerProvider = paramSerializerProvider.getAnnotationIntrospector();
      if ((paramSerializerProvider != null) && (paramSerializerProvider.findSerializationContentType(paramBeanProperty.getMember(), paramBeanProperty.getType()) != null)) {
        return true;
      }
    }
    return false;
  }
  
  public abstract boolean hasSingleElement(T paramT);
  
  public abstract boolean isEmpty(T paramT);
  
  public ContainerSerializer<?> withValueTypeSerializer(TypeSerializer paramTypeSerializer)
  {
    if (paramTypeSerializer == null) {
      return this;
    }
    return _withValueTypeSerializer(paramTypeSerializer);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/ser/ContainerSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */