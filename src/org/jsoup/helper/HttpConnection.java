package org.jsoup.helper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import org.jsoup.Connection;
import org.jsoup.Connection.Base;
import org.jsoup.Connection.KeyVal;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Request;
import org.jsoup.Connection.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.parser.Parser;
import org.jsoup.parser.TokenQueue;

public class HttpConnection
  implements Connection
{
  private Connection.Request req = new Request(null);
  private Connection.Response res = new Response();
  
  public static Connection connect(String paramString)
  {
    HttpConnection localHttpConnection = new HttpConnection();
    localHttpConnection.url(paramString);
    return localHttpConnection;
  }
  
  public static Connection connect(URL paramURL)
  {
    HttpConnection localHttpConnection = new HttpConnection();
    localHttpConnection.url(paramURL);
    return localHttpConnection;
  }
  
  private static String encodeUrl(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    return paramString.replaceAll(" ", "%20");
  }
  
  public Connection cookie(String paramString1, String paramString2)
  {
    this.req.cookie(paramString1, paramString2);
    return this;
  }
  
  public Connection cookies(Map<String, String> paramMap)
  {
    Validate.notNull(paramMap, "Cookie map must not be null");
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap.next();
      this.req.cookie((String)localEntry.getKey(), (String)localEntry.getValue());
    }
    return this;
  }
  
  public Connection data(String paramString1, String paramString2)
  {
    this.req.data(KeyVal.create(paramString1, paramString2));
    return this;
  }
  
  public Connection data(Collection<Connection.KeyVal> paramCollection)
  {
    Validate.notNull(paramCollection, "Data collection must not be null");
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext())
    {
      Connection.KeyVal localKeyVal = (Connection.KeyVal)paramCollection.next();
      this.req.data(localKeyVal);
    }
    return this;
  }
  
  public Connection data(Map<String, String> paramMap)
  {
    Validate.notNull(paramMap, "Data map must not be null");
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap.next();
      this.req.data(KeyVal.create((String)localEntry.getKey(), (String)localEntry.getValue()));
    }
    return this;
  }
  
  public Connection data(String... paramVarArgs)
  {
    Validate.notNull(paramVarArgs, "Data key value pairs must not be null");
    if (paramVarArgs.length % 2 == 0) {}
    for (boolean bool = true;; bool = false)
    {
      Validate.isTrue(bool, "Must supply an even number of key value pairs");
      int i = 0;
      while (i < paramVarArgs.length)
      {
        String str1 = paramVarArgs[i];
        String str2 = paramVarArgs[(i + 1)];
        Validate.notEmpty(str1, "Data key must not be empty");
        Validate.notNull(str2, "Data value must not be null");
        this.req.data(KeyVal.create(str1, str2));
        i += 2;
      }
    }
    return this;
  }
  
  public Connection.Response execute()
    throws IOException
  {
    this.res = Response.execute(this.req);
    return this.res;
  }
  
  public Connection followRedirects(boolean paramBoolean)
  {
    this.req.followRedirects(paramBoolean);
    return this;
  }
  
  public Document get()
    throws IOException
  {
    this.req.method(Connection.Method.GET);
    execute();
    return this.res.parse();
  }
  
  public Connection header(String paramString1, String paramString2)
  {
    this.req.header(paramString1, paramString2);
    return this;
  }
  
  public Connection ignoreContentType(boolean paramBoolean)
  {
    this.req.ignoreContentType(paramBoolean);
    return this;
  }
  
  public Connection ignoreHttpErrors(boolean paramBoolean)
  {
    this.req.ignoreHttpErrors(paramBoolean);
    return this;
  }
  
  public Connection maxBodySize(int paramInt)
  {
    this.req.maxBodySize(paramInt);
    return this;
  }
  
  public Connection method(Connection.Method paramMethod)
  {
    this.req.method(paramMethod);
    return this;
  }
  
  public Connection parser(Parser paramParser)
  {
    this.req.parser(paramParser);
    return this;
  }
  
  public Document post()
    throws IOException
  {
    this.req.method(Connection.Method.POST);
    execute();
    return this.res.parse();
  }
  
  public Connection referrer(String paramString)
  {
    Validate.notNull(paramString, "Referrer must not be null");
    this.req.header("Referer", paramString);
    return this;
  }
  
  public Connection.Request request()
  {
    return this.req;
  }
  
  public Connection request(Connection.Request paramRequest)
  {
    this.req = paramRequest;
    return this;
  }
  
  public Connection.Response response()
  {
    return this.res;
  }
  
  public Connection response(Connection.Response paramResponse)
  {
    this.res = paramResponse;
    return this;
  }
  
  public Connection timeout(int paramInt)
  {
    this.req.timeout(paramInt);
    return this;
  }
  
  public Connection url(String paramString)
  {
    Validate.notEmpty(paramString, "Must supply a valid URL");
    try
    {
      this.req.url(new URL(encodeUrl(paramString)));
      return this;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      throw new IllegalArgumentException("Malformed URL: " + paramString, localMalformedURLException);
    }
  }
  
  public Connection url(URL paramURL)
  {
    this.req.url(paramURL);
    return this;
  }
  
  public Connection userAgent(String paramString)
  {
    Validate.notNull(paramString, "User agent must not be null");
    this.req.header("User-Agent", paramString);
    return this;
  }
  
  private static abstract class Base<T extends Connection.Base>
    implements Connection.Base<T>
  {
    Map<String, String> cookies = new LinkedHashMap();
    Map<String, String> headers = new LinkedHashMap();
    Connection.Method method;
    URL url;
    
    private String getHeaderCaseInsensitive(String paramString)
    {
      Validate.notNull(paramString, "Header name must not be null");
      Object localObject2 = (String)this.headers.get(paramString);
      Object localObject1 = localObject2;
      if (localObject2 == null) {
        localObject1 = (String)this.headers.get(paramString.toLowerCase());
      }
      localObject2 = localObject1;
      if (localObject1 == null)
      {
        paramString = scanHeaders(paramString);
        localObject2 = localObject1;
        if (paramString != null) {
          localObject2 = (String)paramString.getValue();
        }
      }
      return (String)localObject2;
    }
    
    private Map.Entry<String, String> scanHeaders(String paramString)
    {
      paramString = paramString.toLowerCase();
      Iterator localIterator = this.headers.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        if (((String)localEntry.getKey()).toLowerCase().equals(paramString)) {
          return localEntry;
        }
      }
      return null;
    }
    
    public String cookie(String paramString)
    {
      Validate.notNull(paramString, "Cookie name must not be null");
      return (String)this.cookies.get(paramString);
    }
    
    public T cookie(String paramString1, String paramString2)
    {
      Validate.notEmpty(paramString1, "Cookie name must not be empty");
      Validate.notNull(paramString2, "Cookie value must not be null");
      this.cookies.put(paramString1, paramString2);
      return this;
    }
    
    public Map<String, String> cookies()
    {
      return this.cookies;
    }
    
    public boolean hasCookie(String paramString)
    {
      Validate.notEmpty("Cookie name must not be empty");
      return this.cookies.containsKey(paramString);
    }
    
    public boolean hasHeader(String paramString)
    {
      Validate.notEmpty(paramString, "Header name must not be empty");
      return getHeaderCaseInsensitive(paramString) != null;
    }
    
    public String header(String paramString)
    {
      Validate.notNull(paramString, "Header name must not be null");
      return getHeaderCaseInsensitive(paramString);
    }
    
    public T header(String paramString1, String paramString2)
    {
      Validate.notEmpty(paramString1, "Header name must not be empty");
      Validate.notNull(paramString2, "Header value must not be null");
      removeHeader(paramString1);
      this.headers.put(paramString1, paramString2);
      return this;
    }
    
    public Map<String, String> headers()
    {
      return this.headers;
    }
    
    public T method(Connection.Method paramMethod)
    {
      Validate.notNull(paramMethod, "Method must not be null");
      this.method = paramMethod;
      return this;
    }
    
    public Connection.Method method()
    {
      return this.method;
    }
    
    public T removeCookie(String paramString)
    {
      Validate.notEmpty("Cookie name must not be empty");
      this.cookies.remove(paramString);
      return this;
    }
    
    public T removeHeader(String paramString)
    {
      Validate.notEmpty(paramString, "Header name must not be empty");
      paramString = scanHeaders(paramString);
      if (paramString != null) {
        this.headers.remove(paramString.getKey());
      }
      return this;
    }
    
    public URL url()
    {
      return this.url;
    }
    
    public T url(URL paramURL)
    {
      Validate.notNull(paramURL, "URL must not be null");
      this.url = paramURL;
      return this;
    }
  }
  
  public static class KeyVal
    implements Connection.KeyVal
  {
    private String key;
    private String value;
    
    private KeyVal(String paramString1, String paramString2)
    {
      this.key = paramString1;
      this.value = paramString2;
    }
    
    public static KeyVal create(String paramString1, String paramString2)
    {
      Validate.notEmpty(paramString1, "Data key must not be empty");
      Validate.notNull(paramString2, "Data value must not be null");
      return new KeyVal(paramString1, paramString2);
    }
    
    public String key()
    {
      return this.key;
    }
    
    public KeyVal key(String paramString)
    {
      Validate.notEmpty(paramString, "Data key must not be empty");
      this.key = paramString;
      return this;
    }
    
    public String toString()
    {
      return this.key + "=" + this.value;
    }
    
    public String value()
    {
      return this.value;
    }
    
    public KeyVal value(String paramString)
    {
      Validate.notNull(paramString, "Data value must not be null");
      this.value = paramString;
      return this;
    }
  }
  
  public static class Request
    extends HttpConnection.Base<Connection.Request>
    implements Connection.Request
  {
    private Collection<Connection.KeyVal> data = new ArrayList();
    private boolean followRedirects = true;
    private boolean ignoreContentType = false;
    private boolean ignoreHttpErrors = false;
    private int maxBodySizeBytes = 1048576;
    private Parser parser;
    private int timeoutMilliseconds = 3000;
    
    private Request()
    {
      super();
      this.method = Connection.Method.GET;
      this.headers.put("Accept-Encoding", "gzip");
      this.parser = Parser.htmlParser();
    }
    
    public Collection<Connection.KeyVal> data()
    {
      return this.data;
    }
    
    public Request data(Connection.KeyVal paramKeyVal)
    {
      Validate.notNull(paramKeyVal, "Key val must not be null");
      this.data.add(paramKeyVal);
      return this;
    }
    
    public Connection.Request followRedirects(boolean paramBoolean)
    {
      this.followRedirects = paramBoolean;
      return this;
    }
    
    public boolean followRedirects()
    {
      return this.followRedirects;
    }
    
    public Connection.Request ignoreContentType(boolean paramBoolean)
    {
      this.ignoreContentType = paramBoolean;
      return this;
    }
    
    public boolean ignoreContentType()
    {
      return this.ignoreContentType;
    }
    
    public Connection.Request ignoreHttpErrors(boolean paramBoolean)
    {
      this.ignoreHttpErrors = paramBoolean;
      return this;
    }
    
    public boolean ignoreHttpErrors()
    {
      return this.ignoreHttpErrors;
    }
    
    public int maxBodySize()
    {
      return this.maxBodySizeBytes;
    }
    
    public Connection.Request maxBodySize(int paramInt)
    {
      if (paramInt >= 0) {}
      for (boolean bool = true;; bool = false)
      {
        Validate.isTrue(bool, "maxSize must be 0 (unlimited) or larger");
        this.maxBodySizeBytes = paramInt;
        return this;
      }
    }
    
    public Request parser(Parser paramParser)
    {
      this.parser = paramParser;
      return this;
    }
    
    public Parser parser()
    {
      return this.parser;
    }
    
    public int timeout()
    {
      return this.timeoutMilliseconds;
    }
    
    public Request timeout(int paramInt)
    {
      if (paramInt >= 0) {}
      for (boolean bool = true;; bool = false)
      {
        Validate.isTrue(bool, "Timeout milliseconds must be 0 (infinite) or greater");
        this.timeoutMilliseconds = paramInt;
        return this;
      }
    }
  }
  
  public static class Response
    extends HttpConnection.Base<Connection.Response>
    implements Connection.Response
  {
    private static final int MAX_REDIRECTS = 20;
    private ByteBuffer byteData;
    private String charset;
    private String contentType;
    private boolean executed = false;
    private int numRedirects = 0;
    private Connection.Request req;
    private int statusCode;
    private String statusMessage;
    
    Response()
    {
      super();
    }
    
    private Response(Response paramResponse)
      throws IOException
    {
      super();
      if (paramResponse != null)
      {
        paramResponse.numRedirects += 1;
        if (this.numRedirects >= 20) {
          throw new IOException(String.format("Too many redirects occurred trying to load URL %s", new Object[] { paramResponse.url() }));
        }
      }
    }
    
    private static HttpURLConnection createConnection(Connection.Request paramRequest)
      throws IOException
    {
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)paramRequest.url().openConnection();
      localHttpURLConnection.setRequestMethod(paramRequest.method().name());
      localHttpURLConnection.setInstanceFollowRedirects(false);
      localHttpURLConnection.setConnectTimeout(paramRequest.timeout());
      localHttpURLConnection.setReadTimeout(paramRequest.timeout());
      if (paramRequest.method() == Connection.Method.POST) {
        localHttpURLConnection.setDoOutput(true);
      }
      if (paramRequest.cookies().size() > 0) {
        localHttpURLConnection.addRequestProperty("Cookie", getRequestCookieString(paramRequest));
      }
      paramRequest = paramRequest.headers().entrySet().iterator();
      while (paramRequest.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)paramRequest.next();
        localHttpURLConnection.addRequestProperty((String)localEntry.getKey(), (String)localEntry.getValue());
      }
      return localHttpURLConnection;
    }
    
    static Response execute(Connection.Request paramRequest)
      throws IOException
    {
      return execute(paramRequest, null);
    }
    
    static Response execute(Connection.Request paramRequest, Response paramResponse)
      throws IOException
    {
      Validate.notNull(paramRequest, "Request must not be null");
      Object localObject1 = paramRequest.url().getProtocol();
      if ((!((String)localObject1).equals("http")) && (!((String)localObject1).equals("https"))) {
        throw new MalformedURLException("Only http & https protocols supported");
      }
      if ((paramRequest.method() == Connection.Method.GET) && (paramRequest.data().size() > 0)) {
        serialiseRequestUrl(paramRequest);
      }
      HttpURLConnection localHttpURLConnection = createConnection(paramRequest);
      int i;
      label396:
      label410:
      label676:
      label719:
      for (;;)
      {
        int k;
        Response localResponse2;
        try
        {
          localHttpURLConnection.connect();
          if (paramRequest.method() == Connection.Method.POST) {
            writePost(paramRequest.data(), localHttpURLConnection.getOutputStream());
          }
          k = localHttpURLConnection.getResponseCode();
          int j = 0;
          i = j;
          if (k != 200)
          {
            if ((k == 302) || (k == 301)) {
              break label719;
            }
            if (k == 303) {
              break label719;
            }
          }
          else
          {
            localResponse2 = new Response(paramResponse);
            localResponse2.setupFromConnection(localHttpURLConnection, paramResponse);
            if ((i == 0) || (!paramRequest.followRedirects())) {
              break label410;
            }
            paramRequest.method(Connection.Method.GET);
            paramRequest.data().clear();
            localObject1 = localResponse2.header("Location");
            paramResponse = (Response)localObject1;
            if (localObject1 != null)
            {
              paramResponse = (Response)localObject1;
              if (((String)localObject1).startsWith("http:/"))
              {
                paramResponse = (Response)localObject1;
                if (((String)localObject1).charAt(6) != '/') {
                  paramResponse = ((String)localObject1).substring(6);
                }
              }
            }
            paramRequest.url(new URL(paramRequest.url(), HttpConnection.encodeUrl(paramResponse)));
            paramResponse = localResponse2.cookies.entrySet().iterator();
            if (!paramResponse.hasNext()) {
              break label396;
            }
            localObject1 = (Map.Entry)paramResponse.next();
            paramRequest.cookie((String)((Map.Entry)localObject1).getKey(), (String)((Map.Entry)localObject1).getValue());
            continue;
          }
          i = j;
        }
        finally
        {
          localHttpURLConnection.disconnect();
        }
        if (!paramRequest.ignoreHttpErrors())
        {
          throw new HttpStatusException("HTTP error fetching URL", k, paramRequest.url().toString());
          paramRequest = execute(paramRequest, localResponse2);
          localHttpURLConnection.disconnect();
          return paramRequest;
          localResponse2.req = paramRequest;
          paramResponse = localResponse2.contentType();
          if ((paramResponse != null) && (!paramRequest.ignoreContentType()) && (!paramResponse.startsWith("text/")) && (!paramResponse.startsWith("application/xml")) && (!paramResponse.startsWith("application/xhtml+xml"))) {
            throw new UnsupportedMimeTypeException("Unhandled content type. Must be text/*, application/xml, or application/xhtml+xml", paramResponse, paramRequest.url().toString());
          }
          localObject1 = null;
          paramResponse = null;
          Object localObject2 = localObject1;
          Response localResponse1 = paramResponse;
          try
          {
            if (localHttpURLConnection.getErrorStream() != null)
            {
              localObject2 = localObject1;
              localResponse1 = paramResponse;
              paramResponse = localHttpURLConnection.getErrorStream();
              localObject2 = localObject1;
              localResponse1 = paramResponse;
              if (!localResponse2.hasHeader("Content-Encoding")) {
                break label676;
              }
              localObject2 = localObject1;
              localResponse1 = paramResponse;
              if (!localResponse2.header("Content-Encoding").equalsIgnoreCase("gzip")) {
                break label676;
              }
              localObject2 = localObject1;
              localResponse1 = paramResponse;
            }
            for (localObject1 = new BufferedInputStream(new GZIPInputStream(paramResponse));; localObject1 = new BufferedInputStream(paramResponse))
            {
              localObject2 = localObject1;
              localResponse1 = paramResponse;
              localResponse2.byteData = DataUtil.readToByteBuffer((InputStream)localObject1, paramRequest.maxBodySize());
              localObject2 = localObject1;
              localResponse1 = paramResponse;
              localResponse2.charset = DataUtil.getCharsetFromContentType(localResponse2.contentType);
              if (localObject1 != null) {
                ((InputStream)localObject1).close();
              }
              if (paramResponse != null) {
                paramResponse.close();
              }
              localHttpURLConnection.disconnect();
              localResponse2.executed = true;
              return localResponse2;
              localObject2 = localObject1;
              localResponse1 = paramResponse;
              paramResponse = localHttpURLConnection.getInputStream();
              break;
              localObject2 = localObject1;
              localResponse1 = paramResponse;
            }
            i = 1;
          }
          finally
          {
            if (localObject2 != null) {
              ((InputStream)localObject2).close();
            }
            if (localResponse1 != null) {
              localResponse1.close();
            }
          }
        }
      }
    }
    
    private static String getRequestCookieString(Connection.Request paramRequest)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      int i = 1;
      paramRequest = paramRequest.cookies().entrySet().iterator();
      if (paramRequest.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)paramRequest.next();
        if (i == 0) {
          localStringBuilder.append("; ");
        }
        for (;;)
        {
          localStringBuilder.append((String)localEntry.getKey()).append('=').append((String)localEntry.getValue());
          break;
          i = 0;
        }
      }
      return localStringBuilder.toString();
    }
    
    private static void serialiseRequestUrl(Connection.Request paramRequest)
      throws IOException
    {
      Object localObject = paramRequest.url();
      StringBuilder localStringBuilder = new StringBuilder();
      int i = 1;
      localStringBuilder.append(((URL)localObject).getProtocol()).append("://").append(((URL)localObject).getAuthority()).append(((URL)localObject).getPath()).append("?");
      if (((URL)localObject).getQuery() != null)
      {
        localStringBuilder.append(((URL)localObject).getQuery());
        i = 0;
      }
      localObject = paramRequest.data().iterator();
      if (((Iterator)localObject).hasNext())
      {
        Connection.KeyVal localKeyVal = (Connection.KeyVal)((Iterator)localObject).next();
        if (i == 0) {
          localStringBuilder.append('&');
        }
        for (;;)
        {
          localStringBuilder.append(URLEncoder.encode(localKeyVal.key(), "UTF-8")).append('=').append(URLEncoder.encode(localKeyVal.value(), "UTF-8"));
          break;
          i = 0;
        }
      }
      paramRequest.url(new URL(localStringBuilder.toString()));
      paramRequest.data().clear();
    }
    
    private void setupFromConnection(HttpURLConnection paramHttpURLConnection, Connection.Response paramResponse)
      throws IOException
    {
      this.method = Connection.Method.valueOf(paramHttpURLConnection.getRequestMethod());
      this.url = paramHttpURLConnection.getURL();
      this.statusCode = paramHttpURLConnection.getResponseCode();
      this.statusMessage = paramHttpURLConnection.getResponseMessage();
      this.contentType = paramHttpURLConnection.getContentType();
      processResponseHeaders(paramHttpURLConnection.getHeaderFields());
      if (paramResponse != null)
      {
        paramHttpURLConnection = paramResponse.cookies().entrySet().iterator();
        while (paramHttpURLConnection.hasNext())
        {
          paramResponse = (Map.Entry)paramHttpURLConnection.next();
          if (!hasCookie((String)paramResponse.getKey())) {
            cookie((String)paramResponse.getKey(), (String)paramResponse.getValue());
          }
        }
      }
    }
    
    private static void writePost(Collection<Connection.KeyVal> paramCollection, OutputStream paramOutputStream)
      throws IOException
    {
      paramOutputStream = new OutputStreamWriter(paramOutputStream, "UTF-8");
      int i = 1;
      paramCollection = paramCollection.iterator();
      if (paramCollection.hasNext())
      {
        Connection.KeyVal localKeyVal = (Connection.KeyVal)paramCollection.next();
        if (i == 0) {
          paramOutputStream.append('&');
        }
        for (;;)
        {
          paramOutputStream.write(URLEncoder.encode(localKeyVal.key(), "UTF-8"));
          paramOutputStream.write(61);
          paramOutputStream.write(URLEncoder.encode(localKeyVal.value(), "UTF-8"));
          break;
          i = 0;
        }
      }
      paramOutputStream.close();
    }
    
    public String body()
    {
      Validate.isTrue(this.executed, "Request must be executed (with .execute(), .get(), or .post() before getting response body");
      if (this.charset == null) {}
      for (String str = Charset.forName("UTF-8").decode(this.byteData).toString();; str = Charset.forName(this.charset).decode(this.byteData).toString())
      {
        this.byteData.rewind();
        return str;
      }
    }
    
    public byte[] bodyAsBytes()
    {
      Validate.isTrue(this.executed, "Request must be executed (with .execute(), .get(), or .post() before getting response body");
      return this.byteData.array();
    }
    
    public String charset()
    {
      return this.charset;
    }
    
    public String contentType()
    {
      return this.contentType;
    }
    
    public Document parse()
      throws IOException
    {
      Validate.isTrue(this.executed, "Request must be executed (with .execute(), .get(), or .post() before parsing response");
      Document localDocument = DataUtil.parseByteData(this.byteData, this.charset, this.url.toExternalForm(), this.req.parser());
      this.byteData.rewind();
      this.charset = localDocument.outputSettings().charset().name();
      return localDocument;
    }
    
    void processResponseHeaders(Map<String, List<String>> paramMap)
    {
      Iterator localIterator1 = paramMap.entrySet().iterator();
      while (localIterator1.hasNext())
      {
        Object localObject = (Map.Entry)localIterator1.next();
        paramMap = (String)((Map.Entry)localObject).getKey();
        if (paramMap != null)
        {
          localObject = (List)((Map.Entry)localObject).getValue();
          if (paramMap.equalsIgnoreCase("Set-Cookie"))
          {
            Iterator localIterator2 = ((List)localObject).iterator();
            while (localIterator2.hasNext())
            {
              paramMap = (String)localIterator2.next();
              if (paramMap != null)
              {
                paramMap = new TokenQueue(paramMap);
                String str = paramMap.chompTo("=").trim();
                localObject = paramMap.consumeTo(";").trim();
                paramMap = (Map<String, List<String>>)localObject;
                if (localObject == null) {
                  paramMap = "";
                }
                if ((str != null) && (str.length() > 0)) {
                  cookie(str, paramMap);
                }
              }
            }
          }
          else if (!((List)localObject).isEmpty())
          {
            header(paramMap, (String)((List)localObject).get(0));
          }
        }
      }
    }
    
    public int statusCode()
    {
      return this.statusCode;
    }
    
    public String statusMessage()
    {
      return this.statusMessage;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/jsoup/helper/HttpConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */