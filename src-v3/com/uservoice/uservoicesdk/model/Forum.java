package com.uservoice.uservoicesdk.model;

import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class Forum extends BaseModel {
    private List<Category> categories;
    private String name;
    private int numberOfOpenSuggestions;
    private int numberOfVotesAllowed;

    /* renamed from: com.uservoice.uservoicesdk.model.Forum.1 */
    static class C11861 extends RestTaskCallback {
        final /* synthetic */ Callback val$callback;

        C11861(Callback x0, Callback callback) {
            this.val$callback = callback;
            super(x0);
        }

        public void onComplete(JSONObject object) throws JSONException {
            this.val$callback.onModel(BaseModel.deserializeObject(object, "forum", Forum.class));
        }
    }

    public static void loadForum(int forumId, Callback<Forum> callback) {
        BaseModel.doGet(BaseModel.apiPath("/forums/%d.json", Integer.valueOf(forumId)), new C11861(callback, callback));
    }

    public void load(JSONObject object) throws JSONException {
        super.load(object);
        this.name = getString(object, "name");
        JSONObject topic = object.getJSONArray("topics").getJSONObject(0);
        this.numberOfOpenSuggestions = topic.getInt("open_suggestions_count");
        this.numberOfVotesAllowed = topic.getInt("votes_allowed");
        this.categories = BaseModel.deserializeList(topic, "categories", Category.class);
        if (this.categories == null) {
            this.categories = new ArrayList();
        }
    }

    public String getName() {
        return this.name;
    }

    public int getNumberOfOpenSuggestions() {
        return this.numberOfOpenSuggestions;
    }

    public int getNumberOfVotesAllowed() {
        return this.numberOfVotesAllowed;
    }

    public List<Category> getCategories() {
        return this.categories;
    }
}
