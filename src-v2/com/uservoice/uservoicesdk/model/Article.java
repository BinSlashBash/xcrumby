/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.uservoice.uservoicesdk.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.uservoice.uservoicesdk.model.BaseModel;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestTask;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class Article
extends BaseModel
implements Parcelable {
    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>(){

        public Article createFromParcel(Parcel parcel) {
            return new Article(parcel);
        }

        public Article[] newArray(int n2) {
            return new Article[n2];
        }
    };
    private String html;
    private String title;
    private String topicName;
    private int weight;

    public Article() {
    }

    private Article(Parcel parcel) {
        this.id = parcel.readInt();
        this.title = parcel.readString();
        this.html = parcel.readString();
        this.topicName = parcel.readString();
        this.weight = parcel.readInt();
    }

    public static RestTask loadInstantAnswers(String string2, final Callback<List<BaseModel>> callback) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("per_page", "3");
        hashMap.put("forum_id", String.valueOf(Article.getConfig().getForumId()));
        hashMap.put("query", string2);
        if (Article.getConfig().getTopicId() != -1) {
            hashMap.put("topic_id", String.valueOf(Article.getConfig().getTopicId()));
        }
        return Article.doGet(Article.apiPath("/instant_answers/search.json", new Object[0]), hashMap, new RestTaskCallback(callback){

            @Override
            public void onComplete(JSONObject jSONObject) throws JSONException {
                callback.onModel(BaseModel.deserializeHeterogenousList(jSONObject, "instant_answers"));
            }
        });
    }

    public static void loadPage(int n2, final Callback<List<Article>> callback) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("sort", "ordered");
        hashMap.put("per_page", "50");
        hashMap.put("page", String.valueOf(n2));
        Article.doGet(Article.apiPath("/articles.json", new Object[0]), hashMap, new RestTaskCallback(callback){

            @Override
            public void onComplete(JSONObject jSONObject) throws JSONException {
                callback.onModel(BaseModel.deserializeList(jSONObject, "articles", Article.class));
            }
        });
    }

    public static void loadPageForTopic(int n2, int n3, final Callback<List<Article>> callback) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("sort", "ordered");
        hashMap.put("per_page", "50");
        hashMap.put("page", String.valueOf(n3));
        Article.doGet(Article.apiPath("/topics/%d/articles.json", n2), hashMap, new RestTaskCallback(callback){

            @Override
            public void onComplete(JSONObject jSONObject) throws JSONException {
                callback.onModel(BaseModel.deserializeList(jSONObject, "articles", Article.class));
            }
        });
    }

    public int describeContents() {
        return 0;
    }

    public String getHtml() {
        return this.html;
    }

    public String getTitle() {
        return this.title;
    }

    public String getTopicName() {
        return this.topicName;
    }

    public int getWeight() {
        return this.weight;
    }

    @Override
    public void load(JSONObject jSONObject) throws JSONException {
        super.load(jSONObject);
        this.title = this.getString(jSONObject, "question");
        this.html = this.getHtml(jSONObject, "answer_html");
        if (jSONObject.has("normalized_weight")) {
            this.weight = jSONObject.getInt("normalized_weight");
        }
        if (!jSONObject.isNull("topic")) {
            this.topicName = jSONObject.getJSONObject("topic").getString("name");
        }
    }

    public void writeToParcel(Parcel parcel, int n2) {
        parcel.writeInt(this.id);
        parcel.writeString(this.title);
        parcel.writeString(this.html);
        parcel.writeString(this.topicName);
        parcel.writeInt(this.weight);
    }

}

