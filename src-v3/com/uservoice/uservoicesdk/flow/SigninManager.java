package com.uservoice.uservoicesdk.flow;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import com.uservoice.uservoicesdk.C0621R;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.babayaga.Babayaga.Event;
import com.uservoice.uservoicesdk.dialog.PasswordDialogFragment;
import com.uservoice.uservoicesdk.dialog.SigninDialogFragment;
import com.uservoice.uservoicesdk.model.AccessTokenResult;
import com.uservoice.uservoicesdk.model.RequestToken;
import com.uservoice.uservoicesdk.model.User;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestResult;
import com.uservoice.uservoicesdk.ui.DefaultCallback;
import java.util.regex.Pattern;

public class SigninManager {
    private final FragmentActivity activity;
    private final SigninCallback callback;
    private String email;
    private Pattern emailFormat;
    private String name;
    private boolean passwordOnly;

    /* renamed from: com.uservoice.uservoicesdk.flow.SigninManager.1 */
    class C11771 extends Callback<User> {
        C11771() {
        }

        public void onModel(User model) {
            SigninManager.this.promptToSignIn();
        }

        public void onError(RestResult error) {
            SigninManager.this.createUser();
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.flow.SigninManager.2 */
    class C14472 extends DefaultCallback<RequestToken> {

        /* renamed from: com.uservoice.uservoicesdk.flow.SigninManager.2.1 */
        class C14461 extends DefaultCallback<AccessTokenResult<User>> {
            C14461(Context x0) {
                super(x0);
            }

            public void onModel(AccessTokenResult<User> model) {
                Session.getInstance().setUser((User) model.getModel());
                Session.getInstance().setAccessToken(SigninManager.this.activity, model.getAccessToken());
                Babayaga.track(Event.IDENTIFY);
                SigninManager.this.callback.onSuccess();
            }
        }

        C14472(Context x0) {
            super(x0);
        }

        public void onModel(RequestToken model) {
            Session.getInstance().setRequestToken(model);
            User.findOrCreate(SigninManager.this.email, SigninManager.this.name, new C14461(SigninManager.this.activity));
        }
    }

    public static void signIn(FragmentActivity activity, SigninCallback callback) {
        new SigninManager(activity, null, null, callback).signIn();
    }

    public static void signIn(FragmentActivity activity, String email, String name, SigninCallback callback) {
        new SigninManager(activity, email, name, callback).signIn();
    }

    private SigninManager(FragmentActivity activity, String email, String name, SigninCallback callback) {
        this.emailFormat = Pattern.compile("\\A(\\w[-+.\\w!\\#\\$%&'\\*\\+\\-/=\\?\\^_`\\{\\|\\}~]*@([-\\w]*\\.)+[a-zA-Z]{2,9})\\z");
        this.activity = activity;
        if (email == null || email.trim().length() == 0) {
            email = null;
        }
        this.email = email;
        if (name == null || name.trim().length() == 0) {
            name = null;
        }
        this.name = name;
        this.callback = callback;
    }

    private void signIn() {
        User currentUser = Session.getInstance().getUser();
        if (currentUser != null && (this.email == null || this.email.equals(currentUser.getEmail()))) {
            this.callback.onSuccess();
        } else if (Session.getInstance().getAccessToken() != null) {
            this.callback.onSuccess();
        } else if (this.email == null || this.emailFormat.matcher(this.email).matches()) {
            this.email = this.email == null ? Session.getInstance().getEmail() : this.email;
            this.name = this.name == null ? Session.getInstance().getName() : this.name;
            if (this.email != null) {
                User.discover(this.email, new C11771());
            } else {
                promptToSignIn();
            }
        } else {
            Toast.makeText(this.activity, C0621R.string.uv_msg_bad_email_format, 0).show();
            this.callback.onFailure();
        }
    }

    private void createUser() {
        RequestToken.getRequestToken(new C14472(this.activity));
    }

    private void promptToSignIn() {
        if (this.passwordOnly) {
            new PasswordDialogFragment(this.callback).show(this.activity.getSupportFragmentManager(), "PasswordDialogFragment");
        } else {
            new SigninDialogFragment(this.email, this.name, this.callback).show(this.activity.getSupportFragmentManager(), "SigninDialogFragment");
        }
    }

    public void setPasswordOnly(boolean passwordOnly) {
        this.passwordOnly = passwordOnly;
    }

    public static void signinForSubscribe(FragmentActivity activity, String email, SigninCallback callback) {
        SigninManager manager = new SigninManager(activity, email, Session.getInstance().getName(), callback);
        manager.setPasswordOnly(true);
        manager.signIn();
    }
}
