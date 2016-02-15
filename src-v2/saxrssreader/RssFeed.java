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
import java.util.ArrayList;
import saxrssreader.RssItem;

public class RssFeed
implements Parcelable {
    public static final Parcelable.Creator<RssFeed> CREATOR = new Parcelable.Creator<RssFeed>(){

        public RssFeed createFromParcel(Parcel parcel) {
            return new RssFeed(parcel);
        }

        public RssFeed[] newArray(int n2) {
            return new RssFeed[n2];
        }
    };
    private String description;
    private String language;
    private String link;
    private ArrayList<RssItem> rssItems;
    private String title;

    public RssFeed() {
        this.rssItems = new ArrayList();
    }

    public RssFeed(Parcel parcel) {
        parcel = parcel.readBundle();
        this.title = parcel.getString("title");
        this.link = parcel.getString("link");
        this.description = parcel.getString("description");
        this.language = parcel.getString("language");
        this.rssItems = parcel.getParcelableArrayList("rssItems");
    }

    void addRssItem(RssItem rssItem) {
        this.rssItems.add(rssItem);
    }

    public int describeContents() {
        return 0;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getLink() {
        return this.link;
    }

    public ArrayList<RssItem> getRssItems() {
        return this.rssItems;
    }

    public String getTitle() {
        return this.title;
    }

    public void setDescription(String string2) {
        this.description = string2;
    }

    public void setLanguage(String string2) {
        this.language = string2;
    }

    public void setLink(String string2) {
        this.link = string2;
    }

    public void setRssItems(ArrayList<RssItem> arrayList) {
        this.rssItems = arrayList;
    }

    public void setTitle(String string2) {
        this.title = string2;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        Bundle bundle = new Bundle();
        bundle.putString("title", this.title);
        bundle.putString("link", this.link);
        bundle.putString("description", this.description);
        bundle.putString("language", this.language);
        bundle.putParcelableArrayList("rssItems", this.rssItems);
        parcel.writeBundle(bundle);
    }

}

