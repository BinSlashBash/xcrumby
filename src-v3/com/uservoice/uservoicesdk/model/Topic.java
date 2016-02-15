package com.uservoice.uservoicesdk.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.uservoice.uservoicesdk.C0621R;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class Topic extends BaseModel implements Parcelable {
    public static Topic ALL_ARTICLES;
    public static final Creator<Topic> CREATOR;
    protected String name;
    private int numberOfArticles;

    /* renamed from: com.uservoice.uservoicesdk.model.Topic.4 */
    static class C06514 implements Creator<Topic> {
        C06514() {
        }

        public Topic createFromParcel(Parcel in) {
            return new Topic(null);
        }

        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.model.Topic.2 */
    static class C11942 extends RestTaskCallback {
        final /* synthetic */ Callback val$callback;

        C11942(Callback x0, Callback callback) {
            this.val$callback = callback;
            super(x0);
        }

        public void onComplete(JSONObject object) throws JSONException {
            List<Topic> allTopics = BaseModel.deserializeList(object, "topics", Topic.class);
            List<Topic> topicsWithArticles = new ArrayList(allTopics.size());
            for (Topic topic : allTopics) {
                if (topic.getNumberOfArticles() > 0) {
                    topicsWithArticles.add(topic);
                }
            }
            this.val$callback.onModel(topicsWithArticles);
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.model.Topic.3 */
    static class C11953 extends RestTaskCallback {
        final /* synthetic */ Callback val$callback;

        C11953(Callback x0, Callback callback) {
            this.val$callback = callback;
            super(x0);
        }

        public void onComplete(JSONObject object) throws JSONException {
            this.val$callback.onModel(BaseModel.deserializeObject(object, "topic", Topic.class));
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.model.Topic.1 */
    static class C14481 extends Topic {
        C14481() {
            this.name = Session.getInstance().getContext().getString(C0621R.string.uv_all_articles);
            this.id = -1;
        }
    }

    static {
        ALL_ARTICLES = new C14481();
        CREATOR = new C06514();
    }

    public static void loadTopics(Callback<List<Topic>> callback) {
        BaseModel.doGet(BaseModel.apiPath("/topics.json", new Object[0]), new C11942(callback, callback));
    }

    public static void loadTopic(int topicId, Callback<Topic> callback) {
        BaseModel.doGet(BaseModel.apiPath("/topics/%d.json", Integer.valueOf(topicId)), new C11953(callback, callback));
    }

    public void load(JSONObject object) throws JSONException {
        super.load(object);
        this.name = getString(object, "name");
        this.numberOfArticles = object.getInt("article_count");
    }

    public String getName() {
        return this.name;
    }

    public int getNumberOfArticles() {
        return this.numberOfArticles;
    }

    public String toString() {
        return this.name;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.id);
        out.writeString(this.name);
        out.writeInt(this.numberOfArticles);
    }

    private Topic(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.numberOfArticles = in.readInt();
    }
}
