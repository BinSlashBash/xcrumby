package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public abstract interface Converter<IN, OUT>
{
  public abstract OUT convert(IN paramIN);
  
  public abstract JavaType getInputType(TypeFactory paramTypeFactory);
  
  public abstract JavaType getOutputType(TypeFactory paramTypeFactory);
  
  public static abstract class None
    implements Converter<Object, Object>
  {}
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/util/Converter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */