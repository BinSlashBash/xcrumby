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

public class QueryStringSigningStrategy
implements SigningStrategy {
    private static final long serialVersionUID = 1;

    @Override
    public String writeSignature(String object, HttpRequest httpRequest, HttpParameters httpParameters) {
        httpParameters = httpParameters.getOAuthParameters();
        httpParameters.put("oauth_signature", (String)object, true);
        object = httpParameters.keySet().iterator();
        CharSequence charSequence = (String)object.next();
        charSequence = new StringBuilder(OAuth.addQueryString(httpRequest.getRequestUrl(), httpParameters.getAsQueryString(charSequence)));
        while (object.hasNext()) {
            charSequence.append("&");
            charSequence.append(httpParameters.getAsQueryString((String)object.next()));
        }
        object = charSequence.toString();
        httpRequest.setRequestUrl((String)object);
        return object;
    }
}

