/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.internal.jg;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.NotifyTransactionStatusRequest;
import com.google.android.gms.wallet.Payments;
import com.google.android.gms.wallet.Wallet;

public class jf
implements Payments {
    @Override
    public void changeMaskedWallet(GoogleApiClient googleApiClient, final String string2, final String string3, final int n2) {
        googleApiClient.a(new Wallet.b(){

            @Override
            protected void a(jg jg2) {
                jg2.d(string2, string3, n2);
                this.a(Status.Bv);
            }
        });
    }

    @Override
    public void checkForPreAuthorization(GoogleApiClient googleApiClient, final int n2) {
        googleApiClient.a(new Wallet.b(){

            @Override
            protected void a(jg jg2) {
                jg2.cD(n2);
                this.a(Status.Bv);
            }
        });
    }

    @Override
    public void loadFullWallet(GoogleApiClient googleApiClient, final FullWalletRequest fullWalletRequest, final int n2) {
        googleApiClient.a(new Wallet.b(){

            @Override
            protected void a(jg jg2) {
                jg2.a(fullWalletRequest, n2);
                this.a(Status.Bv);
            }
        });
    }

    @Override
    public void loadMaskedWallet(GoogleApiClient googleApiClient, final MaskedWalletRequest maskedWalletRequest, final int n2) {
        googleApiClient.a(new Wallet.b(){

            @Override
            protected void a(jg jg2) {
                jg2.a(maskedWalletRequest, n2);
                this.a(Status.Bv);
            }
        });
    }

    @Override
    public void notifyTransactionStatus(GoogleApiClient googleApiClient, final NotifyTransactionStatusRequest notifyTransactionStatusRequest) {
        googleApiClient.a(new Wallet.b(){

            @Override
            protected void a(jg jg2) {
                jg2.a(notifyTransactionStatusRequest);
                this.a(Status.Bv);
            }
        });
    }

}

