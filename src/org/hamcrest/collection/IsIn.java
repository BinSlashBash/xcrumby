package org.hamcrest.collection;

import java.util.Arrays;
import java.util.Collection;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsIn<T>
  extends BaseMatcher<T>
{
  private final Collection<T> collection;
  
  public IsIn(Collection<T> paramCollection)
  {
    this.collection = paramCollection;
  }
  
  public IsIn(T[] paramArrayOfT)
  {
    this.collection = Arrays.asList(paramArrayOfT);
  }
  
  @Factory
  public static <T> Matcher<T> isIn(Collection<T> paramCollection)
  {
    return new IsIn(paramCollection);
  }
  
  @Factory
  public static <T> Matcher<T> isIn(T[] paramArrayOfT)
  {
    return new IsIn(paramArrayOfT);
  }
  
  @Factory
  public static <T> Matcher<T> isOneOf(T... paramVarArgs)
  {
    return isIn(paramVarArgs);
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("one of ");
    paramDescription.appendValueList("{", ", ", "}", this.collection);
  }
  
  public boolean matches(Object paramObject)
  {
    return this.collection.contains(paramObject);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/collection/IsIn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */