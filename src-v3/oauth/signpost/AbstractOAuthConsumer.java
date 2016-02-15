package oauth.signpost;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
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

public abstract class AbstractOAuthConsumer implements OAuthConsumer {
    private static final long serialVersionUID = 1;
    private HttpParameters additionalParameters;
    private String consumerKey;
    private String consumerSecret;
    private OAuthMessageSigner messageSigner;
    private final Random random;
    private HttpParameters requestParameters;
    private boolean sendEmptyTokens;
    private SigningStrategy signingStrategy;
    private String token;

    protected abstract HttpRequest wrap(Object obj);

    public AbstractOAuthConsumer(String consumerKey, String consumerSecret) {
        this.random = new Random(System.nanoTime());
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        setMessageSigner(new HmacSha1MessageSigner());
        setSigningStrategy(new AuthorizationHeaderSigningStrategy());
    }

    public void setMessageSigner(OAuthMessageSigner messageSigner) {
        this.messageSigner = messageSigner;
        messageSigner.setConsumerSecret(this.consumerSecret);
    }

    public void setSigningStrategy(SigningStrategy signingStrategy) {
        this.signingStrategy = signingStrategy;
    }

    public void setAdditionalParameters(HttpParameters additionalParameters) {
        this.additionalParameters = additionalParameters;
    }

    public synchronized HttpRequest sign(HttpRequest request) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {
        if (this.consumerKey == null) {
            throw new OAuthExpectationFailedException("consumer key not set");
        } else if (this.consumerSecret == null) {
            throw new OAuthExpectationFailedException("consumer secret not set");
        } else {
            this.requestParameters = new HttpParameters();
            try {
                if (this.additionalParameters != null) {
                    this.requestParameters.putAll(this.additionalParameters, false);
                }
                collectHeaderParameters(request, this.requestParameters);
                collectQueryParameters(request, this.requestParameters);
                collectBodyParameters(request, this.requestParameters);
                completeOAuthParameters(this.requestParameters);
                this.requestParameters.remove(OAuth.OAUTH_SIGNATURE);
                String signature = this.messageSigner.sign(request, this.requestParameters);
                OAuth.debugOut("signature", signature);
                this.signingStrategy.writeSignature(signature, request, this.requestParameters);
                OAuth.debugOut("Request URL", request.getRequestUrl());
            } catch (IOException e) {
                throw new OAuthCommunicationException(e);
            }
        }
        return request;
    }

    public synchronized HttpRequest sign(Object request) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {
        return sign(wrap(request));
    }

    public synchronized String sign(String url) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {
        HttpRequest request;
        request = new UrlStringRequestAdapter(url);
        SigningStrategy oldStrategy = this.signingStrategy;
        this.signingStrategy = new QueryStringSigningStrategy();
        sign(request);
        this.signingStrategy = oldStrategy;
        return request.getRequestUrl();
    }

    public void setTokenWithSecret(String token, String tokenSecret) {
        this.token = token;
        this.messageSigner.setTokenSecret(tokenSecret);
    }

    public String getToken() {
        return this.token;
    }

    public String getTokenSecret() {
        return this.messageSigner.getTokenSecret();
    }

    public String getConsumerKey() {
        return this.consumerKey;
    }

    public String getConsumerSecret() {
        return this.consumerSecret;
    }

    protected void completeOAuthParameters(HttpParameters out) {
        if (!out.containsKey(OAuth.OAUTH_CONSUMER_KEY)) {
            out.put(OAuth.OAUTH_CONSUMER_KEY, this.consumerKey, true);
        }
        if (!out.containsKey(OAuth.OAUTH_SIGNATURE_METHOD)) {
            out.put(OAuth.OAUTH_SIGNATURE_METHOD, this.messageSigner.getSignatureMethod(), true);
        }
        if (!out.containsKey(OAuth.OAUTH_TIMESTAMP)) {
            out.put(OAuth.OAUTH_TIMESTAMP, generateTimestamp(), true);
        }
        if (!out.containsKey(OAuth.OAUTH_NONCE)) {
            out.put(OAuth.OAUTH_NONCE, generateNonce(), true);
        }
        if (!out.containsKey(OAuth.OAUTH_VERSION)) {
            out.put(OAuth.OAUTH_VERSION, OAuth.VERSION_1_0, true);
        }
        if (!out.containsKey(OAuth.OAUTH_TOKEN)) {
            if ((this.token != null && !this.token.equals(UnsupportedUrlFragment.DISPLAY_NAME)) || this.sendEmptyTokens) {
                out.put(OAuth.OAUTH_TOKEN, this.token, true);
            }
        }
    }

    public HttpParameters getRequestParameters() {
        return this.requestParameters;
    }

    public void setSendEmptyTokens(boolean enable) {
        this.sendEmptyTokens = enable;
    }

    protected void collectHeaderParameters(HttpRequest request, HttpParameters out) {
        out.putAll(OAuth.oauthHeaderToParamsMap(request.getHeader(OAuth.HTTP_AUTHORIZATION_HEADER)), false);
    }

    protected void collectBodyParameters(HttpRequest request, HttpParameters out) throws IOException {
        String contentType = request.getContentType();
        if (contentType != null && contentType.startsWith(OAuth.FORM_ENCODED)) {
            out.putAll(OAuth.decodeForm(request.getMessagePayload()), true);
        }
    }

    protected void collectQueryParameters(HttpRequest request, HttpParameters out) {
        String url = request.getRequestUrl();
        int q = url.indexOf(63);
        if (q >= 0) {
            out.putAll(OAuth.decodeForm(url.substring(q + 1)), true);
        }
    }

    protected String generateTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    protected String generateNonce() {
        return Long.toString(this.random.nextLong());
    }
}
