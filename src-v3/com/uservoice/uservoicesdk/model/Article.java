package com.uservoice.uservoicesdk.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestTask;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class Article extends BaseModel implements Parcelable {
    public static final Creator<Article> CREATOR;
    private String html;
    private String title;
    private String topicName;
    private int weight;

    /* renamed from: com.uservoice.uservoicesdk.model.Article.4 */
    static class C06504 implements Creator<Article> {
        C06504() {
        }

        public Article createFromParcel(Parcel in) {
            return new Article(null);
        }

        public Article[] newArray(int size) {
            return new Article[size];
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.model.Article.1 */
    static class C11791 extends RestTaskCallback {
        final /* synthetic */ Callback val$callback;

        C11791(Callback x0, Callback callback) {
            this.val$callback = callback;
            super(x0);
        }

        public void onComplete(JSONObject result) throws JSONException {
            this.val$callback.onModel(BaseModel.deserializeList(result, "articles", Article.class));
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.model.Article.2 */
    static class C11802 extends RestTaskCallback {
        final /* synthetic */ Callback val$callback;

        C11802(Callback x0, Callback callback) {
            this.val$callback = callback;
            super(x0);
        }

        public void onComplete(JSONObject result) throws JSONException {
            this.val$callback.onModel(BaseModel.deserializeList(result, "articles", Article.class));
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.model.Article.3 */
    static class C11813 extends RestTaskCallback {
        final /* synthetic */ Callback val$callback;

        C11813(Callback x0, Callback callback) {
            this.val$callback = callback;
            super(x0);
        }

        public void onComplete(JSONObject result) throws JSONException {
            this.val$callback.onModel(BaseModel.deserializeHeterogenousList(result, "instant_answers"));
        }
    }

    public static void loadPage(int page, Callback<List<Article>> callback) {
        Map<String, String> params = new HashMap();
        params.put("sort", "ordered");
        params.put("per_page", "50");
        params.put("page", String.valueOf(page));
        BaseModel.doGet(BaseModel.apiPath("/articles.json", new Object[0]), params, new C11791(callback, callback));
    }

    public static void loadPageForTopic(int topicId, int page, Callback<List<Article>> callback) {
        Map<String, String> params = new HashMap();
        params.put("sort", "ordered");
        params.put("per_page", "50");
        params.put("page", String.valueOf(page));
        BaseModel.doGet(BaseModel.apiPath("/topics/%d/articles.json", Integer.valueOf(topicId)), params, new C11802(callback, callback));
    }

    public static RestTask loadInstantAnswers(String query, Callback<List<BaseModel>> callback) {
        Map<String, String> params = new HashMap();
        params.put("per_page", "3");
        params.put("forum_id", String.valueOf(BaseModel.getConfig().getForumId()));
        params.put("query", query);
        if (BaseModel.getConfig().getTopicId() != -1) {
            params.put("topic_id", String.valueOf(BaseModel.getConfig().getTopicId()));
        }
        return BaseModel.doGet(BaseModel.apiPath("/instant_answers/search.json", new Object[0]), params, new C11813(callback, callback));
    }

    public void load(JSONObject object) throws JSONException {
        super.load(object);
        this.title = getString(object, "question");
        this.html = getHtml(object, "answer_html");
        if (object.has("normalized_weight")) {
            this.weight = object.getInt("normalized_weight");
        }
        if (!object.isNull("topic")) {
            this.topicName = object.getJSONObject("topic").getString("name");
        }
    }

    public String getTitle() {
        return this.title;
    }

    public String getHtml() {
        return this.html;
    }

    public String getTopicName() {
        return this.topicName;
    }

    public int getWeight() {
        return this.weight;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.id);
        out.writeString(this.title);
        out.writeString(this.html);
        out.writeString(this.topicName);
        out.writeInt(this.weight);
    }

    static {
        CREATOR = new C06504();
    }

    private Article(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.html = in.readString();
        this.topicName = in.readString();
        this.weight = in.readInt();
    }
}
