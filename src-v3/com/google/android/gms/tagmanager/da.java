package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import uk.co.senab.photoview.IPhotoView;

class da implements ab {
    private final Context aac;
    private final String aat;
    private final HttpClient aau;
    private C0520a aav;

    /* renamed from: com.google.android.gms.tagmanager.da.a */
    public interface C0520a {
        void m1454a(ap apVar);

        void m1455b(ap apVar);

        void m1456c(ap apVar);
    }

    da(HttpClient httpClient, Context context, C0520a c0520a) {
        this.aac = context.getApplicationContext();
        this.aat = m2521a("GoogleTagManager", "4.00", VERSION.RELEASE, m2520b(Locale.getDefault()), Build.MODEL, Build.ID);
        this.aau = httpClient;
        this.aav = c0520a;
    }

    private HttpEntityEnclosingRequest m2518a(URL url) {
        HttpEntityEnclosingRequest basicHttpEntityEnclosingRequest;
        URISyntaxException e;
        try {
            basicHttpEntityEnclosingRequest = new BasicHttpEntityEnclosingRequest("GET", url.toURI().toString());
            try {
                basicHttpEntityEnclosingRequest.addHeader("User-Agent", this.aat);
            } catch (URISyntaxException e2) {
                e = e2;
                bh.m1387z("Exception sending hit: " + e.getClass().getSimpleName());
                bh.m1387z(e.getMessage());
                return basicHttpEntityEnclosingRequest;
            }
        } catch (URISyntaxException e3) {
            URISyntaxException uRISyntaxException = e3;
            basicHttpEntityEnclosingRequest = null;
            e = uRISyntaxException;
            bh.m1387z("Exception sending hit: " + e.getClass().getSimpleName());
            bh.m1387z(e.getMessage());
            return basicHttpEntityEnclosingRequest;
        }
        return basicHttpEntityEnclosingRequest;
    }

    private void m2519a(HttpEntityEnclosingRequest httpEntityEnclosingRequest) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Object obj : httpEntityEnclosingRequest.getAllHeaders()) {
            stringBuffer.append(obj.toString()).append("\n");
        }
        stringBuffer.append(httpEntityEnclosingRequest.getRequestLine().toString()).append("\n");
        if (httpEntityEnclosingRequest.getEntity() != null) {
            try {
                InputStream content = httpEntityEnclosingRequest.getEntity().getContent();
                if (content != null) {
                    int available = content.available();
                    if (available > 0) {
                        byte[] bArr = new byte[available];
                        content.read(bArr);
                        stringBuffer.append("POST:\n");
                        stringBuffer.append(new String(bArr)).append("\n");
                    }
                }
            } catch (IOException e) {
                bh.m1386y("Error Writing hit to log...");
            }
        }
        bh.m1386y(stringBuffer.toString());
    }

    static String m2520b(Locale locale) {
        if (locale == null || locale.getLanguage() == null || locale.getLanguage().length() == 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(locale.getLanguage().toLowerCase());
        if (!(locale.getCountry() == null || locale.getCountry().length() == 0)) {
            stringBuilder.append("-").append(locale.getCountry().toLowerCase());
        }
        return stringBuilder.toString();
    }

    String m2521a(String str, String str2, String str3, String str4, String str5, String str6) {
        return String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", new Object[]{str, str2, str3, str4, str5, str6});
    }

    public boolean ch() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.aac.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        bh.m1386y("...no network connectivity");
        return false;
    }

    URL m2522d(ap apVar) {
        try {
            return new URL(apVar.kE());
        } catch (MalformedURLException e) {
            bh.m1384w("Error trying to parse the GTM url.");
            return null;
        }
    }

    public void m2523d(List<ap> list) {
        int min = Math.min(list.size(), 40);
        Object obj = 1;
        int i = 0;
        while (i < min) {
            Object obj2;
            ap apVar = (ap) list.get(i);
            URL d = m2522d(apVar);
            if (d == null) {
                bh.m1387z("No destination: discarding hit.");
                this.aav.m1455b(apVar);
                obj2 = obj;
            } else {
                HttpEntityEnclosingRequest a = m2518a(d);
                if (a == null) {
                    this.aav.m1455b(apVar);
                    obj2 = obj;
                } else {
                    HttpHost httpHost = new HttpHost(d.getHost(), d.getPort(), d.getProtocol());
                    a.addHeader("Host", httpHost.toHostString());
                    m2519a(a);
                    if (obj != null) {
                        try {
                            bn.m1395p(this.aac);
                            obj = null;
                        } catch (ClientProtocolException e) {
                            bh.m1387z("ClientProtocolException sending hit; discarding hit...");
                            this.aav.m1455b(apVar);
                            obj2 = obj;
                        } catch (IOException e2) {
                            bh.m1387z("Exception sending hit: " + e2.getClass().getSimpleName());
                            bh.m1387z(e2.getMessage());
                            this.aav.m1456c(apVar);
                            obj2 = obj;
                        }
                    }
                    HttpResponse execute = this.aau.execute(httpHost, a);
                    int statusCode = execute.getStatusLine().getStatusCode();
                    HttpEntity entity = execute.getEntity();
                    if (entity != null) {
                        entity.consumeContent();
                    }
                    if (statusCode != IPhotoView.DEFAULT_ZOOM_DURATION) {
                        bh.m1387z("Bad response: " + execute.getStatusLine().getStatusCode());
                        this.aav.m1456c(apVar);
                    } else {
                        this.aav.m1454a(apVar);
                    }
                    obj2 = obj;
                }
            }
            i++;
            obj = obj2;
        }
    }
}
