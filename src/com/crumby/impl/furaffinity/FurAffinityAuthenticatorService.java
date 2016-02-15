package com.crumby.impl.furaffinity;

import com.crumby.CrumbyApp;
import com.crumby.impl.derpibooru.AuthenticatorService;
import com.crumby.lib.authentication.ServerAuthenticate;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class FurAffinityAuthenticatorService
  extends AuthenticatorService
{
  public static final ServerAuthenticate SERVER_AUTHENTICATE = new ServerAuthenticate()
  {
    private final MediaType FORM = MediaType.parse("application/x-www-form-urlencode; charset=UTF-8");
    
    public void userSignIn(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3)
      throws Exception
    {
      paramAnonymousString1 = new FormEncodingBuilder().add("action", "login").add("retard_protection", "1").add("name", paramAnonymousString1).add("pass", paramAnonymousString2).add("login", "Login to FurAffinity").build();
      paramAnonymousString1 = new Request.Builder().url("https://www.furaffinity.net/login/?ref=http://www.furaffinity.net/").header("Origin", "https://www.furaffinity.net/").header("Referer", "https://www.furaffinity.net/login/").post(paramAnonymousString1);
      if (Jsoup.parse(CrumbyApp.getHttpClient().newCall(paramAnonymousString1.build()).execute().body().string()).getElementById("my-username") != null) {
        return;
      }
      throw new Exception("Invalid username and/or password");
    }
    
    public String userSignInAndGetAuth(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3)
      throws Exception
    {
      userSignIn(paramAnonymousString1, paramAnonymousString2, paramAnonymousString3);
      return "okay";
    }
  };
  
  protected ServerAuthenticate getServerAuthenticate()
  {
    return SERVER_AUTHENTICATE;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/furaffinity/FurAffinityAuthenticatorService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */