package com.google.android.gms.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public final class CampaignTrackingReceiver extends BroadcastReceiver {
    public void onReceive(Context ctx, Intent intent) {
        String stringExtra = intent.getStringExtra("referrer");
        if ("com.android.vending.INSTALL_REFERRER".equals(intent.getAction()) && stringExtra != null) {
            Intent intent2 = new Intent(ctx, CampaignTrackingService.class);
            intent2.putExtra("referrer", stringExtra);
            ctx.startService(intent2);
        }
    }
}
