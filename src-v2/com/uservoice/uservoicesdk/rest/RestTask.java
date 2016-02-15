/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.net.http.AndroidHttpClient
 *  android.os.AsyncTask
 *  android.util.Log
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpResponse
 *  org.apache.http.StatusLine
 *  org.apache.http.client.entity.UrlEncodedFormEntity
 *  org.apache.http.client.methods.HttpDelete
 *  org.apache.http.client.methods.HttpEntityEnclosingRequestBase
 *  org.apache.http.client.methods.HttpGet
 *  org.apache.http.client.methods.HttpPost
 *  org.apache.http.client.methods.HttpPut
 *  org.apache.http.client.methods.HttpRequestBase
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.message.BasicNameValuePair
 *  org.apache.http.util.EntityUtils
 */
package com.uservoice.uservoicesdk.rest;

import android.content.Context;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import com.uservoice.uservoicesdk.Config;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.UserVoice;
import com.uservoice.uservoicesdk.model.AccessToken;
import com.uservoice.uservoicesdk.rest.RestMethod;
import com.uservoice.uservoicesdk.rest.RestResult;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.http.HttpRequest;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class RestTask
extends AsyncTask<String, String, RestResult> {
    private RestTaskCallback callback;
    private RestMethod method;
    private List<BasicNameValuePair> params;
    private HttpUriRequest request;
    private String urlPath;

    public RestTask(RestMethod restMethod, String string2, List<BasicNameValuePair> list, RestTaskCallback restTaskCallback) {
        this.method = restMethod;
        this.urlPath = string2;
        this.callback = restTaskCallback;
        this.params = list;
    }

    /*
     * Enabled aggressive block sorting
     */
    public RestTask(RestMethod restMethod, String string2, Map<String, String> list, RestTaskCallback restTaskCallback) {
        list = list == null ? null : RestTask.paramsToList(list);
        this(restMethod, string2, list, restTaskCallback);
    }

    /*
     * Enabled aggressive block sorting
     */
    private HttpUriRequest createRequest() throws URISyntaxException, UnsupportedEncodingException {
        String string2 = Session.getInstance().getConfig().getSite();
        Uri.Builder builder = new Uri.Builder();
        String string3 = string2.contains(".us.com") ? "http" : "https";
        builder.scheme(string3);
        builder.encodedAuthority(string2);
        builder.path(this.urlPath);
        if (this.method == RestMethod.GET) {
            return this.requestWithQueryString((HttpRequestBase)new HttpGet(), builder);
        }
        if (this.method == RestMethod.DELETE) {
            return this.requestWithQueryString((HttpRequestBase)new HttpDelete(), builder);
        }
        if (this.method == RestMethod.POST) {
            return this.requestWithEntity((HttpEntityEnclosingRequestBase)new HttpPost(), builder);
        }
        if (this.method == RestMethod.PUT) {
            return this.requestWithEntity((HttpEntityEnclosingRequestBase)new HttpPut(), builder);
        }
        throw new IllegalArgumentException("Method must be one of [GET, POST, PUT, DELETE], but was " + (Object)((Object)this.method));
    }

    public static List<BasicNameValuePair> paramsToList(Map<String, String> map) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>(map.size());
        for (String string2 : map.keySet()) {
            String string3 = map.get(string2);
            if (string3 == null) continue;
            arrayList.add(new BasicNameValuePair(string2, string3));
        }
        return arrayList;
    }

    private HttpUriRequest requestWithEntity(HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase, Uri.Builder builder) throws UnsupportedEncodingException, URISyntaxException {
        if (this.params != null) {
            httpEntityEnclosingRequestBase.setEntity((HttpEntity)new UrlEncodedFormEntity(this.params, "UTF-8"));
        }
        httpEntityEnclosingRequestBase.setURI(new URI(builder.build().toString()));
        return httpEntityEnclosingRequestBase;
    }

    private HttpUriRequest requestWithQueryString(HttpRequestBase httpRequestBase, Uri.Builder builder) throws URISyntaxException {
        if (this.params != null) {
            for (BasicNameValuePair basicNameValuePair : this.params) {
                builder.appendQueryParameter(basicNameValuePair.getName(), basicNameValuePair.getValue());
            }
        }
        httpRequestBase.setURI(new URI(builder.build().toString()));
        return httpRequestBase;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected /* varargs */ RestResult doInBackground(String ... androidHttpClient) {
        AndroidHttpClient androidHttpClient2;
        int n2;
        OAuthConsumer oAuthConsumer;
        Object object = null;
        androidHttpClient = androidHttpClient2 = null;
        Object object2 = object;
        try {
            block15 : {
                try {
                    this.request = this.createRequest();
                    androidHttpClient = androidHttpClient2;
                    object2 = object;
                    if (this.isCancelled()) {
                        androidHttpClient = androidHttpClient2;
                        object2 = object;
                        throw new InterruptedException();
                    }
                    androidHttpClient = androidHttpClient2;
                    object2 = object;
                    oAuthConsumer = Session.getInstance().getOAuthConsumer();
                    if (oAuthConsumer == null) break block15;
                    androidHttpClient = androidHttpClient2;
                    object2 = object;
                }
                catch (Exception var4_5) {
                    RestResult restResult;
                    object2 = androidHttpClient;
                    object2 = restResult = new RestResult(var4_5);
                    if (androidHttpClient == null) return object2;
                    androidHttpClient.close();
                    return restResult;
                }
                AccessToken accessToken = Session.getInstance().getAccessToken();
                if (accessToken != null) {
                    androidHttpClient = androidHttpClient2;
                    object2 = object;
                    oAuthConsumer.setTokenWithSecret(accessToken.getKey(), accessToken.getSecret());
                }
                androidHttpClient = androidHttpClient2;
                object2 = object;
                oAuthConsumer.sign((Object)this.request);
            }
            androidHttpClient = androidHttpClient2;
            object2 = object;
            Log.d((String)"UV", (String)this.urlPath);
            androidHttpClient = androidHttpClient2;
            object2 = object;
            this.request.setHeader("Accept-Language", Locale.getDefault().getLanguage());
            androidHttpClient = androidHttpClient2;
            object2 = object;
            this.request.setHeader("API-Client", String.format("uservoice-android-%s", UserVoice.getVersion()));
            androidHttpClient = androidHttpClient2;
            object2 = object;
            androidHttpClient = androidHttpClient2 = AndroidHttpClient.newInstance((String)String.format("uservoice-android-%s", UserVoice.getVersion()), (Context)Session.getInstance().getContext());
            object2 = androidHttpClient2;
            if (this.isCancelled()) {
                androidHttpClient = androidHttpClient2;
                object2 = androidHttpClient2;
                throw new InterruptedException();
            }
            androidHttpClient = androidHttpClient2;
            object2 = androidHttpClient2;
            oAuthConsumer = androidHttpClient2.execute(this.request);
            androidHttpClient = androidHttpClient2;
            object2 = androidHttpClient2;
            if (this.isCancelled()) {
                androidHttpClient = androidHttpClient2;
                object2 = androidHttpClient2;
                throw new InterruptedException();
            }
        }
        catch (Throwable var1_2) {
            if (object2 == null) throw var1_2;
            object2.close();
            throw var1_2;
        }
        androidHttpClient = androidHttpClient2;
        object2 = androidHttpClient2;
        object = oAuthConsumer.getEntity();
        androidHttpClient = androidHttpClient2;
        object2 = androidHttpClient2;
        if ((oAuthConsumer = oAuthConsumer.getStatusLine()) != null) {
            androidHttpClient = androidHttpClient2;
            object2 = androidHttpClient2;
            n2 = oAuthConsumer.getStatusCode();
        } else {
            n2 = 0;
        }
        if (object != null) {
            androidHttpClient = androidHttpClient2;
            object2 = androidHttpClient2;
            object = EntityUtils.toString((HttpEntity)object);
        } else {
            object = null;
        }
        androidHttpClient = androidHttpClient2;
        object2 = androidHttpClient2;
        if (this.isCancelled()) {
            androidHttpClient = androidHttpClient2;
            object2 = androidHttpClient2;
            throw new InterruptedException();
        }
        androidHttpClient = androidHttpClient2;
        object2 = androidHttpClient2;
        object2 = object = new RestResult(n2, new JSONObject((String)object));
        if (androidHttpClient2 == null) return object2;
        androidHttpClient2.close();
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void onPostExecute(RestResult restResult) {
        if (restResult.isError()) {
            this.callback.onError(restResult);
        } else {
            try {
                this.callback.onComplete(restResult.getObject());
            }
            catch (JSONException var2_2) {
                this.callback.onError(new RestResult(var2_2, restResult.getStatusCode(), restResult.getObject()));
            }
        }
        super.onPostExecute((Object)restResult);
    }
}

