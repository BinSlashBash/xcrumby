package com.fasterxml.jackson.databind.jsonFormatVisitors;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;

public abstract interface JsonMapFormatVisitor
  extends JsonFormatVisitorWithSerializerProvider
{
  public abstract void keyFormat(JsonFormatVisitable paramJsonFormatVisitable, JavaType paramJavaType)
    throws JsonMappingException;
  
  public abstract void valueFormat(JsonFormatVisitable paramJsonFormatVisitable, JavaType paramJavaType)
    throws JsonMappingException;
  
  public static class Base
    implements JsonMapFormatVisitor
  {
    protected SerializerProvider _provider;
    
    public Base() {}
    
    public Base(SerializerProvider paramSerializerProvider)
    {
      this._provider = paramSerializerProvider;
    }
    
    public SerializerProvider getProvider()
    {
      return this._provider;
    }
    
    public void keyFormat(JsonFormatVisitable paramJsonFormatVisitable, JavaType paramJavaType)
      throws JsonMappingException
    {}
    
    public void setProvider(SerializerProvider paramSerializerProvider)
    {
      this._provider = paramSerializerProvider;
    }
    
    public void valueFormat(JsonFormatVisitable paramJsonFormatVisitable, JavaType paramJavaType)
      throws JsonMappingException
    {}
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/jsonFormatVisitors/JsonMapFormatVisitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */