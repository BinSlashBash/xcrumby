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
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.android.gms.maps.model.StreetViewPanoramaLocationCreator;

public interface q
extends IInterface {
    public void onStreetViewPanoramaChange(StreetViewPanoramaLocation var1) throws RemoteException;

    public static abstract class com.google.android.gms.maps.internal.q$a
    extends Binder
    implements q {
        public com.google.android.gms.maps.internal.q$a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.maps.internal.IOnStreetViewPanoramaChangeListener");
        }

        public static q at(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IOnStreetViewPanoramaChangeListener");
            if (iInterface != null && iInterface instanceof q) {
                return (q)iInterface;
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
                    parcel.writeString("com.google.android.gms.maps.internal.IOnStreetViewPanoramaChangeListener");
                    return true;
                }
                case 1: 
            }
            object.enforceInterface("com.google.android.gms.maps.internal.IOnStreetViewPanoramaChangeListener");
            object = object.readInt() != 0 ? StreetViewPanoramaLocation.CREATOR.createFromParcel((Parcel)object) : null;
            this.onStreetViewPanoramaChange((StreetViewPanoramaLocation)object);
            parcel.writeNoException();
            return true;
        }

        private static class a
        implements q {
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
            public void onStreetViewPanoramaChange(StreetViewPanoramaLocation streetViewPanoramaLocation) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IOnStreetViewPanoramaChangeListener");
                    if (streetViewPanoramaLocation != null) {
                        parcel.writeInt(1);
                        streetViewPanoramaLocation.writeToParcel(parcel, 0);
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

