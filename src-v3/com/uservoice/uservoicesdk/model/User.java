package com.uservoice.uservoicesdk.model;

import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class User extends BaseModel {
    private String email;
    private String name;

    /* renamed from: com.uservoice.uservoicesdk.model.User.1 */
    static class C11961 extends RestTaskCallback {
        final /* synthetic */ Callback val$callback;

        C11961(Callback x0, Callback callback) {
            this.val$callback = callback;
            super(x0);
        }

        public void onComplete(JSONObject result) throws JSONException {
            this.val$callback.onModel(BaseModel.deserializeObject(result, "user", User.class));
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.model.User.2 */
    static class C11972 extends RestTaskCallback {
        final /* synthetic */ Callback val$callback;

        C11972(Callback x0, Callback callback) {
            this.val$callback = callback;
            super(x0);
        }

        public void onComplete(JSONObject object) throws JSONException {
            this.val$callback.onModel(BaseModel.deserializeObject(object, "user", User.class));
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.model.User.3 */
    static class C11983 extends RestTaskCallback {
        final /* synthetic */ Callback val$callback;

        C11983(Callback x0, Callback callback) {
            this.val$callback = callback;
            super(x0);
        }

        public void onComplete(JSONObject result) throws JSONException {
            User user = (User) BaseModel.deserializeObject(result, "user", User.class);
            this.val$callback.onModel(new AccessTokenResult(user, (AccessToken) BaseModel.deserializeObject(result, "token", AccessToken.class)));
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.model.User.4 */
    static class C11994 extends RestTaskCallback {
        final /* synthetic */ Callback val$callback;

        C11994(Callback x0, Callback callback) {
            this.val$callback = callback;
            super(x0);
        }

        public void onComplete(JSONObject result) throws JSONException {
            User user = (User) BaseModel.deserializeObject(result, "user", User.class);
            this.val$callback.onModel(new AccessTokenResult(user, (AccessToken) BaseModel.deserializeObject(result, "token", AccessToken.class)));
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.model.User.5 */
    static class C12005 extends RestTaskCallback {
        final /* synthetic */ Callback val$callback;

        C12005(Callback x0, Callback callback) {
            this.val$callback = callback;
            super(x0);
        }

        public void onComplete(JSONObject result) throws JSONException {
            this.val$callback.onModel(BaseModel.deserializeObject(result, "user", User.class));
        }
    }

    public static void discover(String email, Callback<User> callback) {
        Map<String, String> params = new HashMap();
        params.put("email", email);
        BaseModel.doGet(BaseModel.apiPath("/users/discover.json", new Object[0]), params, new C11961(callback, callback));
    }

    public static void loadCurrentUser(Callback<User> callback) {
        BaseModel.doGet(BaseModel.apiPath("/users/current.json", new Object[0]), new C11972(callback, callback));
    }

    public static void findOrCreate(String email, String name, String guid, Callback<AccessTokenResult<User>> callback) {
        Map<String, String> params = new HashMap();
        params.put("user[display_name]", name);
        params.put("user[email]", email);
        params.put("user[guid]", guid);
        params.put("request_token", Session.getInstance().getRequestToken().getKey());
        BaseModel.doPost(BaseModel.apiPath("/users/find_or_create.json", new Object[0]), params, new C11983(callback, callback));
    }

    public static void findOrCreate(String email, String name, Callback<AccessTokenResult<User>> callback) {
        Map<String, String> params = new HashMap();
        params.put("user[display_name]", name);
        params.put("user[email]", email);
        params.put("request_token", Session.getInstance().getRequestToken().getKey());
        BaseModel.doPost(BaseModel.apiPath("/users.json", new Object[0]), params, new C11994(callback, callback));
    }

    public static void sendForgotPassword(String email, Callback<User> callback) {
        Map<String, String> params = new HashMap();
        params.put("user[email]", email);
        BaseModel.doGet(BaseModel.apiPath("/users/forgot_password.json", new Object[0]), params, new C12005(callback, callback));
    }

    public void load(JSONObject object) throws JSONException {
        super.load(object);
        this.name = getString(object, "name");
        this.email = getString(object, "email");
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }
}
