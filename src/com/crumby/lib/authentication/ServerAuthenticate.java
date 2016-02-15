package com.crumby.lib.authentication;

public abstract interface ServerAuthenticate
{
  public abstract void userSignIn(String paramString1, String paramString2, String paramString3)
    throws Exception;
  
  public abstract String userSignInAndGetAuth(String paramString1, String paramString2, String paramString3)
    throws Exception;
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/authentication/ServerAuthenticate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */