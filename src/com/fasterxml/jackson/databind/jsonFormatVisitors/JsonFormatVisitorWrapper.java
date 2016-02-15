package com.fasterxml.jackson.databind.jsonFormatVisitors;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;

public abstract interface JsonFormatVisitorWrapper
  extends JsonFormatVisitorWithSerializerProvider
{
  public abstract JsonAnyFormatVisitor expectAnyFormat(JavaType paramJavaType)
    throws JsonMappingException;
  
  public abstract JsonArrayFormatVisitor expectArrayFormat(JavaType paramJavaType)
    throws JsonMappingException;
  
  public abstract JsonBooleanFormatVisitor expectBooleanFormat(JavaType paramJavaType)
    throws JsonMappingException;
  
  public abstract JsonIntegerFormatVisitor expectIntegerFormat(JavaType paramJavaType)
    throws JsonMappingException;
  
  public abstract JsonMapFormatVisitor expectMapFormat(JavaType paramJavaType)
    throws JsonMappingException;
  
  public abstract JsonNullFormatVisitor expectNullFormat(JavaType paramJavaType)
    throws JsonMappingException;
  
  public abstract JsonNumberFormatVisitor expectNumberFormat(JavaType paramJavaType)
    throws JsonMappingException;
  
  public abstract JsonObjectFormatVisitor expectObjectFormat(JavaType paramJavaType)
    throws JsonMappingException;
  
  public abstract JsonStringFormatVisitor expectStringFormat(JavaType paramJavaType)
    throws JsonMappingException;
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/jsonFormatVisitors/JsonFormatVisitorWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */