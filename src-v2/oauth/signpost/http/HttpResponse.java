/*
 * Decompiled with CFR 0_110.
 */
package oauth.signpost.http;

import java.io.IOException;
import java.io.InputStream;

public interface HttpResponse {
    public InputStream getContent() throws IOException;

    public String getReasonPhrase() throws Exception;

    public int getStatusCode() throws IOException;

    public Object unwrap();
}

