package com.uservoice.uservoicesdk.deflection;

import android.util.Log;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.model.Article;
import com.uservoice.uservoicesdk.model.BaseModel;
import com.uservoice.uservoicesdk.model.ClientConfig;
import com.uservoice.uservoicesdk.model.Suggestion;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestMethod;
import com.uservoice.uservoicesdk.rest.RestResult;
import com.uservoice.uservoicesdk.rest.RestTask;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class Deflection
{
  private static int interactionIdentifier = Integer.parseInt(String.valueOf(new Date().getTime()).substring(4));
  private static String searchText;
  
  private static Map<String, String> deflectionParams()
  {
    HashMap localHashMap = new HashMap();
    if (Babayaga.getUvts() != null) {
      localHashMap.put("uvts", Babayaga.getUvts());
    }
    localHashMap.put("channel", "android");
    localHashMap.put("search_term", searchText);
    localHashMap.put("interaction_identifier", String.valueOf(interactionIdentifier));
    localHashMap.put("subdomain_id", String.valueOf(Session.getInstance().getClientConfig().getSubdomainId()));
    return localHashMap;
  }
  
  private static RestTaskCallback getCallback()
  {
    new RestTaskCallback(null)
    {
      public void onComplete(JSONObject paramAnonymousJSONObject)
        throws JSONException
      {
        Log.d("UV", paramAnonymousJSONObject.toString());
      }
      
      public void onError(RestResult paramAnonymousRestResult)
      {
        Log.e("UV", "Failed sending deflection: " + paramAnonymousRestResult.getMessage());
      }
    };
  }
  
  public static void setSearchText(String paramString)
  {
    if (paramString.equals(searchText)) {
      return;
    }
    searchText = paramString;
    interactionIdentifier += 1;
  }
  
  public static void trackDeflection(String paramString1, String paramString2, BaseModel paramBaseModel)
  {
    Map localMap = deflectionParams();
    localMap.put("kind", paramString1);
    localMap.put("deflecting_type", paramString2);
    localMap.put("deflector_id", String.valueOf(paramBaseModel.getId()));
    if ((paramBaseModel instanceof Article)) {}
    for (paramString1 = "Faq";; paramString1 = "Suggestion")
    {
      localMap.put("deflector_type", paramString1);
      new RestTask(RestMethod.GET, "/clients/omnibox/deflections/upsert.json", localMap, getCallback()).execute(new String[0]);
      return;
    }
  }
  
  public static void trackSearchDeflection(List<BaseModel> paramList, String paramString)
  {
    Map localMap = deflectionParams();
    localMap.put("kind", "list");
    localMap.put("deflecting_type", paramString);
    int k = 0;
    int j = 0;
    int i = 0;
    paramList = paramList.iterator();
    if (paramList.hasNext())
    {
      Object localObject = (BaseModel)paramList.next();
      paramString = "results[" + String.valueOf(i) + "]";
      localMap.put(paramString + "[position]", String.valueOf(i));
      localMap.put(paramString + "[deflector_id]", String.valueOf(((BaseModel)localObject).getId()));
      int n;
      int m;
      if ((localObject instanceof Suggestion))
      {
        n = j + 1;
        localObject = (Suggestion)localObject;
        localMap.put(paramString + "[weight]", String.valueOf(((Suggestion)localObject).getWeight()));
        localMap.put(paramString + "[deflector_type]", "Suggestion");
        m = k;
      }
      for (;;)
      {
        i += 1;
        k = m;
        j = n;
        break;
        m = k;
        n = j;
        if ((localObject instanceof Article))
        {
          m = k + 1;
          localObject = (Article)localObject;
          localMap.put(paramString + "[weight]", String.valueOf(((Article)localObject).getWeight()));
          localMap.put(paramString + "[deflector_type]", "Faq");
          n = j;
        }
      }
    }
    localMap.put("faq_results", String.valueOf(k));
    localMap.put("suggestion_results", String.valueOf(j));
    new RestTask(RestMethod.GET, "/clients/omnibox/deflections/list_view.json", localMap, getCallback()).execute(new String[0]);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/uservoice/uservoicesdk/deflection/Deflection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */