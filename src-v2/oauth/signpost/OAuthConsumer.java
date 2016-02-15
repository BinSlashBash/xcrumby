/*
 * Decompiled with CFR 0_110.
 */
package oauth.signpost;

import java.io.Serializable;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.http.HttpRequest;
import oauth.signpost.signature.OAuthMessageSigner;
import oauth.signpost.signature.SigningStrategy;

public interface OAuthConsumer
extends Serializable {
    public String getConsumerKey();

    public String getConsumerSecret();

    public HttpParameters getRequestParameters();

    public String getToken();

    public String getTokenSecret();

    public void setAdditionalParameters(HttpParameters var1);

    public void setMessageSigner(OAuthMessageSigner var1);

    public void setSendEmptyTokens(boolean var1);

    public void setSigningStrategy(SigningStrategy var1);

    public void setTokenWithSecret(String var1, String var2);

    public String sign(String var1) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException;

    public HttpRequest sign(Object var1) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException;

    public HttpRequest sign(HttpRequest var1) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException;
}

