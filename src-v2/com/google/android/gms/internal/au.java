/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.AppEventListener;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.mediation.NetworkExtras;
import com.google.android.gms.ads.purchase.InAppPurchaseListener;
import com.google.android.gms.internal.af;
import com.google.android.gms.internal.ag;
import com.google.android.gms.internal.ah;
import com.google.android.gms.internal.aj;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.am;
import com.google.android.gms.internal.ao;
import com.google.android.gms.internal.ap;
import com.google.android.gms.internal.ar;
import com.google.android.gms.internal.as;
import com.google.android.gms.internal.bp;
import com.google.android.gms.internal.co;
import com.google.android.gms.internal.cp;
import com.google.android.gms.internal.dw;
import java.util.Map;

public final class au {
    private AdListener lF;
    private AppEventListener lV;
    private String lX;
    private final Context mContext;
    private final bp ml = new bp();
    private final aj mm;
    private ap mn;
    private InAppPurchaseListener mp;

    public au(Context context) {
        this(context, aj.az());
    }

    public au(Context context, aj aj2) {
        this.mContext = context;
        this.mm = aj2;
    }

    private void k(String string2) throws RemoteException {
        if (this.lX == null) {
            this.l(string2);
        }
        this.mn = ag.a(this.mContext, new ak(), this.lX, this.ml);
        if (this.lF != null) {
            this.mn.a(new af(this.lF));
        }
        if (this.lV != null) {
            this.mn.a(new am(this.lV));
        }
        if (this.mp != null) {
            this.mn.a(new cp(this.mp));
        }
    }

    private void l(String string2) {
        if (this.mn == null) {
            throw new IllegalStateException("The ad unit ID must be set on InterstitialAd before " + string2 + " is called.");
        }
    }

    public void a(as as2) {
        try {
            if (this.mn == null) {
                this.k("loadAd");
            }
            if (this.mn.a(this.mm.a(this.mContext, as2))) {
                this.ml.c(as2.aC());
                this.ml.d(as2.aD());
            }
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Failed to load ad.", (Throwable)var1_2);
            return;
        }
    }

    public AdListener getAdListener() {
        return this.lF;
    }

    public String getAdUnitId() {
        return this.lX;
    }

    public AppEventListener getAppEventListener() {
        return this.lV;
    }

    public InAppPurchaseListener getInAppPurchaseListener() {
        return this.mp;
    }

    public boolean isLoaded() {
        block3 : {
            try {
                if (this.mn != null) break block3;
                return false;
            }
            catch (RemoteException var2_2) {
                dw.c("Failed to check if ad is ready.", (Throwable)var2_2);
                return false;
            }
        }
        boolean bl2 = this.mn.isReady();
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setAdListener(AdListener object) {
        try {
            this.lF = object;
            if (this.mn != null) {
                ap ap2 = this.mn;
                object = object != null ? new af((AdListener)object) : null;
                ap2.a((ao)object);
            }
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Failed to set the AdListener.", (Throwable)var1_2);
            return;
        }
    }

    public void setAdUnitId(String string2) {
        if (this.lX != null) {
            throw new IllegalStateException("The ad unit ID can only be set once on InterstitialAd.");
        }
        this.lX = string2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setAppEventListener(AppEventListener object) {
        try {
            this.lV = object;
            if (this.mn != null) {
                ap ap2 = this.mn;
                object = object != null ? new am((AppEventListener)object) : null;
                ap2.a((ar)object);
            }
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Failed to set the AppEventListener.", (Throwable)var1_2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setInAppPurchaseListener(InAppPurchaseListener object) {
        try {
            this.mp = object;
            if (this.mn != null) {
                ap ap2 = this.mn;
                object = object != null ? new cp((InAppPurchaseListener)object) : null;
                ap2.a((co)object);
            }
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Failed to set the InAppPurchaseListener.", (Throwable)var1_2);
            return;
        }
    }

    public void show() {
        try {
            this.l("show");
            this.mn.showInterstitial();
            return;
        }
        catch (RemoteException var1_1) {
            dw.c("Failed to show interstitial.", (Throwable)var1_1);
            return;
        }
    }
}

