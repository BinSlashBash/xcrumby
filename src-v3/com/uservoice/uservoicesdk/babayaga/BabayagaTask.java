package com.uservoice.uservoicesdk.babayaga;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import com.crumby.impl.device.DeviceFragment;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.UserVoice;
import com.uservoice.uservoicesdk.babayaga.Babayaga.Event;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class BabayagaTask extends AsyncTask<String, String, Void> {
    private final String event;
    private final Map<String, Object> eventProps;
    private final Map<String, Object> traits;
    private final String uvts;

    public BabayagaTask(String event, String uvts, Map<String, Object> traits, Map<String, Object> eventProps) {
        this.event = event;
        this.uvts = uvts;
        this.traits = traits;
        this.eventProps = eventProps;
    }

    protected Void doInBackground(String... args) {
        AndroidHttpClient client = null;
        try {
            String subdomain;
            String route;
            JSONObject data = new JSONObject();
            if (this.traits != null) {
                if (!this.traits.isEmpty()) {
                    data.put("u", new JSONObject(this.traits));
                }
            }
            if (this.eventProps != null) {
                if (!this.eventProps.isEmpty()) {
                    data.put("e", (Object) this.eventProps);
                }
            }
            if (Session.getInstance().getClientConfig() != null) {
                subdomain = Session.getInstance().getClientConfig().getSubdomainId();
                route = "t";
            } else {
                subdomain = Session.getInstance().getConfig().getSite().split("\\.")[0];
                route = "t/k";
            }
            String channel = this.event.equals(Event.VIEW_APP) ? Babayaga.EXTERNAL_CHANNEL : Babayaga.CHANNEL;
            Object[] objArr = new Object[5];
            objArr[0] = Babayaga.DOMAIN;
            objArr[1] = route;
            objArr[2] = subdomain;
            objArr[3] = channel;
            objArr[4] = this.event;
            StringBuilder stringBuilder = new StringBuilder(String.format("https://%s/%s/%s/%s/%s", objArr));
            if (this.uvts != null) {
                stringBuilder.append(DeviceFragment.REGEX_BASE);
                stringBuilder.append(this.uvts);
            }
            stringBuilder.append("/track.js?_=");
            stringBuilder.append(new Date().getTime());
            stringBuilder.append("&c=_");
            if (data.length() != 0) {
                stringBuilder.append("&d=");
                stringBuilder.append(URLEncoder.encode(Base64.encodeToString(data.toString().getBytes(), 2), Hex.DEFAULT_CHARSET_NAME));
            }
            Log.d("UV", stringBuilder.toString());
            HttpRequestBase request = new HttpGet();
            request.setURI(new URI(stringBuilder.toString()));
            client = AndroidHttpClient.newInstance(String.format("uservoice-android-%s", new Object[]{UserVoice.getVersion()}), Session.getInstance().getContext());
            HttpResponse response = client.execute(request);
            HttpEntity responseEntity = response.getEntity();
            StatusLine responseStatus = response.getStatusLine();
            if ((responseStatus != null ? responseStatus.getStatusCode() : 0) == 200) {
                String body = responseEntity != null ? EntityUtils.toString(responseEntity) : null;
                if (body != null && body.length() > 0) {
                    Babayaga.setUvts(new JSONObject(body.substring(2, body.length() - 2)).getString("uvts"));
                }
                if (client != null) {
                    client.close();
                }
                return null;
            } else if (client == null) {
                return null;
            } else {
                client.close();
                return null;
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (Exception e2) {
            try {
                e2.printStackTrace();
                Log.e("UV", String.format("%s: %s", new Object[]{e2.getClass().getName(), e2.getMessage()}));
                return null;
            } finally {
                if (client != null) {
                    client.close();
                }
            }
        }
    }
}
