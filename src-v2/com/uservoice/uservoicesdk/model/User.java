/*
 * Decompiled with CFR 0_110.
 */
package com.uservoice.uservoicesdk.model;

import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.model.AccessToken;
import com.uservoice.uservoicesdk.model.AccessTokenResult;
import com.uservoice.uservoicesdk.model.BaseModel;
import com.uservoice.uservoicesdk.model.RequestToken;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class User
extends BaseModel {
    private String email;
    private String name;

    public static void discover(String string2, final Callback<User> callback) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("email", string2);
        User.doGet(User.apiPath("/users/discover.json", new Object[0]), hashMap, new RestTaskCallback(callback){

            @Override
            public void onComplete(JSONObject jSONObject) throws JSONException {
                callback.onModel(BaseModel.deserializeObject(jSONObject, "user", User.class));
            }
        });
    }

    public static void findOrCreate(String string2, String string3, final Callback<AccessTokenResult<User>> callback) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("user[display_name]", string3);
        hashMap.put("user[email]", string2);
        hashMap.put("request_token", Session.getInstance().getRequestToken().getKey());
        User.doPost(User.apiPath("/users.json", new Object[0]), hashMap, new RestTaskCallback(callback){

            @Override
            public void onComplete(JSONObject object) throws JSONException {
                AccessToken accessToken = (AccessToken)BaseModel.deserializeObject((JSONObject)object, "token", AccessToken.class);
                object = (User)BaseModel.deserializeObject((JSONObject)object, "user", User.class);
                callback.onModel(new AccessTokenResult<Object>(object, accessToken));
            }
        });
    }

    public static void findOrCreate(String string2, String string3, String string4, final Callback<AccessTokenResult<User>> callback) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("user[display_name]", string3);
        hashMap.put("user[email]", string2);
        hashMap.put("user[guid]", string4);
        hashMap.put("request_token", Session.getInstance().getRequestToken().getKey());
        User.doPost(User.apiPath("/users/find_or_create.json", new Object[0]), hashMap, new RestTaskCallback(callback){

            @Override
            public void onComplete(JSONObject object) throws JSONException {
                AccessToken accessToken = (AccessToken)BaseModel.deserializeObject((JSONObject)object, "token", AccessToken.class);
                object = (User)BaseModel.deserializeObject((JSONObject)object, "user", User.class);
                callback.onModel(new AccessTokenResult<Object>(object, accessToken));
            }
        });
    }

    public static void loadCurrentUser(final Callback<User> callback) {
        User.doGet(User.apiPath("/users/current.json", new Object[0]), new RestTaskCallback(callback){

            @Override
            public void onComplete(JSONObject jSONObject) throws JSONException {
                callback.onModel(BaseModel.deserializeObject(jSONObject, "user", User.class));
            }
        });
    }

    public static void sendForgotPassword(String string2, final Callback<User> callback) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("user[email]", string2);
        User.doGet(User.apiPath("/users/forgot_password.json", new Object[0]), hashMap, new RestTaskCallback(callback){

            @Override
            public void onComplete(JSONObject jSONObject) throws JSONException {
                callback.onModel(BaseModel.deserializeObject(jSONObject, "user", User.class));
            }
        });
    }

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void load(JSONObject jSONObject) throws JSONException {
        super.load(jSONObject);
        this.name = this.getString(jSONObject, "name");
        this.email = this.getString(jSONObject, "email");
    }

}

