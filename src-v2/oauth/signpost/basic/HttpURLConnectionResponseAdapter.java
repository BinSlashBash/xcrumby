/*
 * Decompiled with CFR 0_110.
 */
package oauth.signpost.basic;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import oauth.signpost.http.HttpResponse;

public class HttpURLConnectionResponseAdapter
implements HttpResponse {
    private HttpURLConnection connection;

    public HttpURLConnectionResponseAdapter(HttpURLConnection httpURLConnection) {
        this.connection = httpURLConnection;
    }

    @Override
    public InputStream getContent() throws IOException {
        try {
            InputStream inputStream = this.connection.getInputStream();
            return inputStream;
        }
        catch (IOException var1_2) {
            return this.connection.getErrorStream();
        }
    }

    @Override
    public String getReasonPhrase() throws Exception {
        return this.connection.getResponseMessage();
    }

    @Override
    public int getStatusCode() throws IOException {
        return this.connection.getResponseCode();
    }

    @Override
    public Object unwrap() {
        return this.connection;
    }
}

