package com.uservoice.uservoicesdk.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public abstract class DialogFragmentBugfixed extends DialogFragment {
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setOnDismissListener(null);
        }
        super.onDestroyView();
    }
}
