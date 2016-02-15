/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.os.Bundle
 */
package com.uservoice.uservoicesdk.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.uservoice.uservoicesdk.R;
import com.uservoice.uservoicesdk.activity.ContactActivity;
import com.uservoice.uservoicesdk.dialog.DialogFragmentBugfixed;
import com.uservoice.uservoicesdk.ui.Utils;

public class UnhelpfulDialogFragment
extends DialogFragmentBugfixed {
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        bundle = new AlertDialog.Builder((Context)this.getActivity());
        if (!Utils.isDarkTheme((Context)this.getActivity())) {
            bundle.setInverseBackgroundForced(true);
        }
        bundle.setTitle(R.string.uv_unhelpful_article_message_question);
        bundle.setNegativeButton(R.string.uv_no, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n2) {
                dialogInterface.cancel();
            }
        });
        bundle.setPositiveButton(R.string.uv_yes, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n2) {
                UnhelpfulDialogFragment.this.getActivity().startActivity(new Intent((Context)UnhelpfulDialogFragment.this.getActivity(), (Class)ContactActivity.class));
                dialogInterface.cancel();
            }
        });
        return bundle.create();
    }

}

