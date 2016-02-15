package com.crumby.impl.derpibooru;

import com.crumby.lib.authentication.ServerAuthenticate;
import com.crumby.lib.fragment.producer.GalleryProducer;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import oauth.signpost.OAuth;
import org.apache.commons.codec.binary.Hex;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class DerpibooruAuthenticatorService extends AuthenticatorService {
    public static String AUTHENTICITY_TOKEN = null;
    public static final String FORM_TITLE = "DERPIBOORU";
    public static final String FORM_USER_TITLE = "EMAIL ADDRESS";
    public static final ServerAuthenticate SERVER_AUTHENTICATE;

    /* renamed from: com.crumby.impl.derpibooru.DerpibooruAuthenticatorService.1 */
    static class C07321 implements ServerAuthenticate {
        C07321() {
        }

        public String userSignInAndGetAuth(String user, String pass, String authType) throws Exception {
            userSignIn(user, pass, authType);
            Iterator i$ = Jsoup.parse(GalleryProducer.fetchUrl("https://derpibooru.org/users/edit")).getElementsByTag("h3").iterator();
            while (i$.hasNext()) {
                Element h3 = (Element) i$.next();
                if (h3.text().equals("API Key")) {
                    return h3.nextElementSibling().getElementsByTag("strong").first().text();
                }
            }
            throw new Exception("Could not log you in. Invalid user credentials.");
        }

        public void userSignIn(String user, String pass, String authType) throws Exception {
            DerpibooruAuthenticatorService.AUTHENTICITY_TOKEN = DerpibooruProducer.getAuthKey(GalleryProducer.fetchUrl("https://derpibooru.org/users/sign_in"));
            if (DerpibooruAuthenticatorService.AUTHENTICITY_TOKEN == null) {
                throw new Exception("Could not login with your credentials");
            }
            HttpURLConnection urlConnection = (HttpURLConnection) new URL("https://derpibooru.org/users/sign_in").openConnection();
            urlConnection.setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
            urlConnection.setRequestProperty("Content-Type", OAuth.FORM_ENCODED);
            urlConnection.setRequestProperty("Accept", "*/*");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setInstanceFollowRedirects(true);
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write("authenticity_token=" + URLEncoder.encode(DerpibooruAuthenticatorService.AUTHENTICITY_TOKEN, Hex.DEFAULT_CHARSET_NAME) + "&user[email]=" + URLEncoder.encode(user, Hex.DEFAULT_CHARSET_NAME) + "&user[password]=" + URLEncoder.encode(pass, Hex.DEFAULT_CHARSET_NAME) + "&user[remember_me]=1&commit=Sign+in");
            out.close();
            urlConnection.connect();
            GalleryProducer.readStreamIntoString(urlConnection.getInputStream());
        }
    }

    static {
        SERVER_AUTHENTICATE = new C07321();
    }

    protected ServerAuthenticate getServerAuthenticate() {
        return SERVER_AUTHENTICATE;
    }
}
