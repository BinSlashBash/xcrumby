/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.location.Location
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.location;

import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface a
extends IInterface {
    public void onLocationChanged(Location var1) throws RemoteException;

    public static abstract class com.google.android.gms.location.a$a
    extends Binder
    implements com.google.android.gms.location.a {
        public com.google.android.gms.location.a$a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.location.ILocationListener");
        }

        public static com.google.android.gms.location.a U(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.location.ILocationListener");
            if (iInterface != null && iInterface instanceof com.google.android.gms.location.a) {
                return (com.google.android.gms.location.a)iInterface;
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
                    parcel2.writeString("com.google.android.gms.location.ILocationListener");
                    return true;
                }
                case 1: 
            }
            parcel.enforceInterface("com.google.android.gms.location.ILocationListener");
            parcel = parcel.readInt() != 0 ? (Location)Location.CREATOR.createFromParcel(parcel) : null;
            this.onLocationChanged((Location)parcel);
            return true;
        }

        private static class a
        implements com.google.android.gms.location.a {
            private IBinder kn;

            a(IBinder iBinder) {
                this.kn = iBinder;
            }

            public IBinder asBinder() {
                return this.kn;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onLocationChanged(Location location) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.ILocationListener");
                    if (location != null) {
                        parcel.writeInt(1);
                        location.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(1, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

