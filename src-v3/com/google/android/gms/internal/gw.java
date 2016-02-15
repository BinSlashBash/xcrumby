package com.google.android.gms.internal;

import android.accounts.Account;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.identity.intents.AddressConstants.ErrorCodes;
import com.google.android.gms.identity.intents.AddressConstants.Extras;
import com.google.android.gms.identity.intents.UserAddressRequest;
import com.google.android.gms.internal.ff.C1374e;
import com.google.android.gms.internal.gx.C0904a;
import com.google.android.gms.internal.gy.C0906a;

public class gw extends ff<gy> {
    private C1376a NA;
    private final int mTheme;
    private Activity nS;
    private final String wG;

    /* renamed from: com.google.android.gms.internal.gw.a */
    public static final class C1376a extends C0904a {
        private final int CV;
        private Activity nS;

        public C1376a(int i, Activity activity) {
            this.CV = i;
            this.nS = activity;
        }

        private void setActivity(Activity activity) {
            this.nS = activity;
        }

        public void m3073d(int i, Bundle bundle) {
            PendingIntent createPendingResult;
            if (i == 1) {
                Intent intent = new Intent();
                intent.putExtras(bundle);
                createPendingResult = this.nS.createPendingResult(this.CV, intent, 1073741824);
                if (createPendingResult != null) {
                    try {
                        createPendingResult.send(1);
                        return;
                    } catch (Throwable e) {
                        Log.w("AddressClientImpl", "Exception settng pending result", e);
                        return;
                    }
                }
                return;
            }
            createPendingResult = null;
            if (bundle != null) {
                createPendingResult = (PendingIntent) bundle.getParcelable("com.google.android.gms.identity.intents.EXTRA_PENDING_INTENT");
            }
            ConnectionResult connectionResult = new ConnectionResult(i, createPendingResult);
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this.nS, this.CV);
                    return;
                } catch (Throwable e2) {
                    Log.w("AddressClientImpl", "Exception starting pending intent", e2);
                    return;
                }
            }
            try {
                createPendingResult = this.nS.createPendingResult(this.CV, new Intent(), 1073741824);
                if (createPendingResult != null) {
                    createPendingResult.send(1);
                }
            } catch (Throwable e22) {
                Log.w("AddressClientImpl", "Exception setting pending result", e22);
            }
        }
    }

    public gw(Activity activity, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, String str, int i) {
        super(activity, looper, connectionCallbacks, onConnectionFailedListener, new String[0]);
        this.wG = str;
        this.nS = activity;
        this.mTheme = i;
    }

    protected gy m3074R(IBinder iBinder) {
        return C0906a.m2253T(iBinder);
    }

    public void m3075a(UserAddressRequest userAddressRequest, int i) {
        hO();
        this.NA = new C1376a(i, this.nS);
        Bundle bundle;
        try {
            bundle = new Bundle();
            bundle.putString("com.google.android.gms.identity.intents.EXTRA_CALLING_PACKAGE_NAME", getContext().getPackageName());
            if (!TextUtils.isEmpty(this.wG)) {
                bundle.putParcelable("com.google.android.gms.identity.intents.EXTRA_ACCOUNT", new Account(this.wG, GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE));
            }
            bundle.putInt("com.google.android.gms.identity.intents.EXTRA_THEME", this.mTheme);
            hN().m1039a(this.NA, userAddressRequest, bundle);
        } catch (Throwable e) {
            Log.e("AddressClientImpl", "Exception requesting user address", e);
            bundle = new Bundle();
            bundle.putInt(Extras.EXTRA_ERROR_CODE, ErrorCodes.ERROR_CODE_NO_APPLICABLE_ADDRESSES);
            this.NA.m3073d(1, bundle);
        }
    }

    protected void m3076a(fm fmVar, C1374e c1374e) throws RemoteException {
        fmVar.m959d(c1374e, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName());
    }

    protected String bg() {
        return "com.google.android.gms.identity.service.BIND";
    }

    protected String bh() {
        return "com.google.android.gms.identity.intents.internal.IAddressService";
    }

    public void disconnect() {
        super.disconnect();
        if (this.NA != null) {
            this.NA.setActivity(null);
            this.NA = null;
        }
    }

    protected gy hN() {
        return (gy) super.eM();
    }

    protected void hO() {
        super.bT();
    }

    protected /* synthetic */ IInterface m3077r(IBinder iBinder) {
        return m3074R(iBinder);
    }
}
