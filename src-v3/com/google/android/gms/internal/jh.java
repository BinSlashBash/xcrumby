package com.google.android.gms.internal;

import android.app.Activity;
import android.os.IBinder;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.dynamic.C0305c;
import com.google.android.gms.dynamic.C0309g;
import com.google.android.gms.dynamic.C1324e;
import com.google.android.gms.internal.jc.C0928a;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;

public class jh extends C0309g<jc> {
    private static jh adc;

    protected jh() {
        super("com.google.android.gms.wallet.dynamite.WalletDynamiteCreatorImpl");
    }

    public static iz m2321a(Activity activity, C0305c c0305c, WalletFragmentOptions walletFragmentOptions, ja jaVar) throws GooglePlayServicesNotAvailableException {
        int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (isGooglePlayServicesAvailable != 0) {
            throw new GooglePlayServicesNotAvailableException(isGooglePlayServicesAvailable);
        }
        try {
            return ((jc) lY().m359z(activity)).m1101a(C1324e.m2710h(activity), c0305c, walletFragmentOptions, jaVar);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } catch (Throwable e2) {
            throw new RuntimeException(e2);
        }
    }

    private static jh lY() {
        if (adc == null) {
            adc = new jh();
        }
        return adc;
    }

    protected jc aZ(IBinder iBinder) {
        return C0928a.aV(iBinder);
    }

    protected /* synthetic */ Object m2322d(IBinder iBinder) {
        return aZ(iBinder);
    }
}
