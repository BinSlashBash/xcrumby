package oauth.signpost.commonshttp;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;

public class HttpResponseAdapter
  implements oauth.signpost.http.HttpResponse
{
  private org.apache.http.HttpResponse response;
  
  public HttpResponseAdapter(org.apache.http.HttpResponse paramHttpResponse)
  {
    this.response = paramHttpResponse;
  }
  
  public InputStream getContent()
    throws IOException
  {
    return this.response.getEntity().getContent();
  }
  
  public String getReasonPhrase()
    throws Exception
  {
    return this.response.getStatusLine().getReasonPhrase();
  }
  
  public int getStatusCode()
    throws IOException
  {
    return this.response.getStatusLine().getStatusCode();
  }
  
  public Object unwrap()
  {
    return this.response;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/oauth/signpost/commonshttp/HttpResponseAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */