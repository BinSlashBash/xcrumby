package oauth.signpost.exception;

public abstract class OAuthException
  extends Exception
{
  public OAuthException(String paramString)
  {
    super(paramString);
  }
  
  public OAuthException(String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
  }
  
  public OAuthException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/oauth/signpost/exception/OAuthException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */