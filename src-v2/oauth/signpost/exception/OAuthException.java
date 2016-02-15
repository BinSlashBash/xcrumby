/*
 * Decompiled with CFR 0_110.
 */
package oauth.signpost.exception;

public abstract class OAuthException
extends Exception {
    public OAuthException(String string2) {
        super(string2);
    }

    public OAuthException(String string2, Throwable throwable) {
        super(string2, throwable);
    }

    public OAuthException(Throwable throwable) {
        super(throwable);
    }
}

