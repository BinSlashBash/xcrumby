package com.uservoice.uservoicesdk.model;

import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import java.util.HashMap;
import java.util.Map;
import oauth.signpost.OAuth;
import org.json.JSONException;
import org.json.JSONObject;

public class AccessToken extends BaseModel {
    private String key;
    private String secret;

    /* renamed from: com.uservoice.uservoicesdk.model.AccessToken.1 */
    static class C11781 extends RestTaskCallback {
        final /* synthetic */ Callback val$callback;

        C11781(Callback x0, Callback callback) {
            this.val$callback = callback;
            super(x0);
        }

        public void onComplete(JSONObject result) throws JSONException {
            this.val$callback.onModel(BaseModel.deserializeObject(result, "token", AccessToken.class));
        }
    }

    public static void authorize(String email, String password, Callback<AccessToken> callback) {
        Map<String, String> params = new HashMap();
        params.put("email", email);
        params.put("password", password);
        params.put("request_token", Session.getInstance().getRequestToken().getKey());
        BaseModel.doPost(BaseModel.apiPath("/oauth/authorize.json", new Object[0]), params, new C11781(callback, callback));
    }

    public void load(JSONObject object) throws JSONException {
        this.key = object.getString(OAuth.OAUTH_TOKEN);
        this.secret = object.getString(OAuth.OAUTH_TOKEN_SECRET);
    }

    public String getKey() {
        return this.key;
    }

    public String getSecret() {
        return this.secret;
    }

    public void save(JSONObject object) throws JSONException {
        object.put(OAuth.OAUTH_TOKEN, this.key);
        object.put(OAuth.OAUTH_TOKEN_SECRET, this.secret);
    }
}
