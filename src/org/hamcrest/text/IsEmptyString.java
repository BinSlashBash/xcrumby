package org.hamcrest.text;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.AnyOf;
import org.hamcrest.core.IsNull;

public final class IsEmptyString
  extends BaseMatcher<String>
{
  private static final IsEmptyString INSTANCE = new IsEmptyString();
  private static final Matcher<String> NULL_OR_EMPTY_INSTANCE = AnyOf.anyOf(new Matcher[] { IsNull.nullValue(), INSTANCE });
  
  @Factory
  public static Matcher<String> isEmptyOrNullString()
  {
    return NULL_OR_EMPTY_INSTANCE;
  }
  
  @Factory
  public static Matcher<String> isEmptyString()
  {
    return INSTANCE;
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("an empty string");
  }
  
  public boolean matches(Object paramObject)
  {
    return (paramObject != null) && ((paramObject instanceof String)) && (((String)paramObject).equals(""));
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/text/IsEmptyString.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */