package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;

public abstract class ContainerDeserializerBase<T>
  extends StdDeserializer<T>
{
  protected ContainerDeserializerBase(JavaType paramJavaType)
  {
    super(paramJavaType);
  }
  
  @Deprecated
  protected ContainerDeserializerBase(Class<?> paramClass)
  {
    super(paramClass);
  }
  
  public SettableBeanProperty findBackReference(String paramString)
  {
    JsonDeserializer localJsonDeserializer = getContentDeserializer();
    if (localJsonDeserializer == null) {
      throw new IllegalArgumentException("Can not handle managed/back reference '" + paramString + "': type: container deserializer of type " + getClass().getName() + " returned null for 'getContentDeserializer()'");
    }
    return localJsonDeserializer.findBackReference(paramString);
  }
  
  public abstract JsonDeserializer<Object> getContentDeserializer();
  
  public abstract JavaType getContentType();
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/std/ContainerDeserializerBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */