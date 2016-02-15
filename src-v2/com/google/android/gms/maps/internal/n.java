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

public interface n
extends IInterface {
    public boolean onMyLocationButtonClick() throws RemoteException;

    public static abstract class com.google.android.gms.maps.internal.n$a
    extends Binder
    implements n {
        public com.google.android.gms.maps.internal.n$a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.maps.internal.IOnMyLocationButtonClickListener");
        }

        public static n aq(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IOnMyLocationButtonClickListener");
            if (iInterface != null && iInterface instanceof n) {
                return (n)iInterface;
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
                    parcel2.writeString("com.google.android.gms.maps.internal.IOnMyLocationButtonClickListener");
                    return true;
                }
                case 1: 
            }
            parcel.enforceInterface("com.google.android.gms.maps.internal.IOnMyLocationButtonClickListener");
            boolean bl2 = this.onMyLocationButtonClick();
            parcel2.writeNoException();
            n2 = bl2 ? 1 : 0;
            parcel2.writeInt(n2);
            return true;
        }

        private static class a
        implements n {
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
            public boolean onMyLocationButtonClick() throws RemoteException {
                boolean bl2 = true;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IOnMyLocationButtonClickListener");
                    this.kn.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    int n2 = parcel2.readInt();
                    if (n2 == 0) {
                        bl2 = false;
                    }
                    return bl2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

