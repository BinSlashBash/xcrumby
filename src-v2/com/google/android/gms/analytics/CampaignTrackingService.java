/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.IntentService
 *  android.content.Context
 *  android.content.Intent
 */
package com.google.android.gms.analytics;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.analytics.aa;
import java.io.FileOutputStream;
import java.io.IOException;

public class CampaignTrackingService
extends IntentService {
    public CampaignTrackingService() {
        super("CampaignIntentService");
    }

    public CampaignTrackingService(String string2) {
        super(string2);
    }

    public void onHandleIntent(Intent intent) {
        this.processIntent((Context)this, intent);
    }

    public void processIntent(Context object, Intent object2) {
        object2 = object2.getStringExtra("referrer");
        try {
            object = object.openFileOutput("gaInstallData", 0);
            object.write(object2.getBytes());
            object.close();
            aa.y("Stored campaign information.");
            return;
        }
        catch (IOException var1_2) {
            aa.w("Error storing install campaign.");
            return;
        }
    }
}

