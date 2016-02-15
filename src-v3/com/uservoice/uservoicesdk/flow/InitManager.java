package com.uservoice.uservoicesdk.flow;

import android.content.Context;
import com.uservoice.uservoicesdk.Config;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.babayaga.Babayaga.Event;
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

    /* renamed from: com.uservoice.uservoicesdk.flow.InitManager.1 */
    class C14421 extends DefaultCallback<ClientConfig> {
        C14421(Context x0) {
            super(x0);
        }

        public void onModel(ClientConfig model) {
            Session.getInstance().setClientConfig(model);
            Babayaga.track(Event.VIEW_CHANNEL);
            InitManager.this.loadUser();
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.flow.InitManager.2 */
    class C14442 extends DefaultCallback<RequestToken> {

        /* renamed from: com.uservoice.uservoicesdk.flow.InitManager.2.1 */
        class C14431 extends DefaultCallback<AccessTokenResult<User>> {
            C14431(Context x0) {
                super(x0);
            }

            public void onModel(AccessTokenResult<User> model) {
                if (!InitManager.this.canceled) {
                    Session.getInstance().setAccessToken(InitManager.this.context, model.getAccessToken());
                    Session.getInstance().setUser((User) model.getModel());
                    InitManager.this.done();
                }
            }

            public void onError(RestResult error) {
                if (error.getType().equals("unauthorized")) {
                    InitManager.this.done();
                } else {
                    super.onError(error);
                }
            }
        }

        C14442(Context x0) {
            super(x0);
        }

        public void onModel(RequestToken model) {
            if (!InitManager.this.canceled) {
                Session.getInstance().setRequestToken(model);
                Config config = Session.getInstance().getConfig();
                User.findOrCreate(config.getEmail(), config.getName(), config.getGuid(), new C14431(InitManager.this.context));
            }
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.flow.InitManager.3 */
    class C14453 extends DefaultCallback<User> {
        C14453(Context x0) {
            super(x0);
        }

        public void onModel(User model) {
            Session.getInstance().setUser(model);
            InitManager.this.done();
        }
    }

    public InitManager(Context context, Runnable callback) {
        this.context = context;
        this.callback = callback;
    }

    public void init() {
        if (Session.getInstance().getClientConfig() == null) {
            ClientConfig.loadClientConfig(new C14421(this.context));
        } else {
            loadUser();
        }
    }

    private void loadUser() {
        if (Session.getInstance().getUser() != null) {
            done();
        } else if (shouldSignIn()) {
            RequestToken.getRequestToken(new C14442(this.context));
        } else {
            AccessToken accessToken = (AccessToken) BaseModel.load(Session.getInstance().getSharedPreferences(), "access_token", "access_token", AccessToken.class);
            if (accessToken != null) {
                Session.getInstance().setAccessToken(accessToken);
                User.loadCurrentUser(new C14453(this.context));
                return;
            }
            done();
        }
    }

    private boolean shouldSignIn() {
        return Session.getInstance().getConfig().getEmail() != null;
    }

    public void cancel() {
        this.canceled = true;
    }

    private void done() {
        this.callback.run();
    }
}
