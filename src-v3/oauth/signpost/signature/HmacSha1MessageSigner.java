package oauth.signpost.signature;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import oauth.signpost.OAuth;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.http.HttpRequest;
import org.apache.commons.codec.binary.Hex;

public class HmacSha1MessageSigner extends OAuthMessageSigner {
    private static final String MAC_NAME = "HmacSHA1";

    public String getSignatureMethod() {
        return "HMAC-SHA1";
    }

    public String sign(HttpRequest request, HttpParameters requestParams) throws OAuthMessageSignerException {
        try {
            SecretKey key = new SecretKeySpec((OAuth.percentEncode(getConsumerSecret()) + '&' + OAuth.percentEncode(getTokenSecret())).getBytes(Hex.DEFAULT_CHARSET_NAME), MAC_NAME);
            Mac mac = Mac.getInstance(MAC_NAME);
            mac.init(key);
            String sbs = new SignatureBaseString(request, requestParams).generate();
            OAuth.debugOut("SBS", sbs);
            return base64Encode(mac.doFinal(sbs.getBytes(Hex.DEFAULT_CHARSET_NAME))).trim();
        } catch (Exception e) {
            throw new OAuthMessageSignerException(e);
        } catch (Exception e2) {
            throw new OAuthMessageSignerException(e2);
        }
    }
}
