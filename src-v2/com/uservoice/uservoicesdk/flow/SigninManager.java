/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.widget.Toast
 */
package com.uservoice.uservoicesdk.flow;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;
import com.uservoice.uservoicesdk.R;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.dialog.PasswordDialogFragment;
import com.uservoice.uservoicesdk.dialog.SigninDialogFragment;
import com.uservoice.uservoicesdk.flow.SigninCallback;
import com.uservoice.uservoicesdk.model.AccessToken;
import com.uservoice.uservoicesdk.model.AccessTokenResult;
import com.uservoice.uservoicesdk.model.RequestToken;
import com.uservoice.uservoicesdk.model.User;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestResult;
import com.uservoice.uservoicesdk.ui.DefaultCallback;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SigninManager {
    private final FragmentActivity activity;
    private final SigninCallback callback;
    private String email;
    private Pattern emailFormat;
    private String name;
    private boolean passwordOnly;

    private SigninManager(FragmentActivity object, String string2, String string3, SigninCallback signinCallback) {
        block5 : {
            block4 : {
                this.emailFormat = Pattern.compile("\\A(\\w[-+.\\w!\\#\\$%&'\\*\\+\\-/=\\?\\^_`\\{\\|\\}~]*@([-\\w]*\\.)+[a-zA-Z]{2,9})\\z");
                this.activity = object;
                if (string2 != null) {
                    object = string2;
                    if (string2.trim().length() != 0) break block4;
                }
                object = null;
            }
            this.email = object;
            if (string3 != null) {
                object = string3;
                if (string3.trim().length() != 0) break block5;
            }
            object = null;
        }
        this.name = object;
        this.callback = signinCallback;
    }

    private void createUser() {
        RequestToken.getRequestToken(new DefaultCallback<RequestToken>((Context)this.activity){

            @Override
            public void onModel(RequestToken requestToken) {
                Session.getInstance().setRequestToken(requestToken);
                User.findOrCreate(SigninManager.this.email, SigninManager.this.name, new DefaultCallback<AccessTokenResult<User>>((Context)SigninManager.this.activity){

                    @Override
                    public void onModel(AccessTokenResult<User> accessTokenResult) {
                        Session.getInstance().setUser(accessTokenResult.getModel());
                        Session.getInstance().setAccessToken((Context)SigninManager.this.activity, accessTokenResult.getAccessToken());
                        Babayaga.track(Babayaga.Event.IDENTIFY);
                        SigninManager.this.callback.onSuccess();
                    }
                });
            }

        });
    }

    private void promptToSignIn() {
        if (this.passwordOnly) {
            new PasswordDialogFragment(this.callback).show(this.activity.getSupportFragmentManager(), "PasswordDialogFragment");
            return;
        }
        new SigninDialogFragment(this.email, this.name, this.callback).show(this.activity.getSupportFragmentManager(), "SigninDialogFragment");
    }

    /*
     * Enabled aggressive block sorting
     */
    private void signIn() {
        Object object = Session.getInstance().getUser();
        if (object != null && (this.email == null || this.email.equals(object.getEmail()))) {
            this.callback.onSuccess();
            return;
        }
        if (Session.getInstance().getAccessToken() != null) {
            this.callback.onSuccess();
            return;
        }
        if (this.email != null && !this.emailFormat.matcher(this.email).matches()) {
            Toast.makeText((Context)this.activity, (int)R.string.uv_msg_bad_email_format, (int)0).show();
            this.callback.onFailure();
            return;
        }
        object = this.email == null ? Session.getInstance().getEmail() : this.email;
        this.email = object;
        object = this.name == null ? Session.getInstance().getName() : this.name;
        this.name = object;
        if (this.email != null) {
            User.discover(this.email, new Callback<User>(){

                @Override
                public void onError(RestResult restResult) {
                    SigninManager.this.createUser();
                }

                @Override
                public void onModel(User user) {
                    SigninManager.this.promptToSignIn();
                }
            });
            return;
        }
        this.promptToSignIn();
    }

    public static void signIn(FragmentActivity fragmentActivity, SigninCallback signinCallback) {
        new SigninManager(fragmentActivity, null, null, signinCallback).signIn();
    }

    public static void signIn(FragmentActivity fragmentActivity, String string2, String string3, SigninCallback signinCallback) {
        new SigninManager(fragmentActivity, string2, string3, signinCallback).signIn();
    }

    public static void signinForSubscribe(FragmentActivity object, String string2, SigninCallback signinCallback) {
        object = new SigninManager((FragmentActivity)((Object)object), string2, Session.getInstance().getName(), signinCallback);
        object.setPasswordOnly(true);
        object.signIn();
    }

    public void setPasswordOnly(boolean bl2) {
        this.passwordOnly = bl2;
    }

}

