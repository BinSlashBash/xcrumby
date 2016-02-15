/*
 * Decompiled with CFR 0_110.
 */
package oauth.signpost.signature;

import java.io.Serializable;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.http.HttpRequest;

public interface SigningStrategy
extends Serializable {
    public String writeSignature(String var1, HttpRequest var2, HttpParameters var3);
}

