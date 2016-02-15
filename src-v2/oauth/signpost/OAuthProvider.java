/*
 * Decompiled with CFR 0_110.
 */
package oauth.signpost;

import java.io.Serializable;
import java.util.Map;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProviderListener;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import oauth.signpost.http.HttpParameters;

public interface OAuthProvider
extends Serializable {
    public String getAccessTokenEndpointUrl();

    public String getAuthorizationWebsiteUrl();

    @Deprecated
    public Map<String, String> getRequestHeaders();

    public String getRequestTokenEndpointUrl();

    public HttpParameters getResponseParameters();

    public boolean isOAuth10a();

    public void removeListener(OAuthProviderListener var1);

    public /* varargs */ void retrieveAccessToken(OAuthConsumer var1, String var2, String ... var3) throws OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthExpectationFailedException, OAuthCommunicationException;

    public /* varargs */ String retrieveRequestToken(OAuthConsumer var1, String var2, String ... var3) throws OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthExpectationFailedException, OAuthCommunicationException;

    public void setListener(OAuthProviderListener var1);

    public void setOAuth10a(boolean var1);

    @Deprecated
    public void setRequestHeader(String var1, String var2);

    public void setResponseParameters(HttpParameters var1);
}

