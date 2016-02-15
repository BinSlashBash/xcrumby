/*
 * Decompiled with CFR 0_110.
 */
package oauth.signpost.exception;

import oauth.signpost.exception.OAuthException;

public class OAuthNotAuthorizedException
extends OAuthException {
    private static final String ERROR = "Authorization failed (server replied with a 401). This can happen if the consumer key was not correct or the signatures did not match.";
    private String responseBody;

    public OAuthNotAuthorizedException() {
        super("Authorization failed (server replied with a 401). This can happen if the consumer key was not correct or the signatures did not match.");
    }

    public OAuthNotAuthorizedException(String string2) {
        super("Authorization failed (server replied with a 401). This can happen if the consumer key was not correct or the signatures did not match.");
        this.responseBody = string2;
    }

    public String getResponseBody() {
        return this.responseBody;
    }
}

