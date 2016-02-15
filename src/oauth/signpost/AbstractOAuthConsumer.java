package oauth.signpost;

import java.io.IOException;
import java.util.Random;
import oauth.signpost.basic.UrlStringRequestAdapter;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.http.HttpRequest;
import oauth.signpost.signature.AuthorizationHeaderSigningStrategy;
import oauth.signpost.signature.HmacSha1MessageSigner;
import oauth.signpost.signature.OAuthMessageSigner;
import oauth.signpost.signature.QueryStringSigningStrategy;
import oauth.signpost.signature.SigningStrategy;

public abstract class AbstractOAuthConsumer
  implements OAuthConsumer
{
  private static final long serialVersionUID = 1L;
  private HttpParameters additionalParameters;
  private String consumerKey;
  private String consumerSecret;
  private OAuthMessageSigner messageSigner;
  private final Random random = new Random(System.nanoTime());
  private HttpParameters requestParameters;
  private boolean sendEmptyTokens;
  private SigningStrategy signingStrategy;
  private String token;
  
  public AbstractOAuthConsumer(String paramString1, String paramString2)
  {
    this.consumerKey = paramString1;
    this.consumerSecret = paramString2;
    setMessageSigner(new HmacSha1MessageSigner());
    setSigningStrategy(new AuthorizationHeaderSigningStrategy());
  }
  
  protected void collectBodyParameters(HttpRequest paramHttpRequest, HttpParameters paramHttpParameters)
    throws IOException
  {
    String str = paramHttpRequest.getContentType();
    if ((str != null) && (str.startsWith("application/x-www-form-urlencoded"))) {
      paramHttpParameters.putAll(OAuth.decodeForm(paramHttpRequest.getMessagePayload()), true);
    }
  }
  
  protected void collectHeaderParameters(HttpRequest paramHttpRequest, HttpParameters paramHttpParameters)
  {
    paramHttpParameters.putAll(OAuth.oauthHeaderToParamsMap(paramHttpRequest.getHeader("Authorization")), false);
  }
  
  protected void collectQueryParameters(HttpRequest paramHttpRequest, HttpParameters paramHttpParameters)
  {
    paramHttpRequest = paramHttpRequest.getRequestUrl();
    int i = paramHttpRequest.indexOf('?');
    if (i >= 0) {
      paramHttpParameters.putAll(OAuth.decodeForm(paramHttpRequest.substring(i + 1)), true);
    }
  }
  
  protected void completeOAuthParameters(HttpParameters paramHttpParameters)
  {
    if (!paramHttpParameters.containsKey("oauth_consumer_key")) {
      paramHttpParameters.put("oauth_consumer_key", this.consumerKey, true);
    }
    if (!paramHttpParameters.containsKey("oauth_signature_method")) {
      paramHttpParameters.put("oauth_signature_method", this.messageSigner.getSignatureMethod(), true);
    }
    if (!paramHttpParameters.containsKey("oauth_timestamp")) {
      paramHttpParameters.put("oauth_timestamp", generateTimestamp(), true);
    }
    if (!paramHttpParameters.containsKey("oauth_nonce")) {
      paramHttpParameters.put("oauth_nonce", generateNonce(), true);
    }
    if (!paramHttpParameters.containsKey("oauth_version")) {
      paramHttpParameters.put("oauth_version", "1.0", true);
    }
    if ((!paramHttpParameters.containsKey("oauth_token")) && (((this.token != null) && (!this.token.equals(""))) || (this.sendEmptyTokens))) {
      paramHttpParameters.put("oauth_token", this.token, true);
    }
  }
  
  protected String generateNonce()
  {
    return Long.toString(this.random.nextLong());
  }
  
  protected String generateTimestamp()
  {
    return Long.toString(System.currentTimeMillis() / 1000L);
  }
  
  public String getConsumerKey()
  {
    return this.consumerKey;
  }
  
  public String getConsumerSecret()
  {
    return this.consumerSecret;
  }
  
  public HttpParameters getRequestParameters()
  {
    return this.requestParameters;
  }
  
  public String getToken()
  {
    return this.token;
  }
  
  public String getTokenSecret()
  {
    return this.messageSigner.getTokenSecret();
  }
  
  public void setAdditionalParameters(HttpParameters paramHttpParameters)
  {
    this.additionalParameters = paramHttpParameters;
  }
  
  public void setMessageSigner(OAuthMessageSigner paramOAuthMessageSigner)
  {
    this.messageSigner = paramOAuthMessageSigner;
    paramOAuthMessageSigner.setConsumerSecret(this.consumerSecret);
  }
  
  public void setSendEmptyTokens(boolean paramBoolean)
  {
    this.sendEmptyTokens = paramBoolean;
  }
  
  public void setSigningStrategy(SigningStrategy paramSigningStrategy)
  {
    this.signingStrategy = paramSigningStrategy;
  }
  
  public void setTokenWithSecret(String paramString1, String paramString2)
  {
    this.token = paramString1;
    this.messageSigner.setTokenSecret(paramString2);
  }
  
  public String sign(String paramString)
    throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException
  {
    try
    {
      paramString = new UrlStringRequestAdapter(paramString);
      SigningStrategy localSigningStrategy = this.signingStrategy;
      this.signingStrategy = new QueryStringSigningStrategy();
      sign(paramString);
      this.signingStrategy = localSigningStrategy;
      paramString = paramString.getRequestUrl();
      return paramString;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public HttpRequest sign(Object paramObject)
    throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException
  {
    try
    {
      paramObject = sign(wrap(paramObject));
      return (HttpRequest)paramObject;
    }
    finally
    {
      paramObject = finally;
      throw ((Throwable)paramObject);
    }
  }
  
  public HttpRequest sign(HttpRequest paramHttpRequest)
    throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException
  {
    try
    {
      if (this.consumerKey == null) {
        throw new OAuthExpectationFailedException("consumer key not set");
      }
    }
    finally {}
    if (this.consumerSecret == null) {
      throw new OAuthExpectationFailedException("consumer secret not set");
    }
    this.requestParameters = new HttpParameters();
    try
    {
      if (this.additionalParameters != null) {
        this.requestParameters.putAll(this.additionalParameters, false);
      }
      collectHeaderParameters(paramHttpRequest, this.requestParameters);
      collectQueryParameters(paramHttpRequest, this.requestParameters);
      collectBodyParameters(paramHttpRequest, this.requestParameters);
      completeOAuthParameters(this.requestParameters);
      this.requestParameters.remove("oauth_signature");
      String str = this.messageSigner.sign(paramHttpRequest, this.requestParameters);
      OAuth.debugOut("signature", str);
      this.signingStrategy.writeSignature(str, paramHttpRequest, this.requestParameters);
      OAuth.debugOut("Request URL", paramHttpRequest.getRequestUrl());
      return paramHttpRequest;
    }
    catch (IOException paramHttpRequest)
    {
      throw new OAuthCommunicationException(paramHttpRequest);
    }
  }
  
  protected abstract HttpRequest wrap(Object paramObject);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/oauth/signpost/AbstractOAuthConsumer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */