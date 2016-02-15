/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.furaffinity;

import com.crumby.CrumbyApp;
import com.crumby.impl.derpibooru.AuthenticatorService;
import com.crumby.lib.authentication.ServerAuthenticate;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class FurAffinityAuthenticatorService
extends AuthenticatorService {
    public static final ServerAuthenticate SERVER_AUTHENTICATE = new ServerAuthenticate(){
        private final MediaType FORM = MediaType.parse("application/x-www-form-urlencode; charset=UTF-8");

        @Override
        public void userSignIn(String object, String string2, String string3) throws Exception {
            object = new FormEncodingBuilder().add("action", "login").add("retard_protection", "1").add("name", (String)object).add("pass", string2).add("login", "Login to FurAffinity").build();
            object = new Request.Builder().url("https://www.furaffinity.net/login/?ref=http://www.furaffinity.net/").header("Origin", "https://www.furaffinity.net/").header("Referer", "https://www.furaffinity.net/login/").post((RequestBody)object);
            if (Jsoup.parse(CrumbyApp.getHttpClient().newCall(object.build()).execute().body().string()).getElementById("my-username") != null) {
                return;
            }
            throw new Exception("Invalid username and/or password");
        }

        @Override
        public String userSignInAndGetAuth(String string2, String string3, String string4) throws Exception {
            this.userSignIn(string2, string3, string4);
            return "okay";
        }
    };

    @Override
    protected ServerAuthenticate getServerAuthenticate() {
        return SERVER_AUTHENTICATE;
    }

}

