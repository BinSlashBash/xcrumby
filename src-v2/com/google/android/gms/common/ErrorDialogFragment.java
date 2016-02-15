/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.app.DialogFragment
 *  android.app.FragmentManager
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.os.Bundle
 */
package com.google.android.gms.common;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import com.google.android.gms.internal.fq;

public class ErrorDialogFragment
extends DialogFragment {
    private DialogInterface.OnCancelListener Ai = null;
    private Dialog mDialog = null;

    public static ErrorDialogFragment newInstance(Dialog dialog) {
        return ErrorDialogFragment.newInstance(dialog, null);
    }

    public static ErrorDialogFragment newInstance(Dialog dialog, DialogInterface.OnCancelListener onCancelListener) {
        ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment();
        dialog = fq.b(dialog, (Object)"Cannot display null dialog");
        dialog.setOnCancelListener(null);
        dialog.setOnDismissListener(null);
        errorDialogFragment.mDialog = dialog;
        if (onCancelListener != null) {
            errorDialogFragment.Ai = onCancelListener;
        }
        return errorDialogFragment;
    }

    public void onCancel(DialogInterface dialogInterface) {
        if (this.Ai != null) {
            this.Ai.onCancel(dialogInterface);
        }
    }

    public Dialog onCreateDialog(Bundle bundle) {
        return this.mDialog;
    }

    public void show(FragmentManager fragmentManager, String string2) {
        super.show(fragmentManager, string2);
    }
}

