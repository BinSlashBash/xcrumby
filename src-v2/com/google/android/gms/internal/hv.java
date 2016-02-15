/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.hu;

public interface hv
extends IInterface {
    public void a(hu var1, Uri var2, Bundle var3, boolean var4) throws RemoteException;

    public static abstract class com.google.android.gms.internal.hv$a
    extends Binder
    implements hv {
        public static hv aM(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.panorama.internal.IPanoramaService");
            if (iInterface != null && iInterface instanceof hv) {
                return (hv)iInterface;
            }
            return new a(iBinder);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.google.android.gms.panorama.internal.IPanoramaService");
                    return true;
                }
                case 1: 
            }
            parcel.enforceInterface("com.google.android.gms.panorama.internal.IPanoramaService");
            hu hu2 = hu.a.aL(parcel.readStrongBinder());
            parcel2 = parcel.readInt() != 0 ? (Uri)Uri.CREATOR.createFromParcel(parcel) : null;
            Bundle bundle = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
            boolean bl2 = parcel.readInt() != 0;
            this.a(hu2, (Uri)parcel2, bundle, bl2);
            return true;
        }

        private static class a
        implements hv {
            private IBinder kn;

            a(IBinder iBinder) {
                this.kn = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void a(hu hu2, Uri uri, Bundle bundle, boolean bl2) throws RemoteException {
                IBinder iBinder = null;
                int n2 = 1;
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.panorama.internal.IPanoramaService");
                    if (hu2 != null) {
                        iBinder = hu2.asBinder();
                    }
                    parcel.writeStrongBinder(iBinder);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                }
                catch (Throwable var1_2) {
                    parcel.recycle();
                    throw var1_2;
                }
                if (!bl2) {
                    n2 = 0;
                }
                parcel.writeInt(n2);
                this.kn.transact(1, parcel, null, 1);
                parcel.recycle();
            }

            public IBinder asBinder() {
                return this.kn;
            }
        }

    }

}

