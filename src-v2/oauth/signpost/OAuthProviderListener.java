/*
 * Decompiled with CFR 0_110.
 */
package oauth.signpost;

import oauth.signpost.http.HttpRequest;
import oauth.signpost.http.HttpResponse;

public interface OAuthProviderListener {
    public boolean onResponseReceived(HttpRequest var1, HttpResponse var2) throws Exception;

    public void prepareRequest(HttpRequest var1) throws Exception;

    public void prepareSubmission(HttpRequest var1) throws Exception;
}

