/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.app.Activity
 *  android.app.PendingIntent
 *  android.app.PendingIntent$CanceledException
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.accounts.Account;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.ff;
import com.google.android.gms.internal.fl;
import com.google.android.gms.internal.fm;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.ix;
import com.google.android.gms.internal.jb;
import com.google.android.gms.internal.je;
import com.google.android.gms.wallet.FullWallet;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.NotifyTransactionStatusRequest;

public class jg
extends ff<jb> {
    private final int acq;
    private final int mTheme;
    private final Activity nS;
    private final String wG;

    public jg(Activity activity, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, int n2, String string2, int n3) {
        super((Context)activity, looper, connectionCallbacks, onConnectionFailedListener, new String[0]);
        this.nS = activity;
        this.acq = n2;
        this.wG = string2;
        this.mTheme = n3;
    }

    public static Bundle a(int n2, String string2, String string3, int n3) {
        Bundle bundle = new Bundle();
        bundle.putInt("com.google.android.gms.wallet.EXTRA_ENVIRONMENT", n2);
        bundle.putString("androidPackageName", string2);
        if (!TextUtils.isEmpty((CharSequence)string3)) {
            bundle.putParcelable("com.google.android.gms.wallet.EXTRA_BUYER_ACCOUNT", (Parcelable)new Account(string3, "com.google"));
        }
        bundle.putInt("com.google.android.gms.wallet.EXTRA_THEME", n3);
        return bundle;
    }

    private Bundle lX() {
        return jg.a(this.acq, this.nS.getPackageName(), this.wG, this.mTheme);
    }

    @Override
    protected void a(fm fm2, ff.e e2) throws RemoteException {
        fm2.a(e2, 4452000);
    }

    public void a(FullWalletRequest fullWalletRequest, int n2) {
        b b2 = new b(n2);
        Bundle bundle = this.lX();
        try {
            ((jb)this.eM()).a(fullWalletRequest, bundle, (je)b2);
            return;
        }
        catch (RemoteException var1_2) {
            Log.e((String)"WalletClientImpl", (String)"RemoteException getting full wallet", (Throwable)var1_2);
            b2.a(8, (FullWallet)null, Bundle.EMPTY);
            return;
        }
    }

    public void a(MaskedWalletRequest maskedWalletRequest, int n2) {
        Bundle bundle = this.lX();
        b b2 = new b(n2);
        try {
            ((jb)this.eM()).a(maskedWalletRequest, bundle, (je)b2);
            return;
        }
        catch (RemoteException var1_2) {
            Log.e((String)"WalletClientImpl", (String)"RemoteException getting masked wallet", (Throwable)var1_2);
            b2.a(8, (MaskedWallet)null, Bundle.EMPTY);
            return;
        }
    }

    public void a(NotifyTransactionStatusRequest notifyTransactionStatusRequest) {
        Bundle bundle = this.lX();
        try {
            ((jb)this.eM()).a(notifyTransactionStatusRequest, bundle);
            return;
        }
        catch (RemoteException var1_2) {
            return;
        }
    }

    protected jb aY(IBinder iBinder) {
        return jb.a.aU(iBinder);
    }

    @Override
    protected String bg() {
        return "com.google.android.gms.wallet.service.BIND";
    }

    @Override
    protected String bh() {
        return "com.google.android.gms.wallet.internal.IOwService";
    }

    public void cD(int n2) {
        Bundle bundle = this.lX();
        b b2 = new b(n2);
        try {
            ((jb)this.eM()).a(bundle, b2);
            return;
        }
        catch (RemoteException var3_3) {
            Log.e((String)"WalletClientImpl", (String)"RemoteException during checkForPreAuthorization", (Throwable)var3_3);
            b2.a(8, false, Bundle.EMPTY);
            return;
        }
    }

    public void d(String string2, String string3, int n2) {
        Bundle bundle = this.lX();
        b b2 = new b(n2);
        try {
            ((jb)this.eM()).a(string2, string3, bundle, b2);
            return;
        }
        catch (RemoteException var1_2) {
            Log.e((String)"WalletClientImpl", (String)"RemoteException changing masked wallet", (Throwable)var1_2);
            b2.a(8, (MaskedWallet)null, Bundle.EMPTY);
            return;
        }
    }

    @Override
    protected /* synthetic */ IInterface r(IBinder iBinder) {
        return this.aY(iBinder);
    }

    private static class a
    extends je.a {
        private a() {
        }

        @Override
        public void a(int n2, FullWallet fullWallet, Bundle bundle) {
        }

        @Override
        public void a(int n2, MaskedWallet maskedWallet, Bundle bundle) {
        }

        @Override
        public void a(int n2, boolean bl2, Bundle bundle) {
        }

        @Override
        public void a(Status status, ix ix2, Bundle bundle) {
        }

        @Override
        public void f(int n2, Bundle bundle) {
        }
    }

    final class b
    extends a {
        private final int CV;

        public b(int n2) {
            this.CV = n2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void a(int n2, FullWallet fullWallet, Bundle object) {
            int n3;
            PendingIntent pendingIntent = null;
            if (object != null) {
                pendingIntent = (PendingIntent)object.getParcelable("com.google.android.gms.wallet.EXTRA_PENDING_INTENT");
            }
            if ((object = new ConnectionResult(n2, pendingIntent)).hasResolution()) {
                try {
                    object.startResolutionForResult(jg.this.nS, this.CV);
                    return;
                }
                catch (IntentSender.SendIntentException var2_3) {
                    Log.w((String)"WalletClientImpl", (String)"Exception starting pending intent", (Throwable)var2_3);
                    return;
                }
            }
            pendingIntent = new Intent();
            if (object.isSuccess()) {
                n3 = -1;
                pendingIntent.putExtra("com.google.android.gms.wallet.EXTRA_FULL_WALLET", (Parcelable)fullWallet);
            } else {
                n3 = n2 == 408 ? 0 : 1;
                pendingIntent.putExtra("com.google.android.gms.wallet.EXTRA_ERROR_CODE", n2);
            }
            if ((fullWallet = jg.this.nS.createPendingResult(this.CV, (Intent)pendingIntent, 1073741824)) == null) {
                Log.w((String)"WalletClientImpl", (String)"Null pending result returned for onFullWalletLoaded");
                return;
            }
            try {
                fullWallet.send(n3);
                return;
            }
            catch (PendingIntent.CanceledException var2_4) {
                Log.w((String)"WalletClientImpl", (String)"Exception setting pending result", (Throwable)var2_4);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void a(int n2, MaskedWallet maskedWallet, Bundle object) {
            int n3;
            PendingIntent pendingIntent = null;
            if (object != null) {
                pendingIntent = (PendingIntent)object.getParcelable("com.google.android.gms.wallet.EXTRA_PENDING_INTENT");
            }
            if ((object = new ConnectionResult(n2, pendingIntent)).hasResolution()) {
                try {
                    object.startResolutionForResult(jg.this.nS, this.CV);
                    return;
                }
                catch (IntentSender.SendIntentException var2_3) {
                    Log.w((String)"WalletClientImpl", (String)"Exception starting pending intent", (Throwable)var2_3);
                    return;
                }
            }
            pendingIntent = new Intent();
            if (object.isSuccess()) {
                n3 = -1;
                pendingIntent.putExtra("com.google.android.gms.wallet.EXTRA_MASKED_WALLET", (Parcelable)maskedWallet);
            } else {
                n3 = n2 == 408 ? 0 : 1;
                pendingIntent.putExtra("com.google.android.gms.wallet.EXTRA_ERROR_CODE", n2);
            }
            if ((maskedWallet = jg.this.nS.createPendingResult(this.CV, (Intent)pendingIntent, 1073741824)) == null) {
                Log.w((String)"WalletClientImpl", (String)"Null pending result returned for onMaskedWalletLoaded");
                return;
            }
            try {
                maskedWallet.send(n3);
                return;
            }
            catch (PendingIntent.CanceledException var2_4) {
                Log.w((String)"WalletClientImpl", (String)"Exception setting pending result", (Throwable)var2_4);
                return;
            }
        }

        @Override
        public void a(int n2, boolean bl2, Bundle bundle) {
            bundle = new Intent();
            bundle.putExtra("com.google.android.gm.wallet.EXTRA_IS_USER_PREAUTHORIZED", bl2);
            bundle = jg.this.nS.createPendingResult(this.CV, (Intent)bundle, 1073741824);
            if (bundle == null) {
                Log.w((String)"WalletClientImpl", (String)"Null pending result returned for onPreAuthorizationDetermined");
                return;
            }
            try {
                bundle.send(-1);
                return;
            }
            catch (PendingIntent.CanceledException var3_4) {
                Log.w((String)"WalletClientImpl", (String)"Exception setting pending result", (Throwable)var3_4);
                return;
            }
        }

        @Override
        public void f(int n2, Bundle object) {
            fq.b(object, (Object)"Bundle should not be null");
            object = new ConnectionResult(n2, (PendingIntent)object.getParcelable("com.google.android.gms.wallet.EXTRA_PENDING_INTENT"));
            if (object.hasResolution()) {
                try {
                    object.startResolutionForResult(jg.this.nS, this.CV);
                    return;
                }
                catch (IntentSender.SendIntentException var2_3) {
                    Log.w((String)"WalletClientImpl", (String)"Exception starting pending intent", (Throwable)var2_3);
                    return;
                }
            }
            Log.e((String)"WalletClientImpl", (String)("Create Wallet Objects confirmation UI will not be shown connection result: " + object));
            object = new Intent();
            object.putExtra("com.google.android.gms.wallet.EXTRA_ERROR_CODE", 413);
            object = jg.this.nS.createPendingResult(this.CV, (Intent)object, 1073741824);
            if (object == null) {
                Log.w((String)"WalletClientImpl", (String)"Null pending result returned for onWalletObjectsCreated");
                return;
            }
            try {
                object.send(1);
                return;
            }
            catch (PendingIntent.CanceledException var2_4) {
                Log.w((String)"WalletClientImpl", (String)"Exception setting pending result", (Throwable)var2_4);
                return;
            }
        }
    }

}

