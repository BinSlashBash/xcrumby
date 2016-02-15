/*
 * Decompiled with CFR 0_110.
 */
package oauth.signpost.signature;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Set;
import oauth.signpost.OAuth;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.http.HttpRequest;

public class SignatureBaseString {
    private HttpRequest request;
    private HttpParameters requestParameters;

    public SignatureBaseString(HttpRequest httpRequest, HttpParameters httpParameters) {
        this.request = httpRequest;
        this.requestParameters = httpParameters;
    }

    public String generate() throws OAuthMessageSignerException {
        try {
            String string2 = this.normalizeRequestUrl();
            String string3 = this.normalizeRequestParameters();
            string2 = this.request.getMethod() + '&' + OAuth.percentEncode(string2) + '&' + OAuth.percentEncode(string3);
            return string2;
        }
        catch (Exception var1_2) {
            throw new OAuthMessageSignerException(var1_2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public String normalizeRequestParameters() throws IOException {
        if (this.requestParameters == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<String> iterator = this.requestParameters.keySet().iterator();
        int n2 = 0;
        while (iterator.hasNext()) {
            String string2 = iterator.next();
            if (!"oauth_signature".equals(string2) && !"realm".equals(string2)) {
                if (n2 > 0) {
                    stringBuilder.append("&");
                }
                stringBuilder.append(this.requestParameters.getAsQueryString(string2, false));
            }
            ++n2;
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    public String normalizeRequestUrl() throws URISyntaxException {
        String string2;
        String string3;
        Object object;
        block4 : {
            Object object2 = new URI(this.request.getRequestUrl());
            string2 = object2.getScheme().toLowerCase();
            object = object2.getAuthority().toLowerCase();
            int n2 = string2.equals("http") && object2.getPort() == 80 || string2.equals("https") && object2.getPort() == 443 ? 1 : 0;
            string3 = object;
            if (n2 != 0) {
                n2 = object.lastIndexOf(":");
                string3 = object;
                if (n2 >= 0) {
                    string3 = object.substring(0, n2);
                }
            }
            if ((object2 = object2.getRawPath()) != null) {
                object = object2;
                if (object2.length() > 0) break block4;
            }
            object = "/";
        }
        return string2 + "://" + string3 + (String)object;
    }
}

