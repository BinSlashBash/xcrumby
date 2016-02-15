/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 */
package com.uservoice.uservoicesdk.model;

import android.content.SharedPreferences;
import com.uservoice.uservoicesdk.Config;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.UserVoice;
import com.uservoice.uservoicesdk.model.BaseModel;
import com.uservoice.uservoicesdk.model.CustomField;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestResult;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ClientConfig
extends BaseModel {
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

    /*
     * Enabled aggressive block sorting
     */
    public static void loadClientConfig(final Callback<ClientConfig> callback) {
        if (Session.getInstance().getConfig() == null) {
            callback.onError(new RestResult(new Exception("Uservoice config not loaded.")));
            return;
        }
        String string2 = Session.getInstance().getConfig().getKey() == null ? "/clients/default.json" : "/client.json";
        final String string3 = String.format("uv-client-%s-%s-%s", UserVoice.getVersion(), Session.getInstance().getConfig().getSite(), Session.getInstance().getConfig().getKey());
        final SharedPreferences sharedPreferences = Session.getInstance().getSharedPreferences();
        ClientConfig clientConfig = (ClientConfig)ClientConfig.load(sharedPreferences, string3, "client", ClientConfig.class);
        if (clientConfig != null) {
            callback.onModel(clientConfig);
            ClientConfig.doGet(ClientConfig.apiPath(string2, new Object[0]), new RestTaskCallback(callback){

                @Override
                public void onComplete(JSONObject jSONObject) throws JSONException {
                    ((ClientConfig)BaseModel.deserializeObject(jSONObject, "client", ClientConfig.class)).persist(sharedPreferences, string3, "client");
                }
            });
            return;
        }
        ClientConfig.doGet(ClientConfig.apiPath(string2, new Object[0]), new RestTaskCallback(callback){

            @Override
            public void onComplete(JSONObject object) throws JSONException {
                object = (ClientConfig)BaseModel.deserializeObject((JSONObject)object, "client", ClientConfig.class);
                object.persist(sharedPreferences, string3, "client");
                callback.onModel(object);
            }
        });
    }

    public String getAccountName() {
        return this.accountName;
    }

    public List<CustomField> getCustomFields() {
        return this.customFields;
    }

    public int getDefaultForumId() {
        return this.defaultForumId;
    }

    public String getKey() {
        return this.key;
    }

    public String getSecret() {
        return this.secret;
    }

    public String getSubdomainId() {
        return this.subdomainId;
    }

    public String getSuggestionSort() {
        if (this.defaultSort.equals("new")) {
            return "newest";
        }
        if (this.defaultSort.equals("hot")) {
            return "hot";
        }
        return "votes";
    }

    public boolean isFeedbackEnabled() {
        return this.feedbackEnabled;
    }

    public boolean isTicketSystemEnabled() {
        return this.ticketsEnabled;
    }

    public boolean isWhiteLabel() {
        return this.whiteLabel;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void load(JSONObject object) throws JSONException {
        super.load((JSONObject)object);
        this.ticketsEnabled = object.getBoolean("tickets_enabled");
        this.feedbackEnabled = object.getBoolean("feedback_enabled");
        this.whiteLabel = object.getBoolean("white_label");
        if (object.has("display_suggestions_by_rank")) {
            this.displaySuggestionsByRank = object.getBoolean("display_suggestions_by_rank");
        }
        this.defaultForumId = object.getJSONObject("forum").getInt("id");
        this.customFields = ClientConfig.deserializeList((JSONObject)object, "custom_fields", CustomField.class);
        this.defaultSort = this.getString(object.getJSONObject("subdomain"), "default_sort");
        this.subdomainId = this.getString(object.getJSONObject("subdomain"), "id");
        this.accountName = this.getString(object.getJSONObject("subdomain"), "name");
        this.key = object.getString("key");
        object = object.has("secret") ? object.getString("secret") : null;
        this.secret = object;
    }

    @Override
    public void save(JSONObject jSONObject) throws JSONException {
        super.save(jSONObject);
        jSONObject.put("tickets_enabled", this.ticketsEnabled);
        jSONObject.put("feedback_enabled", this.feedbackEnabled);
        jSONObject.put("white_label", this.whiteLabel);
        jSONObject.put("display_suggestions_by_rank", this.displaySuggestionsByRank);
        Object object = new JSONObject();
        object.put("id", this.defaultForumId);
        jSONObject.put("forum", object);
        object = new JSONArray();
        for (CustomField customField : this.customFields) {
            JSONObject jSONObject2 = new JSONObject();
            customField.save(jSONObject2);
            object.put(jSONObject2);
        }
        jSONObject.put("custom_fields", object);
        object = new JSONObject();
        object.put("id", this.subdomainId);
        object.put("default_sort", this.defaultSort);
        object.put("name", this.accountName);
        jSONObject.put("subdomain", object);
        jSONObject.put("key", this.key);
        if (this.secret != null) {
            jSONObject.put("secret", this.secret);
        }
    }

    public boolean shouldDisplaySuggestionsByRank() {
        return this.displaySuggestionsByRank;
    }

}

