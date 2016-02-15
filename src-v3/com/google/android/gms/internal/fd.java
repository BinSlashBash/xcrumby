package com.google.android.gms.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.util.Log;

public class fd implements OnClickListener {
    private final int CV;
    private final Intent mIntent;
    private final Activity nS;

    public fd(Activity activity, Intent intent, int i) {
        this.nS = activity;
        this.mIntent = intent;
        this.CV = i;
    }

    public void onClick(DialogInterface dialog, int which) {
        try {
            if (this.mIntent != null) {
                this.nS.startActivityForResult(this.mIntent, this.CV);
            }
            dialog.dismiss();
        } catch (ActivityNotFoundException e) {
            Log.e("SettingsRedirect", "Can't redirect to app settings for Google Play services");
        }
    }
}
