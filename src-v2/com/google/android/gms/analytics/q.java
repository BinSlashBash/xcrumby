/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 */
package com.google.android.gms.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.google.android.gms.analytics.af;

class q
extends BroadcastReceiver {
    static final String sD = q.class.getName();
    private final af sE;

    q(af af2) {
        this.sE = af2;
    }

    public static void p(Context context) {
        Intent intent = new Intent("com.google.analytics.RADIO_POWERED");
        intent.addCategory(context.getPackageName());
        intent.putExtra(sD, true);
        context.sendBroadcast(intent);
    }

    public void o(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver((BroadcastReceiver)this, intentFilter);
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.google.analytics.RADIO_POWERED");
        intentFilter.addCategory(context.getPackageName());
        context.registerReceiver((BroadcastReceiver)this, intentFilter);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onReceive(Context object, Intent intent) {
        boolean bl2 = false;
        object = intent.getAction();
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(object)) {
            boolean bl3 = intent.getBooleanExtra("noConnectivity", false);
            object = this.sE;
            if (!bl3) {
                bl2 = true;
            }
            object.s(bl2);
            return;
        } else {
            if (!"com.google.analytics.RADIO_POWERED".equals(object) || intent.hasExtra(sD)) return;
            {
                this.sE.cm();
                return;
            }
        }
    }
}

