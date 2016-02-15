package junit.framework;

public class AssertionFailedError
  extends AssertionError
{
  private static final long serialVersionUID = 1L;
  
  public AssertionFailedError() {}
  
  public AssertionFailedError(String paramString)
  {
    super(defaultString(paramString));
  }
  
  private static String defaultString(String paramString)
  {
    String str = paramString;
    if (paramString == null) {
      str = "";
    }
    return str;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/junit/framework/AssertionFailedError.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */