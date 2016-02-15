package com.uservoice.uservoicesdk.model;

import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import oauth.signpost.OAuth;
import org.json.JSONException;
import org.json.JSONObject;

public class RequestToken extends BaseModel {
    private String key;
    private String secret;

    /* renamed from: com.uservoice.uservoicesdk.model.RequestToken.1 */
    static class C11871 extends RestTaskCallback {
        final /* synthetic */ Callback val$callback;

        C11871(Callback x0, Callback callback) {
            this.val$callback = callback;
            super(x0);
        }

        public void onComplete(JSONObject result) throws JSONException {
            this.val$callback.onModel(BaseModel.deserializeObject(result, "token", RequestToken.class));
        }
    }

    public static void getRequestToken(Callback<RequestToken> callback) {
        BaseModel.doGet(BaseModel.apiPath("/oauth/request_token.json", new Object[0]), new C11871(callback, callback));
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
}
