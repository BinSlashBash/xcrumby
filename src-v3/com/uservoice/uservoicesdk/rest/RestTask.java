package com.uservoice.uservoicesdk.rest;

import android.net.Uri.Builder;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.UserVoice;
import com.uservoice.uservoicesdk.model.AccessToken;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import oauth.signpost.OAuthConsumer;
import org.apache.commons.codec.binary.Hex;
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

public class RestTask extends AsyncTask<String, String, RestResult> {
    private RestTaskCallback callback;
    private RestMethod method;
    private List<BasicNameValuePair> params;
    private HttpUriRequest request;
    private String urlPath;

    public RestTask(RestMethod method, String urlPath, Map<String, String> params, RestTaskCallback callback) {
        this(method, urlPath, params == null ? null : paramsToList(params), callback);
    }

    public RestTask(RestMethod method, String urlPath, List<BasicNameValuePair> params, RestTaskCallback callback) {
        this.method = method;
        this.urlPath = urlPath;
        this.callback = callback;
        this.params = params;
    }

    protected RestResult doInBackground(String... args) {
        AndroidHttpClient client = null;
        RestResult restResult;
        try {
            this.request = createRequest();
            if (isCancelled()) {
                throw new InterruptedException();
            }
            OAuthConsumer consumer = Session.getInstance().getOAuthConsumer();
            if (consumer != null) {
                AccessToken accessToken = Session.getInstance().getAccessToken();
                if (accessToken != null) {
                    consumer.setTokenWithSecret(accessToken.getKey(), accessToken.getSecret());
                }
                consumer.sign(this.request);
            }
            Log.d("UV", this.urlPath);
            this.request.setHeader("Accept-Language", Locale.getDefault().getLanguage());
            this.request.setHeader("API-Client", String.format("uservoice-android-%s", new Object[]{UserVoice.getVersion()}));
            client = AndroidHttpClient.newInstance(String.format("uservoice-android-%s", new Object[]{UserVoice.getVersion()}), Session.getInstance().getContext());
            if (isCancelled()) {
                throw new InterruptedException();
            }
            HttpResponse response = client.execute(this.request);
            if (isCancelled()) {
                throw new InterruptedException();
            }
            HttpEntity responseEntity = response.getEntity();
            StatusLine responseStatus = response.getStatusLine();
            int statusCode = responseStatus != null ? responseStatus.getStatusCode() : 0;
            String body = responseEntity != null ? EntityUtils.toString(responseEntity) : null;
            if (isCancelled()) {
                throw new InterruptedException();
            }
            restResult = new RestResult(statusCode, new JSONObject(body));
            if (client != null) {
                client.close();
            }
            return restResult;
        } catch (Exception e) {
            restResult = new RestResult(e);
            return restResult;
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    private HttpUriRequest createRequest() throws URISyntaxException, UnsupportedEncodingException {
        String host = Session.getInstance().getConfig().getSite();
        Builder uriBuilder = new Builder();
        uriBuilder.scheme(host.contains(".us.com") ? "http" : "https");
        uriBuilder.encodedAuthority(host);
        uriBuilder.path(this.urlPath);
        if (this.method == RestMethod.GET) {
            return requestWithQueryString(new HttpGet(), uriBuilder);
        }
        if (this.method == RestMethod.DELETE) {
            return requestWithQueryString(new HttpDelete(), uriBuilder);
        }
        if (this.method == RestMethod.POST) {
            return requestWithEntity(new HttpPost(), uriBuilder);
        }
        if (this.method == RestMethod.PUT) {
            return requestWithEntity(new HttpPut(), uriBuilder);
        }
        throw new IllegalArgumentException("Method must be one of [GET, POST, PUT, DELETE], but was " + this.method);
    }

    protected void onPostExecute(RestResult result) {
        if (result.isError()) {
            this.callback.onError(result);
        } else {
            try {
                this.callback.onComplete(result.getObject());
            } catch (JSONException e) {
                this.callback.onError(new RestResult(e, result.getStatusCode(), result.getObject()));
            }
        }
        super.onPostExecute(result);
    }

    private HttpUriRequest requestWithQueryString(HttpRequestBase request, Builder uriBuilder) throws URISyntaxException {
        if (this.params != null) {
            for (BasicNameValuePair param : this.params) {
                uriBuilder.appendQueryParameter(param.getName(), param.getValue());
            }
        }
        request.setURI(new URI(uriBuilder.build().toString()));
        return request;
    }

    private HttpUriRequest requestWithEntity(HttpEntityEnclosingRequestBase request, Builder uriBuilder) throws UnsupportedEncodingException, URISyntaxException {
        if (this.params != null) {
            request.setEntity(new UrlEncodedFormEntity(this.params, Hex.DEFAULT_CHARSET_NAME));
        }
        request.setURI(new URI(uriBuilder.build().toString()));
        return request;
    }

    public static List<BasicNameValuePair> paramsToList(Map<String, String> params) {
        ArrayList<BasicNameValuePair> formList = new ArrayList(params.size());
        for (String key : params.keySet()) {
            String value = (String) params.get(key);
            if (value != null) {
                formList.add(new BasicNameValuePair(key, value));
            }
        }
        return formList;
    }
}
