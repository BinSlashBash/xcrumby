package saxrssreader;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.plus.PlusShare;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RssItem implements Comparable<RssItem>, Parcelable {
    public static final Creator<RssItem> CREATOR;
    private String content;
    private String description;
    private RssFeed feed;
    private String link;
    private Date pubDate;
    private String title;

    /* renamed from: saxrssreader.RssItem.1 */
    static class C07101 implements Creator<RssItem> {
        C07101() {
        }

        public RssItem createFromParcel(Parcel data) {
            return new RssItem(data);
        }

        public RssItem[] newArray(int size) {
            return new RssItem[size];
        }
    }

    public RssItem(Parcel source) {
        Bundle data = source.readBundle();
        this.title = data.getString(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE);
        this.link = data.getString("link");
        this.pubDate = (Date) data.getSerializable("pubDate");
        this.description = data.getString(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION);
        this.content = data.getString("content");
        this.feed = (RssFeed) data.getParcelable("feed");
    }

    public void writeToParcel(Parcel dest, int flags) {
        Bundle data = new Bundle();
        data.putString(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE, this.title);
        data.putString("link", this.link);
        data.putSerializable("pubDate", this.pubDate);
        data.putString(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, this.description);
        data.putString("content", this.content);
        data.putParcelable("feed", this.feed);
        dest.writeBundle(data);
    }

    static {
        CREATOR = new C07101();
    }

    public int describeContents() {
        return 0;
    }

    public RssFeed getFeed() {
        return this.feed;
    }

    public void setFeed(RssFeed feed) {
        this.feed = feed;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getPubDate() {
        return this.pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public void setPubDate(String pubDate) {
        try {
            this.pubDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH).parse(pubDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int compareTo(RssItem another) {
        if (getPubDate() == null || another.getPubDate() == null) {
            return 0;
        }
        return getPubDate().compareTo(another.getPubDate());
    }
}
