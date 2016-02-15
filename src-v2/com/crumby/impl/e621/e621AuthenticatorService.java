/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.e621;

import com.crumby.CrumbyApp;
import com.crumby.impl.derpibooru.AuthenticatorService;
import com.crumby.lib.authentication.ServerAuthenticate;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class e621AuthenticatorService
extends AuthenticatorService {
    public static String AUTHENTICITY_TOKEN;
    public static final String FORM_TITLE = "DERPIBOORU";
    public static final String FORM_USER_TITLE = "EMAIL ADDRESS";
    public static final ServerAuthenticate SERVER_AUTHENTICATE;

    static {
        SERVER_AUTHENTICATE = new ServerAuthenticate(){

            /*
             * Enabled aggressive block sorting
             */
            private boolean checkForLogin(String object) {
                Element element = (object = Jsoup.parse((String)object)).getElementById("notice");
                if (element != null && element.text().contains("are now logged in") || object.getElementsByTag("title").get(0).text().contains("Account")) {
                    return true;
                }
                return false;
            }

            /*
             * Enabled aggressive block sorting
             * Lifted jumps to return sites
             */
            @Override
            public void userSignIn(String object, String string2, String object2) throws Exception {
                String string3;
                block2 : {
                    string3 = CrumbyApp.getHttpClient().newCall(new Request.Builder().url("https://e621.net/user/login").build()).execute().body().string();
                    object2 = Jsoup.parse(string3).getElementsByAttributeValue("name", "authenticity_token");
                    String string4 = "";
                    Iterator<Element> iterator = object2.iterator();
                    do {
                        object2 = string4;
                        if (!iterator.hasNext()) break block2;
                    } while (!(object2 = iterator.next()).tagName().equals("input"));
                    object2 = object2.attr("value");
                }
                if (this.checkForLogin(string3)) {
                    return;
                }
                object = new FormEncodingBuilder().add("authenticity_token", (String)object2).add("user[name]", (String)object).add("user[password]", string2).add("user[roaming]", "1").build();
                object = new Request.Builder().url("https://e621.net/user/authenticate").header("Origin", "https://e621.net").header("Referer", "https://e621.net/user/login").post((RequestBody)object);
                if (this.checkForLogin(CrumbyApp.getHttpClient().newCall(object.build()).execute().body().string())) return;
                throw new Exception("Invalid username and/or password");
            }

            @Override
            public String userSignInAndGetAuth(String string2, String string3, String string4) throws Exception {
                this.userSignIn(string2, string3, string4);
                return "FUCK";
            }
        };
    }

    @Override
    protected ServerAuthenticate getServerAuthenticate() {
        return SERVER_AUTHENTICATE;
    }

}

