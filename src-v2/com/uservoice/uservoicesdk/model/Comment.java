/*
 * Decompiled with CFR 0_110.
 */
package com.uservoice.uservoicesdk.model;

import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.model.BaseModel;
import com.uservoice.uservoicesdk.model.Suggestion;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class Comment
extends BaseModel {
    private String avatarUrl;
    private Date createdAt;
    private String text;
    private String userName;

    public static void createComment(final Suggestion suggestion, String string2, final Callback<Comment> callback) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("comment[text]", string2);
        Comment.doPost(Comment.apiPath("/forums/%d/suggestions/%d/comments.json", suggestion.getForumId(), suggestion.getId()), hashMap, new RestTaskCallback(callback){

            @Override
            public void onComplete(JSONObject jSONObject) throws JSONException {
                Babayaga.track(Babayaga.Event.COMMENT_IDEA, suggestion.getId());
                callback.onModel(BaseModel.deserializeObject(jSONObject, "comment", Comment.class));
            }
        });
    }

    public static void loadComments(Suggestion suggestion, int n2, final Callback<List<Comment>> callback) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("page", String.valueOf(n2));
        Comment.doGet(Comment.apiPath("/forums/%d/suggestions/%d/comments.json", suggestion.getForumId(), suggestion.getId()), hashMap, new RestTaskCallback(callback){

            @Override
            public void onComplete(JSONObject jSONObject) throws JSONException {
                callback.onModel(BaseModel.deserializeList(jSONObject, "comments", Comment.class));
            }
        });
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public String getText() {
        return this.text;
    }

    public String getUserName() {
        return this.userName;
    }

    @Override
    public void load(JSONObject jSONObject) throws JSONException {
        super.load(jSONObject);
        this.text = this.getString(jSONObject, "formatted_text");
        JSONObject jSONObject2 = jSONObject.getJSONObject("creator");
        this.userName = this.getString(jSONObject2, "name");
        this.avatarUrl = this.getString(jSONObject2, "avatar_url");
        this.createdAt = this.getDate(jSONObject, "created_at");
    }

}

