package saxrssreader;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RssItem
  implements Comparable<RssItem>, Parcelable
{
  public static final Parcelable.Creator<RssItem> CREATOR = new Parcelable.Creator()
  {
    public RssItem createFromParcel(Parcel paramAnonymousParcel)
    {
      return new RssItem(paramAnonymousParcel);
    }
    
    public RssItem[] newArray(int paramAnonymousInt)
    {
      return new RssItem[paramAnonymousInt];
    }
  };
  private String content;
  private String description;
  private RssFeed feed;
  private String link;
  private Date pubDate;
  private String title;
  
  public RssItem() {}
  
  public RssItem(Parcel paramParcel)
  {
    paramParcel = paramParcel.readBundle();
    this.title = paramParcel.getString("title");
    this.link = paramParcel.getString("link");
    this.pubDate = ((Date)paramParcel.getSerializable("pubDate"));
    this.description = paramParcel.getString("description");
    this.content = paramParcel.getString("content");
    this.feed = ((RssFeed)paramParcel.getParcelable("feed"));
  }
  
  public int compareTo(RssItem paramRssItem)
  {
    if ((getPubDate() != null) && (paramRssItem.getPubDate() != null)) {
      return getPubDate().compareTo(paramRssItem.getPubDate());
    }
    return 0;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String getContent()
  {
    return this.content;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public RssFeed getFeed()
  {
    return this.feed;
  }
  
  public String getLink()
  {
    return this.link;
  }
  
  public Date getPubDate()
  {
    return this.pubDate;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setContent(String paramString)
  {
    this.content = paramString;
  }
  
  public void setDescription(String paramString)
  {
    this.description = paramString;
  }
  
  public void setFeed(RssFeed paramRssFeed)
  {
    this.feed = paramRssFeed;
  }
  
  public void setLink(String paramString)
  {
    this.link = paramString;
  }
  
  public void setPubDate(String paramString)
  {
    try
    {
      this.pubDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH).parse(paramString);
      return;
    }
    catch (ParseException paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void setPubDate(Date paramDate)
  {
    this.pubDate = paramDate;
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
    localBundle.putSerializable("pubDate", this.pubDate);
    localBundle.putString("description", this.description);
    localBundle.putString("content", this.content);
    localBundle.putParcelable("feed", this.feed);
    paramParcel.writeBundle(localBundle);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/saxrssreader/RssItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */