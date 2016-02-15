/*
 * Decompiled with CFR 0_110.
 */
package oauth.signpost;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.OAuthProviderListener;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.http.HttpRequest;
import oauth.signpost.http.HttpResponse;

public abstract class AbstractOAuthProvider
implements OAuthProvider {
    private static final long serialVersionUID = 1;
    private String accessTokenEndpointUrl;
    private String authorizationWebsiteUrl;
    private Map<String, String> defaultHeaders;
    private boolean isOAuth10a;
    private transient OAuthProviderListener listener;
    private String requestTokenEndpointUrl;
    private HttpParameters responseParameters;

    public AbstractOAuthProvider(String string2, String string3, String string4) {
        this.requestTokenEndpointUrl = string2;
        this.accessTokenEndpointUrl = string3;
        this.authorizationWebsiteUrl = string4;
        this.responseParameters = new HttpParameters();
        this.defaultHeaders = new HashMap<String, String>();
    }

    protected void closeConnection(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
    }

    protected abstract HttpRequest createRequest(String var1) throws Exception;

    @Override
    public String getAccessTokenEndpointUrl() {
        return this.accessTokenEndpointUrl;
    }

    @Override
    public String getAuthorizationWebsiteUrl() {
        return this.authorizationWebsiteUrl;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        return this.defaultHeaders;
    }

    @Override
    public String getRequestTokenEndpointUrl() {
        return this.requestTokenEndpointUrl;
    }

    protected String getResponseParameter(String string2) {
        return this.responseParameters.getFirst(string2);
    }

    @Override
    public HttpParameters getResponseParameters() {
        return this.responseParameters;
    }

    protected void handleUnexpectedResponse(int n2, HttpResponse httpResponse) throws Exception {
        if (httpResponse == null) {
            return;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getContent()));
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = bufferedReader.readLine();
        while (string2 != null) {
            stringBuilder.append(string2);
            string2 = bufferedReader.readLine();
        }
        switch (n2) {
            default: {
                throw new OAuthCommunicationException("Service provider responded in error: " + n2 + " (" + httpResponse.getReasonPhrase() + ")", stringBuilder.toString());
            }
            case 401: 
        }
        throw new OAuthNotAuthorizedException(stringBuilder.toString());
    }

    @Override
    public boolean isOAuth10a() {
        return this.isOAuth10a;
    }

    @Override
    public void removeListener(OAuthProviderListener oAuthProviderListener) {
        this.listener = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public /* varargs */ void retrieveAccessToken(OAuthConsumer oAuthConsumer, String string2, String ... arrstring) throws OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthExpectationFailedException, OAuthCommunicationException {
        synchronized (this) {
            if (oAuthConsumer.getToken() == null || oAuthConsumer.getTokenSecret() == null) {
                throw new OAuthExpectationFailedException("Authorized request token or token secret not set. Did you retrieve an authorized request token before?");
            }
            HttpParameters httpParameters = new HttpParameters();
            httpParameters.putAll(arrstring, true);
            if (this.isOAuth10a && string2 != null) {
                httpParameters.put("oauth_verifier", string2, true);
            }
            this.retrieveToken(oAuthConsumer, this.accessTokenEndpointUrl, httpParameters);
            return;
        }
    }

    @Override
    public /* varargs */ String retrieveRequestToken(OAuthConsumer object, String string2, String ... object2) throws OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthExpectationFailedException, OAuthCommunicationException {
        synchronized (this) {
            object.setTokenWithSecret(null, null);
            HttpParameters httpParameters = new HttpParameters();
            httpParameters.putAll((String[])object2, true);
            httpParameters.put("oauth_callback", string2, true);
            this.retrieveToken((OAuthConsumer)object, this.requestTokenEndpointUrl, httpParameters);
            object2 = this.responseParameters.getFirst("oauth_callback_confirmed");
            this.responseParameters.remove("oauth_callback_confirmed");
            this.isOAuth10a = Boolean.TRUE.toString().equals(object2);
            if (this.isOAuth10a) {
                object = OAuth.addQueryParameters(this.authorizationWebsiteUrl, "oauth_token", object.getToken());
                return object;
            }
            object = OAuth.addQueryParameters(this.authorizationWebsiteUrl, "oauth_token", object.getToken(), "oauth_callback", string2);
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected void retrieveToken(OAuthConsumer var1_1, String var2_9, HttpParameters var3_10) throws OAuthMessageSignerException, OAuthCommunicationException, OAuthNotAuthorizedException, OAuthExpectationFailedException {
        block25 : {
            var18_11 = this.getRequestHeaders();
            if (var1_1.getConsumerKey() == null) throw new OAuthExpectationFailedException("Consumer key or secret not set");
            if (var1_1.getConsumerSecret() == null) {
                throw new OAuthExpectationFailedException("Consumer key or secret not set");
            }
            var6_12 = null;
            var10_13 = null;
            var12_14 = null;
            var8_15 = null;
            var15_16 = null;
            var16_17 = null;
            var17_18 = null;
            var9_20 = var14_19 = null;
            var7_21 = var15_16;
            var11_22 = var16_17;
            var13_23 = var17_18;
            var8_15 = var2_9 = this.createRequest((String)var2_9);
            var9_20 = var14_19;
            var6_12 = var2_9;
            var7_21 = var15_16;
            var10_13 = var2_9;
            var11_22 = var16_17;
            var12_14 = var2_9;
            var13_23 = var17_18;
            var19_24 = var18_11.keySet().iterator();
            do {
                var8_15 = var2_9;
                var9_20 = var14_19;
                var6_12 = var2_9;
                var7_21 = var15_16;
                var10_13 = var2_9;
                var11_22 = var16_17;
                var12_14 = var2_9;
                var13_23 = var17_18;
                if (!var19_24.hasNext()) break;
                var8_15 = var2_9;
                var9_20 = var14_19;
                var6_12 = var2_9;
                var7_21 = var15_16;
                var10_13 = var2_9;
                var11_22 = var16_17;
                var12_14 = var2_9;
                var13_23 = var17_18;
                var20_25 = var19_24.next();
                var8_15 = var2_9;
                var9_20 = var14_19;
                var6_12 = var2_9;
                var7_21 = var15_16;
                var10_13 = var2_9;
                var11_22 = var16_17;
                var12_14 = var2_9;
                var13_23 = var17_18;
                var2_9.setHeader(var20_25, var18_11.get(var20_25));
            } while (true);
            if (var3_10 != null) {
                var8_15 = var2_9;
                var9_20 = var14_19;
                var6_12 = var2_9;
                var7_21 = var15_16;
                var10_13 = var2_9;
                var11_22 = var16_17;
                var12_14 = var2_9;
                var13_23 = var17_18;
                if (!var3_10.isEmpty()) {
                    var8_15 = var2_9;
                    var9_20 = var14_19;
                    var6_12 = var2_9;
                    var7_21 = var15_16;
                    var10_13 = var2_9;
                    var11_22 = var16_17;
                    var12_14 = var2_9;
                    var13_23 = var17_18;
                    var1_1.setAdditionalParameters((HttpParameters)var3_10);
                }
            }
            var8_15 = var2_9;
            var9_20 = var14_19;
            var6_12 = var2_9;
            var7_21 = var15_16;
            var10_13 = var2_9;
            var11_22 = var16_17;
            var12_14 = var2_9;
            var13_23 = var17_18;
            if (this.listener != null) {
                var8_15 = var2_9;
                var9_20 = var14_19;
                var6_12 = var2_9;
                var7_21 = var15_16;
                var10_13 = var2_9;
                var11_22 = var16_17;
                var12_14 = var2_9;
                var13_23 = var17_18;
                this.listener.prepareRequest((HttpRequest)var2_9);
            }
            var8_15 = var2_9;
            var9_20 = var14_19;
            var6_12 = var2_9;
            var7_21 = var15_16;
            var10_13 = var2_9;
            var11_22 = var16_17;
            var12_14 = var2_9;
            var13_23 = var17_18;
            var1_1.sign((HttpRequest)var2_9);
            var8_15 = var2_9;
            var9_20 = var14_19;
            var6_12 = var2_9;
            var7_21 = var15_16;
            var10_13 = var2_9;
            var11_22 = var16_17;
            var12_14 = var2_9;
            var13_23 = var17_18;
            if (this.listener != null) {
                var8_15 = var2_9;
                var9_20 = var14_19;
                var6_12 = var2_9;
                var7_21 = var15_16;
                var10_13 = var2_9;
                var11_22 = var16_17;
                var12_14 = var2_9;
                var13_23 = var17_18;
                this.listener.prepareSubmission((HttpRequest)var2_9);
            }
            var8_15 = var2_9;
            var9_20 = var14_19;
            var6_12 = var2_9;
            var7_21 = var15_16;
            var10_13 = var2_9;
            var11_22 = var16_17;
            var12_14 = var2_9;
            var13_23 = var17_18;
            var3_10 = this.sendRequest((HttpRequest)var2_9);
            var8_15 = var2_9;
            var9_20 = var3_10;
            var6_12 = var2_9;
            var7_21 = var3_10;
            var10_13 = var2_9;
            var11_22 = var3_10;
            var12_14 = var2_9;
            var13_23 = var3_10;
            var4_26 = var3_10.getStatusCode();
            var5_27 = false;
            var8_15 = var2_9;
            var9_20 = var3_10;
            var6_12 = var2_9;
            var7_21 = var3_10;
            var10_13 = var2_9;
            var11_22 = var3_10;
            var12_14 = var2_9;
            var13_23 = var3_10;
            if (this.listener != null) {
                var8_15 = var2_9;
                var9_20 = var3_10;
                var6_12 = var2_9;
                var7_21 = var3_10;
                var10_13 = var2_9;
                var11_22 = var3_10;
                var12_14 = var2_9;
                var13_23 = var3_10;
                var5_27 = this.listener.onResponseReceived((HttpRequest)var2_9, (HttpResponse)var3_10);
            }
            if (!var5_27) break block25;
            try {
                this.closeConnection((HttpRequest)var2_9, (HttpResponse)var3_10);
                return;
            }
            catch (Exception var1_4) {
                throw new OAuthCommunicationException(var1_4);
            }
        }
        if (var4_26 < 300) ** GOTO lbl176
        var8_15 = var2_9;
        var9_20 = var3_10;
        var6_12 = var2_9;
        var7_21 = var3_10;
        var10_13 = var2_9;
        var11_22 = var3_10;
        var12_14 = var2_9;
        var13_23 = var3_10;
        this.handleUnexpectedResponse(var4_26, (HttpResponse)var3_10);
lbl176: // 2 sources:
        var8_15 = var2_9;
        var9_20 = var3_10;
        var6_12 = var2_9;
        var7_21 = var3_10;
        var10_13 = var2_9;
        var11_22 = var3_10;
        var12_14 = var2_9;
        var13_23 = var3_10;
        var14_19 = OAuth.decodeForm(var3_10.getContent());
        var8_15 = var2_9;
        var9_20 = var3_10;
        var6_12 = var2_9;
        var7_21 = var3_10;
        var10_13 = var2_9;
        var11_22 = var3_10;
        var12_14 = var2_9;
        var13_23 = var3_10;
        var15_16 = var14_19.getFirst("oauth_token");
        var8_15 = var2_9;
        var9_20 = var3_10;
        var6_12 = var2_9;
        var7_21 = var3_10;
        var10_13 = var2_9;
        var11_22 = var3_10;
        var12_14 = var2_9;
        var13_23 = var3_10;
        var16_17 = var14_19.getFirst("oauth_token_secret");
        var8_15 = var2_9;
        var9_20 = var3_10;
        var6_12 = var2_9;
        var7_21 = var3_10;
        var10_13 = var2_9;
        var11_22 = var3_10;
        var12_14 = var2_9;
        var13_23 = var3_10;
        var14_19.remove("oauth_token");
        var8_15 = var2_9;
        var9_20 = var3_10;
        var6_12 = var2_9;
        var7_21 = var3_10;
        var10_13 = var2_9;
        var11_22 = var3_10;
        var12_14 = var2_9;
        var13_23 = var3_10;
        var14_19.remove("oauth_token_secret");
        var8_15 = var2_9;
        var9_20 = var3_10;
        var6_12 = var2_9;
        var7_21 = var3_10;
        var10_13 = var2_9;
        var11_22 = var3_10;
        var12_14 = var2_9;
        var13_23 = var3_10;
        this.setResponseParameters((HttpParameters)var14_19);
        if (var15_16 == null || var16_17 == null) {
            var8_15 = var2_9;
            var9_20 = var3_10;
            var6_12 = var2_9;
            var7_21 = var3_10;
            var10_13 = var2_9;
            var11_22 = var3_10;
            var12_14 = var2_9;
            var13_23 = var3_10;
            throw new OAuthExpectationFailedException("Request token or token secret not set in server reply. The service provider you use is probably buggy.");
        }
        var8_15 = var2_9;
        var9_20 = var3_10;
        var6_12 = var2_9;
        var7_21 = var3_10;
        var10_13 = var2_9;
        var11_22 = var3_10;
        var12_14 = var2_9;
        var13_23 = var3_10;
        try {
            var1_1.setTokenWithSecret((String)var15_16, (String)var16_17);
        }
        catch (OAuthNotAuthorizedException var1_2) {
            var6_12 = var8_15;
            var7_21 = var9_20;
            throw var1_2;
        }
        try {
            this.closeConnection((HttpRequest)var2_9, (HttpResponse)var3_10);
            return;
        }
        catch (Exception var1_6) {
            throw new OAuthCommunicationException(var1_6);
        }
        {
            catch (Throwable var1_3) {
                try {
                    this.closeConnection((HttpRequest)var6_12, (HttpResponse)var7_21);
                }
                catch (Exception var1_8) {
                    throw new OAuthCommunicationException(var1_8);
                }
                throw var1_3;
                catch (OAuthExpectationFailedException var1_5) {
                    var6_12 = var10_13;
                    var7_21 = var11_22;
                    throw var1_5;
                }
                catch (Exception var1_7) {}
                var6_12 = var12_14;
                var7_21 = var13_23;
                {
                    throw new OAuthCommunicationException(var1_7);
                }
            }
        }
    }

    protected abstract HttpResponse sendRequest(HttpRequest var1) throws Exception;

    @Override
    public void setListener(OAuthProviderListener oAuthProviderListener) {
        this.listener = oAuthProviderListener;
    }

    @Override
    public void setOAuth10a(boolean bl2) {
        this.isOAuth10a = bl2;
    }

    @Override
    public void setRequestHeader(String string2, String string3) {
        this.defaultHeaders.put(string2, string3);
    }

    @Override
    public void setResponseParameters(HttpParameters httpParameters) {
        this.responseParameters = httpParameters;
    }
}

