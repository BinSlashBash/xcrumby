package com.crumby.lib.widget.thirdparty;

import java.io.IOException;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WebkitCookieManagerProxy
  extends java.net.CookieManager
{
  private android.webkit.CookieManager webkitCookieManager = android.webkit.CookieManager.getInstance();
  
  public WebkitCookieManagerProxy()
  {
    this(null, null);
  }
  
  public WebkitCookieManagerProxy(CookieStore paramCookieStore, CookiePolicy paramCookiePolicy)
  {
    super(null, paramCookiePolicy);
  }
  
  public Map<String, List<String>> get(URI paramURI, Map<String, List<String>> paramMap)
    throws IOException
  {
    if ((paramURI == null) || (paramMap == null)) {
      throw new IllegalArgumentException("Argument is null");
    }
    paramMap = paramURI.toString();
    paramURI = new HashMap();
    paramMap = this.webkitCookieManager.getCookie(paramMap);
    if (paramMap != null) {
      paramURI.put("Cookie", Arrays.asList(new String[] { paramMap }));
    }
    return paramURI;
  }
  
  public CookieStore getCookieStore()
  {
    throw new UnsupportedOperationException();
  }
  
  public void put(URI paramURI, Map<String, List<String>> paramMap)
    throws IOException
  {
    label8:
    Iterator localIterator;
    if ((paramURI == null) || (paramMap == null))
    {
      return;
    }
    else
    {
      paramURI = paramURI.toString();
      localIterator = paramMap.keySet().iterator();
    }
    for (;;)
    {
      if (!localIterator.hasNext()) {
        break label8;
      }
      Object localObject = (String)localIterator.next();
      if ((localObject == null) || ((!((String)localObject).equalsIgnoreCase("Set-Cookie2")) && (!((String)localObject).equalsIgnoreCase("Set-Cookie")))) {
        break;
      }
      localObject = ((List)paramMap.get(localObject)).iterator();
      while (((Iterator)localObject).hasNext())
      {
        String str = (String)((Iterator)localObject).next();
        this.webkitCookieManager.setCookie(paramURI, str);
      }
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/thirdparty/WebkitCookieManagerProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */