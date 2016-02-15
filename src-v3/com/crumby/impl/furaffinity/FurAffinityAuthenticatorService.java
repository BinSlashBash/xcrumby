package com.crumby.impl.furaffinity;

import com.crumby.CrumbyApp;
import com.crumby.impl.derpibooru.AuthenticatorService;
import com.crumby.lib.authentication.ServerAuthenticate;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request.Builder;
import org.jsoup.Jsoup;

public class FurAffinityAuthenticatorService extends AuthenticatorService {
    public static final ServerAuthenticate SERVER_AUTHENTICATE;

    /* renamed from: com.crumby.impl.furaffinity.FurAffinityAuthenticatorService.1 */
    static class C07341 implements ServerAuthenticate {
        private final MediaType FORM;

        C07341() {
            this.FORM = MediaType.parse("application/x-www-form-urlencode; charset=UTF-8");
        }

        public String userSignInAndGetAuth(String user, String pass, String authType) throws Exception {
            userSignIn(user, pass, authType);
            return "okay";
        }

        public void userSignIn(String user, String pass, String authType) throws Exception {
            if (Jsoup.parse(CrumbyApp.getHttpClient().newCall(new Builder().url("https://www.furaffinity.net/login/?ref=http://www.furaffinity.net/").header("Origin", "https://www.furaffinity.net/").header("Referer", "https://www.furaffinity.net/login/").post(new FormEncodingBuilder().add("action", "login").add("retard_protection", "1").add("name", user).add("pass", pass).add("login", "Login to FurAffinity").build()).build()).execute().body().string()).getElementById("my-username") == null) {
                throw new Exception("Invalid username and/or password");
            }
        }
    }

    static {
        SERVER_AUTHENTICATE = new C07341();
    }

    protected ServerAuthenticate getServerAuthenticate() {
        return SERVER_AUTHENTICATE;
    }
}
