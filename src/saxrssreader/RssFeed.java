package saxrssreader;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;

public class RssFeed
  implements Parcelable
{
  public static final Parcelable.Creator<RssFeed> CREATOR = new Parcelable.Creator()
  {
    public RssFeed createFromParcel(Parcel paramAnonymousParcel)
    {
      return new RssFeed(paramAnonymousParcel);
    }
    
    public RssFeed[] newArray(int paramAnonymousInt)
    {
      return new RssFeed[paramAnonymousInt];
    }
  };
  private String description;
  private String language;
  private String link;
  private ArrayList<RssItem> rssItems;
  private String title;
  
  public RssFeed()
  {
    this.rssItems = new ArrayList();
  }
  
  public RssFeed(Parcel paramParcel)
  {
    paramParcel = paramParcel.readBundle();
    this.title = paramParcel.getString("title");
    this.link = paramParcel.getString("link");
    this.description = paramParcel.getString("description");
    this.language = paramParcel.getString("language");
    this.rssItems = paramParcel.getParcelableArrayList("rssItems");
  }
  
  void addRssItem(RssItem paramRssItem)
  {
    this.rssItems.add(paramRssItem);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public String getLanguage()
  {
    return this.language;
  }
  
  public String getLink()
  {
    return this.link;
  }
  
  public ArrayList<RssItem> getRssItems()
  {
    return this.rssItems;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setDescription(String paramString)
  {
    this.description = paramString;
  }
  
  public void setLanguage(String paramString)
  {
    this.language = paramString;
  }
  
  public void setLink(String paramString)
  {
    this.link = paramString;
  }
  
  public void setRssItems(ArrayList<RssItem> paramArrayList)
  {
    this.rssItems = paramArrayList;
  }
  
  public void setTitle(String paramString)
  {
    this.title = paramString;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("title", this.title);
    localBundle.putString("link", this.link);
    localBundle.putString("description", this.description);
    localBundle.putString("language", this.language);
    localBundle.putParcelableArrayList("rssItems", this.rssItems);
    paramParcel.writeBundle(localBundle);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/saxrssreader/RssFeed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */