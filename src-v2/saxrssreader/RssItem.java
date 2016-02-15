/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package saxrssreader;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import saxrssreader.RssFeed;

public class RssItem
implements Comparable<RssItem>,
Parcelable {
    public static final Parcelable.Creator<RssItem> CREATOR = new Parcelable.Creator<RssItem>(){

        public RssItem createFromParcel(Parcel parcel) {
            return new RssItem(parcel);
        }

        public RssItem[] newArray(int n2) {
            return new RssItem[n2];
        }
    };
    private String content;
    private String description;
    private RssFeed feed;
    private String link;
    private Date pubDate;
    private String title;

    public RssItem() {
    }

    public RssItem(Parcel parcel) {
        parcel = parcel.readBundle();
        this.title = parcel.getString("title");
        this.link = parcel.getString("link");
        this.pubDate = (Date)parcel.getSerializable("pubDate");
        this.description = parcel.getString("description");
        this.content = parcel.getString("content");
        this.feed = (RssFeed)parcel.getParcelable("feed");
    }

    @Override
    public int compareTo(RssItem rssItem) {
        if (this.getPubDate() != null && rssItem.getPubDate() != null) {
            return this.getPubDate().compareTo(rssItem.getPubDate());
        }
        return 0;
    }

    public int describeContents() {
        return 0;
    }

    public String getContent() {
        return this.content;
    }

    public String getDescription() {
        return this.description;
    }

    public RssFeed getFeed() {
        return this.feed;
    }

    public String getLink() {
        return this.link;
    }

    public Date getPubDate() {
        return this.pubDate;
    }

    public String getTitle() {
        return this.title;
    }

    public void setContent(String string2) {
        this.content = string2;
    }

    public void setDescription(String string2) {
        this.description = string2;
    }

    public void setFeed(RssFeed rssFeed) {
        this.feed = rssFeed;
    }

    public void setLink(String string2) {
        this.link = string2;
    }

    public void setPubDate(String string2) {
        try {
            this.pubDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH).parse(string2);
            return;
        }
        catch (ParseException var1_2) {
            var1_2.printStackTrace();
            return;
        }
    }

    public void setPubDate(Date date) {
        this.pubDate = date;
    }

    public void setTitle(String string2) {
        this.title = string2;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        Bundle bundle = new Bundle();
        bundle.putString("title", this.title);
        bundle.putString("link", this.link);
        bundle.putSerializable("pubDate", (Serializable)this.pubDate);
        bundle.putString("description", this.description);
        bundle.putString("content", this.content);
        bundle.putParcelable("feed", (Parcelable)this.feed);
        parcel.writeBundle(bundle);
    }

}

