package com.uservoice.uservoicesdk.rest;

import android.net.Uri;
import android.net.Uri.Builder;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import com.uservoice.uservoicesdk.Config;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.UserVoice;
import com.uservoice.uservoicesdk.model.AccessToken;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import oauth.signpost.OAuthConsumer;
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
  extends AsyncTask<String, String, RestResult>
{
  private RestTaskCallback callback;
  private RestMethod method;
  private List<BasicNameValuePair> params;
  private HttpUriRequest request;
  private String urlPath;
  
  public RestTask(RestMethod paramRestMethod, String paramString, List<BasicNameValuePair> paramList, RestTaskCallback paramRestTaskCallback)
  {
    this.method = paramRestMethod;
    this.urlPath = paramString;
    this.callback = paramRestTaskCallback;
    this.params = paramList;
  }
  
  public RestTask(RestMethod paramRestMethod, String paramString, Map<String, String> paramMap, RestTaskCallback paramRestTaskCallback) {}
  
  private HttpUriRequest createRequest()
    throws URISyntaxException, UnsupportedEncodingException
  {
    String str2 = Session.getInstance().getConfig().getSite();
    Uri.Builder localBuilder = new Uri.Builder();
    if (str2.contains(".us.com")) {}
    for (String str1 = "http";; str1 = "https")
    {
      localBuilder.scheme(str1);
      localBuilder.encodedAuthority(str2);
      localBuilder.path(this.urlPath);
      if (this.method != RestMethod.GET) {
        break;
      }
      return requestWithQueryString(new HttpGet(), localBuilder);
    }
    if (this.method == RestMethod.DELETE) {
      return requestWithQueryString(new HttpDelete(), localBuilder);
    }
    if (this.method == RestMethod.POST) {
      return requestWithEntity(new HttpPost(), localBuilder);
    }
    if (this.method == RestMethod.PUT) {
      return requestWithEntity(new HttpPut(), localBuilder);
    }
    throw new IllegalArgumentException("Method must be one of [GET, POST, PUT, DELETE], but was " + this.method);
  }
  
  public static List<BasicNameValuePair> paramsToList(Map<String, String> paramMap)
  {
    ArrayList localArrayList = new ArrayList(paramMap.size());
    Iterator localIterator = paramMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      String str2 = (String)paramMap.get(str1);
      if (str2 != null) {
        localArrayList.add(new BasicNameValuePair(str1, str2));
      }
    }
    return localArrayList;
  }
  
  private HttpUriRequest requestWithEntity(HttpEntityEnclosingRequestBase paramHttpEntityEnclosingRequestBase, Uri.Builder paramBuilder)
    throws UnsupportedEncodingException, URISyntaxException
  {
    if (this.params != null) {
      paramHttpEntityEnclosingRequestBase.setEntity(new UrlEncodedFormEntity(this.params, "UTF-8"));
    }
    paramHttpEntityEnclosingRequestBase.setURI(new URI(paramBuilder.build().toString()));
    return paramHttpEntityEnclosingRequestBase;
  }
  
  private HttpUriRequest requestWithQueryString(HttpRequestBase paramHttpRequestBase, Uri.Builder paramBuilder)
    throws URISyntaxException
  {
    if (this.params != null)
    {
      Iterator localIterator = this.params.iterator();
      while (localIterator.hasNext())
      {
        BasicNameValuePair localBasicNameValuePair = (BasicNameValuePair)localIterator.next();
        paramBuilder.appendQueryParameter(localBasicNameValuePair.getName(), localBasicNameValuePair.getValue());
      }
    }
    paramHttpRequestBase.setURI(new URI(paramBuilder.build().toString()));
    return paramHttpRequestBase;
  }
  
  protected RestResult doInBackground(String... paramVarArgs)
  {
    Object localObject4 = null;
    Object localObject2 = null;
    paramVarArgs = (String[])localObject2;
    Object localObject1 = localObject4;
    Object localObject3;
    try
    {
      this.request = createRequest();
      paramVarArgs = (String[])localObject2;
      localObject1 = localObject4;
      if (isCancelled())
      {
        paramVarArgs = (String[])localObject2;
        localObject1 = localObject4;
        throw new InterruptedException();
      }
    }
    catch (Exception localException)
    {
      localObject1 = paramVarArgs;
      localObject3 = new RestResult(localException);
      localObject1 = localObject3;
      if (paramVarArgs != null)
      {
        paramVarArgs.close();
        localObject1 = localObject3;
      }
      return (RestResult)localObject1;
      paramVarArgs = (String[])localObject3;
      localObject1 = localObject4;
      localObject5 = Session.getInstance().getOAuthConsumer();
      if (localObject5 != null)
      {
        paramVarArgs = (String[])localObject3;
        localObject1 = localObject4;
        AccessToken localAccessToken = Session.getInstance().getAccessToken();
        if (localAccessToken != null)
        {
          paramVarArgs = (String[])localObject3;
          localObject1 = localObject4;
          ((OAuthConsumer)localObject5).setTokenWithSecret(localAccessToken.getKey(), localAccessToken.getSecret());
        }
        paramVarArgs = (String[])localObject3;
        localObject1 = localObject4;
        ((OAuthConsumer)localObject5).sign(this.request);
      }
      paramVarArgs = (String[])localObject3;
      localObject1 = localObject4;
      Log.d("UV", this.urlPath);
      paramVarArgs = (String[])localObject3;
      localObject1 = localObject4;
      this.request.setHeader("Accept-Language", Locale.getDefault().getLanguage());
      paramVarArgs = (String[])localObject3;
      localObject1 = localObject4;
      this.request.setHeader("API-Client", String.format("uservoice-android-%s", new Object[] { UserVoice.getVersion() }));
      paramVarArgs = (String[])localObject3;
      localObject1 = localObject4;
      localObject3 = AndroidHttpClient.newInstance(String.format("uservoice-android-%s", new Object[] { UserVoice.getVersion() }), Session.getInstance().getContext());
      paramVarArgs = (String[])localObject3;
      localObject1 = localObject3;
      if (isCancelled())
      {
        paramVarArgs = (String[])localObject3;
        localObject1 = localObject3;
        throw new InterruptedException();
      }
    }
    finally
    {
      if (localObject1 != null) {
        ((AndroidHttpClient)localObject1).close();
      }
    }
    paramVarArgs = (String[])localObject3;
    localObject1 = localObject3;
    Object localObject5 = ((AndroidHttpClient)localObject3).execute(this.request);
    paramVarArgs = (String[])localObject3;
    localObject1 = localObject3;
    if (isCancelled())
    {
      paramVarArgs = (String[])localObject3;
      localObject1 = localObject3;
      throw new InterruptedException();
    }
    paramVarArgs = (String[])localObject3;
    localObject1 = localObject3;
    localObject4 = ((HttpResponse)localObject5).getEntity();
    paramVarArgs = (String[])localObject3;
    localObject1 = localObject3;
    localObject5 = ((HttpResponse)localObject5).getStatusLine();
    int i;
    if (localObject5 != null)
    {
      paramVarArgs = (String[])localObject3;
      localObject1 = localObject3;
      i = ((StatusLine)localObject5).getStatusCode();
      label396:
      if (localObject4 == null) {
        break label487;
      }
      paramVarArgs = (String[])localObject3;
      localObject1 = localObject3;
    }
    label487:
    for (localObject4 = EntityUtils.toString((HttpEntity)localObject4);; localObject4 = null)
    {
      paramVarArgs = (String[])localObject3;
      localObject1 = localObject3;
      if (isCancelled())
      {
        paramVarArgs = (String[])localObject3;
        localObject1 = localObject3;
        throw new InterruptedException();
      }
      paramVarArgs = (String[])localObject3;
      localObject1 = localObject3;
      localObject4 = new RestResult(i, new JSONObject((String)localObject4));
      localObject1 = localObject4;
      if (localObject3 == null) {
        break;
      }
      ((AndroidHttpClient)localObject3).close();
      return (RestResult)localObject4;
      i = 0;
      break label396;
    }
  }
  
  protected void onPostExecute(RestResult paramRestResult)
  {
    if (paramRestResult.isError()) {
      this.callback.onError(paramRestResult);
    }
    for (;;)
    {
      super.onPostExecute(paramRestResult);
      return;
      try
      {
        this.callback.onComplete(paramRestResult.getObject());
      }
      catch (JSONException localJSONException)
      {
        this.callback.onError(new RestResult(localJSONException, paramRestResult.getStatusCode(), paramRestResult.getObject()));
      }
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/uservoice/uservoicesdk/rest/RestTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */