package oauth.signpost;

import oauth.signpost.http.HttpRequest;
import oauth.signpost.http.HttpResponse;

public abstract interface OAuthProviderListener
{
  public abstract boolean onResponseReceived(HttpRequest paramHttpRequest, HttpResponse paramHttpResponse)
    throws Exception;
  
  public abstract void prepareRequest(HttpRequest paramHttpRequest)
    throws Exception;
  
  public abstract void prepareSubmission(HttpRequest paramHttpRequest)
    throws Exception;
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/oauth/signpost/OAuthProviderListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */