/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.uservoice.uservoicesdk.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import com.uservoice.uservoicesdk.R;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.model.BaseModel;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class Topic
extends BaseModel
implements Parcelable {
    public static Topic ALL_ARTICLES = new Topic(){};
    public static final Parcelable.Creator<Topic> CREATOR = new Parcelable.Creator<Topic>(){

        public Topic createFromParcel(Parcel parcel) {
            return new Topic(parcel);
        }

        public Topic[] newArray(int n2) {
            return new Topic[n2];
        }
    };
    protected String name;
    private int numberOfArticles;

    public Topic() {
    }

    private Topic(Parcel parcel) {
        this.id = parcel.readInt();
        this.name = parcel.readString();
        this.numberOfArticles = parcel.readInt();
    }

    public static void loadTopic(int n2, final Callback<Topic> callback) {
        Topic.doGet(Topic.apiPath("/topics/%d.json", n2), new RestTaskCallback(callback){

            @Override
            public void onComplete(JSONObject jSONObject) throws JSONException {
                callback.onModel(BaseModel.deserializeObject(jSONObject, "topic", Topic.class));
            }
        });
    }

    public static void loadTopics(final Callback<List<Topic>> callback) {
        Topic.doGet(Topic.apiPath("/topics.json", new Object[0]), new RestTaskCallback(callback){

            @Override
            public void onComplete(JSONObject object) throws JSONException {
                Object object2 = BaseModel.deserializeList((JSONObject)object, "topics", Topic.class);
                object = new ArrayList(object2.size());
                object2 = object2.iterator();
                while (object2.hasNext()) {
                    Topic topic = (Topic)object2.next();
                    if (topic.getNumberOfArticles() <= 0) continue;
                    object.add(topic);
                }
                callback.onModel(object);
            }
        });
    }

    public int describeContents() {
        return 0;
    }

    public String getName() {
        return this.name;
    }

    public int getNumberOfArticles() {
        return this.numberOfArticles;
    }

    @Override
    public void load(JSONObject jSONObject) throws JSONException {
        super.load(jSONObject);
        this.name = this.getString(jSONObject, "name");
        this.numberOfArticles = jSONObject.getInt("article_count");
    }

    public String toString() {
        return this.name;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
        parcel.writeInt(this.numberOfArticles);
    }

}

