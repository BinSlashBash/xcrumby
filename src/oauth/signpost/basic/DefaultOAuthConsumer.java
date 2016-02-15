package oauth.signpost.basic;

import java.net.HttpURLConnection;
import oauth.signpost.AbstractOAuthConsumer;
import oauth.signpost.http.HttpRequest;

public class DefaultOAuthConsumer
  extends AbstractOAuthConsumer
{
  private static final long serialVersionUID = 1L;
  
  public DefaultOAuthConsumer(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }
  
  protected HttpRequest wrap(Object paramObject)
  {
    if (!(paramObject instanceof HttpURLConnection)) {
      throw new IllegalArgumentException("The default consumer expects requests of type java.net.HttpURLConnection");
    }
    return new HttpURLConnectionRequestAdapter((HttpURLConnection)paramObject);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/oauth/signpost/basic/DefaultOAuthConsumer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */