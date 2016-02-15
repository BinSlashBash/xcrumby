package oauth.signpost;

import java.io.Serializable;
import java.util.Map;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import oauth.signpost.http.HttpParameters;

public abstract interface OAuthProvider
  extends Serializable
{
  public abstract String getAccessTokenEndpointUrl();
  
  public abstract String getAuthorizationWebsiteUrl();
  
  @Deprecated
  public abstract Map<String, String> getRequestHeaders();
  
  public abstract String getRequestTokenEndpointUrl();
  
  public abstract HttpParameters getResponseParameters();
  
  public abstract boolean isOAuth10a();
  
  public abstract void removeListener(OAuthProviderListener paramOAuthProviderListener);
  
  public abstract void retrieveAccessToken(OAuthConsumer paramOAuthConsumer, String paramString, String... paramVarArgs)
    throws OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthExpectationFailedException, OAuthCommunicationException;
  
  public abstract String retrieveRequestToken(OAuthConsumer paramOAuthConsumer, String paramString, String... paramVarArgs)
    throws OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthExpectationFailedException, OAuthCommunicationException;
  
  public abstract void setListener(OAuthProviderListener paramOAuthProviderListener);
  
  public abstract void setOAuth10a(boolean paramBoolean);
  
  @Deprecated
  public abstract void setRequestHeader(String paramString1, String paramString2);
  
  public abstract void setResponseParameters(HttpParameters paramHttpParameters);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/oauth/signpost/OAuthProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */