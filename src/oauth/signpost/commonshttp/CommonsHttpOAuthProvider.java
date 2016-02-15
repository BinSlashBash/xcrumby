package oauth.signpost.commonshttp;

import java.io.IOException;
import oauth.signpost.AbstractOAuthProvider;
import oauth.signpost.http.HttpRequest;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

public class CommonsHttpOAuthProvider
  extends AbstractOAuthProvider
{
  private static final long serialVersionUID = 1L;
  private transient HttpClient httpClient;
  
  public CommonsHttpOAuthProvider(String paramString1, String paramString2, String paramString3)
  {
    super(paramString1, paramString2, paramString3);
    this.httpClient = new DefaultHttpClient();
  }
  
  public CommonsHttpOAuthProvider(String paramString1, String paramString2, String paramString3, HttpClient paramHttpClient)
  {
    super(paramString1, paramString2, paramString3);
    this.httpClient = paramHttpClient;
  }
  
  protected void closeConnection(HttpRequest paramHttpRequest, oauth.signpost.http.HttpResponse paramHttpResponse)
    throws Exception
  {
    if (paramHttpResponse != null)
    {
      paramHttpRequest = ((org.apache.http.HttpResponse)paramHttpResponse.unwrap()).getEntity();
      if (paramHttpRequest == null) {}
    }
    try
    {
      paramHttpRequest.consumeContent();
      return;
    }
    catch (IOException paramHttpRequest)
    {
      paramHttpRequest.printStackTrace();
    }
  }
  
  protected HttpRequest createRequest(String paramString)
    throws Exception
  {
    return new HttpRequestAdapter(new HttpPost(paramString));
  }
  
  protected oauth.signpost.http.HttpResponse sendRequest(HttpRequest paramHttpRequest)
    throws Exception
  {
    return new HttpResponseAdapter(this.httpClient.execute((HttpUriRequest)paramHttpRequest.unwrap()));
  }
  
  public void setHttpClient(HttpClient paramHttpClient)
  {
    this.httpClient = paramHttpClient;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/oauth/signpost/commonshttp/CommonsHttpOAuthProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */