/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.os.Bundle
 *  android.os.RemoteException
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package com.google.android.gms.ads;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.internal.cj;
import com.google.android.gms.internal.ck;
import com.google.android.gms.internal.dw;

public final class AdActivity
extends Activity {
    public static final String CLASS_NAME = "com.google.android.gms.ads.AdActivity";
    public static final String SIMPLE_CLASS_NAME = "AdActivity";
    private ck ko;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void N() {
        if (this.ko == null) return;
        try {
            this.ko.N();
            return;
        }
        catch (RemoteException var1_1) {
            dw.c("Could not forward setContentViewSet to ad overlay:", (Throwable)var1_1);
            return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.ko = cj.a(this);
        if (this.ko == null) {
            dw.z("Could not create ad overlay.");
            this.finish();
            return;
        }
        try {
            this.ko.onCreate(bundle);
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not forward onCreate to ad overlay:", (Throwable)var1_2);
            this.finish();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void onDestroy() {
        try {
            if (this.ko != null) {
                this.ko.onDestroy();
            }
        }
        catch (RemoteException var1_1) {
            dw.c("Could not forward onDestroy to ad overlay:", (Throwable)var1_1);
        }
        super.onDestroy();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void onPause() {
        try {
            if (this.ko != null) {
                this.ko.onPause();
            }
        }
        catch (RemoteException var1_1) {
            dw.c("Could not forward onPause to ad overlay:", (Throwable)var1_1);
            this.finish();
        }
        super.onPause();
    }

    protected void onRestart() {
        super.onRestart();
        try {
            if (this.ko != null) {
                this.ko.onRestart();
            }
            return;
        }
        catch (RemoteException var1_1) {
            dw.c("Could not forward onRestart to ad overlay:", (Throwable)var1_1);
            this.finish();
            return;
        }
    }

    protected void onResume() {
        super.onResume();
        try {
            if (this.ko != null) {
                this.ko.onResume();
            }
            return;
        }
        catch (RemoteException var1_1) {
            dw.c("Could not forward onResume to ad overlay:", (Throwable)var1_1);
            this.finish();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void onSaveInstanceState(Bundle bundle) {
        try {
            if (this.ko != null) {
                this.ko.onSaveInstanceState(bundle);
            }
        }
        catch (RemoteException var2_2) {
            dw.c("Could not forward onSaveInstanceState to ad overlay:", (Throwable)var2_2);
            this.finish();
        }
        super.onSaveInstanceState(bundle);
    }

    protected void onStart() {
        super.onStart();
        try {
            if (this.ko != null) {
                this.ko.onStart();
            }
            return;
        }
        catch (RemoteException var1_1) {
            dw.c("Could not forward onStart to ad overlay:", (Throwable)var1_1);
            this.finish();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void onStop() {
        try {
            if (this.ko != null) {
                this.ko.onStop();
            }
        }
        catch (RemoteException var1_1) {
            dw.c("Could not forward onStop to ad overlay:", (Throwable)var1_1);
            this.finish();
        }
        super.onStop();
    }

    public void setContentView(int n2) {
        super.setContentView(n2);
        this.N();
    }

    public void setContentView(View view) {
        super.setContentView(view);
        this.N();
    }

    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        super.setContentView(view, layoutParams);
        this.N();
    }
}

