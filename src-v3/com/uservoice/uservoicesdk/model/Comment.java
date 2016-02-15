package com.uservoice.uservoicesdk.model;

import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.babayaga.Babayaga.Event;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class Comment extends BaseModel {
    private String avatarUrl;
    private Date createdAt;
    private String text;
    private String userName;

    /* renamed from: com.uservoice.uservoicesdk.model.Comment.1 */
    static class C11841 extends RestTaskCallback {
        final /* synthetic */ Callback val$callback;

        C11841(Callback x0, Callback callback) {
            this.val$callback = callback;
            super(x0);
        }

        public void onComplete(JSONObject object) throws JSONException {
            this.val$callback.onModel(BaseModel.deserializeList(object, "comments", Comment.class));
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.model.Comment.2 */
    static class C11852 extends RestTaskCallback {
        final /* synthetic */ Callback val$callback;
        final /* synthetic */ Suggestion val$suggestion;

        C11852(Callback x0, Suggestion suggestion, Callback callback) {
            this.val$suggestion = suggestion;
            this.val$callback = callback;
            super(x0);
        }

        public void onComplete(JSONObject object) throws JSONException {
            Babayaga.track(Event.COMMENT_IDEA, this.val$suggestion.getId());
            this.val$callback.onModel(BaseModel.deserializeObject(object, "comment", Comment.class));
        }
    }

    public static void loadComments(Suggestion suggestion, int page, Callback<List<Comment>> callback) {
        Map<String, String> params = new HashMap();
        params.put("page", String.valueOf(page));
        BaseModel.doGet(BaseModel.apiPath("/forums/%d/suggestions/%d/comments.json", Integer.valueOf(suggestion.getForumId()), Integer.valueOf(suggestion.getId())), params, new C11841(callback, callback));
    }

    public static void createComment(Suggestion suggestion, String text, Callback<Comment> callback) {
        Map<String, String> params = new HashMap();
        params.put("comment[text]", text);
        BaseModel.doPost(BaseModel.apiPath("/forums/%d/suggestions/%d/comments.json", Integer.valueOf(suggestion.getForumId()), Integer.valueOf(suggestion.getId())), params, new C11852(callback, suggestion, callback));
    }

    public void load(JSONObject object) throws JSONException {
        super.load(object);
        this.text = getString(object, "formatted_text");
        JSONObject user = object.getJSONObject("creator");
        this.userName = getString(user, "name");
        this.avatarUrl = getString(user, "avatar_url");
        this.createdAt = getDate(object, "created_at");
    }

    public String getText() {
        return this.text;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }
}
