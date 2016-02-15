/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.DialogInterface$OnShowListener
 *  android.os.Bundle
 *  android.text.Editable
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnFocusChangeListener
 *  android.view.ViewGroup
 *  android.view.Window
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.Toast
 */
package com.uservoice.uservoicesdk.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.uservoice.uservoicesdk.R;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.dialog.DialogFragmentBugfixed;
import com.uservoice.uservoicesdk.flow.SigninCallback;
import com.uservoice.uservoicesdk.model.AccessToken;
import com.uservoice.uservoicesdk.model.AccessTokenResult;
import com.uservoice.uservoicesdk.model.RequestToken;
import com.uservoice.uservoicesdk.model.User;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestResult;
import com.uservoice.uservoicesdk.ui.DefaultCallback;
import com.uservoice.uservoicesdk.ui.Utils;

@SuppressLint(value={"ValidFragment"})
public class SigninDialogFragment
extends DialogFragmentBugfixed {
    private SigninCallback callback;
    private String email;
    private EditText emailField;
    private Button forgotPassword;
    private String name;
    private EditText nameField;
    private EditText passwordField;
    private View passwordFields;
    private Runnable requestTokenCallback;

    public SigninDialogFragment() {
    }

    public SigninDialogFragment(String string2, String string3, SigninCallback signinCallback) {
        this.email = string2;
        this.name = string3;
        this.callback = signinCallback;
    }

    private void discoverUser() {
        User.discover(this.emailField.getText().toString(), new Callback<User>(){

            @Override
            public void onError(RestResult restResult) {
                SigninDialogFragment.this.passwordFields.setVisibility(8);
                SigninDialogFragment.this.nameField.setVisibility(0);
                SigninDialogFragment.this.nameField.requestFocus();
            }

            @Override
            public void onModel(User user) {
                SigninDialogFragment.this.passwordFields.setVisibility(0);
                SigninDialogFragment.this.nameField.setVisibility(8);
                SigninDialogFragment.this.passwordField.requestFocus();
            }
        });
    }

    private void sendForgotPassword() {
        final FragmentActivity fragmentActivity = this.getActivity();
        User.sendForgotPassword(this.emailField.getText().toString(), new DefaultCallback<User>((Context)this.getActivity()){

            @Override
            public void onModel(User user) {
                Toast.makeText((Context)fragmentActivity, (int)R.string.uv_msg_forgot_password, (int)0).show();
            }
        });
    }

    private void signIn() {
        Runnable runnable = new Runnable(this.getActivity()){
            final /* synthetic */ Activity val$activity;

            @Override
            public void run() {
                if (SigninDialogFragment.this.nameField.getVisibility() == 0) {
                    User.findOrCreate(SigninDialogFragment.this.emailField.getText().toString(), SigninDialogFragment.this.nameField.getText().toString(), new DefaultCallback<AccessTokenResult<User>>((Context)SigninDialogFragment.this.getActivity()){

                        @Override
                        public void onModel(AccessTokenResult<User> accessTokenResult) {
                            Session.getInstance().setUser(accessTokenResult.getModel());
                            Session.getInstance().setAccessToken((Context)6.this.val$activity, accessTokenResult.getAccessToken());
                            Babayaga.track(Babayaga.Event.AUTHENTICATE);
                            SigninDialogFragment.this.dismiss();
                            SigninDialogFragment.this.callback.onSuccess();
                        }
                    });
                    return;
                }
                AccessToken.authorize(SigninDialogFragment.this.emailField.getText().toString(), SigninDialogFragment.this.passwordField.getText().toString(), new Callback<AccessToken>(){

                    @Override
                    public void onError(RestResult restResult) {
                        Toast.makeText((Context)6.this.val$activity, (int)R.string.uv_failed_signin_error, (int)0).show();
                    }

                    @Override
                    public void onModel(AccessToken accessToken) {
                        Session.getInstance().setAccessToken((Context)6.this.val$activity, accessToken);
                        User.loadCurrentUser(new DefaultCallback<User>((Context)SigninDialogFragment.this.getActivity()){

                            @Override
                            public void onModel(User user) {
                                Session.getInstance().setUser(user);
                                Babayaga.track(Babayaga.Event.AUTHENTICATE);
                                SigninDialogFragment.this.dismiss();
                                SigninDialogFragment.this.callback.onSuccess();
                            }
                        });
                    }

                });
            }

        };
        if (Session.getInstance().getRequestToken() != null) {
            runnable.run();
            return;
        }
        this.requestTokenCallback = runnable;
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        RequestToken.getRequestToken(new DefaultCallback<RequestToken>((Context)this.getActivity()){

            @Override
            public void onModel(RequestToken requestToken) {
                Session.getInstance().setRequestToken(requestToken);
                if (SigninDialogFragment.this.requestTokenCallback != null) {
                    SigninDialogFragment.this.requestTokenCallback.run();
                }
            }
        });
        bundle = new AlertDialog.Builder((Context)this.getActivity());
        if (!Utils.isDarkTheme((Context)this.getActivity())) {
            bundle.setInverseBackgroundForced(true);
        }
        bundle.setTitle(R.string.uv_signin_dialog_title);
        View view = this.getActivity().getLayoutInflater().inflate(R.layout.uv_signin_layout, null);
        this.emailField = (EditText)view.findViewById(R.id.uv_signin_email);
        this.nameField = (EditText)view.findViewById(R.id.uv_signin_name);
        this.passwordField = (EditText)view.findViewById(R.id.uv_signin_password);
        this.passwordFields = view.findViewById(R.id.uv_signin_password_fields);
        this.forgotPassword = (Button)view.findViewById(R.id.uv_signin_forgot_password);
        this.passwordFields.setVisibility(8);
        this.emailField.setText((CharSequence)this.email);
        this.nameField.setText((CharSequence)this.name);
        if (this.email != null) {
            this.discoverUser();
        }
        this.forgotPassword.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                SigninDialogFragment.this.sendForgotPassword();
            }
        });
        this.emailField.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            public void onFocusChange(View view, boolean bl2) {
                if (view == SigninDialogFragment.this.emailField && !bl2) {
                    SigninDialogFragment.this.discoverUser();
                }
            }
        });
        bundle.setView(view);
        bundle.setNegativeButton(17039360, null);
        bundle.setPositiveButton(R.string.uv_signin_dialog_ok, null);
        bundle = bundle.create();
        bundle.setOnShowListener(new DialogInterface.OnShowListener((AlertDialog)bundle){
            final /* synthetic */ AlertDialog val$dialog;

            public void onShow(DialogInterface dialogInterface) {
                this.val$dialog.getButton(-1).setOnClickListener(new View.OnClickListener(){

                    public void onClick(View view) {
                        SigninDialogFragment.this.signIn();
                    }
                });
                ((InputMethodManager)SigninDialogFragment.this.getActivity().getSystemService("input_method")).showSoftInput((View)SigninDialogFragment.this.emailField, 1);
            }

        });
        bundle.getWindow().setSoftInputMode(5);
        return bundle;
    }

}

