/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
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

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface hu
extends IInterface {
    public void a(int var1, Bundle var2, int var3, Intent var4) throws RemoteException;

    public static abstract class com.google.android.gms.internal.hu$a
    extends Binder
    implements hu {
        public com.google.android.gms.internal.hu$a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.panorama.internal.IPanoramaCallbacks");
        }

        public static hu aL(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.panorama.internal.IPanoramaCallbacks");
            if (iInterface != null && iInterface instanceof hu) {
                return (hu)iInterface;
            }
            return new a(iBinder);
        }

        public IBinder asBinder() {
            return this;
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
                    parcel2.writeString("com.google.android.gms.panorama.internal.IPanoramaCallbacks");
                    return true;
                }
                case 1: 
            }
            parcel.enforceInterface("com.google.android.gms.panorama.internal.IPanoramaCallbacks");
            n2 = parcel.readInt();
            Bundle bundle = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
            n3 = parcel.readInt();
            parcel = parcel.readInt() != 0 ? (Intent)Intent.CREATOR.createFromParcel(parcel) : null;
            this.a(n2, bundle, n3, (Intent)parcel);
            parcel2.writeNoException();
            return true;
        }

        private static class a
        implements hu {
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
            public void a(int n2, Bundle bundle, int n3, Intent intent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.panorama.internal.IPanoramaCallbacks");
                    parcel.writeInt(n2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n3);
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public IBinder asBinder() {
                return this.kn;
            }
        }

    }

}

