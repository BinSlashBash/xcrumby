/*
 * Decompiled with CFR 0_110.
 */
package com.uservoice.uservoicesdk.model;

import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.model.BaseModel;
import com.uservoice.uservoicesdk.model.Category;
import com.uservoice.uservoicesdk.model.Comment;
import com.uservoice.uservoicesdk.model.Forum;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestTask;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class Suggestion
extends BaseModel {
    private String adminResponseAvatarUrl;
    private Date adminResponseCreatedAt;
    private String adminResponseText;
    private String adminResponseUserName;
    private Category category;
    private Date createdAt;
    private String creatorName;
    private int forumId;
    private String forumName;
    private int numberOfComments;
    private int numberOfSubscribers;
    private int rank;
    private String status;
    private String statusColor;
    private boolean subscribed;
    private String text;
    private String title;
    private int weight;

    public static void createSuggestion(Forum forum, Category category, String string2, String string3, int n2, final Callback<Suggestion> callback) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("subscribe", "true");
        hashMap.put("suggestion[title]", string2);
        hashMap.put("suggestion[text]", string3);
        if (category != null) {
            hashMap.put("suggestion[category_id]", String.valueOf(category.getId()));
        }
        Suggestion.doPost(Suggestion.apiPath("/forums/%d/suggestions.json", forum.getId()), hashMap, new RestTaskCallback(callback){

            @Override
            public void onComplete(JSONObject jSONObject) throws JSONException {
                callback.onModel(BaseModel.deserializeObject(jSONObject, "suggestion", Suggestion.class));
            }
        });
    }

    public static void loadSuggestions(Forum forum, int n2, final Callback<List<Suggestion>> callback) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("page", String.valueOf(n2));
        hashMap.put("per_page", "20");
        hashMap.put("filter", "public");
        hashMap.put("sort", Suggestion.getClientConfig().getSuggestionSort());
        Suggestion.doGet(Suggestion.apiPath("/forums/%d/suggestions.json", forum.getId()), hashMap, new RestTaskCallback(callback){

            @Override
            public void onComplete(JSONObject jSONObject) throws JSONException {
                callback.onModel(BaseModel.deserializeList(jSONObject, "suggestions", Suggestion.class));
            }
        });
    }

    public static RestTask searchSuggestions(Forum forum, String string2, final Callback<List<Suggestion>> callback) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("query", string2);
        return Suggestion.doGet(Suggestion.apiPath("/forums/%d/suggestions/search.json", forum.getId()), hashMap, new RestTaskCallback(callback){

            @Override
            public void onComplete(JSONObject jSONObject) throws JSONException {
                callback.onModel(BaseModel.deserializeList(jSONObject, "suggestions", Suggestion.class));
            }
        });
    }

    public void commentPosted(Comment comment) {
        ++this.numberOfComments;
    }

    public String getAdminResponseAvatarUrl() {
        return this.adminResponseAvatarUrl;
    }

    public Date getAdminResponseCreatedAt() {
        return this.adminResponseCreatedAt;
    }

    public String getAdminResponseText() {
        return this.adminResponseText;
    }

    public String getAdminResponseUserName() {
        return this.adminResponseUserName;
    }

    public Category getCategory() {
        return this.category;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public String getCreatorName() {
        return this.creatorName;
    }

    public int getForumId() {
        return this.forumId;
    }

    public String getForumName() {
        return this.forumName;
    }

    public int getNumberOfComments() {
        return this.numberOfComments;
    }

    public int getNumberOfSubscribers() {
        return this.numberOfSubscribers;
    }

    public int getRank() {
        return this.rank;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String getRankString() {
        String string2;
        if (this.rank % 100 > 10 && this.rank % 100 < 14) {
            string2 = "th";
            do {
                return String.valueOf(this.rank) + string2;
                break;
            } while (true);
        }
        switch (this.rank % 10) {
            default: {
                string2 = "th";
                return String.valueOf(this.rank) + string2;
            }
            case 1: {
                string2 = "st";
                return String.valueOf(this.rank) + string2;
            }
            case 2: {
                string2 = "nd";
                return String.valueOf(this.rank) + string2;
            }
            case 3: 
        }
        string2 = "rd";
        return String.valueOf(this.rank) + string2;
    }

    public String getStatus() {
        return this.status;
    }

    public String getStatusColor() {
        return this.statusColor;
    }

    public String getText() {
        return this.text;
    }

    public String getTitle() {
        return this.title;
    }

    public int getWeight() {
        return this.weight;
    }

    public boolean isSubscribed() {
        return this.subscribed;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void load(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2;
        super.load(jSONObject);
        this.title = this.getString(jSONObject, "title");
        this.text = this.getString(jSONObject, "formatted_text");
        this.createdAt = this.getDate(jSONObject, "created_at");
        this.forumId = jSONObject.getJSONObject("topic").getJSONObject("forum").getInt("id");
        this.forumName = jSONObject.getJSONObject("topic").getJSONObject("forum").getString("name");
        boolean bl2 = jSONObject.has("subscribed") && jSONObject.getBoolean("subscribed");
        this.subscribed = bl2;
        if (!jSONObject.isNull("category")) {
            this.category = (Category)Suggestion.deserializeObject(jSONObject, "category", Category.class);
        }
        this.numberOfComments = jSONObject.getInt("comments_count");
        this.numberOfSubscribers = jSONObject.getInt("subscriber_count");
        if (!jSONObject.isNull("creator")) {
            this.creatorName = this.getString(jSONObject.getJSONObject("creator"), "name");
        }
        if (!jSONObject.isNull("status")) {
            jSONObject2 = jSONObject.getJSONObject("status");
            this.status = this.getString(jSONObject2, "name");
            this.statusColor = this.getString(jSONObject2, "hex_color");
        }
        if (!jSONObject.isNull("response")) {
            jSONObject2 = jSONObject.getJSONObject("response");
            this.adminResponseText = this.getString(jSONObject2, "formatted_text");
            this.adminResponseCreatedAt = this.getDate(jSONObject2, "created_at");
            jSONObject2 = jSONObject2.getJSONObject("creator");
            this.adminResponseUserName = this.getString(jSONObject2, "name");
            this.adminResponseAvatarUrl = this.getString(jSONObject2, "avatar_url");
        }
        if (jSONObject.has("normalized_weight")) {
            this.weight = jSONObject.getInt("normalized_weight");
        }
        if (jSONObject.has("rank")) {
            this.rank = jSONObject.getInt("rank");
        }
    }

    public void subscribe(final Callback<Suggestion> callback) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("subscribe", "true");
        Suggestion.doPost(Suggestion.apiPath("/forums/%d/suggestions/%d/watch.json", this.forumId, this.id), hashMap, new RestTaskCallback(callback){

            @Override
            public void onComplete(JSONObject jSONObject) throws JSONException {
                Babayaga.track(Babayaga.Event.VOTE_IDEA, Suggestion.this.getId());
                Babayaga.track(Babayaga.Event.SUBSCRIBE_IDEA, Suggestion.this.getId());
                Suggestion.this.load(jSONObject.getJSONObject("suggestion"));
                callback.onModel(Suggestion.this);
            }
        });
    }

    public void unsubscribe(final Callback<Suggestion> callback) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("subscribe", "false");
        Suggestion.doPost(Suggestion.apiPath("/forums/%d/suggestions/%d/watch.json", this.forumId, this.id), hashMap, new RestTaskCallback(callback){

            @Override
            public void onComplete(JSONObject jSONObject) throws JSONException {
                Suggestion.this.load(jSONObject.getJSONObject("suggestion"));
                callback.onModel(Suggestion.this);
            }
        });
    }

}

