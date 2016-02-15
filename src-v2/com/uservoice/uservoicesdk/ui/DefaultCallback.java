/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.util.Log
 */
package com.uservoice.uservoicesdk.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import com.uservoice.uservoicesdk.R;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestResult;

public abstract class DefaultCallback<T>
extends Callback<T> {
    private static final String TAG = "com.uservoice.uservoicesdk";
    private final Context context;

    public DefaultCallback(Context context) {
        this.context = context;
    }

    @Override
    public void onError(RestResult restResult) {
        Log.e((String)"com.uservoice.uservoicesdk", (String)restResult.getMessage());
        try {
            new AlertDialog.Builder(this.context).setTitle(R.string.uv_network_error).show();
            return;
        }
        catch (Exception var1_2) {
            Log.e((String)"com.uservoice.uservoicesdk", (String)("Failed trying to show alert: " + var1_2.getMessage()));
            return;
        }
    }
}

