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
import com.google.android.gms.internal.cn;

public interface co
extends IInterface {
    public void a(cn var1) throws RemoteException;

    public static abstract class com.google.android.gms.internal.co$a
    extends Binder
    implements co {
        public com.google.android.gms.internal.co$a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.ads.internal.purchase.client.IInAppPurchaseListener");
        }

        public static co p(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.purchase.client.IInAppPurchaseListener");
            if (iInterface != null && iInterface instanceof co) {
                return (co)iInterface;
            }
            return new a(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.google.android.gms.ads.internal.purchase.client.IInAppPurchaseListener");
                    return true;
                }
                case 1: 
            }
            parcel.enforceInterface("com.google.android.gms.ads.internal.purchase.client.IInAppPurchaseListener");
            this.a(cn.a.o(parcel.readStrongBinder()));
            parcel2.writeNoException();
            return true;
        }

        private static class a
        implements co {
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
            public void a(cn cn2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.ads.internal.purchase.client.IInAppPurchaseListener");
                    cn2 = cn2 != null ? cn2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)cn2);
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

