/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.ActivityNotFoundException
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.internal.cb;
import com.google.android.gms.internal.ci;
import com.google.android.gms.internal.dw;

public final class bz {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean a(Context context, cb cb2, ci ci2) {
        if (cb2 == null) {
            dw.z("No intent data for launcher overlay.");
            return false;
        }
        Intent intent = new Intent();
        if (TextUtils.isEmpty((CharSequence)cb2.nO)) {
            dw.z("Open GMSG did not contain a URL.");
            return false;
        }
        if (!TextUtils.isEmpty((CharSequence)cb2.mimeType)) {
            intent.setDataAndType(Uri.parse((String)cb2.nO), cb2.mimeType);
        } else {
            intent.setData(Uri.parse((String)cb2.nO));
        }
        intent.setAction("android.intent.action.VIEW");
        if (!TextUtils.isEmpty((CharSequence)cb2.packageName)) {
            intent.setPackage(cb2.packageName);
        }
        if (!TextUtils.isEmpty((CharSequence)cb2.nP)) {
            String[] arrstring = cb2.nP.split("/", 2);
            if (arrstring.length < 2) {
                dw.z("Could not parse component name from open GMSG: " + cb2.nP);
                return false;
            }
            intent.setClassName(arrstring[0], arrstring[1]);
        }
        try {
            dw.y("Launching an intent: " + (Object)intent);
            context.startActivity(intent);
            ci2.U();
            return true;
        }
        catch (ActivityNotFoundException var0_1) {
            dw.z(var0_1.getMessage());
            return false;
        }
    }
}

