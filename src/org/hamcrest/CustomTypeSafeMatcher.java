package org.hamcrest;

public abstract class CustomTypeSafeMatcher<T>
  extends TypeSafeMatcher<T>
{
  private final String fixedDescription;
  
  public CustomTypeSafeMatcher(String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("Description must be non null!");
    }
    this.fixedDescription = paramString;
  }
  
  public final void describeTo(Description paramDescription)
  {
    paramDescription.appendText(this.fixedDescription);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/CustomTypeSafeMatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */