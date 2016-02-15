/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.os.Bundle
 *  android.text.Editable
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.Window
 *  android.widget.EditText
 */
package com.uservoice.uservoicesdk.dialog;

import android.annotation.SuppressLint;
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
import android.widget.EditText;
import com.uservoice.uservoicesdk.R;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.dialog.DialogFragmentBugfixed;
import com.uservoice.uservoicesdk.flow.SigninCallback;
import com.uservoice.uservoicesdk.model.AccessToken;
import com.uservoice.uservoicesdk.model.RequestToken;
import com.uservoice.uservoicesdk.ui.DefaultCallback;
import com.uservoice.uservoicesdk.ui.Utils;

@SuppressLint(value={"ValidFragment"})
public class PasswordDialogFragment
extends DialogFragmentBugfixed {
    private final SigninCallback callback;
    private EditText password;

    public PasswordDialogFragment(SigninCallback signinCallback) {
        this.callback = signinCallback;
    }

    private void authorize() {
        AccessToken.authorize(Session.getInstance().getEmail(), this.password.getText().toString(), new DefaultCallback<AccessToken>((Context)this.getActivity()){

            @Override
            public void onModel(AccessToken accessToken) {
                Session.getInstance().setAccessToken(accessToken);
                PasswordDialogFragment.this.callback.onSuccess();
            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        bundle = new AlertDialog.Builder((Context)this.getActivity());
        bundle.setTitle(R.string.uv_password_dialog_title);
        if (!Utils.isDarkTheme((Context)this.getActivity())) {
            bundle.setInverseBackgroundForced(true);
        }
        View view = this.getActivity().getLayoutInflater().inflate(R.layout.uv_password_dialog, null);
        this.password = (EditText)view.findViewById(R.id.uv_password);
        bundle.setView(view);
        bundle.setNegativeButton(R.string.uv_cancel, null);
        bundle.setPositiveButton(17039370, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n2) {
                if (Session.getInstance().getRequestToken() != null) {
                    PasswordDialogFragment.this.authorize();
                    return;
                }
                RequestToken.getRequestToken(new DefaultCallback<RequestToken>((Context)PasswordDialogFragment.this.getActivity()){

                    @Override
                    public void onModel(RequestToken requestToken) {
                        Session.getInstance().setRequestToken(requestToken);
                        PasswordDialogFragment.this.authorize();
                    }
                });
            }

        });
        bundle = bundle.create();
        bundle.getWindow().setSoftInputMode(5);
        return bundle;
    }

}

