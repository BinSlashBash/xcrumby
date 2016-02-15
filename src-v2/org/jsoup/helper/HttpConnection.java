/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.helper;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.helper.DataUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.parser.TokenQueue;

public class HttpConnection
implements Connection {
    private Connection.Request req = new Request();
    private Connection.Response res = new Response();

    private HttpConnection() {
    }

    static /* synthetic */ String access$200(String string2) {
        return HttpConnection.encodeUrl(string2);
    }

    public static Connection connect(String string2) {
        HttpConnection httpConnection = new HttpConnection();
        httpConnection.url(string2);
        return httpConnection;
    }

    public static Connection connect(URL uRL) {
        HttpConnection httpConnection = new HttpConnection();
        httpConnection.url(uRL);
        return httpConnection;
    }

    private static String encodeUrl(String string2) {
        if (string2 == null) {
            return null;
        }
        return string2.replaceAll(" ", "%20");
    }

    @Override
    public Connection cookie(String string2, String string3) {
        this.req.cookie(string2, string3);
        return this;
    }

    @Override
    public Connection cookies(Map<String, String> object) {
        Validate.notNull(object, "Cookie map must not be null");
        for (Map.Entry entry : object.entrySet()) {
            this.req.cookie((String)entry.getKey(), (String)entry.getValue());
        }
        return this;
    }

    @Override
    public Connection data(String string2, String string3) {
        this.req.data(KeyVal.create(string2, string3));
        return this;
    }

    @Override
    public Connection data(Collection<Connection.KeyVal> object) {
        Validate.notNull(object, "Data collection must not be null");
        object = object.iterator();
        while (object.hasNext()) {
            Connection.KeyVal keyVal = (Connection.KeyVal)object.next();
            this.req.data(keyVal);
        }
        return this;
    }

    @Override
    public Connection data(Map<String, String> object) {
        Validate.notNull(object, "Data map must not be null");
        for (Map.Entry entry : object.entrySet()) {
            this.req.data(KeyVal.create((String)entry.getKey(), (String)entry.getValue()));
        }
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public /* varargs */ Connection data(String ... arrstring) {
        Validate.notNull(arrstring, "Data key value pairs must not be null");
        boolean bl2 = arrstring.length % 2 == 0;
        Validate.isTrue(bl2, "Must supply an even number of key value pairs");
        int n2 = 0;
        while (n2 < arrstring.length) {
            String string2 = arrstring[n2];
            String string3 = arrstring[n2 + 1];
            Validate.notEmpty(string2, "Data key must not be empty");
            Validate.notNull(string3, "Data value must not be null");
            this.req.data(KeyVal.create(string2, string3));
            n2 += 2;
        }
        return this;
    }

    @Override
    public Connection.Response execute() throws IOException {
        this.res = Response.execute(this.req);
        return this.res;
    }

    @Override
    public Connection followRedirects(boolean bl2) {
        this.req.followRedirects(bl2);
        return this;
    }

    @Override
    public Document get() throws IOException {
        this.req.method(Connection.Method.GET);
        this.execute();
        return this.res.parse();
    }

    @Override
    public Connection header(String string2, String string3) {
        this.req.header(string2, string3);
        return this;
    }

    @Override
    public Connection ignoreContentType(boolean bl2) {
        this.req.ignoreContentType(bl2);
        return this;
    }

    @Override
    public Connection ignoreHttpErrors(boolean bl2) {
        this.req.ignoreHttpErrors(bl2);
        return this;
    }

    @Override
    public Connection maxBodySize(int n2) {
        this.req.maxBodySize(n2);
        return this;
    }

    @Override
    public Connection method(Connection.Method method) {
        this.req.method(method);
        return this;
    }

    @Override
    public Connection parser(Parser parser) {
        this.req.parser(parser);
        return this;
    }

    @Override
    public Document post() throws IOException {
        this.req.method(Connection.Method.POST);
        this.execute();
        return this.res.parse();
    }

    @Override
    public Connection referrer(String string2) {
        Validate.notNull(string2, "Referrer must not be null");
        this.req.header("Referer", string2);
        return this;
    }

    @Override
    public Connection.Request request() {
        return this.req;
    }

    @Override
    public Connection request(Connection.Request request) {
        this.req = request;
        return this;
    }

    @Override
    public Connection.Response response() {
        return this.res;
    }

    @Override
    public Connection response(Connection.Response response) {
        this.res = response;
        return this;
    }

    @Override
    public Connection timeout(int n2) {
        this.req.timeout(n2);
        return this;
    }

    @Override
    public Connection url(String string2) {
        Validate.notEmpty(string2, "Must supply a valid URL");
        try {
            this.req.url(new URL(HttpConnection.encodeUrl(string2)));
            return this;
        }
        catch (MalformedURLException var2_2) {
            throw new IllegalArgumentException("Malformed URL: " + string2, var2_2);
        }
    }

    @Override
    public Connection url(URL uRL) {
        this.req.url(uRL);
        return this;
    }

    @Override
    public Connection userAgent(String string2) {
        Validate.notNull(string2, "User agent must not be null");
        this.req.header("User-Agent", string2);
        return this;
    }

    private static abstract class Base<T extends Connection.Base>
    implements Connection.Base<T> {
        Map<String, String> cookies = new LinkedHashMap<String, String>();
        Map<String, String> headers = new LinkedHashMap<String, String>();
        Connection.Method method;
        URL url;

        private Base() {
        }

        private String getHeaderCaseInsensitive(String object) {
            String string2;
            Validate.notNull(object, "Header name must not be null");
            String string3 = string2 = this.headers.get(object);
            if (string2 == null) {
                string3 = this.headers.get(object.toLowerCase());
            }
            string2 = string3;
            if (string3 == null) {
                object = this.scanHeaders((String)object);
                string2 = string3;
                if (object != null) {
                    string2 = (String)object.getValue();
                }
            }
            return string2;
        }

        private Map.Entry<String, String> scanHeaders(String string2) {
            string2 = string2.toLowerCase();
            for (Map.Entry<String, String> entry : this.headers.entrySet()) {
                if (!entry.getKey().toLowerCase().equals(string2)) continue;
                return entry;
            }
            return null;
        }

        @Override
        public String cookie(String string2) {
            Validate.notNull(string2, "Cookie name must not be null");
            return this.cookies.get(string2);
        }

        @Override
        public T cookie(String string2, String string3) {
            Validate.notEmpty(string2, "Cookie name must not be empty");
            Validate.notNull(string3, "Cookie value must not be null");
            this.cookies.put(string2, string3);
            return (T)this;
        }

        @Override
        public Map<String, String> cookies() {
            return this.cookies;
        }

        @Override
        public boolean hasCookie(String string2) {
            Validate.notEmpty("Cookie name must not be empty");
            return this.cookies.containsKey(string2);
        }

        @Override
        public boolean hasHeader(String string2) {
            Validate.notEmpty(string2, "Header name must not be empty");
            if (this.getHeaderCaseInsensitive(string2) != null) {
                return true;
            }
            return false;
        }

        @Override
        public String header(String string2) {
            Validate.notNull(string2, "Header name must not be null");
            return this.getHeaderCaseInsensitive(string2);
        }

        @Override
        public T header(String string2, String string3) {
            Validate.notEmpty(string2, "Header name must not be empty");
            Validate.notNull(string3, "Header value must not be null");
            this.removeHeader(string2);
            this.headers.put(string2, string3);
            return (T)this;
        }

        @Override
        public Map<String, String> headers() {
            return this.headers;
        }

        @Override
        public T method(Connection.Method method) {
            Validate.notNull((Object)method, "Method must not be null");
            this.method = method;
            return (T)this;
        }

        @Override
        public Connection.Method method() {
            return this.method;
        }

        @Override
        public T removeCookie(String string2) {
            Validate.notEmpty("Cookie name must not be empty");
            this.cookies.remove(string2);
            return (T)this;
        }

        @Override
        public T removeHeader(String object) {
            Validate.notEmpty((String)object, "Header name must not be empty");
            object = this.scanHeaders((String)object);
            if (object != null) {
                this.headers.remove(object.getKey());
            }
            return (T)this;
        }

        @Override
        public URL url() {
            return this.url;
        }

        @Override
        public T url(URL uRL) {
            Validate.notNull(uRL, "URL must not be null");
            this.url = uRL;
            return (T)this;
        }
    }

    public static class KeyVal
    implements Connection.KeyVal {
        private String key;
        private String value;

        private KeyVal(String string2, String string3) {
            this.key = string2;
            this.value = string3;
        }

        public static KeyVal create(String string2, String string3) {
            Validate.notEmpty(string2, "Data key must not be empty");
            Validate.notNull(string3, "Data value must not be null");
            return new KeyVal(string2, string3);
        }

        @Override
        public String key() {
            return this.key;
        }

        @Override
        public KeyVal key(String string2) {
            Validate.notEmpty(string2, "Data key must not be empty");
            this.key = string2;
            return this;
        }

        public String toString() {
            return this.key + "=" + this.value;
        }

        @Override
        public String value() {
            return this.value;
        }

        @Override
        public KeyVal value(String string2) {
            Validate.notNull(string2, "Data value must not be null");
            this.value = string2;
            return this;
        }
    }

    public static class Request
    extends Base<Connection.Request>
    implements Connection.Request {
        private Collection<Connection.KeyVal> data = new ArrayList<Connection.KeyVal>();
        private boolean followRedirects = true;
        private boolean ignoreContentType = false;
        private boolean ignoreHttpErrors = false;
        private int maxBodySizeBytes = 1048576;
        private Parser parser;
        private int timeoutMilliseconds = 3000;

        private Request() {
            this.method = Connection.Method.GET;
            this.headers.put("Accept-Encoding", "gzip");
            this.parser = Parser.htmlParser();
        }

        @Override
        public Collection<Connection.KeyVal> data() {
            return this.data;
        }

        @Override
        public Request data(Connection.KeyVal keyVal) {
            Validate.notNull(keyVal, "Key val must not be null");
            this.data.add(keyVal);
            return this;
        }

        @Override
        public Connection.Request followRedirects(boolean bl2) {
            this.followRedirects = bl2;
            return this;
        }

        @Override
        public boolean followRedirects() {
            return this.followRedirects;
        }

        @Override
        public Connection.Request ignoreContentType(boolean bl2) {
            this.ignoreContentType = bl2;
            return this;
        }

        @Override
        public boolean ignoreContentType() {
            return this.ignoreContentType;
        }

        @Override
        public Connection.Request ignoreHttpErrors(boolean bl2) {
            this.ignoreHttpErrors = bl2;
            return this;
        }

        @Override
        public boolean ignoreHttpErrors() {
            return this.ignoreHttpErrors;
        }

        @Override
        public int maxBodySize() {
            return this.maxBodySizeBytes;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public Connection.Request maxBodySize(int n2) {
            boolean bl2 = n2 >= 0;
            Validate.isTrue(bl2, "maxSize must be 0 (unlimited) or larger");
            this.maxBodySizeBytes = n2;
            return this;
        }

        @Override
        public Request parser(Parser parser) {
            this.parser = parser;
            return this;
        }

        @Override
        public Parser parser() {
            return this.parser;
        }

        @Override
        public int timeout() {
            return this.timeoutMilliseconds;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public Request timeout(int n2) {
            boolean bl2 = n2 >= 0;
            Validate.isTrue(bl2, "Timeout milliseconds must be 0 (infinite) or greater");
            this.timeoutMilliseconds = n2;
            return this;
        }
    }

    public static class Response
    extends Base<Connection.Response>
    implements Connection.Response {
        private static final int MAX_REDIRECTS = 20;
        private ByteBuffer byteData;
        private String charset;
        private String contentType;
        private boolean executed = false;
        private int numRedirects = 0;
        private Connection.Request req;
        private int statusCode;
        private String statusMessage;

        Response() {
        }

        private Response(Response response) throws IOException {
            if (response != null) {
                this.numRedirects = response.numRedirects + 1;
                if (this.numRedirects >= 20) {
                    throw new IOException(String.format("Too many redirects occurred trying to load URL %s", response.url()));
                }
            }
        }

        private static HttpURLConnection createConnection(Connection.Request object) throws IOException {
            HttpURLConnection httpURLConnection = (HttpURLConnection)object.url().openConnection();
            httpURLConnection.setRequestMethod(object.method().name());
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setConnectTimeout(object.timeout());
            httpURLConnection.setReadTimeout(object.timeout());
            if (object.method() == Connection.Method.POST) {
                httpURLConnection.setDoOutput(true);
            }
            if (object.cookies().size() > 0) {
                httpURLConnection.addRequestProperty("Cookie", Response.getRequestCookieString((Connection.Request)((Object)object)));
            }
            for (Map.Entry<String, String> entry : object.headers().entrySet()) {
                httpURLConnection.addRequestProperty(entry.getKey(), entry.getValue());
            }
            return httpURLConnection;
        }

        static Response execute(Connection.Request request) throws IOException {
            return Response.execute(request, null);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        static Response execute(Connection.Request var0, Response var1_3) throws IOException {
            block26 : {
                Validate.notNull(var0, "Request must not be null");
                var5_4 = var0.url().getProtocol();
                if (!var5_4.equals("http") && !var5_4.equals("https")) {
                    throw new MalformedURLException("Only http & https protocols supported");
                }
                if (var0.method() == Connection.Method.GET && var0.data().size() > 0) {
                    Response.serialiseRequestUrl((Connection.Request)var0);
                }
                var8_5 = Response.createConnection((Connection.Request)var0);
                var8_5.connect();
                if (var0.method() == Connection.Method.POST) {
                    Response.writePost(var0.data(), var8_5.getOutputStream());
                }
                var4_6 = var8_5.getResponseCode();
                var2_8 = var3_7 = false;
                if (var4_6 != 200) {
                    if (var4_6 != 302 && var4_6 != 301 && var4_6 != 303) {
                        var2_8 = var3_7;
                        if (!var0.ignoreHttpErrors()) {
                            throw new HttpStatusException("HTTP error fetching URL", var4_6, var0.url().toString());
                        }
                    } else {
                        var2_8 = true;
                    }
                }
                var9_9 = new Response((Response)var1_3);
                var9_9.setupFromConnection(var8_5, (Connection.Response)var1_3);
                if (!var2_8 || !var0.followRedirects()) break block26;
                var0.method(Connection.Method.GET);
                var0.data().clear();
                var1_3 = var5_4 = var9_9.header("Location");
                if (var5_4 != null) {
                    var1_3 = var5_4;
                    if (var5_4.startsWith("http:/")) {
                        var1_3 = var5_4;
                        if (var5_4.charAt(6) != '/') {
                            var1_3 = var5_4.substring(6);
                        }
                    }
                }
                var0.url(new URL(var0.url(), HttpConnection.access$200((String)var1_3)));
                for (Object var5_4 : var9_9.cookies.entrySet()) {
                    var0.cookie((String)var5_4.getKey(), (String)var5_4.getValue());
                }
                var0 = Response.execute((Connection.Request)var0, var9_9);
                var8_5.disconnect();
                return var0;
                {
                    catch (Throwable var0_1) {
                        var8_5.disconnect();
                        throw var0_1;
                    }
                }
            }
            var9_9.req = var0;
            var1_3 = var9_9.contentType();
            if (!(var1_3 == null || var0.ignoreContentType() || var1_3.startsWith("text/") || var1_3.startsWith("application/xml") || var1_3.startsWith("application/xhtml+xml"))) {
                throw new UnsupportedMimeTypeException("Unhandled content type. Must be text/*, application/xml, or application/xhtml+xml", (String)var1_3, var0.url().toString());
            }
            var5_4 = null;
            var1_3 = null;
            var6_10 = var5_4;
            var7_11 = var1_3;
            if (var8_5.getErrorStream() == null) ** GOTO lbl60
            var6_10 = var5_4;
            var7_11 = var1_3;
            var1_3 = var8_5.getErrorStream();
            ** GOTO lbl64
lbl60: // 1 sources:
            var6_10 = var5_4;
            var7_11 = var1_3;
            var1_3 = var8_5.getInputStream();
lbl64: // 2 sources:
            var6_10 = var5_4;
            var7_11 = var1_3;
            if (!var9_9.hasHeader("Content-Encoding")) ** GOTO lbl-1000
            var6_10 = var5_4;
            var7_11 = var1_3;
            if (var9_9.header("Content-Encoding").equalsIgnoreCase("gzip")) {
                var6_10 = var5_4;
                var7_11 = var1_3;
                var5_4 = new BufferedInputStream(new GZIPInputStream((InputStream)var1_3));
            } else lbl-1000: // 2 sources:
            {
                var6_10 = var5_4;
                var7_11 = var1_3;
                var5_4 = new BufferedInputStream((InputStream)var1_3);
            }
            var6_10 = var5_4;
            var7_11 = var1_3;
            var9_9.byteData = DataUtil.readToByteBuffer((InputStream)var5_4, var0.maxBodySize());
            var6_10 = var5_4;
            var7_11 = var1_3;
            var9_9.charset = DataUtil.getCharsetFromContentType(var9_9.contentType);
            if (var5_4 == null) ** GOTO lbl86
            var5_4.close();
lbl86: // 2 sources:
            if (var1_3 != null) {
                var1_3.close();
            }
            var8_5.disconnect();
            var9_9.executed = true;
            return var9_9;
            catch (Throwable var0_2) {
                if (var6_10 != null) {
                    var6_10.close();
                }
                if (var7_11 == null) throw var0_2;
                var7_11.close();
                throw var0_2;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private static String getRequestCookieString(Connection.Request iterator) {
            StringBuilder stringBuilder = new StringBuilder();
            boolean bl2 = true;
            iterator = iterator.cookies().entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                if (!bl2) {
                    stringBuilder.append("; ");
                } else {
                    bl2 = false;
                }
                stringBuilder.append(entry.getKey()).append('=').append(entry.getValue());
            }
            return stringBuilder.toString();
        }

        /*
         * Enabled aggressive block sorting
         */
        private static void serialiseRequestUrl(Connection.Request request) throws IOException {
            Object object = request.url();
            StringBuilder stringBuilder = new StringBuilder();
            boolean bl2 = true;
            stringBuilder.append(object.getProtocol()).append("://").append(object.getAuthority()).append(object.getPath()).append("?");
            if (object.getQuery() != null) {
                stringBuilder.append(object.getQuery());
                bl2 = false;
            }
            object = request.data().iterator();
            do {
                if (!object.hasNext()) {
                    request.url(new URL(stringBuilder.toString()));
                    request.data().clear();
                    return;
                }
                Connection.KeyVal keyVal = (Connection.KeyVal)object.next();
                if (!bl2) {
                    stringBuilder.append('&');
                } else {
                    bl2 = false;
                }
                stringBuilder.append(URLEncoder.encode(keyVal.key(), "UTF-8")).append('=').append(URLEncoder.encode(keyVal.value(), "UTF-8"));
            } while (true);
        }

        private void setupFromConnection(HttpURLConnection object, Connection.Response object22) throws IOException {
            this.method = Connection.Method.valueOf(object.getRequestMethod());
            this.url = object.getURL();
            this.statusCode = object.getResponseCode();
            this.statusMessage = object.getResponseMessage();
            this.contentType = object.getContentType();
            this.processResponseHeaders(object.getHeaderFields());
            if (object22 != null) {
                for (Map.Entry<String, String> entry : object22.cookies().entrySet()) {
                    if (this.hasCookie(entry.getKey())) continue;
                    this.cookie(entry.getKey(), entry.getValue());
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private static void writePost(Collection<Connection.KeyVal> iterator, OutputStream closeable) throws IOException {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter((OutputStream)closeable, "UTF-8");
            boolean bl2 = true;
            iterator = iterator.iterator();
            do {
                if (!iterator.hasNext()) {
                    outputStreamWriter.close();
                    return;
                }
                Connection.KeyVal keyVal = (Connection.KeyVal)iterator.next();
                if (!bl2) {
                    outputStreamWriter.append('&');
                } else {
                    bl2 = false;
                }
                outputStreamWriter.write(URLEncoder.encode(keyVal.key(), "UTF-8"));
                outputStreamWriter.write(61);
                outputStreamWriter.write(URLEncoder.encode(keyVal.value(), "UTF-8"));
            } while (true);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public String body() {
            Validate.isTrue(this.executed, "Request must be executed (with .execute(), .get(), or .post() before getting response body");
            String string2 = this.charset == null ? Charset.forName("UTF-8").decode(this.byteData).toString() : Charset.forName(this.charset).decode(this.byteData).toString();
            this.byteData.rewind();
            return string2;
        }

        @Override
        public byte[] bodyAsBytes() {
            Validate.isTrue(this.executed, "Request must be executed (with .execute(), .get(), or .post() before getting response body");
            return this.byteData.array();
        }

        @Override
        public String charset() {
            return this.charset;
        }

        @Override
        public String contentType() {
            return this.contentType;
        }

        @Override
        public Document parse() throws IOException {
            Validate.isTrue(this.executed, "Request must be executed (with .execute(), .get(), or .post() before parsing response");
            Document document = DataUtil.parseByteData(this.byteData, this.charset, this.url.toExternalForm(), this.req.parser());
            this.byteData.rewind();
            this.charset = document.outputSettings().charset().name();
            return document;
        }

        void processResponseHeaders(Map<String, List<String>> object) {
            Iterator<Map.Entry<String, List<String>>> iterator = object.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<String>> entry = iterator.next();
                object = entry.getKey();
                if (object == null) continue;
                entry = entry.getValue();
                if (object.equalsIgnoreCase("Set-Cookie")) {
                    Iterator iterator2 = entry.iterator();
                    while (iterator2.hasNext()) {
                        object = (String)iterator2.next();
                        if (object == null) continue;
                        object = new TokenQueue((String)object);
                        String string2 = object.chompTo("=").trim();
                        object = entry = object.consumeTo(";").trim();
                        if (entry == null) {
                            object = "";
                        }
                        if (string2 == null || string2.length() <= 0) continue;
                        this.cookie(string2, (String)object);
                    }
                    continue;
                }
                if (entry.isEmpty()) continue;
                this.header((String)object, (String)entry.get(0));
            }
        }

        @Override
        public int statusCode() {
            return this.statusCode;
        }

        @Override
        public String statusMessage() {
            return this.statusMessage;
        }
    }

}

