package org.hamcrest;

public abstract interface Description
{
  public static final Description NONE = new NullDescription();
  
  public abstract Description appendDescriptionOf(SelfDescribing paramSelfDescribing);
  
  public abstract Description appendList(String paramString1, String paramString2, String paramString3, Iterable<? extends SelfDescribing> paramIterable);
  
  public abstract Description appendText(String paramString);
  
  public abstract Description appendValue(Object paramObject);
  
  public abstract <T> Description appendValueList(String paramString1, String paramString2, String paramString3, Iterable<T> paramIterable);
  
  public abstract <T> Description appendValueList(String paramString1, String paramString2, String paramString3, T... paramVarArgs);
  
  public static final class NullDescription
    implements Description
  {
    public Description appendDescriptionOf(SelfDescribing paramSelfDescribing)
    {
      return this;
    }
    
    public Description appendList(String paramString1, String paramString2, String paramString3, Iterable<? extends SelfDescribing> paramIterable)
    {
      return this;
    }
    
    public Description appendText(String paramString)
    {
      return this;
    }
    
    public Description appendValue(Object paramObject)
    {
      return this;
    }
    
    public <T> Description appendValueList(String paramString1, String paramString2, String paramString3, Iterable<T> paramIterable)
    {
      return this;
    }
    
    public <T> Description appendValueList(String paramString1, String paramString2, String paramString3, T... paramVarArgs)
    {
      return this;
    }
    
    public String toString()
    {
      return "";
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/Description.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */