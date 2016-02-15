package oauth.signpost.signature;

import java.util.Iterator;
import oauth.signpost.OAuth;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.http.HttpRequest;

public class QueryStringSigningStrategy implements SigningStrategy {
    private static final long serialVersionUID = 1;

    public String writeSignature(String signature, HttpRequest request, HttpParameters requestParameters) {
        HttpParameters oauthParams = requestParameters.getOAuthParameters();
        oauthParams.put(OAuth.OAUTH_SIGNATURE, signature, true);
        Iterator<String> iter = oauthParams.keySet().iterator();
        StringBuilder sb = new StringBuilder(OAuth.addQueryString(request.getRequestUrl(), oauthParams.getAsQueryString((String) iter.next())));
        while (iter.hasNext()) {
            sb.append("&");
            sb.append(oauthParams.getAsQueryString((String) iter.next()));
        }
        String signedUrl = sb.toString();
        request.setRequestUrl(signedUrl);
        return signedUrl;
    }
}
