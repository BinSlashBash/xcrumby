package com.uservoice.uservoicesdk.model;

import android.content.SharedPreferences;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.UserVoice;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestResult;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ClientConfig extends BaseModel {
    private String accountName;
    private List<CustomField> customFields;
    private int defaultForumId;
    private String defaultSort;
    private boolean displaySuggestionsByRank;
    private boolean feedbackEnabled;
    private String key;
    private String secret;
    private String subdomainId;
    private boolean ticketsEnabled;
    private boolean whiteLabel;

    /* renamed from: com.uservoice.uservoicesdk.model.ClientConfig.1 */
    static class C11821 extends RestTaskCallback {
        final /* synthetic */ String val$cacheKey;
        final /* synthetic */ SharedPreferences val$prefs;

        C11821(Callback x0, SharedPreferences sharedPreferences, String str) {
            this.val$prefs = sharedPreferences;
            this.val$cacheKey = str;
            super(x0);
        }

        public void onComplete(JSONObject result) throws JSONException {
            ((ClientConfig) BaseModel.deserializeObject(result, "client", ClientConfig.class)).persist(this.val$prefs, this.val$cacheKey, "client");
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.model.ClientConfig.2 */
    static class C11832 extends RestTaskCallback {
        final /* synthetic */ String val$cacheKey;
        final /* synthetic */ Callback val$callback;
        final /* synthetic */ SharedPreferences val$prefs;

        C11832(Callback x0, SharedPreferences sharedPreferences, String str, Callback callback) {
            this.val$prefs = sharedPreferences;
            this.val$cacheKey = str;
            this.val$callback = callback;
            super(x0);
        }

        public void onComplete(JSONObject result) throws JSONException {
            ClientConfig clientConfig = (ClientConfig) BaseModel.deserializeObject(result, "client", ClientConfig.class);
            clientConfig.persist(this.val$prefs, this.val$cacheKey, "client");
            this.val$callback.onModel(clientConfig);
        }
    }

    public static void loadClientConfig(Callback<ClientConfig> callback) {
        if (Session.getInstance().getConfig() == null) {
            callback.onError(new RestResult(new Exception("Uservoice config not loaded.")));
            return;
        }
        String path = Session.getInstance().getConfig().getKey() == null ? "/clients/default.json" : "/client.json";
        String cacheKey = String.format("uv-client-%s-%s-%s", new Object[]{UserVoice.getVersion(), Session.getInstance().getConfig().getSite(), Session.getInstance().getConfig().getKey()});
        SharedPreferences prefs = Session.getInstance().getSharedPreferences();
        ClientConfig clientConfig = (ClientConfig) BaseModel.load(prefs, cacheKey, "client", ClientConfig.class);
        if (clientConfig != null) {
            callback.onModel(clientConfig);
            BaseModel.doGet(BaseModel.apiPath(path, new Object[0]), new C11821(callback, prefs, cacheKey));
            return;
        }
        BaseModel.doGet(BaseModel.apiPath(path, new Object[0]), new C11832(callback, prefs, cacheKey, callback));
    }

    public void load(JSONObject object) throws JSONException {
        super.load(object);
        this.ticketsEnabled = object.getBoolean("tickets_enabled");
        this.feedbackEnabled = object.getBoolean("feedback_enabled");
        this.whiteLabel = object.getBoolean("white_label");
        if (object.has("display_suggestions_by_rank")) {
            this.displaySuggestionsByRank = object.getBoolean("display_suggestions_by_rank");
        }
        this.defaultForumId = object.getJSONObject("forum").getInt("id");
        this.customFields = BaseModel.deserializeList(object, "custom_fields", CustomField.class);
        this.defaultSort = getString(object.getJSONObject("subdomain"), "default_sort");
        this.subdomainId = getString(object.getJSONObject("subdomain"), "id");
        this.accountName = getString(object.getJSONObject("subdomain"), "name");
        this.key = object.getString("key");
        this.secret = object.has("secret") ? object.getString("secret") : null;
    }

    public void save(JSONObject object) throws JSONException {
        super.save(object);
        object.put("tickets_enabled", this.ticketsEnabled);
        object.put("feedback_enabled", this.feedbackEnabled);
        object.put("white_label", this.whiteLabel);
        object.put("display_suggestions_by_rank", this.displaySuggestionsByRank);
        Object forum = new JSONObject();
        forum.put("id", this.defaultForumId);
        object.put("forum", forum);
        Object jsonCustomFields = new JSONArray();
        for (CustomField customField : this.customFields) {
            Object jsonCustomField = new JSONObject();
            customField.save(jsonCustomField);
            jsonCustomFields.put(jsonCustomField);
        }
        object.put("custom_fields", jsonCustomFields);
        Object subdomain = new JSONObject();
        subdomain.put("id", this.subdomainId);
        subdomain.put("default_sort", this.defaultSort);
        subdomain.put("name", this.accountName);
        object.put("subdomain", subdomain);
        object.put("key", this.key);
        if (this.secret != null) {
            object.put("secret", this.secret);
        }
    }

    public String getAccountName() {
        return this.accountName;
    }

    public boolean isTicketSystemEnabled() {
        return this.ticketsEnabled;
    }

    public boolean isFeedbackEnabled() {
        return this.feedbackEnabled;
    }

    public boolean isWhiteLabel() {
        return this.whiteLabel;
    }

    public boolean shouldDisplaySuggestionsByRank() {
        return this.displaySuggestionsByRank;
    }

    public int getDefaultForumId() {
        return this.defaultForumId;
    }

    public List<CustomField> getCustomFields() {
        return this.customFields;
    }

    public String getSuggestionSort() {
        if (this.defaultSort.equals("new")) {
            return "newest";
        }
        return this.defaultSort.equals("hot") ? "hot" : "votes";
    }

    public String getSubdomainId() {
        return this.subdomainId;
    }

    public String getKey() {
        return this.key;
    }

    public String getSecret() {
        return this.secret;
    }
}
