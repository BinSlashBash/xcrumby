package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class FailingDeserializer
  extends StdDeserializer<Object>
{
  private static final long serialVersionUID = 1L;
  protected final String _message;
  
  public FailingDeserializer(String paramString)
  {
    super(Object.class);
    this._message = paramString;
  }
  
  public Object deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws JsonMappingException
  {
    throw paramDeserializationContext.mappingException(this._message);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/impl/FailingDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */