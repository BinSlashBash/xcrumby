package com.uservoice.uservoicesdk.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.uservoice.uservoicesdk.R.string;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class Topic
  extends BaseModel
  implements Parcelable
{
  public static Topic ALL_ARTICLES = new Topic() {};
  public static final Parcelable.Creator<Topic> CREATOR = new Parcelable.Creator()
  {
    public Topic createFromParcel(Parcel paramAnonymousParcel)
    {
      return new Topic(paramAnonymousParcel, null);
    }
    
    public Topic[] newArray(int paramAnonymousInt)
    {
      return new Topic[paramAnonymousInt];
    }
  };
  protected String name;
  private int numberOfArticles;
  
  public Topic() {}
  
  private Topic(Parcel paramParcel)
  {
    this.id = paramParcel.readInt();
    this.name = paramParcel.readString();
    this.numberOfArticles = paramParcel.readInt();
  }
  
  public static void loadTopic(int paramInt, final Callback<Topic> paramCallback)
  {
    doGet(apiPath("/topics/%d.json", new Object[] { Integer.valueOf(paramInt) }), new RestTaskCallback(paramCallback)
    {
      public void onComplete(JSONObject paramAnonymousJSONObject)
        throws JSONException
      {
        paramCallback.onModel(BaseModel.deserializeObject(paramAnonymousJSONObject, "topic", Topic.class));
      }
    });
  }
  
  public static void loadTopics(final Callback<List<Topic>> paramCallback)
  {
    doGet(apiPath("/topics.json", new Object[0]), new RestTaskCallback(paramCallback)
    {
      public void onComplete(JSONObject paramAnonymousJSONObject)
        throws JSONException
      {
        Object localObject = BaseModel.deserializeList(paramAnonymousJSONObject, "topics", Topic.class);
        paramAnonymousJSONObject = new ArrayList(((List)localObject).size());
        localObject = ((List)localObject).iterator();
        while (((Iterator)localObject).hasNext())
        {
          Topic localTopic = (Topic)((Iterator)localObject).next();
          if (localTopic.getNumberOfArticles() > 0) {
            paramAnonymousJSONObject.add(localTopic);
          }
        }
        paramCallback.onModel(paramAnonymousJSONObject);
      }
    });
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public int getNumberOfArticles()
  {
    return this.numberOfArticles;
  }
  
  public void load(JSONObject paramJSONObject)
    throws JSONException
  {
    super.load(paramJSONObject);
    this.name = getString(paramJSONObject, "name");
    this.numberOfArticles = paramJSONObject.getInt("article_count");
  }
  
  public String toString()
  {
    return this.name;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.id);
    paramParcel.writeString(this.name);
    paramParcel.writeInt(this.numberOfArticles);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/uservoice/uservoicesdk/model/Topic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */