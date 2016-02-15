package com.crumby.impl.e621;

import com.crumby.CrumbyApp;
import com.crumby.impl.derpibooru.AuthenticatorService;
import com.crumby.lib.authentication.ServerAuthenticate;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class e621AuthenticatorService
  extends AuthenticatorService
{
  public static String AUTHENTICITY_TOKEN;
  public static final String FORM_TITLE = "DERPIBOORU";
  public static final String FORM_USER_TITLE = "EMAIL ADDRESS";
  public static final ServerAuthenticate SERVER_AUTHENTICATE = new ServerAuthenticate()
  {
    private boolean checkForLogin(String paramAnonymousString)
    {
      paramAnonymousString = Jsoup.parse(paramAnonymousString);
      Element localElement = paramAnonymousString.getElementById("notice");
      if ((localElement != null) && (localElement.text().contains("are now logged in"))) {}
      while (paramAnonymousString.getElementsByTag("title").get(0).text().contains("Account")) {
        return true;
      }
      return false;
    }
    
    public void userSignIn(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3)
      throws Exception
    {
      String str2 = CrumbyApp.getHttpClient().newCall(new Request.Builder().url("https://e621.net/user/login").build()).execute().body().string();
      paramAnonymousString3 = Jsoup.parse(str2).getElementsByAttributeValue("name", "authenticity_token");
      String str1 = "";
      Iterator localIterator = paramAnonymousString3.iterator();
      do
      {
        paramAnonymousString3 = str1;
        if (!localIterator.hasNext()) {
          break;
        }
        paramAnonymousString3 = (Element)localIterator.next();
      } while (!paramAnonymousString3.tagName().equals("input"));
      paramAnonymousString3 = paramAnonymousString3.attr("value");
      if (checkForLogin(str2)) {}
      do
      {
        return;
        paramAnonymousString1 = new FormEncodingBuilder().add("authenticity_token", paramAnonymousString3).add("user[name]", paramAnonymousString1).add("user[password]", paramAnonymousString2).add("user[roaming]", "1").build();
        paramAnonymousString1 = new Request.Builder().url("https://e621.net/user/authenticate").header("Origin", "https://e621.net").header("Referer", "https://e621.net/user/login").post(paramAnonymousString1);
      } while (checkForLogin(CrumbyApp.getHttpClient().newCall(paramAnonymousString1.build()).execute().body().string()));
      throw new Exception("Invalid username and/or password");
    }
    
    public String userSignInAndGetAuth(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3)
      throws Exception
    {
      userSignIn(paramAnonymousString1, paramAnonymousString2, paramAnonymousString3);
      return "FUCK";
    }
  };
  
  protected ServerAuthenticate getServerAuthenticate()
  {
    return SERVER_AUTHENTICATE;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/e621/e621AuthenticatorService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */