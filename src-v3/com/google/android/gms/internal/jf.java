package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.NotifyTransactionStatusRequest;
import com.google.android.gms.wallet.Payments;
import com.google.android.gms.wallet.Wallet.C1549b;

public class jf implements Payments {

    /* renamed from: com.google.android.gms.internal.jf.1 */
    class C16631 extends C1549b {
        final /* synthetic */ int Nx;
        final /* synthetic */ jf acV;

        C16631(jf jfVar, int i) {
            this.acV = jfVar;
            this.Nx = i;
        }

        protected void m3859a(jg jgVar) {
            jgVar.cD(this.Nx);
            m1659a(Status.Bv);
        }
    }

    /* renamed from: com.google.android.gms.internal.jf.2 */
    class C16642 extends C1549b {
        final /* synthetic */ int Nx;
        final /* synthetic */ jf acV;
        final /* synthetic */ MaskedWalletRequest acW;

        C16642(jf jfVar, MaskedWalletRequest maskedWalletRequest, int i) {
            this.acV = jfVar;
            this.acW = maskedWalletRequest;
            this.Nx = i;
        }

        protected void m3861a(jg jgVar) {
            jgVar.m3141a(this.acW, this.Nx);
            m1659a(Status.Bv);
        }
    }

    /* renamed from: com.google.android.gms.internal.jf.3 */
    class C16653 extends C1549b {
        final /* synthetic */ int Nx;
        final /* synthetic */ jf acV;
        final /* synthetic */ FullWalletRequest acX;

        C16653(jf jfVar, FullWalletRequest fullWalletRequest, int i) {
            this.acV = jfVar;
            this.acX = fullWalletRequest;
            this.Nx = i;
        }

        protected void m3863a(jg jgVar) {
            jgVar.m3140a(this.acX, this.Nx);
            m1659a(Status.Bv);
        }
    }

    /* renamed from: com.google.android.gms.internal.jf.4 */
    class C16664 extends C1549b {
        final /* synthetic */ int Nx;
        final /* synthetic */ jf acV;
        final /* synthetic */ String acY;
        final /* synthetic */ String acZ;

        C16664(jf jfVar, String str, String str2, int i) {
            this.acV = jfVar;
            this.acY = str;
            this.acZ = str2;
            this.Nx = i;
        }

        protected void m3865a(jg jgVar) {
            jgVar.m3143d(this.acY, this.acZ, this.Nx);
            m1659a(Status.Bv);
        }
    }

    /* renamed from: com.google.android.gms.internal.jf.5 */
    class C16675 extends C1549b {
        final /* synthetic */ jf acV;
        final /* synthetic */ NotifyTransactionStatusRequest ada;

        C16675(jf jfVar, NotifyTransactionStatusRequest notifyTransactionStatusRequest) {
            this.acV = jfVar;
            this.ada = notifyTransactionStatusRequest;
        }

        protected void m3867a(jg jgVar) {
            jgVar.m3142a(this.ada);
            m1659a(Status.Bv);
        }
    }

    public void changeMaskedWallet(GoogleApiClient googleApiClient, String googleTransactionId, String merchantTransactionId, int requestCode) {
        googleApiClient.m124a(new C16664(this, googleTransactionId, merchantTransactionId, requestCode));
    }

    public void checkForPreAuthorization(GoogleApiClient googleApiClient, int requestCode) {
        googleApiClient.m124a(new C16631(this, requestCode));
    }

    public void loadFullWallet(GoogleApiClient googleApiClient, FullWalletRequest request, int requestCode) {
        googleApiClient.m124a(new C16653(this, request, requestCode));
    }

    public void loadMaskedWallet(GoogleApiClient googleApiClient, MaskedWalletRequest request, int requestCode) {
        googleApiClient.m124a(new C16642(this, request, requestCode));
    }

    public void notifyTransactionStatus(GoogleApiClient googleApiClient, NotifyTransactionStatusRequest request) {
        googleApiClient.m124a(new C16675(this, request));
    }
}
