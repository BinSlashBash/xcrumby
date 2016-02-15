/*
 * Decompiled with CFR 0_110.
 */
package com.uservoice.uservoicesdk.model;

import com.uservoice.uservoicesdk.model.BaseModel;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import org.json.JSONException;
import org.json.JSONObject;

public class RequestToken
extends BaseModel {
    private String key;
    private String secret;

    public static void getRequestToken(final Callback<RequestToken> callback) {
        RequestToken.doGet(RequestToken.apiPath("/oauth/request_token.json", new Object[0]), new RestTaskCallback(callback){

            @Override
            public void onComplete(JSONObject jSONObject) throws JSONException {
                callback.onModel(BaseModel.deserializeObject(jSONObject, "token", RequestToken.class));
            }
        });
    }

    public String getKey() {
        return this.key;
    }

    public String getSecret() {
        return this.secret;
    }

    @Override
    public void load(JSONObject jSONObject) throws JSONException {
        this.key = jSONObject.getString("oauth_token");
        this.secret = jSONObject.getString("oauth_token_secret");
    }

}

