/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextUtils
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
 *  org.apache.http.entity.StringEntity
 *  org.apache.http.message.BasicHttpEntityEnclosingRequest
 */
package com.google.android.gms.analytics;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.aa;
import com.google.android.gms.analytics.ab;
import com.google.android.gms.analytics.ak;
import com.google.android.gms.analytics.n;
import com.google.android.gms.analytics.q;
import com.google.android.gms.analytics.x;
import com.google.android.gms.analytics.y;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
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
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

class ah
implements n {
    private final Context mContext;
    private GoogleAnalytics sX;
    private final String vI;
    private final HttpClient vJ;
    private URL vK;

    ah(HttpClient httpClient, Context context) {
        this(httpClient, GoogleAnalytics.getInstance(context), context);
    }

    ah(HttpClient httpClient, GoogleAnalytics googleAnalytics, Context context) {
        this.mContext = context.getApplicationContext();
        this.vI = this.a("GoogleAnalytics", "3.0", Build.VERSION.RELEASE, ak.a(Locale.getDefault()), Build.MODEL, Build.ID);
        this.vJ = httpClient;
        this.sX = googleAnalytics;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void a(ab ab2, URL uRL, boolean bl2) {
        if (TextUtils.isEmpty((CharSequence)ab2.cU())) return;
        if (!this.db()) {
            return;
        }
        if (uRL == null) {
            uRL = this.vK != null ? this.vK : new URL("https://ssl.google-analytics.com/collect");
        }
        HttpHost httpHost = new HttpHost(uRL.getHost(), uRL.getPort(), uRL.getProtocol());
        try {
            if ((ab2 = this.c(ab2.cU(), uRL.getPath())) == null) return;
            ab2.addHeader("Host", httpHost.toHostString());
            if (aa.cT()) {
                this.a((HttpEntityEnclosingRequest)ab2);
            }
            if (bl2) {
                q.p(this.mContext);
            }
            ab2 = this.vJ.execute(httpHost, (HttpRequest)ab2);
            int n2 = ab2.getStatusLine().getStatusCode();
            uRL = ab2.getEntity();
            if (uRL != null) {
                uRL.consumeContent();
            }
            if (n2 == 200) return;
            aa.z("Bad response: " + ab2.getStatusLine().getStatusCode());
            return;
        }
        catch (ClientProtocolException var1_2) {
            aa.z("ClientProtocolException sending monitoring hit.");
            return;
        }
        catch (MalformedURLException malformedURLException) {
            return;
        }
        catch (IOException iOException) {
            aa.z("Exception sending monitoring hit: " + iOException.getClass().getSimpleName());
            aa.z(iOException.getMessage());
            return;
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
                aa.y("Error Writing hit to log...");
            }
        }
        aa.y(stringBuffer.toString());
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private HttpEntityEnclosingRequest c(String string2, String string3) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            aa.z("Empty hit, discarding.");
            return null;
        }
        String string4 = string3 + "?" + string2;
        if (string4.length() < 2036) {
            string2 = new BasicHttpEntityEnclosingRequest("GET", string4);
        } else {
            string3 = new BasicHttpEntityEnclosingRequest("POST", string3);
            string3.setEntity((HttpEntity)new StringEntity(string2));
            string2 = string3;
        }
        string2.addHeader("User-Agent", this.vI);
        return string2;
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            aa.z("Encoding error, discarding hit");
            return null;
        }
    }

    @Override
    public void F(String string2) {
        try {
            this.vK = new URL(string2);
            return;
        }
        catch (MalformedURLException var1_2) {
            this.vK = null;
            return;
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
    public int a(List<x> var1_1, ab var2_3, boolean var3_4) {
        var7_5 = 0;
        var8_6 = Math.min(var1_1.size(), 40);
        var2_3.c("_hr", var1_1.size());
        var4_7 = 0;
        var13_8 = null;
        var10_9 = true;
        var6_10 = 0;
        do {
            if (var6_10 >= var8_6) {
                var2_3.c("_hd", var4_7);
                var2_3.c("_hs", var7_5);
                if (var3_4 == false) return var7_5;
                this.a(var2_3, (URL)var13_8, var10_9);
                return var7_5;
            }
            var17_19 = var1_1.get(var6_10);
            var14_15 = this.a(var17_19);
            if (var14_15 != null) ** GOTO lbl25
            if (aa.cT()) {
                aa.z("No destination: discarding hit: " + var17_19.cO());
            } else {
                aa.z("No destination: discarding hit.");
            }
            ++var4_7;
            var5_11 = var7_5 + 1;
            ** GOTO lbl81
lbl25: // 1 sources:
            var15_16 = new HttpHost(var14_15.getHost(), var14_15.getPort(), var14_15.getProtocol());
            var16_18 = var14_15.getPath();
            var13_8 = TextUtils.isEmpty((CharSequence)var17_19.cO()) != false ? "" : y.a(var17_19, System.currentTimeMillis());
            if ((var16_18 = this.c((String)var13_8, var16_18)) != null) ** GOTO lbl33
            ++var4_7;
            var5_11 = var7_5 + 1;
            var13_8 = var14_15;
            ** GOTO lbl81
lbl33: // 1 sources:
            var16_18.addHeader("Host", var15_16.toHostString());
            if (aa.cT()) {
                this.a((HttpEntityEnclosingRequest)var16_18);
            }
            if (var13_8.length() <= 8192) ** GOTO lbl40
            aa.z("Hit too long (> 8192 bytes)--not sent");
            var5_11 = var4_7 + 1;
            ** GOTO lbl77
lbl40: // 1 sources:
            if (!this.sX.isDryRunEnabled()) ** GOTO lbl44
            aa.x("Dry run enabled. Hit not actually sent.");
            var5_11 = var4_7;
            ** GOTO lbl77
lbl44: // 1 sources:
            var11_13 = var10_9;
            if (!var10_9) ** GOTO lbl50
            var12_14 = var10_9;
            try {
                q.p(this.mContext);
                var11_13 = false;
lbl50: // 2 sources:
                var12_14 = var11_13;
                var10_9 = var11_13;
                var15_16 = this.vJ.execute(var15_16, (HttpRequest)var16_18);
                var12_14 = var11_13;
                var10_9 = var11_13;
                var9_12 = var15_16.getStatusLine().getStatusCode();
                var12_14 = var11_13;
                var10_9 = var11_13;
                var16_18 = var15_16.getEntity();
                if (var16_18 != null) {
                    var12_14 = var11_13;
                    var10_9 = var11_13;
                    var16_18.consumeContent();
                }
                var10_9 = var11_13;
                var5_11 = var4_7;
                if (var9_12 != 200) {
                    var12_14 = var11_13;
                    var10_9 = var11_13;
                    aa.z("Bad response: " + var15_16.getStatusLine().getStatusCode());
                    var10_9 = var11_13;
                    var5_11 = var4_7;
                }
            }
            catch (ClientProtocolException var15_17) {
                aa.z("ClientProtocolException sending hit; discarding hit...");
                var2_3.c("_hd", var4_7);
                var10_9 = var12_14;
                var5_11 = var4_7;
            }
lbl77: // 4 sources:
            var2_3.c("_td", var13_8.getBytes().length);
            var13_8 = var14_15;
            var4_7 = var5_11;
            var5_11 = ++var7_5;
lbl81: // 3 sources:
            ++var6_10;
            var7_5 = var5_11;
        } while (true);
        catch (IOException var1_2) {
            aa.z("Exception sending hit: " + var1_2.getClass().getSimpleName());
            aa.z(var1_2.getMessage());
            var2_3.c("_de", 1);
            var2_3.c("_hd", var4_7);
            var2_3.c("_hs", var7_5);
            this.a(var2_3, var14_15, var10_9);
            return var7_5;
        }
    }

    String a(String string2, String string3, String string4, String string5, String string6, String string7) {
        return String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", string2, string3, string4, string5, string6, string7);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    URL a(x object) {
        if (this.vK != null) {
            return this.vK;
        }
        object = object.cR();
        try {
            object = "http:".equals(object) ? "http://www.google-analytics.com/collect" : "https://ssl.google-analytics.com/collect";
            return new URL((String)object);
        }
        catch (MalformedURLException var1_2) {
            aa.w("Error trying to parse the hardcoded host url. This really shouldn't happen.");
            return null;
        }
    }

    @Override
    public boolean ch() {
        NetworkInfo networkInfo = ((ConnectivityManager)this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            aa.y("...no network connectivity");
            return false;
        }
        return true;
    }

    boolean db() {
        if (Math.random() * 100.0 <= 1.0) {
            return true;
        }
        return false;
    }
}

