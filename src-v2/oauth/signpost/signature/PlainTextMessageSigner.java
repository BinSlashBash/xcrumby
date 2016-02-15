/*
 * Decompiled with CFR 0_110.
 */
package oauth.signpost.signature;

import oauth.signpost.OAuth;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.http.HttpRequest;
import oauth.signpost.signature.OAuthMessageSigner;

public class PlainTextMessageSigner
extends OAuthMessageSigner {
    @Override
    public String getSignatureMethod() {
        return "PLAINTEXT";
    }

    @Override
    public String sign(HttpRequest httpRequest, HttpParameters httpParameters) throws OAuthMessageSignerException {
        return OAuth.percentEncode(this.getConsumerSecret()) + '&' + OAuth.percentEncode(this.getTokenSecret());
    }
}

