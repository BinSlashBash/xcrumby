/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.apache.http.HttpRequest
 *  org.apache.http.client.methods.HttpUriRequest
 */
package oauth.signpost.commonshttp;

import oauth.signpost.AbstractOAuthConsumer;
import oauth.signpost.commonshttp.HttpRequestAdapter;
import oauth.signpost.http.HttpRequest;
import org.apache.http.client.methods.HttpUriRequest;

public class CommonsHttpOAuthConsumer
extends AbstractOAuthConsumer {
    private static final long serialVersionUID = 1;

    public CommonsHttpOAuthConsumer(String string2, String string3) {
        super(string2, string3);
    }

    @Override
    protected HttpRequest wrap(Object object) {
        if (!(object instanceof org.apache.http.HttpRequest)) {
            throw new IllegalArgumentException("This consumer expects requests of type " + org.apache.http.HttpRequest.class.getCanonicalName());
        }
        return new HttpRequestAdapter((HttpUriRequest)object);
    }
}

