package org.json;

public class JSONException
  extends RuntimeException
{
  private static final long serialVersionUID = 0L;
  private Throwable cause;
  
  public JSONException(String paramString)
  {
    super(paramString);
  }
  
  public JSONException(Throwable paramThrowable)
  {
    super(paramThrowable.getMessage());
    this.cause = paramThrowable;
  }
  
  public Throwable getCause()
  {
    return this.cause;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/json/JSONException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */