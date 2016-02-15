package com.uservoice.uservoicesdk.babayaga;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.UserVoice;
import com.uservoice.uservoicesdk.model.ClientConfig;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class BabayagaTask
  extends AsyncTask<String, String, Void>
{
  private final String event;
  private final Map<String, Object> eventProps;
  private final Map<String, Object> traits;
  private final String uvts;
  
  public BabayagaTask(String paramString1, String paramString2, Map<String, Object> paramMap1, Map<String, Object> paramMap2)
  {
    this.event = paramString1;
    this.uvts = paramString2;
    this.traits = paramMap1;
    this.eventProps = paramMap2;
  }
  
  protected Void doInBackground(String... paramVarArgs)
  {
    localObject7 = null;
    localObject6 = null;
    localObject1 = localObject6;
    paramVarArgs = (String[])localObject7;
    for (;;)
    {
      try
      {
        localJSONObject = new JSONObject();
        localObject1 = localObject6;
        paramVarArgs = (String[])localObject7;
        if (this.traits != null)
        {
          localObject1 = localObject6;
          paramVarArgs = (String[])localObject7;
          if (!this.traits.isEmpty())
          {
            localObject1 = localObject6;
            paramVarArgs = (String[])localObject7;
            localJSONObject.put("u", new JSONObject(this.traits));
          }
        }
        localObject1 = localObject6;
        paramVarArgs = (String[])localObject7;
        if (this.eventProps != null)
        {
          localObject1 = localObject6;
          paramVarArgs = (String[])localObject7;
          if (!this.eventProps.isEmpty())
          {
            localObject1 = localObject6;
            paramVarArgs = (String[])localObject7;
            localJSONObject.put("e", this.eventProps);
          }
        }
        localObject1 = localObject6;
        paramVarArgs = (String[])localObject7;
        if (Session.getInstance().getClientConfig() != null)
        {
          localObject1 = localObject6;
          paramVarArgs = (String[])localObject7;
          localObject4 = Session.getInstance().getClientConfig().getSubdomainId();
          localObject3 = "t";
          localObject1 = localObject6;
          paramVarArgs = (String[])localObject7;
          if (!this.event.equals(Babayaga.Event.VIEW_APP)) {
            continue;
          }
          localObject1 = localObject6;
          paramVarArgs = (String[])localObject7;
          localObject5 = Babayaga.EXTERNAL_CHANNEL;
          localObject1 = localObject6;
          paramVarArgs = (String[])localObject7;
          localObject3 = new StringBuilder(String.format("https://%s/%s/%s/%s/%s", new Object[] { Babayaga.DOMAIN, localObject3, localObject4, localObject5, this.event }));
          localObject1 = localObject6;
          paramVarArgs = (String[])localObject7;
          if (this.uvts != null)
          {
            localObject1 = localObject6;
            paramVarArgs = (String[])localObject7;
            ((StringBuilder)localObject3).append("/");
            localObject1 = localObject6;
            paramVarArgs = (String[])localObject7;
            ((StringBuilder)localObject3).append(this.uvts);
          }
          localObject1 = localObject6;
          paramVarArgs = (String[])localObject7;
          ((StringBuilder)localObject3).append("/track.js?_=");
          localObject1 = localObject6;
          paramVarArgs = (String[])localObject7;
          ((StringBuilder)localObject3).append(new Date().getTime());
          localObject1 = localObject6;
          paramVarArgs = (String[])localObject7;
          ((StringBuilder)localObject3).append("&c=_");
          localObject1 = localObject6;
          paramVarArgs = (String[])localObject7;
          if (localJSONObject.length() != 0)
          {
            localObject1 = localObject6;
            paramVarArgs = (String[])localObject7;
            ((StringBuilder)localObject3).append("&d=");
            localObject1 = localObject6;
            paramVarArgs = (String[])localObject7;
          }
        }
      }
      catch (Exception localException)
      {
        JSONObject localJSONObject;
        Object localObject3;
        Object localObject5;
        paramVarArgs = (String[])localObject1;
        localException.printStackTrace();
        paramVarArgs = (String[])localObject1;
        Log.e("UV", String.format("%s: %s", new Object[] { localException.getClass().getName(), localException.getMessage() }));
        if (localObject1 == null) {
          continue;
        }
        ((AndroidHttpClient)localObject1).close();
        return null;
        int i = 0;
        continue;
        if (localObject4 == null) {
          continue;
        }
        localObject1 = localException;
        paramVarArgs = localException;
        Object localObject4 = EntityUtils.toString((HttpEntity)localObject4);
        if (localObject4 == null) {
          continue;
        }
        localObject1 = localException;
        paramVarArgs = localException;
        if (((String)localObject4).length() <= 0) {
          continue;
        }
        localObject1 = localException;
        paramVarArgs = localException;
        Babayaga.setUvts(new JSONObject(((String)localObject4).substring(2, ((String)localObject4).length() - 2)).getString("uvts"));
        if (localException == null) {
          continue;
        }
        localException.close();
        continue;
        localObject4 = null;
        continue;
      }
      finally
      {
        if (paramVarArgs == null) {
          continue;
        }
        paramVarArgs.close();
      }
      try
      {
        ((StringBuilder)localObject3).append(URLEncoder.encode(Base64.encodeToString(localJSONObject.toString().getBytes(), 2), "UTF-8"));
        localObject1 = localObject6;
        paramVarArgs = (String[])localObject7;
        Log.d("UV", ((StringBuilder)localObject3).toString());
        localObject1 = localObject6;
        paramVarArgs = (String[])localObject7;
        localObject4 = new HttpGet();
        localObject1 = localObject6;
        paramVarArgs = (String[])localObject7;
        ((HttpRequestBase)localObject4).setURI(new URI(((StringBuilder)localObject3).toString()));
        localObject1 = localObject6;
        paramVarArgs = (String[])localObject7;
        localObject3 = AndroidHttpClient.newInstance(String.format("uservoice-android-%s", new Object[] { UserVoice.getVersion() }), Session.getInstance().getContext());
        localObject1 = localObject3;
        paramVarArgs = (String[])localObject3;
        localObject5 = ((AndroidHttpClient)localObject3).execute((HttpUriRequest)localObject4);
        localObject1 = localObject3;
        paramVarArgs = (String[])localObject3;
        localObject4 = ((HttpResponse)localObject5).getEntity();
        localObject1 = localObject3;
        paramVarArgs = (String[])localObject3;
        localObject5 = ((HttpResponse)localObject5).getStatusLine();
        if (localObject5 == null) {
          continue;
        }
        localObject1 = localObject3;
        paramVarArgs = (String[])localObject3;
        i = ((StatusLine)localObject5).getStatusCode();
        if (i == 200) {
          continue;
        }
        if (localObject3 != null) {
          ((AndroidHttpClient)localObject3).close();
        }
        return null;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        localObject1 = localObject6;
        paramVarArgs = (String[])localObject7;
        throw new RuntimeException(localUnsupportedEncodingException);
      }
      localObject1 = localObject6;
      paramVarArgs = (String[])localObject7;
      localObject4 = Session.getInstance().getConfig().getSite().split("\\.")[0];
      localObject3 = "t/k";
      continue;
      localObject1 = localObject6;
      paramVarArgs = (String[])localObject7;
      localObject5 = Babayaga.CHANNEL;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/uservoice/uservoicesdk/babayaga/BabayagaTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */