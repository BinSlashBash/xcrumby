/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Map;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

public interface Connection {
    public Connection cookie(String var1, String var2);

    public Connection cookies(Map<String, String> var1);

    public Connection data(String var1, String var2);

    public Connection data(Collection<KeyVal> var1);

    public Connection data(Map<String, String> var1);

    public /* varargs */ Connection data(String ... var1);

    public Response execute() throws IOException;

    public Connection followRedirects(boolean var1);

    public Document get() throws IOException;

    public Connection header(String var1, String var2);

    public Connection ignoreContentType(boolean var1);

    public Connection ignoreHttpErrors(boolean var1);

    public Connection maxBodySize(int var1);

    public Connection method(Method var1);

    public Connection parser(Parser var1);

    public Document post() throws IOException;

    public Connection referrer(String var1);

    public Request request();

    public Connection request(Request var1);

    public Response response();

    public Connection response(Response var1);

    public Connection timeout(int var1);

    public Connection url(String var1);

    public Connection url(URL var1);

    public Connection userAgent(String var1);

    public static interface Base<T extends Base> {
        public String cookie(String var1);

        public T cookie(String var1, String var2);

        public Map<String, String> cookies();

        public boolean hasCookie(String var1);

        public boolean hasHeader(String var1);

        public String header(String var1);

        public T header(String var1, String var2);

        public Map<String, String> headers();

        public T method(Method var1);

        public Method method();

        public T removeCookie(String var1);

        public T removeHeader(String var1);

        public URL url();

        public T url(URL var1);
    }

    public static interface KeyVal {
        public String key();

        public KeyVal key(String var1);

        public String value();

        public KeyVal value(String var1);
    }

    public static enum Method {
        GET,
        POST;
        

        private Method() {
        }
    }

    public static interface Request
    extends Base<Request> {
        public Collection<KeyVal> data();

        public Request data(KeyVal var1);

        public Request followRedirects(boolean var1);

        public boolean followRedirects();

        public Request ignoreContentType(boolean var1);

        public boolean ignoreContentType();

        public Request ignoreHttpErrors(boolean var1);

        public boolean ignoreHttpErrors();

        public int maxBodySize();

        public Request maxBodySize(int var1);

        public Request parser(Parser var1);

        public Parser parser();

        public int timeout();

        public Request timeout(int var1);
    }

    public static interface Response
    extends Base<Response> {
        public String body();

        public byte[] bodyAsBytes();

        public String charset();

        public String contentType();

        public Document parse() throws IOException;

        public int statusCode();

        public String statusMessage();
    }

}

