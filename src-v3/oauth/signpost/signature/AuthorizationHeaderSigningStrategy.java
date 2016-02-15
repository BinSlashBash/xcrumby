package oauth.signpost.signature;

import java.util.Iterator;
import oauth.signpost.OAuth;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.http.HttpRequest;

public class AuthorizationHeaderSigningStrategy implements SigningStrategy {
    private static final long serialVersionUID = 1;

    public String writeSignature(String signature, HttpRequest request, HttpParameters requestParameters) {
        StringBuilder sb = new StringBuilder();
        sb.append("OAuth ");
        if (requestParameters.containsKey("realm")) {
            sb.append(requestParameters.getAsHeaderElement("realm"));
            sb.append(", ");
        }
        HttpParameters oauthParams = requestParameters.getOAuthParameters();
        oauthParams.put(OAuth.OAUTH_SIGNATURE, signature, true);
        Iterator<String> iter = oauthParams.keySet().iterator();
        while (iter.hasNext()) {
            sb.append(oauthParams.getAsHeaderElement((String) iter.next()));
            if (iter.hasNext()) {
                sb.append(", ");
            }
        }
        String header = sb.toString();
        OAuth.debugOut("Auth Header", header);
        request.setHeader(OAuth.HTTP_AUTHORIZATION_HEADER, header);
        return header;
    }
}
