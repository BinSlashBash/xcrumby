/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.net.Uri
 *  android.net.Uri$Builder
 */
package com.google.android.gms.internal;

import android.content.Intent;
import android.net.Uri;

public class fi {
    private static final Uri DF = Uri.parse((String)"http://plus.google.com/");
    private static final Uri DG = DF.buildUpon().appendPath("circles").appendPath("find").build();

    public static Intent am(String string2) {
        string2 = Uri.fromParts((String)"package", (String)string2, (String)null);
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData((Uri)string2);
        return intent;
    }

    private static Uri an(String string2) {
        return Uri.parse((String)"market://details").buildUpon().appendQueryParameter("id", string2).build();
    }

    public static Intent ao(String string2) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(fi.an(string2));
        intent.setPackage("com.android.vending");
        intent.addFlags(524288);
        return intent;
    }

    public static Intent eS() {
        return new Intent("android.settings.DATE_SETTINGS");
    }
}

