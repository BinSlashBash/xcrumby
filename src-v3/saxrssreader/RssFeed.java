package saxrssreader;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.plus.PlusShare;
import java.util.ArrayList;

public class RssFeed implements Parcelable {
    public static final Creator<RssFeed> CREATOR;
    private String description;
    private String language;
    private String link;
    private ArrayList<RssItem> rssItems;
    private String title;

    /* renamed from: saxrssreader.RssFeed.1 */
    static class C07091 implements Creator<RssFeed> {
        C07091() {
        }

        public RssFeed createFromParcel(Parcel data) {
            return new RssFeed(data);
        }

        public RssFeed[] newArray(int size) {
            return new RssFeed[size];
        }
    }

    public RssFeed() {
        this.rssItems = new ArrayList();
    }

    public RssFeed(Parcel source) {
        Bundle data = source.readBundle();
        this.title = data.getString(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE);
        this.link = data.getString("link");
        this.description = data.getString(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION);
        this.language = data.getString("language");
        this.rssItems = data.getParcelableArrayList("rssItems");
    }

    public void writeToParcel(Parcel dest, int flags) {
        Bundle data = new Bundle();
        data.putString(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE, this.title);
        data.putString("link", this.link);
        data.putString(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, this.description);
        data.putString("language", this.language);
        data.putParcelableArrayList("rssItems", this.rssItems);
        dest.writeBundle(data);
    }

    static {
        CREATOR = new C07091();
    }

    public int describeContents() {
        return 0;
    }

    void addRssItem(RssItem rssItem) {
        this.rssItems.add(rssItem);
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ArrayList<RssItem> getRssItems() {
        return this.rssItems;
    }

    public void setRssItems(ArrayList<RssItem> rssItems) {
        this.rssItems = rssItems;
    }
}
