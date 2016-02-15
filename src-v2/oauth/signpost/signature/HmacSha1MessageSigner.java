/*
 * Decompiled with CFR 0_110.
 */
package oauth.signpost.signature;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import oauth.signpost.OAuth;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.http.HttpRequest;
import oauth.signpost.signature.OAuthMessageSigner;
import oauth.signpost.signature.SignatureBaseString;

public class HmacSha1MessageSigner
extends OAuthMessageSigner {
    private static final String MAC_NAME = "HmacSHA1";

    @Override
    public String getSignatureMethod() {
        return "HMAC-SHA1";
    }

    @Override
    public String sign(HttpRequest object, HttpParameters httpParameters) throws OAuthMessageSignerException {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec((OAuth.percentEncode(this.getConsumerSecret()) + '&' + OAuth.percentEncode(this.getTokenSecret())).getBytes("UTF-8"), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKeySpec);
            object = new SignatureBaseString((HttpRequest)object, httpParameters).generate();
            OAuth.debugOut("SBS", (String)object);
            object = this.base64Encode(mac.doFinal(object.getBytes("UTF-8"))).trim();
            return object;
        }
        catch (GeneralSecurityException var1_2) {
            throw new OAuthMessageSignerException(var1_2);
        }
        catch (UnsupportedEncodingException var1_3) {
            throw new OAuthMessageSignerException(var1_3);
        }
    }
}

