/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpResponse
 *  org.apache.http.StatusLine
 *  org.apache.http.client.HttpClient
 *  org.apache.http.client.methods.HttpGet
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.conn.ClientConnectionManager
 *  org.apache.http.impl.client.DefaultHttpClient
 *  org.apache.http.params.BasicHttpParams
 *  org.apache.http.params.HttpConnectionParams
 *  org.apache.http.params.HttpParams
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.bl;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

class av
implements bl {
    private HttpClient Yg;

    av() {
    }

    private InputStream a(HttpClient object, HttpResponse httpResponse) throws IOException {
        int n2 = httpResponse.getStatusLine().getStatusCode();
        if (n2 == 200) {
            bh.y("Success response");
            return httpResponse.getEntity().getContent();
        }
        object = "Bad response: " + n2;
        if (n2 == 404) {
            throw new FileNotFoundException((String)object);
        }
        throw new IOException((String)object);
    }

    private void a(HttpClient httpClient) {
        if (httpClient != null && httpClient.getConnectionManager() != null) {
            httpClient.getConnectionManager().shutdown();
        }
    }

    @Override
    public InputStream bD(String string2) throws IOException {
        this.Yg = this.kF();
        string2 = this.Yg.execute((HttpUriRequest)new HttpGet(string2));
        return this.a(this.Yg, (HttpResponse)string2);
    }

    @Override
    public void close() {
        this.a(this.Yg);
    }

    HttpClient kF() {
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout((HttpParams)basicHttpParams, (int)20000);
        HttpConnectionParams.setSoTimeout((HttpParams)basicHttpParams, (int)20000);
        return new DefaultHttpClient((HttpParams)basicHttpParams);
    }
}

