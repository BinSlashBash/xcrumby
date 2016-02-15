/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.RemoteException
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.AppEventListener;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.mediation.NetworkExtras;
import com.google.android.gms.ads.purchase.InAppPurchaseListener;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.dynamic.e;
import com.google.android.gms.internal.af;
import com.google.android.gms.internal.ag;
import com.google.android.gms.internal.ah;
import com.google.android.gms.internal.aj;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.am;
import com.google.android.gms.internal.an;
import com.google.android.gms.internal.ao;
import com.google.android.gms.internal.ap;
import com.google.android.gms.internal.ar;
import com.google.android.gms.internal.as;
import com.google.android.gms.internal.bp;
import com.google.android.gms.internal.co;
import com.google.android.gms.internal.cp;
import com.google.android.gms.internal.dv;
import com.google.android.gms.internal.dw;
import java.util.Map;

public final class at {
    private AdListener lF;
    private AppEventListener lV;
    private AdSize[] lW;
    private String lX;
    private final bp ml = new bp();
    private final aj mm;
    private ap mn;
    private ViewGroup mo;
    private InAppPurchaseListener mp;

    public at(ViewGroup viewGroup) {
        this(viewGroup, null, false, aj.az());
    }

    public at(ViewGroup viewGroup, AttributeSet attributeSet, boolean bl2) {
        this(viewGroup, attributeSet, bl2, aj.az());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    at(ViewGroup viewGroup, AttributeSet object, boolean bl2, aj aj2) {
        this.mo = viewGroup;
        this.mm = aj2;
        if (object == null) return;
        aj2 = viewGroup.getContext();
        try {
            object = new an((Context)aj2, (AttributeSet)object);
            this.lW = object.e(bl2);
            this.lX = object.getAdUnitId();
        }
        catch (IllegalArgumentException illegalArgumentException) {
            dv.a(viewGroup, new ak((Context)aj2, AdSize.BANNER), illegalArgumentException.getMessage(), illegalArgumentException.getMessage());
            return;
        }
        if (!viewGroup.isInEditMode()) return;
        dv.a(viewGroup, new ak((Context)aj2, this.lW[0]), "Ads by Google");
    }

    private void aF() {
        d d2;
        block3 : {
            try {
                d2 = this.mn.Q();
                if (d2 != null) break block3;
                return;
            }
            catch (RemoteException var1_2) {
                dw.c("Failed to get an ad frame.", (Throwable)var1_2);
                return;
            }
        }
        this.mo.addView((View)e.d(d2));
    }

    private void aG() throws RemoteException {
        if ((this.lW == null || this.lX == null) && this.mn == null) {
            throw new IllegalStateException("The ad size and ad unit ID must be set before loadAd is called.");
        }
        Context context = this.mo.getContext();
        this.mn = ag.a(context, new ak(context, this.lW), this.lX, this.ml);
        if (this.lF != null) {
            this.mn.a(new af(this.lF));
        }
        if (this.lV != null) {
            this.mn.a(new am(this.lV));
        }
        if (this.mp != null) {
            this.mn.a(new cp(this.mp));
        }
        this.aF();
    }

    public void a(as as2) {
        try {
            if (this.mn == null) {
                this.aG();
            }
            if (this.mn.a(this.mm.a(this.mo.getContext(), as2))) {
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public /* varargs */ void a(AdSize ... arradSize) {
        this.lW = arradSize;
        try {
            if (this.mn != null) {
                this.mn.a(new ak(this.mo.getContext(), this.lW));
            }
        }
        catch (RemoteException var1_2) {
            dw.c("Failed to set the ad size.", (Throwable)var1_2);
        }
        this.mo.requestLayout();
    }

    public void destroy() {
        try {
            if (this.mn != null) {
                this.mn.destroy();
            }
            return;
        }
        catch (RemoteException var1_1) {
            dw.c("Failed to destroy AdView.", (Throwable)var1_1);
            return;
        }
    }

    public AdListener getAdListener() {
        return this.lF;
    }

    public AdSize getAdSize() {
        try {
            if (this.mn != null) {
                AdSize adSize = this.mn.R().aA();
                return adSize;
            }
        }
        catch (RemoteException var1_2) {
            dw.c("Failed to get the current AdSize.", (Throwable)var1_2);
        }
        if (this.lW != null) {
            return this.lW[0];
        }
        return null;
    }

    public AdSize[] getAdSizes() {
        return this.lW;
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

    public void pause() {
        try {
            if (this.mn != null) {
                this.mn.pause();
            }
            return;
        }
        catch (RemoteException var1_1) {
            dw.c("Failed to call pause.", (Throwable)var1_1);
            return;
        }
    }

    public void recordManualImpression() {
        try {
            this.mn.ac();
            return;
        }
        catch (RemoteException var1_1) {
            dw.c("Failed to record impression.", (Throwable)var1_1);
            return;
        }
    }

    public void resume() {
        try {
            if (this.mn != null) {
                this.mn.resume();
            }
            return;
        }
        catch (RemoteException var1_1) {
            dw.c("Failed to call resume.", (Throwable)var1_1);
            return;
        }
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

    public /* varargs */ void setAdSizes(AdSize ... arradSize) {
        if (this.lW != null) {
            throw new IllegalStateException("The ad size can only be set once on AdView.");
        }
        this.a(arradSize);
    }

    public void setAdUnitId(String string2) {
        if (this.lX != null) {
            throw new IllegalStateException("The ad unit ID can only be set once on AdView.");
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
}

