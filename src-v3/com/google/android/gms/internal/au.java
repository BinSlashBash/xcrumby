package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.AppEventListener;
import com.google.android.gms.ads.purchase.InAppPurchaseListener;

public final class au {
    private AdListener lF;
    private AppEventListener lV;
    private String lX;
    private final Context mContext;
    private final bp ml;
    private final aj mm;
    private ap mn;
    private InAppPurchaseListener mp;

    public au(Context context) {
        this(context, aj.az());
    }

    public au(Context context, aj ajVar) {
        this.ml = new bp();
        this.mContext = context;
        this.mm = ajVar;
    }

    private void m651k(String str) throws RemoteException {
        if (this.lX == null) {
            m652l(str);
        }
        this.mn = ag.m2007a(this.mContext, new ak(), this.lX, this.ml);
        if (this.lF != null) {
            this.mn.m622a(new af(this.lF));
        }
        if (this.lV != null) {
            this.mn.m623a(new am(this.lV));
        }
        if (this.mp != null) {
            this.mn.m624a(new cp(this.mp));
        }
    }

    private void m652l(String str) {
        if (this.mn == null) {
            throw new IllegalStateException("The ad unit ID must be set on InterstitialAd before " + str + " is called.");
        }
    }

    public void m653a(as asVar) {
        try {
            if (this.mn == null) {
                m651k("loadAd");
            }
            if (this.mn.m625a(this.mm.m613a(this.mContext, asVar))) {
                this.ml.m2911c(asVar.aC());
                this.ml.m2912d(asVar.aD());
            }
        } catch (Throwable e) {
            dw.m817c("Failed to load ad.", e);
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
        boolean z = false;
        try {
            if (this.mn != null) {
                z = this.mn.isReady();
            }
        } catch (Throwable e) {
            dw.m817c("Failed to check if ad is ready.", e);
        }
        return z;
    }

    public void setAdListener(AdListener adListener) {
        try {
            this.lF = adListener;
            if (this.mn != null) {
                this.mn.m622a(adListener != null ? new af(adListener) : null);
            }
        } catch (Throwable e) {
            dw.m817c("Failed to set the AdListener.", e);
        }
    }

    public void setAdUnitId(String adUnitId) {
        if (this.lX != null) {
            throw new IllegalStateException("The ad unit ID can only be set once on InterstitialAd.");
        }
        this.lX = adUnitId;
    }

    public void setAppEventListener(AppEventListener appEventListener) {
        try {
            this.lV = appEventListener;
            if (this.mn != null) {
                this.mn.m623a(appEventListener != null ? new am(appEventListener) : null);
            }
        } catch (Throwable e) {
            dw.m817c("Failed to set the AppEventListener.", e);
        }
    }

    public void setInAppPurchaseListener(InAppPurchaseListener inAppPurchaseListener) {
        try {
            this.mp = inAppPurchaseListener;
            if (this.mn != null) {
                this.mn.m624a(inAppPurchaseListener != null ? new cp(inAppPurchaseListener) : null);
            }
        } catch (Throwable e) {
            dw.m817c("Failed to set the InAppPurchaseListener.", e);
        }
    }

    public void show() {
        try {
            m652l("show");
            this.mn.showInterstitial();
        } catch (Throwable e) {
            dw.m817c("Failed to show interstitial.", e);
        }
    }
}
