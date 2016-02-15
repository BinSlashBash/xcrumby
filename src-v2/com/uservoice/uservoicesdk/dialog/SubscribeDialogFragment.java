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
import com.uservoice.uservoicesdk.activity.InstantAnswersActivity;
import com.uservoice.uservoicesdk.deflection.Deflection;
import com.uservoice.uservoicesdk.dialog.DialogFragmentBugfixed;
import com.uservoice.uservoicesdk.dialog.SuggestionDialogFragment;
import com.uservoice.uservoicesdk.flow.SigninCallback;
import com.uservoice.uservoicesdk.flow.SigninManager;
import com.uservoice.uservoicesdk.model.Suggestion;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.ui.DefaultCallback;
import com.uservoice.uservoicesdk.ui.Utils;

@SuppressLint(value={"ValidFragment"})
public class SubscribeDialogFragment
extends DialogFragmentBugfixed {
    private final String deflectingType;
    private final Suggestion suggestion;
    private final SuggestionDialogFragment suggestionDialog;

    public SubscribeDialogFragment(Suggestion suggestion, SuggestionDialogFragment suggestionDialogFragment, String string2) {
        this.suggestion = suggestion;
        this.suggestionDialog = suggestionDialogFragment;
        this.deflectingType = string2;
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        bundle = new AlertDialog.Builder((Context)this.getActivity());
        bundle.setTitle(R.string.uv_subscribe_dialog_title);
        if (!Utils.isDarkTheme((Context)this.getActivity())) {
            bundle.setInverseBackgroundForced(true);
        }
        View view = this.getActivity().getLayoutInflater().inflate(R.layout.uv_subscribe_dialog, null);
        final EditText editText = (EditText)view.findViewById(R.id.uv_email);
        editText.setText((CharSequence)Session.getInstance().getEmail());
        bundle.setView(view);
        bundle.setNegativeButton(R.string.uv_nevermind, null);
        bundle.setPositiveButton(R.string.uv_subscribe, new DialogInterface.OnClickListener(){

            public void onClick(final DialogInterface dialogInterface, int n2) {
                Session.getInstance().persistIdentity(Session.getInstance().getName(), editText.getText().toString());
                SigninManager.signinForSubscribe(SubscribeDialogFragment.this.getActivity(), Session.getInstance().getEmail(), new SigninCallback(){

                    @Override
                    public void onSuccess() {
                        SubscribeDialogFragment.this.suggestion.subscribe(new DefaultCallback<Suggestion>((Context)SubscribeDialogFragment.this.getActivity()){

                            @Override
                            public void onModel(Suggestion suggestion) {
                                if (SubscribeDialogFragment.this.getActivity() instanceof InstantAnswersActivity) {
                                    Deflection.trackDeflection("subscribed", SubscribeDialogFragment.this.deflectingType, suggestion);
                                }
                                SubscribeDialogFragment.this.suggestionDialog.suggestionSubscriptionUpdated(suggestion);
                                dialogInterface.dismiss();
                            }
                        });
                    }

                });
            }

        });
        bundle = bundle.create();
        bundle.getWindow().setSoftInputMode(5);
        return bundle;
    }

}

