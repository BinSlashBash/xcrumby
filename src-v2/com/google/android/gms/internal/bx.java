/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.Handler;
import android.os.RemoteException;
import com.google.ads.AdRequest;
import com.google.ads.mediation.MediationBannerAdapter;
import com.google.ads.mediation.MediationBannerListener;
import com.google.ads.mediation.MediationInterstitialAdapter;
import com.google.ads.mediation.MediationInterstitialListener;
import com.google.ads.mediation.MediationServerParameters;
import com.google.ads.mediation.NetworkExtras;
import com.google.android.gms.internal.bs;
import com.google.android.gms.internal.by;
import com.google.android.gms.internal.dv;
import com.google.android.gms.internal.dw;

public final class bx<NETWORK_EXTRAS extends NetworkExtras, SERVER_PARAMETERS extends MediationServerParameters>
implements MediationBannerListener,
MediationInterstitialListener {
    private final bs nG;

    public bx(bs bs2) {
        this.nG = bs2;
    }

    @Override
    public void onClick(MediationBannerAdapter<?, ?> mediationBannerAdapter) {
        dw.v("Adapter called onClick.");
        if (!dv.bD()) {
            dw.z("onClick must be called on the main UI thread.");
            dv.rp.post(new Runnable(){

                @Override
                public void run() {
                    try {
                        bx.this.nG.P();
                        return;
                    }
                    catch (RemoteException var1_1) {
                        dw.c("Could not call onAdClicked.", (Throwable)var1_1);
                        return;
                    }
                }
            });
            return;
        }
        try {
            this.nG.P();
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not call onAdClicked.", (Throwable)var1_2);
            return;
        }
    }

    @Override
    public void onDismissScreen(MediationBannerAdapter<?, ?> mediationBannerAdapter) {
        dw.v("Adapter called onDismissScreen.");
        if (!dv.bD()) {
            dw.z("onDismissScreen must be called on the main UI thread.");
            dv.rp.post(new Runnable(){

                @Override
                public void run() {
                    try {
                        bx.this.nG.onAdClosed();
                        return;
                    }
                    catch (RemoteException var1_1) {
                        dw.c("Could not call onAdClosed.", (Throwable)var1_1);
                        return;
                    }
                }
            });
            return;
        }
        try {
            this.nG.onAdClosed();
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not call onAdClosed.", (Throwable)var1_2);
            return;
        }
    }

    @Override
    public void onDismissScreen(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter) {
        dw.v("Adapter called onDismissScreen.");
        if (!dv.bD()) {
            dw.z("onDismissScreen must be called on the main UI thread.");
            dv.rp.post(new Runnable(){

                @Override
                public void run() {
                    try {
                        bx.this.nG.onAdClosed();
                        return;
                    }
                    catch (RemoteException var1_1) {
                        dw.c("Could not call onAdClosed.", (Throwable)var1_1);
                        return;
                    }
                }
            });
            return;
        }
        try {
            this.nG.onAdClosed();
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not call onAdClosed.", (Throwable)var1_2);
            return;
        }
    }

    @Override
    public void onFailedToReceiveAd(MediationBannerAdapter<?, ?> mediationBannerAdapter, final AdRequest.ErrorCode errorCode) {
        dw.v("Adapter called onFailedToReceiveAd with error. " + (Object)((Object)errorCode));
        if (!dv.bD()) {
            dw.z("onFailedToReceiveAd must be called on the main UI thread.");
            dv.rp.post(new Runnable(){

                @Override
                public void run() {
                    try {
                        bx.this.nG.onAdFailedToLoad(by.a(errorCode));
                        return;
                    }
                    catch (RemoteException var1_1) {
                        dw.c("Could not call onAdFailedToLoad.", (Throwable)var1_1);
                        return;
                    }
                }
            });
            return;
        }
        try {
            this.nG.onAdFailedToLoad(by.a(errorCode));
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not call onAdFailedToLoad.", (Throwable)var1_2);
            return;
        }
    }

    @Override
    public void onFailedToReceiveAd(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter, final AdRequest.ErrorCode errorCode) {
        dw.v("Adapter called onFailedToReceiveAd with error " + (Object)((Object)errorCode) + ".");
        if (!dv.bD()) {
            dw.z("onFailedToReceiveAd must be called on the main UI thread.");
            dv.rp.post(new Runnable(){

                @Override
                public void run() {
                    try {
                        bx.this.nG.onAdFailedToLoad(by.a(errorCode));
                        return;
                    }
                    catch (RemoteException var1_1) {
                        dw.c("Could not call onAdFailedToLoad.", (Throwable)var1_1);
                        return;
                    }
                }
            });
            return;
        }
        try {
            this.nG.onAdFailedToLoad(by.a(errorCode));
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not call onAdFailedToLoad.", (Throwable)var1_2);
            return;
        }
    }

    @Override
    public void onLeaveApplication(MediationBannerAdapter<?, ?> mediationBannerAdapter) {
        dw.v("Adapter called onLeaveApplication.");
        if (!dv.bD()) {
            dw.z("onLeaveApplication must be called on the main UI thread.");
            dv.rp.post(new Runnable(){

                @Override
                public void run() {
                    try {
                        bx.this.nG.onAdLeftApplication();
                        return;
                    }
                    catch (RemoteException var1_1) {
                        dw.c("Could not call onAdLeftApplication.", (Throwable)var1_1);
                        return;
                    }
                }
            });
            return;
        }
        try {
            this.nG.onAdLeftApplication();
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not call onAdLeftApplication.", (Throwable)var1_2);
            return;
        }
    }

    @Override
    public void onLeaveApplication(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter) {
        dw.v("Adapter called onLeaveApplication.");
        if (!dv.bD()) {
            dw.z("onLeaveApplication must be called on the main UI thread.");
            dv.rp.post(new Runnable(){

                @Override
                public void run() {
                    try {
                        bx.this.nG.onAdLeftApplication();
                        return;
                    }
                    catch (RemoteException var1_1) {
                        dw.c("Could not call onAdLeftApplication.", (Throwable)var1_1);
                        return;
                    }
                }
            });
            return;
        }
        try {
            this.nG.onAdLeftApplication();
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not call onAdLeftApplication.", (Throwable)var1_2);
            return;
        }
    }

    @Override
    public void onPresentScreen(MediationBannerAdapter<?, ?> mediationBannerAdapter) {
        dw.v("Adapter called onPresentScreen.");
        if (!dv.bD()) {
            dw.z("onPresentScreen must be called on the main UI thread.");
            dv.rp.post(new Runnable(){

                @Override
                public void run() {
                    try {
                        bx.this.nG.onAdOpened();
                        return;
                    }
                    catch (RemoteException var1_1) {
                        dw.c("Could not call onAdOpened.", (Throwable)var1_1);
                        return;
                    }
                }
            });
            return;
        }
        try {
            this.nG.onAdOpened();
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not call onAdOpened.", (Throwable)var1_2);
            return;
        }
    }

    @Override
    public void onPresentScreen(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter) {
        dw.v("Adapter called onPresentScreen.");
        if (!dv.bD()) {
            dw.z("onPresentScreen must be called on the main UI thread.");
            dv.rp.post(new Runnable(){

                @Override
                public void run() {
                    try {
                        bx.this.nG.onAdOpened();
                        return;
                    }
                    catch (RemoteException var1_1) {
                        dw.c("Could not call onAdOpened.", (Throwable)var1_1);
                        return;
                    }
                }
            });
            return;
        }
        try {
            this.nG.onAdOpened();
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not call onAdOpened.", (Throwable)var1_2);
            return;
        }
    }

    @Override
    public void onReceivedAd(MediationBannerAdapter<?, ?> mediationBannerAdapter) {
        dw.v("Adapter called onReceivedAd.");
        if (!dv.bD()) {
            dw.z("onReceivedAd must be called on the main UI thread.");
            dv.rp.post(new Runnable(){

                @Override
                public void run() {
                    try {
                        bx.this.nG.onAdLoaded();
                        return;
                    }
                    catch (RemoteException var1_1) {
                        dw.c("Could not call onAdLoaded.", (Throwable)var1_1);
                        return;
                    }
                }
            });
            return;
        }
        try {
            this.nG.onAdLoaded();
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not call onAdLoaded.", (Throwable)var1_2);
            return;
        }
    }

    @Override
    public void onReceivedAd(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter) {
        dw.v("Adapter called onReceivedAd.");
        if (!dv.bD()) {
            dw.z("onReceivedAd must be called on the main UI thread.");
            dv.rp.post(new Runnable(){

                @Override
                public void run() {
                    try {
                        bx.this.nG.onAdLoaded();
                        return;
                    }
                    catch (RemoteException var1_1) {
                        dw.c("Could not call onAdLoaded.", (Throwable)var1_1);
                        return;
                    }
                }
            });
            return;
        }
        try {
            this.nG.onAdLoaded();
            return;
        }
        catch (RemoteException var1_2) {
            dw.c("Could not call onAdLoaded.", (Throwable)var1_2);
            return;
        }
    }

}

