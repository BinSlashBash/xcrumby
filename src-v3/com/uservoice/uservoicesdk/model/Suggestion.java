package com.uservoice.uservoicesdk.model;

import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.plus.PlusShare;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.babayaga.Babayaga.Event;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestTask;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class Suggestion extends BaseModel {
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

    /* renamed from: com.uservoice.uservoicesdk.model.Suggestion.1 */
    static class C11881 extends RestTaskCallback {
        final /* synthetic */ Callback val$callback;

        C11881(Callback x0, Callback callback) {
            this.val$callback = callback;
            super(x0);
        }

        public void onComplete(JSONObject object) throws JSONException {
            this.val$callback.onModel(BaseModel.deserializeList(object, "suggestions", Suggestion.class));
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.model.Suggestion.2 */
    static class C11892 extends RestTaskCallback {
        final /* synthetic */ Callback val$callback;

        C11892(Callback x0, Callback callback) {
            this.val$callback = callback;
            super(x0);
        }

        public void onComplete(JSONObject object) throws JSONException {
            this.val$callback.onModel(BaseModel.deserializeList(object, "suggestions", Suggestion.class));
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.model.Suggestion.3 */
    static class C11903 extends RestTaskCallback {
        final /* synthetic */ Callback val$callback;

        C11903(Callback x0, Callback callback) {
            this.val$callback = callback;
            super(x0);
        }

        public void onComplete(JSONObject object) throws JSONException {
            this.val$callback.onModel(BaseModel.deserializeObject(object, "suggestion", Suggestion.class));
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.model.Suggestion.4 */
    class C11914 extends RestTaskCallback {
        final /* synthetic */ Callback val$callback;

        C11914(Callback x0, Callback callback) {
            this.val$callback = callback;
            super(x0);
        }

        public void onComplete(JSONObject result) throws JSONException {
            Babayaga.track(Event.VOTE_IDEA, Suggestion.this.getId());
            Babayaga.track(Event.SUBSCRIBE_IDEA, Suggestion.this.getId());
            Suggestion.this.load(result.getJSONObject("suggestion"));
            this.val$callback.onModel(Suggestion.this);
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.model.Suggestion.5 */
    class C11925 extends RestTaskCallback {
        final /* synthetic */ Callback val$callback;

        C11925(Callback x0, Callback callback) {
            this.val$callback = callback;
            super(x0);
        }

        public void onComplete(JSONObject result) throws JSONException {
            Suggestion.this.load(result.getJSONObject("suggestion"));
            this.val$callback.onModel(Suggestion.this);
        }
    }

    public static void loadSuggestions(Forum forum, int page, Callback<List<Suggestion>> callback) {
        Map<String, String> params = new HashMap();
        params.put("page", String.valueOf(page));
        params.put("per_page", "20");
        params.put("filter", "public");
        params.put("sort", BaseModel.getClientConfig().getSuggestionSort());
        BaseModel.doGet(BaseModel.apiPath("/forums/%d/suggestions.json", Integer.valueOf(forum.getId())), params, new C11881(callback, callback));
    }

    public static RestTask searchSuggestions(Forum forum, String query, Callback<List<Suggestion>> callback) {
        Map<String, String> params = new HashMap();
        params.put("query", query);
        return BaseModel.doGet(BaseModel.apiPath("/forums/%d/suggestions/search.json", Integer.valueOf(forum.getId())), params, new C11892(callback, callback));
    }

    public static void createSuggestion(Forum forum, Category category, String title, String text, int numberOfVotes, Callback<Suggestion> callback) {
        Map<String, String> params = new HashMap();
        params.put("subscribe", "true");
        params.put("suggestion[title]", title);
        params.put("suggestion[text]", text);
        if (category != null) {
            params.put("suggestion[category_id]", String.valueOf(category.getId()));
        }
        BaseModel.doPost(BaseModel.apiPath("/forums/%d/suggestions.json", Integer.valueOf(forum.getId())), params, new C11903(callback, callback));
    }

    public void subscribe(Callback<Suggestion> callback) {
        Map<String, String> params = new HashMap();
        params.put("subscribe", "true");
        BaseModel.doPost(BaseModel.apiPath("/forums/%d/suggestions/%d/watch.json", Integer.valueOf(this.forumId), Integer.valueOf(this.id)), params, new C11914(callback, callback));
    }

    public void unsubscribe(Callback<Suggestion> callback) {
        Map<String, String> params = new HashMap();
        params.put("subscribe", "false");
        BaseModel.doPost(BaseModel.apiPath("/forums/%d/suggestions/%d/watch.json", Integer.valueOf(this.forumId), Integer.valueOf(this.id)), params, new C11925(callback, callback));
    }

    public void load(JSONObject object) throws JSONException {
        super.load(object);
        this.title = getString(object, PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE);
        this.text = getString(object, "formatted_text");
        this.createdAt = getDate(object, "created_at");
        this.forumId = object.getJSONObject("topic").getJSONObject("forum").getInt("id");
        this.forumName = object.getJSONObject("topic").getJSONObject("forum").getString("name");
        boolean z = object.has("subscribed") && object.getBoolean("subscribed");
        this.subscribed = z;
        if (!object.isNull("category")) {
            this.category = (Category) BaseModel.deserializeObject(object, "category", Category.class);
        }
        this.numberOfComments = object.getInt("comments_count");
        this.numberOfSubscribers = object.getInt("subscriber_count");
        if (!object.isNull("creator")) {
            this.creatorName = getString(object.getJSONObject("creator"), "name");
        }
        if (!object.isNull("status")) {
            JSONObject statusObject = object.getJSONObject("status");
            this.status = getString(statusObject, "name");
            this.statusColor = getString(statusObject, "hex_color");
        }
        if (!object.isNull("response")) {
            JSONObject response = object.getJSONObject("response");
            this.adminResponseText = getString(response, "formatted_text");
            this.adminResponseCreatedAt = getDate(response, "created_at");
            JSONObject responseUser = response.getJSONObject("creator");
            this.adminResponseUserName = getString(responseUser, "name");
            this.adminResponseAvatarUrl = getString(responseUser, "avatar_url");
        }
        if (object.has("normalized_weight")) {
            this.weight = object.getInt("normalized_weight");
        }
        if (object.has("rank")) {
            this.rank = object.getInt("rank");
        }
    }

    public String getForumName() {
        return this.forumName;
    }

    public boolean isSubscribed() {
        return this.subscribed;
    }

    public int getForumId() {
        return this.forumId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getText() {
        return this.text;
    }

    public String getStatus() {
        return this.status;
    }

    public String getStatusColor() {
        return this.statusColor;
    }

    public String getCreatorName() {
        return this.creatorName;
    }

    public String getAdminResponseText() {
        return this.adminResponseText;
    }

    public String getAdminResponseUserName() {
        return this.adminResponseUserName;
    }

    public String getAdminResponseAvatarUrl() {
        return this.adminResponseAvatarUrl;
    }

    public Date getAdminResponseCreatedAt() {
        return this.adminResponseCreatedAt;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Category getCategory() {
        return this.category;
    }

    public int getNumberOfComments() {
        return this.numberOfComments;
    }

    public int getNumberOfSubscribers() {
        return this.numberOfSubscribers;
    }

    public void commentPosted(Comment comment) {
        this.numberOfComments++;
    }

    public int getWeight() {
        return this.weight;
    }

    public int getRank() {
        return this.rank;
    }

    public String getRankString() {
        String suffix;
        if (this.rank % 100 <= 10 || this.rank % 100 >= 14) {
            switch (this.rank % 10) {
                case Std.STD_FILE /*1*/:
                    suffix = "st";
                    break;
                case Std.STD_URL /*2*/:
                    suffix = "nd";
                    break;
                case Std.STD_URI /*3*/:
                    suffix = "rd";
                    break;
                default:
                    suffix = "th";
                    break;
            }
        }
        suffix = "th";
        return String.valueOf(this.rank) + suffix;
    }
}
