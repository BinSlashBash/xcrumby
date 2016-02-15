/*
 * Decompiled with CFR 0_110.
 */
package oauth.signpost.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface HttpRequest {
    public Map<String, String> getAllHeaders();

    public String getContentType();

    public String getHeader(String var1);

    public InputStream getMessagePayload() throws IOException;

    public String getMethod();

    public String getRequestUrl();

    public void setHeader(String var1, String var2);

    public void setRequestUrl(String var1);

    public Object unwrap();
}

