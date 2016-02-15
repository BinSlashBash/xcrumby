/*
 * Decompiled with CFR 0_110.
 */
package com.uservoice.uservoicesdk.model;

import com.uservoice.uservoicesdk.model.BaseModel;
import com.uservoice.uservoicesdk.model.Category;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Forum
extends BaseModel {
    private List<Category> categories;
    private String name;
    private int numberOfOpenSuggestions;
    private int numberOfVotesAllowed;

    public static void loadForum(int n2, final Callback<Forum> callback) {
        Forum.doGet(Forum.apiPath("/forums/%d.json", n2), new RestTaskCallback(callback){

            @Override
            public void onComplete(JSONObject jSONObject) throws JSONException {
                callback.onModel(BaseModel.deserializeObject(jSONObject, "forum", Forum.class));
            }
        });
    }

    public List<Category> getCategories() {
        return this.categories;
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

    @Override
    public void load(JSONObject jSONObject) throws JSONException {
        super.load(jSONObject);
        this.name = this.getString(jSONObject, "name");
        jSONObject = jSONObject.getJSONArray("topics").getJSONObject(0);
        this.numberOfOpenSuggestions = jSONObject.getInt("open_suggestions_count");
        this.numberOfVotesAllowed = jSONObject.getInt("votes_allowed");
        this.categories = Forum.deserializeList(jSONObject, "categories", Category.class);
        if (this.categories == null) {
            this.categories = new ArrayList<Category>();
        }
    }

}

