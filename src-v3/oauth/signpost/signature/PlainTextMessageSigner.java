package oauth.signpost.signature;

import oauth.signpost.OAuth;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.http.HttpRequest;

public class PlainTextMessageSigner extends OAuthMessageSigner {
    public String getSignatureMethod() {
        return "PLAINTEXT";
    }

    public String sign(HttpRequest request, HttpParameters requestParams) throws OAuthMessageSignerException {
        return OAuth.percentEncode(getConsumerSecret()) + '&' + OAuth.percentEncode(getTokenSecret());
    }
}
