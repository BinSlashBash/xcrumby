package oauth.signpost.basic;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import oauth.signpost.http.HttpResponse;

public class HttpURLConnectionResponseAdapter
  implements HttpResponse
{
  private HttpURLConnection connection;
  
  public HttpURLConnectionResponseAdapter(HttpURLConnection paramHttpURLConnection)
  {
    this.connection = paramHttpURLConnection;
  }
  
  public InputStream getContent()
    throws IOException
  {
    try
    {
      InputStream localInputStream = this.connection.getInputStream();
      return localInputStream;
    }
    catch (IOException localIOException) {}
    return this.connection.getErrorStream();
  }
  
  public String getReasonPhrase()
    throws Exception
  {
    return this.connection.getResponseMessage();
  }
  
  public int getStatusCode()
    throws IOException
  {
    return this.connection.getResponseCode();
  }
  
  public Object unwrap()
  {
    return this.connection;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/oauth/signpost/basic/HttpURLConnectionResponseAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */