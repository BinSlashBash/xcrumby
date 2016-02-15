package oauth.signpost.http;

import java.io.IOException;
import java.io.InputStream;

public abstract interface HttpResponse
{
  public abstract InputStream getContent()
    throws IOException;
  
  public abstract String getReasonPhrase()
    throws Exception;
  
  public abstract int getStatusCode()
    throws IOException;
  
  public abstract Object unwrap();
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/oauth/signpost/http/HttpResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */