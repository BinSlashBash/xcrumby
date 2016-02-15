package com.uservoice.uservoicesdk.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.uservoice.uservoicesdk.Config;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestTask;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class Article
  extends BaseModel
  implements Parcelable
{
  public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator()
  {
    public Article createFromParcel(Parcel paramAnonymousParcel)
    {
      return new Article(paramAnonymousParcel, null);
    }
    
    public Article[] newArray(int paramAnonymousInt)
    {
      return new Article[paramAnonymousInt];
    }
  };
  private String html;
  private String title;
  private String topicName;
  private int weight;
  
  public Article() {}
  
  private Article(Parcel paramParcel)
  {
    this.id = paramParcel.readInt();
    this.title = paramParcel.readString();
    this.html = paramParcel.readString();
    this.topicName = paramParcel.readString();
    this.weight = paramParcel.readInt();
  }
  
  public static RestTask loadInstantAnswers(String paramString, final Callback<List<BaseModel>> paramCallback)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("per_page", "3");
    localHashMap.put("forum_id", String.valueOf(getConfig().getForumId()));
    localHashMap.put("query", paramString);
    if (getConfig().getTopicId() != -1) {
      localHashMap.put("topic_id", String.valueOf(getConfig().getTopicId()));
    }
    doGet(apiPath("/instant_answers/search.json", new Object[0]), localHashMap, new RestTaskCallback(paramCallback)
    {
      public void onComplete(JSONObject paramAnonymousJSONObject)
        throws JSONException
      {
        paramCallback.onModel(BaseModel.deserializeHeterogenousList(paramAnonymousJSONObject, "instant_answers"));
      }
    });
  }
  
  public static void loadPage(int paramInt, final Callback<List<Article>> paramCallback)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("sort", "ordered");
    localHashMap.put("per_page", "50");
    localHashMap.put("page", String.valueOf(paramInt));
    doGet(apiPath("/articles.json", new Object[0]), localHashMap, new RestTaskCallback(paramCallback)
    {
      public void onComplete(JSONObject paramAnonymousJSONObject)
        throws JSONException
      {
        paramCallback.onModel(BaseModel.deserializeList(paramAnonymousJSONObject, "articles", Article.class));
      }
    });
  }
  
  public static void loadPageForTopic(int paramInt1, int paramInt2, final Callback<List<Article>> paramCallback)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("sort", "ordered");
    localHashMap.put("per_page", "50");
    localHashMap.put("page", String.valueOf(paramInt2));
    doGet(apiPath("/topics/%d/articles.json", new Object[] { Integer.valueOf(paramInt1) }), localHashMap, new RestTaskCallback(paramCallback)
    {
      public void onComplete(JSONObject paramAnonymousJSONObject)
        throws JSONException
      {
        paramCallback.onModel(BaseModel.deserializeList(paramAnonymousJSONObject, "articles", Article.class));
      }
    });
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String getHtml()
  {
    return this.html;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public String getTopicName()
  {
    return this.topicName;
  }
  
  public int getWeight()
  {
    return this.weight;
  }
  
  public void load(JSONObject paramJSONObject)
    throws JSONException
  {
    super.load(paramJSONObject);
    this.title = getString(paramJSONObject, "question");
    this.html = getHtml(paramJSONObject, "answer_html");
    if (paramJSONObject.has("normalized_weight")) {
      this.weight = paramJSONObject.getInt("normalized_weight");
    }
    if (!paramJSONObject.isNull("topic")) {
      this.topicName = paramJSONObject.getJSONObject("topic").getString("name");
    }
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.id);
    paramParcel.writeString(this.title);
    paramParcel.writeString(this.html);
    paramParcel.writeString(this.topicName);
    paramParcel.writeInt(this.weight);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/uservoice/uservoicesdk/model/Article.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */