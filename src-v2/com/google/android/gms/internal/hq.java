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
package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.DataHolderCreator;

public interface hq
extends IInterface {
    public void K(DataHolder var1) throws RemoteException;

    public void L(DataHolder var1) throws RemoteException;

    public static abstract class com.google.android.gms.internal.hq$a
    extends Binder
    implements hq {
        public static hq Y(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.location.places.internal.IPlacesCallbacks");
            if (iInterface != null && iInterface instanceof hq) {
                return (hq)iInterface;
            }
            return new a(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n2, Parcel parcel, Parcel object, int n3) throws RemoteException {
            Object var6_5 = null;
            Object var5_6 = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, (Parcel)object, n3);
                }
                case 1598968902: {
                    object.writeString("com.google.android.gms.location.places.internal.IPlacesCallbacks");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.google.android.gms.location.places.internal.IPlacesCallbacks");
                    object = var5_6;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.K((DataHolder)object);
                    return true;
                }
                case 2: 
            }
            parcel.enforceInterface("com.google.android.gms.location.places.internal.IPlacesCallbacks");
            object = var6_5;
            if (parcel.readInt() != 0) {
                object = DataHolder.CREATOR.createFromParcel(parcel);
            }
            this.L((DataHolder)object);
            return true;
        }

        private static class a
        implements hq {
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
            public void K(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IPlacesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void L(DataHolder dataHolder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IPlacesCallbacks");
                    if (dataHolder != null) {
                        parcel.writeInt(1);
                        dataHolder.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(2, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public IBinder asBinder() {
                return this.kn;
            }
        }

    }

}

