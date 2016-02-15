package oauth.signpost.basic;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import oauth.signpost.http.HttpRequest;

public class UrlStringRequestAdapter
  implements HttpRequest
{
  private String url;
  
  public UrlStringRequestAdapter(String paramString)
  {
    this.url = paramString;
  }
  
  public Map<String, String> getAllHeaders()
  {
    return Collections.emptyMap();
  }
  
  public String getContentType()
  {
    return null;
  }
  
  public String getHeader(String paramString)
  {
    return null;
  }
  
  public InputStream getMessagePayload()
    throws IOException
  {
    return null;
  }
  
  public String getMethod()
  {
    return "GET";
  }
  
  public String getRequestUrl()
  {
    return this.url;
  }
  
  public void setHeader(String paramString1, String paramString2) {}
  
  public void setRequestUrl(String paramString)
  {
    this.url = paramString;
  }
  
  public Object unwrap()
  {
    return this.url;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/oauth/signpost/basic/UrlStringRequestAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */