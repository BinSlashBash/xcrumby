package oauth.signpost.basic;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import oauth.signpost.http.HttpRequest;

public class HttpURLConnectionRequestAdapter
  implements HttpRequest
{
  protected HttpURLConnection connection;
  
  public HttpURLConnectionRequestAdapter(HttpURLConnection paramHttpURLConnection)
  {
    this.connection = paramHttpURLConnection;
  }
  
  public Map<String, String> getAllHeaders()
  {
    Map localMap = this.connection.getRequestProperties();
    HashMap localHashMap = new HashMap(localMap.size());
    Iterator localIterator = localMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      List localList = (List)localMap.get(str);
      if (!localList.isEmpty()) {
        localHashMap.put(str, localList.get(0));
      }
    }
    return localHashMap;
  }
  
  public String getContentType()
  {
    return this.connection.getRequestProperty("Content-Type");
  }
  
  public String getHeader(String paramString)
  {
    return this.connection.getRequestProperty(paramString);
  }
  
  public InputStream getMessagePayload()
    throws IOException
  {
    return null;
  }
  
  public String getMethod()
  {
    return this.connection.getRequestMethod();
  }
  
  public String getRequestUrl()
  {
    return this.connection.getURL().toExternalForm();
  }
  
  public void setHeader(String paramString1, String paramString2)
  {
    this.connection.setRequestProperty(paramString1, paramString2);
  }
  
  public void setRequestUrl(String paramString) {}
  
  public HttpURLConnection unwrap()
  {
    return this.connection;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/oauth/signpost/basic/HttpURLConnectionRequestAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */