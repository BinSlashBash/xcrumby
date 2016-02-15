package com.uservoice.uservoicesdk.ui;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.util.Log;
import com.uservoice.uservoicesdk.C0621R;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestResult;

public abstract class DefaultCallback<T> extends Callback<T> {
    private static final String TAG = "com.uservoice.uservoicesdk";
    private final Context context;

    public DefaultCallback(Context context) {
        this.context = context;
    }

    public void onError(RestResult error) {
        Log.e(TAG, error.getMessage());
        try {
            new Builder(this.context).setTitle(C0621R.string.uv_network_error).show();
        } catch (Exception e) {
            Log.e(TAG, "Failed trying to show alert: " + e.getMessage());
        }
    }
}
