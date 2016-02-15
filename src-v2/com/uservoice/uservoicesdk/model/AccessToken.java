/*
 * Decompiled with CFR 0_110.
 */
package com.uservoice.uservoicesdk.model;

import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.model.BaseModel;
import com.uservoice.uservoicesdk.model.RequestToken;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class AccessToken
extends BaseModel {
    private String key;
    private String secret;

    public static void authorize(String string2, String string3, final Callback<AccessToken> callback) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("email", string2);
        hashMap.put("password", string3);
        hashMap.put("request_token", Session.getInstance().getRequestToken().getKey());
        AccessToken.doPost(AccessToken.apiPath("/oauth/authorize.json", new Object[0]), hashMap, new RestTaskCallback(callback){

            @Override
            public void onComplete(JSONObject jSONObject) throws JSONException {
                callback.onModel(BaseModel.deserializeObject(jSONObject, "token", AccessToken.class));
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

    @Override
    public void save(JSONObject jSONObject) throws JSONException {
        jSONObject.put("oauth_token", this.key);
        jSONObject.put("oauth_token_secret", this.secret);
    }

}

