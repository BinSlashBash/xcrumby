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
import com.google.android.gms.identity.intents.UserAddressRequest;
import com.google.android.gms.internal.ff;
import com.google.android.gms.internal.fl;
import com.google.android.gms.internal.fm;
import com.google.android.gms.internal.gx;
import com.google.android.gms.internal.gy;

public class gw
extends ff<gy> {
    private a NA;
    private final int mTheme;
    private Activity nS;
    private final String wG;

    public gw(Activity activity, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, String string2, int n2) {
        super((Context)activity, looper, connectionCallbacks, onConnectionFailedListener, new String[0]);
        this.wG = string2;
        this.nS = activity;
        this.mTheme = n2;
    }

    protected gy R(IBinder iBinder) {
        return gy.a.T(iBinder);
    }

    public void a(UserAddressRequest userAddressRequest, int n2) {
        this.hO();
        this.NA = new a(n2, this.nS);
        try {
            Bundle bundle = new Bundle();
            bundle.putString("com.google.android.gms.identity.intents.EXTRA_CALLING_PACKAGE_NAME", this.getContext().getPackageName());
            if (!TextUtils.isEmpty((CharSequence)this.wG)) {
                bundle.putParcelable("com.google.android.gms.identity.intents.EXTRA_ACCOUNT", (Parcelable)new Account(this.wG, "com.google"));
            }
            bundle.putInt("com.google.android.gms.identity.intents.EXTRA_THEME", this.mTheme);
            this.hN().a(this.NA, userAddressRequest, bundle);
            return;
        }
        catch (RemoteException var1_2) {
            Log.e((String)"AddressClientImpl", (String)"Exception requesting user address", (Throwable)var1_2);
            Bundle bundle = new Bundle();
            bundle.putInt("com.google.android.gms.identity.intents.EXTRA_ERROR_CODE", 555);
            this.NA.d(1, bundle);
            return;
        }
    }

    @Override
    protected void a(fm fm2, ff.e e2) throws RemoteException {
        fm2.d(e2, 4452000, this.getContext().getPackageName());
    }

    @Override
    protected String bg() {
        return "com.google.android.gms.identity.service.BIND";
    }

    @Override
    protected String bh() {
        return "com.google.android.gms.identity.intents.internal.IAddressService";
    }

    @Override
    public void disconnect() {
        super.disconnect();
        if (this.NA != null) {
            this.NA.setActivity(null);
            this.NA = null;
        }
    }

    protected gy hN() {
        return (gy)super.eM();
    }

    protected void hO() {
        super.bT();
    }

    @Override
    protected /* synthetic */ IInterface r(IBinder iBinder) {
        return this.R(iBinder);
    }

    public static final class a
    extends gx.a {
        private final int CV;
        private Activity nS;

        public a(int n2, Activity activity) {
            this.CV = n2;
            this.nS = activity;
        }

        private void setActivity(Activity activity) {
            this.nS = activity;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void d(int n2, Bundle object) {
            if (n2 == 1) {
                Intent intent = new Intent();
                intent.putExtras((Bundle)object);
                object = this.nS.createPendingResult(this.CV, intent, 1073741824);
                if (object == null) return;
                {
                    try {
                        object.send(1);
                        return;
                    }
                    catch (PendingIntent.CanceledException var2_3) {
                        Log.w((String)"AddressClientImpl", (String)"Exception settng pending result", (Throwable)var2_3);
                        return;
                    }
                }
            }
            PendingIntent pendingIntent = null;
            if (object != null) {
                pendingIntent = (PendingIntent)object.getParcelable("com.google.android.gms.identity.intents.EXTRA_PENDING_INTENT");
            }
            if ((object = new ConnectionResult(n2, pendingIntent)).hasResolution()) {
                try {
                    object.startResolutionForResult(this.nS, this.CV);
                    return;
                }
                catch (IntentSender.SendIntentException var2_4) {
                    Log.w((String)"AddressClientImpl", (String)"Exception starting pending intent", (Throwable)var2_4);
                    return;
                }
            }
            try {
                object = this.nS.createPendingResult(this.CV, new Intent(), 1073741824);
                if (object == null) {
                    return;
                }
                object.send(1);
                return;
            }
            catch (PendingIntent.CanceledException var2_5) {
                Log.w((String)"AddressClientImpl", (String)"Exception setting pending result", (Throwable)var2_5);
                return;
            }
        }
    }

}

