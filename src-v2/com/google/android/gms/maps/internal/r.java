/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientationCreator;

public interface r
extends IInterface {
    public void onStreetViewPanoramaClick(StreetViewPanoramaOrientation var1) throws RemoteException;

    public static abstract class com.google.android.gms.maps.internal.r$a
    extends Binder
    implements r {
        public com.google.android.gms.maps.internal.r$a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.maps.internal.IOnStreetViewPanoramaClickListener");
        }

        public static r au(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IOnStreetViewPanoramaClickListener");
            if (iInterface != null && iInterface instanceof r) {
                return (r)iInterface;
            }
            return new a(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            switch (n2) {
                default: {
                    return super.onTransact(n2, (Parcel)object, parcel, n3);
                }
                case 1598968902: {
                    parcel.writeString("com.google.android.gms.maps.internal.IOnStreetViewPanoramaClickListener");
                    return true;
                }
                case 1: 
            }
            object.enforceInterface("com.google.android.gms.maps.internal.IOnStreetViewPanoramaClickListener");
            object = object.readInt() != 0 ? StreetViewPanoramaOrientation.CREATOR.createFromParcel((Parcel)object) : null;
            this.onStreetViewPanoramaClick((StreetViewPanoramaOrientation)object);
            parcel.writeNoException();
            return true;
        }

        private static class a
        implements r {
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
            public void onStreetViewPanoramaClick(StreetViewPanoramaOrientation streetViewPanoramaOrientation) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IOnStreetViewPanoramaClickListener");
                    if (streetViewPanoramaOrientation != null) {
                        parcel.writeInt(1);
                        streetViewPanoramaOrientation.writeToParcel(parcel, 0);
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
        }

    }

}

