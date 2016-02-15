/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.Bundle
 */
package com.google.android.gms.tagmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import com.google.android.gms.tagmanager.cw;

class bn
extends BroadcastReceiver {
    static final String sD = bn.class.getName();
    private final cw YE;

    bn(cw cw2) {
        this.YE = cw2;
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
    public void onReceive(Context object, Intent object2) {
        object = object2.getAction();
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(object)) {
            Bundle bundle = object2.getExtras();
            object = Boolean.FALSE;
            if (bundle != null) {
                object = object2.getExtras().getBoolean("noConnectivity");
            }
            object2 = this.YE;
            boolean bl2 = !object.booleanValue();
            object2.s(bl2);
            return;
        } else {
            if (!"com.google.analytics.RADIO_POWERED".equals(object) || object2.hasExtra(sD)) return;
            {
                this.YE.cm();
                return;
            }
        }
    }
}

