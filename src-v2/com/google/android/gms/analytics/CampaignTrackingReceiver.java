/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 */
package com.google.android.gms.analytics;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.analytics.CampaignTrackingService;

public final class CampaignTrackingReceiver
extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        String string2 = intent.getStringExtra("referrer");
        if (!"com.android.vending.INSTALL_REFERRER".equals(intent.getAction()) || string2 == null) {
            return;
        }
        intent = new Intent(context, (Class)CampaignTrackingService.class);
        intent.putExtra("referrer", string2);
        context.startService(intent);
    }
}

