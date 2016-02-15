/*
 * Decompiled with CFR 0_110.
 */
package oauth.signpost.signature;

import java.util.Iterator;
import java.util.Set;
import oauth.signpost.OAuth;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.http.HttpRequest;
import oauth.signpost.signature.SigningStrategy;

public class AuthorizationHeaderSigningStrategy
implements SigningStrategy {
    private static final long serialVersionUID = 1;

    @Override
    public String writeSignature(String object, HttpRequest httpRequest, HttpParameters httpParameters) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("OAuth ");
        if (httpParameters.containsKey("realm")) {
            stringBuilder.append(httpParameters.getAsHeaderElement("realm"));
            stringBuilder.append(", ");
        }
        httpParameters = httpParameters.getOAuthParameters();
        httpParameters.put("oauth_signature", (String)object, true);
        object = httpParameters.keySet().iterator();
        while (object.hasNext()) {
            stringBuilder.append(httpParameters.getAsHeaderElement((String)object.next()));
            if (!object.hasNext()) continue;
            stringBuilder.append(", ");
        }
        object = stringBuilder.toString();
        OAuth.debugOut("Auth Header", (String)object);
        httpRequest.setHeader("Authorization", (String)object);
        return object;
    }
}

