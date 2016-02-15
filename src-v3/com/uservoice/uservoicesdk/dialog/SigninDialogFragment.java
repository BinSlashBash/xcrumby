package com.uservoice.uservoicesdk.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.uservoice.uservoicesdk.C0621R;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.babayaga.Babayaga.Event;
import com.uservoice.uservoicesdk.flow.SigninCallback;
import com.uservoice.uservoicesdk.model.AccessToken;
import com.uservoice.uservoicesdk.model.AccessTokenResult;
import com.uservoice.uservoicesdk.model.RequestToken;
import com.uservoice.uservoicesdk.model.User;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestResult;
import com.uservoice.uservoicesdk.ui.DefaultCallback;
import com.uservoice.uservoicesdk.ui.Utils;

@SuppressLint({"ValidFragment"})
public class SigninDialogFragment extends DialogFragmentBugfixed {
    private SigninCallback callback;
    private String email;
    private EditText emailField;
    private Button forgotPassword;
    private String name;
    private EditText nameField;
    private EditText passwordField;
    private View passwordFields;
    private Runnable requestTokenCallback;

    /* renamed from: com.uservoice.uservoicesdk.dialog.SigninDialogFragment.2 */
    class C06402 implements OnClickListener {
        C06402() {
        }

        public void onClick(View v) {
            SigninDialogFragment.this.sendForgotPassword();
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.dialog.SigninDialogFragment.3 */
    class C06413 implements OnFocusChangeListener {
        C06413() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (v == SigninDialogFragment.this.emailField && !hasFocus) {
                SigninDialogFragment.this.discoverUser();
            }
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.dialog.SigninDialogFragment.4 */
    class C06434 implements OnShowListener {
        final /* synthetic */ AlertDialog val$dialog;

        /* renamed from: com.uservoice.uservoicesdk.dialog.SigninDialogFragment.4.1 */
        class C06421 implements OnClickListener {
            C06421() {
            }

            public void onClick(View v) {
                SigninDialogFragment.this.signIn();
            }
        }

        C06434(AlertDialog alertDialog) {
            this.val$dialog = alertDialog;
        }

        public void onShow(DialogInterface di) {
            this.val$dialog.getButton(-1).setOnClickListener(new C06421());
            ((InputMethodManager) SigninDialogFragment.this.getActivity().getSystemService("input_method")).showSoftInput(SigninDialogFragment.this.emailField, 1);
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.dialog.SigninDialogFragment.6 */
    class C06446 implements Runnable {
        final /* synthetic */ Activity val$activity;

        /* renamed from: com.uservoice.uservoicesdk.dialog.SigninDialogFragment.6.2 */
        class C11742 extends Callback<AccessToken> {

            /* renamed from: com.uservoice.uservoicesdk.dialog.SigninDialogFragment.6.2.1 */
            class C14381 extends DefaultCallback<User> {
                C14381(Context x0) {
                    super(x0);
                }

                public void onModel(User model) {
                    Session.getInstance().setUser(model);
                    Babayaga.track(Event.AUTHENTICATE);
                    SigninDialogFragment.this.dismiss();
                    SigninDialogFragment.this.callback.onSuccess();
                }
            }

            C11742() {
            }

            public void onModel(AccessToken accessToken) {
                Session.getInstance().setAccessToken(C06446.this.val$activity, accessToken);
                User.loadCurrentUser(new C14381(SigninDialogFragment.this.getActivity()));
            }

            public void onError(RestResult error) {
                Toast.makeText(C06446.this.val$activity, C0621R.string.uv_failed_signin_error, 0).show();
            }
        }

        /* renamed from: com.uservoice.uservoicesdk.dialog.SigninDialogFragment.6.1 */
        class C14371 extends DefaultCallback<AccessTokenResult<User>> {
            C14371(Context x0) {
                super(x0);
            }

            public void onModel(AccessTokenResult<User> model) {
                Session.getInstance().setUser((User) model.getModel());
                Session.getInstance().setAccessToken(C06446.this.val$activity, model.getAccessToken());
                Babayaga.track(Event.AUTHENTICATE);
                SigninDialogFragment.this.dismiss();
                SigninDialogFragment.this.callback.onSuccess();
            }
        }

        C06446(Activity activity) {
            this.val$activity = activity;
        }

        public void run() {
            if (SigninDialogFragment.this.nameField.getVisibility() == 0) {
                User.findOrCreate(SigninDialogFragment.this.emailField.getText().toString(), SigninDialogFragment.this.nameField.getText().toString(), new C14371(SigninDialogFragment.this.getActivity()));
            } else {
                AccessToken.authorize(SigninDialogFragment.this.emailField.getText().toString(), SigninDialogFragment.this.passwordField.getText().toString(), new C11742());
            }
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.dialog.SigninDialogFragment.5 */
    class C11735 extends Callback<User> {
        C11735() {
        }

        public void onModel(User model) {
            SigninDialogFragment.this.passwordFields.setVisibility(0);
            SigninDialogFragment.this.nameField.setVisibility(8);
            SigninDialogFragment.this.passwordField.requestFocus();
        }

        public void onError(RestResult error) {
            SigninDialogFragment.this.passwordFields.setVisibility(8);
            SigninDialogFragment.this.nameField.setVisibility(0);
            SigninDialogFragment.this.nameField.requestFocus();
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.dialog.SigninDialogFragment.1 */
    class C14361 extends DefaultCallback<RequestToken> {
        C14361(Context x0) {
            super(x0);
        }

        public void onModel(RequestToken requestToken) {
            Session.getInstance().setRequestToken(requestToken);
            if (SigninDialogFragment.this.requestTokenCallback != null) {
                SigninDialogFragment.this.requestTokenCallback.run();
            }
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.dialog.SigninDialogFragment.7 */
    class C14397 extends DefaultCallback<User> {
        final /* synthetic */ Activity val$activity;

        C14397(Context x0, Activity activity) {
            this.val$activity = activity;
            super(x0);
        }

        public void onModel(User model) {
            Toast.makeText(this.val$activity, C0621R.string.uv_msg_forgot_password, 0).show();
        }
    }

    public SigninDialogFragment(String email, String name, SigninCallback callback) {
        this.email = email;
        this.name = name;
        this.callback = callback;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        RequestToken.getRequestToken(new C14361(getActivity()));
        Builder builder = new Builder(getActivity());
        if (!Utils.isDarkTheme(getActivity())) {
            builder.setInverseBackgroundForced(true);
        }
        builder.setTitle(C0621R.string.uv_signin_dialog_title);
        View view = getActivity().getLayoutInflater().inflate(C0621R.layout.uv_signin_layout, null);
        this.emailField = (EditText) view.findViewById(C0621R.id.uv_signin_email);
        this.nameField = (EditText) view.findViewById(C0621R.id.uv_signin_name);
        this.passwordField = (EditText) view.findViewById(C0621R.id.uv_signin_password);
        this.passwordFields = view.findViewById(C0621R.id.uv_signin_password_fields);
        this.forgotPassword = (Button) view.findViewById(C0621R.id.uv_signin_forgot_password);
        this.passwordFields.setVisibility(8);
        this.emailField.setText(this.email);
        this.nameField.setText(this.name);
        if (this.email != null) {
            discoverUser();
        }
        this.forgotPassword.setOnClickListener(new C06402());
        this.emailField.setOnFocusChangeListener(new C06413());
        builder.setView(view);
        builder.setNegativeButton(17039360, null);
        builder.setPositiveButton(C0621R.string.uv_signin_dialog_ok, null);
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new C06434(dialog));
        dialog.getWindow().setSoftInputMode(5);
        return dialog;
    }

    private void discoverUser() {
        User.discover(this.emailField.getText().toString(), new C11735());
    }

    private void signIn() {
        Runnable runnable = new C06446(getActivity());
        if (Session.getInstance().getRequestToken() != null) {
            runnable.run();
        } else {
            this.requestTokenCallback = runnable;
        }
    }

    private void sendForgotPassword() {
        User.sendForgotPassword(this.emailField.getText().toString(), new C14397(getActivity(), getActivity()));
    }
}
