/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.net.Uri
 *  android.os.Bundle
 */
package com.google.android.gms.tagmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.gms.tagmanager.TagManager;
import com.google.android.gms.tagmanager.bh;

public class PreviewActivity
extends Activity {
    private void b(String string2, String string3, String string4) {
        AlertDialog alertDialog = new AlertDialog.Builder((Context)this).create();
        alertDialog.setTitle((CharSequence)string2);
        alertDialog.setMessage((CharSequence)string3);
        alertDialog.setButton(-1, (CharSequence)string4, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n2) {
            }
        });
        alertDialog.show();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onCreate(Bundle object) {
        try {
            super.onCreate((Bundle)object);
            bh.x("Preview activity");
            object = this.getIntent().getData();
            if (!TagManager.getInstance((Context)this).g((Uri)object)) {
                object = "Cannot preview the app with the uri: " + object + ". Launching current version instead.";
                bh.z((String)object);
                this.b("Preview failure", (String)object, "Continue");
            }
            if ((object = this.getPackageManager().getLaunchIntentForPackage(this.getPackageName())) != null) {
                bh.x("Invoke the launch activity for package name: " + this.getPackageName());
                this.startActivity((Intent)object);
                return;
            }
            bh.x("No launch activity found for package name: " + this.getPackageName());
            return;
        }
        catch (Exception var1_2) {
            bh.w("Calling preview threw an exception: " + var1_2.getMessage());
            return;
        }
    }

}

