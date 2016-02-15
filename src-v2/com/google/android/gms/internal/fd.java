/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ActivityNotFoundException
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

public class fd
implements DialogInterface.OnClickListener {
    private final int CV;
    private final Intent mIntent;
    private final Activity nS;

    public fd(Activity activity, Intent intent, int n2) {
        this.nS = activity;
        this.mIntent = intent;
        this.CV = n2;
    }

    public void onClick(DialogInterface dialogInterface, int n2) {
        try {
            if (this.mIntent != null) {
                this.nS.startActivityForResult(this.mIntent, this.CV);
            }
            dialogInterface.dismiss();
            return;
        }
        catch (ActivityNotFoundException var1_2) {
            Log.e((String)"SettingsRedirect", (String)"Can't redirect to app settings for Google Play services");
            return;
        }
    }
}

