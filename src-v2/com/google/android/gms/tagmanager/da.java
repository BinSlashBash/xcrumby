/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 *  org.apache.http.Header
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpEntityEnclosingRequest
 *  org.apache.http.HttpHost
 *  org.apache.http.HttpRequest
 *  org.apache.http.HttpResponse
 *  org.apache.http.RequestLine
 *  org.apache.http.StatusLine
 *  org.apache.http.client.ClientProtocolException
 *  org.apache.http.client.HttpClient
 *  org.apache.http.message.BasicHttpEntityEnclosingRequest
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import com.google.android.gms.tagmanager.ab;
import com.google.android.gms.tagmanager.ap;
import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.bn;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

class da
implements ab {
    private final Context aac;
    private final String aat;
    private final HttpClient aau;
    private a aav;

    da(HttpClient httpClient, Context context, a a2) {
        this.aac = context.getApplicationContext();
        this.aat = this.a("GoogleTagManager", "4.00", Build.VERSION.RELEASE, da.b(Locale.getDefault()), Build.MODEL, Build.ID);
        this.aau = httpClient;
        this.aav = a2;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private HttpEntityEnclosingRequest a(URL uRL) {
        try {
            uRL = new BasicHttpEntityEnclosingRequest("GET", uRL.toURI().toString());
        }
        catch (URISyntaxException var2_2) {
            void var2_3;
            uRL = null;
            bh.z("Exception sending hit: " + var2_3.getClass().getSimpleName());
            bh.z(var2_3.getMessage());
            return uRL;
        }
        uRL.addHeader("User-Agent", this.aat);
        return uRL;
        {
            catch (URISyntaxException uRISyntaxException) {}
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void a(HttpEntityEnclosingRequest object) {
        int n2;
        StringBuffer stringBuffer = new StringBuffer();
        Object object2 = object.getAllHeaders();
        int n3 = object2.length;
        for (n2 = 0; n2 < n3; ++n2) {
            stringBuffer.append(object2[n2].toString()).append("\n");
        }
        stringBuffer.append(object.getRequestLine().toString()).append("\n");
        if (object.getEntity() != null) {
            try {
                object = object.getEntity().getContent();
                if (object != null && (n2 = object.available()) > 0) {
                    object2 = new byte[n2];
                    object.read((byte[])object2);
                    stringBuffer.append("POST:\n");
                    stringBuffer.append(new String((byte[])object2)).append("\n");
                }
            }
            catch (IOException var1_2) {
                bh.y("Error Writing hit to log...");
            }
        }
        bh.y(stringBuffer.toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    static String b(Locale locale) {
        if (locale == null || locale.getLanguage() == null || locale.getLanguage().length() == 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(locale.getLanguage().toLowerCase());
        if (locale.getCountry() != null && locale.getCountry().length() != 0) {
            stringBuilder.append("-").append(locale.getCountry().toLowerCase());
        }
        return stringBuilder.toString();
    }

    String a(String string2, String string3, String string4, String string5, String string6, String string7) {
        return String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", string2, string3, string4, string5, string6, string7);
    }

    @Override
    public boolean ch() {
        NetworkInfo networkInfo = ((ConnectivityManager)this.aac.getSystemService("connectivity")).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            bh.y("...no network connectivity");
            return false;
        }
        return true;
    }

    URL d(ap object) {
        object = object.kE();
        try {
            object = new URL((String)object);
            return object;
        }
        catch (MalformedURLException var1_2) {
            bh.w("Error trying to parse the GTM url.");
            return null;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void d(List<ap> var1_1) {
        var6_2 = Math.min(var1_1.size(), 40);
        var2_3 = true;
        var5_4 = 0;
        while (var5_4 < var6_2) {
            block8 : {
                var8_8 = var1_1.get(var5_4);
                var10_12 = this.d(var8_8);
                if (var10_12 != null) ** GOTO lbl11
                bh.z("No destination: discarding hit.");
                this.aav.b(var8_8);
                ** GOTO lbl60
lbl11: // 1 sources:
                var9_9 = this.a(var10_12);
                if (var9_9 != null) ** GOTO lbl15
                this.aav.b(var8_8);
                ** GOTO lbl60
lbl15: // 1 sources:
                var10_12 = new HttpHost(var10_12.getHost(), var10_12.getPort(), var10_12.getProtocol());
                var9_9.addHeader("Host", var10_12.toHostString());
                this.a(var9_9);
                var3_5 = var2_3;
                if (!var2_3) ** GOTO lbl24
                var4_6 = var2_3;
                try {
                    bn.p(this.aac);
                    var3_5 = false;
lbl24: // 2 sources:
                    var4_6 = var3_5;
                    var2_3 = var3_5;
                    var9_9 = this.aau.execute((HttpHost)var10_12, (HttpRequest)var9_9);
                    var4_6 = var3_5;
                    var2_3 = var3_5;
                    var7_7 = var9_9.getStatusLine().getStatusCode();
                    var4_6 = var3_5;
                    var2_3 = var3_5;
                    var10_12 = var9_9.getEntity();
                    if (var10_12 != null) {
                        var4_6 = var3_5;
                        var2_3 = var3_5;
                        var10_12.consumeContent();
                    }
                    if (var7_7 != 200) {
                        var4_6 = var3_5;
                        var2_3 = var3_5;
                        bh.z("Bad response: " + var9_9.getStatusLine().getStatusCode());
                        var4_6 = var3_5;
                        var2_3 = var3_5;
                        this.aav.c(var8_8);
                    } else {
                        var4_6 = var3_5;
                        var2_3 = var3_5;
                        this.aav.a(var8_8);
                    }
                }
                catch (ClientProtocolException var9_10) {
                    bh.z("ClientProtocolException sending hit; discarding hit...");
                    this.aav.b(var8_8);
                    var2_3 = var4_6;
                    break block8;
                }
                catch (IOException var9_11) {
                    bh.z("Exception sending hit: " + var9_11.getClass().getSimpleName());
                    bh.z(var9_11.getMessage());
                    this.aav.c(var8_8);
                    break block8;
                }
                var2_3 = var3_5;
            }
            ++var5_4;
        }
    }

    public static interface a {
        public void a(ap var1);

        public void b(ap var1);

        public void c(ap var1);
    }

}

