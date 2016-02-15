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

public interface cn
extends IInterface {
    public String getProductId() throws RemoteException;

    public void recordPlayBillingResolution(int var1) throws RemoteException;

    public void recordResolution(int var1) throws RemoteException;

    public static abstract class com.google.android.gms.internal.cn$a
    extends Binder
    implements cn {
        public com.google.android.gms.internal.cn$a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.ads.internal.purchase.client.IInAppPurchase");
        }

        public static cn o(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.purchase.client.IInAppPurchase");
            if (iInterface != null && iInterface instanceof cn) {
                return (cn)iInterface;
            }
            return new a(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            switch (n2) {
                default: {
                    return super.onTransact(n2, (Parcel)object, parcel, n3);
                }
                case 1598968902: {
                    parcel.writeString("com.google.android.gms.ads.internal.purchase.client.IInAppPurchase");
                    return true;
                }
                case 1: {
                    object.enforceInterface("com.google.android.gms.ads.internal.purchase.client.IInAppPurchase");
                    object = this.getProductId();
                    parcel.writeNoException();
                    parcel.writeString((String)object);
                    return true;
                }
                case 2: {
                    object.enforceInterface("com.google.android.gms.ads.internal.purchase.client.IInAppPurchase");
                    this.recordResolution(object.readInt());
                    parcel.writeNoException();
                    return true;
                }
                case 3: 
            }
            object.enforceInterface("com.google.android.gms.ads.internal.purchase.client.IInAppPurchase");
            this.recordPlayBillingResolution(object.readInt());
            parcel.writeNoException();
            return true;
        }

        private static class a
        implements cn {
            private IBinder kn;

            a(IBinder iBinder) {
                this.kn = iBinder;
            }

            public IBinder asBinder() {
                return this.kn;
            }

            @Override
            public String getProductId() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.purchase.client.IInAppPurchase");
                    this.kn.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    String string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void recordPlayBillingResolution(int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.purchase.client.IInAppPurchase");
                    parcel.writeInt(n2);
                    this.kn.transact(3, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void recordResolution(int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.purchase.client.IInAppPurchase");
                    parcel.writeInt(n2);
                    this.kn.transact(2, parcel, parcel2, 0);
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

