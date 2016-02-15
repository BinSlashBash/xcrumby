/*
 * Decompiled with CFR 0_110.
 */
package oauth.signpost;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Random;
import java.util.SortedSet;
import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
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
implements OAuthConsumer {
    private static final long serialVersionUID = 1;
    private HttpParameters additionalParameters;
    private String consumerKey;
    private String consumerSecret;
    private OAuthMessageSigner messageSigner;
    private final Random random = new Random(System.nanoTime());
    private HttpParameters requestParameters;
    private boolean sendEmptyTokens;
    private SigningStrategy signingStrategy;
    private String token;

    public AbstractOAuthConsumer(String string2, String string3) {
        this.consumerKey = string2;
        this.consumerSecret = string3;
        this.setMessageSigner(new HmacSha1MessageSigner());
        this.setSigningStrategy(new AuthorizationHeaderSigningStrategy());
    }

    protected void collectBodyParameters(HttpRequest httpRequest, HttpParameters httpParameters) throws IOException {
        String string2 = httpRequest.getContentType();
        if (string2 != null && string2.startsWith("application/x-www-form-urlencoded")) {
            httpParameters.putAll(OAuth.decodeForm(httpRequest.getMessagePayload()), true);
        }
    }

    protected void collectHeaderParameters(HttpRequest httpRequest, HttpParameters httpParameters) {
        httpParameters.putAll(OAuth.oauthHeaderToParamsMap(httpRequest.getHeader("Authorization")), false);
    }

    protected void collectQueryParameters(HttpRequest object, HttpParameters httpParameters) {
        int n2 = (object = object.getRequestUrl()).indexOf(63);
        if (n2 >= 0) {
            httpParameters.putAll(OAuth.decodeForm(object.substring(n2 + 1)), true);
        }
    }

    protected void completeOAuthParameters(HttpParameters httpParameters) {
        if (!httpParameters.containsKey("oauth_consumer_key")) {
            httpParameters.put("oauth_consumer_key", this.consumerKey, true);
        }
        if (!httpParameters.containsKey("oauth_signature_method")) {
            httpParameters.put("oauth_signature_method", this.messageSigner.getSignatureMethod(), true);
        }
        if (!httpParameters.containsKey("oauth_timestamp")) {
            httpParameters.put("oauth_timestamp", this.generateTimestamp(), true);
        }
        if (!httpParameters.containsKey("oauth_nonce")) {
            httpParameters.put("oauth_nonce", this.generateNonce(), true);
        }
        if (!httpParameters.containsKey("oauth_version")) {
            httpParameters.put("oauth_version", "1.0", true);
        }
        if (!httpParameters.containsKey("oauth_token") && (this.token != null && !this.token.equals("") || this.sendEmptyTokens)) {
            httpParameters.put("oauth_token", this.token, true);
        }
    }

    protected String generateNonce() {
        return Long.toString(this.random.nextLong());
    }

    protected String generateTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    @Override
    public String getConsumerKey() {
        return this.consumerKey;
    }

    @Override
    public String getConsumerSecret() {
        return this.consumerSecret;
    }

    @Override
    public HttpParameters getRequestParameters() {
        return this.requestParameters;
    }

    @Override
    public String getToken() {
        return this.token;
    }

    @Override
    public String getTokenSecret() {
        return this.messageSigner.getTokenSecret();
    }

    @Override
    public void setAdditionalParameters(HttpParameters httpParameters) {
        this.additionalParameters = httpParameters;
    }

    @Override
    public void setMessageSigner(OAuthMessageSigner oAuthMessageSigner) {
        this.messageSigner = oAuthMessageSigner;
        oAuthMessageSigner.setConsumerSecret(this.consumerSecret);
    }

    @Override
    public void setSendEmptyTokens(boolean bl2) {
        this.sendEmptyTokens = bl2;
    }

    @Override
    public void setSigningStrategy(SigningStrategy signingStrategy) {
        this.signingStrategy = signingStrategy;
    }

    @Override
    public void setTokenWithSecret(String string2, String string3) {
        this.token = string2;
        this.messageSigner.setTokenSecret(string3);
    }

    @Override
    public String sign(String object) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {
        synchronized (this) {
            object = new UrlStringRequestAdapter((String)object);
            SigningStrategy signingStrategy = this.signingStrategy;
            this.signingStrategy = new QueryStringSigningStrategy();
            this.sign((HttpRequest)object);
            this.signingStrategy = signingStrategy;
            object = object.getRequestUrl();
            return object;
        }
    }

    @Override
    public HttpRequest sign(Object object) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {
        synchronized (this) {
            object = this.sign(this.wrap(object));
            return object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public HttpRequest sign(HttpRequest httpRequest) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {
        synchronized (this) {
            String string2;
            if (this.consumerKey == null) {
                throw new OAuthExpectationFailedException("consumer key not set");
            }
            if (this.consumerSecret == null) {
                throw new OAuthExpectationFailedException("consumer secret not set");
            }
            this.requestParameters = new HttpParameters();
            try {
                if (this.additionalParameters != null) {
                    this.requestParameters.putAll(this.additionalParameters, false);
                }
                this.collectHeaderParameters(httpRequest, this.requestParameters);
                this.collectQueryParameters(httpRequest, this.requestParameters);
                this.collectBodyParameters(httpRequest, this.requestParameters);
                this.completeOAuthParameters(this.requestParameters);
                this.requestParameters.remove("oauth_signature");
                string2 = this.messageSigner.sign(httpRequest, this.requestParameters);
            }
            catch (IOException var1_2) {
                throw new OAuthCommunicationException(var1_2);
            }
            OAuth.debugOut("signature", string2);
            this.signingStrategy.writeSignature(string2, httpRequest, this.requestParameters);
            OAuth.debugOut("Request URL", httpRequest.getRequestUrl());
            return httpRequest;
        }
    }

    protected abstract HttpRequest wrap(Object var1);
}

