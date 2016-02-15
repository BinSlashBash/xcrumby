/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.IntentService
 *  android.content.Context
 *  android.content.Intent
 */
package com.google.android.gms.tagmanager;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.analytics.CampaignTrackingService;
import com.google.android.gms.tagmanager.ay;

public final class InstallReferrerService
extends IntentService {
    CampaignTrackingService Yi;
    Context Yj;

    public InstallReferrerService() {
        super("InstallReferrerService");
    }

    public InstallReferrerService(String string2) {
        super(string2);
    }

    private void a(Context context, Intent intent) {
        if (this.Yi == null) {
            this.Yi = new CampaignTrackingService();
        }
        this.Yi.processIntent(context, intent);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onHandleIntent(Intent intent) {
        String string2 = intent.getStringExtra("referrer");
        Context context = this.Yj != null ? this.Yj : this.getApplicationContext();
        ay.c(context, string2);
        this.a(context, intent);
    }
}

