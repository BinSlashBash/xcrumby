/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.derpibooru;

import com.crumby.impl.derpibooru.AuthenticatorService;
import com.crumby.impl.derpibooru.DerpibooruProducer;
import com.crumby.lib.authentication.ServerAuthenticate;
import com.crumby.lib.fragment.producer.GalleryProducer;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DerpibooruAuthenticatorService
extends AuthenticatorService {
    public static String AUTHENTICITY_TOKEN;
    public static final String FORM_TITLE = "DERPIBOORU";
    public static final String FORM_USER_TITLE = "EMAIL ADDRESS";
    public static final ServerAuthenticate SERVER_AUTHENTICATE;

    static {
        SERVER_AUTHENTICATE = new ServerAuthenticate(){

            @Override
            public void userSignIn(String string2, String string3, String object) throws Exception {
                DerpibooruAuthenticatorService.AUTHENTICITY_TOKEN = DerpibooruProducer.getAuthKey(GalleryProducer.fetchUrl("https://derpibooru.org/users/sign_in"));
                if (DerpibooruAuthenticatorService.AUTHENTICITY_TOKEN == null) {
                    throw new Exception("Could not login with your credentials");
                }
                object = (HttpURLConnection)new URL("https://derpibooru.org/users/sign_in").openConnection();
                object.setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
                object.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                object.setRequestProperty("Accept", "*/*");
                object.setDoInput(true);
                object.setDoOutput(true);
                object.setInstanceFollowRedirects(true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(object.getOutputStream());
                outputStreamWriter.write("authenticity_token=" + URLEncoder.encode(DerpibooruAuthenticatorService.AUTHENTICITY_TOKEN, "UTF-8") + "&user[email]=" + URLEncoder.encode(string2, "UTF-8") + "&user[password]=" + URLEncoder.encode(string3, "UTF-8") + "&user[remember_me]=1&commit=Sign+in");
                outputStreamWriter.close();
                object.connect();
                GalleryProducer.readStreamIntoString(object.getInputStream());
            }

            @Override
            public String userSignInAndGetAuth(String object, String object22, String string2) throws Exception {
                this.userSignIn((String)object, (String)object22, string2);
                for (Object object22 : Jsoup.parse(GalleryProducer.fetchUrl("https://derpibooru.org/users/edit")).getElementsByTag("h3")) {
                    if (!object22.text().equals("API Key")) continue;
                    return object22.nextElementSibling().getElementsByTag("strong").first().text();
                }
                throw new Exception("Could not log you in. Invalid user credentials.");
            }
        };
    }

    @Override
    protected ServerAuthenticate getServerAuthenticate() {
        return SERVER_AUTHENTICATE;
    }

}

