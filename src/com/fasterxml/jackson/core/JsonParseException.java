package com.fasterxml.jackson.core;

public class JsonParseException
  extends JsonProcessingException
{
  private static final long serialVersionUID = 1L;
  
  public JsonParseException(String paramString, JsonLocation paramJsonLocation)
  {
    super(paramString, paramJsonLocation);
  }
  
  public JsonParseException(String paramString, JsonLocation paramJsonLocation, Throwable paramThrowable)
  {
    super(paramString, paramJsonLocation, paramThrowable);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/core/JsonParseException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */