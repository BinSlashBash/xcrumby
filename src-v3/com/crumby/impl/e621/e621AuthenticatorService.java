package com.crumby.impl.e621;

import com.crumby.CrumbyApp;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.impl.derpibooru.AuthenticatorService;
import com.crumby.lib.authentication.ServerAuthenticate;
import com.google.android.gms.plus.PlusShare;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request.Builder;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class e621AuthenticatorService extends AuthenticatorService {
    public static String AUTHENTICITY_TOKEN = null;
    public static final String FORM_TITLE = "DERPIBOORU";
    public static final String FORM_USER_TITLE = "EMAIL ADDRESS";
    public static final ServerAuthenticate SERVER_AUTHENTICATE;

    /* renamed from: com.crumby.impl.e621.e621AuthenticatorService.1 */
    static class C07331 implements ServerAuthenticate {
        C07331() {
        }

        public String userSignInAndGetAuth(String user, String pass, String authType) throws Exception {
            userSignIn(user, pass, authType);
            return "FUCK";
        }

        private boolean checkForLogin(String body) {
            Document doc = Jsoup.parse(body);
            Element el = doc.getElementById("notice");
            if ((el == null || !el.text().contains("are now logged in")) && !doc.getElementsByTag(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE).get(0).text().contains("Account")) {
                return false;
            }
            return true;
        }

        public void userSignIn(String user, String pass, String authType) throws Exception {
            String body = CrumbyApp.getHttpClient().newCall(new Builder().url("https://e621.net/user/login").build()).execute().body().string();
            String AUTH_TOKEN = "authenticity_token";
            Elements elements = Jsoup.parse(body).getElementsByAttributeValue("name", "authenticity_token");
            String authToken = UnsupportedUrlFragment.DISPLAY_NAME;
            Iterator i$ = elements.iterator();
            while (i$.hasNext()) {
                Element element = (Element) i$.next();
                if (element.tagName().equals("input")) {
                    authToken = element.attr("value");
                    break;
                }
            }
            if (!checkForLogin(body)) {
                if (!checkForLogin(CrumbyApp.getHttpClient().newCall(new Builder().url("https://e621.net/user/authenticate").header("Origin", e621Fragment.BASE_URL).header("Referer", "https://e621.net/user/login").post(new FormEncodingBuilder().add("authenticity_token", authToken).add("user[name]", user).add("user[password]", pass).add("user[roaming]", "1").build()).build()).execute().body().string())) {
                    throw new Exception("Invalid username and/or password");
                }
            }
        }
    }

    static {
        SERVER_AUTHENTICATE = new C07331();
    }

    protected ServerAuthenticate getServerAuthenticate() {
        return SERVER_AUTHENTICATE;
    }
}
