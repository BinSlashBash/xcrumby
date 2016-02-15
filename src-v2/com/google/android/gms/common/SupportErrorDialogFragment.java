/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.os.Bundle
 */
package com.google.android.gms.common;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import com.google.android.gms.internal.fq;

public class SupportErrorDialogFragment
extends DialogFragment {
    private DialogInterface.OnCancelListener Ai = null;
    private Dialog mDialog = null;

    public static SupportErrorDialogFragment newInstance(Dialog dialog) {
        return SupportErrorDialogFragment.newInstance(dialog, null);
    }

    public static SupportErrorDialogFragment newInstance(Dialog dialog, DialogInterface.OnCancelListener onCancelListener) {
        SupportErrorDialogFragment supportErrorDialogFragment = new SupportErrorDialogFragment();
        dialog = fq.b(dialog, (Object)"Cannot display null dialog");
        dialog.setOnCancelListener(null);
        dialog.setOnDismissListener(null);
        supportErrorDialogFragment.mDialog = dialog;
        if (onCancelListener != null) {
            supportErrorDialogFragment.Ai = onCancelListener;
        }
        return supportErrorDialogFragment;
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        if (this.Ai != null) {
            this.Ai.onCancel(dialogInterface);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        return this.mDialog;
    }

    @Override
    public void show(FragmentManager fragmentManager, String string2) {
        super.show(fragmentManager, string2);
    }
}

