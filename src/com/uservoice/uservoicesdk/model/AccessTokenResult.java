package com.uservoice.uservoicesdk.model;

public class AccessTokenResult<T>
{
  private AccessToken accessToken;
  private T model;
  
  public AccessTokenResult(T paramT, AccessToken paramAccessToken)
  {
    this.model = paramT;
    this.accessToken = paramAccessToken;
  }
  
  public AccessToken getAccessToken()
  {
    return this.accessToken;
  }
  
  public T getModel()
  {
    return (T)this.model;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/uservoice/uservoicesdk/model/AccessTokenResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */