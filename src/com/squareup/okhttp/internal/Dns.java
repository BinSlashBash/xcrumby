package com.squareup.okhttp.internal;

import java.net.InetAddress;
import java.net.UnknownHostException;

public abstract interface Dns
{
  public static final Dns DEFAULT = new Dns()
  {
    public InetAddress[] getAllByName(String paramAnonymousString)
      throws UnknownHostException
    {
      if (paramAnonymousString == null) {
        throw new UnknownHostException("host == null");
      }
      return InetAddress.getAllByName(paramAnonymousString);
    }
  };
  
  public abstract InetAddress[] getAllByName(String paramString)
    throws UnknownHostException;
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/squareup/okhttp/internal/Dns.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */