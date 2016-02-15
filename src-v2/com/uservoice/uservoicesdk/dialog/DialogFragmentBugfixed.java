/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnDismissListener
 *  android.os.Bundle
 */
package com.uservoice.uservoicesdk.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public abstract class DialogFragmentBugfixed
extends DialogFragment {
    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.setRetainInstance(true);
    }

    @Override
    public void onDestroyView() {
        if (this.getDialog() != null && this.getRetainInstance()) {
            this.getDialog().setOnDismissListener(null);
        }
        super.onDestroyView();
    }
}

