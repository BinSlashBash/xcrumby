/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 */
package com.uservoice.uservoicesdk.flow;

import android.content.Context;
import android.content.SharedPreferences;
import com.uservoice.uservoicesdk.Config;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.model.AccessToken;
import com.uservoice.uservoicesdk.model.AccessTokenResult;
import com.uservoice.uservoicesdk.model.BaseModel;
import com.uservoice.uservoicesdk.model.ClientConfig;
import com.uservoice.uservoicesdk.model.RequestToken;
import com.uservoice.uservoicesdk.model.User;
import com.uservoice.uservoicesdk.rest.RestResult;
import com.uservoice.uservoicesdk.ui.DefaultCallback;

public class InitManager {
    private final Runnable callback;
    private boolean canceled;
    private final Context context;

    public InitManager(Context context, Runnable runnable) {
        this.context = context;
        this.callback = runnable;
    }

    private void done() {
        this.callback.run();
    }

    private void loadUser() {
        if (Session.getInstance().getUser() == null) {
            if (this.shouldSignIn()) {
                RequestToken.getRequestToken(new DefaultCallback<RequestToken>(this.context){

                    @Override
                    public void onModel(RequestToken object) {
                        if (InitManager.this.canceled) {
                            return;
                        }
                        Session.getInstance().setRequestToken((RequestToken)object);
                        object = Session.getInstance().getConfig();
                        User.findOrCreate(object.getEmail(), object.getName(), object.getGuid(), new DefaultCallback<AccessTokenResult<User>>(InitManager.this.context){

                            @Override
                            public void onError(RestResult restResult) {
                                if (restResult.getType().equals("unauthorized")) {
                                    InitManager.this.done();
                                    return;
                                }
                                super.onError(restResult);
                            }

                            @Override
                            public void onModel(AccessTokenResult<User> accessTokenResult) {
                                if (InitManager.this.canceled) {
                                    return;
                                }
                                Session.getInstance().setAccessToken(InitManager.this.context, accessTokenResult.getAccessToken());
                                Session.getInstance().setUser(accessTokenResult.getModel());
                                InitManager.this.done();
                            }
                        });
                    }

                });
                return;
            }
            AccessToken accessToken = (AccessToken)BaseModel.load(Session.getInstance().getSharedPreferences(), "access_token", "access_token", AccessToken.class);
            if (accessToken != null) {
                Session.getInstance().setAccessToken(accessToken);
                User.loadCurrentUser(new DefaultCallback<User>(this.context){

                    @Override
                    public void onModel(User user) {
                        Session.getInstance().setUser(user);
                        InitManager.this.done();
                    }
                });
                return;
            }
            this.done();
            return;
        }
        this.done();
    }

    private boolean shouldSignIn() {
        if (Session.getInstance().getConfig().getEmail() != null) {
            return true;
        }
        return false;
    }

    public void cancel() {
        this.canceled = true;
    }

    public void init() {
        if (Session.getInstance().getClientConfig() == null) {
            ClientConfig.loadClientConfig(new DefaultCallback<ClientConfig>(this.context){

                @Override
                public void onModel(ClientConfig clientConfig) {
                    Session.getInstance().setClientConfig(clientConfig);
                    Babayaga.track(Babayaga.Event.VIEW_CHANNEL);
                    InitManager.this.loadUser();
                }
            });
            return;
        }
        this.loadUser();
    }

}

