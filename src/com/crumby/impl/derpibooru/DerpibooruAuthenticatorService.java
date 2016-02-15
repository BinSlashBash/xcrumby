package com.crumby.impl.derpibooru;

import com.crumby.lib.authentication.ServerAuthenticate;
import com.crumby.lib.fragment.producer.GalleryProducer;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DerpibooruAuthenticatorService
  extends AuthenticatorService
{
  public static String AUTHENTICITY_TOKEN;
  public static final String FORM_TITLE = "DERPIBOORU";
  public static final String FORM_USER_TITLE = "EMAIL ADDRESS";
  public static final ServerAuthenticate SERVER_AUTHENTICATE = new ServerAuthenticate()
  {
    public void userSignIn(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3)
      throws Exception
    {
      DerpibooruAuthenticatorService.AUTHENTICITY_TOKEN = DerpibooruProducer.getAuthKey(GalleryProducer.fetchUrl("https://derpibooru.org/users/sign_in"));
      if (DerpibooruAuthenticatorService.AUTHENTICITY_TOKEN == null) {
        throw new Exception("Could not login with your credentials");
      }
      paramAnonymousString3 = (HttpURLConnection)new URL("https://derpibooru.org/users/sign_in").openConnection();
      paramAnonymousString3.setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
      paramAnonymousString3.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      paramAnonymousString3.setRequestProperty("Accept", "*/*");
      paramAnonymousString3.setDoInput(true);
      paramAnonymousString3.setDoOutput(true);
      paramAnonymousString3.setInstanceFollowRedirects(true);
      OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(paramAnonymousString3.getOutputStream());
      localOutputStreamWriter.write("authenticity_token=" + URLEncoder.encode(DerpibooruAuthenticatorService.AUTHENTICITY_TOKEN, "UTF-8") + "&user[email]=" + URLEncoder.encode(paramAnonymousString1, "UTF-8") + "&user[password]=" + URLEncoder.encode(paramAnonymousString2, "UTF-8") + "&user[remember_me]=1&commit=Sign+in");
      localOutputStreamWriter.close();
      paramAnonymousString3.connect();
      GalleryProducer.readStreamIntoString(paramAnonymousString3.getInputStream());
    }
    
    public String userSignInAndGetAuth(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3)
      throws Exception
    {
      userSignIn(paramAnonymousString1, paramAnonymousString2, paramAnonymousString3);
      paramAnonymousString1 = Jsoup.parse(GalleryProducer.fetchUrl("https://derpibooru.org/users/edit")).getElementsByTag("h3").iterator();
      while (paramAnonymousString1.hasNext())
      {
        paramAnonymousString2 = (Element)paramAnonymousString1.next();
        if (paramAnonymousString2.text().equals("API Key")) {
          return paramAnonymousString2.nextElementSibling().getElementsByTag("strong").first().text();
        }
      }
      throw new Exception("Could not log you in. Invalid user credentials.");
    }
  };
  
  protected ServerAuthenticate getServerAuthenticate()
  {
    return SERVER_AUTHENTICATE;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/derpibooru/DerpibooruAuthenticatorService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */