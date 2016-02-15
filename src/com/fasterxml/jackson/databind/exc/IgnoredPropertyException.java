package com.fasterxml.jackson.databind.exc;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import java.util.Collection;

public class IgnoredPropertyException
  extends PropertyBindingException
{
  private static final long serialVersionUID = 1L;
  
  public IgnoredPropertyException(String paramString1, JsonLocation paramJsonLocation, Class<?> paramClass, String paramString2, Collection<Object> paramCollection)
  {
    super(paramString1, paramJsonLocation, paramClass, paramString2, paramCollection);
  }
  
  public static IgnoredPropertyException from(JsonParser paramJsonParser, Object paramObject, String paramString, Collection<Object> paramCollection)
  {
    if (paramObject == null) {
      throw new IllegalArgumentException();
    }
    if ((paramObject instanceof Class)) {}
    for (Class localClass = (Class)paramObject;; localClass = paramObject.getClass())
    {
      paramJsonParser = new IgnoredPropertyException("Ignored field \"" + paramString + "\" (class " + localClass.getName() + ") encountered; mapper configured not to allow this", paramJsonParser.getCurrentLocation(), localClass, paramString, paramCollection);
      paramJsonParser.prependPath(paramObject, paramString);
      return paramJsonParser;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/exc/IgnoredPropertyException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */