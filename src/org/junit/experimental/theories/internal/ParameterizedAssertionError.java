package org.junit.experimental.theories.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class ParameterizedAssertionError
  extends RuntimeException
{
  private static final long serialVersionUID = 1L;
  
  public ParameterizedAssertionError(Throwable paramThrowable, String paramString, Object... paramVarArgs)
  {
    super(String.format("%s(%s)", new Object[] { paramString, join(", ", paramVarArgs) }), paramThrowable);
  }
  
  public static String join(String paramString, Collection<Object> paramCollection)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext())
    {
      localStringBuffer.append(stringValueOf(paramCollection.next()));
      if (paramCollection.hasNext()) {
        localStringBuffer.append(paramString);
      }
    }
    return localStringBuffer.toString();
  }
  
  public static String join(String paramString, Object... paramVarArgs)
  {
    return join(paramString, Arrays.asList(paramVarArgs));
  }
  
  private static String stringValueOf(Object paramObject)
  {
    try
    {
      paramObject = String.valueOf(paramObject);
      return (String)paramObject;
    }
    catch (Throwable paramObject) {}
    return "[toString failed]";
  }
  
  public boolean equals(Object paramObject)
  {
    return toString().equals(paramObject.toString());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/experimental/theories/internal/ParameterizedAssertionError.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */