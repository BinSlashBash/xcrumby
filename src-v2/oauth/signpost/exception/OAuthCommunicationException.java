/*
 * Decompiled with CFR 0_110.
 */
package oauth.signpost.exception;

import oauth.signpost.exception.OAuthException;

public class OAuthCommunicationException
extends OAuthException {
    private String responseBody;

    public OAuthCommunicationException(Exception exception) {
        super("Communication with the service provider failed: " + exception.getLocalizedMessage(), exception);
    }

    public OAuthCommunicationException(String string2, String string3) {
        super(string2);
        this.responseBody = string3;
    }

    public String getResponseBody() {
        return this.responseBody;
    }
}

