/*
 * Decompiled with CFR 0_110.
 */
package oauth.signpost.exception;

import oauth.signpost.exception.OAuthException;

public class OAuthMessageSignerException
extends OAuthException {
    public OAuthMessageSignerException(Exception exception) {
        super(exception);
    }

    public OAuthMessageSignerException(String string2) {
        super(string2);
    }
}

