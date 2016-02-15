/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.bl;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

class aw
implements bl {
    private HttpURLConnection Yh;

    aw() {
    }

    private InputStream a(HttpURLConnection object) throws IOException {
        int n2 = object.getResponseCode();
        if (n2 == 200) {
            return object.getInputStream();
        }
        object = "Bad response: " + n2;
        if (n2 == 404) {
            throw new FileNotFoundException((String)object);
        }
        throw new IOException((String)object);
    }

    private void b(HttpURLConnection httpURLConnection) {
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }

    @Override
    public InputStream bD(String string2) throws IOException {
        this.Yh = this.bE(string2);
        return this.a(this.Yh);
    }

    HttpURLConnection bE(String object) throws IOException {
        object = (HttpURLConnection)new URL((String)object).openConnection();
        object.setReadTimeout(20000);
        object.setConnectTimeout(20000);
        return object;
    }

    @Override
    public void close() {
        this.b(this.Yh);
    }
}

