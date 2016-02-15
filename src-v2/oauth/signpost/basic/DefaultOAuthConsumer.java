/*
 * Decompiled with CFR 0_110.
 */
package oauth.signpost.basic;

import java.net.HttpURLConnection;
import oauth.signpost.AbstractOAuthConsumer;
import oauth.signpost.basic.HttpURLConnectionRequestAdapter;
import oauth.signpost.http.HttpRequest;

public class DefaultOAuthConsumer
extends AbstractOAuthConsumer {
    private static final long serialVersionUID = 1;

    public DefaultOAuthConsumer(String string2, String string3) {
        super(string2, string3);
    }

    @Override
    protected HttpRequest wrap(Object object) {
        if (!(object instanceof HttpURLConnection)) {
            throw new IllegalArgumentException("The default consumer expects requests of type java.net.HttpURLConnection");
        }
        return new HttpURLConnectionRequestAdapter((HttpURLConnection)object);
    }
}

