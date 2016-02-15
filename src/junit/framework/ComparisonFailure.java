package junit.framework;

public class ComparisonFailure
  extends AssertionFailedError
{
  private static final int MAX_CONTEXT_LENGTH = 20;
  private static final long serialVersionUID = 1L;
  private String fActual;
  private String fExpected;
  
  public ComparisonFailure(String paramString1, String paramString2, String paramString3)
  {
    super(paramString1);
    this.fExpected = paramString2;
    this.fActual = paramString3;
  }
  
  public String getActual()
  {
    return this.fActual;
  }
  
  public String getExpected()
  {
    return this.fExpected;
  }
  
  public String getMessage()
  {
    return new ComparisonCompactor(20, this.fExpected, this.fActual).compact(super.getMessage());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/junit/framework/ComparisonFailure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */